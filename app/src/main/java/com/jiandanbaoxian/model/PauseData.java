package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.1.9.停保信息对象PauseData.
 */
public class PauseData implements Parcelable {

    private Float totalPauseFee;//累计停保费
    private Float pausePrice;//停保每天的费用
    private Integer orderid;//对应保单号
    private Integer limitday;//限行周几 1 2 3 4 5 6 7  分别对应周日到周六
    private String licenseplate;//对应车牌号
    private String pauseRule;//停保规则
    private String reservedays;//预约日期  如“2015-09-07，2015-09-12”
    private Float usefulPauseFee;//可用停保费
    private Boolean limitPaused;//是否现行暂停过
    private Boolean reservePaused;//是否预约暂停过
    private Long startdate;//保险的有效起始时间
    private Long enddate;//保险的有效结束时间


    public Float getTotalPauseFee() {
        return totalPauseFee;
    }

    public void setTotalPauseFee(Float totalPauseFee) {
        this.totalPauseFee = totalPauseFee;
    }

    public Float getPausePrice() {
        return pausePrice;
    }

    public void setPausePrice(Float pausePrice) {
        this.pausePrice = pausePrice;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getLimitday() {
        return limitday;
    }

    public void setLimitday(Integer limitday) {
        this.limitday = limitday;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public String getPauseRule() {
        return pauseRule;
    }

    public void setPauseRule(String pauseRule) {
        this.pauseRule = pauseRule;
    }

    public String getReservedays() {
        return reservedays;
    }

    public void setReservedays(String reservedays) {
        this.reservedays = reservedays;
    }

    public Float getUsefulPauseFee() {
        return usefulPauseFee;
    }

    public void setUsefulPauseFee(Float usefulPauseFee) {
        this.usefulPauseFee = usefulPauseFee;
    }

    public Boolean getLimitPaused() {
        return limitPaused;
    }

    public void setLimitPaused(Boolean limitPaused) {
        this.limitPaused = limitPaused;
    }

    @Override
    public String toString() {
        return "PauseData{" +
                "totalPauseFee=" + totalPauseFee +
                ", pausePrice=" + pausePrice +
                ", orderid=" + orderid +
                ", limitday=" + limitday +
                ", licenseplate='" + licenseplate + '\'' +
                ", pauseRule='" + pauseRule + '\'' +
                ", reservedays='" + reservedays + '\'' +
                ", usefulPauseFee=" + usefulPauseFee +
                ", limitPaused=" + limitPaused +
                ", reservePaused=" + reservePaused +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                '}';
    }

    public Boolean getReservePaused() {
        return reservePaused;
    }

    public void setReservePaused(Boolean reservePaused) {
        this.reservePaused = reservePaused;
    }

    public static Creator<PauseData> getCREATOR() {
        return CREATOR;
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

    public PauseData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.totalPauseFee);
        dest.writeValue(this.pausePrice);
        dest.writeValue(this.orderid);
        dest.writeValue(this.limitday);
        dest.writeString(this.licenseplate);
        dest.writeString(this.pauseRule);
        dest.writeString(this.reservedays);
        dest.writeValue(this.usefulPauseFee);
        dest.writeValue(this.limitPaused);
        dest.writeValue(this.reservePaused);
        dest.writeValue(this.startdate);
        dest.writeValue(this.enddate);
    }

    protected PauseData(Parcel in) {
        this.totalPauseFee = (Float) in.readValue(Float.class.getClassLoader());
        this.pausePrice = (Float) in.readValue(Float.class.getClassLoader());
        this.orderid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.limitday = (Integer) in.readValue(Integer.class.getClassLoader());
        this.licenseplate = in.readString();
        this.pauseRule = in.readString();
        this.reservedays = in.readString();
        this.usefulPauseFee = (Float) in.readValue(Float.class.getClassLoader());
        this.limitPaused = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.reservePaused = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.startdate = (Long) in.readValue(Long.class.getClassLoader());
        this.enddate = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<PauseData> CREATOR = new Creator<PauseData>() {
        public PauseData createFromParcel(Parcel source) {
            return new PauseData(source);
        }

        public PauseData[] newArray(int size) {
            return new PauseData[size];
        }
    };
}
