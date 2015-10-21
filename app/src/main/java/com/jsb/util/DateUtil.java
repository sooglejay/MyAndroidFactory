package com.jsb.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by JammyQtheLab on 2015/10/21.
 */
public class DateUtil {

    /**
     * 按照年、月、天来比较两个日期（不包括时间部分）
     *
     * @param date1
     * @param date2
     * @return －1 小于，0 等于，1 大于
     */
    public static int compareDateByDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        int year1 = calendar1.get(Calendar.YEAR);
        int month1 = calendar1.get(Calendar.MONTH);
        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        int year2 = calendar2.get(Calendar.YEAR);
        int month2 = calendar2.get(Calendar.MONTH);
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);

        if (year1 < year2) {
            return -1;
        } else if (year1 > year2) {
            return 1;
        } else if (month1 < month2) {
            return -1;
        } else if (month1 > month2) {
            return 1;
        } else if (day1 < day2) {
            return -1;
        } else if (day1 > day2) {
            return 1;
        } else {
            return 0;
        }
    }

}
