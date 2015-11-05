package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 5.1.11.报案信息对象ReportData
 */
public class ReportData implements Parcelable {

    private ReportableInsurance reportableInsurance;

    @Override
    public String toString() {
        return "ReportData{" +
                "reportableInsurance=" + reportableInsurance +
                '}';
    }

    public ReportableInsurance getReportableInsurance() {
        return reportableInsurance;
    }

    public void setReportableInsurance(ReportableInsurance reportableInsurance) {
        this.reportableInsurance = reportableInsurance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.reportableInsurance, 0);
    }

    public ReportData() {
    }

    protected ReportData(Parcel in) {
        this.reportableInsurance = in.readParcelable(ReportableInsurance.class.getClassLoader());
    }

    public static final Creator<ReportData> CREATOR = new Creator<ReportData>() {
        public ReportData createFromParcel(Parcel source) {
            return new ReportData(source);
        }

        public ReportData[] newArray(int size) {
            return new ReportData[size];
        }
    };
}
