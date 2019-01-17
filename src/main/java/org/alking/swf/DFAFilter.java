package org.alking.swf;

import com.github.houbb.opencc4j.util.ZhConverterUtil;

import java.util.*;

/**
 *
 */
public class DFAFilter {

    private static final int PINYIN_MAX = 6;

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


        List<DFAMatch> acc = new ArrayList<>();
        for(int i = 0; i < word.length();i++){
            matchWord2(null,word,i,i,acc);
        }
        return acc;
    }

    private void matchWord2(final DFAMatch prev, final String word, final int start,final int originStart,final List<DFAMatch> acc){
        if(start > word.length()-1){
            return;
        }
        List<DFAMatch> findList = findMatch(prev,word,start);
        if(!findList.isEmpty()){
            for(DFAMatch match: findList){
                if(match.getMatched().leaf){
                    acc.add(new DFAMatch(originStart,match.getEnd(),match.getMatched()));
                }
                matchWord2(match,word,match.getEnd()+1,originStart,acc);
            }
        }
    }



    protected List<DFAMatch> findMatch(final DFAMatch prev,final String word, final int start){
        List<DFAMatch> acc = new ArrayList<>();
        if(start > word.length() - 1){
            return acc;
        }
        final char c = word.charAt(start);
        if(supportStopWord & stopWordSet.contains(c)){
            if(prev != null){
                acc.add(new DFAMatch(start,start,prev.getMatched()));
            }
            return acc;
        }

        if(prev == null){

            if(cMap.containsKey(c)){
                acc.add( new DFAMatch(start,start,cMap.get(c)));
            }

            if(supportPinyin){
                int sum = 0;
                StringBuilder sb = new StringBuilder();
                for(int i = start;i < word.length();i++){
                    if(i - start > PINYIN_MAX){
                        // 超过拼音的最大长度
                        break;
                    }
                    char t = word.charAt(i);
                    if(supportStopWord && stopWordSet.contains(t)){
                        sum ++;
                    }else if(Character.isLetter(t)){
                        sb.append(Character.toUpperCase(t));
                        sum ++;
                        if(sMap.containsKey(sb.toString())){
                            acc.add( new DFAMatch(start,start+sum-1,sMap.get(sb.toString())));
                        }
                    }else {
                        break;
                    }
                }
            }

            if(ignoreCase && Character.isUpperCase(c)){
                char lower = Character.toLowerCase(c);
                if(cMap.containsKey(lower)){
                    acc.add(new DFAMatch(start,start, cMap.get(lower)));
                }
            }

            if(supportSimple){
                char simple =  ZhConverterUtil.convertToSimple(String.valueOf(c)).toCharArray()[0];
                if(simple != c && cMap.containsKey(simple)){
                    acc.add(new DFAMatch(start,start, cMap.get(simple)));
                }
            }

            if(supportDbc){
                char dbc = BCConvert.sbc2dbc(c);
                if(dbc != c && cMap.containsKey(dbc)){
                    acc.add(new DFAMatch(start,start,cMap.get(dbc)));
                }
            }

            return acc;
        }
        // prev is not null
        DFANode node = prev.getMatched().getNode(c);
        if(node != null){
            acc.add( new DFAMatch(start,start,node));
        }
        if(supportPinyin){
            int sum = 0;
            StringBuilder sb = new StringBuilder();
            for(int i = start;i < word.length();i++){
                if(i - start > PINYIN_MAX){
                    // 超过拼音的最大长度
                    break;
                }
                char t = word.charAt(i);
                if(supportStopWord && stopWordSet.contains(t)){
                    sum ++;
                }else if(Character.isLetter(t)){
                    sb.append(Character.toUpperCase(t));
                    sum ++;
                    node = prev.getMatched().getNode(sb.toString());
                    if(node != null){
                        acc.add( new DFAMatch(start,start+sum-1, node));
                    }
                }else {
                    break;
                }
            }
        }

        if(ignoreCase && Character.isUpperCase(c)){
            char lower = Character.toLowerCase(c);
            node = prev.getMatched().getNode(lower);
            if(node != null){
                acc.add(new DFAMatch(start,start,node));
            }
        }

        if(supportSimple){
            char simple =  ZhConverterUtil.convertToSimple(String.valueOf(c)).toCharArray()[0];
            node = prev.getMatched().getNode(simple);
            if(simple != c && node != null){
                acc.add(new DFAMatch(start,start,node));
            }
        }

        if(supportDbc){
            char dbc = BCConvert.sbc2dbc(c);
            node = prev.getMatched().getNode(dbc);
            if(dbc != c && node != null){
                acc.add(new DFAMatch(start,start,node));
            }
        }
        return acc;
    }


}