package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sooglejay on 16/2/24.
 */
public class CarModelBean implements Parcelable {
    private String carModelName;
    private String carPrice;
    private int id;

    @Override
    public String toString() {
        return "CarModelBean{" +
                "carModelName='" + carModelName + '\'' +
                ", carPrice='" + carPrice + '\'' +
                ", id=" + id +
                ", isSelected=" + isSelected +
                '}';
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    private boolean isSelected;

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(String carPrice) {
        this.carPrice = carPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.carModelName);
        dest.writeString(this.carPrice);
        dest.writeInt(this.id);
        dest.writeByte(isSelected ? (byte) 1 : (byte) 0);
    }

    public CarModelBean() {
    }

    protected CarModelBean(Parcel in) {
        this.carModelName = in.readString();
        this.carPrice = in.readString();
        this.id = in.readInt();
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CarModelBean> CREATOR = new Parcelable.Creator<CarModelBean>() {
        public CarModelBean createFromParcel(Parcel source) {
            return new CarModelBean(source);
        }

        public CarModelBean[] newArray(int size) {
            return new CarModelBean[size];
        }
    };
}
