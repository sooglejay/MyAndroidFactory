package com.jiandanbaoxian.bean;

/**
 * Created by JammyQtheLab on 2015/10/23.
 */
public class aaa_MyMoneyPocketBean {
    private int status;//标志
    private String moneyKind;//收益名称
    private float moneyAmount;//收益金额

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMoneyKind() {
        return moneyKind;
    }

    public void setMoneyKind(String moneyKind) {
        this.moneyKind = moneyKind;
    }

    public float getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(float moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
