package com.jsb.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by JammyQtheLab on 2015/11/2.
 */
public class TimeUtil {
    private static SimpleDateFormat dateFormat_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
    }

    public static String getTimeString(long timeDate)
    {
        return dateFormat_yyyy_mm_dd.format(timeDate);
    }
}
