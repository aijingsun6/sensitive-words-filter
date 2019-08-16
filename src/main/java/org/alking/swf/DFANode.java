package org.alking.swf;

import java.util.HashMap;
import java.util.Map;

final class DFANode<T extends Comparable<T>> {

    protected final char ch;

    protected boolean leaf;

    protected String word = null;

    protected T ext;

    protected DFANode parent;

    /**
     * chat -> node
     */
    private Map<Character, DFANode<T>> cMap = new HashMap<>(0);

    /**
     * string -> node
     * 用于拼音
     */
    private Map<String, DFANode<T>> sMap = new HashMap<>(0);

    public DFANode<T> getNode(char c) {
        return this.cMap.get(c);
    }

    public DFANode<T> getNode(String s) {
        return this.sMap.get(s);
    }

    public void putNode(char c, DFANode<T> node) {
        this.cMap.put(c, node);
        node.parent = this;
    }

    public void putNode(String s, DFANode<T> node) {
        this.sMap.put(s, node);
        node.parent = this;
    }

    public DFANode(char ch, boolean leaf) {
        this.ch = ch;
        this.leaf = leaf;
    }
}

