package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.7.1.限行停保信息Limitpausetable
 */
public class Limitpausetable implements Parcelable {

    private Integer userid;//用户id(被保险人)	Y
    private Integer id;//int	停保编号	Y
    private Integer orderid;//对应保险单的编号	Y
    private Integer limitdate;//停保日子（每周周几） 1 2 3 4 5 6 7  分别对应周日到周六	Y
    private Integer days;//总共停的天数	Y
    private Integer status;//是否有效  0  否  1是	Y
    private Float dayprice;//每天的价格	Y
    private Float totalfee;//产生的总费用	Y
    private Long startdate;//	起效时间	Y
    private Long enddate;//    结束时间	Y

    @Override
    public String toString() {
        return "Limitpausetable{" +
                "userid=" + userid +
                ", id=" + id +
                ", orderid=" + orderid +
                ", limitdate=" + limitdate +
                ", days=" + days +
                ", status=" + status +
                ", dayprice=" + dayprice +
                ", totalfee=" + totalfee +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                '}';
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getLimitdate() {
        return limitdate;
    }

    public void setLimitdate(Integer limitdate) {
        this.limitdate = limitdate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getDayprice() {
        return dayprice;
    }

    public void setDayprice(Float dayprice) {
        this.dayprice = dayprice;
    }

    public Float getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(Float totalfee) {
        this.totalfee = totalfee;
    }

    public Long getStartdate() {
        return startdate;
    }

    public void setStartdate(Long startdate) {
        this.startdate = startdate;
    }

    public Long getEnddate() {
        return enddate;
    }

    public void setEnddate(Long enddate) {
        this.enddate = enddate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.userid);
        dest.writeValue(this.id);
        dest.writeValue(this.orderid);
        dest.writeValue(this.limitdate);
        dest.writeValue(this.days);
        dest.writeValue(this.status);
        dest.writeValue(this.dayprice);
        dest.writeValue(this.totalfee);
        dest.writeValue(this.startdate);
        dest.writeValue(this.enddate);
    }

    public Limitpausetable() {
    }

    protected Limitpausetable(Parcel in) {
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.limitdate = (Integer) in.readValue(Integer.class.getClassLoader());
        this.days = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dayprice = (Float) in.readValue(Float.class.getClassLoader());
        this.totalfee = (Float) in.readValue(Float.class.getClassLoader());
        this.startdate = (Long) in.readValue(Long.class.getClassLoader());
        this.enddate = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<Limitpausetable> CREATOR = new Parcelable.Creator<Limitpausetable>() {
        public Limitpausetable createFromParcel(Parcel source) {
            return new Limitpausetable(source);
        }

        public Limitpausetable[] newArray(int size) {
            return new Limitpausetable[size];
        }
    };
}
