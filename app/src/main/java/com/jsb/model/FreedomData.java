package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.1.12.自由人搜团信息封装对象FreedomData
 */
public class FreedomData implements Parcelable {

    private String leaderName;//String	团长名	Y
    private String teamName;//String	团名	Y
    private Integer amount;//int	人数	Y
    private Integer teamid;//int	团队编号	Y
    private Double totalFee;//Double	团队累计保费	Y

    @Override
    public String toString() {
        return "FreedomData{" +
                "leaderName='" + leaderName + '\'' +
                ", teamName='" + teamName + '\'' +
                ", amount=" + amount +
                ", teamid=" + teamid +
                ", totalFee=" + totalFee +
                '}';
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.leaderName);
        dest.writeString(this.teamName);
        dest.writeValue(this.amount);
        dest.writeValue(this.teamid);
        dest.writeValue(this.totalFee);
    }

    public FreedomData() {
    }

    protected FreedomData(Parcel in) {
        this.leaderName = in.readString();
        this.teamName = in.readString();
        this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.teamid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalFee = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<FreedomData> CREATOR = new Parcelable.Creator<FreedomData>() {
        public FreedomData createFromParcel(Parcel source) {
            return new FreedomData(source);
        }

        public FreedomData[] newArray(int size) {
            return new FreedomData[size];
        }
    };
}
