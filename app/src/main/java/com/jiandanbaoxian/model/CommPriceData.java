package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sooglejay on 16/2/18.
 *
 * 报价信息封装CommPriceData

 */
public class CommPriceData implements Parcelable {

    /**
     * huanPriceData	HuanPriceData 5.4.11	保险公司报价	Y
     */
    private HuanPriceData huanPriceData;

    public HuanPriceData getHuanPriceData() {
        return huanPriceData;
    }

    public void setHuanPriceData(HuanPriceData huanPriceData) {

        this.huanPriceData = huanPriceData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.huanPriceData, 0);
    }

    public CommPriceData() {
    }

    protected CommPriceData(Parcel in) {
        this.huanPriceData = in.readParcelable(HuanPriceData.class.getClassLoader());
    }

    public static final Parcelable.Creator<CommPriceData> CREATOR = new Parcelable.Creator<CommPriceData>() {
        public CommPriceData createFromParcel(Parcel source) {
            return new CommPriceData(source);
        }

        public CommPriceData[] newArray(int size) {
            return new CommPriceData[size];
        }
    };
}
