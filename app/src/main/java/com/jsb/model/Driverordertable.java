package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/25.
 */
public class Driverordertable implements Parcelable {

    private Integer id;
    private Integer userid;
    private Integer payed;
    private Integer chargeid;
    private Integer compensated;
    private Integer companyid;
    private Integer operatorid;
    private Integer refereeid;
    private Integer deleted;
    private String name;
    private String idnumber;
    private Float money;
    private Long startdate;
    private Long enddate;
    private Long buydate;
    private String contactaddress;

    @Override
    public String toString() {
        return "Driverordertable{" +
                "id=" + id +
                ", userid=" + userid +
                ", payed=" + payed +
                ", chargeid=" + chargeid +
                ", compensated=" + compensated +
                ", companyid=" + companyid +
                ", operatorid=" + operatorid +
                ", refereeid=" + refereeid +
                ", deleted=" + deleted +
                ", name='" + name + '\'' +
                ", idnumber='" + idnumber + '\'' +
                ", money=" + money +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", buydate=" + buydate +
                ", contactaddress='" + contactaddress + '\'' +
                '}';
    }

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

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Integer getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(Integer operatorid) {
        this.operatorid = operatorid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
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

    public Long getBuydate() {
        return buydate;
    }

    public void setBuydate(Long buydate) {
        this.buydate = buydate;
    }

    public String getContactaddress() {
        return contactaddress;
    }

    public void setContactaddress(String contactaddress) {
        this.contactaddress = contactaddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.userid);
        dest.writeValue(this.payed);
        dest.writeValue(this.chargeid);
        dest.writeValue(this.compensated);
        dest.writeValue(this.companyid);
        dest.writeValue(this.operatorid);
        dest.writeValue(this.refereeid);
        dest.writeValue(this.deleted);
        dest.writeString(this.name);
        dest.writeString(this.idnumber);
        dest.writeValue(this.money);
        dest.writeValue(this.startdate);
        dest.writeValue(this.enddate);
        dest.writeValue(this.buydate);
        dest.writeString(this.contactaddress);
    }

    public Driverordertable() {
    }

    protected Driverordertable(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.payed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.chargeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.compensated = (Integer) in.readValue(Integer.class.getClassLoader());
        this.companyid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.operatorid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.refereeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.deleted = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.idnumber = in.readString();
        this.money = (Float) in.readValue(Float.class.getClassLoader());
        this.startdate = (Long) in.readValue(Long.class.getClassLoader());
        this.enddate = (Long) in.readValue(Long.class.getClassLoader());
        this.buydate = (Long) in.readValue(Long.class.getClassLoader());
        this.contactaddress = in.readString();
    }

    public static final Parcelable.Creator<Driverordertable> CREATOR = new Parcelable.Creator<Driverordertable>() {
        public Driverordertable createFromParcel(Parcel source) {
            return new Driverordertable(source);
        }

        public Driverordertable[] newArray(int size) {
            return new Driverordertable[size];
        }
    };
}
