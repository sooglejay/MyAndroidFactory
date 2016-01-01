package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.1.20.业务费封装对象WorkfeeRecord
 */
public class WorkfeeRecord implements Parcelable {

//    id	int	记录编号
//    time	timestamp	费用生成时间
//    orderid	int	对应的保单编号
//    type	int	费用类型 0管理   1业务  2推荐 3额外奖励
//    username	String	对应的产生该费用的用户名
//    amount	float	对应的金额（元）

    private int id;
    private long time;
    private int orderid;
    private int type;
    private String username;
    private float amount;

    @Override
    public String toString() {
        return "WorkfeeRecord{" +
                "id=" + id +
                ", time=" + time +
                ", orderid=" + orderid +
                ", type=" + type +
                ", username='" + username + '\'' +
                ", amount=" + amount +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.time);
        dest.writeInt(this.orderid);
        dest.writeInt(this.type);
        dest.writeString(this.username);
        dest.writeFloat(this.amount);
    }

    public WorkfeeRecord() {
    }

    protected WorkfeeRecord(Parcel in) {
        this.id = in.readInt();
        this.time = in.readLong();
        this.orderid = in.readInt();
        this.type = in.readInt();
        this.username = in.readString();
        this.amount = in.readFloat();
    }

    public static final Parcelable.Creator<WorkfeeRecord> CREATOR = new Parcelable.Creator<WorkfeeRecord>() {
        public WorkfeeRecord createFromParcel(Parcel source) {
            return new WorkfeeRecord(source);
        }

        public WorkfeeRecord[] newArray(int size) {
            return new WorkfeeRecord[size];
        }
    };
}
