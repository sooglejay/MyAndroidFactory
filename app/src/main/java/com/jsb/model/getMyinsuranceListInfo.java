package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class getMyinsuranceListInfo implements Parcelable {


    private List<DriverorderRecordsBean>driverorderRecords;
    private List<VehicleorderRecordsBean>vehicleorderRecords;
    private List<OvertimeRecordsBean>overtimeorderRecords;
    private Integer vehicleorderAmount;
    private Integer driverorderAmount;
    private Integer overtimeorderAmount;

    public List<DriverorderRecordsBean> getDriverorderRecords() {
        return driverorderRecords;
    }

    public void setDriverorderRecords(List<DriverorderRecordsBean> driverorderRecords) {
        this.driverorderRecords = driverorderRecords;
    }

    public List<VehicleorderRecordsBean> getVehicleorderRecords() {
        return vehicleorderRecords;
    }

    public void setVehicleorderRecords(List<VehicleorderRecordsBean> vehicleorderRecords) {
        this.vehicleorderRecords = vehicleorderRecords;
    }

    public List<OvertimeRecordsBean> getOvertimeorderRecords() {
        return overtimeorderRecords;
    }

    public void setOvertimeorderRecords(List<OvertimeRecordsBean> overtimeorderRecords) {
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
        dest.writeTypedList(driverorderRecords);
        dest.writeTypedList(vehicleorderRecords);
        dest.writeTypedList(overtimeorderRecords);
        dest.writeValue(this.vehicleorderAmount);
        dest.writeValue(this.driverorderAmount);
        dest.writeValue(this.overtimeorderAmount);
    }

    public getMyinsuranceListInfo() {
    }

    protected getMyinsuranceListInfo(Parcel in) {
        this.driverorderRecords = in.createTypedArrayList(DriverorderRecordsBean.CREATOR);
        this.vehicleorderRecords = in.createTypedArrayList(VehicleorderRecordsBean.CREATOR);
        this.overtimeorderRecords = in.createTypedArrayList(OvertimeRecordsBean.CREATOR);
        this.vehicleorderAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.driverorderAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.overtimeorderAmount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<getMyinsuranceListInfo> CREATOR = new Parcelable.Creator<getMyinsuranceListInfo>() {
        public getMyinsuranceListInfo createFromParcel(Parcel source) {
            return new getMyinsuranceListInfo(source);
        }

        public getMyinsuranceListInfo[] newArray(int size) {
            return new getMyinsuranceListInfo[size];
        }
    };
}
