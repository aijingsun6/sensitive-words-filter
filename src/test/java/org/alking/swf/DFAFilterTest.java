package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DFAFilterTest {

    @Test
    public void findMatchTest1(){

        DFAFilter dfaFilter = new DFAFilter();

        dfaFilter.putWord("fuck",1);

        List<DFAMatch> ret = dfaFilter.findMatch(null,"fuck",0);

        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(0, ret.get(0).getStart());
        Assert.assertEquals(0, ret.get(0).getEnd());
        Assert.assertEquals('f',ret.get(0).getMatched().c);

        DFANode node = null;

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"fuck",1);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).getStart());
        Assert.assertEquals(1, ret.get(0).getEnd());
        Assert.assertEquals('u',ret.get(0).getMatched().c);

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"fuck",2);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(2, ret.get(0).getStart());
        Assert.assertEquals(2, ret.get(0).getEnd());
        Assert.assertEquals('c',ret.get(0).getMatched().c);

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"fuck",3);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(3, ret.get(0).getStart());
        Assert.assertEquals(3, ret.get(0).getEnd());
        Assert.assertEquals('k',ret.get(0).getMatched().c);

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"fuck",4);
        Assert.assertEquals(0,ret.size());

    }

    @Test
    public void findMatchTest2(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportStopWord( true );
        dfaFilter.setStopWord("|");

        dfaFilter.putWord("fuck",1);

        List<DFAMatch> ret;
        ret = dfaFilter.findMatch(null,"f|u|c|k",0);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(0, ret.get(0).getStart());
        Assert.assertEquals(0, ret.get(0).getEnd());
        Assert.assertEquals('f',ret.get(0).getMatched().c);

        DFANode node = null;

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"f|u|c|k",1);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).getStart());
        Assert.assertEquals(1, ret.get(0).getEnd());
        Assert.assertEquals('f',ret.get(0).getMatched().c);

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"f|u|c|k",2);
        Assert.assertEquals(2, ret.get(0).getStart());
        Assert.assertEquals(2, ret.get(0).getEnd());
        Assert.assertEquals('u',ret.get(0).getMatched().c);

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"f|u|c|k",3);
        Assert.assertEquals(3, ret.get(0).getStart());
        Assert.assertEquals(3, ret.get(0).getEnd());
        Assert.assertEquals('u',ret.get(0).getMatched().c);

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"f|u|c|k",4);
        Assert.assertEquals(4, ret.get(0).getStart());
        Assert.assertEquals(4, ret.get(0).getEnd());
        Assert.assertEquals('c',ret.get(0).getMatched().c);

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"f|u|c|k",5);
        Assert.assertEquals(5, ret.get(0).getStart());
        Assert.assertEquals(5, ret.get(0).getEnd());
        Assert.assertEquals('c',ret.get(0).getMatched().c);

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"f|u|c|k",6);
        Assert.assertEquals(6, ret.get(0).getStart());
        Assert.assertEquals(6, ret.get(0).getEnd());
        Assert.assertEquals('k',ret.get(0).getMatched().c);

    }

    @Test
    public void findMatchTest3(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setIgnoreCase(true);

        dfaFilter.putWord("fuck",1);

        List<DFAMatch> ret = dfaFilter.findMatch(null,"FUCK",0);

        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(0, ret.get(0).getStart());
        Assert.assertEquals(0, ret.get(0).getEnd());
        Assert.assertEquals('f',ret.get(0).getMatched().c);

        DFANode node = null;

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"FUCK",1);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).getStart());
        Assert.assertEquals(1, ret.get(0).getEnd());
        Assert.assertEquals('u',ret.get(0).getMatched().c);

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"FUCK",2);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(2, ret.get(0).getStart());
        Assert.assertEquals(2, ret.get(0).getEnd());
        Assert.assertEquals('c',ret.get(0).getMatched().c);

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"FUCK",3);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(3, ret.get(0).getStart());
        Assert.assertEquals(3, ret.get(0).getEnd());
        Assert.assertEquals('k',ret.get(0).getMatched().c);

        node = ret.get(0).getMatched();
        ret = dfaFilter.findMatch(node,"FUCK",4);
        Assert.assertEquals(0,ret.size());

    }

    @Test
    public void findMatchTest4(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportPinyin( true );
        dfaFilter.setSupportStopWord( true );
        dfaFilter.setStopWord("|");

        dfaFilter.putWord("毛",1);

        List<DFAMatch> ret = dfaFilter.findMatch(null,"mao",0);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(0, ret.get(0).getStart());
        Assert.assertEquals(2, ret.get(0).getEnd());
        Assert.assertEquals("MAO",ret.get(0).getMatched().pinyin);
        Assert.assertTrue(ret.get(0).getMatched().leaf);

    }

    @Test
    public void findMatchTest5(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportPinyin( true );
        dfaFilter.setSupportStopWord( true );
        dfaFilter.setStopWord("|");

        dfaFilter.putWord("毛",1);

        List<DFAMatch> ret = dfaFilter.findMatch(null,"m|a|o",0);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(0, ret.get(0).getStart());
        Assert.assertEquals(4, ret.get(0).getEnd());
        Assert.assertEquals("MAO",ret.get(0).getMatched().pinyin);
        Assert.assertTrue(ret.get(0).getMatched().leaf);

    }

    @Test
    public void findMatchTest6(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportPinyin( true );
        dfaFilter.setSupportStopWord( true );
        dfaFilter.setStopWord("|");
        dfaFilter.putWord("毛",1);
        List<DFAMatch> ret = dfaFilter.findMatch(null,"m|a-|o",0);
        Assert.assertEquals(0,ret.size());
    }

}
