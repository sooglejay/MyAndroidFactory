package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.1.8.加班险信息对象OvertimeData
 */
public class OvertimeData implements Parcelable {
    private Overtimeinsurance overtimeinsurance;//加班险信息

    @Override
    public String toString() {
        return "OvertimeData{" +
                "overtimeinsurance=" + overtimeinsurance +
                '}';
    }

    public Overtimeinsurance getOvertimeInsurance() {
        return overtimeinsurance;
    }

    public void setOvertimeInsurance(Overtimeinsurance overtimeInsurance) {
        this.overtimeinsurance = overtimeInsurance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.overtimeinsurance, 0);
    }

    public OvertimeData() {
    }

    protected OvertimeData(Parcel in) {
        this.overtimeinsurance = in.readParcelable(Overtimeinsurance.class.getClassLoader());
    }

    public static final Parcelable.Creator<OvertimeData> CREATOR = new Parcelable.Creator<OvertimeData>() {
        public OvertimeData createFromParcel(Parcel source) {
            return new OvertimeData(source);
        }

        public OvertimeData[] newArray(int size) {
            return new OvertimeData[size];
        }
    };
}
