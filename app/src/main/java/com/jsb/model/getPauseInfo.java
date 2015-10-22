package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * 7.7.停保
 7.7.1.获取累计停保费和可提现余额
 * Created by JammyQtheLab on 2015/10/22.
 * 用户停保时，获取累计停保费用、可用费用和停保规则
 */
public class getPauseInfo implements Parcelable {

    private Integer totalPauseFee;
    private Integer orderid;
    private Integer pausePrice;
    private Integer limitday;
    private Integer usefulPauseFee;
    private Boolean limitPaused;
    private Boolean reservePaused;
    private String licenseplate;
    private String reservedays;
    private String pauseRule;
    private Long startdate;
    private Long enddate;

    public Integer getTotalPauseFee() {
        return totalPauseFee;
    }

    public void setTotalPauseFee(Integer totalPauseFee) {
        this.totalPauseFee = totalPauseFee;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getPausePrice() {
        return pausePrice;
    }

    public void setPausePrice(Integer pausePrice) {
        this.pausePrice = pausePrice;
    }

    public Integer getLimitday() {
        return limitday;
    }

    public void setLimitday(Integer limitday) {
        this.limitday = limitday;
    }

    public Integer getUsefulPauseFee() {
        return usefulPauseFee;
    }

    public void setUsefulPauseFee(Integer usefulPauseFee) {
        this.usefulPauseFee = usefulPauseFee;
    }

    public Boolean getLimitPaused() {
        return limitPaused;
    }

    public void setLimitPaused(Boolean limitPaused) {
        this.limitPaused = limitPaused;
    }

    public Boolean getReservePaused() {
        return reservePaused;
    }

    public void setReservePaused(Boolean reservePaused) {
        this.reservePaused = reservePaused;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public String getReservedays() {
        return reservedays;
    }

    public void setReservedays(String reservedays) {
        this.reservedays = reservedays;
    }

    public String getPauseRule() {
        return pauseRule;
    }

    public void setPauseRule(String pauseRule) {
        this.pauseRule = pauseRule;
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
        dest.writeValue(this.totalPauseFee);
        dest.writeValue(this.orderid);
        dest.writeValue(this.pausePrice);
        dest.writeValue(this.limitday);
        dest.writeValue(this.usefulPauseFee);
        dest.writeValue(this.limitPaused);
        dest.writeValue(this.reservePaused);
        dest.writeString(this.licenseplate);
        dest.writeString(this.reservedays);
        dest.writeString(this.pauseRule);
        dest.writeValue(this.startdate);
        dest.writeValue(this.enddate);
    }

    public getPauseInfo() {
    }

    protected getPauseInfo(Parcel in) {
        this.totalPauseFee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pausePrice = (Integer) in.readValue(Integer.class.getClassLoader());
        this.limitday = (Integer) in.readValue(Integer.class.getClassLoader());
        this.usefulPauseFee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.limitPaused = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.reservePaused = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.licenseplate = in.readString();
        this.reservedays = in.readString();
        this.pauseRule = in.readString();
        this.startdate = (Long) in.readValue(Long.class.getClassLoader());
        this.enddate = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<getPauseInfo> CREATOR = new Parcelable.Creator<getPauseInfo>() {
        public getPauseInfo createFromParcel(Parcel source) {
            return new getPauseInfo(source);
        }

        public getPauseInfo[] newArray(int size) {
            return new getPauseInfo[size];
        }
    };
}
