package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/25.
 */
public class ReportableInsurance implements Parcelable {
    private List<VehicleReportableData> vehicleReportableData;
    private List<Driverordertable> driverReportableData;
    private List<Overtimeordertable> overtimeReportableData;

    @Override
    public String toString() {
        return "ReportableInsurance{" +
                "vehicleReportableData=" + vehicleReportableData +
                ", driverReportableData=" + driverReportableData +
                ", overtimeReportableData=" + overtimeReportableData +
                '}';
    }

    public List<VehicleReportableData> getVehicleReportableData() {
        return vehicleReportableData;
    }

    public void setVehicleReportableData(List<VehicleReportableData> vehicleReportableData) {
        this.vehicleReportableData = vehicleReportableData;
    }

    public List<Driverordertable> getDriverReportableData() {
        return driverReportableData;
    }

    public void setDriverReportableData(List<Driverordertable> driverReportableData) {
        this.driverReportableData = driverReportableData;
    }

    public List<Overtimeordertable> getOvertimeReportableData() {
        return overtimeReportableData;
    }

    public void setOvertimeReportableData(List<Overtimeordertable> overtimeReportableData) {
        this.overtimeReportableData = overtimeReportableData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(vehicleReportableData);
        dest.writeTypedList(driverReportableData);
        dest.writeTypedList(overtimeReportableData);
    }

    public ReportableInsurance() {
    }

    protected ReportableInsurance(Parcel in) {
        this.vehicleReportableData = in.createTypedArrayList(VehicleReportableData.CREATOR);
        this.driverReportableData = in.createTypedArrayList(Driverordertable.CREATOR);
        this.overtimeReportableData = in.createTypedArrayList(Overtimeordertable.CREATOR);
    }

    public static final Creator<ReportableInsurance> CREATOR = new Creator<ReportableInsurance>() {
        public ReportableInsurance createFromParcel(Parcel source) {
            return new ReportableInsurance(source);
        }

        public ReportableInsurance[] newArray(int size) {
            return new ReportableInsurance[size];
        }
    };
}
