package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sooglejay on 16/3/8.
 */
public class VhlTax implements Parcelable {
//
//    名称	类型	说明	不可为空
//    tax_vhl_type	String	车船税车辆分类
//    tax_type	String	减免税标志
//    former_tax	Float	上年应缴
//    current_tax	Float	当年应缴	Y
//    sum_up_tax	Float	合计应缴	Y
    private String tax_vhl_type;
    private String tax_type;
    private String former_tax;
    private String current_tax;
    private String sum_up_tax;

    @Override
    public String toString() {
        return "VhlTax{" +
                "tax_vhl_type='" + tax_vhl_type + '\'' +
                ", tax_type='" + tax_type + '\'' +
                ", former_tax='" + former_tax + '\'' +
                ", current_tax='" + current_tax + '\'' +
                ", sum_up_tax='" + sum_up_tax + '\'' +
                '}';
    }

    public String getTax_vhl_type() {
        return tax_vhl_type;
    }

    public void setTax_vhl_type(String tax_vhl_type) {
        this.tax_vhl_type = tax_vhl_type;
    }

    public String getTax_type() {
        return tax_type;
    }

    public void setTax_type(String tax_type) {
        this.tax_type = tax_type;
    }

    public String getFormer_tax() {
        return former_tax;
    }

    public void setFormer_tax(String former_tax) {
        this.former_tax = former_tax;
    }

    public String getCurrent_tax() {
        return current_tax;
    }

    public void setCurrent_tax(String current_tax) {
        this.current_tax = current_tax;
    }

    public String getSum_up_tax() {
        return sum_up_tax;
    }

    public void setSum_up_tax(String sum_up_tax) {
        this.sum_up_tax = sum_up_tax;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tax_vhl_type);
        dest.writeString(this.tax_type);
        dest.writeString(this.former_tax);
        dest.writeString(this.current_tax);
        dest.writeString(this.sum_up_tax);
    }

    public VhlTax() {
    }

    protected VhlTax(Parcel in) {
        this.tax_vhl_type = in.readString();
        this.tax_type = in.readString();
        this.former_tax = in.readString();
        this.current_tax = in.readString();
        this.sum_up_tax = in.readString();
    }

    public static final Parcelable.Creator<VhlTax> CREATOR = new Parcelable.Creator<VhlTax>() {
        public VhlTax createFromParcel(Parcel source) {
            return new VhlTax(source);
        }

        public VhlTax[] newArray(int size) {
            return new VhlTax[size];
        }
    };
}
