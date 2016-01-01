package com.jiandanbaoxian.util;

/**
 * Created by JammyQtheLab on 2015/10/26.
 */
public class DiditUtil {


    /**
     * 比如 digit 是245.34，返回1000，并将1000 作为分母，245.34作为分子，得到 百分比
     *
     * @param digit
     * @return
     */
    public static double getMaxInteger(float digit) {
        String intStr = ((int) digit + "");
        Integer intLength = intStr.length();
        return Math.pow(10, intLength);
    }
}
