package com.example.taoying.testapplication;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";

    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
        System.out.println("a");
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("b");
            }
        }).run();
        System.out.println("c");
    }
}