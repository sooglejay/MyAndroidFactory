package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sooglejay on 16/3/5.
 */
public class ErrorData implements Parcelable {

//    {
//        "status":404,
//            "message":"华安返回 ： 商业险提交核保返回信息： 2 ,此标的车已在我司承保，请核实",
//            "data":""
//    }

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorData{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.message);
        dest.writeParcelable((Parcelable) this.data, flags);
    }

    public ErrorData() {
    }

    protected ErrorData(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.data = in.readParcelable(Object.class.getClassLoader());
    }

    public static final Creator<ErrorData> CREATOR = new Creator<ErrorData>() {
        public ErrorData createFromParcel(Parcel source) {
            return new ErrorData(source);
        }

        public ErrorData[] newArray(int size) {
            return new ErrorData[size];
        }
    };
}
