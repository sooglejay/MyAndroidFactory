package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class Reservepausetable implements Parcelable {


    private Integer id;//停保编号	Y
    private Integer day;//int	总共停的天数	Y
    private Integer status;//是否有效  0  否  1是	Y
    private String reservedays;
    private Integer userid;//用户id(被保险人)	Y
    private Integer totalfee;//float	产生的总费用	Y
    private Integer dayprice;//float	每天的价格	Y
    private Integer orderid;//对应保险单的编号	Y
    private Long starttime;//起效时间	Y
    private Long endtime;//结束时间	Y

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReservedays() {
        return reservedays;
    }

    public void setReservedays(String reservedays) {
        this.reservedays = reservedays;
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

    public Integer getDayprice() {
        return dayprice;
    }

    public void setDayprice(Integer dayprice) {
        this.dayprice = dayprice;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.day);
        dest.writeValue(this.status);
        dest.writeString(this.reservedays);
        dest.writeValue(this.userid);
        dest.writeValue(this.totalfee);
        dest.writeValue(this.dayprice);
        dest.writeValue(this.orderid);
        dest.writeValue(this.starttime);
        dest.writeValue(this.endtime);
    }

    public Reservepausetable() {
    }

    protected Reservepausetable(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.day = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.reservedays = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalfee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dayprice = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.starttime = (Long) in.readValue(Long.class.getClassLoader());
        this.endtime = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<Reservepausetable> CREATOR = new Parcelable.Creator<Reservepausetable>() {
        public Reservepausetable createFromParcel(Parcel source) {
            return new Reservepausetable(source);
        }

        public Reservepausetable[] newArray(int size) {
            return new Reservepausetable[size];
        }
    };
}
