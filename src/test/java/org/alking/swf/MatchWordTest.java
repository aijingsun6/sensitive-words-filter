package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MatchWordTest {

    @Test
    public void test1(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("fuck",1);

        List<DFAMatch> matchList = dfaFilter.matchWord("i fuck u, fuck r");

        Assert.assertEquals(2,matchList.size());
        DFAMatch match = matchList.get(0);
        Assert.assertEquals(2,match.getStart());
        Assert.assertEquals(5,match.getEnd());
        Assert.assertEquals("fuck",match.getWord());

        match = matchList.get(1);
        Assert.assertEquals(10,match.getStart());
        Assert.assertEquals(13,match.getEnd());
        Assert.assertEquals("fuck",match.getWord());

    }
}
