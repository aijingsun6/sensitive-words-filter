package org.alking.swf;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.*;

/**
 *
 */
public class DFAFilter {

    private static final int PINYIN_MAX = 6;

    private final DFANode root = new DFANode('R', false);

    private final Set<Character> stopWordSet = new HashSet<>();
    /**
     * 是否支持拼音，配置汉字只能识别汉字，不能识别拼音
     */
    private boolean supportPinyin = false;
    /**
     * 忽略大小写，默认大小写敏感
     */
    private boolean ignoreCase = false;
    /**
     * 支持简体，繁体，默认不支持，配置简体只能识别简体
     */
    private boolean supportSimpleTraditional = false;
    /**
     * 支持半角全角，默认不支持，配置半角只能识别半角
     */
    private boolean supportDbc = false;
    /**
     * 支持停顿词，默认不支持，字之间使用别的字符隔开不识别
     */
    private boolean supportStopWord = false;

    public boolean isSupportPinyin() {
        return supportPinyin;
    }

    public void setSupportPinyin(boolean supportPinyin) {
        this.supportPinyin = supportPinyin;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public boolean isSupportSimpleTraditional() {
        return supportSimpleTraditional;
    }

    public boolean isSupportDbc() {
        return supportDbc;
    }

    public void setSupportDbc(boolean supportDbc) {
        this.supportDbc = supportDbc;
    }

    public boolean isSupportStopWord() {
        return supportStopWord;
    }

    public void setSupportStopWord(boolean supportStopWord) {
        this.supportStopWord = supportStopWord;
    }

    public DFANode getRoot() {
        return getRoot();
    }

    public DFAFilter() {

    }

    private static boolean isEmpty(String s) {
        return s == null || s.length() < 1;
    }

    public void setStopWord(String word) {
        if (!supportStopWord) {
            throw new IllegalStateException("stop word is not support");
        }
        this.stopWordSet.clear();
        char[] chars = word.toCharArray();
        for (char c : chars) {
            this.stopWordSet.add(c);
        }
    }

    public void putWord(final String word, final Comparable ext) {
        if (isEmpty(word)) {
            return;
        }
        putWord(this.root, word, 0, ext);
    }

    private void putWord(final DFANode prev, final String word, final int idx, final Comparable ext) {
        final boolean last = idx == word.length() - 1;
        final char ch = word.charAt(idx);
        if (supportStopWord && stopWordSet.contains(ch)) {
            // c 是停顿词
            if (last && prev != this.root) {
                prev.leaf = true;
                if (prev.ext == null) {
                    prev.ext = ext;
                } else if (ext != null && ext.compareTo(prev.ext) > 0) {
                    prev.ext = ext;
                }
                return;
            }
            putWord(prev, word, idx + 1, ext);
            return;
        }
        // c 不是停顿词

        //先进行字符操作
        this.putCh(prev, word, idx, ch, last, ext);
        if (this.supportPinyin && Pinyin.isChinese(ch)) {
            String pinyin = Pinyin.toPinyin(ch);
            this.putPinyin(prev, word, idx, pinyin, last, ext);
        }
    }

    private void putCh(final DFANode prev, final String word, final int idx, char ch, boolean last, final Comparable ext) {
        DFANode find = prev.getNode(ch);
        if (find == null) {
            find = new DFANode(ch, last);
            prev.putNode(ch, find);
        }

        if (last) {
            find.leaf = true;
            find.word = word;

            if (find.ext == null) {
                find.ext = ext;
            } else if (ext != null && ext.compareTo(find.ext) > 0) {
                find.ext = ext;
            }

            return;
        }
        this.putWord(find, word, idx + 1, ext);
    }

    private void putPinyin(final DFANode prev, final String word, final int idx, String pinyin, boolean last, final Comparable ext) {
        DFANode find = prev.getNode(pinyin);
        if (find == null) {
            char ch = word.charAt(idx);
            find = prev.getNode(ch);
            if (find == null) {
                find = new DFANode(ch, last);
            }
            prev.putNode(pinyin, find);
        }
        if (last) {
            find.leaf = true;
            find.word = word;
            if (find.ext == null) {
                find.ext = ext;
            } else if (ext != null && ext.compareTo(find.ext) > 0) {
                find.ext = ext;
            }
            return;
        }
        this.putWord(find, word, idx + 1, ext);
    }


    public List<DFAMatch> matchWord(final String word) {
        if (isEmpty(word)) {
            return Collections.emptyList();
        }
        List<DFAMatch> acc = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {
            matchWord2(this.root, word, i, i, acc);
        }
        return acc;
    }

    private void matchWord2(final DFANode prev, final String word, final int originStart, final int start, final List<DFAMatch> acc) {
        if (start > word.length() - 1) {
            return;
        }
        char ch = word.charAt(start);

        if (this.supportStopWord && this.stopWordSet.contains(ch)) {
            //停顿词
            matchWord2(prev, word, originStart, start + 1, acc);
            return;
        }

        // 字符寻找
        DFANode cNode = prev.getNode(ch);
        if (cNode != null) {
            if (cNode.leaf) {
                // 叶子节点
                acc.add(new DFAMatch(originStart, start, cNode));
            }
            matchWord2(cNode, word, originStart, start + 1, acc);
        }

        if (this.ignoreCase) {
            // 忽略大小写
            char low = Character.toLowerCase(ch);
            char upper = Character.toUpperCase(ch);
            this.matchWordChar(ch, low, prev, word, originStart, start, acc);
            this.matchWordChar(ch, upper, prev, word, originStart, start, acc);
        }

        if (this.supportDbc) {
            // 支持全角半角
            char dbc = BCConvert.sbc2dbc(ch);
            char sbc = BCConvert.dbc2sbc(ch);
            this.matchWordChar(ch, dbc, prev, word, originStart, start, acc);
            this.matchWordChar(ch, sbc, prev, word, originStart, start, acc);
        }

        if (this.supportSimpleTraditional && Pinyin.isChinese(ch)) {
            // 支持简体，繁体
            String simple = ZhConverterUtil.convertToSimple(String.valueOf(ch));
            char simpleChar = simple.charAt(0);
            String trad = ZhConverterUtil.convertToTraditional(String.valueOf(ch));
            char tradChar = trad.charAt(0);
            this.matchWordChar(ch, simpleChar, prev, word, originStart, start, acc);
            this.matchWordChar(ch, tradChar, prev, word, originStart, start, acc);
        }

        if (this.supportPinyin && Character.isLetter(ch)) {
            // 支持拼音，但是要是 letter
            StringBuilder sb = new StringBuilder();
            sb.append(Character.toUpperCase(ch));
            for (int i = 1; i < PINYIN_MAX; i++) {
                //拼音最多5个字符
                char c = word.charAt(start + i);
                if (!Character.isLetter(c)) {
                    break;
                }
                sb.append(Character.toUpperCase(c));
                this.matchWordPinyin(sb.toString(), prev, word, originStart, start + i, acc);
            }
        }
    }


    private void matchWordChar(final char oriCh, final char ch, final DFANode prev, final String word, final int originStart, final int start, final List<DFAMatch> acc) {
        if (oriCh == ch) {
            return;
        }
        DFANode cNode = prev.getNode(ch);
        if (cNode != null) {
            if (cNode.leaf) {
                acc.add(new DFAMatch(originStart, start, cNode));
            }
            matchWord2(cNode, word, originStart, start + 1, acc);
        }
    }

    private void matchWordPinyin(final String pinyin, final DFANode prev, final String word, final int originStart, final int start, final List<DFAMatch> acc) {
        DFANode sNode = prev.getNode(pinyin);
        if (sNode != null) {
            if (sNode.leaf) {
                acc.add(new DFAMatch(originStart, start, sNode));
            }
            matchWord2(sNode, word, originStart, start + 1, acc);
        }
    }

    public String replaceWord(final String origin, final List<DFAMatch> matches, final char c) {
        if (isEmpty(origin)) {
            return origin;
        }
        char[] chars = origin.toCharArray();
        int length = chars.length;
        for (DFAMatch match : matches) {
            for (int idx = match.getStart(); idx <= match.getEnd() && idx < length; idx++) {
                chars[idx] = c;
            }
        }
        return new String(chars);
    }

}