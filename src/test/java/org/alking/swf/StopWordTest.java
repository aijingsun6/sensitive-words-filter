package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class StopWordTest {

    @Test
    public void test1() {
        DFAFilter dfaFilter = new DFAFilter();
        DFAConfig config = new DFAConfig.Builder().setSupportStopWord(true).setStopWord("|").build();
        dfaFilter.setConfig(config);
        dfaFilter.putWord("fuck", 1);

        String src = "fuck";
        List<DFAMatch> matchList = dfaFilter.matchWord("fuck");
        String replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());

        DFAMatch match = matchList.get(0);
        Assert.assertEquals(0, match.getStart());
        Assert.assertEquals(3, match.getEnd());
        Assert.assertEquals("fuck", match.getMatched().word);
        Assert.assertEquals("****", replaced);


        src = "|f|u|c|k|";
        matchList = dfaFilter.matchWord(src);
        replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());

        match = matchList.get(0);
        Assert.assertEquals(1, match.getStart());
        Assert.assertEquals(7, match.getEnd());
        Assert.assertEquals("fuck", match.getMatched().word);
        Assert.assertEquals("|*******|", replaced);

        src = "f|u|c|k";
        matchList = dfaFilter.matchWord(src);
        replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());

        match = matchList.get(0);
        Assert.assertEquals(0, match.getStart());
        Assert.assertEquals(6, match.getEnd());
        Assert.assertEquals("fuck", match.getMatched().word);
        Assert.assertEquals("*******", replaced);
    }

    @Test
    public void test2() {
        DFAFilter dfaFilter = new DFAFilter();
        DFAConfig config = new DFAConfig.Builder().setSupportStopWord(true).setStopWord("| ").build();
        dfaFilter.setConfig(config);
        dfaFilter.putWord("fuck", 1);

        String src = "fuck";
        List<DFAMatch> matchList = dfaFilter.matchWord("fuck");
        String replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());

        DFAMatch match = matchList.get(0);
        Assert.assertEquals(0, match.getStart());
        Assert.assertEquals(3, match.getEnd());
        Assert.assertEquals("fuck", match.getMatched().word);
        Assert.assertEquals("****", replaced);

        src = "f|u c|k";
        matchList = dfaFilter.matchWord(src);
        replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());

        match = matchList.get(0);
        Assert.assertEquals(0, match.getStart());
        Assert.assertEquals(6, match.getEnd());
        Assert.assertEquals("fuck", match.getMatched().word);
        Assert.assertEquals("*******", replaced);
    }

    @Test
    public void test3() {
        DFAFilter dfaFilter = new DFAFilter();
        DFAConfig config = new DFAConfig.Builder().setSupportStopWord(true).setStopWord("| ").build();
        dfaFilter.setConfig(config);
        dfaFilter.putWord("fuck you", 1);

        String src = "fuck you";
        List<DFAMatch> matchList = dfaFilter.matchWord(src);
        String replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());

        DFAMatch match = matchList.get(0);
        Assert.assertEquals(0, match.getStart());
        Assert.assertEquals(7, match.getEnd());
        Assert.assertEquals("fuck you", match.getMatched().word);
        Assert.assertEquals("********", replaced);


        src = "fuckyou";
        matchList = dfaFilter.matchWord(src);
        replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());

        match = matchList.get(0);
        Assert.assertEquals(0, match.getStart());
        Assert.assertEquals(6, match.getEnd());
        Assert.assertEquals("fuck you", match.getMatched().word);
        Assert.assertEquals("*******", replaced);
    }
}
