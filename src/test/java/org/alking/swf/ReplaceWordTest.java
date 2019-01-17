package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ReplaceWordTest {

    @Test
    public void test1(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.putWord("fuck",1);
        final String origin = "i fuck u";
        List<DFAMatch> matches = dfaFilter.matchWord(origin);
        Assert.assertEquals("i **** u", dfaFilter.replaceWord( origin,matches,'*' ));
    }

    @Test
    public void test2(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportStopWord( true );
        dfaFilter.setStopWord("| ");
        dfaFilter.putWord("fuck",1);
        final String origin = "i f|uc|k u";
        List<DFAMatch> matches = dfaFilter.matchWord(origin);
        Assert.assertEquals("i ****** u", dfaFilter.replaceWord( origin,matches,'*' ));
    }

    @Test
    public void test3(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportPinyin(true);
        dfaFilter.putWord("毛主席",1);
        final String origin = "我爱maozhuxi";
        List<DFAMatch> matches = dfaFilter.matchWord(origin);
        Assert.assertEquals("我爱********", dfaFilter.replaceWord( origin,matches,'*' ));
    }

    @Test
    public void test4(){
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.setSupportPinyin(true);
        dfaFilter.putWord("毛主席",1);
        final String origin = "我爱mao主xi";
        List<DFAMatch> matches = dfaFilter.matchWord(origin);
        Assert.assertEquals("我爱******", dfaFilter.replaceWord( origin,matches,'*' ));
    }
}
