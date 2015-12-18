package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.5.1.加班险订单Overtimeordertable
 */
public class Overtimeordertable implements Parcelable {

    private Integer id;//int	加班险订单编号	Y
    private Integer payed;//int	是否支付  0未付 1已付	Y
    private Integer userid;//int	用户编号（被保险人）	Y
    private Integer charegeid;//int	支付对象id
    private Integer compensated;//int	是否赔付 0未赔 1已赔	Y

    @Override
    public String toString() {
        return "Overtimeordertable{" +
                "id=" + id +
                ", payed=" + payed +
                ", userid=" + userid +
                ", charegeid=" + charegeid +
                ", compensated=" + compensated +
                ", money=" + money +
                ", coverage=" + coverage +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", reportdate=" + reportdate +
                ", companyaddress='" + companyaddress + '\'' +
                ", companyname='" + companyname + '\'' +
                ", homeaddress='" + homeaddress + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", super_status=" + super_status +
                '}';
    }

    public Float getCoverage() {
        return coverage;
    }

    public void setCoverage(Float coverage) {
        this.coverage = coverage;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    private Float money;//Float	金额	Y  你给保险公司
    private Float coverage;//Float	金额	Y    保险公司给你
    private Long startdate;//起效时间	Y
    private Long enddate;//timestamp	结束时间	Y
    private Long reportdate;//timestamp	报案时间
    private String companyaddress;//String	公司地址	Y
    private String companyname;//String	公司名字	Y
    private String homeaddress;//String	家庭地址
    //    lat	Float	公司纬度
//    lng	Float	公司经度
    private Float lat;
    private Float lng;
    private int super_status = 0;//妥协的设计，增加一个 超级状态变量，作为 客户端 ui 设计的辅助变量

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public int getSuper_status() {
        return super_status;
    }

    public void setSuper_status(int super_status) {
        this.super_status = super_status;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Long getEnddate() {
        return enddate;
    }

    public Long getReportdate() {
        return reportdate;
    }

    public void setReportdate(Long reportdate) {
        this.reportdate = reportdate;
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress;
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public Long getStartdate() {
        return startdate;
    }

    public void setStartdate(Long startdate) {
        this.startdate = startdate;
    }

    public static Creator<Overtimeordertable> getCREATOR() {
        return CREATOR;
    }

    public void setEnddate(Long enddate) {

        this.enddate = enddate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPayed() {
        return payed;
    }

    public void setPayed(Integer payed) {
        this.payed = payed;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCharegeid() {
        return charegeid;
    }

    public void setCharegeid(Integer charegeid) {
        this.charegeid = charegeid;
    }

    public Integer getCompensated() {
        return compensated;
    }

    public void setCompensated(Integer compensated) {
        this.compensated = compensated;
    }

    public Float getMoney() {
        return money;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public Overtimeordertable() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.payed);
        dest.writeValue(this.userid);
        dest.writeValue(this.charegeid);
        dest.writeValue(this.compensated);
        dest.writeValue(this.money);
        dest.writeValue(this.coverage);
        dest.writeValue(this.startdate);
        dest.writeValue(this.enddate);
        dest.writeValue(this.reportdate);
        dest.writeString(this.companyaddress);
        dest.writeString(this.companyname);
        dest.writeString(this.homeaddress);
        dest.writeValue(this.lat);
        dest.writeValue(this.lng);
        dest.writeInt(this.super_status);
    }

    protected Overtimeordertable(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.payed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.charegeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.compensated = (Integer) in.readValue(Integer.class.getClassLoader());
        this.money = (Float) in.readValue(Float.class.getClassLoader());
        this.coverage = (Float) in.readValue(Float.class.getClassLoader());
        this.startdate = (Long) in.readValue(Long.class.getClassLoader());
        this.enddate = (Long) in.readValue(Long.class.getClassLoader());
        this.reportdate = (Long) in.readValue(Float.class.getClassLoader());
        this.companyaddress = in.readString();
        this.companyname = in.readString();
        this.homeaddress = in.readString();
        this.lat = (Float) in.readValue(Float.class.getClassLoader());
        this.lng = (Float) in.readValue(Float.class.getClassLoader());
        this.super_status = in.readInt();
    }

    public static final Creator<Overtimeordertable> CREATOR = new Creator<Overtimeordertable>() {
        public Overtimeordertable createFromParcel(Parcel source) {
            return new Overtimeordertable(source);
        }

        public Overtimeordertable[] newArray(int size) {
            return new Overtimeordertable[size];
        }
    };
}
