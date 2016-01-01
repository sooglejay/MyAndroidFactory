package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/11/8.
 */
public class jugeOvertimeInsuranceOrder implements Parcelable {

//    "status": 200,
//            "message": "buyed",   //买过
//            "data":" "

    private Integer status;
    private String message;
    private Object data;

    @Override
    public String toString() {
        return "jugeOvertimeInsuranceOrder{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        dest.writeValue(this.status);
        dest.writeString(this.message);
        dest.writeParcelable((Parcelable) this.data, flags);
    }

    public jugeOvertimeInsuranceOrder() {
    }

    protected jugeOvertimeInsuranceOrder(Parcel in) {
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.message = in.readString();
        this.data = in.readParcelable(Object.class.getClassLoader());
    }

    public static final Parcelable.Creator<jugeOvertimeInsuranceOrder> CREATOR = new Parcelable.Creator<jugeOvertimeInsuranceOrder>() {
        public jugeOvertimeInsuranceOrder createFromParcel(Parcel source) {
            return new jugeOvertimeInsuranceOrder(source);
        }

        public jugeOvertimeInsuranceOrder[] newArray(int size) {
            return new jugeOvertimeInsuranceOrder[size];
        }
    };
}
