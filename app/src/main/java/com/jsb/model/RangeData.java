package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 5.1.15.团队排名封装对象RangeData
 */
public class RangeData implements Parcelable {
   private List<RangeRecord> rangeOfYear;//
   private List<RangeRecord> rangeOfMonth;//

    @Override
    public String toString() {
        return "RangeData{" +
                "rangeOfYear=" + rangeOfYear +
                ", rangeOfMonth=" + rangeOfMonth +
                '}';
    }

    public List<RangeRecord> getRangeOfYear() {
        return rangeOfYear;
    }

    public void setRangeOfYear(List<RangeRecord> rangeOfYear) {
        this.rangeOfYear = rangeOfYear;
    }

    public List<RangeRecord> getRangeOfMonth() {
        return rangeOfMonth;
    }

    public void setRangeOfMonth(List<RangeRecord> rangeOfMonth) {
        this.rangeOfMonth = rangeOfMonth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(rangeOfYear);
        dest.writeTypedList(rangeOfMonth);
    }

    public RangeData() {
    }

    protected RangeData(Parcel in) {
        this.rangeOfYear = in.createTypedArrayList(RangeRecord.CREATOR);
        this.rangeOfMonth = in.createTypedArrayList(RangeRecord.CREATOR);
    }

    public static final Creator<RangeData> CREATOR = new Creator<RangeData>() {
        public RangeData createFromParcel(Parcel source) {
            return new RangeData(source);
        }

        public RangeData[] newArray(int size) {
            return new RangeData[size];
        }
    };
}
