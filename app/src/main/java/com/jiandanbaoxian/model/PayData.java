package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.1.10.Ping++支付信息对象PayData
 */
public class PayData implements Parcelable {

    private Integer status;//int	200成功 404失败 500异常
    private Integer orderid;//int	订单编号
    private String message;//String	对应状态的信息
    private Charge charge;//Ping++支付对象，参见5.2

    @Override
    public String toString() {
        return "PayData{" +
                "status=" + status +
                ", orderid=" + orderid +
                ", message='" + message + '\'' +
                ", charge=" + charge +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        dest.writeValue(this.status);
        dest.writeValue(this.orderid);
        dest.writeString(this.message);
        dest.writeParcelable(this.charge, 0);
    }

    public PayData() {
    }

    protected PayData(Parcel in) {
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.message = in.readString();
        this.charge = in.readParcelable(Charge.class.getClassLoader());
    }

    public static final Parcelable.Creator<PayData> CREATOR = new Parcelable.Creator<PayData>() {
        public PayData createFromParcel(Parcel source) {
            return new PayData(source);
        }

        public PayData[] newArray(int size) {
            return new PayData[size];
        }
    };
}
