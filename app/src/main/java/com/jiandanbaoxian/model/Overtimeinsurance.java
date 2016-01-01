package com.jiandanbaoxian.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 获取在售加班险信息
 */
public class Overtimeinsurance implements Parcelable {
//
//    5.5.2.加班险信息Overtimeinsurance
//    名称	类型	说明	不可为空
//    id	int	编号	Y
//    releasetime	timestamp	放险时间	Y
//    amount	int	放险数量	Y
//    residue	int	剩余数量	Y
//    status	int	在售与否  0 停售  1 在售	Y
//    coverage	float	保额（出险赔多少）	Y
//    fee	float	保费（每份多少钱）	Y
//    rule	string	加班险规则	Y
//


    private Integer id;//编号
    private Integer amount;//放险数量
    private Integer residue;//剩余数量
    private Integer status;//在售与否  0 停售  1 在售
    private Long releasetime;//放险时间
    private String rule;//加班险规则	Y
    private Float fee;//保费（每份多少钱）	Y
    private Float coverage;//保额（出险赔多少）	Y

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getResidue() {
        return residue;
    }

    public void setResidue(Integer residue) {
        this.residue = residue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(Long releasetime) {
        this.releasetime = releasetime;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public Float getCoverage() {
        return coverage;
    }

    public void setCoverage(Float coverage) {
        this.coverage = coverage;
    }

    @Override
    public String toString() {
        return "Overtimeinsurance{" +
                "id=" + id +
                ", amount=" + amount +
                ", residue=" + residue +
                ", status=" + status +
                ", releasetime=" + releasetime +
                ", rule='" + rule + '\'' +
                ", fee=" + fee +
                ", coverage=" + coverage +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.amount);
        dest.writeValue(this.residue);
        dest.writeValue(this.status);
        dest.writeValue(this.releasetime);
        dest.writeString(this.rule);
        dest.writeValue(this.fee);
        dest.writeValue(this.coverage);
    }

    public Overtimeinsurance() {
    }

    protected Overtimeinsurance(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.residue = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.releasetime = (Long) in.readValue(Long.class.getClassLoader());
        this.rule = in.readString();
        this.fee = (Float) in.readValue(Float.class.getClassLoader());
        this.coverage = (Float) in.readValue(Float.class.getClassLoader());
    }

    public static final Creator<Overtimeinsurance> CREATOR = new Creator<Overtimeinsurance>() {
        public Overtimeinsurance createFromParcel(Parcel source) {
            return new Overtimeinsurance(source);
        }

        public Overtimeinsurance[] newArray(int size) {
            return new Overtimeinsurance[size];
        }
    };
}
