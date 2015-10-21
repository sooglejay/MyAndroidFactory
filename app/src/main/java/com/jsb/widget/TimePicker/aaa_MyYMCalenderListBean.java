package com.jsb.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/20.
 */
public class aaa_MyYMCalenderListBean {
    private List<aaa_MyDayCalenderListBean> daysList = new ArrayList<>();
    public List<aaa_MyDayCalenderListBean> getDaysList() {
        return daysList;
    }
    public void setDaysList(List<aaa_MyDayCalenderListBean> daysList) {
        this.daysList = daysList;
    }
}
