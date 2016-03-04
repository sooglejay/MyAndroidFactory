package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sooglejay on 16/3/4.
 */
public class HuanQueryStatusData implements Parcelable {
    /**

     名称	类型	说明	不可为空
     ply_app_no	String	投保单号	Y
     pay_status	int	改单状态码：-2 待核保;-1 预核保待付费;0 未确认未收款;1 已确认未收款;5 已确认已收款.	Y
     payStr	String	状态对应的含义
     */
    private String ply_app_no;
    private int pay_status;
    private int payStr;

    @Override
    public String toString() {
        return "HuanQueryStatusData{" +
                "ply_app_no='" + ply_app_no + '\'' +
                ", pay_status=" + pay_status +
                ", payStr=" + payStr +
                '}';
    }

    public String getPly_app_no() {
        return ply_app_no;
    }

    public void setPly_app_no(String ply_app_no) {
        this.ply_app_no = ply_app_no;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public int getPayStr() {
        return payStr;
    }

    public void setPayStr(int payStr) {
        this.payStr = payStr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ply_app_no);
        dest.writeInt(this.pay_status);
        dest.writeInt(this.payStr);
    }

    public HuanQueryStatusData() {
    }

    protected HuanQueryStatusData(Parcel in) {
        this.ply_app_no = in.readString();
        this.pay_status = in.readInt();
        this.payStr = in.readInt();
    }

    public static final Parcelable.Creator<HuanQueryStatusData> CREATOR = new Parcelable.Creator<HuanQueryStatusData>() {
        public HuanQueryStatusData createFromParcel(Parcel source) {
            return new HuanQueryStatusData(source);
        }

        public HuanQueryStatusData[] newArray(int size) {
            return new HuanQueryStatusData[size];
        }
    };
}
