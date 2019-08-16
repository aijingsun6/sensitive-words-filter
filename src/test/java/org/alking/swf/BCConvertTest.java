package org.alking.swf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BCConvertTest {

    private void sbc2dbcTest(char half, char full) {
        System.out.println(String.format("sbc2dbcTest %c ===> %c , %d ===> %d", full, half, (int) full, (int) half));
        Assert.assertFalse(half == full);
        Assert.assertEquals(half, BCConvert.sbc2dbc(full));
    }

    private void dbc2sbcTest(char half, char full) {
        System.out.println(String.format("dbc2sbcTest %c ===> %c, %d ===> %d", half, full, (int) half, (int) full));
        Assert.assertFalse(half == full);
        Assert.assertEquals(full, BCConvert.dbc2sbc(half));
    }

    private HashMap<Character, Character> half2full = new HashMap<>();

    @Before
    public void init() {
        half2full.put(' ', '　');
        half2full.put('1', '１');
        half2full.put('a', 'ａ');
    }

    @Test
    public void sbc2dbcTest() {
        for (Map.Entry<Character, Character> entry : this.half2full.entrySet()) {
            sbc2dbcTest(entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void dbc2sbcTest() {
        for (Map.Entry<Character, Character> entry : this.half2full.entrySet()) {
            dbc2sbcTest(entry.getKey(), entry.getValue());
        }
    }

}
