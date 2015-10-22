package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class OvertimeRecordsBean implements Parcelable {

    private Integer id;
    private Integer userid;
    private Integer money;
    private Integer payed;
    private Integer chargeid;
    private Integer compensated;
    private Long startdate;
    private Long enddate;
    private Long reportdate;
    private String companyaddress;
    private String homeaddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getPayed() {
        return payed;
    }

    public void setPayed(Integer payed) {
        this.payed = payed;
    }

    public Integer getChargeid() {
        return chargeid;
    }

    public void setChargeid(Integer chargeid) {
        this.chargeid = chargeid;
    }

    public Integer getCompensated() {
        return compensated;
    }

    public void setCompensated(Integer compensated) {
        this.compensated = compensated;
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
        dest.writeValue(this.userid);
        dest.writeValue(this.money);
        dest.writeValue(this.payed);
        dest.writeValue(this.chargeid);
        dest.writeValue(this.compensated);
        dest.writeValue(this.startdate);
        dest.writeValue(this.enddate);
        dest.writeValue(this.reportdate);
        dest.writeString(this.companyaddress);
        dest.writeString(this.homeaddress);
    }

    public OvertimeRecordsBean() {
    }

    protected OvertimeRecordsBean(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.money = (Integer) in.readValue(Integer.class.getClassLoader());
        this.payed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.chargeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.compensated = (Integer) in.readValue(Integer.class.getClassLoader());
        this.startdate = (Long) in.readValue(Long.class.getClassLoader());
        this.enddate = (Long) in.readValue(Long.class.getClassLoader());
        this.reportdate = (Long) in.readValue(Long.class.getClassLoader());
        this.companyaddress = in.readString();
        this.homeaddress = in.readString();
    }

    public static final Parcelable.Creator<OvertimeRecordsBean> CREATOR = new Parcelable.Creator<OvertimeRecordsBean>() {
        public OvertimeRecordsBean createFromParcel(Parcel source) {
            return new OvertimeRecordsBean(source);
        }

        public OvertimeRecordsBean[] newArray(int size) {
            return new OvertimeRecordsBean[size];
        }
    };
}
