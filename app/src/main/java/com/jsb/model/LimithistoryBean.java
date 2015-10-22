package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *  发送时机	用户点击累计停保费时
 */
public class LimithistoryBean implements Parcelable {

    private Integer limitdate;
    private Integer userid;
    private Integer totalfee;
    private Long enddate;
    private Long startdate;
    private Integer days;
    private Integer dayprice;
    private Integer status;
    private Integer orderid;
    private Integer id;

    public Integer getLimitdate() {
        return limitdate;
    }

    public void setLimitdate(Integer limitdate) {
        this.limitdate = limitdate;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(Integer totalfee) {
        this.totalfee = totalfee;
    }

    public Long getEnddate() {
        return enddate;
    }

    public void setEnddate(Long enddate) {
        this.enddate = enddate;
    }

    public Long getStartdate() {
        return startdate;
    }

    public void setStartdate(Long startdate) {
        this.startdate = startdate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getDayprice() {
        return dayprice;
    }

    public void setDayprice(Integer dayprice) {
        this.dayprice = dayprice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.limitdate);
        dest.writeValue(this.userid);
        dest.writeValue(this.totalfee);
        dest.writeValue(this.enddate);
        dest.writeValue(this.startdate);
        dest.writeValue(this.days);
        dest.writeValue(this.dayprice);
        dest.writeValue(this.status);
        dest.writeValue(this.orderid);
        dest.writeValue(this.id);
    }

    public LimithistoryBean() {
    }

    protected LimithistoryBean(Parcel in) {
        this.limitdate = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalfee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.enddate = (Long) in.readValue(Long.class.getClassLoader());
        this.startdate = (Long) in.readValue(Long.class.getClassLoader());
        this.days = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dayprice = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<LimithistoryBean> CREATOR = new Parcelable.Creator<LimithistoryBean>() {
        public LimithistoryBean createFromParcel(Parcel source) {
            return new LimithistoryBean(source);
        }

        public LimithistoryBean[] newArray(int size) {
            return new LimithistoryBean[size];
        }
    };
}
