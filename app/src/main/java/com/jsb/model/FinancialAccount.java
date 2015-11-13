package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/11/12.
 */
public class FinancialAccount implements Parcelable {

    private Integer userid;
    private Integer id;
    private String account_num;
    private String bank_name;
    private String account_name;
    private Integer type;


    //superFlag
    private Boolean superFlag= false;

    @Override
    public String toString() {
        return "FinancialAccount{" +
                "userid=" + userid +
                ", id=" + id +
                ", account_num='" + account_num + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", account_name='" + account_name + '\'' +
                ", type=" + type +
                ", superFlag=" + superFlag +
                '}';
    }

    public Boolean getSuperFlag() {
        return superFlag;
    }

    public void setSuperFlag(Boolean superFlag) {
        this.superFlag = superFlag;
    }

    public static Creator<FinancialAccount> getCREATOR() {
        return CREATOR;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount_num() {
        return account_num;
    }

    public void setAccount_num(String account_num) {
        this.account_num = account_num;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public FinancialAccount() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.userid);
        dest.writeValue(this.id);
        dest.writeString(this.account_num);
        dest.writeString(this.bank_name);
        dest.writeString(this.account_name);
        dest.writeValue(this.type);
        dest.writeValue(this.superFlag);
    }

    protected FinancialAccount(Parcel in) {
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.account_num = in.readString();
        this.bank_name = in.readString();
        this.account_name = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.superFlag = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<FinancialAccount> CREATOR = new Creator<FinancialAccount>() {
        public FinancialAccount createFromParcel(Parcel source) {
            return new FinancialAccount(source);
        }

        public FinancialAccount[] newArray(int size) {
            return new FinancialAccount[size];
        }
    };
}

