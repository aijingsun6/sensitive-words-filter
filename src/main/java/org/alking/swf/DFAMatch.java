package org.alking.swf;

public class DFAMatch {

    private int start;

    private int end;

    private DFANode matched;

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public DFANode getMatched() {
        return matched;
    }

    public DFAMatch(int start, int end, DFANode matched) {
        this.start = start;
        this.end = end;
        this.matched = matched;
    }

    public String getWord() {
        return this.matched.word;
    }

}