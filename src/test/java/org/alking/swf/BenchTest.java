package org.alking.swf;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class BenchTest {


//    private void runTest(final DFAFilter dfaFilter, final List<String> words, final String target, final int times) {
//
//        for (String word : words) {
//            dfaFilter.putWord(word, 1);
//        }
//        long start = System.currentTimeMillis();
//
//        for (int i = 0; i < times; i++) {
//            dfaFilter.matchWord(target);
//        }
//
//        long end = System.currentTimeMillis();
//
//        final String msg = String.format("word size %d,target size %d,times %d,cost %d ms", words.size(), target.length(), times, (end - start));
//        System.out.println(msg);
//    }
//
//    @Test
//    public void test1() {
//        DFAFilter dfaFilter = new DFAFilter();
//        runTest(dfaFilter, Arrays.asList("fuck"),"fuck",10000);
//    }
//
//    @Test
//    public void test2() {
//        DFAFilter dfaFilter = new DFAFilter();
//        runTest(dfaFilter, Arrays.asList("fuck"),"fuck",10000*100);
//    }
//
//    @Test
//    public void test3(){
//        DFAFilter dfaFilter = new DFAFilter();
//        List<String> words = Arrays.asList("a","ab","abc","abcd","abcde","abcdef","abcdefg");
//        final String target = "abcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefg";
//        runTest(dfaFilter,words,target,10000);
//
//    }


}
