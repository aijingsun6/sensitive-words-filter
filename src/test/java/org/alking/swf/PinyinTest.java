package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

public class PinyinTest {

    @Test
    public void test1(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportPinyin(false);
        dfaFilter.putWord("我",1);
        Assert.assertEquals(1, dfaFilter.getcMap().size());
        Assert.assertEquals(0, dfaFilter.getsMap().size());
    }

    @Test
    public void test2(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportPinyin(false);
        dfaFilter.putWord("fuck",1);
        Assert.assertEquals(1, dfaFilter.getcMap().size());
        Assert.assertEquals(0, dfaFilter.getsMap().size());
    }

    @Test
    public void test3(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportPinyin(true);
        dfaFilter.putWord("我",1);
        Assert.assertEquals(1, dfaFilter.getcMap().size());
        Assert.assertEquals(1, dfaFilter.getsMap().size());
        DFANode node = dfaFilter.getcMap().get('我');
        DFANode node2 = dfaFilter.getsMap().get("WO");
        Assert.assertEquals(node,node2);
    }

    @Test
    public void test4(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportPinyin(true);
        dfaFilter.putWord("fuck我",1);
        DFANode node = dfaFilter.getcMap().get('f').getNode('u').getNode('c').getNode('k').getNode('我');
        Assert.assertTrue( node.leaf );
        Assert.assertEquals(1,node.score);

        node = dfaFilter.getcMap().get('f').getNode('u').getNode('c').getNode('k').getNode("WO");
        Assert.assertTrue( node.leaf );
        Assert.assertEquals(1,node.score);

    }
}
