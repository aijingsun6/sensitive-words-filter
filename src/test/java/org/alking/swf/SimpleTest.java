package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {

    @Test
    public void test1(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportSimple( false );
        dfaFilter.putWord("國家",1);
        Assert.assertEquals(1,dfaFilter.getcMap().size());
    }

    @Test
    public void test2(){

        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportSimple( true );
        dfaFilter.putWord("國家",1);
        Assert.assertEquals(2,dfaFilter.getcMap().size());
    }

    @Test
    public void test3(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportSimple( true );
        dfaFilter.putWord("国家",1);
        Assert.assertEquals(1,dfaFilter.getcMap().size());
    }

}
