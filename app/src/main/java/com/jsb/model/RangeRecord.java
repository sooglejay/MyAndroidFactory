package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 5.1.14.团队排名对象RangeRecord
 */
public class RangeRecord implements Parcelable {
    //    name    	String	姓名
    //    userid	Integer	个人编号
    //    money	    Double	个人累计保费
    private String name;
    private Integer userid;
    private String money;

    @Override
    public String toString() {
        return "RangeRecord{" +
                "name='" + name + '\'' +
                ", userid=" + userid +
                ", money='" + money + '\'' +
                '}';
    }

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.userid);
        dest.writeString(this.money);
    }

    public RangeRecord() {
    }

    protected RangeRecord(Parcel in) {
        this.name = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.money = in.readString();
    }

    public static final Parcelable.Creator<RangeRecord> CREATOR = new Parcelable.Creator<RangeRecord>() {
        public RangeRecord createFromParcel(Parcel source) {
            return new RangeRecord(source);
        }

        public RangeRecord[] newArray(int size) {
            return new RangeRecord[size];
        }
    };
}
