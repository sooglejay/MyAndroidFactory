package com.jsb.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/20.
 */
public class FatherBean {
    private List<SonBean> daysList = new ArrayList<>();
    public List<SonBean> getDaysList() {
        return daysList;
    }
    public void setDaysList(List<SonBean> daysList) {
        this.daysList = daysList;
    }
}