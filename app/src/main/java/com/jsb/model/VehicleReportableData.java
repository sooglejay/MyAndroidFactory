package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/25.
 */
public class VehicleReportableData implements Parcelable {

    @Override
    public String toString() {
        return "VehicleReportableData{" +
                "id=" + id +
                ", operatorid=" + operatorid +
                ", kind=" + kind +
                ", status=" + status +
                ", selectedpriceid=" + selectedpriceid +
                ", compensated=" + compensated +
                ", type=" + type +
                ", refereeid=" + refereeid +
                ", deleted=" + deleted +
                ", money=" + money +
                ", insurancetype=" + insurancetype +
                ", totalpausefee=" + totalpausefee +
                ", licenseplate='" + licenseplate + '\'' +
                ", provence='" + provence + '\'' +
                ", forage='" + forage + '\'' +
                ", ordernum='" + ordernum + '\'' +
                ", orderdate=" + orderdate +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", name='" + name + '\'' +
                ", recievename='" + recievename + '\'' +
                ", recievephone='" + recievephone + '\'' +
                ", recieveaddress='" + recieveaddress + '\'' +
                ", userid=" + userid +
                ", idcardnum='" + idcardnum + '\'' +
                ", payed=" + payed +
                ", chargeid=" + chargeid +
                ", companyInfo=" + companyInfo +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(Integer operatorid) {
        this.operatorid = operatorid;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSelectedpriceid() {
        return selectedpriceid;
    }

    public void setSelectedpriceid(Integer selectedpriceid) {
        this.selectedpriceid = selectedpriceid;
    }

    public Integer getCompensated() {
        return compensated;
    }

    public void setCompensated(Integer compensated) {
        this.compensated = compensated;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getInsurancetype() {
        return insurancetype;
    }

    public void setInsurancetype(Integer insurancetype) {
        this.insurancetype = insurancetype;
    }

    public Integer getTotalpausefee() {
        return totalpausefee;
    }

    public void setTotalpausefee(Integer totalpausefee) {
        this.totalpausefee = totalpausefee;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
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

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public Long getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Long orderdate) {
        this.orderdate = orderdate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecievename() {
        return recievename;
    }

    public void setRecievename(String recievename) {
        this.recievename = recievename;
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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getIdcardnum() {
        return idcardnum;
    }

    public void setIdcardnum(String idcardnum) {
        this.idcardnum = idcardnum;
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

    public InsuranceCompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(InsuranceCompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    private Integer id;
    private Integer operatorid;
    private Integer kind;
    private Integer status;
    private Integer selectedpriceid;
    private Integer compensated;
    private Integer type;
    private Integer refereeid;
    private Integer deleted;
    private Integer money;
    private Integer insurancetype;
    private Integer totalpausefee;
    private String licenseplate;
    private String provence;
    private String forage;
    private String ordernum;
    private Long orderdate;
    private Long startdate;
    private Long enddate;
    private String name;
    private String recievename;
    private String recievephone;
    private String recieveaddress;
    private Integer userid;
    private String idcardnum;
    private Integer payed;
    private Integer chargeid;
    private InsuranceCompanyInfo companyInfo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.operatorid);
        dest.writeValue(this.kind);
        dest.writeValue(this.status);
        dest.writeValue(this.selectedpriceid);
        dest.writeValue(this.compensated);
        dest.writeValue(this.type);
        dest.writeValue(this.refereeid);
        dest.writeValue(this.deleted);
        dest.writeValue(this.money);
        dest.writeValue(this.insurancetype);
        dest.writeValue(this.totalpausefee);
        dest.writeString(this.licenseplate);
        dest.writeString(this.provence);
        dest.writeString(this.forage);
        dest.writeString(this.ordernum);
        dest.writeValue(this.orderdate);
        dest.writeValue(this.startdate);
        dest.writeValue(this.enddate);
        dest.writeString(this.name);
        dest.writeString(this.recievename);
        dest.writeString(this.recievephone);
        dest.writeString(this.recieveaddress);
        dest.writeValue(this.userid);
        dest.writeString(this.idcardnum);
        dest.writeValue(this.payed);
        dest.writeValue(this.chargeid);
        dest.writeParcelable(this.companyInfo, 0);
    }

    public VehicleReportableData() {
    }

    protected VehicleReportableData(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.operatorid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.kind = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.selectedpriceid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.compensated = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.refereeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.deleted = (Integer) in.readValue(Integer.class.getClassLoader());
        this.money = (Integer) in.readValue(Integer.class.getClassLoader());
        this.insurancetype = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalpausefee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.licenseplate = in.readString();
        this.provence = in.readString();
        this.forage = in.readString();
        this.ordernum = in.readString();
        this.orderdate = (Long) in.readValue(Long.class.getClassLoader());
        this.startdate = (Long) in.readValue(Long.class.getClassLoader());
        this.enddate = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.recievename = in.readString();
        this.recievephone = in.readString();
        this.recieveaddress = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.idcardnum = in.readString();
        this.payed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.chargeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.companyInfo = in.readParcelable(InsuranceCompanyInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<VehicleReportableData> CREATOR = new Parcelable.Creator<VehicleReportableData>() {
        public VehicleReportableData createFromParcel(Parcel source) {
            return new VehicleReportableData(source);
        }

        public VehicleReportableData[] newArray(int size) {
            return new VehicleReportableData[size];
        }
    };
}
