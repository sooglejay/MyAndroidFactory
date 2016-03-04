package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sooglejay on 16/3/4.
 */
public class ApplyPayBean implements Parcelable {
    private String ply_app_no;

    @Override
    public String toString() {
        return "ApplyPayBean{" +
                "ply_app_no='" + ply_app_no + '\'' +
                '}';
    }

    public String getPly_app_no() {
        return ply_app_no;
    }

    public void setPly_app_no(String ply_app_no) {
        this.ply_app_no = ply_app_no;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ply_app_no);
    }

    public ApplyPayBean() {
    }

    protected ApplyPayBean(Parcel in) {
        this.ply_app_no = in.readString();
    }

    public static final Parcelable.Creator<ApplyPayBean> CREATOR = new Parcelable.Creator<ApplyPayBean>() {
        public ApplyPayBean createFromParcel(Parcel source) {
            return new ApplyPayBean(source);
        }

        public ApplyPayBean[] newArray(int size) {
            return new ApplyPayBean[size];
        }
    };
}
