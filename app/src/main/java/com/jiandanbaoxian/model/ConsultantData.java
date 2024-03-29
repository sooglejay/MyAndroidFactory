package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 5.1.18.我的顾问对象ConsultantData
 */
public class ConsultantData implements Parcelable {
//上面显示的保险顾问，下面显示的是其他顾问


    /**
     * amount	Int	其他顾问总数
     * myConsultant	Userstable	我的顾问(推荐人)
     * maintainConsultant	Userstable	其他顾问集合（4s）
     * insuranceConsultant	Userstable	保险顾问（保险公司人）
     */

    private Integer amount;//
    private List<Userstable> myConsultant;//
    private List<FourService> maintainConsultant;//
    private List<Userstable> insuranceConsultant;//

    @Override
    public String toString() {
        return "ConsultantData{" +
                "amount=" + amount +
                ", myConsultant=" + myConsultant +
                ", maintainConsultant=" + maintainConsultant +
                ", insuranceConsultant=" + insuranceConsultant +
                '}';
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<Userstable> getMyConsultant() {
        return myConsultant;
    }

    public void setMyConsultant(List<Userstable> myConsultant) {
        this.myConsultant = myConsultant;
    }

    public List<FourService> getMaintainConsultant() {
        return maintainConsultant;
    }

    public void setMaintainConsultant(List<FourService> maintainConsultant) {
        this.maintainConsultant = maintainConsultant;
    }

    public List<Userstable> getInsuranceConsultant() {
        return insuranceConsultant;
    }

    public void setInsuranceConsultant(List<Userstable> insuranceConsultant) {
        this.insuranceConsultant = insuranceConsultant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.amount);
        dest.writeTypedList(myConsultant);
        dest.writeTypedList(maintainConsultant);
        dest.writeTypedList(insuranceConsultant);
    }

    public ConsultantData() {
    }

    protected ConsultantData(Parcel in) {
        this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.myConsultant = in.createTypedArrayList(Userstable.CREATOR);
        this.maintainConsultant = in.createTypedArrayList(FourService.CREATOR);
        this.insuranceConsultant = in.createTypedArrayList(Userstable.CREATOR);
    }

    public static final Creator<ConsultantData> CREATOR = new Creator<ConsultantData>() {
        public ConsultantData createFromParcel(Parcel source) {
            return new ConsultantData(source);
        }

        public ConsultantData[] newArray(int size) {
            return new ConsultantData[size];
        }
    };
}
