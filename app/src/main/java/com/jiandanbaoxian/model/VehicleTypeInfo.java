package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sooglejay on 16/2/25.
 */
public class VehicleTypeInfo implements Parcelable {
/**
 model_code	String	车型代码	Y
 model_name	String	车型名字	Y
 car_sort	String	车型分类
 car_style	String	进口/国产  进口车/国产车/合资车  如：合资车
 car_maker	String	制造商
 set_num	int	座位数
 displacement	float	排量  升
 quality	float	整备质量  KG
 market_year	Int	车辆年份
 car_price	float	新车购置价
 risk_name	String	风险类型  正常车型  老旧车型 配件疑难 特异车型  稀有车型
 */

    private String model_code;
    private String model_name;
    private String car_sort;
    private String car_style;
    private String car_maker;
    private Integer set_num;
    private Float displacement;
    private Float quality;
    private Integer market_year;
    private Float car_price;




    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    private boolean isSelected;


    @Override
    public String toString() {
        return "VehicleTypeInfo{" +
                "model_code='" + model_code + '\'' +
                ", model_name='" + model_name + '\'' +
                ", car_sort='" + car_sort + '\'' +
                ", car_style='" + car_style + '\'' +
                ", car_maker='" + car_maker + '\'' +
                ", set_num=" + set_num +
                ", displacement=" + displacement +
                ", quality=" + quality +
                ", market_year=" + market_year +
                ", car_price=" + car_price +
                ", isSelected=" + isSelected +
                ", risk_name='" + risk_name + '\'' +
                '}';
    }

    public static Creator<VehicleTypeInfo> getCREATOR() {
        return CREATOR;
    }

    public String getRisk_name() {
        return risk_name;
    }

    public void setRisk_name(String risk_name) {
        this.risk_name = risk_name;
    }

    public String getModel_code() {
        return model_code;
    }

    public void setModel_code(String model_code) {
        this.model_code = model_code;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getCar_sort() {
        return car_sort;
    }

    public void setCar_sort(String car_sort) {
        this.car_sort = car_sort;
    }

    public String getCar_style() {
        return car_style;
    }

    public void setCar_style(String car_style) {
        this.car_style = car_style;
    }

    public String getCar_maker() {
        return car_maker;
    }

    public void setCar_maker(String car_maker) {
        this.car_maker = car_maker;
    }

    public Integer getSet_num() {
        return set_num;
    }

    public void setSet_num(Integer set_num) {
        this.set_num = set_num;
    }

    public Float getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Float displacement) {
        this.displacement = displacement;
    }

    public Float getQuality() {
        return quality;
    }

    public void setQuality(Float quality) {
        this.quality = quality;
    }

    public Integer getMarket_year() {
        return market_year;
    }

    public void setMarket_year(Integer market_year) {
        this.market_year = market_year;
    }

    public Float getCar_price() {
        return car_price;
    }

    public void setCar_price(Float car_price) {
        this.car_price = car_price;
    }

    private String risk_name;

    public VehicleTypeInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.model_code);
        dest.writeString(this.model_name);
        dest.writeString(this.car_sort);
        dest.writeString(this.car_style);
        dest.writeString(this.car_maker);
        dest.writeValue(this.set_num);
        dest.writeValue(this.displacement);
        dest.writeValue(this.quality);
        dest.writeValue(this.market_year);
        dest.writeValue(this.car_price);
        dest.writeByte(isSelected ? (byte) 1 : (byte) 0);
        dest.writeString(this.risk_name);
    }

    protected VehicleTypeInfo(Parcel in) {
        this.model_code = in.readString();
        this.model_name = in.readString();
        this.car_sort = in.readString();
        this.car_style = in.readString();
        this.car_maker = in.readString();
        this.set_num = (Integer) in.readValue(Integer.class.getClassLoader());
        this.displacement = (Float) in.readValue(Float.class.getClassLoader());
        this.quality = (Float) in.readValue(Float.class.getClassLoader());
        this.market_year = (Integer) in.readValue(Integer.class.getClassLoader());
        this.car_price = (Float) in.readValue(Float.class.getClassLoader());
        this.isSelected = in.readByte() != 0;
        this.risk_name = in.readString();
    }

    public static final Creator<VehicleTypeInfo> CREATOR = new Creator<VehicleTypeInfo>() {
        public VehicleTypeInfo createFromParcel(Parcel source) {
            return new VehicleTypeInfo(source);
        }

        public VehicleTypeInfo[] newArray(int size) {
            return new VehicleTypeInfo[size];
        }
    };
}
