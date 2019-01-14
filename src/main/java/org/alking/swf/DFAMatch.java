package org.alking.swf;

public class DFAMatch {

    private int start;

    private int end;

    private DFANode matched;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public DFANode getMatched() {
        return matched;
    }

    public void setMatched(DFANode matched) {
        this.matched = matched;
    }

    public DFAMatch() {
    }

    public DFAMatch(int start, int end, DFANode matched) {
        this.start = start;
        this.end = end;
        this.matched = matched;
    }
}