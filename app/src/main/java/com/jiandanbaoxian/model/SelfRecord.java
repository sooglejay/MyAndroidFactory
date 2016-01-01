package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/11/2.
 */
public class SelfRecord implements Parcelable {

//    {
//        "name": "急急急",
//            "userid": 1,
//            "user": {    //对应的用户完整信息对象
//        "id": 1,
//                "phone": "13678054215",
//                "registertime": 1443767342000,
//                "name": "急急急",
//                "sex": null,
//                "city": "四川省成都市",
//                "contactaddress": null,
//                "idnumber": "急急急",
//                "pwd": "e10adc3949ba59abbe56e057f20f883e",
//                "idcardname": "1447126650134Pictures1447126763082.jpg",
//                "type": 2,
//                "lastLoginTime": 1443767342000,
//                "account": null,
//                "company": null,
//                "companyname": null,
//                "fourService": null,
//                "service": "急急急",
//                "worknum": null,
//                "audit": 0
//    },
//        "moneyofyear": 100,
//            "rankofyear": 1,
//            "moneyofmonth": 0,
//            "rankofmonth": 0
//    }

    private String name;//
    private Integer userid;//
    private Userstable user;//
    private Double moneyofyear;//
    private Integer rankofyear;
    private Integer rankofmonth;
    private Double moneyofmonth;//

    @Override
    public String toString() {
        return "SelfRecord{" +
                "name='" + name + '\'' +
                ", userid=" + userid +
                ", user=" + user +
                ", moneyofyear=" + moneyofyear +
                ", rankofyear=" + rankofyear +
                ", rankofmonth=" + rankofmonth +
                ", moneyofmonth=" + moneyofmonth +
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

    public Userstable getUser() {
        return user;
    }

    public void setUser(Userstable user) {
        this.user = user;
    }

    public Double getMoneyofyear() {
        return moneyofyear;
    }

    public void setMoneyofyear(Double moneyofyear) {
        this.moneyofyear = moneyofyear;
    }

    public Integer getRankofyear() {
        return rankofyear;
    }

    public void setRankofyear(Integer rankofyear) {
        this.rankofyear = rankofyear;
    }

    public Integer getRankofmonth() {
        return rankofmonth;
    }

    public void setRankofmonth(Integer rankofmonth) {
        this.rankofmonth = rankofmonth;
    }

    public Double getMoneyofmonth() {
        return moneyofmonth;
    }

    public void setMoneyofmonth(Double moneyofmonth) {
        this.moneyofmonth = moneyofmonth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.userid);
        dest.writeParcelable(this.user, 0);
        dest.writeValue(this.moneyofyear);
        dest.writeValue(this.rankofyear);
        dest.writeValue(this.rankofmonth);
        dest.writeValue(this.moneyofmonth);
    }

    public SelfRecord() {
    }

    protected SelfRecord(Parcel in) {
        this.name = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.user = in.readParcelable(Userstable.class.getClassLoader());
        this.moneyofyear = (Double) in.readValue(Double.class.getClassLoader());
        this.rankofyear = (Integer) in.readValue(Integer.class.getClassLoader());
        this.rankofmonth = (Integer) in.readValue(Integer.class.getClassLoader());
        this.moneyofmonth = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<SelfRecord> CREATOR = new Creator<SelfRecord>() {
        public SelfRecord createFromParcel(Parcel source) {
            return new SelfRecord(source);
        }

        public SelfRecord[] newArray(int size) {
            return new SelfRecord[size];
        }
    };
}
