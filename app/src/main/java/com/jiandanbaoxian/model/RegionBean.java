package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sooglejay on 16/2/18.
 */
public class RegionBean implements Parcelable {
    /**
     *  {
     "regionNum": "110000",
     "regionName": "北京市"
     },
     {
     "regionNum": "130000",
     "regionName": "河北省"
     },
     ...

     */
    private String regionNum;
    private String regionName;

    public String getRegionNum() {
        return regionNum;
    }

    public void setRegionNum(String regionNum) {
        this.regionNum = regionNum;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.regionNum);
        dest.writeString(this.regionName);
    }

    public RegionBean() {
    }

    protected RegionBean(Parcel in) {
        this.regionNum = in.readString();
        this.regionName = in.readString();
    }

    public static final Parcelable.Creator<RegionBean> CREATOR = new Parcelable.Creator<RegionBean>() {
        public RegionBean createFromParcel(Parcel source) {
            return new RegionBean(source);
        }

        public RegionBean[] newArray(int size) {
            return new RegionBean[size];
        }
    };
}
