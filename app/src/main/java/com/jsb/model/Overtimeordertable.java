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
    private Float money;//float	金额	Y
    private Float startdate;//起效时间	Y
    private Float enddate;//timestamp	结束时间	Y
    private Float reportdate;//timestamp	报案时间
    private String companyaddress;//String	公司地址	Y
    private String homeaddress;//String	家庭地址

    @Override
    public String toString() {
        return "Overtimeordertable{" +
                "id=" + id +
                ", payed=" + payed +
                ", userid=" + userid +
                ", charegeid=" + charegeid +
                ", compensated=" + compensated +
                ", money=" + money +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", reportdate=" + reportdate +
                ", companyaddress='" + companyaddress + '\'' +
                ", homeaddress='" + homeaddress + '\'' +
                '}';
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

    public void setMoney(Float money) {
        this.money = money;
    }

    public Float getStartdate() {
        return startdate;
    }

    public void setStartdate(Float startdate) {
        this.startdate = startdate;
    }

    public Float getEnddate() {
        return enddate;
    }

    public void setEnddate(Float enddate) {
        this.enddate = enddate;
    }

    public Float getReportdate() {
        return reportdate;
    }

    public void setReportdate(Float reportdate) {
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

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
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
        dest.writeValue(this.startdate);
        dest.writeValue(this.enddate);
        dest.writeValue(this.reportdate);
        dest.writeString(this.companyaddress);
        dest.writeString(this.homeaddress);
    }

    public Overtimeordertable() {
    }

    protected Overtimeordertable(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.payed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.charegeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.compensated = (Integer) in.readValue(Integer.class.getClassLoader());
        this.money = (Float) in.readValue(Float.class.getClassLoader());
        this.startdate = (Float) in.readValue(Float.class.getClassLoader());
        this.enddate = (Float) in.readValue(Float.class.getClassLoader());
        this.reportdate = (Float) in.readValue(Float.class.getClassLoader());
        this.companyaddress = in.readString();
        this.homeaddress = in.readString();
    }

    public static final Parcelable.Creator<Overtimeordertable> CREATOR = new Parcelable.Creator<Overtimeordertable>() {
        public Overtimeordertable createFromParcel(Parcel source) {
            return new Overtimeordertable(source);
        }

        public Overtimeordertable[] newArray(int size) {
            return new Overtimeordertable[size];
        }
    };
}
