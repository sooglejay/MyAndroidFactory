package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class UserInfo implements Parcelable {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactaddress() {
        return contactaddress;
    }

    public void setContactaddress(String contactaddress) {
        this.contactaddress = contactaddress;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIdcardname() {
        return idcardname;
    }

    public void setIdcardname(String idcardname) {
        this.idcardname = idcardname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Long registertime) {
        this.registertime = registertime;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    private Integer id;
    private Integer type;
    private String phone;
    private String city;
    private String name;
    private String contactaddress;
    private String idnumber;
    private String pwd;
    private String idcardname;
    private String account;
    private Long registertime;
    private Long lastLoginTime;
    private Integer sex;//	int	性别  0女  1男


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.type);
        dest.writeString(this.phone);
        dest.writeString(this.city);
        dest.writeString(this.name);
        dest.writeString(this.contactaddress);
        dest.writeString(this.idnumber);
        dest.writeString(this.pwd);
        dest.writeString(this.idcardname);
        dest.writeString(this.account);
        dest.writeValue(this.registertime);
        dest.writeValue(this.lastLoginTime);
        dest.writeValue(this.sex);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.phone = in.readString();
        this.city = in.readString();
        this.name = in.readString();
        this.contactaddress = in.readString();
        this.idnumber = in.readString();
        this.pwd = in.readString();
        this.idcardname = in.readString();
        this.account = in.readString();
        this.registertime = (Long) in.readValue(Long.class.getClassLoader());
        this.lastLoginTime = (Long) in.readValue(Long.class.getClassLoader());
        this.sex = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
