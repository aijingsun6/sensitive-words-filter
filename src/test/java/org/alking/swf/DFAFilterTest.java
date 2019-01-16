package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DFAFilterTest {

    @Test
    public void findMatchTest1(){

        DFAFilter dfaFilter = new DFAFilter();

        dfaFilter.putWord("fuck",1);

        List<DFAFilter.Match> ret = dfaFilter.findMatch(null,"fuck",0);

        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);
        Assert.assertEquals('f',ret.get(0).node.c);

        DFANode node = null;

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"fuck",1);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"fuck",2);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"fuck",3);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"fuck",4);
        Assert.assertEquals(0,ret.size());

    }

    @Test
    public void findMatchTest2(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportStopWord( true );
        dfaFilter.setStopWord("|");

        dfaFilter.putWord("fuck",1);

        List<DFAFilter.Match> ret;
        ret = dfaFilter.findMatch(null,"f|u|c|k",0);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);
        Assert.assertEquals('f',ret.get(0).node.c);

        DFANode node = null;

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"f|u|c|k",1);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);
        Assert.assertEquals('f',ret.get(0).node.c);

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"f|u|c|k",2);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);
        Assert.assertEquals('u',ret.get(0).node.c);

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"f|u|c|k",3);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);
        Assert.assertEquals('u',ret.get(0).node.c);

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"f|u|c|k",4);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);
        Assert.assertEquals('c',ret.get(0).node.c);

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"f|u|c|k",5);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);
        Assert.assertEquals('c',ret.get(0).node.c);

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"f|u|c|k",6);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);
        Assert.assertEquals('k',ret.get(0).node.c);
        Assert.assertTrue(ret.get(0).node.leaf);

    }

    @Test
    public void findMatchTest3(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setIgnoreCase(true);

        dfaFilter.putWord("fuck",1);

        List<DFAFilter.Match> ret = dfaFilter.findMatch(null,"FUCK",0);

        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);
        Assert.assertEquals('f',ret.get(0).node.c);

        DFANode node = null;

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"FUCK",1);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"FUCK",2);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"FUCK",3);
        Assert.assertEquals(1,ret.size());
        Assert.assertEquals(1, ret.get(0).size);

        node = ret.get(0).node;
        ret = dfaFilter.findMatch(node,"FUCK",4);
        Assert.assertEquals(0,ret.size());

    }

}
