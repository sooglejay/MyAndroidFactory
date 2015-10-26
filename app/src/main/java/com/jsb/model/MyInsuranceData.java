package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 5.1.6.我的保险信息对象MyInsuranceData
 */
public class MyInsuranceData implements Parcelable {

    private Driverordertable driverorderRecords;//驾驶险订单集合，参见5.6.1
    private Vehicleordertable vehicleorderRecords;//车险订单集合，参见5.4.3
    private Overtimeordertable overtimeorderRecords;//Overtimeordertable	加班险订单集合，参见5.5.1


    private Integer vehicleorderAmount;//Int	车险记录总数
    private Integer driverorderAmount;//Int	驾驶险记录总数
    private Integer overtimeorderAmount;//Int	加班险记录总数

    @Override
    public String toString() {
        return "MyInsuranceData{" +
                "driverorderRecords=" + driverorderRecords +
                ", vehicleorderRecords=" + vehicleorderRecords +
                ", overtimeorderRecords=" + overtimeorderRecords +
                ", vehicleorderAmount=" + vehicleorderAmount +
                ", driverorderAmount=" + driverorderAmount +
                ", overtimeorderAmount=" + overtimeorderAmount +
                '}';
    }

    public Driverordertable getDriverorderRecords() {
        return driverorderRecords;
    }

    public void setDriverorderRecords(Driverordertable driverorderRecords) {
        this.driverorderRecords = driverorderRecords;
    }

    public Vehicleordertable getVehicleorderRecords() {
        return vehicleorderRecords;
    }

    public void setVehicleorderRecords(Vehicleordertable vehicleorderRecords) {
        this.vehicleorderRecords = vehicleorderRecords;
    }

    public Overtimeordertable getOvertimeorderRecords() {
        return overtimeorderRecords;
    }

    public void setOvertimeorderRecords(Overtimeordertable overtimeorderRecords) {
        this.overtimeorderRecords = overtimeorderRecords;
    }

    public Integer getVehicleorderAmount() {
        return vehicleorderAmount;
    }

    public void setVehicleorderAmount(Integer vehicleorderAmount) {
        this.vehicleorderAmount = vehicleorderAmount;
    }

    public Integer getDriverorderAmount() {
        return driverorderAmount;
    }

    public void setDriverorderAmount(Integer driverorderAmount) {
        this.driverorderAmount = driverorderAmount;
    }

    public Integer getOvertimeorderAmount() {
        return overtimeorderAmount;
    }

    public void setOvertimeorderAmount(Integer overtimeorderAmount) {
        this.overtimeorderAmount = overtimeorderAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.driverorderRecords, 0);
        dest.writeParcelable(this.vehicleorderRecords, 0);
        dest.writeParcelable(this.overtimeorderRecords, 0);
        dest.writeValue(this.vehicleorderAmount);
        dest.writeValue(this.driverorderAmount);
        dest.writeValue(this.overtimeorderAmount);
    }

    public MyInsuranceData() {
    }

    protected MyInsuranceData(Parcel in) {
        this.driverorderRecords = in.readParcelable(Driverordertable.class.getClassLoader());
        this.vehicleorderRecords = in.readParcelable(Vehicleordertable.class.getClassLoader());
        this.overtimeorderRecords = in.readParcelable(Overtimeordertable.class.getClassLoader());
        this.vehicleorderAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.driverorderAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overtimeorderAmount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<MyInsuranceData> CREATOR = new Parcelable.Creator<MyInsuranceData>() {
        public MyInsuranceData createFromParcel(Parcel source) {
            return new MyInsuranceData(source);
        }

        public MyInsuranceData[] newArray(int size) {
            return new MyInsuranceData[size];
        }
    };
}
