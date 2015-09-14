package com.jsb.event;

/**
 * Created by Administrator on 2015/9/13.
 */
public class BooleanEvent {
    private boolean msg;

    public BooleanEvent(boolean msg) {
        this.msg = msg;
    }


    public boolean getMsg() {
        return msg;
    }

    public void setMsg(boolean msg) {
        this.msg = msg;
    }
}
