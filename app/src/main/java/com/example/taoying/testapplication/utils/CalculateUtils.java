package com.example.taoying.testapplication.utils;

import java.math.BigDecimal;

public class CalculateUtils {
    public static float div(float v1, float v2, int scale){
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
