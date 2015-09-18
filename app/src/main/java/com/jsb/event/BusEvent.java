package com.jsb.event;

/**
 * Created by Administrator on 2015/9/13.
 */
public class BusEvent {
    public static final int  MSG_INT_TIME = 1000;
    private String start_time;
    private String end_time;
    private String interval_time;

    private int msg=0;

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public BusEvent(int msg) {
        this.msg = msg;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getInterval_time() {
        return interval_time;
    }

    public void setInterval_time(String interval_time) {
        this.interval_time = interval_time;
    }
}
