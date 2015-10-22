package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class VehicleorderRecordsBean implements Parcelable {

    private Integer id;
    private String idcardnum;//这个字段有异议，你懂得起吧！！ ;;;;;;已解决！！！标识的是身份证号
    private String recievephone;
    private String recieveaddress;
    private Integer status;
    private Integer insurancetype;
    private InsuranceCompanyInfo companyInfo;// 这个字段有异议，你懂得起！;
    private Integer totalpausefee;
    private Integer money;
    private String provence;
    private String forage;
    private Integer kind;
    private Integer type;
    private Integer compensated;
    private Integer ordernum;
    private Integer deleted;
    private Integer selectedpriceid;
    private Integer operatorid;
    private Integer refereeid;
    private Integer userid;
    private String recievename;
    private Long enddate;
    private Long startdate;
    private Integer chargeid;
    private Integer payed;
    private Long orderdate;
    private String licenseplate;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdcardnum() {
        return idcardnum;
    }

    public void setIdcardnum(String idcardnum) {
        this.idcardnum = idcardnum;
    }

    public String getRecievephone() {
        return recievephone;
    }

    public void setRecievephone(String recievephone) {
        this.recievephone = recievephone;
    }

    public String getRecieveaddress() {
        return recieveaddress;
    }

    public void setRecieveaddress(String recieveaddress) {
        this.recieveaddress = recieveaddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInsurancetype() {
        return insurancetype;
    }

    public void setInsurancetype(Integer insurancetype) {
        this.insurancetype = insurancetype;
    }

    public InsuranceCompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(InsuranceCompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public Integer getTotalpausefee() {
        return totalpausefee;
    }

    public void setTotalpausefee(Integer totalpausefee) {
        this.totalpausefee = totalpausefee;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getProvence() {
        return provence;
    }

    public void setProvence(String provence) {
        this.provence = provence;
    }

    public String getForage() {
        return forage;
    }

    public void setForage(String forage) {
        this.forage = forage;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCompensated() {
        return compensated;
    }

    public void setCompensated(Integer compensated) {
        this.compensated = compensated;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getSelectedpriceid() {
        return selectedpriceid;
    }

    public void setSelectedpriceid(Integer selectedpriceid) {
        this.selectedpriceid = selectedpriceid;
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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getRecievename() {
        return recievename;
    }

    public void setRecievename(String recievename) {
        this.recievename = recievename;
    }

    public Long getEnddate() {
        return enddate;
    }

    public void setEnddate(Long enddate) {
        this.enddate = enddate;
    }

    public Long getStartdate() {
        return startdate;
    }

    public void setStartdate(Long startdate) {
        this.startdate = startdate;
    }

    public Integer getChargeid() {
        return chargeid;
    }

    public void setChargeid(Integer chargeid) {
        this.chargeid = chargeid;
    }

    public Integer getPayed() {
        return payed;
    }

    public void setPayed(Integer payed) {
        this.payed = payed;
    }

    public Long getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Long orderdate) {
        this.orderdate = orderdate;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.idcardnum);
        dest.writeString(this.recievephone);
        dest.writeString(this.recieveaddress);
        dest.writeValue(this.status);
        dest.writeValue(this.insurancetype);
        dest.writeParcelable(this.companyInfo, 0);
        dest.writeValue(this.totalpausefee);
        dest.writeValue(this.money);
        dest.writeString(this.provence);
        dest.writeString(this.forage);
        dest.writeValue(this.kind);
        dest.writeValue(this.type);
        dest.writeValue(this.compensated);
        dest.writeValue(this.ordernum);
        dest.writeValue(this.deleted);
        dest.writeValue(this.selectedpriceid);
        dest.writeValue(this.operatorid);
        dest.writeValue(this.refereeid);
        dest.writeValue(this.userid);
        dest.writeString(this.recievename);
        dest.writeValue(this.enddate);
        dest.writeValue(this.startdate);
        dest.writeValue(this.chargeid);
        dest.writeValue(this.payed);
        dest.writeValue(this.orderdate);
        dest.writeString(this.licenseplate);
        dest.writeString(this.name);
    }

    public VehicleorderRecordsBean() {
    }

    protected VehicleorderRecordsBean(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.idcardnum = in.readString();
        this.recievephone = in.readString();
        this.recieveaddress = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.insurancetype = (Integer) in.readValue(Integer.class.getClassLoader());
        this.companyInfo = in.readParcelable(InsuranceCompanyInfo.class.getClassLoader());
        this.totalpausefee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.money = (Integer) in.readValue(Integer.class.getClassLoader());
        this.provence = in.readString();
        this.forage = in.readString();
        this.kind = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.compensated = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ordernum = (Integer) in.readValue(Integer.class.getClassLoader());
        this.deleted = (Integer) in.readValue(Integer.class.getClassLoader());
        this.selectedpriceid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.operatorid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.refereeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.recievename = in.readString();
        this.enddate = (Long) in.readValue(Long.class.getClassLoader());
        this.startdate = (Long) in.readValue(Long.class.getClassLoader());
        this.chargeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.payed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderdate = (Long) in.readValue(Long.class.getClassLoader());
        this.licenseplate = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<VehicleorderRecordsBean> CREATOR = new Parcelable.Creator<VehicleorderRecordsBean>() {
        public VehicleorderRecordsBean createFromParcel(Parcel source) {
            return new VehicleorderRecordsBean(source);
        }

        public VehicleorderRecordsBean[] newArray(int size) {
            return new VehicleorderRecordsBean[size];
        }
    };
}
