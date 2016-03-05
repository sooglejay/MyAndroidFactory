package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sooglejay on 16/3/1.
 */
public class ConfirmOrderBean implements Parcelable {
//    {"commerceNo":"WX5121303802016000336","ConfirmCompulsoryNo":"WX5121303602016000317"}
    private String commerceNo;
    private String compulsoryNo;

    @Override
    public String toString() {
        return "ConfirmOrderBean{" +
                "commerceNo='" + commerceNo + '\'' +
                ", ConfirmCompulsoryNo='" + compulsoryNo + '\'' +
                '}';
    }

    public String getCommerceNo() {
        return commerceNo;
    }

    public void setCommerceNo(String commerceNo) {
        this.commerceNo = commerceNo;
    }

    public String getCompulsoryNo() {
        return compulsoryNo;
    }

    public void setCompulsoryNo(String compulsoryNo) {
        this.compulsoryNo = compulsoryNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.commerceNo);
        dest.writeString(this.compulsoryNo);
    }

    public ConfirmOrderBean() {
    }

    protected ConfirmOrderBean(Parcel in) {
        this.commerceNo = in.readString();
        this.compulsoryNo = in.readString();
    }

    public static final Parcelable.Creator<ConfirmOrderBean> CREATOR = new Parcelable.Creator<ConfirmOrderBean>() {
        public ConfirmOrderBean createFromParcel(Parcel source) {
            return new ConfirmOrderBean(source);
        }

        public ConfirmOrderBean[] newArray(int size) {
            return new ConfirmOrderBean[size];
        }
    };






}
