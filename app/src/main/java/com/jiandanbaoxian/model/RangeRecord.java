package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.1.14.团队排名对象RangeRecord
 */
public class RangeRecord implements Parcelable {
    //    name	String	姓名
//    userid	String	个人编号
//    money	Double	个人累计保费（元）
//    rank	Integer	名次
//    user	Userstable	用户信息对象
    private String name;
    private Integer userid;

    public Float getMoney() {
        return money;
    }

    private Float money;
    private Integer rank;
    private Userstable user;

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


    public RangeRecord() {
    }

    public void setMoney(Float money) {

        this.money = money;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Userstable getUser() {
        return user;
    }

    public void setUser(Userstable user) {
        this.user = user;
    }

    public static Creator<RangeRecord> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.userid);
        dest.writeValue(this.money);
        dest.writeValue(this.rank);
        dest.writeParcelable(this.user, 0);
    }

    protected RangeRecord(Parcel in) {
        this.name = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.money = (Float) in.readValue(Float.class.getClassLoader());
        this.rank = (Integer) in.readValue(Integer.class.getClassLoader());
        this.user = in.readParcelable(Userstable.class.getClassLoader());
    }

    public static final Creator<RangeRecord> CREATOR = new Creator<RangeRecord>() {
        public RangeRecord createFromParcel(Parcel source) {
            return new RangeRecord(source);
        }

        public RangeRecord[] newArray(int size) {
            return new RangeRecord[size];
        }
    };
}
