package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/11/7.
 */
public class FourService implements Parcelable {
//
//    5.4.7.4S店信息FourService
//    名称	类型	说明	不可为空
//    id	int	编号	Y
//    phone	String	电话	Y
//    name	String	名字
//    brand	Brand	品牌
//    address	string	地址

    private Integer id;
    private String phone;
    private String name;
    private Brand brand;
    private String address;
    private String managername;//服务经理名字

    @Override
    public String toString() {
        return "FourService{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", address='" + address + '\'' +
                ", managername='" + managername + '\'' +
                ", service='" + service + '\'' +
                ", certification='" + certification + '\'' +
                ", certification_num='" + certification_num + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", status=" + status +
                '}';
    }

    public String getManagername() {
        return managername;
    }

    public void setManagername(String managername) {
        this.managername = managername;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getCertification_num() {
        return certification_num;
    }

    public void setCertification_num(String certification_num) {
        this.certification_num = certification_num;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static Creator<FourService> getCREATOR() {
        return CREATOR;
    }

    private String service;
    private String certification;//营业执照图片名字
    private String certification_num;//营业执照编号
    private Float lat;
    private Float lng;
    private Integer status;//申请的审核状态 0等待审核   1通过   2未通过

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FourService() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.phone);
        dest.writeString(this.name);
        dest.writeParcelable(this.brand, 0);
        dest.writeString(this.address);
        dest.writeString(this.managername);
        dest.writeString(this.service);
        dest.writeString(this.certification);
        dest.writeString(this.certification_num);
        dest.writeValue(this.lat);
        dest.writeValue(this.lng);
        dest.writeValue(this.status);
    }

    protected FourService(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.phone = in.readString();
        this.name = in.readString();
        this.brand = in.readParcelable(Brand.class.getClassLoader());
        this.address = in.readString();
        this.managername = in.readString();
        this.service = in.readString();
        this.certification = in.readString();
        this.certification_num = in.readString();
        this.lat = (Float) in.readValue(Float.class.getClassLoader());
        this.lng = (Float) in.readValue(Float.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<FourService> CREATOR = new Creator<FourService>() {
        public FourService createFromParcel(Parcel source) {
            return new FourService(source);
        }

        public FourService[] newArray(int size) {
            return new FourService[size];
        }
    };
}
