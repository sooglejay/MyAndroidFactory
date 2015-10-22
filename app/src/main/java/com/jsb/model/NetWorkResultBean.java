package com.jsb.model;

/**
 * Created by JiangWei on 2015/10/22.
 */
public class NetWorkResultBean<T> {
    public int status;
    public Object message;
    public T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
