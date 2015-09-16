package com.jsb.util;

import com.jsb.R;
import com.jsb.widget.DatePicker.bizs.themes.DPTheme;


/**
 * Created by Administrator on 2015/9/15.
 */
public class TimePickerTheme extends DPTheme {
    @Override
    public int colorBG() {
        return 0xFFFFFFFF;
    }

    @Override
    public int colorBGCircle() {
        return 0x44000000;
    }

    @Override
    public int colorTitleBG() {
        return R.color.time_picker_title_bar_color;
    }

    @Override
    public int colorTitle() {
        return 0xEEFFFFFF;
    }

    @Override
    public int colorToday() {
        return 0x88F37B7A;
    }

    @Override
    public int colorG() {
        return 0xEE333333;
    }

    @Override
    public int colorF() {
        return 0xEEC08AA4;
    }

    @Override
    public int colorWeekend() {
        return 0xEEF78082;
    }

    @Override
    public int colorHoliday() {
        return 0x80FED6D6;
    }

}
