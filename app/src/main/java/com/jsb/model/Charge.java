package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 5.2.Ping++支付对象Charge
 */
public class Charge implements Parcelable {
//
//    {
//        "id": "ch_X9qXXTr9WDKOfbLuzDer5e14",
//            "object": "charge",
//            "created": 1446899016,
//            "livemode": false,
//            "paid": false,
//            "refunded": false,
//            "app": "app_1Gqj58ynP0mHeX1q",
//            "channel": "wx",
//            "order_no": "03f816ec625a8014",
//            "client_ip": "125.71.229.16",
//            "amount": 55,
//            "amount_settle": 0,
//            "currency": "cny",
//            "subject": "Your Subject",
//            "body": "Your Body",
//            "extra": {},
//        "time_paid": null,
//            "time_expire": 1446906216,
//            "time_settle": null,
//            "transaction_no": null,
//            "refunds": {
//        "object": "list",
//                "url": "\/v1\/charges\/ch_X9qXXTr9WDKOfbLuzDer5e14\/refunds",
//                "has_more": false,
//                "data": []
//    },
//        "amount_refunded": 0,
//            "failure_code": null,
//            "failure_msg": null,
//            "metadata": {},
//        "credential": {
//        "object": "credential",
//                "wx": {
//            "appId": "wx4yb5adyle1ypufj9",
//                    "partnerId": "1272046101",
//                    "prepayId": "1101000000151107zhurr9tuhk88bjzx",
//                    "nonceStr": "c1e6c5e37d7c27c1c9ff5dbd925085c6",
//                    "timeStamp": 1446899016,
//                    "packageValue": "Sign=WXPay",
//                    "sign": "266d0e8767f4d3732e8b7494c8a7cfa6d9b66068"
//        }
//    },
//        "description": null
//    }


//private static class Refunds{
//    private String object;//
//    private String url;//
//    private Boolean has_more;//
//    private List<Charge>data;
//}


//private static class Metadata {
////    一些 Ping++ 对象支持加入用户指定的 metadata 参数。
//// 你可以使用键值对的形式来构建自己的 metadata，例如 metadata[color] = red，
//// 你可以在每一个 charge 对象中加入订单的一些详情，如颜色、型号等属性，在查询时获得更多信息。
//// 每一个对象的 metadata 最多可以拥有 10 个键值对，数据总长度在 1000 个 Unicode 字符以内。
//}


    private String order_no;//
    private Integer amount_settle;//
    private Object extra;//
    private Long time_paid;//
    private Long time_expire;//
    private Long time_settle;//
    private String transaction_no;//
    private Object refunds;//
    private Integer amount_refunded;//
    private String failure_code;//
    private String failure_msg;//
    private Object metadata;//

    @Override
    public String toString() {
        return "Charge{" +
                "order_no='" + order_no + '\'' +
                ", amount_settle=" + amount_settle +
                ", extra=" + extra +
                ", time_paid=" + time_paid +
                ", time_expire=" + time_expire +
                ", time_settle=" + time_settle +
                ", transaction_no='" + transaction_no + '\'' +
                ", refunds=" + refunds +
                ", amount_refunded=" + amount_refunded +
                ", failure_code='" + failure_code + '\'' +
                ", failure_msg='" + failure_msg + '\'' +
                ", metadata=" + metadata +
                ", id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", livemode=" + livemode +
                ", paid=" + paid +
                ", refunded=" + refunded +
                ", app='" + app + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", currency='" + currency + '\'' +
                ", userid=" + userid +
                ", type=" + type +
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
                ", channel='" + channel + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", clientIp='" + clientIp + '\'' +
                '}';
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Integer getAmount_settle() {
        return amount_settle;
    }

    public void setAmount_settle(Integer amount_settle) {
        this.amount_settle = amount_settle;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public Long getTime_paid() {
        return time_paid;
    }

    public void setTime_paid(Long time_paid) {
        this.time_paid = time_paid;
    }

    public Long getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(Long time_expire) {
        this.time_expire = time_expire;
    }

    public Long getTime_settle() {
        return time_settle;
    }

    public void setTime_settle(Long time_settle) {
        this.time_settle = time_settle;
    }

    public String getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(String transaction_no) {
        this.transaction_no = transaction_no;
    }

    public Object getRefunds() {
        return refunds;
    }

    public void setRefunds(Object refunds) {
        this.refunds = refunds;
    }

    public Integer getAmount_refunded() {
        return amount_refunded;
    }

    public void setAmount_refunded(Integer amount_refunded) {
        this.amount_refunded = amount_refunded;
    }

    public String getFailure_code() {
        return failure_code;
    }

    public void setFailure_code(String failure_code) {
        this.failure_code = failure_code;
    }

    public String getFailure_msg() {
        return failure_msg;
    }

    public void setFailure_msg(String failure_msg) {
        this.failure_msg = failure_msg;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    private String id;//String 单号	Y
    private String object;//String	支付主题（固定值charge）	Y
    private Long created;//int	创建时间（秒）
    private Boolean livemode;//boolean	是否是使用模式，（有开发和测试模式）	Y
    private Boolean paid;//boolean	是否付款	Y
    private Boolean refunded;//boolean	是否有退款
    private String app;//String	应用编号	Y
    private String subject;//String	支付主题
    private String body;//String	支付说明
    private String currency;//String	货比类型，统一为人民币（“cny”）	Y
    private Integer userid;//int	被保险人的编号
    private Integer type;//保险类型： 0车险  1加班险  2驾驶险
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
    private String channel;//String	支付渠道，参见ping++可选项	Y
    private String orderNo;//String	支付渠道要用的订单编号（8-20位）
    private String clientIp;//String	客户端ip	Y

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

    public Charge() {
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public static Creator<Charge> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.order_no);
        dest.writeValue(this.amount_settle);
        dest.writeParcelable((Parcelable) this.extra, flags);
        dest.writeValue(this.time_paid);
        dest.writeValue(this.time_expire);
        dest.writeValue(this.time_settle);
        dest.writeString(this.transaction_no);
        dest.writeParcelable((Parcelable) this.refunds, flags);
        dest.writeValue(this.amount_refunded);
        dest.writeString(this.failure_code);
        dest.writeString(this.failure_msg);
        dest.writeParcelable((Parcelable) this.metadata, flags);
        dest.writeString(this.id);
        dest.writeString(this.object);
        dest.writeValue(this.created);
        dest.writeValue(this.livemode);
        dest.writeValue(this.paid);
        dest.writeValue(this.refunded);
        dest.writeString(this.app);
        dest.writeString(this.subject);
        dest.writeString(this.body);
        dest.writeString(this.currency);
        dest.writeValue(this.userid);
        dest.writeValue(this.type);
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
        dest.writeString(this.channel);
        dest.writeString(this.orderNo);
        dest.writeString(this.clientIp);
    }

    protected Charge(Parcel in) {
        this.order_no = in.readString();
        this.amount_settle = (Integer) in.readValue(Integer.class.getClassLoader());
        this.extra = in.readParcelable(Object.class.getClassLoader());
        this.time_paid = (Long) in.readValue(Long.class.getClassLoader());
        this.time_expire = (Long) in.readValue(Long.class.getClassLoader());
        this.time_settle = (Long) in.readValue(Long.class.getClassLoader());
        this.transaction_no = in.readString();
        this.refunds = in.readParcelable(Object.class.getClassLoader());
        this.amount_refunded = (Integer) in.readValue(Integer.class.getClassLoader());
        this.failure_code = in.readString();
        this.failure_msg = in.readString();
        this.metadata = in.readParcelable(Object.class.getClassLoader());
        this.id = in.readString();
        this.object = in.readString();
        this.created = (Long) in.readValue(Long.class.getClassLoader());
        this.livemode = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.paid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.refunded = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.app = in.readString();
        this.subject = in.readString();
        this.body = in.readString();
        this.currency = in.readString();
        this.userid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
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
        this.channel = in.readString();
        this.orderNo = in.readString();
        this.clientIp = in.readString();
    }

    public static final Creator<Charge> CREATOR = new Creator<Charge>() {
        public Charge createFromParcel(Parcel source) {
            return new Charge(source);
        }

        public Charge[] newArray(int size) {
            return new Charge[size];
        }
    };
}
