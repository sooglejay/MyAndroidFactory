package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.1.13.团长看团信息封装对象TeamData
 */
public class TeamData implements Parcelable {
    private UserInfo newMembers;//Userstable	待审核新成员集合，参见对象5.3
    private String totalFeeOfMonth;//String	月度保费
    private Integer persons;//int	人数
    private Integer teamid;//int	团队编号
    private Double totalFeeOfYear;//Double	年度保费

    @Override
    public String toString() {
        return "TeamData{" +
                "newMembers=" + newMembers +
                ", totalFeeOfMonth='" + totalFeeOfMonth + '\'' +
                ", persons=" + persons +
                ", teamid=" + teamid +
                ", totalFeeOfYear=" + totalFeeOfYear +
                '}';
    }

    public UserInfo getNewMembers() {
        return newMembers;
    }

    public void setNewMembers(UserInfo newMembers) {
        this.newMembers = newMembers;
    }

    public String getTotalFeeOfMonth() {
        return totalFeeOfMonth;
    }

    public void setTotalFeeOfMonth(String totalFeeOfMonth) {
        this.totalFeeOfMonth = totalFeeOfMonth;
    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public Double getTotalFeeOfYear() {
        return totalFeeOfYear;
    }

    public void setTotalFeeOfYear(Double totalFeeOfYear) {
        this.totalFeeOfYear = totalFeeOfYear;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.newMembers, 0);
        dest.writeString(this.totalFeeOfMonth);
        dest.writeValue(this.persons);
        dest.writeValue(this.teamid);
        dest.writeValue(this.totalFeeOfYear);
    }

    public TeamData() {
    }

    protected TeamData(Parcel in) {
        this.newMembers = in.readParcelable(UserInfo.class.getClassLoader());
        this.totalFeeOfMonth = in.readString();
        this.persons = (Integer) in.readValue(Integer.class.getClassLoader());
        this.teamid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalFeeOfYear = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<TeamData> CREATOR = new Parcelable.Creator<TeamData>() {
        public TeamData createFromParcel(Parcel source) {
            return new TeamData(source);
        }

        public TeamData[] newArray(int size) {
            return new TeamData[size];
        }
    };
}
