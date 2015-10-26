package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *5.2.Ping++支付对象Charge
 */
public class Charge implements Parcelable {

    private String id;//String 单号	Y
    private String object;//String	支付主题（固定值charge）	Y
    private String subject;//String	支付主题
    private String body;//String	支付说明
    private String currency;//String	货比类型，统一为人民币（“cny”）	Y
    private Integer userid;//int	被保险人的编号
    private Integer type;//保险类型： 0车险  1加班险  2驾驶险
    private Integer created;//int	创建时间（秒）
    private Integer timePaid;//int	支付时间
    private Integer timeExpire;//int	支付有效期
    private Integer amountRefunded;//int	退款金额
    private String transactionNo;//String	交易编号
    private String failureCode;//String	失败编号
    private String failureMsg;//String	失败信息
    private String description;//String	附加描述
    private Object credential;//支付凭证
    private Object fefunds;//Object	退款对象
    private Integer amount;//int	支付金额（整数，单位分）	Y
    private Boolean livemode;//boolean	是否是使用模式，（有开发和测试模式）	Y
    private Boolean paid;//boolean	是否付款	Y
    private Boolean refunded;//boolean	是否有退款
    private String app;//String	应用编号	Y
    private String channel;//String	支付渠道，参见ping++可选项	Y
    private String orderNo;//String	支付渠道要用的订单编号（8-20位）
    private String clientIp;//String	客户端ip	Y

    @Override
    public String toString() {
        return "Charge{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", currency='" + currency + '\'' +
                ", userid=" + userid +
                ", type=" + type +
                ", created=" + created +
                ", timePaid=" + timePaid +
                ", timeExpire=" + timeExpire +
                ", amountRefunded=" + amountRefunded +
                ", transactionNo='" + transactionNo + '\'' +
                ", failureCode='" + failureCode + '\'' +
                ", failureMsg='" + failureMsg + '\'' +
                ", description='" + description + '\'' +
                ", credential=" + credential +
                ", fefunds=" + fefunds +
                ", amount=" + amount +
                ", livemode=" + livemode +
                ", paid=" + paid +
                ", refunded=" + refunded +
                ", app='" + app + '\'' +
                ", channel='" + channel + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", clientIp='" + clientIp + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getTimePaid() {
        return timePaid;
    }

    public void setTimePaid(Integer timePaid) {
        this.timePaid = timePaid;
    }

    public Integer getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(Integer timeExpire) {
        this.timeExpire = timeExpire;
    }

    public Integer getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(Integer amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public String getFailureMsg() {
        return failureMsg;
    }

    public void setFailureMsg(String failureMsg) {
        this.failureMsg = failureMsg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getCredential() {
        return credential;
    }

    public void setCredential(Object credential) {
        this.credential = credential;
    }

    public Object getFefunds() {
        return fefunds;
    }

    public void setFefunds(Object fefunds) {
        this.fefunds = fefunds;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getRefunded() {
        return refunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.object);
        dest.writeString(this.subject);
        dest.writeString(this.body);
        dest.writeString(this.currency);
        dest.writeValue(this.userid);
        dest.writeValue(this.type);
        dest.writeValue(this.created);
        dest.writeValue(this.timePaid);
        dest.writeValue(this.timeExpire);
        dest.writeValue(this.amountRefunded);
        dest.writeString(this.transactionNo);
        dest.writeString(this.failureCode);
        dest.writeString(this.failureMsg);
        dest.writeString(this.description);
        dest.writeParcelable((Parcelable) this.credential, flags);
        dest.writeParcelable((Parcelable) this.fefunds, flags);
        dest.writeValue(this.amount);
        dest.writeValue(this.livemode);
        dest.writeValue(this.paid);
        dest.writeValue(this.refunded);
        dest.writeString(this.app);
        dest.writeString(this.channel);
        dest.writeString(this.orderNo);
        dest.writeString(this.clientIp);
    }

    public Charge() {
    }

    protected Charge(Parcel in) {
        this.id = in.readString();
        this.object = in.readString();
        this.subject = in.readString();
        this.body = in.readString();
        this.currency = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.created = (Integer) in.readValue(Integer.class.getClassLoader());
        this.timePaid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.timeExpire = (Integer) in.readValue(Integer.class.getClassLoader());
        this.amountRefunded = (Integer) in.readValue(Integer.class.getClassLoader());
        this.transactionNo = in.readString();
        this.failureCode = in.readString();
        this.failureMsg = in.readString();
        this.description = in.readString();
        this.credential = in.readParcelable(Object.class.getClassLoader());
        this.fefunds = in.readParcelable(Object.class.getClassLoader());
        this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.livemode = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.paid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.refunded = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.app = in.readString();
        this.channel = in.readString();
        this.orderNo = in.readString();
        this.clientIp = in.readString();
    }

    public static final Parcelable.Creator<Charge> CREATOR = new Parcelable.Creator<Charge>() {
        public Charge createFromParcel(Parcel source) {
            return new Charge(source);
        }

        public Charge[] newArray(int size) {
            return new Charge[size];
        }
    };
}
