package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/31.
 */
public class PauseHistory implements Parcelable {
    private List<Reservepausetable> reservehistory;
    private List<Limitpausetable> limithistory;

    @Override
    public String toString() {
        return "PauseHistory{" +
                "reservehistory=" + reservehistory +
                ", limithistory=" + limithistory +
                '}';
    }

    public List<Reservepausetable> getReservehistory() {
        return reservehistory;
    }

    public void setReservehistory(List<Reservepausetable> reservehistory) {
        this.reservehistory = reservehistory;
    }

    public List<Limitpausetable> getLimithistory() {
        return limithistory;
    }

    public void setLimithistory(List<Limitpausetable> limithistory) {
        this.limithistory = limithistory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(reservehistory);
        dest.writeTypedList(limithistory);
    }

    public PauseHistory() {
    }

    protected PauseHistory(Parcel in) {
        this.reservehistory = in.createTypedArrayList(Reservepausetable.CREATOR);
        this.limithistory = in.createTypedArrayList(Limitpausetable.CREATOR);
    }

    public static final Parcelable.Creator<PauseHistory> CREATOR = new Parcelable.Creator<PauseHistory>() {
        public PauseHistory createFromParcel(Parcel source) {
            return new PauseHistory(source);
        }

        public PauseHistory[] newArray(int size) {
            return new PauseHistory[size];
        }
    };
}
