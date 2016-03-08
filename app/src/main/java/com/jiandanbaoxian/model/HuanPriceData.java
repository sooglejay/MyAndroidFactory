package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by sooglejay on 16/2/18.
 * 华安报价结果详情HuanPriceData

 */
public class HuanPriceData implements Parcelable {
    /**
     *
     commerceAmount	Float	商业险总费用金额	Y
     companyName	String	保险公司名字	Y
     companyId	int	保险公司编号id
     compulsoryAmount	Float	交强险总费用金额
     insuranceItem	InsuranceItemData集合 5.4.9	具体保险详细
     priceId	Int	报价编号
     orderId	Int	订单编号
     compulsoryBaseInfo	HuanInsuranceBaseInfoData	交强险的基本信息
     commerceBaseInfo	HuanInsuranceBaseInfoData 5.4.10	商业险的基本信息
     */

    private Float commerceAmount;
    private Float compulsoryAmount;
    private String companyName;
    private Integer companyId;
    private Integer priceId;
    private Integer orderId;
    private HuanInsuranceBaseInfoData compulsoryBaseInfo;
    private HuanInsuranceBaseInfoData commerceBaseInfo;
    private VhlTax tax;

    @Override
    public String toString() {
        return "HuanPriceData{" +
                "commerceAmount=" + commerceAmount +
                ", compulsoryAmount=" + compulsoryAmount +
                ", companyName='" + companyName + '\'' +
                ", companyId=" + companyId +
                ", priceId=" + priceId +
                ", orderId=" + orderId +
                ", compulsoryBaseInfo=" + compulsoryBaseInfo +
                ", commerceBaseInfo=" + commerceBaseInfo +
                ", tax=" + tax +
                ", insuranceItem=" + insuranceItem +
                '}';
    }

    public VhlTax getTax() {
        return tax;
    }

    public void setTax(VhlTax tax) {
        this.tax = tax;
    }

    public static Creator<HuanPriceData> getCREATOR() {
        return CREATOR;
    }

    private List<InsuranceItemData> insuranceItem;

    public Float getCommerceAmount() {
        return commerceAmount;
    }

    public void setCommerceAmount(Float commerceAmount) {
        this.commerceAmount = commerceAmount;
    }

    public Float getCompulsoryAmount() {
        return compulsoryAmount;
    }

    public void setCompulsoryAmount(Float compulsoryAmount) {
        this.compulsoryAmount = compulsoryAmount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public HuanInsuranceBaseInfoData getCompulsoryBaseInfo() {
        return compulsoryBaseInfo;
    }

    public void setCompulsoryBaseInfo(HuanInsuranceBaseInfoData compulsoryBaseInfo) {
        this.compulsoryBaseInfo = compulsoryBaseInfo;
    }

    public HuanInsuranceBaseInfoData getCommerceBaseInfo() {
        return commerceBaseInfo;
    }

    public void setCommerceBaseInfo(HuanInsuranceBaseInfoData commerceBaseInfo) {
        this.commerceBaseInfo = commerceBaseInfo;
    }

    public List<InsuranceItemData> getInsuranceItem() {
        return insuranceItem;
    }

    public void setInsuranceItem(List<InsuranceItemData> insuranceItem) {
        this.insuranceItem = insuranceItem;
    }

    public HuanPriceData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.commerceAmount);
        dest.writeValue(this.compulsoryAmount);
        dest.writeString(this.companyName);
        dest.writeValue(this.companyId);
        dest.writeValue(this.priceId);
        dest.writeValue(this.orderId);
        dest.writeParcelable(this.compulsoryBaseInfo, 0);
        dest.writeParcelable(this.commerceBaseInfo, 0);
        dest.writeParcelable(this.tax, 0);
        dest.writeTypedList(insuranceItem);
    }

    protected HuanPriceData(Parcel in) {
        this.commerceAmount = (Float) in.readValue(Float.class.getClassLoader());
        this.compulsoryAmount = (Float) in.readValue(Float.class.getClassLoader());
        this.companyName = in.readString();
        this.companyId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.priceId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.compulsoryBaseInfo = in.readParcelable(HuanInsuranceBaseInfoData.class.getClassLoader());
        this.commerceBaseInfo = in.readParcelable(HuanInsuranceBaseInfoData.class.getClassLoader());
        this.tax = in.readParcelable(VhlTax.class.getClassLoader());
        this.insuranceItem = in.createTypedArrayList(InsuranceItemData.CREATOR);
    }

    public static final Creator<HuanPriceData> CREATOR = new Creator<HuanPriceData>() {
        public HuanPriceData createFromParcel(Parcel source) {
            return new HuanPriceData(source);
        }

        public HuanPriceData[] newArray(int size) {
            return new HuanPriceData[size];
        }
    };
}
