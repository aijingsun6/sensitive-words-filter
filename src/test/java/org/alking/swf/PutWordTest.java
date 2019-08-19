package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

public class PutWordTest {

    @Test
    public void test1() {

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("fuck", 1);
        DFANode root = dfaFilter.getRoot();

        Assert.assertEquals(1, root.getcMap().size());
        Assert.assertEquals(0, root.getsMap().size());

        DFANode node = root.getNode('f');
        Assert.assertEquals('f', node.ch);
        Assert.assertEquals(null, node.ext);
        Assert.assertFalse(node.leaf);

        node = node.getNode('u');
        Assert.assertEquals('u', node.ch);
        Assert.assertEquals(null, node.ext);
        Assert.assertFalse(node.leaf);

        node = node.getNode('c');
        Assert.assertEquals('c', node.ch);
        Assert.assertEquals(null, node.ext);
        Assert.assertFalse(node.leaf);

        node = node.getNode('k');
        Assert.assertEquals('k', node.ch);
        Assert.assertEquals(1, node.ext);
        Assert.assertTrue(node.leaf);

    }

    @Test
    public void test2() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("ab", 1);
        dfaFilter.putWord("abc", 2);
        DFANode root = dfaFilter.getRoot();
        Assert.assertEquals(1, root.getcMap().size());
        Assert.assertEquals(0, root.getsMap().size());

        DFANode node = root.getNode('a');
        Assert.assertEquals(null, node.ext);
        Assert.assertFalse(node.leaf);

        node = root.getNode('a').getNode('b');
        Assert.assertEquals(1, node.ext);
        Assert.assertTrue(node.leaf);

        node = root.getNode('a').getNode('b').getNode('c');
        Assert.assertEquals(2, node.ext);
        Assert.assertTrue(node.leaf);
    }

    @Test
    public void overrideTest() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("fuck", 1);
        dfaFilter.putWord("fuck", 2);
        DFANode root = dfaFilter.getRoot();
        DFANode left = root.getNode('f').getNode('u').getNode('c').getNode('k');
        Assert.assertEquals(2, left.ext);
    }
}
