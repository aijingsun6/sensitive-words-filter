package org.alking.swf;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.HashMap;
import java.util.Map;

class DFANode {

    protected char c;

    protected char lower;

    protected char dbc;

    private Map<Character, DFANode> cMap;

    private final Object cMapLock = new Object();

    private Map<String, DFANode> sMap;

    private final Object sMapLock = new Object();

    protected DFANode parent;

    protected boolean leaf;

    protected String pinyin = null;

    protected char simple;

    protected int score = 0;

    public DFANode getNode(char c) {
        synchronized (cMapLock) {
            if(cMap == null){
                return null;
            }
            return this.cMap.get(c);
        }
    }

    public DFANode getNode(String s) {
        synchronized (sMapLock) {
            if(sMap == null){
                return null;
            }
            return this.sMap.get(s);
        }
    }

    public void putNode(char c, DFANode node) {
        synchronized (cMapLock) {
            if (this.cMap == null) {
                this.cMap = new HashMap<>();
            }
            this.cMap.put(c, node);
        }
        node.parent = this;
    }

    public void putNode(String s, DFANode node) {
        synchronized (sMapLock) {
            if (this.sMap == null) {
                this.sMap = new HashMap<>();
            }
            this.sMap.put(s, node);
        }
        node.parent = this;
    }

    public DFANode(char c) {
        this(c, false, 0);
    }

    public DFANode(char c, boolean leaf, int score) {
        this.c = c;
        this.leaf = leaf;
        this.score = leaf ? score : 0;
        this.lower = Character.toLowerCase(c);
        this.dbc = BCConvert.sbc2dbc(c);
        if (Pinyin.isChinese(c)) {
            this.pinyin = Pinyin.toPinyin(c);
            this.simple = ZhConverterUtil.convertToSimple(String.valueOf(c)).toCharArray()[0];
        }
    }

    public boolean simpleSame() {
        return c == simple;
    }

    public boolean lowerSame() {
        return c == lower;
    }

    public boolean dbcSame() {
        return c == dbc;
    }

}

