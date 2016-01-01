package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.1.21.额外奖励封装对象AwardChoiceRecord
 */
public class AwardChoiceRecord implements Parcelable {
    //    time	Timestamp	奖励时间
//    awardName	String	奖项名称
//    amount	float	金额（元）
    private long time;
    private String awardName;
    private float amount;

    @Override
    public String toString() {
        return "AwardChoiceRecord{" +
                "time=" + time +
                ", awardName='" + awardName + '\'' +
                ", amount=" + amount +
                '}';
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public AwardChoiceRecord() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.time);
        dest.writeString(this.awardName);
        dest.writeFloat(this.amount);
    }

    protected AwardChoiceRecord(Parcel in) {
        this.time = in.readLong();
        this.awardName = in.readString();
        this.amount = in.readFloat();
    }

    public static final Parcelable.Creator<AwardChoiceRecord> CREATOR = new Parcelable.Creator<AwardChoiceRecord>() {
        public AwardChoiceRecord createFromParcel(Parcel source) {
            return new AwardChoiceRecord(source);
        }

        public AwardChoiceRecord[] newArray(int size) {
            return new AwardChoiceRecord[size];
        }
    };
}
