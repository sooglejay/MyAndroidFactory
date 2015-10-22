package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class getMywalletInfo implements Parcelable {
    private Integer pasueFee;
    private Integer awardFee;
    private Integer overtimeFee;
    private List<OvertimeRecordsBean> overtimeRecords;
    private Integer sharesAmount;
    private Integer overtimeAmount;

    public Integer getPasueFee() {
        return pasueFee;
    }

    public void setPasueFee(Integer pasueFee) {
        this.pasueFee = pasueFee;
    }

    public Integer getAwardFee() {
        return awardFee;
    }

    public void setAwardFee(Integer awardFee) {
        this.awardFee = awardFee;
    }

    public Integer getOvertimeFee() {
        return overtimeFee;
    }

    public void setOvertimeFee(Integer overtimeFee) {
        this.overtimeFee = overtimeFee;
    }

    public List<OvertimeRecordsBean> getOvertimeRecords() {
        return overtimeRecords;
    }

    public void setOvertimeRecords(List<OvertimeRecordsBean> overtimeRecords) {
        this.overtimeRecords = overtimeRecords;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.pasueFee);
        dest.writeValue(this.awardFee);
        dest.writeValue(this.overtimeFee);
        dest.writeTypedList(overtimeRecords);
        dest.writeValue(this.sharesAmount);
        dest.writeValue(this.overtimeAmount);
    }

    public getMywalletInfo() {
    }

    protected getMywalletInfo(Parcel in) {
        this.pasueFee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.awardFee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overtimeFee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overtimeRecords = in.createTypedArrayList(OvertimeRecordsBean.CREATOR);
        this.sharesAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overtimeAmount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<getMywalletInfo> CREATOR = new Parcelable.Creator<getMywalletInfo>() {
        public getMywalletInfo createFromParcel(Parcel source) {
            return new getMywalletInfo(source);
        }

        public getMywalletInfo[] newArray(int size) {
            return new getMywalletInfo[size];
        }
    };
}
