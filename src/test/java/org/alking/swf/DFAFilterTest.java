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
    public void putWordTest3(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setStopWord(false);
        dfaFilter.setIgnoreCase(false);
        dfaFilter.setPinyin(true);

        dfaFilter.putWord("毛泽东",1);

        Map<Character,DFANode> cMap = dfaFilter.getcMap();
        DFANode node = cMap.get('毛');
        Assert.assertEquals('毛',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('泽');
        Assert.assertEquals('泽',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('东');
        Assert.assertEquals('东',node.c);
        Assert.assertEquals(1,node.score);
        Assert.assertFalse(!node.leaf);

        Map<String,DFANode> sMap = dfaFilter.getsMap();

        node =  sMap.get("MAO");
        Assert.assertEquals('毛',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertEquals("MAO",node.pinyin);
        Assert.assertFalse(node.leaf);

        node = node.getNode("ZE");
        Assert.assertEquals('泽',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertEquals("ZE",node.pinyin);
        Assert.assertFalse(node.leaf);

        node = node.getNode("DONG");
        Assert.assertEquals('东',node.c);
        Assert.assertEquals(1,node.score);
        Assert.assertEquals("DONG",node.pinyin);
        Assert.assertTrue(node.leaf);

    }

    @Test
    public void putWordTest4(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setStopWord(false);
        dfaFilter.setIgnoreCase(false);
        dfaFilter.setPinyin(false);

        dfaFilter.putWord("毛泽东",1);

        Map<Character,DFANode> cMap = dfaFilter.getcMap();
        Assert.assertEquals(1,cMap.size());

        DFANode node = cMap.get('毛');
        Assert.assertEquals('毛',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);
        Assert.assertEquals(1,node.cMap.size());

        node = node.getNode('泽');
        Assert.assertEquals('泽',node.c);
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);
        Assert.assertEquals(1,node.cMap.size());

        node = node.getNode('东');
        Assert.assertEquals('东',node.c);
        Assert.assertEquals(1,node.score);
        Assert.assertFalse(!node.leaf);
        Assert.assertEquals(0,node.cMap.size());

        Map<String,DFANode> sMap = dfaFilter.getsMap();
        Assert.assertTrue(sMap.isEmpty());
    }

    @Test
    public void  putWordTest5(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setStopWord(false);
        dfaFilter.setIgnoreCase(false);
        dfaFilter.putWord("f u c k",1);
        Map<Character,DFANode> cMap = dfaFilter.getcMap();
        Assert.assertEquals(1,cMap.size());

        DFANode node = cMap.get('f');
        Assert.assertEquals('f',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertEquals(0,node.sMap.size());
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode(' ');
        Assert.assertEquals(' ',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertEquals(0,node.sMap.size());
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('u');
        Assert.assertEquals('u',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertEquals(0,node.sMap.size());
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);


        node = node.getNode(' ');
        Assert.assertEquals(' ',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertEquals(0,node.sMap.size());
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('c');
        Assert.assertEquals('c',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertEquals(0,node.sMap.size());
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);


        node = node.getNode(' ');
        Assert.assertEquals(' ',node.c);
        Assert.assertEquals(1,node.cMap.size());
        Assert.assertEquals(0,node.sMap.size());
        Assert.assertEquals(0,node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('k');
        Assert.assertEquals('k',node.c);
        Assert.assertEquals(0,node.cMap.size());
        Assert.assertEquals(0,node.sMap.size());
        Assert.assertEquals(1,node.score);
        Assert.assertTrue(node.leaf);


    }






}
