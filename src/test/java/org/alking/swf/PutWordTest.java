package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class PutWordTest {

    @Test
    public void test1(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("fuck",1);
        Assert.assertEquals(1, dfaFilter.getcMap().size());
        Assert.assertEquals(0, dfaFilter.getsMap().size());

        Map<Character, DFANode> cMap = dfaFilter.getcMap();
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
    public void test2(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("ab",1);
        dfaFilter.putWord("abc",2);

        Assert.assertEquals(1, dfaFilter.getcMap().size());
        Assert.assertEquals(0, dfaFilter.getsMap().size());

        Map<Character, DFANode> cMap = dfaFilter.getcMap();
        DFANode node = cMap.get('a');
        Assert.assertEquals(0, node.score);
        Assert.assertFalse(node.leaf);

        node = cMap.get('a').getNode('b');
        Assert.assertEquals(1, node.score);
        Assert.assertTrue(node.leaf);

        node = cMap.get('a').getNode('b').getNode('c');
        Assert.assertEquals(2, node.score);
        Assert.assertTrue(node.leaf);
    }
}
