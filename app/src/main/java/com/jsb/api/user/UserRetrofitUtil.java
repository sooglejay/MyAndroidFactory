package com.jsb.api.user;

import android.content.Context;
import android.util.Log;

import com.jsb.api.callback.NetCallback;
import com.jsb.model.AccountData;
import com.jsb.model.CommData;
import com.jsb.model.FreedomData;
import com.jsb.model.MyInsuranceData;
import com.jsb.model.MyWalletData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.OvertimeData;
import com.jsb.model.Overtimeinsurance;
import com.jsb.model.PauseData;
import com.jsb.model.ReportableInsurance;
import com.jsb.model.TeamData;
import com.jsb.model.Userstable;
import com.jsb.util.Base64Util;
import com.jsb.util.MD5Util;
import com.jsb.util.RetrofitUtil;

import java.util.List;

import retrofit.RestAdapter;

public class UserRetrofitUtil extends RetrofitUtil {

    /**
     * 获取验证码
     *
     * @param mContext
     * @param phone
     * @param callback
     */
    public static void obtainVerifyCode(Context mContext, String phone, NetCallback<NetWorkResultBean<CommData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String p = new String("phone=");
        String k = p + phone;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "s=" + s);
        git.obtainVerifyCode(s, callback);
    }

    /**
     * 获取验证码
     *
     * @param mContext
     * @param phone
     * @param callback
     */
    public static void login(Context mContext, String phone, String verifyCode, NetCallback<NetWorkResultBean<CommData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "phone=" + phone + "&verifyCode=" + verifyCode;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.login(s, callback);
    }

    /**
     * 获取在售加班险信息
     *
     * @param mContext
     * @param callback
     */
    public static void getOvertimeInsuranceInfo(Context mContext, NetCallback<NetWorkResultBean<OvertimeData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        git.getOvertimeInsuranceInfo("", callback);
    }


    /**
     * 加班狗报案
     *
     * @param mContext
     * @param userid
     * @param callback
     */
    public static void getReportableInsurance(Context mContext, int userid, NetCallback<NetWorkResultBean<ReportableInsurance>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getReportableInsurance(s, callback);
    }


    /**
     * 设置提现等 操作时候的 密码
     *
     * @param mContext
     * @param callback
     */
    public static void setWithdrawlPassword(Context mContext, String phone, String verifyCode, String withdrawlPwd, NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String w = MD5Util.getMD5(withdrawlPwd);
        String k = "phone=" + phone + "&verifyCode=" + verifyCode + "&withdrawlPwd=" + w;
        String s = Base64Util.encode(k.getBytes());
        git.setWithdrawlPassword(s, callback);
    }


    /**

     发送时机	提交订单时保存订单信息
     参数说明
     1、String phone； // 被保人电话
     2、float money； // 保费
     3、String companyaddress； // 公司地址
     4、String homeaddress; //家庭地址
     *
     * @param mContext
     * @param callback
     */
    public static void saveOvertimeInsuranceOrder(Context mContext,
                                                  String phone,
                                                  float money,
                                                  String  companyaddress,
                                                  String homeaddress,
                                                  NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String key =
                "phone=" + phone +
                "&money=" + money +
                "&companyaddress=" + companyaddress +
                "&homeaddress=" + homeaddress;
        String value = Base64Util.encode(key.getBytes());
        Log.e("Retrofit", "original:" + key + "\nbase64:" + value);
        git.saveOvertimeInsuranceOrder(value, callback);
    }


    /**
     * 设置提现等 操作时候的 密码
     *
     * @param mContext
     * @param callback
     */
    public static void getPauseInfo(Context mContext,int userid , NetCallback<NetWorkResultBean<List<PauseData>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getPauseInfo(s, callback);
    }


    /**
     * 7.7.2.保存限行停保
     参数说明
     1、int userid ; //用户编号
     2、int orderid ; //保单编号
     3、float dayPrice ;// 暂停一天所得费用
     4、int day_of_week;// 每周几暂停  约定  周日 周一…周六 ，分别对应 1 2…7
     * 发送时机	用户点击限行暂停时调用
     *
     * @param mContext
     * @param callback
     */
    public static void saveLimitPauseInfo(Context mContext,
                                          int userid ,
                                          int orderid  ,
                                          float dayPrice   ,
                                          int day_of_week   ,
                                          NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid+
                "&orderid="+orderid+
                "&dayPrice="+dayPrice+
                "&day_of_week="+day_of_week;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.saveLimitPauseInfo(s, callback);
    }


    /**
     * 7.7.3.保存预约停保
     参数说明
     1、int userid ; //用户编号
     2、int orderid ; //保单编号
     3、float dayPrice ;// 暂停一天所得费用
     4、string  reservedays;// 预约日期，年月日多个以豆号隔开如“2015-09-07，2015-09-12”,保单开始结束时间在7.7.1接口中已返回，手机端在选择日期时要判断，只能选择从当前日期之后的2个工作日开始选，且不能选超过结束日期。
     * 发送时机	用户选择预约暂停时调用
     *
     * @param mContext
     * @param callback
     */
    public static void saveReservePauseInfo(Context mContext,
                                          int userid ,
                                          int orderid ,
                                          float dayPrice ,
                                          String reservedays ,
                                          NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid+
                "&orderid="+orderid+
                "&dayPrice="+dayPrice+
                "&reservedays="+reservedays;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.saveReservePauseInfo(s, callback);
    }


    /**
     * 按团长名字，电话，团名搜索团信息
     *
     * @param mContext
     * @param callback
     */
    public static void searchTeam(Context mContext,String searchParam , NetCallback<NetWorkResultBean<List<TeamData>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "searchParam=" + searchParam;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.searchTeam(s, callback);
    }

    /**
     * 7.8.5.3.随机获取4个团队信息
     *
     * @param mContext
     * @param callback
     */
    public static void getFourTeamInfo(Context mContext, NetCallback<NetWorkResultBean<List<FreedomData>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        git.getFourTeamInfo("", callback);
    }

    /**
     * 停保滑动时，验证密码
     * @param mContext
     * @param callback
     */
    public static void verifyPwd(Context mContext,String phone,String pwd, NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String w = MD5Util.getMD5(pwd);
        String k = "phone=" + phone+"&pwd="+w;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.verifyPwd(s, callback);
    }
    /**
     * 用户重置手机号，提交手机号和验证码。
     * @param mContext
     * @param callback
     */
    public static void resetSubmitPhoneAndVerifycode(Context mContext,String oldPhone,String newPhone,String verifyCode, NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "oldPhone=" + oldPhone+"&newPhone="+newPhone+"&verifyCode="+verifyCode;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.resetSubmitPhoneAndVerifycode(s, callback);
    }



    /**
     * 7.4.车险
     7.4.1.保存车辆信息

     1、int userid； // 用户编号
     2、String licenseplate； // 车牌号
     3、String enginenumber； // 发动机号
     4、String framenumber； // 车架号
     5、Int seatingcapacity； // 几座
     6、Int registrationDate； // 登记时间 （毫秒）
     7、String ownerName； // 车主姓名
     8、Int commercestartdate； //商业险起效日期（毫秒）
     9、Int compulsorystartdate ; //交强险起效日期（毫秒）

     参数1、2、4、5、6、7、8、9为必填，若用户未填7 就默认传userid值
     8、9若只选则了一个（至少选择一个），则未选中的给默认值0
     * @param mContext
     * @param callback
     *
     */
    public static void saveVehicleInfo(Context mContext,
                                      int userid,
                                       String licenseplate,
                                       String enginenumber,
                                       String framenumber,
                                       int seatingcapacity,
                                       long registrationDate,
                                       String ownerName,
                                       long commercestartdate,
                                       long compulsorystartdate,

                                       NetCallback<NetWorkResultBean<CommData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid+
                "&licenseplate=" +licenseplate+
                "&enginenumber="+enginenumber+
                "&framenumber="+framenumber+
                "&seatingcapacity="+seatingcapacity +
                "&registrationDate="+registrationDate+
                "&ownerName="+ownerName+
                "&commercestartdate="+commercestartdate+
                "&compulsorystartdate="+compulsorystartdate ;

        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.saveVehicleInfo(s, callback);
    }



    /**
     *7.4.2.保存报价信息
     接口名称	saveOfferPrice
     发送时机	查看各个保险公司报价页面切换时调用，或者点击提交时调用。

     1、double totalprice； // 总价格，保留两位小数
     2、Int companyid； // 保险公司编号
     3、Int orderid； // 车险订单编号
     4、String damage； //车辆损失险，格式：险种名字,保额,保费
     5、String third； //第三者责任险，格式：险种名字,保额,保费
     6、String seat； //车上人员责任险，格式：险种名字,保额,保费,几座
     7、String steal； //全车盗抢险，格式：险种名字,保额,保费
     8、String free； //不计免赔险，格式：险种名字,保额,保费
     9、String grass;// 玻璃单独破碎险，格式：险种名字,保额,保费
     10、String scratch;// 车身划痕损失险，格式：险种名字,保额,保费
     11、String fire;// 自燃损失险，格式：险种名字,保额,保费
     12、String engine;// 发动机涉水损失险，格式：险种名字,保额,保费
     13、String compensation;// 修理期间费用补偿险，格式：险种名字,保额,保费
     14、String nothird;// 损失无法找到第三方特约险，格式：险种名字,保额,保费
     15、String repair;// 指定修理厂险，格式：险种名字,保额,保费
     16、String force;//交强险，格式：险种名字,保额,保费

     参数1、2，3，4、5、6、7、8、9、10、11、12、13、14、15、16为必填。
     注意：隔开的豆号,是英文状态的豆号,如果该险种没买（没选），传值“-1”：
     例如,damage=车辆损失险,200000,340.50&force=-1。座位险多一个座位值哦！
     * @param mContext
     * @param callback
     *
     */
    public static void saveOfferPrice(Context mContext,
                                      float  totalprice,
                                       int companyid,
                                       int orderid,
                                       String damage,
                                       String third,
                                       String seat,
                                       String steal,
                                       String free,
                                       String grass,
                                      String scratch,
                                      String fire,
                                      String engine,
                                      String compensation,
                                      String nothird,
                                      String repair,
                                      String force,

                                       NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "totalprice=" + totalprice+
                "&companyid=" +companyid+
                "&orderid="+orderid+
                "&damage="+damage+
                "&third="+third +
                "&seat="+seat+
                "&steal="+steal+
                "&free="+free+
                "&grass="+grass +
                "&scratch="+scratch +
                "&fire="+fire +
                "&engine="+engine +
                "&compensation="+compensation +
                "&nothird="+nothird +
                "&repair="+repair +
                "&force="+force
                ;

        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.saveOfferPrice(s, callback);
    }



    /**
     * 7.4.3.选择报价方案
     * 发送时机	提交订单时，用于确定选择的是何种报价。
     * @param mContext
     * @param callback
     */
    public static void selectPlan(Context mContext,
                                  int companyid,
                                  int orderid,
                                  NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "companyid=" + companyid+"&orderid="+orderid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.selectPlan(s, callback);
    }


    /**
     * 获取保险公司信息
     * 发送时机	获取所有保险公司信息，如网点 、政策，电话
     * @param mContext
     * @param callback
     */
    public static void getInsuranceCompanyInfo(Context mContext,
                                  NetCallback<NetWorkResultBean<CommData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        git.getInsuranceCompanyInfo("", callback);
    }



    /**
     * 7.4.5.保存订单信息
     * 发送时机	提交订单时保存订单信息
     *
     * 参数说明
     1、String name； // 被保人姓名
     2、Int orderid； // 订单编号
     3、String phone； // 被保人电话
     4、String idcardnum； // 身份证号
     5、String recievename; //保单收件人姓名 用户未选传默认值为 1、name
     6、String receivephone; //收件人电话，无则传3、phone值
     7、String recieveaddress; //接收地址，无则传3、phone值
     8、String refereephone ; //推荐人手机，无则传3、phone值
     9、String operatorphone ;//经办人手机
     10、String provence; //出险省，即买保险的省
     * @param mContext
     * @param callback
     */
    public static void confirmVehicleOrder(Context mContext,
                                        String name,
                                        int orderid,
                                        String phone,
                                        String idcardnum,
                                        String recievename,
                                        String receivephone,
                                        String recieveaddress,
                                        String refereephone ,
                                        String operatorphone ,
                                        String provence ,
                                  NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "name=" + name+
                "&orderid="+orderid+
                "&phone="+phone+
                "&idcardnum="+idcardnum+
                "&recievename="+recievename+
                "&receivephone="+receivephone+
                "&recieveaddress="+recieveaddress+
                "&refereephone="+refereephone+
                "&operatorphone="+operatorphone+
                "&provence="+provence
                ;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.confirmVehicleOrder(s, callback);
    }



    /**
     * 发送时机	启动页面，后台发送检测版本更新情况（用于比对本地和服务的版本）
     * @param mContext
     * @param callback
     */
    public static void checkUpdate(Context mContext,
                                               NetCallback<NetWorkResultBean<CommData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        git.checkUpdate("", callback);
    }




    /**
     * 7.4.5.保存订单信息
     * 发送时机	提交订单时保存订单信息
     *
     参数说明
     1、int userid； // 用户编号
     2、String company；// 公司名
     3、String  companyaddress; // 公司地址
     4、String service; //服务介绍
     * @param mContext
     * @param callback
     */
    public static void modifySelfInfo(Context mContext,
                                           int userid,
                                           String company,
                                           String companyaddress,
                                           String service,

                                           NetCallback<NetWorkResultBean<Userstable>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid+
                "&company="+company+
                "&companyaddress="+companyaddress+
                "&service="+service
                ;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.modifySelfInfo(s, callback);
    }

    /**
     * 7.7.4.取消停保
     * 发送时机	用户选择取消暂停调用
     *
     参数说明
     1、int userid ; //用户编号
     2、int orderid ; //保单编号
     3、int type ;//   0限行取消   1预约取消
     * @param mContext
     * @param callback
     */
    public static void cancelPause(Context mContext,
                                           int userid,
                                           int orderid,
                                           int type,
                                           NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid+
                "&orderid="+orderid+
                "&type="+type;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.cancelPause(s, callback);
    }

    /**
     * 7.8.1.我的钱包
     7.8.1.1.可用余额
     * 发送时机	用户点击我的钱包，获取个人钱包信息
     *
     参数说明
     1、int userid ; //用户编号
     * @param mContext
     * @param callback
     */
    public static void getMywalletInfo(Context mContext,
                                           int userid,
                                           NetCallback<NetWorkResultBean<MyWalletData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getMywalletInfo(s, callback);
    }



    /**
     * 7.8.1.2.提现
     * 发送时机	用户提现
     *
     参数说明
     1、int userid ; //用户编号
     2、int type; //提现类型 停保费 1；加班险费2 ；奖励费3。
     3、float amount ;// 提现金额，小数位最多2位
     4、int  realname ;//姓名
     5、int  withdrawlPwd；// 提现密码（Md5编码）
     6、String account；//账号
     7、String  union;// 银联名字（支付宝就填支付宝，微信就填微信）
     8、int accountType ;//账号类型  提现账号的类型  0 银联  1 支付宝 2微信
     限制条件	参数1、2、3、4、5、6、7、8为必填。
     * @param mContext
     * @param callback
     */
    public static void saveWithdrawlInfo(Context mContext,
                                       int userid,
                                         int type,
                                         float amount,
                                         int  realname,
                                         int  withdrawlPwd,
                                         String account,
                                         String  union,
                                         int accountType,
                                         NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid+
                "&type="+type+
                "&amount="+amount+
                "&realname="+realname+
                "&withdrawlPwd="+withdrawlPwd+
                "&account="+account+
                "&union="+union+
                "&accountType="+accountType;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.saveWithdrawlInfo(s, callback);
    }


    /**
     * 7.8.1.3.提现获取上一次账号信息
     * 发送时机	用户提现时，用于获取上一次填写的账号信息，可以作为默认值使用
     *
     参数说明
     1、int userid ; //用户编号
     * @param mContext
     * @param callback
     */
    public static void getLastAccountInfo(Context mContext,
                                       int userid,
                                       NetCallback<NetWorkResultBean<AccountData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getLastAccountInfo(s, callback);
    }


    /**
     7.8.2.我的保险
     7.8.2.1.首页保险分类列表
     接口名称	getMyinsuranceListInfo
     发送时机	我的保险首页保险分类列表信息，每类均返回一个代表和该类的总数，点击后边有具体分页获取接口
     参数说明	1、int userid ; //用户编号
     限制条件	参数1为必填。
     * @param mContext
     * @param callback
     */
    public static void getMyinsuranceListInfo(Context mContext,
                                       int userid,
                                       NetCallback<NetWorkResultBean<MyInsuranceData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getMyinsuranceListInfo(s, callback);
    }


}
