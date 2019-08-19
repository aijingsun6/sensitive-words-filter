package org.alking.swf;

import java.util.HashMap;
import java.util.Map;

final class DFANode {

    protected final char ch;

    protected boolean leaf;

    protected String word = null;

    protected Comparable ext;

    protected DFANode parent;

    /**
     * chat -> node
     */
    private Map<Character, DFANode> cMap = new HashMap<>(0);

    /**
     * string -> node
     * 用于拼音
     */
    private Map<String, DFANode> sMap = new HashMap<>(0);

    public Map<Character, DFANode> getcMap() {
        return cMap;
    }

    public Map<String, DFANode> getsMap() {
        return sMap;
    }

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

    public DFANode(char ch, boolean leaf) {
        this.ch = ch;
        this.leaf = leaf;
    }
}

