package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SupportDBCTest {

    @Test
    public void test(){
        DFAFilter dfaFilter = new DFAFilter();
        DFAConfig config = new DFAConfig.Builder().setSupportDbc(true).build();
        dfaFilter.setConfig(config);
        dfaFilter.putWord("fuck", 1);

        String src = "ｆｕｃｋ";
        List<DFAMatch> matchList = dfaFilter.matchWord(src);
        String replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals("****",replaced);
    }

    @Test
    public void test2(){
        DFAFilter dfaFilter = new DFAFilter();
        DFAConfig config = new DFAConfig.Builder().setSupportDbc(true).build();
        dfaFilter.setConfig(config);
        dfaFilter.putWord("ｆｕｃｋ", 1);

        String src = "fuck";
        List<DFAMatch> matchList = dfaFilter.matchWord(src);
        String replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals("****",replaced);
    }

    /**
     * 如果同一个屏蔽词即配置了半角也配置了全角，那就返回匹配了2个吧
     */
    @Test
    public void test3(){
        DFAFilter dfaFilter = new DFAFilter();
        DFAConfig config = new DFAConfig.Builder().setSupportDbc(true).build();
        dfaFilter.setConfig(config);
        dfaFilter.putWord("ｆｕｃｋ", 1);
        dfaFilter.putWord("fuck", 1);

        String src = "fuck";
        List<DFAMatch> matchList = dfaFilter.matchWord(src);
        String replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(2, matchList.size());
        Assert.assertEquals("****",replaced);
    }
}
