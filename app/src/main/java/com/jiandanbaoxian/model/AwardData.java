package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.1.19.奖励对象AwardData
 */
public class AwardData implements Parcelable {

//    workfeeCount	int	业务费总条数
//    awardChoiceCount	Int	额外奖励总条数
//    workfeeAmount	float	总业务费
//    awardChoiceAmount	float	总额外奖励
//    workfeeRecord	WorkfeeRecord	业务费集合 5.1.20
//    awardChoiceRecord	AwardChoiceRecord	外奖励集合 5.1.21

    private int workfeeCount;
    private int awardChoiceCount;
    private float workfeeAmount;
    private float awardChoiceAmount;
    private WorkfeeRecord workfeeRecord;
    private AwardChoiceRecord awardChoiceRecord;

    @Override
    public String toString() {
        return "AwardData{" +
                "workfeeCount=" + workfeeCount +
                ", awardChoiceCount=" + awardChoiceCount +
                ", workfeeAmount=" + workfeeAmount +
                ", awardChoiceAmount=" + awardChoiceAmount +
                ", workfeeRecord=" + workfeeRecord +
                ", awardChoiceRecord=" + awardChoiceRecord +
                '}';
    }

    public int getAwardChoiceCount() {
        return awardChoiceCount;
    }

    public void setAwardChoiceCount(int awardChoiceCount) {
        this.awardChoiceCount = awardChoiceCount;
    }

    public float getWorkfeeAmount() {
        return workfeeAmount;
    }

    public void setWorkfeeAmount(float workfeeAmount) {
        this.workfeeAmount = workfeeAmount;
    }

    public float getAwardChoiceAmount() {
        return awardChoiceAmount;
    }

    public void setAwardChoiceAmount(float awardChoiceAmount) {
        this.awardChoiceAmount = awardChoiceAmount;
    }

    public WorkfeeRecord getWorkfeeRecord() {
        return workfeeRecord;
    }

    public void setWorkfeeRecord(WorkfeeRecord workfeeRecord) {
        this.workfeeRecord = workfeeRecord;
    }

    public AwardChoiceRecord getAwardChoiceRecord() {
        return awardChoiceRecord;
    }

    public void setAwardChoiceRecord(AwardChoiceRecord awardChoiceRecord) {
        this.awardChoiceRecord = awardChoiceRecord;
    }

    public int getWorkfeeCount() {

        return workfeeCount;
    }

    public void setWorkfeeCount(int workfeeCount) {
        this.workfeeCount = workfeeCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.workfeeCount);
        dest.writeInt(this.awardChoiceCount);
        dest.writeFloat(this.workfeeAmount);
        dest.writeFloat(this.awardChoiceAmount);
        dest.writeParcelable(this.workfeeRecord, 0);
        dest.writeParcelable(this.awardChoiceRecord, 0);
    }

    public AwardData() {
    }

    protected AwardData(Parcel in) {
        this.workfeeCount = in.readInt();
        this.awardChoiceCount = in.readInt();
        this.workfeeAmount = in.readFloat();
        this.awardChoiceAmount = in.readFloat();
        this.workfeeRecord = in.readParcelable(WorkfeeRecord.class.getClassLoader());
        this.awardChoiceRecord = in.readParcelable(AwardChoiceRecord.class.getClassLoader());
    }

    public static final Creator<AwardData> CREATOR = new Creator<AwardData>() {
        public AwardData createFromParcel(Parcel source) {
            return new AwardData(source);
        }

        public AwardData[] newArray(int size) {
            return new AwardData[size];
        }
    };
}
