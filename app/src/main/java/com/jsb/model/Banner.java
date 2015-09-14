package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/9/7.
 */
public class Banner implements Parcelable {
    /**
     * 广告图片地址
     */
    private String file_location;
    /**
     * 广告URL
     */
    private String url;
    public String getFile_location() {
        return file_location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFile_location(String file_location) {
        this.file_location = file_location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.file_location);
        dest.writeString(this.url);
    }

    public Banner() {
    }

    protected Banner(Parcel in) {
        this.file_location = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Banner> CREATOR = new Parcelable.Creator<Banner>() {
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
}
