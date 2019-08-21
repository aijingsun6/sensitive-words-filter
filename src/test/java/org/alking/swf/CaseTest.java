package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 大小写匹配
 */
public class CaseTest {

    /**
     * 配置小写，只能匹配小写，其他不匹配
     */
    @Test
    public void caseSensitiveTest() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("fuck", 1);

        List<DFAMatch> ret = dfaFilter.matchWord("abcfuckabc");
        Assert.assertEquals(1, ret.size());

        ret = dfaFilter.matchWord("abcFuckabc");
        Assert.assertEquals(0, ret.size());

        ret = dfaFilter.matchWord("abcFUCKacda");
        Assert.assertEquals(0, ret.size());
    }

    /**
     * 配置大写，只能匹配大写，其他不匹配
     */
    @Test
    public void caseSensitiveTest2() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("FUCK", 1);

        List<DFAMatch> ret = dfaFilter.matchWord("abcFUCKabc");
        Assert.assertEquals(1, ret.size());

        ret = dfaFilter.matchWord("abcfuckcadc");
        Assert.assertEquals(0, ret.size());
    }

    /**
     * 配置大小写混合
     */
    @Test
    public void caseSensitiveTest3() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("Fuck", 1);

        List<DFAMatch> ret = dfaFilter.matchWord("acsdcasdFuckadadc");
        Assert.assertEquals(1, ret.size());

        ret = dfaFilter.matchWord("abcfuckcadc");
        Assert.assertEquals(0, ret.size());
    }

    @Test
    public void ignoreCaseTest() {
        DFAConfig config = new DFAConfig.Builder()
                .setIgnoreCase(true)
                .build();

        DFAFilter dfaFilter = new DFAFilter(config);
        dfaFilter.putWord("fuck", 1);

        String[] src = {
                "fuck",
                "Fuck",
                "fUck",
                "fuCk",
                "fucK",
                "FUck",
                "FuCk",
                "FucK",
                "fUCk",
                "fUcK",
                "fuCK",
                "FUCk",
                "FUcK",
                "FuCK",
                "fUCK",
                "FUCK",
        };
        for (String s : src) {
            List<DFAMatch> ret = dfaFilter.matchWord(s);
            Assert.assertEquals(1, ret.size());
        }

        dfaFilter = new DFAFilter(config);
        dfaFilter.putWord("FUCK", 1);
        for (String s : src) {
            List<DFAMatch> ret = dfaFilter.matchWord(s);
            Assert.assertEquals(1, ret.size());
        }
    }
}
