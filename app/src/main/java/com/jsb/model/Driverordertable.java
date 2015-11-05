package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.6.驾驶险
 5.6.1.驾驶险订单Driverordertable
 */
public class Driverordertable implements Parcelable {

    private Integer id;//驾驶险订单编号	Y
    private Integer userid;//用户编号（被保险人）	Y
    private Integer payed;//int	是否支付 0未付 1支付	Y
    private Integer chargeid;//int	支付对象id
    private Integer compensated;//int	是否赔付 0未赔 1已赔
    private Integer companyid;//int	保险公司id	Y
    private Integer operatorid;//int	操作人（经办人）id
    private Integer refereeid;//int	推荐人id
    private Integer deleted;//int	是否被用户删除 0未删 1已删
    private String name;//string	姓名	Y
    private String idnumber;//身份证号	Y
    private Float money;//金额  	Y
    private Long startdate;//起效时间	Y
    private Long enddate;//结束时间	Y
    private Long buydate;//下单日期	Y
    private String contactaddress;//string	联系地址	Y

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
                ", companyInfo=" + companyInfo +
                '}';
    }


    public InsuranceCompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(InsuranceCompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public static Creator<Driverordertable> getCREATOR() {
        return CREATOR;
    }

    private InsuranceCompanyInfo companyInfo;//保险公司数据结构


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

    public Driverordertable() {
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
        dest.writeParcelable(this.companyInfo, 0);
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
        this.companyInfo = in.readParcelable(InsuranceCompanyInfo.class.getClassLoader());
    }

    public static final Creator<Driverordertable> CREATOR = new Creator<Driverordertable>() {
        public Driverordertable createFromParcel(Parcel source) {
            return new Driverordertable(source);
        }

        public Driverordertable[] newArray(int size) {
            return new Driverordertable[size];
        }
    };
}
