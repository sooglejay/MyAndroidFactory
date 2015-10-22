package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 设置体现密码
 */
public class saveVehicleInfo implements Parcelable {
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<InsuranceCompanyInfo> getInsurancecompanyInfo() {
        return insurancecompanyInfo;
    }

    public void setInsurancecompanyInfo(List<InsuranceCompanyInfo> insurancecompanyInfo) {
        this.insurancecompanyInfo = insurancecompanyInfo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    private UserInfo userInfo;
    private List<InsuranceCompanyInfo>insurancecompanyInfo;
    private String version;
    private String downLoadUrl;
    private String verifyCode;
    private Integer userid;
    private Integer orderid;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.userInfo, 0);
        dest.writeTypedList(insurancecompanyInfo);
        dest.writeString(this.version);
        dest.writeString(this.downLoadUrl);
        dest.writeString(this.verifyCode);
        dest.writeValue(this.userid);
        dest.writeValue(this.orderid);
    }

    public saveVehicleInfo() {
    }

    protected saveVehicleInfo(Parcel in) {
        this.userInfo = in.readParcelable(UserInfo.class.getClassLoader());
        this.insurancecompanyInfo = in.createTypedArrayList(InsuranceCompanyInfo.CREATOR);
        this.version = in.readString();
        this.downLoadUrl = in.readString();
        this.verifyCode = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderid = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<saveVehicleInfo> CREATOR = new Parcelable.Creator<saveVehicleInfo>() {
        public saveVehicleInfo createFromParcel(Parcel source) {
            return new saveVehicleInfo(source);
        }

        public saveVehicleInfo[] newArray(int size) {
            return new saveVehicleInfo[size];
        }
    };
}
