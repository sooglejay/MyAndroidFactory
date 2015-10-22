package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 7.6.2.保存驾驶险订单
 * 用户点击去付款时，调该接口，以保存订单信息
 */
public class saveDriverInsuranceOrder implements Parcelable {

    private DriverInsuranceInfo driverInsuranceInfo;

    public DriverInsuranceInfo getDriverInsuranceInfo() {
        return driverInsuranceInfo;
    }

    public void setDriverInsuranceInfo(DriverInsuranceInfo driverInsuranceInfo) {
        this.driverInsuranceInfo = driverInsuranceInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.driverInsuranceInfo, 0);
    }

    public saveDriverInsuranceOrder() {
    }

    protected saveDriverInsuranceOrder(Parcel in) {
        this.driverInsuranceInfo = in.readParcelable(DriverInsuranceInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<saveDriverInsuranceOrder> CREATOR = new Parcelable.Creator<saveDriverInsuranceOrder>() {
        public saveDriverInsuranceOrder createFromParcel(Parcel source) {
            return new saveDriverInsuranceOrder(source);
        }

        public saveDriverInsuranceOrder[] newArray(int size) {
            return new saveDriverInsuranceOrder[size];
        }
    };
}
