package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class submitPhone implements Parcelable {
    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.verifyCode);
    }

    public submitPhone() {
    }

    protected submitPhone(Parcel in) {
        this.verifyCode = in.readString();
    }

    public static final Parcelable.Creator<submitPhone> CREATOR = new Parcelable.Creator<submitPhone>() {
        public submitPhone createFromParcel(Parcel source) {
            return new submitPhone(source);
        }

        public submitPhone[] newArray(int size) {
            return new submitPhone[size];
        }
    };
}
