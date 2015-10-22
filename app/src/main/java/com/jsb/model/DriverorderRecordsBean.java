package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class DriverorderRecordsBean implements Parcelable {

    private Integer id;
    private Integer userid;
    private String name;
    private Integer money;
    private Integer payed;
    private Long buydate;
    private Integer refereeid;
    private Integer deleted;
    private InsuranceCompanyInfo companyInfo;//  123;//这个字段有异议，你懂得吧！！
    private Integer companyid;
    private Integer compensated;
    private Integer operatorid;
    private Integer chargeid;
    private Long startdate;
    private Long enddate;
    private String contactaddress;
    private String idnumber;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getBuydate() {
        return buydate;
    }

    public void setBuydate(Long buydate) {
        this.buydate = buydate;
    }

    public Integer getRefereeid() {
        return refereeid;
    }

    public void setRefereeid(Integer refereeid) {
        this.refereeid = refereeid;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public InsuranceCompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(InsuranceCompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Integer getCompensated() {
        return compensated;
    }

    public void setCompensated(Integer compensated) {
        this.compensated = compensated;
    }

    public Integer getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(Integer operatorid) {
        this.operatorid = operatorid;
    }

    public Integer getChargeid() {
        return chargeid;
    }

    public void setChargeid(Integer chargeid) {
        this.chargeid = chargeid;
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

    public String getContactaddress() {
        return contactaddress;
    }

    public void setContactaddress(String contactaddress) {
        this.contactaddress = contactaddress;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.userid);
        dest.writeString(this.name);
        dest.writeValue(this.money);
        dest.writeValue(this.payed);
        dest.writeValue(this.buydate);
        dest.writeValue(this.refereeid);
        dest.writeValue(this.deleted);
        dest.writeParcelable(this.companyInfo, 0);
        dest.writeValue(this.companyid);
        dest.writeValue(this.compensated);
        dest.writeValue(this.operatorid);
        dest.writeValue(this.chargeid);
        dest.writeValue(this.startdate);
        dest.writeValue(this.enddate);
        dest.writeString(this.contactaddress);
        dest.writeString(this.idnumber);
    }

    public DriverorderRecordsBean() {
    }

    protected DriverorderRecordsBean(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.money = (Integer) in.readValue(Integer.class.getClassLoader());
        this.payed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.buydate = (Long) in.readValue(Long.class.getClassLoader());
        this.refereeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.deleted = (Integer) in.readValue(Integer.class.getClassLoader());
        this.companyInfo = in.readParcelable(InsuranceCompanyInfo.class.getClassLoader());
        this.companyid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.compensated = (Integer) in.readValue(Integer.class.getClassLoader());
        this.operatorid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.chargeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.startdate = (Long) in.readValue(Long.class.getClassLoader());
        this.enddate = (Long) in.readValue(Long.class.getClassLoader());
        this.contactaddress = in.readString();
        this.idnumber = in.readString();
    }

    public static final Parcelable.Creator<DriverorderRecordsBean> CREATOR = new Parcelable.Creator<DriverorderRecordsBean>() {
        public DriverorderRecordsBean createFromParcel(Parcel source) {
            return new DriverorderRecordsBean(source);
        }

        public DriverorderRecordsBean[] newArray(int size) {
            return new DriverorderRecordsBean[size];
        }
    };
}
