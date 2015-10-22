package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class getLastAccountInfo implements Parcelable {

    private String account;
    private Integer type;
    private String union;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.account);
        dest.writeValue(this.type);
        dest.writeString(this.union);
    }

    public getLastAccountInfo() {
    }

    protected getLastAccountInfo(Parcel in) {
        this.account = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.union = in.readString();
    }

    public static final Parcelable.Creator<getLastAccountInfo> CREATOR = new Parcelable.Creator<getLastAccountInfo>() {
        public getLastAccountInfo createFromParcel(Parcel source) {
            return new getLastAccountInfo(source);
        }

        public getLastAccountInfo[] newArray(int size) {
            return new getLastAccountInfo[size];
        }
    };
}
