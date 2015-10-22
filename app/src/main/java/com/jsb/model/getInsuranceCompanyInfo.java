package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jsb.constant.StringConstant;

import java.util.List;


/**
 * 7.4.4.获取保险公司信息
 */
public class getInsuranceCompanyInfo implements Parcelable {


    private String version;
    private String downLoadUrl;
    private String verifyCode;
    private Integer userid;
    private Integer orderid;
    private UserInfo userInfo;
    private List<InsuranceCompanyInfo>insurancecompanyInfo;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.version);
        dest.writeString(this.downLoadUrl);
        dest.writeString(this.verifyCode);
        dest.writeValue(this.userid);
        dest.writeValue(this.orderid);
        dest.writeParcelable(this.userInfo, 0);
        dest.writeTypedList(insurancecompanyInfo);
    }

    public getInsuranceCompanyInfo() {
    }

    protected getInsuranceCompanyInfo(Parcel in) {
        this.version = in.readString();
        this.downLoadUrl = in.readString();
        this.verifyCode = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userInfo = in.readParcelable(UserInfo.class.getClassLoader());
        this.insurancecompanyInfo = in.createTypedArrayList(InsuranceCompanyInfo.CREATOR);
    }

    public static final Parcelable.Creator<getInsuranceCompanyInfo> CREATOR = new Parcelable.Creator<getInsuranceCompanyInfo>() {
        public getInsuranceCompanyInfo createFromParcel(Parcel source) {
            return new getInsuranceCompanyInfo(source);
        }

        public getInsuranceCompanyInfo[] newArray(int size) {
            return new getInsuranceCompanyInfo[size];
        }
    };
}
