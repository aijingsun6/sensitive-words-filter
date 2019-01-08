package org.alking.swf;

public class DFAMatch {

    private int start;

    private int end;

    private DFANode match;

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

    public DFANode getMatch() {
        return match;
    }

    public void setMatch(DFANode match) {
        this.match = match;
    }

    public DFAMatch() {
    }

    public DFAMatch(int start, int end, DFANode match) {
        this.start = start;
        this.end = end;
        this.match = match;
    }
}