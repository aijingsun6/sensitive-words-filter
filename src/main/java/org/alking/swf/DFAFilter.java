package org.alking.swf;

import java.util.*;

public class DFAFilter {

    private final Map<Character, DFANode> charMap = new HashMap<>();

    private final Map<String, DFANode> strMap = new HashMap<>();

    private final Set<Character> stopWordSet = new HashSet<>();

    /**
     * 拼音
     */
    private boolean matchPinyin = false;

    /**
     * 大小写敏感
     */
    private boolean caseSensitive = false;

    /**
     * 匹配简体繁体
     */
    private boolean matchSimple = false;

    /**
     * 匹配全角半角
     */
    private boolean matchDBC = false;

    public DFAFilter() {

    }

    public DFAFilter(boolean matchPinyin, boolean caseSensitive, boolean matchSimple, boolean matchDBC) {
        this.matchPinyin = matchPinyin;
        this.caseSensitive = caseSensitive;
        this.matchSimple = matchSimple;
        this.matchDBC = matchDBC;
    }

    private static boolean isEmpty(String s) {
        return s == null || s.length() < 1;
    }


    public void putStopWord(String word) {
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
    public void putWord(String word, int score) {
        if (isEmpty(word)) {
            return;
        }
        char[] chars = word.toCharArray();
    }

    private void putChars(DFANode node, char[] chars, int idx, int score) {


    }
}