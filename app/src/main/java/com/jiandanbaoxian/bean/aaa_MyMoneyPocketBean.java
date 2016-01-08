package com.jiandanbaoxian.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/23.
 */
public class aaa_MyMoneyPocketBean implements Parcelable {
    private int status;//标志
    private int type;//提现 的金额类型     int type; //提现类型 停保费 1；加班险费2 ；奖励费3。

    @Override
    public String toString() {
        return "aaa_MyMoneyPocketBean{" +
                "status=" + status +
                ", type=" + type +
                ", moneyKind='" + moneyKind + '\'' +
                ", moneyAmount=" + moneyAmount +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private String moneyKind;//收益名称
    private float moneyAmount;//收益金额

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMoneyKind() {
        return moneyKind;
    }

    public void setMoneyKind(String moneyKind) {
        this.moneyKind = moneyKind;
    }

    public float getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(float moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeInt(this.type);
        dest.writeString(this.moneyKind);
        dest.writeFloat(this.moneyAmount);
    }

    public aaa_MyMoneyPocketBean() {
    }

    protected aaa_MyMoneyPocketBean(Parcel in) {
        this.status = in.readInt();
        this.type = in.readInt();
        this.moneyKind = in.readString();
        this.moneyAmount = in.readFloat();
    }

    public static final Parcelable.Creator<aaa_MyMoneyPocketBean> CREATOR = new Parcelable.Creator<aaa_MyMoneyPocketBean>() {
        public aaa_MyMoneyPocketBean createFromParcel(Parcel source) {
            return new aaa_MyMoneyPocketBean(source);
        }

        public aaa_MyMoneyPocketBean[] newArray(int size) {
            return new aaa_MyMoneyPocketBean[size];
        }
    };
}
