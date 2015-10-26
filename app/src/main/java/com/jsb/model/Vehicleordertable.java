package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.4.3.车险订单Vehicleordertable
 */
public class Vehicleordertable implements Parcelable {

    private Integer id;//int	车险订单编号	Y
    private Integer userid;//int	用户id（被保险人）	Y
    private Integer payed;//int	是否付款   0未付 1已付	Y
    private Integer chargeid;//int 支付对象id
    private Integer refereeid;//int 	推荐人id
    private Integer operatorid;//int 	操作人id（经办人）
    private Integer selectedpriceid;//int 	选中的报价编号
    private Integer deleted;//int 	是否被用户删除  0未删除1删除
    private Integer type;//int 被保人是否是注册用户 0非 1是	Y
    private Integer status;//int 保单是否有效期内 0非 1是	Y
    private Integer compensated;//int 是否赔付  0未赔 1已赔	Y
    private Integer insurancetype;//int 车险类型 0商业 1交强	Y
    private String licenseplate;//String	车牌号	Y
    private String ordernum;//String	保险单号
    private String provence;//String	出险省份	Y
    private Long orderdate;//下单日期	Y
    private Long startdate;//起效日期	Y
    private Long enddate;//结束日期	Y
    private String name;//姓名
    private String forage;//用于标记两个记录是否属于一次车险订单，相等则说明是
    private String recievename;//收件人姓名
    private String recievephone;//收件人电话
    private String recieveaddress;//String	收件人地址
    private String idcardnum;//String	身份证号	Y
    private Float money;//float	金额	Y
    private Float totalpausefee;//float	该单子累计停保费
    private Vehicletable vehicle;//车辆信息对象5.4.4
    private Vechicleinsurancedetail insuranceDetail;//具体保险内容对象集合5.4.5

    @Override
    public String toString() {
        return "Vehicleordertable{" +
                "id=" + id +
                ", userid=" + userid +
                ", payed=" + payed +
                ", chargeid=" + chargeid +
                ", refereeid=" + refereeid +
                ", operatorid=" + operatorid +
                ", selectedpriceid=" + selectedpriceid +
                ", deleted=" + deleted +
                ", type=" + type +
                ", status=" + status +
                ", compensated=" + compensated +
                ", insurancetype=" + insurancetype +
                ", licenseplate='" + licenseplate + '\'' +
                ", ordernum='" + ordernum + '\'' +
                ", provence='" + provence + '\'' +
                ", orderdate=" + orderdate +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", name='" + name + '\'' +
                ", forage='" + forage + '\'' +
                ", recievename='" + recievename + '\'' +
                ", recievephone='" + recievephone + '\'' +
                ", recieveaddress='" + recieveaddress + '\'' +
                ", idcardnum='" + idcardnum + '\'' +
                ", money=" + money +
                ", totalpausefee=" + totalpausefee +
                ", vehicle=" + vehicle +
                ", insuranceDetail=" + insuranceDetail +
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

    public Integer getRefereeid() {
        return refereeid;
    }

    public void setRefereeid(Integer refereeid) {
        this.refereeid = refereeid;
    }

    public Integer getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(Integer operatorid) {
        this.operatorid = operatorid;
    }

    public Integer getSelectedpriceid() {
        return selectedpriceid;
    }

    public void setSelectedpriceid(Integer selectedpriceid) {
        this.selectedpriceid = selectedpriceid;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCompensated() {
        return compensated;
    }

    public void setCompensated(Integer compensated) {
        this.compensated = compensated;
    }

    public Integer getInsurancetype() {
        return insurancetype;
    }

    public void setInsurancetype(Integer insurancetype) {
        this.insurancetype = insurancetype;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getProvence() {
        return provence;
    }

    public void setProvence(String provence) {
        this.provence = provence;
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

    public String getForage() {
        return forage;
    }

    public void setForage(String forage) {
        this.forage = forage;
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

    public String getIdcardnum() {
        return idcardnum;
    }

    public void setIdcardnum(String idcardnum) {
        this.idcardnum = idcardnum;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Float getTotalpausefee() {
        return totalpausefee;
    }

    public void setTotalpausefee(Float totalpausefee) {
        this.totalpausefee = totalpausefee;
    }

    public Vehicletable getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicletable vehicle) {
        this.vehicle = vehicle;
    }

    public Vechicleinsurancedetail getInsuranceDetail() {
        return insuranceDetail;
    }

    public void setInsuranceDetail(Vechicleinsurancedetail insuranceDetail) {
        this.insuranceDetail = insuranceDetail;
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
        dest.writeValue(this.refereeid);
        dest.writeValue(this.operatorid);
        dest.writeValue(this.selectedpriceid);
        dest.writeValue(this.deleted);
        dest.writeValue(this.type);
        dest.writeValue(this.status);
        dest.writeValue(this.compensated);
        dest.writeValue(this.insurancetype);
        dest.writeString(this.licenseplate);
        dest.writeString(this.ordernum);
        dest.writeString(this.provence);
        dest.writeValue(this.orderdate);
        dest.writeValue(this.startdate);
        dest.writeValue(this.enddate);
        dest.writeString(this.name);
        dest.writeString(this.forage);
        dest.writeString(this.recievename);
        dest.writeString(this.recievephone);
        dest.writeString(this.recieveaddress);
        dest.writeString(this.idcardnum);
        dest.writeValue(this.money);
        dest.writeValue(this.totalpausefee);
        dest.writeParcelable(this.vehicle, 0);
        dest.writeParcelable(this.insuranceDetail, 0);
    }

    public Vehicleordertable() {
    }

    protected Vehicleordertable(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.payed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.chargeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.refereeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.operatorid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.selectedpriceid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.deleted = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.compensated = (Integer) in.readValue(Integer.class.getClassLoader());
        this.insurancetype = (Integer) in.readValue(Integer.class.getClassLoader());
        this.licenseplate = in.readString();
        this.ordernum = in.readString();
        this.provence = in.readString();
        this.orderdate = (Long) in.readValue(Long.class.getClassLoader());
        this.startdate = (Long) in.readValue(Long.class.getClassLoader());
        this.enddate = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.forage = in.readString();
        this.recievename = in.readString();
        this.recievephone = in.readString();
        this.recieveaddress = in.readString();
        this.idcardnum = in.readString();
        this.money = (Float) in.readValue(Float.class.getClassLoader());
        this.totalpausefee = (Float) in.readValue(Float.class.getClassLoader());
        this.vehicle = in.readParcelable(Vehicletable.class.getClassLoader());
        this.insuranceDetail = in.readParcelable(Vechicleinsurancedetail.class.getClassLoader());
    }

    public static final Parcelable.Creator<Vehicleordertable> CREATOR = new Parcelable.Creator<Vehicleordertable>() {
        public Vehicleordertable createFromParcel(Parcel source) {
            return new Vehicleordertable(source);
        }

        public Vehicleordertable[] newArray(int size) {
            return new Vehicleordertable[size];
        }
    };
}
