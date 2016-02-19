package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sooglejay on 16/2/18.
 * 华安保险基本信息HuanInsuranceBaseInfoData

 */
public class HuanInsuranceBaseInfoData implements Parcelable {
    /**
     *
     *
     query_sequence_no	String	保险查询码	Y
     amount	Float	保额合计	Y
     premium	Float	保费合计
     cal_app_no	String	报价单号
     */
    private String query_sequence_no;
    private Float amount;
    private Float premium;
    private String cal_app_no;

    public String getQuery_sequence_no() {
        return query_sequence_no;
    }

    public void setQuery_sequence_no(String query_sequence_no) {
        this.query_sequence_no = query_sequence_no;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getPremium() {
        return premium;
    }

    public void setPremium(Float premium) {
        this.premium = premium;
    }

    public String getCal_app_no() {
        return cal_app_no;
    }

    public void setCal_app_no(String cal_app_no) {
        this.cal_app_no = cal_app_no;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.query_sequence_no);
        dest.writeValue(this.amount);
        dest.writeValue(this.premium);
        dest.writeString(this.cal_app_no);
    }

    public HuanInsuranceBaseInfoData() {
    }

    protected HuanInsuranceBaseInfoData(Parcel in) {
        this.query_sequence_no = in.readString();
        this.amount = (Float) in.readValue(Float.class.getClassLoader());
        this.premium = (Float) in.readValue(Float.class.getClassLoader());
        this.cal_app_no = in.readString();
    }

    public static final Parcelable.Creator<HuanInsuranceBaseInfoData> CREATOR = new Parcelable.Creator<HuanInsuranceBaseInfoData>() {
        public HuanInsuranceBaseInfoData createFromParcel(Parcel source) {
            return new HuanInsuranceBaseInfoData(source);
        }

        public HuanInsuranceBaseInfoData[] newArray(int size) {
            return new HuanInsuranceBaseInfoData[size];
        }
    };
}
