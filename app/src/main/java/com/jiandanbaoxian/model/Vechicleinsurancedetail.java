package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.4.5.车险具体保险内容Vechicleinsurancedetail
 */
public class Vechicleinsurancedetail implements Parcelable {

    private Integer id;//对象编号	Y
    private Integer priceid;//对应的报价编号	Y
    private Integer coverage;//保额（元）	Y
    private String insurancename;//险种名称	Y
    private Float fee;//保费（元）	Y
    private Integer seat;//几座

    @Override
    public String toString() {
        return "Vechicleinsurancedetail{" +
                "id=" + id +
                ", priceid=" + priceid +
                ", coverage=" + coverage +
                ", insurancename='" + insurancename + '\'' +
                ", fee=" + fee +
                ", seat=" + seat +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPriceid() {
        return priceid;
    }

    public void setPriceid(Integer priceid) {
        this.priceid = priceid;
    }

    public Integer getCoverage() {
        return coverage;
    }

    public void setCoverage(Integer coverage) {
        this.coverage = coverage;
    }

    public String getInsurancename() {
        return insurancename;
    }

    public void setInsurancename(String insurancename) {
        this.insurancename = insurancename;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.priceid);
        dest.writeValue(this.coverage);
        dest.writeString(this.insurancename);
        dest.writeValue(this.fee);
        dest.writeValue(this.seat);
    }

    public Vechicleinsurancedetail() {
    }

    protected Vechicleinsurancedetail(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.priceid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.coverage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.insurancename = in.readString();
        this.fee = (Float) in.readValue(Float.class.getClassLoader());
        this.seat = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Vechicleinsurancedetail> CREATOR = new Parcelable.Creator<Vechicleinsurancedetail>() {
        public Vechicleinsurancedetail createFromParcel(Parcel source) {
            return new Vechicleinsurancedetail(source);
        }

        public Vechicleinsurancedetail[] newArray(int size) {
            return new Vechicleinsurancedetail[size];
        }
    };
}
