package org.alking.swf;

import java.util.*;

public class DFAFilter {

    private final Map<Character, DFANode> cMap = new HashMap<>();

    private final Map<String, DFANode> sMap = new HashMap<>();

    private final Set<Character> stopWordSet = new HashSet<>();

    private boolean pinyin = true;

    private boolean ignoreCase = false;

    private boolean ignoreSimple = true;

    private boolean ignoreDbc = true;

    private boolean stopWord = true;

    public Map<Character, DFANode> getcMap() {
        return cMap;
    }

    public Map<String, DFANode> getsMap() {
        return sMap;
    }

    public Set<Character> getStopWordSet() {
        return stopWordSet;
    }

    public boolean isPinyin() {
        return pinyin;
    }

    public void setPinyin(boolean pinyin) {
        this.pinyin = pinyin;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public boolean isIgnoreSimple() {
        return ignoreSimple;
    }

    public void setIgnoreSimple(boolean ignoreSimple) {
        this.ignoreSimple = ignoreSimple;
    }

    public boolean isIgnoreDbc() {
        return ignoreDbc;
    }

    public void setIgnoreDbc(boolean ignoreDbc) {
        this.ignoreDbc = ignoreDbc;
    }

    public boolean isStopWord() {
        return stopWord;
    }

    public void setStopWord(boolean stopWord) {
        this.stopWord = stopWord;
    }

    public DFAFilter() {

    }

    public DFAFilter(boolean pinyin, boolean ignoreCase, boolean ignoreSimple, boolean ignoreDbc, boolean stopWord) {
        this.pinyin = pinyin;
        this.ignoreCase = ignoreCase;
        this.ignoreSimple = ignoreSimple;
        this.ignoreDbc = ignoreDbc;
        this.stopWord = stopWord;
    }

    private static boolean isEmpty(String s) {
        return s == null || s.length() < 1;
    }

    public void setStopWord(String word) {
        this.stopWordSet.clear();
        char[] chars = word.toCharArray();
        for (char c : chars) {
            this.stopWordSet.add(c);
        }
    }

    public void putWord(String word, int score) {
        if (isEmpty(word)) {
            return;
        }

        char[] chars = word.toCharArray();
        putChars(null, chars, 0, score);
    }

    private void putChars(DFANode acc, char[] chars, int idx, int score) {
        boolean last = idx == (chars.length - 1);
        char c = chars[idx];
        if (stopWord && this.stopWordSet.contains(c)) {

            if (last && acc != null) {
                acc.leaf = true;
                acc.score = score;
            } else if (!last) {
                putChars(acc, chars, idx + 1, score);
            }
            return;
        }

        DFANode node = new DFANode(c, last, score);
        if (acc == null) {

            this.cMap.put( c,node);

            if (this.ignoreSimple && node.simple != 0 && !node.simpleSame()) {
               this.cMap.put( node.simple,node);
            }

            if (this.pinyin && !isEmpty(node.pinyin)) {
                this.sMap.put(node.pinyin,node);
            }

            if(this.ignoreDbc && !node.dbcSame()){
                this.cMap.put(node.dbc,node);
            }

            if(this.ignoreCase && !node.lowerSame()){
                this.cMap.put(node.lower,node);
            }

        }else {
            acc.putNode(c, node);

            if (this.ignoreSimple && node.simple != 0 && !node.simpleSame()) {
                acc.putNode(node.simple, node);
            }

            if (this.pinyin && !isEmpty(node.pinyin)) {
                acc.putNode(node.pinyin, node);
            }

            if(this.ignoreDbc && !node.dbcSame()){
                acc.putNode(node.dbc,node);
            }

            if(this.ignoreCase && !node.lowerSame()){
                acc.putNode(node.lower,node);
            }
        }
        if(!last){
            putChars(node,chars,idx+1,score);
        }
    }
}