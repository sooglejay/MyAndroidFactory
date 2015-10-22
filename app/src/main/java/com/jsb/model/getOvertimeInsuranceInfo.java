package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 7.5.加班险
 7.5.1.获取加班险信息

 * 获取在售加班险信息
 */

public class getOvertimeInsuranceInfo implements Parcelable {

    private Overtimeinsurance overtimeinsurance;

    public Overtimeinsurance getOvertimeinsurance() {
        return overtimeinsurance;
    }

    public void setOvertimeinsurance(Overtimeinsurance overtimeinsurance) {
        this.overtimeinsurance = overtimeinsurance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.overtimeinsurance, 0);
    }

    public getOvertimeInsuranceInfo() {
    }

    protected getOvertimeInsuranceInfo(Parcel in) {
        this.overtimeinsurance = in.readParcelable(Overtimeinsurance.class.getClassLoader());
    }

    public static final Parcelable.Creator<getOvertimeInsuranceInfo> CREATOR = new Parcelable.Creator<getOvertimeInsuranceInfo>() {
        public getOvertimeInsuranceInfo createFromParcel(Parcel source) {
            return new getOvertimeInsuranceInfo(source);
        }

        public getOvertimeInsuranceInfo[] newArray(int size) {
            return new getOvertimeInsuranceInfo[size];
        }
    };
}
