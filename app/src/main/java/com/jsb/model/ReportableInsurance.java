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

    @Override
    public String toString() {
        return "ReportableInsurance{" +
                "vehicleReportableData=" + vehicleReportableData +
                ", driverReportableData=" + driverReportableData +
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.vehicleReportableData);
        dest.writeTypedList(driverReportableData);
    }

    public ReportableInsurance() {
    }

    protected ReportableInsurance(Parcel in) {
        this.vehicleReportableData = new ArrayList<VehicleReportableData>();
        in.readList(this.vehicleReportableData, List.class.getClassLoader());
        this.driverReportableData = in.createTypedArrayList(Driverordertable.CREATOR);
    }

    public static final Parcelable.Creator<ReportableInsurance> CREATOR = new Parcelable.Creator<ReportableInsurance>() {
        public ReportableInsurance createFromParcel(Parcel source) {
            return new ReportableInsurance(source);
        }

        public ReportableInsurance[] newArray(int size) {
            return new ReportableInsurance[size];
        }
    };
}
