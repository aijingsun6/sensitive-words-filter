package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class DFAFilterTest {

    @Test
    public void matchWordsTest() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("ABC");
        dfaFilter.putStopWord("|");
        Set<WordMatch> matchSet = dfaFilter.matchWords("A|B|C");
        Assert.assertEquals(1, matchSet.size());
        WordMatch math = (WordMatch) matchSet.toArray()[0];
        Assert.assertEquals(0,math.getStart());
        Assert.assertEquals(4,math.getEnd());
        Assert.assertEquals("abc",math.getMatched());

        matchSet = dfaFilter.matchWords("ABBC");
        Assert.assertEquals(1, matchSet.size());
        math = (WordMatch) matchSet.toArray()[0];
        Assert.assertEquals(0,math.getStart());
        Assert.assertEquals(3,math.getEnd());
        Assert.assertEquals("abc",math.getMatched());

    }

    @Test
    public void matchWordsTest2() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("FUCK");
        dfaFilter.putStopWord("|");
        Set<WordMatch> matchSet = dfaFilter.matchWords("FUU |C C|K");
        Assert.assertEquals(1, matchSet.size());
        WordMatch math = (WordMatch) matchSet.toArray()[0];
        Assert.assertEquals(0,math.getStart());
        Assert.assertEquals(9,math.getEnd());
        Assert.assertEquals("fuck",math.getMatched());

    }

    @Test
    public void matchWordsTest3() {
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("干你娘");
        dfaFilter.putStopWord("|#");
        Set<WordMatch> matchSet = dfaFilter.matchWords("幹|你你#孃");
        Assert.assertEquals(1, matchSet.size());
        WordMatch math = (WordMatch) matchSet.toArray()[0];
        Assert.assertEquals(0,math.getStart());
        Assert.assertEquals(5,math.getEnd());
        Assert.assertEquals("干你娘",math.getMatched());
    }

    @Test
    public void matchPinyinTest(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("干你娘");
        dfaFilter.putStopWord("|#");
        Set<WordMatch> matchSet = dfaFilter.matchWords("ganniniang");
        Assert.assertEquals(1, matchSet.size());
        WordMatch math = (WordMatch) matchSet.toArray()[0];
        Assert.assertEquals(0,math.getStart());
        Assert.assertEquals(9,math.getEnd());
        Assert.assertEquals("ganniniang",math.getMatched());

        String src = "GANnini||ang";
        matchSet = dfaFilter.matchWords(src);
        Assert.assertEquals(1, matchSet.size());
        math = (WordMatch) matchSet.toArray()[0];
        Assert.assertEquals(0,math.getStart());
        Assert.assertEquals(11,math.getEnd());
        Assert.assertEquals("ganniniang",math.getMatched());
        Assert.assertEquals("************",dfaFilter.replace(src,matchSet,'*'));
    }

    /**
     * 部分汉字用拼音代替
     */
    @Test
    public void pinyinTest(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("干你娘");
        dfaFilter.putStopWord("|#");
        Set<WordMatch> matchSet = dfaFilter.matchWords("gan你|孃");
        Assert.assertEquals(1, matchSet.size());
        WordMatch math = (WordMatch) matchSet.toArray()[0];
        Assert.assertEquals(0,math.getStart());
        Assert.assertEquals(5,math.getEnd());
        Assert.assertEquals("ganniniang",math.getMatched());

        matchSet = dfaFilter.matchWords("干ni娘");
        Assert.assertEquals(1, matchSet.size());
        math = (WordMatch) matchSet.toArray()[0];
        Assert.assertEquals(0,math.getStart());
        Assert.assertEquals(3,math.getEnd());
        Assert.assertEquals("ganniniang",math.getMatched());
        Assert.assertEquals("****",dfaFilter.replace("干ni娘",'*'));
    }
}
