package org.alking.swf;

import java.util.Objects;

public class WordMatch {

    private int start;

    private int end;

    private String matched;

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

    public String getMatched() {
        return matched;
    }

    public void setMatched(String matched) {
        this.matched = matched;
    }

    public WordMatch(int start, int end, String matched) {
        this.start = start;
        this.end = end;
        this.matched = matched;
    }

    public WordMatch(int start, int end, DFANode node) {
        this.start = start;
        this.end = end;
        StringBuilder sb = new StringBuilder();
        DFANode n = node;
        while (n != null) {
            sb.append(n.c);
            n = n.parent;
        }
        this.matched = sb.reverse().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordMatch)) return false;
        WordMatch wordMatch = (WordMatch) o;
        return start == wordMatch.start &&
                end == wordMatch.end &&
                Objects.equals(matched, wordMatch.matched);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, matched);
    }

    @Override
    public String toString() {
        return String.format("(%s,%d,%d)", matched, start, end);
    }
}