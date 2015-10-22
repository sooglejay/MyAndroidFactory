package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class loginVerifyPhoneAndCode implements Parcelable {
    private String version;
    private Integer userid;
    private Integer orderid;
    private String downLoadUrl;
    private String verifyCode;
    private List<InsuranceCompanyInfo> insurancecompanyInfo;
    private UserInfo userInfo;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public List<InsuranceCompanyInfo> getInsurancecompanyInfo() {
        return insurancecompanyInfo;
    }

    public void setInsurancecompanyInfo(List<InsuranceCompanyInfo> insurancecompanyInfo) {
        this.insurancecompanyInfo = insurancecompanyInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.version);
        dest.writeValue(this.userid);
        dest.writeValue(this.orderid);
        dest.writeString(this.downLoadUrl);
        dest.writeString(this.verifyCode);
        dest.writeTypedList(insurancecompanyInfo);
        dest.writeParcelable(this.userInfo, 0);
    }

    public loginVerifyPhoneAndCode() {
    }

    protected loginVerifyPhoneAndCode(Parcel in) {
        this.version = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.downLoadUrl = in.readString();
        this.verifyCode = in.readString();
        this.insurancecompanyInfo = in.createTypedArrayList(InsuranceCompanyInfo.CREATOR);
        this.userInfo = in.readParcelable(UserInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<loginVerifyPhoneAndCode> CREATOR = new Parcelable.Creator<loginVerifyPhoneAndCode>() {
        public loginVerifyPhoneAndCode createFromParcel(Parcel source) {
            return new loginVerifyPhoneAndCode(source);
        }

        public loginVerifyPhoneAndCode[] newArray(int size) {
            return new loginVerifyPhoneAndCode[size];
        }
    };
}
