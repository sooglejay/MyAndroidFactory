package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class getPauseHistory implements Parcelable {

    private List<ReservehistoryBean>reservehistory;
    private List<LimithistoryBean>limithistory;

    public List<ReservehistoryBean> getReservehistory() {
        return reservehistory;
    }

    public void setReservehistory(List<ReservehistoryBean> reservehistory) {
        this.reservehistory = reservehistory;
    }

    public List<LimithistoryBean> getLimithistory() {
        return limithistory;
    }

    public void setLimithistory(List<LimithistoryBean> limithistory) {
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

    public getPauseHistory() {
    }

    protected getPauseHistory(Parcel in) {
        this.reservehistory = in.createTypedArrayList(ReservehistoryBean.CREATOR);
        this.limithistory = in.createTypedArrayList(LimithistoryBean.CREATOR);
    }

    public static final Parcelable.Creator<getPauseHistory> CREATOR = new Parcelable.Creator<getPauseHistory>() {
        public getPauseHistory createFromParcel(Parcel source) {
            return new getPauseHistory(source);
        }

        public getPauseHistory[] newArray(int size) {
            return new getPauseHistory[size];
        }
    };
}
