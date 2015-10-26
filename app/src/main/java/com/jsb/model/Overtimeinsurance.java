package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 获取在售加班险信息
 */
public class Overtimeinsurance implements Parcelable {

    private Integer id;//编号
    private Integer amount;//放险数量
    private int residue;//剩余数量
    private Integer status;//在售与否  0 停售  1 在售
    private Long releasetime;//放险时间

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
    }

    public Overtimeinsurance() {
    }

    protected Overtimeinsurance(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.residue = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.releasetime = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<Overtimeinsurance> CREATOR = new Parcelable.Creator<Overtimeinsurance>() {
        public Overtimeinsurance createFromParcel(Parcel source) {
            return new Overtimeinsurance(source);
        }

        public Overtimeinsurance[] newArray(int size) {
            return new Overtimeinsurance[size];
        }
    };


    @Override
    public String toString() {
        return "Overtimeinsurance{" +
                "id=" + id +
                ", amount=" + amount +
                ", residue=" + residue +
                ", status=" + status +
                ", releasetime=" + releasetime +
                '}';
    }
}
