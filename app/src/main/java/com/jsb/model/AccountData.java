package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.1.2.账户信息对象AccountData
 */
public class AccountData implements Parcelable {


    private String account;//提现账号	Y
    private Integer type;//提现账号的类型   0 银联   1 支付宝   2微信	Y
    private String union;//提现银行名字，支付宝微信，值为支付宝微信	Y

    @Override
    public String toString() {
        return "AccountData{" +
                "account='" + account + '\'' +
                ", type=" + type +
                ", union='" + union + '\'' +
                '}';
    }

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

    public AccountData() {
    }

    protected AccountData(Parcel in) {
        this.account = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.union = in.readString();
    }

    public static final Parcelable.Creator<AccountData> CREATOR = new Parcelable.Creator<AccountData>() {
        public AccountData createFromParcel(Parcel source) {
            return new AccountData(source);
        }

        public AccountData[] newArray(int size) {
            return new AccountData[size];
        }
    };
}
