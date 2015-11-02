package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/11/2.
 */
public class SelfRecord implements Parcelable {

    /**
     * name	String	用户姓名
     userid	int	用户编号
     moneyofyear	Double	年度保费
     moneyofmonth	Double	月度保费
     */

    private String name ;//
    private Integer userid;//
    private Double moneyofyear;//
    private Double moneyofmonth;//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Double getMoneyofyear() {
        return moneyofyear;
    }

    public void setMoneyofyear(Double moneyofyear) {
        this.moneyofyear = moneyofyear;
    }

    public Double getMoneyofmonth() {
        return moneyofmonth;
    }

    public void setMoneyofmonth(Double moneyofmonth) {
        this.moneyofmonth = moneyofmonth;
    }

    @Override
    public String toString() {
        return "SelfRecord{" +
                "name='" + name + '\'' +
                ", userid=" + userid +
                ", moneyofyear=" + moneyofyear +
                ", moneyofmonth=" + moneyofmonth +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.userid);
        dest.writeValue(this.moneyofyear);
        dest.writeValue(this.moneyofmonth);
    }

    public SelfRecord() {
    }

    protected SelfRecord(Parcel in) {
        this.name = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.moneyofyear = (Double) in.readValue(Double.class.getClassLoader());
        this.moneyofmonth = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<SelfRecord> CREATOR = new Parcelable.Creator<SelfRecord>() {
        public SelfRecord createFromParcel(Parcel source) {
            return new SelfRecord(source);
        }

        public SelfRecord[] newArray(int size) {
            return new SelfRecord[size];
        }
    };
}
