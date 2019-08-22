package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 简体，繁体测试
 */
public class SimpleTraditionalTest {

    @Test
    public void onlySimpleTest(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("共产党",1);
        List<DFAMatch> matchList = dfaFilter.matchWord("我爱共产党万岁");
        Assert.assertEquals(1,matchList.size());

        matchList = dfaFilter.matchWord("我愛共產黨萬歲");
        Assert.assertEquals(0,matchList.size());
    }

    @Test
    public void onlyTraditionalTest(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("共產黨",1);
        List<DFAMatch> matchList = dfaFilter.matchWord("我爱共产党万岁");
        Assert.assertEquals(0,matchList.size());

        matchList = dfaFilter.matchWord("我愛共產黨萬歲");
        Assert.assertEquals(1,matchList.size());
    }

    @Test
    public void supportTraditionalTest(){
        DFAConfig config = new DFAConfig.Builder()
                .setSupportSimpleTraditional(true)
                .build();
        DFAFilter dfaFilter = new DFAFilter(config);
        dfaFilter.putWord("共产党",1);
        List<DFAMatch> matchList = dfaFilter.matchWord("我爱共产党万岁");
        Assert.assertEquals(1,matchList.size());

        matchList = dfaFilter.matchWord("我愛共產黨萬歲");
        Assert.assertEquals(1,matchList.size());


        dfaFilter = new DFAFilter(config);
        dfaFilter.putWord("共產黨",1);
        matchList = dfaFilter.matchWord("我爱共产党万岁");
        Assert.assertEquals(1,matchList.size());

        matchList = dfaFilter.matchWord("我愛共產黨萬歲");
        Assert.assertEquals(1,matchList.size());
    }

    @Test
    public void bothSimpleTraditionalTest(){
        DFAConfig config = new DFAConfig.Builder()
                .setSupportSimpleTraditional(true)
                .build();
        DFAFilter dfaFilter = new DFAFilter(config);
        dfaFilter.putWord("共产党",1);
        dfaFilter.putWord("共產黨",1);
        List<DFAMatch> matchList = dfaFilter.matchWord("我爱共产党万岁");
        Assert.assertEquals(2,matchList.size());

        matchList = dfaFilter.matchWord("我愛共產黨萬歲");
        Assert.assertEquals(2,matchList.size());
    }

}
