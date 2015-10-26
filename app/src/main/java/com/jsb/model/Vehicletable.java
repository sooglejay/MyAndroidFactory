package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 5.4.4.车辆信息Vehicletable
 */
public class Vehicletable implements Parcelable {

    private Integer id;//int	车险订单编号	Y
    private Integer userid;//int	用户id	Y
    private Integer seatingcapacity;//int	容量	Y
    private String licenseplate;//String 车牌号	Y
    private String enginenumber;//String	发动机号	Y
    private String framenumber;//String	车驾号	Y
    private String ownerName;//String	车主	Y
    private Long registrationDate;//登记时间	Y

    @Override
    public String toString() {
        return "Vehicletable{" +
                "id=" + id +
                ", userid=" + userid +
                ", seatingcapacity=" + seatingcapacity +
                ", licenseplate='" + licenseplate + '\'' +
                ", enginenumber='" + enginenumber + '\'' +
                ", framenumber='" + framenumber + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getSeatingcapacity() {
        return seatingcapacity;
    }

    public void setSeatingcapacity(Integer seatingcapacity) {
        this.seatingcapacity = seatingcapacity;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public String getEnginenumber() {
        return enginenumber;
    }

    public void setEnginenumber(String enginenumber) {
        this.enginenumber = enginenumber;
    }

    public String getFramenumber() {
        return framenumber;
    }

    public void setFramenumber(String framenumber) {
        this.framenumber = framenumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Long registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.userid);
        dest.writeValue(this.seatingcapacity);
        dest.writeString(this.licenseplate);
        dest.writeString(this.enginenumber);
        dest.writeString(this.framenumber);
        dest.writeString(this.ownerName);
        dest.writeValue(this.registrationDate);
    }

    public Vehicletable() {
    }

    protected Vehicletable(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.seatingcapacity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.licenseplate = in.readString();
        this.enginenumber = in.readString();
        this.framenumber = in.readString();
        this.ownerName = in.readString();
        this.registrationDate = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<Vehicletable> CREATOR = new Parcelable.Creator<Vehicletable>() {
        public Vehicletable createFromParcel(Parcel source) {
            return new Vehicletable(source);
        }

        public Vehicletable[] newArray(int size) {
            return new Vehicletable[size];
        }
    };
}
