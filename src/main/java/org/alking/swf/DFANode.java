package org.alking.swf;

import java.util.HashMap;
import java.util.Map;

public class DFANode {

    public char c;

    public final Map<Character, DFANode> map = new HashMap<>();

    public DFANode parent;

    public boolean leaf;

    public DFANode getNode(char c) {
        return map.get(c);
    }

    public void putNode(char c, DFANode node) {
        node.parent = this;
        this.map.put(c, node);
    }

    public DFANode(char c,boolean leaf) {
        this.c = c;
        this.leaf = leaf;
    }
}

