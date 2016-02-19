package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sooglejay on 16/2/18.
 * 车险详情InsuranceItemData

 */
public class InsuranceItemData implements Parcelable {

    /**
     *
     insrnc_cde	String	保险类别编号	Y
     insrnc_name	String	保险类别名字	Y
     amt	Float	保险金额/赔偿限额
     premium	Float	保费
     number	Int	保障天数/人数
     franchise_flag	Int	是否不计免赔  0否  1是
     c_ly15	Int	车损绝对免赔额   （有接口可以获取免赔额列表）
     bullet_glass	Int	是否防弹玻璃 o 否 1是
     remark	String	备注

     */
    private String insrnc_cde;
    private String insrnc_name;
    private String remark;
    private Float amt;
    private Float premium;
    private Integer number;
    private Integer franchise_flag;
    private Integer c_ly15;
    private Integer bullet_glass;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.insrnc_cde);
        dest.writeString(this.insrnc_name);
        dest.writeString(this.remark);
        dest.writeValue(this.amt);
        dest.writeValue(this.premium);
        dest.writeValue(this.number);
        dest.writeValue(this.franchise_flag);
        dest.writeValue(this.c_ly15);
        dest.writeValue(this.bullet_glass);
    }

    public InsuranceItemData() {
    }

    protected InsuranceItemData(Parcel in) {
        this.insrnc_cde = in.readString();
        this.insrnc_name = in.readString();
        this.remark = in.readString();
        this.amt = (Float) in.readValue(Float.class.getClassLoader());
        this.premium = (Float) in.readValue(Float.class.getClassLoader());
        this.number = (Integer) in.readValue(Integer.class.getClassLoader());
        this.franchise_flag = (Integer) in.readValue(Integer.class.getClassLoader());
        this.c_ly15 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.bullet_glass = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<InsuranceItemData> CREATOR = new Parcelable.Creator<InsuranceItemData>() {
        public InsuranceItemData createFromParcel(Parcel source) {
            return new InsuranceItemData(source);
        }

        public InsuranceItemData[] newArray(int size) {
            return new InsuranceItemData[size];
        }
    };

    public String getInsrnc_cde() {
        return insrnc_cde;
    }

    public void setInsrnc_cde(String insrnc_cde) {
        this.insrnc_cde = insrnc_cde;
    }

    public String getInsrnc_name() {
        return insrnc_name;
    }

    public void setInsrnc_name(String insrnc_name) {
        this.insrnc_name = insrnc_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Float getAmt() {
        return amt;
    }

    public void setAmt(Float amt) {
        this.amt = amt;
    }

    public Float getPremium() {
        return premium;
    }

    public void setPremium(Float premium) {
        this.premium = premium;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getFranchise_flag() {
        return franchise_flag;
    }

    public void setFranchise_flag(Integer franchise_flag) {
        this.franchise_flag = franchise_flag;
    }

    public Integer getC_ly15() {
        return c_ly15;
    }

    public void setC_ly15(Integer c_ly15) {
        this.c_ly15 = c_ly15;
    }

    public Integer getBullet_glass() {
        return bullet_glass;
    }

    public void setBullet_glass(Integer bullet_glass) {
        this.bullet_glass = bullet_glass;
    }

    public static Creator<InsuranceItemData> getCREATOR() {
        return CREATOR;
    }
}
