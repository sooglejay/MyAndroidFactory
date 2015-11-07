package com.jsb.model;

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
//    brand	String	品牌
//    address	string	地址

    private Integer id;
    private String phone;
    private String name;
    private String brand;
    private String address;

    @Override
    public String toString() {
        return "FourService{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", address='" + address + '\'' +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        dest.writeString(this.brand);
        dest.writeString(this.address);
    }

    public FourService() {
    }

    protected FourService(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.phone = in.readString();
        this.name = in.readString();
        this.brand = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<FourService> CREATOR = new Parcelable.Creator<FourService>() {
        public FourService createFromParcel(Parcel source) {
            return new FourService(source);
        }

        public FourService[] newArray(int size) {
            return new FourService[size];
        }
    };
}
