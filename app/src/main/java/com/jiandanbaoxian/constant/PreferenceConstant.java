package com.jiandanbaoxian.constant;

public class PreferenceConstant {

    public static final String TimePickerDateStart = "dateStart";
    public static final String TimePickerDateEnd = "dateEnd";
    public static final String TimePickerDateInterval = "dateInterval";
    public static final String timeStringFroServer = "timeStringFroServer";


    //用户信息相关
    public static final String version = "version";
    public static final String userid = "userid";
    public static final String id = "id";
    public static final String phone = "phone";
    public static final String name = "name";
    public static final String companyname = "companyname";
    public static final String companyaddress = "companyaddress";
    public static final String type = "type";
    public static final String idcardname = "idcardname";
    public static final String contactaddress = "contactaddress";
    public static final String sex = "sex";
    public static final String city = "city";
    public static final String idnumber = "idnumber";
    public static final String pwd = "pwd";


    //car insurance preference constant
    public static final String engineNumber = "engineNumber";
    public static final String frameNumber = "frameNumber";
    public static final String etOwnerName = "etOwnerName";
    public static final String etIdNumber = "etIdNumber";
    public static final String etLicensePlateNumber = "etLicensePlateNumber";
    public static final String registrationDateLong = "registrationDateLong";
    public static final String issueDateLong = "issueDateLong";

    public static final String commercestartdate = "commercestartdate";
    public static final String compulsorystartdate = "compulsorystartdate";


    public static String insuranceUserName = "insuranceUserName";
    public static String insuranceUserPhone = "insuranceUserPhone";
    public static String insuranceReceiveUserName = "insuranceReceiveUserName";
    public static String insuranceReceivePhone = "insuranceReceivePhone";
    public static String insuranceReceiveAddress = "insuranceReceiveAddress";
    public static String insuranceRecommendUserPhone = "insuranceRecommendUserPhone";
    public static String insuranceOperationUserPhone = "insuranceOperationUserPhone";


    //核保成功后 保存此次核保的纪录，如果用户没有支付，那么他下一次来核保时肯定会失败，然后我就使用这一次的本地保存的核保信息去支付
    //但是，记得在支付成功的时候，清除掉本地的核保数据
    //核保成功后  返回的  商业险编号
    public static String confirmCommercialNo = "confirmCommercialNo";
    //核保成功后  返回的  交强险编号
    public static String ConfirmCompulsoryNo = "ConfirmCompulsoryNo";
    public static String ConfirmCompulsoryAmount = "ConfirmCompulsoryAmount";
    public static String ConfirmCommercialAmount = "ConfirmCommercialAmount";
    public static String ConfirmCommercialStartDate = "ConfirmCommercialStartDate";
    public static String ConfirmCompulsoryStartDate = "ConfirmCompulsoryStartDate";
    public static String ConfirmCityNo = "ConfirmCityNo";
    public static String ConfirmType = "ConfirmType";
    public static String ConfirmCalAppNo = "ConfirmCalAppNo";


}
