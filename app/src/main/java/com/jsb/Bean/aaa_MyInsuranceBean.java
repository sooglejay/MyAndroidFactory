package com.jsb.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/30.
 */
public class aaa_MyInsuranceBean implements Parcelable {
    private String insuranceName;
    private String insuranceCompanyName;
    private float insuranceMoney;
    private long insuranceBuyDate;

    @Override
    public String toString() {
        return "aaa_MyInsuranceBean{" +
                "insuranceName=" + insuranceName +
                ", insuranceCompanyName=" + insuranceCompanyName  +
                ", insuranceMoney=" + insuranceMoney +
                ", insuranceBuyDate=" + insuranceBuyDate +
                '}';
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public String getInsuranceCompanyName() {
        return insuranceCompanyName;
    }

    public void setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
    }

    public float getInsuranceMoney() {
        return insuranceMoney;
    }

    public void setInsuranceMoney(float insuranceMoney) {
        this.insuranceMoney = insuranceMoney;
    }

    public long getInsuranceBuyDate() {
        return insuranceBuyDate;
    }

    public void setInsuranceBuyDate(long insuranceBuyDate) {
        this.insuranceBuyDate = insuranceBuyDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.insuranceName);
        dest.writeString(this.insuranceCompanyName);
        dest.writeFloat(this.insuranceMoney);
        dest.writeLong(this.insuranceBuyDate);
    }

    public aaa_MyInsuranceBean() {
    }

    protected aaa_MyInsuranceBean(Parcel in) {
        this.insuranceName = in.readString();
        this.insuranceCompanyName = in.readString();
        this.insuranceMoney = in.readFloat();
        this.insuranceBuyDate = in.readLong();
    }

    public static final Parcelable.Creator<aaa_MyInsuranceBean> CREATOR = new Parcelable.Creator<aaa_MyInsuranceBean>() {
        public aaa_MyInsuranceBean createFromParcel(Parcel source) {
            return new aaa_MyInsuranceBean(source);
        }

        public aaa_MyInsuranceBean[] newArray(int size) {
            return new aaa_MyInsuranceBean[size];
        }
    };
}
