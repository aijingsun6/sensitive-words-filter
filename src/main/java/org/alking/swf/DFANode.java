package org.alking.swf;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.HashMap;
import java.util.Map;

public class DFANode {

    /**
     * 节点字符，可以是字母，可以是汉字，也可是是别的字
     */
    public char c;

    /**
     * 小写字符
     */
    public char lower;

    /**
     * 半角字符
     */
    public char dbc;

    /**
     * char map
     */
    public final Map<Character, DFANode> cMap = new HashMap<>();

    /**
     * string map
     * pinyin -> DFANode
     */
    public final Map<String, DFANode> sMap = new HashMap<>();

    /**
     * 父节点
     */
    public DFANode parent;

    /**
     * 是否是叶子节点
     */
    public boolean leaf;

    /**
     * 如果是汉字，那么就会有拼音
     */
    public String pinyin = null;

    public char simple;

    public int score = 0;

    public DFANode getNode(char c) {
        return this.cMap.get(c);
    }

    public DFANode getNode(String s) {
        return this.sMap.get(s);
    }

    public void putNode(char c, DFANode node) {
        this.cMap.put(c, node);
        node.parent = this;
    }

    public void putNode(String s, DFANode node) {
        this.sMap.put(s, node);
        node.parent = this;
    }

    public DFANode(char c, boolean leaf, int score) {
        this.c = c;
        this.leaf = leaf;
        this.score = score;
        this.lower = Character.toLowerCase(c);
        this.dbc = BCConvert.sbc2dbc(c);
        if (Pinyin.isChinese(c)) {
            this.pinyin = Pinyin.toPinyin(c);
            this.simple = ZhConverterUtil.convertToSimple(String.valueOf(c)).toCharArray()[0];
        }
    }

    /**
     * 简繁体是否一致
     */
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

