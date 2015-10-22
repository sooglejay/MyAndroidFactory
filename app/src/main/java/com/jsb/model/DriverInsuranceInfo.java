package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 7.6.2.保存驾驶险订单
 * 发送时机	用户点击去付款时，调该接口，以保存订单信息
 */
public class DriverInsuranceInfo implements Parcelable {

    private Integer id;
    private String  content;
    private Integer  price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.content);
        dest.writeValue(this.price);
    }

    public DriverInsuranceInfo() {
    }

    protected DriverInsuranceInfo(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.content = in.readString();
        this.price = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<DriverInsuranceInfo> CREATOR = new Parcelable.Creator<DriverInsuranceInfo>() {
        public DriverInsuranceInfo createFromParcel(Parcel source) {
            return new DriverInsuranceInfo(source);
        }

        public DriverInsuranceInfo[] newArray(int size) {
            return new DriverInsuranceInfo[size];
        }
    };
}
