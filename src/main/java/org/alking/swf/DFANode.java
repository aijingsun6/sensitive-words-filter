package org.alking.swf;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.HashMap;
import java.util.Map;

class DFANode {

    public char c;

    public char lower;

    public char dbc;

    public Map<Character, DFANode> cMap;

    private final Object cMapLock = new Object();

    public Map<String, DFANode> sMap;

    private final Object sMapLock = new Object();

    public DFANode parent;

    public boolean leaf;

    public String pinyin = null;

    public char simple;

    public int score = 0;

    public DFANode getNode(char c) {
        synchronized (cMapLock) {
            return this.cMap.get(c);
        }
    }

    public DFANode getNode(String s) {
        synchronized (sMapLock) {
            return this.sMap.get(s);
        }
    }

    public void putNode(char c, DFANode node) {
        synchronized (cMapLock) {
            if (this.cMap == null) {
                this.cMap = new HashMap<>();
            }
        }

        this.cMap.put(c, node);
        node.parent = this;
    }

    public void putNode(String s, DFANode node) {
        synchronized (sMapLock) {
            if (this.sMap == null) {
                this.sMap = new HashMap<>();
            }
        }
        this.sMap.put(s, node);
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

