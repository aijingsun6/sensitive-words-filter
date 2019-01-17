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

    @Test
    public void test2(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setIgnoreCase(true);
        dfaFilter.putWord("fuck",1);

        List<DFAMatch> matchList = dfaFilter.matchWord("i fUck u, fuCk r");

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

    @Test
    public void test3(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportStopWord( true );
        dfaFilter.setStopWord("|#");
        dfaFilter.putWord("fuck",1);
        List<DFAMatch> matchList = dfaFilter.matchWord("i f|u#c|k u");
        Assert.assertEquals(1,matchList.size());
        Assert.assertEquals(2, matchList.get(0).getStart());
        Assert.assertEquals(8, matchList.get(0).getEnd());
        Assert.assertEquals("fuck", matchList.get(0).getWord());
    }

    /**
     * 脏字abc,abcde
     * abcde应该有2个匹配项目
     */
    @Test
    public void test4(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("abc",1);
        dfaFilter.putWord("abcde",2);

        List<DFAMatch> matchList = dfaFilter.matchWord("abcde");
        Assert.assertEquals(2,matchList.size());

        Assert.assertEquals(0, matchList.get(0).getStart());
        Assert.assertEquals(2, matchList.get(0).getEnd());
        Assert.assertEquals( 1, matchList.get(0).score());
        Assert.assertEquals("abc",matchList.get(0).getWord());

        Assert.assertEquals(0, matchList.get(1).getStart());
        Assert.assertEquals(4, matchList.get(1).getEnd());
        Assert.assertEquals( 2, matchList.get(1).score());
        Assert.assertEquals("abcde",matchList.get(1).getWord());
    }
}
