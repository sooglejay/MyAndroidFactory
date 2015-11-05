package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 5.1.5.报价历史信息对象HistoryPriceData
 */
public class HistoryPriceData implements Parcelable {

    private List<Insurancecompanyprice> insurancecompanyprice;//报价列表集合参见5.4.2
    private List<Vehicleordertable>  vehicleorderRecords;//报价保单列表参见5.4.3
    private Vehicleordertable  vehicleorderDetail;//某一个保单的具体报价详情
    private Integer  vehicleorderAmount;//历史报价总数

    @Override
    public String toString() {
        return "HistoryPriceData{" +
                "insurancecompanyprice=" + insurancecompanyprice +
                ", vehicleorderRecords=" + vehicleorderRecords +
                ", vehicleorderDetail=" + vehicleorderDetail +
                ", vehicleorderAmount=" + vehicleorderAmount +
                '}';
    }

    public List<Insurancecompanyprice> getInsurancecompanyprice() {
        return insurancecompanyprice;
    }

    public void setInsurancecompanyprice(List<Insurancecompanyprice> insurancecompanyprice) {
        this.insurancecompanyprice = insurancecompanyprice;
    }

    public List<Vehicleordertable> getVehicleorderRecords() {
        return vehicleorderRecords;
    }

    public void setVehicleorderRecords(List<Vehicleordertable> vehicleorderRecords) {
        this.vehicleorderRecords = vehicleorderRecords;
    }

    public Vehicleordertable getVehicleorderDetail() {
        return vehicleorderDetail;
    }

    public void setVehicleorderDetail(Vehicleordertable vehicleorderDetail) {
        this.vehicleorderDetail = vehicleorderDetail;
    }

    public Integer getVehicleorderAmount() {
        return vehicleorderAmount;
    }

    public void setVehicleorderAmount(Integer vehicleorderAmount) {
        this.vehicleorderAmount = vehicleorderAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(insurancecompanyprice);
        dest.writeTypedList(vehicleorderRecords);
        dest.writeParcelable(this.vehicleorderDetail, 0);
        dest.writeValue(this.vehicleorderAmount);
    }

    public HistoryPriceData() {
    }

    protected HistoryPriceData(Parcel in) {
        this.insurancecompanyprice = in.createTypedArrayList(Insurancecompanyprice.CREATOR);
        this.vehicleorderRecords = in.createTypedArrayList(Vehicleordertable.CREATOR);
        this.vehicleorderDetail = in.readParcelable(Vehicleordertable.class.getClassLoader());
        this.vehicleorderAmount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<HistoryPriceData> CREATOR = new Creator<HistoryPriceData>() {
        public HistoryPriceData createFromParcel(Parcel source) {
            return new HistoryPriceData(source);
        }

        public HistoryPriceData[] newArray(int size) {
            return new HistoryPriceData[size];
        }
    };
}
