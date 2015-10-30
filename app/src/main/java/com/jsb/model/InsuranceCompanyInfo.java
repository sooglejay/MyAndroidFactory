package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class InsuranceCompanyInfo implements Parcelable {
    public static Creator<InsuranceCompanyInfo> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "InsuranceCompanyInfo{" +
                "id=" + id +
                ", companyname='" + companyname + '\'' +
                ", companyphone='" + companyphone + '\'' +
                ", hotline='" + hotline + '\'' +
                ", policy='" + policy + '\'' +
                ", cooperationnetwork='" + cooperationnetwork + '\'' +
                ", pauserule='" + pauserule + '\'' +
                ", pauseprice=" + pauseprice +
                '}';
    }

    private Integer id;
    private String companyname;
    private String companyphone;
    private String hotline;
    private String policy;
    private String cooperationnetwork;
    private String pauserule;
    private Integer pauseprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyphone() {
        return companyphone;
    }

    public void setCompanyphone(String companyphone) {
        this.companyphone = companyphone;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getCooperationnetwork() {
        return cooperationnetwork;
    }

    public void setCooperationnetwork(String cooperationnetwork) {
        this.cooperationnetwork = cooperationnetwork;
    }

    public String getPauserule() {
        return pauserule;
    }

    public void setPauserule(String pauserule) {
        this.pauserule = pauserule;
    }

    public Integer getPauseprice() {
        return pauseprice;
    }

    public void setPauseprice(Integer pauseprice) {
        this.pauseprice = pauseprice;
    }

    public InsuranceCompanyInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.companyname);
        dest.writeString(this.companyphone);
        dest.writeString(this.hotline);
        dest.writeString(this.policy);
        dest.writeString(this.cooperationnetwork);
        dest.writeString(this.pauserule);
        dest.writeValue(this.pauseprice);
    }

    protected InsuranceCompanyInfo(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.companyname = in.readString();
        this.companyphone = in.readString();
        this.hotline = in.readString();
        this.policy = in.readString();
        this.cooperationnetwork = in.readString();
        this.pauserule = in.readString();
        this.pauseprice = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<InsuranceCompanyInfo> CREATOR = new Creator<InsuranceCompanyInfo>() {
        public InsuranceCompanyInfo createFromParcel(Parcel source) {
            return new InsuranceCompanyInfo(source);
        }

        public InsuranceCompanyInfo[] newArray(int size) {
            return new InsuranceCompanyInfo[size];
        }
    };
}
