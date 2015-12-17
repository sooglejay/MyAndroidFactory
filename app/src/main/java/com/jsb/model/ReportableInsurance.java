package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/25.
 */
public class ReportableInsurance implements Parcelable {
    private List<Vehicleordertable> vehicleReportableData;
    private List<Driverordertable> driverReportableData;
    private Overtimeordertable overtimeReportableData;

    @Override
    public String toString() {
        return "ReportableInsurance{" +
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

    public Overtimeordertable getOvertimeReportableData() {
        return overtimeReportableData;
    }

    public void setOvertimeReportableData(Overtimeordertable overtimeReportableData) {
        this.overtimeReportableData = overtimeReportableData;
    }

    public ReportableInsurance() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(vehicleReportableData);
        dest.writeTypedList(driverReportableData);
        dest.writeParcelable(this.overtimeReportableData, 0);
    }

    protected ReportableInsurance(Parcel in) {
        this.vehicleReportableData = in.createTypedArrayList(Vehicleordertable.CREATOR);
        this.driverReportableData = in.createTypedArrayList(Driverordertable.CREATOR);
        this.overtimeReportableData = in.readParcelable(Overtimeordertable.class.getClassLoader());
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
