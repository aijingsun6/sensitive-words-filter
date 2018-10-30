package org.alking.swf;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.github.promeg.pinyinhelper.Pinyin;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class DFAFilter {

    private final Map<Character, DFANode> rootMap = new HashMap<>();

    private final Set<Character> stopWordSet = new HashSet<>();

    public DFAFilter() {

    }

    /**
     * 转简体 +  转半角 +  转小写
     */
    private String convertString(String word) {
        // 转简体
        word = ZhConverterUtil.convertToSimple(word);
        // 转半角
        word = BCConvert.sbc2dbcCase(word);
        // 转小写
        word = word.toLowerCase();
        return word;
    }

    private String convertPinyin(String word) {
        // 转简体
        word = ZhConverterUtil.convertToSimple(word);
        // 转半角
        word = BCConvert.sbc2dbcCase(word);
        // 转拼音
        word = Pinyin.toPinyin(word, "");
        // 转小写
        word = word.toLowerCase();
        return word;
    }

    public void putStopWord(String word) {
        word = convertString(word);
        char[] chars = word.toCharArray();
        for (char c : chars) {
            this.stopWordSet.add(c);
        }
    }

    /**
     * 条件：
     * 1.小写
     * 2.半角
     * 3.中文简体
     */
    public void putWord(String word) {
        if (StringUtils.isEmpty(word)) {
            return;
        }
        if (word.length() < 2) {
            return;
        }
        String word2 = convertString(word);
        char[] chars = word2.toCharArray();
        putChars(chars);

        String word3 = convertPinyin(word);
        if (!word3.equals(word2)) {
            chars = word3.toCharArray();
            putChars(chars);
        }
    }

    private void putChars(char[] chars) {
        DFANode acc = null;
        int size = chars.length;
        for (int i = 0; i < size; i++) {
            char c = chars[i];
            if (Character.isSpaceChar(c) || stopWordSet.contains(c)) {
                continue;
            }
            boolean last = false;
            if(i == (size -1)){
                last = true;
            }
            if (acc == null) {
                // 第一个字符
                if (rootMap.containsKey(c)) {
                    acc = rootMap.get(c);
                } else {
                    acc = new DFANode(c,last);
                    rootMap.put(c, acc);
                }
            } else {
                // 非第一个字符
                DFANode node = acc.getNode(c);
                if (node != null) {
                    acc = node;
                } else {
                    node = new DFANode(c,last);
                    acc.putNode(c, node);
                    acc = node;
                }
            }
        }
    }


    public Set<WordMatch> matchWords(String content) {
        if (StringUtils.isEmpty(content)) {
            return new HashSet<>();
        }

        String content1 = convertString(content);
        Set<WordMatch> result = matchChars(content1.toCharArray());
        String content2 = convertPinyin(content);
        if (content2.equals(content1)) {
            return result;
        }
        result.addAll(matchPinyin(content, content2));
        return result;
    }

    private Set<WordMatch> matchPinyin(String origin, String pinyin) {

        // 转换拼音后的映射
        Map<Integer, Integer> idxMap = new HashMap<>();
        int idx = 0;
        char[] chars = origin.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Pinyin.isChinese(c)) {
                String ch = Pinyin.toPinyin(c);
                for (int j = 0; j < ch.length(); j++) {
                    idxMap.put(idx + j, i);
                }
                idx += ch.length();
            }else {
                idxMap.put(idx,i);
                idx += 1;
            }
        }

        Set<WordMatch> matchSet = matchChars(pinyin.toCharArray());

        Set<WordMatch> result = new HashSet<>();
        for (WordMatch match : matchSet) {
            int start = idxMap.get(match.getStart());
            int end = idxMap.get(match.getEnd());
            result.add(new WordMatch(start, end, match.getMatched()));
        }
        return result;
    }

    private Set<WordMatch> matchChars(char[] chars) {
        Set<WordMatch> result = new HashSet<>();
        int size = chars.length;
        for (int i = 0; i < size; i++) {
            // 外循环
            char c = chars[i];
            if (Character.isSpaceChar(c) || stopWordSet.contains(c)) {
                continue;
            }
            DFANode node = this.rootMap.get(c);
            if (node == null) {
                // 第一个字符匹配不上
                continue;
            }
            char lastChar = chars[i];
            for (int j = i + 1; j < size; j++) {
                // 内循环
                char c2 = chars[j];
                if (Character.isSpaceChar(c2) || stopWordSet.contains(c2)) {
                    continue;
                }
                DFANode n2 = node.getNode(c2);
                if (n2 != null && n2.leaf) {
                    result.add(new WordMatch(i, j, n2));
                    break;
                } else if (n2 != null) {
                    node = n2;
                    lastChar = chars[j];
                } else if ((n2 == null) && (chars[j] == lastChar)) {
                    continue;
                } else {
                    break;
                }
            }
        }
        return result;
    }

    private static class WordRange {

        public int start;

        public int end;

        public WordRange(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public String replace(String content, char replaceChar) {
        Set<WordMatch> matches = matchWords(content);
        return replace(content, matches, replaceChar);
    }

    public String replace(String content, Set<WordMatch> matches, char replaceChar) {
        List<WordMatch> matchList = new ArrayList<>(matches.size());
        for (WordMatch match : matches) {
            matchList.add(match);
        }
        Collections.sort(matchList, new Comparator<WordMatch>() {
            @Override
            public int compare(WordMatch o1, WordMatch o2) {
                int start1 = o1.getStart();
                int start2 = o2.getStart();
                return start1 - start2;
            }
        });

        LinkedList<WordRange> ranges = new LinkedList<>();
        for (WordMatch match : matchList) {
            if (ranges.isEmpty()) {
                ranges.add(new WordRange(match.getStart(), match.getEnd()));
            } else {
                WordRange last = ranges.getLast();
                if (last.end >= match.getStart()) {
                    // merge
                    last.end = Math.max(last.end, match.getEnd());
                } else {
                    ranges.add(new WordRange(match.getStart(), match.getEnd()));
                }
            }
        }

        char[] chars = content.toCharArray();
        for (WordRange range : ranges) {
            int start = range.start;
            int end = range.end;
            for (int i = start; i <= end; i++) {
                chars[i] = replaceChar;
            }
        }
        return new String(chars);
    }
}