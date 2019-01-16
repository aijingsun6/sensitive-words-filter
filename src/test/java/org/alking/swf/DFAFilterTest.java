package org.alking.swf;


import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class DFAFilterTest {


    @Test
    public void putWordTest(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setStopWord(false);
        dfaFilter.setIgnoreCase(false);
        dfaFilter.putWord("fuck",1);

        Map<Character,DFANode> cMap = dfaFilter.getcMap();

        Assert.assertTrue( cMap.containsKey('f'));

        DFANode node = cMap.get('f');
        Assert.assertEquals('f',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('u');
        Assert.assertEquals('u',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('c');
        Assert.assertEquals('c',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('k');
        Assert.assertEquals('k',node.c);
        Assert.assertEquals(1,node.score);
        Assert.assertTrue(node.leaf);

    }

    @Test
    public void putWordTest2(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setStopWord(false);
        dfaFilter.setIgnoreCase(true);
        dfaFilter.putWord("FUCK",1);

        Map<Character,DFANode> cMap = dfaFilter.getcMap();

        DFANode node = cMap.get('f');
        Assert.assertEquals('F',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = cMap.get('F');
        Assert.assertEquals('F',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        DFANode tmp = node;

        node = tmp.getNode('u');
        Assert.assertEquals('U',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = tmp.getNode('U');
        Assert.assertEquals('U',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        tmp = node;

        node = tmp.getNode('c');
        Assert.assertEquals('C',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = tmp.getNode('C');
        Assert.assertEquals('C',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        tmp = node;

        node = tmp.getNode('k');
        Assert.assertEquals('K',node.c);
        Assert.assertEquals(1,node.score);
        Assert.assertTrue(node.leaf);

        node = tmp.getNode('K');
        Assert.assertEquals('K',node.c);
        Assert.assertEquals(1,node.score);
        Assert.assertTrue(node.leaf);

    }


    @Test
    public void  putWordTest3(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setStopWord(false);
        dfaFilter.setIgnoreCase(false);
        dfaFilter.putWord("f u c k",1);
        Map<Character,DFANode> cMap = dfaFilter.getcMap();
        Assert.assertEquals(1,cMap.size());

        DFANode node = cMap.get('f');
        Assert.assertEquals('f',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertNull(node.sMap);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode(' ');
        Assert.assertEquals(' ',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertNull(node.sMap);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('u');
        Assert.assertEquals('u',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertNull(node.sMap);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);


        node = node.getNode(' ');
        Assert.assertEquals(' ',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertNull(node.sMap);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('c');
        Assert.assertEquals('c',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertNull(node.sMap);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);


        node = node.getNode(' ');
        Assert.assertEquals(' ',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertNull(node.sMap);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('k');
        Assert.assertEquals('k',node.c);
        Assert.assertNull(node.cMap);
        Assert.assertNull(node.sMap);
        Assert.assertEquals(1,node.score);
        Assert.assertTrue(node.leaf);


    }






}
