package org.alking.swf;

public class DFAMatch <T extends Comparable<T>>{

    private int start;

    private int end;

    private DFANode<T> matched;

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public DFANode<T> getMatched() {
        return matched;
    }

    public DFAMatch(int start, int end, DFANode<T> matched) {
        this.start = start;
        this.end = end;
        this.matched = matched;
    }

    public String getWord() {
        return this.matched.word;
    }

}