package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/26.
 */
public class MyWalletData implements Parcelable {

    private Float paueFee;//float	停保费
    private Float shareFee;//float	分项费
    private Float workFee;//float	销售业绩费
    private Float manageFee;//float	管理费
    private Float refereeFee;//float	推荐费
    private Float overtimeFee;//float	加班险费
    private Integer sharesAmount;//int	分项总数
    private Integer overtimeAmount;//int	加班险总数
//    private ShareRecord shareRecords;//分项记录集合，参见5.8.2  有问题，待定
    private Overtimeordertable overtimeRecords;//加班险集合，参见5.5.1

    @Override
    public String toString() {
        return "MyWalletData{" +
                "paueFee=" + paueFee +
                ", shareFee=" + shareFee +
                ", workFee=" + workFee +
                ", manageFee=" + manageFee +
                ", refereeFee=" + refereeFee +
                ", overtimeFee=" + overtimeFee +
                ", sharesAmount=" + sharesAmount +
                ", overtimeAmount=" + overtimeAmount +
                ", overtimeRecords=" + overtimeRecords +
                '}';
    }

    public Float getPaueFee() {
        return paueFee;
    }

    public void setPaueFee(Float paueFee) {
        this.paueFee = paueFee;
    }

    public Float getShareFee() {
        return shareFee;
    }

    public void setShareFee(Float shareFee) {
        this.shareFee = shareFee;
    }

    public Float getWorkFee() {
        return workFee;
    }

    public void setWorkFee(Float workFee) {
        this.workFee = workFee;
    }

    public Float getManageFee() {
        return manageFee;
    }

    public void setManageFee(Float manageFee) {
        this.manageFee = manageFee;
    }

    public Float getRefereeFee() {
        return refereeFee;
    }

    public void setRefereeFee(Float refereeFee) {
        this.refereeFee = refereeFee;
    }

    public Float getOvertimeFee() {
        return overtimeFee;
    }

    public void setOvertimeFee(Float overtimeFee) {
        this.overtimeFee = overtimeFee;
    }

    public Integer getSharesAmount() {
        return sharesAmount;
    }

    public void setSharesAmount(Integer sharesAmount) {
        this.sharesAmount = sharesAmount;
    }

    public Integer getOvertimeAmount() {
        return overtimeAmount;
    }

    public void setOvertimeAmount(Integer overtimeAmount) {
        this.overtimeAmount = overtimeAmount;
    }

    public Overtimeordertable getOvertimeRecords() {
        return overtimeRecords;
    }

    public void setOvertimeRecords(Overtimeordertable overtimeRecords) {
        this.overtimeRecords = overtimeRecords;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.paueFee);
        dest.writeValue(this.shareFee);
        dest.writeValue(this.workFee);
        dest.writeValue(this.manageFee);
        dest.writeValue(this.refereeFee);
        dest.writeValue(this.overtimeFee);
        dest.writeValue(this.sharesAmount);
        dest.writeValue(this.overtimeAmount);
        dest.writeParcelable(this.overtimeRecords, 0);
    }

    public MyWalletData() {
    }

    protected MyWalletData(Parcel in) {
        this.paueFee = (Float) in.readValue(Float.class.getClassLoader());
        this.shareFee = (Float) in.readValue(Float.class.getClassLoader());
        this.workFee = (Float) in.readValue(Float.class.getClassLoader());
        this.manageFee = (Float) in.readValue(Float.class.getClassLoader());
        this.refereeFee = (Float) in.readValue(Float.class.getClassLoader());
        this.overtimeFee = (Float) in.readValue(Float.class.getClassLoader());
        this.sharesAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overtimeAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overtimeRecords = in.readParcelable(Overtimeordertable.class.getClassLoader());
    }

    public static final Parcelable.Creator<MyWalletData> CREATOR = new Parcelable.Creator<MyWalletData>() {
        public MyWalletData createFromParcel(Parcel source) {
            return new MyWalletData(source);
        }

        public MyWalletData[] newArray(int size) {
            return new MyWalletData[size];
        }
    };
}
