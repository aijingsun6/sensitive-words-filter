package org.alking.swf;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiTest {

    private void ensureContainsWord(List<DFAMatch> matchList, String[] words) {
        Map<String, DFAMatch> map = new HashMap<>();
        for (DFAMatch match : matchList) {
            map.put(match.getWord(), match);
        }
        for (String word : words) {
            Assert.assertTrue(map.containsKey(word));
        }
    }

    @Test
    public void test1() {

        DFAConfig config = new DFAConfig.Builder()
                .setSupportStopWord(true)
                .setStopWord("、，。")
                .build();
        DFAFilter dfaFilter = new DFAFilter(config);
        String [] words = {
                "江泽民",
                "李鹏",
                "朱溶基",
                "主席",
                "北京途中",
                "坠毁飞机"
        };
        for(String s: words){
            dfaFilter.putWord(s,1);
        }

        String src = "话说江泽民、李鹏、朱溶基，还有一名少先队员，一起坐飞机北京，途中飞机严重故障，即将坠毁。飞机上只有三个降落伞。大件商议决定，主席先跳。江主席跳伞之后，大家还在剩下两个降落伞该怎么分，结果李鹏二话不说就抢先跳了。这时，朱溶基对少先队员说：小朋友，还剩一个降落伞，你是祖国的花朵，是祖国的未来，你拿了跳伞逃生吧。少先队员却说：朱爷爷，没关系，还剩两个降落伞呢，刚才我看见李鹏爷爷抓了我书包就跳了。";

        List<DFAMatch> matchList = dfaFilter.matchWord(src);
        ensureContainsWord(matchList,words);
    }


}
