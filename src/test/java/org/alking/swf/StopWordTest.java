package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class StopWordTest {

    @Test
    public void test1() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("fuck", 1);

        Map<Character, DFANode> cMap = dfaFilter.getcMap();

        Assert.assertTrue(cMap.containsKey('f'));

        DFANode node = cMap.get('f');
        Assert.assertEquals('f', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('u');
        Assert.assertEquals('u', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('c');
        Assert.assertEquals('c', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('k');
        Assert.assertEquals('k', node.c);
        Assert.assertEquals(1, node.score);
        Assert.assertTrue(node.leaf);
    }

    @Test
    public void test2() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportStopWord( true );
        dfaFilter.setStopWord("|");
        dfaFilter.putWord("f|u|c|k", 1);

        Map<Character, DFANode> cMap = dfaFilter.getcMap();

        Assert.assertTrue(cMap.containsKey('f'));

        DFANode node = cMap.get('f');
        Assert.assertEquals('f', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('u');
        Assert.assertEquals('u', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('c');
        Assert.assertEquals('c', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('k');
        Assert.assertEquals('k', node.c);
        Assert.assertEquals(1, node.score);
        Assert.assertTrue(node.leaf);
    }

    @Test
    public void test3() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("|",1);
        Map<Character, DFANode> cMap = dfaFilter.getcMap();
        Assert.assertEquals(1, cMap.size());
    }

    @Test
    public void test4() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportStopWord( true );
        dfaFilter.setStopWord("|");
        dfaFilter.putWord("|",1);
        Assert.assertEquals(0,dfaFilter.getcMap().size());
        Assert.assertEquals(0,dfaFilter.getsMap().size());
    }

    @Test
    public void test5(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportStopWord( true );
        dfaFilter.setStopWord("|");
        dfaFilter.putWord("f|u|c|k", 1);

        Map<Character, DFANode> cMap = dfaFilter.getcMap();

        Assert.assertTrue(cMap.containsKey('f'));

        DFANode node = cMap.get('f');
        Assert.assertEquals('f', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('u');
        Assert.assertEquals('u', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('c');
        Assert.assertEquals('c', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('k');
        Assert.assertEquals('k', node.c);
        Assert.assertEquals(1, node.score);
        Assert.assertTrue(node.leaf);
    }

    @Test
    public void test6(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportStopWord( true );
        dfaFilter.setStopWord("|# ");
        dfaFilter.putWord("f u|c#k", 1);

        Map<Character, DFANode> cMap = dfaFilter.getcMap();

        Assert.assertTrue(cMap.containsKey('f'));

        DFANode node = cMap.get('f');
        Assert.assertEquals('f', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('u');
        Assert.assertEquals('u', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('c');
        Assert.assertEquals('c', node.c);
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = node.getNode('k');
        Assert.assertEquals('k', node.c);
        Assert.assertEquals(1, node.score);
        Assert.assertTrue(node.leaf);
    }
}
