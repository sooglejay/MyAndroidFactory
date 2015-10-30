package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 5.1.6.我的保险信息对象MyInsuranceData
 */
public class MyInsuranceData implements Parcelable {

    private List<Driverordertable> driverorderRecords;//驾驶险订单集合，参见5.6.1
    private List<Vehicleordertable> vehicleorderRecords;//车险订单集合，参见5.4.3
    private List<Overtimeordertable> overtimeorderRecords;//Overtimeordertable	加班险订单集合，参见5.5.1

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

    public List<Driverordertable> getDriverorderRecords() {
        return driverorderRecords;
    }

    public void setDriverorderRecords(List<Driverordertable> driverorderRecords) {
        this.driverorderRecords = driverorderRecords;
    }

    public List<Vehicleordertable> getVehicleorderRecords() {
        return vehicleorderRecords;
    }

    public void setVehicleorderRecords(List<Vehicleordertable> vehicleorderRecords) {
        this.vehicleorderRecords = vehicleorderRecords;
    }

    public List<Overtimeordertable> getOvertimeorderRecords() {
        return overtimeorderRecords;
    }

    public void setOvertimeorderRecords(List<Overtimeordertable> overtimeorderRecords) {
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

    public static Creator<MyInsuranceData> getCREATOR() {
        return CREATOR;
    }

    public MyInsuranceData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(driverorderRecords);
        dest.writeTypedList(vehicleorderRecords);
        dest.writeTypedList(overtimeorderRecords);
        dest.writeValue(this.vehicleorderAmount);
        dest.writeValue(this.driverorderAmount);
        dest.writeValue(this.overtimeorderAmount);
    }

    protected MyInsuranceData(Parcel in) {
        this.driverorderRecords = in.createTypedArrayList(Driverordertable.CREATOR);
        this.vehicleorderRecords = in.createTypedArrayList(Vehicleordertable.CREATOR);
        this.overtimeorderRecords = in.createTypedArrayList(Overtimeordertable.CREATOR);
        this.vehicleorderAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.driverorderAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overtimeorderAmount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<MyInsuranceData> CREATOR = new Creator<MyInsuranceData>() {
        public MyInsuranceData createFromParcel(Parcel source) {
            return new MyInsuranceData(source);
        }

        public MyInsuranceData[] newArray(int size) {
            return new MyInsuranceData[size];
        }
    };
}
