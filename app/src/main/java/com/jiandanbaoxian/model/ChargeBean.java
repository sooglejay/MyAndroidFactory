package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JiangWei on 2015/10/22.
 */
public class ChargeBean implements Parcelable {
    public int status;
    public Object message;
    private String orderid;
    public Charge charge;

    @Override
    public String toString() {
        return "ChargeBean{" +
                "status=" + status +
                ", message=" + message +
                ", orderid='" + orderid + '\'' +
                ", charge=" + charge +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeParcelable((Parcelable) this.message, flags);
        dest.writeString(this.orderid);
        dest.writeParcelable(this.charge, 0);
    }

    public ChargeBean() {
    }

    protected ChargeBean(Parcel in) {
        this.status = in.readInt();
        this.message = in.readParcelable(Object.class.getClassLoader());
        this.orderid = in.readString();
        this.charge = in.readParcelable(Charge.class.getClassLoader());
    }

    public static final Parcelable.Creator<ChargeBean> CREATOR = new Parcelable.Creator<ChargeBean>() {
        public ChargeBean createFromParcel(Parcel source) {
            return new ChargeBean(source);
        }

        public ChargeBean[] newArray(int size) {
            return new ChargeBean[size];
        }
    };
}
