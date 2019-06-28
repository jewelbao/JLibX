package com.jewel.libx;

import android.content.Context;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        for(int i = 10; i < 100;){
            i = i + (i>>1);
            System.out.println(i);
        }
    }
}
