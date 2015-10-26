package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.4.2.保险公司报价信息Insurancecompanyprice
 */
public class Insurancecompanyprice implements Parcelable {


    private Integer id;//报价编号	Y
    private Integer companyid;//报价公司编号	Y
    private Integer selected;//该报价是否被选中，0未选中 1选中	Y
    private Float totalprice;//总价	Y
    private Long date;//报价时间	Y
    private Vechicleinsurancedetail insuranceDetail;//报价时间	Y 5.4.5

    @Override
    public String toString() {
        return "Insurancecompanyprice{" +
                "id=" + id +
                ", companyid=" + companyid +
                ", selected=" + selected +
                ", totalprice=" + totalprice +
                ", date=" + date +
                ", insuranceDetail=" + insuranceDetail +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
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
        dest.writeValue(this.companyid);
        dest.writeValue(this.selected);
        dest.writeValue(this.totalprice);
        dest.writeValue(this.date);
        dest.writeParcelable(this.insuranceDetail, 0);
    }

    public Insurancecompanyprice() {
    }

    protected Insurancecompanyprice(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.companyid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.selected = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalprice = (Float) in.readValue(Float.class.getClassLoader());
        this.date = (Long) in.readValue(Long.class.getClassLoader());
        this.insuranceDetail = in.readParcelable(Vechicleinsurancedetail.class.getClassLoader());
    }

    public static final Parcelable.Creator<Insurancecompanyprice> CREATOR = new Parcelable.Creator<Insurancecompanyprice>() {
        public Insurancecompanyprice createFromParcel(Parcel source) {
            return new Insurancecompanyprice(source);
        }

        public Insurancecompanyprice[] newArray(int size) {
            return new Insurancecompanyprice[size];
        }
    };
}
