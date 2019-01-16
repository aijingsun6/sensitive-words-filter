package org.alking.swf;

import java.util.*;

/**
 *
 */
public class DFAFilter {

    private final Map<Character, DFANode> cMap = new HashMap<>();

    private final Map<String, DFANode> sMap = new HashMap<>();

    private final Set<Character> stopWordSet = new HashSet<>();

    private boolean supportPinyin = false;

    private boolean ignoreCase = false;

    private boolean supportSimple = false;

    private boolean supportDbc = false;

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

    public boolean isSupportSimple() {
        return supportSimple;
    }

    public void setSupportSimple(boolean supportSimple) {
        this.supportSimple = supportSimple;
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

    public Map<Character, DFANode> getcMap() {
        return cMap;
    }

    public Map<String, DFANode> getsMap() {
        return sMap;
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

    public void putWord(final String word, final int score) {
        if (isEmpty(word)) {
            return;
        }

        putWord(null, word, 0, score);
    }

    private void putWord(final DFANode acc, final String word, final int idx, final int score) {
        final boolean last = idx == word.length() - 1;
        final char c = word.charAt(idx);
        if (supportStopWord && stopWordSet.contains(c)) {
            // c is stop word
            if (last && acc != null) {
                acc.leaf = true;
                acc.score = score;
            } else if (!last) {
                putWord(acc, word, idx + 1, score);
            }
            return;
        }
        // c is not stop word
        DFANode node = new DFANode(c, last, score);
        if (acc == null) {

            this.cMap.put(c, node);

            if (this.supportSimple && node.simple != 0 && !node.simpleSame()) {
                this.cMap.put(node.simple, node);
            }

            if (this.supportPinyin && !isEmpty(node.pinyin)) {
                this.sMap.put(node.pinyin, node);
            }

            if (this.supportDbc && !node.dbcSame()) {
                this.cMap.put(node.dbc, node);
            }

            if (this.ignoreCase && !node.lowerSame()) {
                this.cMap.put(node.lower, node);
            }

        } else {
            acc.putNode(c, node);

            if (this.supportSimple && node.simple != 0 && !node.simpleSame()) {
                acc.putNode(node.simple, node);
            }

            if (this.supportPinyin && !isEmpty(node.pinyin)) {
                acc.putNode(node.pinyin, node);
            }

            if (this.supportDbc && !node.dbcSame()) {
                acc.putNode(node.dbc, node);
            }

            if (this.ignoreCase && !node.lowerSame()) {
                acc.putNode(node.lower, node);
            }
        }
        if (!last) {
            putWord(node, word, idx + 1, score);
        }
    }

    public List<DFAMatch> matchWord(final String word) {
        if (isEmpty(word)) {
            return Collections.emptyList();
        }

        char[] chars = word.toCharArray();


        return null;
    }

    private DFANode firstNode(char c) {


        return null;

    }

    private void matchChars(final char[] chars, int idx, final DFANode prev, final int start, final List<DFAMatch> acc) {

        if (prev != null && prev.leaf) {
            DFAMatch match = new DFAMatch(start, idx - 1, prev);
            acc.add(match);
        }

        if (idx > chars.length - 1) {
            // reach end
            return;
        }
        if (prev == null) {
            return;
        }


    }


}