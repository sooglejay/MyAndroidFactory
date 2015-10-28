package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/10/28.
 */
public class Userstable implements Parcelable {

    private Integer id;//int	用户ID	Y
    private String phone;//电话	Y
    private Long registertime;//注册时间
    private Long last_login_time;//timestamp	最近一次登录时间
    private String name;//String	姓名
    private String contactaddress;//String	联系地址
    private String account;//String	提现账号
    private String idnumber;//	String	身份证号
    private String pwd;//	String	提现密码
    private String idcardpicname;//String	身份证图片名字
    private Integer type;//int	用户角色 0 一般用户，1 业务员 2团长	Y
    private String city;//String	城市
    private Integer sex;//int	性别  0女  1男

    @Override
    public String toString() {
        return "Userstable{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", registertime=" + registertime +
                ", last_login_time=" + last_login_time +
                ", name='" + name + '\'' +
                ", contactaddress='" + contactaddress + '\'' +
                ", account='" + account + '\'' +
                ", idnumber='" + idnumber + '\'' +
                ", pwd='" + pwd + '\'' +
                ", idcardpicname='" + idcardpicname + '\'' +
                ", type=" + type +
                ", city='" + city + '\'' +
                ", sex=" + sex +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Long registertime) {
        this.registertime = registertime;
    }

    public Long getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Long last_login_time) {
        this.last_login_time = last_login_time;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getIdcardpicname() {
        return idcardpicname;
    }

    public void setIdcardpicname(String idcardpicname) {
        this.idcardpicname = idcardpicname;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.phone);
        dest.writeValue(this.registertime);
        dest.writeValue(this.last_login_time);
        dest.writeString(this.name);
        dest.writeString(this.contactaddress);
        dest.writeString(this.account);
        dest.writeString(this.idnumber);
        dest.writeString(this.pwd);
        dest.writeString(this.idcardpicname);
        dest.writeValue(this.type);
        dest.writeString(this.city);
        dest.writeValue(this.sex);
    }

    public Userstable() {
    }

    protected Userstable(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.phone = in.readString();
        this.registertime = (Long) in.readValue(Long.class.getClassLoader());
        this.last_login_time = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.contactaddress = in.readString();
        this.account = in.readString();
        this.idnumber = in.readString();
        this.pwd = in.readString();
        this.idcardpicname = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.city = in.readString();
        this.sex = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Userstable> CREATOR = new Parcelable.Creator<Userstable>() {
        public Userstable createFromParcel(Parcel source) {
            return new Userstable(source);
        }

        public Userstable[] newArray(int size) {
            return new Userstable[size];
        }
    };
}
