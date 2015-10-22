package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 发送时机	获取加班明细
 */
public class getMyOvertimeInfo implements Parcelable {

    private Integer pasueFee;
    private Integer awardFee;
    private Integer overtimeFee;
    private Integer overtimeAmount;
    private Integer sharesAmount;
    private List<OvertimeRecordsBean> overtimeRecords;

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

    public Integer getOvertimeAmount() {
        return overtimeAmount;
    }

    public void setOvertimeAmount(Integer overtimeAmount) {
        this.overtimeAmount = overtimeAmount;
    }

    public Integer getSharesAmount() {
        return sharesAmount;
    }

    public void setSharesAmount(Integer sharesAmount) {
        this.sharesAmount = sharesAmount;
    }

    public List<OvertimeRecordsBean> getOvertimeRecords() {
        return overtimeRecords;
    }

    public void setOvertimeRecords(List<OvertimeRecordsBean> overtimeRecords) {
        this.overtimeRecords = overtimeRecords;
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
        dest.writeValue(this.overtimeAmount);
        dest.writeValue(this.sharesAmount);
        dest.writeTypedList(overtimeRecords);
    }

    public getMyOvertimeInfo() {
    }

    protected getMyOvertimeInfo(Parcel in) {
        this.pasueFee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.awardFee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overtimeFee = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overtimeAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sharesAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overtimeRecords = in.createTypedArrayList(OvertimeRecordsBean.CREATOR);
    }

    public static final Parcelable.Creator<getMyOvertimeInfo> CREATOR = new Parcelable.Creator<getMyOvertimeInfo>() {
        public getMyOvertimeInfo createFromParcel(Parcel source) {
            return new getMyOvertimeInfo(source);
        }

        public getMyOvertimeInfo[] newArray(int size) {
            return new getMyOvertimeInfo[size];
        }
    };
}
