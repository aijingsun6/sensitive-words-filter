package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PinyinTest {

    @Test
    public void pinyinTest() {

        DFAFilter dfaFilter = new DFAFilter();
        DFAConfig config = new DFAConfig.Builder().setSupportPinyin(true).build();
        dfaFilter.setConfig(config);
        dfaFilter.putWord("共产党", 1);

        String src = "gongchandang";
        List<DFAMatch> matchList = dfaFilter.matchWord(src);
        String replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals("************", replaced);

        src = "我爱gongchandang";
        matchList = dfaFilter.matchWord(src);
        replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals("我爱************", replaced);


        src = "我爱gongchandang哈哈";
        matchList = dfaFilter.matchWord(src);
        replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals("我爱************哈哈", replaced);
    }

    @Test
    public void pinyinWithStopWordTest() {
        DFAFilter dfaFilter = new DFAFilter();
        DFAConfig config = new DFAConfig.Builder()
                .setSupportPinyin(true)
                .setSupportStopWord(true)
                .setStopWord("|")
                .build();
        dfaFilter.setConfig(config);
        dfaFilter.putWord("共产党", 1);

        String src = "gong|chan|dang";
        List<DFAMatch> matchList = dfaFilter.matchWord(src);
        String replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals("**************", replaced);

        src = "gong|CHAN|dang";
        matchList = dfaFilter.matchWord(src);
        replaced = dfaFilter.replaceWord(src, matchList, '*');
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals("**************", replaced);

    }

    /**
     * 习近平
     */
    @Test
    public void xijinpingTest() {
        DFAFilter dfaFilter = new DFAFilter();
        DFAConfig config = new DFAConfig.Builder()
                .setSupportPinyin(true)
                .setSupportStopWord(true)
                .setStopWord("|")
                .build();
        dfaFilter.setConfig(config);
        String word = "习近平";
        dfaFilter.putWord(word, 1);

        String src = "习近平";
        List<DFAMatch> matchList = dfaFilter.matchWord(src);
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals(word, matchList.get(0).getWord());

        src = "习近ping";
        matchList = dfaFilter.matchWord(src);
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals(word, matchList.get(0).getWord());

        src = "习jin平";
        matchList = dfaFilter.matchWord(src);
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals(word, matchList.get(0).getWord());

        src = "习JIN平";
        matchList = dfaFilter.matchWord(src);
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals(word, matchList.get(0).getWord());

        src = "XI近平";
        matchList = dfaFilter.matchWord(src);
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals(word, matchList.get(0).getWord());

        src = "XIJINPING";
        matchList = dfaFilter.matchWord(src);
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals(word, matchList.get(0).getWord());

        src = "xijinping";
        matchList = dfaFilter.matchWord(src);
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals(word, matchList.get(0).getWord());

        src = "x|ijinping";
        matchList = dfaFilter.matchWord(src);
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals(word, matchList.get(0).getWord());

        // 这个 已经被分隔成这个样子了
        src = "x|i|j|i|n|p|i|n|g";
        matchList = dfaFilter.matchWord(src);
        Assert.assertEquals(1, matchList.size());
        Assert.assertEquals(word, matchList.get(0).getWord());
    }


//
//    @Test
//    public void test2(){
//        DFAFilter dfaFilter = new DFAFilter();
//        dfaFilter.setSupportPinyin(false);
//        dfaFilter.putWord("fuck",1);
//        Assert.assertEquals(1, dfaFilter.getcMap().size());
//        Assert.assertEquals(0, dfaFilter.getsMap().size());
//    }
//
//    @Test
//    public void test3(){
//        DFAFilter dfaFilter = new DFAFilter();
//        dfaFilter.setSupportPinyin(true);
//        dfaFilter.putWord("我",1);
//        Assert.assertEquals(1, dfaFilter.getcMap().size());
//        Assert.assertEquals(1, dfaFilter.getsMap().size());
//        DFANode node = dfaFilter.getcMap().get('我');
//        DFANode node2 = dfaFilter.getsMap().get("WO");
//        Assert.assertEquals(node,node2);
//    }
//
//    @Test
//    public void test4(){
//        DFAFilter dfaFilter = new DFAFilter();
//        dfaFilter.setSupportPinyin(true);
//        dfaFilter.putWord("fuck我",1);
//        DFANode node = dfaFilter.getcMap().get('f').getNode('u').getNode('c').getNode('k').getNode('我');
//        Assert.assertTrue( node.leaf );
//        Assert.assertEquals(1,node.score);
//
//        node = dfaFilter.getcMap().get('f').getNode('u').getNode('c').getNode('k').getNode("WO");
//        Assert.assertTrue( node.leaf );
//        Assert.assertEquals(1,node.score);
//
//    }
}
