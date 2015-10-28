package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.1.3.通用信息对象CommData
 */
public class CommData implements Parcelable {
    private String version;//服务器版本号
    private String verifyCode;//手机验证码
    private Integer userid;//会员编号
    private Integer orderid;//车险订单编号
    private String downLoadUrl;//下载路径
    private UserInfo userInfo;//参见5.3用户信息对象

    private InsuranceCompanyInfo insurancecompanyInfo;//保险公司信息集合参见5.4.2

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public InsuranceCompanyInfo getInsurancecompanyInfo() {
        return insurancecompanyInfo;
    }

    public void setInsurancecompanyInfo(InsuranceCompanyInfo insurancecompanyInfo) {
        this.insurancecompanyInfo = insurancecompanyInfo;
    }

    public CommData() {
    }

    @Override
    public String toString() {
        return "CommData{" +
                "version='" + version + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", userid=" + userid +
                ", orderid=" + orderid +
                ", downLoadUrl='" + downLoadUrl + '\'' +
                ", userInfo=" + userInfo +
                ", insurancecompanyInfo=" + insurancecompanyInfo +
                '}';
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public static Creator<CommData> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.version);
        dest.writeString(this.verifyCode);
        dest.writeValue(this.userid);
        dest.writeValue(this.orderid);
        dest.writeString(this.downLoadUrl);
        dest.writeParcelable(this.userInfo, 0);
        dest.writeParcelable(this.insurancecompanyInfo, 0);
    }

    protected CommData(Parcel in) {
        this.version = in.readString();
        this.verifyCode = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.downLoadUrl = in.readString();
        this.userInfo = in.readParcelable(UserInfo.class.getClassLoader());
        this.insurancecompanyInfo = in.readParcelable(InsuranceCompanyInfo.class.getClassLoader());
    }

    public static final Creator<CommData> CREATOR = new Creator<CommData>() {
        public CommData createFromParcel(Parcel source) {
            return new CommData(source);
        }

        public CommData[] newArray(int size) {
            return new CommData[size];
        }
    };
}
