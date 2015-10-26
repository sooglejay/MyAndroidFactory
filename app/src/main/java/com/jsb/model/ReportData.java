package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 5.1.11.报案信息对象ReportData
 */
public class ReportData implements Parcelable {
    List<Vehicleordertable>vehicleReportableData;
    List<Driverordertable>driverReportableData;
    List<Overtimeordertable>overtimeReportableData;

    @Override
    public String toString() {
        return "ReportData{" +
                "vehicleReportableData=" + vehicleReportableData +
                ", driverReportableData=" + driverReportableData +
                ", overtimeReportableData=" + overtimeReportableData +
                '}';
    }

    public List<Vehicleordertable> getVehicleReportableData() {
        return vehicleReportableData;
    }

    public void setVehicleReportableData(List<Vehicleordertable> vehicleReportableData) {
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

    public ReportData() {
    }

    protected ReportData(Parcel in) {
        this.vehicleReportableData = in.createTypedArrayList(Vehicleordertable.CREATOR);
        this.driverReportableData = in.createTypedArrayList(Driverordertable.CREATOR);
        this.overtimeReportableData = in.createTypedArrayList(Overtimeordertable.CREATOR);
    }

    public static final Parcelable.Creator<ReportData> CREATOR = new Parcelable.Creator<ReportData>() {
        public ReportData createFromParcel(Parcel source) {
            return new ReportData(source);
        }

        public ReportData[] newArray(int size) {
            return new ReportData[size];
        }
    };
}
