package com.jsb.api.user;

import android.content.Context;
import android.util.Log;

import com.jsb.api.callback.NetCallback;
import com.jsb.model.AccountData;
import com.jsb.model.Charge;
import com.jsb.model.ChargeBean;
import com.jsb.model.CommData;
import com.jsb.model.ConsultantData;
import com.jsb.model.FreedomData;
import com.jsb.model.HistoryPriceData;
import com.jsb.model.InviteInfo;
import com.jsb.model.MyInsuranceData;
import com.jsb.model.MyWalletData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.OvertimeData;
import com.jsb.model.Overtimeinsurance;
import com.jsb.model.Overtimeordertable;
import com.jsb.model.PauseData;
import com.jsb.model.PauseHistory;
import com.jsb.model.RangeData;
import com.jsb.model.ReportData;
import com.jsb.model.ReportableInsurance;
import com.jsb.model.SelfRecord;
import com.jsb.model.TeamData;
import com.jsb.model.Userstable;
import com.jsb.model.jugeOvertimeInsuranceOrder;
import com.jsb.util.Base64Util;
import com.jsb.util.MD5Util;
import com.jsb.util.RetrofitUtil;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.mime.TypedFile;

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
     发送时机	判断本周是否购买过
     参数说明	1、String phone;//用户电话
     * @param mContext
     * @param callback
     */
    public static void jugeOvertimeInsuranceOrder(Context mContext,
                                                  String phone,
                                                  NetCallback<jugeOvertimeInsuranceOrder> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "phone=" + phone;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.jugeOvertimeInsuranceOrder("", callback);
    }


    /**
     * 发送时机	获取加班明细
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int pageSize ; //每页数据量大小
     * 3、int pageNum；//当前页数
     *
     * @param mContext
     * @param callback
     */
    public static void getMyOvertimeInfo(Context mContext,
                                         int userid,
                                         int pageSize,
                                         int pageNum,

                                         NetCallback<NetWorkResultBean<MyWalletData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid +
                        "&pageSize=" + pageSize +
                        "&pageNum=" + pageNum;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getMyOvertimeInfo(s, callback);
    }


    /**
     * 发送时机	用户点击我要报案时，获取可报案列表
     * 参数说明	1、int userid ; //用户编号
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param userid
     * @param callback
     */
    public static void getReportableInsurance(Context mContext, int userid, NetCallback<NetWorkResultBean<ReportData>> callback) {
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
     2、Int overtimeInsuranceId;//上一个接口返回的加班险id
     3、float money； // 保费
     4、String companyaddress； // 公司地址
     5、String homeaddress; //家庭地址
     6、float  lat;//纬度   8位小数
     7、float lng;//经度   8位小数
     限制条件	参数1、2，3，4、5、6、7为必填。
     * @param mContext
     * @param callback
     */
    public static void saveOvertimeInsuranceOrder(Context mContext,
                                                  String phone,
                                                  int overtimeInsuranceId,
                                                  float money,
                                                  String companyaddress,
                                                  String homeaddress,
                                                  double  lat,
                                                  double  lng,
                                                  NetCallback<NetWorkResultBean<Overtimeordertable>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String key = "phone=" + phone +
                     "&overtimeInsuranceId=" + overtimeInsuranceId +
                        "&money=" + money +
                        "&companyaddress=" + companyaddress +
                        "&homeaddress=" + homeaddress+
                        "&lat=" + lat+
                        "&lng=" + lng
                ;
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
    public static void getPauseInfo(Context mContext, int userid, NetCallback<NetWorkResultBean<List<PauseData>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getPauseInfo(s, callback);
    }


    /**
     * 7.7.2.保存限行停保
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int orderid ; //保单编号
     * 3、float dayPrice ;// 暂停一天所得费用
     * 4、int day_of_week;// 每周几暂停  约定  周日 周一…周六 ，分别对应 1 2…7
     * 发送时机	用户点击限行暂停时调用
     *
     * @param mContext
     * @param callback
     */
    public static void saveLimitPauseInfo(Context mContext,
                                          int userid,
                                          int orderid,
                                          float dayPrice,
                                          int day_of_week,
                                          NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid +
                        "&orderid=" + orderid +
                        "&dayPrice=" + dayPrice +
                        "&day_of_week=" + day_of_week;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.saveLimitPauseInfo(s, callback);
    }


    /**
     * 7.7.3.保存预约停保
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int orderid ; //保单编号
     * 3、float dayPrice ;// 暂停一天所得费用
     * 4、string  reservedays;// 预约日期，年月日多个以豆号隔开如“2015-09-07，2015-09-12”,保单开始结束时间在7.7.1接口中已返回，手机端在选择日期时要判断，只能选择从当前日期之后的2个工作日开始选，且不能选超过结束日期。
     * 发送时机	用户选择预约暂停时调用
     *
     * @param mContext
     * @param callback
     */
    public static void saveReservePauseInfo(Context mContext,
                                            int userid,
                                            int orderid,
                                            float dayPrice,
                                            String reservedays,
                                            NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid +
                        "&orderid=" + orderid +
                        "&dayPrice=" + dayPrice +
                        "&reservedays=" + reservedays;
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
    public static void searchTeam(Context mContext, String searchParam, NetCallback<NetWorkResultBean<List<TeamData>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "searchParam=" + searchParam;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.searchTeam(s, callback);
    }


    /**
     * 发送时机	提交入团请求
     * 参数说明
     * 1、int userid; //用户id
     * 2、int teamid ;//申请入团的团队编号
     * 限制条件	参数1、2为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void submitJoinRequest(Context mContext,
                                         int userid,
                                         int teamid,
                                         NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid +
                        "&teamid=" + teamid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.submitJoinRequest(s, callback);
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
     发送时机	加入团队保存个人身份信息
     参数说明
     1、int userid ; //用户编号
     2、string realname ; //姓名
     3、String city；//地址
     4、String idcardnum ;//身份证号
     5、String refereephone;//推荐人电话，没有就传"0"
     6、int companyid;//公司编号  无则传-1
     7、int fourServiceId;//4s店编号  无则传-1
     8、String service;//服务介绍
     9、String worknum;//保险公司，员工工号，无传 “-1”
     本接口还有两个参数：一个imagesData,放图片（可多张身份证jpg），图片名字每次加个随机数上防止名字冲突，另一个photoData(一张头像图jpg)
     *
     * @param mContext
     */
    public static void fillInfoJoinTeam(Context mContext,
                                        int userid,
                                        String realname,
                                        String city,
                                        String idcardnum,
                                        String refereephone,
                                        int companyid,
                                        int fourServiceId,
                                        String service,
                                        String worknum,
                                        TypedFile imagesData,
                                        NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid +
                        "&realname=" + realname +
                        "&city=" + city +
                        "&idcardnum=" + idcardnum +
                        "&refereephone=" + refereephone +
                        "&companyid=" + companyid +
                        "&fourServiceId=" + fourServiceId +
                        "&service=" + service +
                        "&worknum=" + worknum
                ;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.fillInfoJoinTeam(s,imagesData, callback);
    }

    /**
     * 发送时机	用户点击加入团队时，调用此接口获取默认信息，有则用户可以不重新填写
     * 参数说明	1、int userid ; //用户编号
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getTeamRangeInfo(Context mContext, int userid, NetCallback<NetWorkResultBean<RangeData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getTeamRangeInfo(s, callback);
    }

    /**
     * 发送时机	获取邀请信息
     * 参数说明	1、int userid; //用户id
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getInviteInfo(Context mContext, int userid, NetCallback<NetWorkResultBean<List<InviteInfo>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getInviteInfo(s, callback);
    }

    /**
     * 发送时机	创建团队获取可选团员
     * 参数说明
     * 1、int userid; //用户id
     * 2、int pageSize;//每页容量
     * 3、int pageNum ;//页码
     *
     * @param mContext
     * @param callback
     */
    public static void getChoicers(Context mContext,
                                   int userid,
                                   int pageSize,
                                   int pageNum,
                                   NetCallback<NetWorkResultBean<List<Userstable>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid +
                        "&pageSize=" + pageSize +
                        "&pageNum=" + pageNum;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getChoicers(s, callback);
    }

    /**
     * 发送时机	团队创建好后，想添加团员时，获取可选人员
     * 参数说明
     * 1、int userid; //用户id
     * 2、int pageSize;//每页容量
     * 3、int pageNum ;//页码
     *
     * @param mContext
     * @param callback
     */
    public static void getAvailable(Context mContext,
                                    int userid,
                                    int pageSize,
                                    int pageNum,
                                    NetCallback<NetWorkResultBean<List<Userstable>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid +
                        "&pageSize=" + pageSize +
                        "&pageNum=" + pageNum;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getAvailable(s, callback);
    }

    /**
     * 发送时机	添加团员时，选择好人员，提交
     * 参数说明
     * 1、int leaderid; //用户id(团长id)
     * 2、String ids;//选中的队员编号，多个以豆号隔开
     *
     * @param mContext
     * @param callback
     */
    public static void addNewMember(Context mContext,
                                    int leaderid,
                                    String ids,
                                    NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "leaderid=" + leaderid +
                        "&ids=" + ids;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.addNewMember(s, callback);
    }

    /**
     * 发送时机	创建团队异步验证团名是否可用
     * 参数说明	1、String teamname;//团队名字
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void verifyTeamName(Context mContext,
                                      String teamname,
                                      NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "teamname=" + teamname;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.verifyTeamName(s, callback);
    }

    /**
     * 接口名称	createTeam
     * 发送时机	创建团队提交
     * 参数说明	1、int userid; //用户id
     * 2、String teamname;//团队名字
     * 3、String memberids;//多个以逗号隔开，例如： 1，2，3 小刘要做审核处理，给选定的人写邀请信息,或者调我的接口 ，没有勾选则传"",
     * 限制条件	参数1、2、3为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void createTeam(Context mContext,
                                  int userid,
                                  String teamname,
                                  String memberids,
                                  NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid +
                        "&teamname=" + teamname +
                        "&memberids=" + memberids;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.createTeam(s, callback);
    }

    /**
     * 发送时机	邀请入团信息处理
     * 参数说明
     * 1、int inviteinfoid; //邀请信息id
     * 2、int received ;//0不接受  1 接受
     * 限制条件	参数1、2为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void dealInviting(Context mContext,
                                    int inviteinfoid,
                                    int received,
                                    NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "inviteinfoid=" + inviteinfoid +
                        "&received=" + received;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.dealInviting(s, callback);
    }

    /**
     * 发送时机	团长查看申请
     * 参数说明	1、int userid; //用户id
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getJoinRequest(Context mContext,
                                      int userid,
                                      NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getJoinRequest(s, callback);
    }

    /**
     * 发送时机	团长提交审核结果
     * 参数说明
     * 1、int memberid; //请求用户id
     * 2、int teamid ;//申请入团的团队编号
     * 3、int  auditResult ;// 0未通过  1 通过
     *
     * @param mContext
     * @param callback
     */
    public static void auditJoinRequest(Context mContext,
                                        int memberid,
                                        int teamid,
                                        int auditResult,
                                        NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "memberid=" + memberid +
                        "&teamid=" + teamid +
                        "&auditResult=" + auditResult;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.auditJoinRequest(s, callback);
    }

    /**
     * 发送时机	团长查看团队信息
     * 参数说明	1、int userid; //用户id
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getMyTeamInfo(Context mContext,
                                     int userid,
                                     NetCallback<NetWorkResultBean<TeamData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getMyTeamInfo(s, callback);
    }

    /**
     * 发送时机	团长搜索团员
     * 参数说明
     * 1、int userid; //团长id
     * 2、String searchParam ;//电话或姓名,成员列表直接放在data中 SelfRecord
     * 限制条件	参数1、2为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void searchMember(Context mContext,
                                    int userid,
                                    String searchParam,
                                    NetCallback<NetWorkResultBean<SelfRecord>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid +
                        "&searchParam=" + searchParam;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.searchMember(s, callback);
    }

    /**
     * 发送时机	查询我的顾问
     * 参数说明	1、int userid; //用户id
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getMyConsultant(Context mContext,
                                       int userid,
                                       NetCallback<NetWorkResultBean<ConsultantData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getMyConsultant(s, callback);
    }

    /**
     * 发送时机	查询其他顾问
     * 参数说明
     * 1、int userid; //用户id
     * 2、int pageSize;//每页容量
     * 3、int pageNum;//页码
     *
     * @param mContext
     * @param callback
     */
    public static void getOtherConsultant(Context mContext,
                                          int userid,
                                          int pageSize,
                                          int pageNum,
                                          NetCallback<NetWorkResultBean<ConsultantData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid +
                        "&pageSize=" + pageSize +
                        "&pageNum=" + pageNum;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getOtherConsultant(s, callback);
    }

    /**
     * 发送时机	用户点击加入团队时，调用此接口获取默认信息，有则用户可以不重新填写
     * 参数说明	1、int userid ; //用户编号
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getSelfInfo(Context mContext, int userid, NetCallback<NetWorkResultBean<Userstable>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getSelfInfo(s, callback);
    }

    /**
     * 停保滑动时，验证密码
     *
     * @param mContext
     * @param callback
     */
    public static void verifyPwd(Context mContext, String phone, String pwd, NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String w = MD5Util.getMD5(pwd);
        String k = "phone=" + phone + "&pwd=" + w;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.verifyPwd(s, callback);
    }

    /**
     * 用户重置手机号，提交手机号和验证码。
     *
     * @param mContext
     * @param callback
     */
    public static void resetSubmitPhoneAndVerifycode(Context mContext, String oldPhone, String newPhone, String verifyCode, NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "oldPhone=" + oldPhone + "&newPhone=" + newPhone + "&verifyCode=" + verifyCode;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.resetSubmitPhoneAndVerifycode(s, callback);
    }


    /**
     * 7.4.车险
     * 7.4.1.保存车辆信息
     * <p/>
     * 1、int userid； // 用户编号
     * 2、String licenseplate； // 车牌号
     * 3、String enginenumber； // 发动机号
     * 4、String framenumber； // 车架号
     * 5、Int seatingcapacity； // 几座
     * 6、Int registrationDate； // 登记时间 （毫秒）
     * 7、String ownerName； // 车主姓名
     * 8、Int commercestartdate； //商业险起效日期（毫秒）
     * 9、Int compulsorystartdate ; //交强险起效日期（毫秒）
     * 9、Int issueDate ; //发证日期（毫秒）
     * <p/>
     * 参数1、2、4、5、6、7、8、9为必填，若用户未填7 就默认传userid值
     * 8、9若只选则了一个（至少选择一个），则未选中的给默认值0
     *
     * @param mContext
     * @param callback
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
                                       long issueDate,

                                       NetCallback<NetWorkResultBean<CommData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid +
                "&licenseplate=" + licenseplate +
                "&enginenumber=" + enginenumber +
                "&framenumber=" + framenumber +
                "&seatingcapacity=" + seatingcapacity +
                "&registrationDate=" + registrationDate +
                "&ownerName=" + ownerName +
                "&commercestartdate=" + commercestartdate +
                "&compulsorystartdate=" + compulsorystartdate+
                "&issueDate=" + issueDate
                ;

        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.saveVehicleInfo(s, callback);
    }


    /**
     * 7.4.2.保存报价信息
     * 接口名称	saveOfferPrice
     * 发送时机	查看各个保险公司报价页面切换时调用，或者点击提交时调用。
     * <p/>
     * 1、double totalprice； // 总价格，保留两位小数
     * 2、Int companyid； // 保险公司编号
     * 3、Int orderid； // 车险订单编号
     * 4、String damage； //车辆损失险，格式：险种名字,保额,保费
     * 5、String third； //第三者责任险，格式：险种名字,保额,保费
     * 6、String seat； //车上人员责任险，格式：险种名字,保额,保费,几座
     * 7、String steal； //全车盗抢险，格式：险种名字,保额,保费
     * 8、String free； //不计免赔险，格式：险种名字,保额,保费
     * 9、String grass;// 玻璃单独破碎险，格式：险种名字,保额,保费
     * 10、String scratch;// 车身划痕损失险，格式：险种名字,保额,保费
     * 11、String fire;// 自燃损失险，格式：险种名字,保额,保费
     * 12、String engine;// 发动机涉水损失险，格式：险种名字,保额,保费
     * 13、String compensation;// 修理期间费用补偿险，格式：险种名字,保额,保费
     * 14、String nothird;// 损失无法找到第三方特约险，格式：险种名字,保额,保费
     * 15、String repair;// 指定修理厂险，格式：险种名字,保额,保费
     * 16、String force;//交强险，格式：险种名字,保额,保费
     * <p/>
     * 参数1、2，3，4、5、6、7、8、9、10、11、12、13、14、15、16为必填。
     * 注意：隔开的豆号,是英文状态的豆号,如果该险种没买（没选），传值“-1”：
     * 例如,damage=车辆损失险,200000,340.50&force=-1。座位险多一个座位值哦！
     *
     * @param mContext
     * @param callback
     */
    public static void saveOfferPrice(Context mContext,
                                      float totalprice,
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
        String k = "totalprice=" + totalprice +
                "&companyid=" + companyid +
                "&orderid=" + orderid +
                "&damage=" + damage +
                "&third=" + third +
                "&seat=" + seat +
                "&steal=" + steal +
                "&free=" + free +
                "&grass=" + grass +
                "&scratch=" + scratch +
                "&fire=" + fire +
                "&engine=" + engine +
                "&compensation=" + compensation +
                "&nothird=" + nothird +
                "&repair=" + repair +
                "&force=" + force;

        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.saveOfferPrice(s, callback);
    }


    /**
     * 7.4.3.选择报价方案
     * 发送时机	提交订单时，用于确定选择的是何种报价。
     *
     * @param mContext
     * @param callback
     */
    public static void selectPlan(Context mContext,
                                  int companyid,
                                  int orderid,
                                  NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "companyid=" + companyid + "&orderid=" + orderid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.selectPlan(s, callback);
    }


    /**
     * 获取保险公司信息
     * 发送时机	获取所有保险公司信息，如网点 、政策，电话
     *
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
     * <p/>
     * 参数说明
     * 1、String name； // 被保人姓名
     * 2、Int orderid； // 订单编号
     * 3、String phone； // 被保人电话
     * 4、String idcardnum； // 身份证号
     * 5、String recievename; //保单收件人姓名 用户未选传默认值为 1、name
     * 6、String receivephone; //收件人电话，无则传3、phone值
     * 7、String recieveaddress; //接收地址，无则传3、phone值
     * 8、String refereephone ; //推荐人手机，无则传3、phone值
     * 9、String operatorphone ;//经办人手机
     * 10、String provence; //出险省，即买保险的省
     *
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
                                           String refereephone,
                                           String operatorphone,
                                           String provence,
                                           NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "name=" + name +
                "&orderid=" + orderid +
                "&phone=" + phone +
                "&idcardnum=" + idcardnum +
                "&recievename=" + recievename +
                "&receivephone=" + receivephone +
                "&recieveaddress=" + recieveaddress +
                "&refereephone=" + refereephone +
                "&operatorphone=" + operatorphone +
                "&provence=" + provence;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.confirmVehicleOrder(s, callback);
    }


    /**
     * 发送时机	启动页面，后台发送检测版本更新情况（用于比对本地和服务的版本）
     *
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
     * <p/>
     * 参数说明
     * 1、int userid； // 用户编号
     * 2、String company；// 公司名
     * 3、String  companyaddress; // 公司地址
     * 4、String service; //服务介绍
     *
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
        String k = "userid=" + userid +
                "&company=" + company +
                "&companyaddress=" + companyaddress +
                "&service=" + service;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.modifySelfInfo(s, callback);
    }

    /**
     * 7.7.4.取消停保
     * 发送时机	用户选择取消暂停调用
     * <p/>
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int orderid ; //保单编号
     * 3、int type ;//   0限行取消   1预约取消
     *
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
        String k = "userid=" + userid +
                "&orderid=" + orderid +
                "&type=" + type;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.cancelPause(s, callback);
    }

    /**
     * 7.7.5.暂停历史
     * 发送时机	用户点击累计停保费、查看预约暂停记录时，获取两种类型的暂停记录，一次全返回
     * 参数说明	1、int userid ; //用户编号
     * 2、int orderid ; //保单编号
     * 限制条件	参数1、2为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getPauseHistory(Context mContext,
                                       int userid,
                                       int orderid,
                                       NetCallback<NetWorkResultBean<PauseHistory>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid +
                "&orderid=" + orderid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getPauseHistory(s, callback);
    }

    /**
     * 7.8.1.我的钱包
     * 7.8.1.1.可用余额
     * 发送时机	用户点击我的钱包，获取个人钱包信息
     * <p/>
     * 参数说明
     * 1、int userid ; //用户编号
     *
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
     * <p/>
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int type; //提现类型 停保费 1；加班险费2 ；奖励费3。
     * 3、float amount ;// 提现金额，小数位最多2位
     * 4、String  realname ;//姓名
     * 5、int  withdrawlPwd；// 提现密码（Md5编码）
     * 6、String account；//账号
     * 7、String  union;// 银联名字（支付宝就填支付宝，微信就填微信）
     * 8、int accountType ;//账号类型  提现账号的类型  0 银联  1 支付宝 2微信
     * 限制条件	参数1、2、3、4、5、6、7、8为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void saveWithdrawlInfo(Context mContext,
                                         int userid,
                                         int type,
                                         float amount,
                                         String realname,
                                         String withdrawlPwd,
                                         String account,
                                         String union,
                                         int accountType,
                                         NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid +
                "&type=" + type +
                "&amount=" + amount +
                "&realname=" + realname +
                "&withdrawlPwd=" + MD5Util.getMD5(withdrawlPwd) +
                "&account=" + account +
                "&union=" + union +
                "&accountType=" + accountType;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.saveWithdrawlInfo(s, callback);
    }


    /**
     * 7.8.1.3.提现获取上一次账号信息
     * 发送时机	用户提现时，用于获取上一次填写的账号信息，可以作为默认值使用
     * <p/>
     * 参数说明
     * 1、int userid ; //用户编号
     *
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
     * 7.8.2.我的保险
     * 7.8.2.1.首页保险分类列表
     * 接口名称	getMyinsuranceListInfo
     * 发送时机	我的保险首页保险分类列表信息，每类均返回一个代表和该类的总数，点击后边有具体分页获取接口
     * 参数说明	1、int userid ; //用户编号
     * 限制条件	参数1为必填。
     *
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


    /**
     * 发送时机	分页获取驾驶险信息
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int pageSize ; //每页数据量大小
     * 3、int pageNum；//当前页数
     *
     * @param mContext
     * @param callback
     */
    public static void getDriverOrderByPage(Context mContext,
                                            int userid,
                                            int pageSize,
                                            int pageNum,
                                            NetCallback<NetWorkResultBean<MyInsuranceData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid +
                "&pageSize=" + pageSize +
                "&pageNum=" + pageNum;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getDriverOrderByPage(s, callback);
    }


    /**
     * 发送时机	分页获取车险信息
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int pageSize ; //每页数据量大小
     * 3、int pageNum；//当前页数
     *
     * @param mContext
     * @param callback
     */
    public static void getVehicleOrderByPage(Context mContext,
                                             int userid,
                                             int pageSize,
                                             int pageNum,
                                             NetCallback<NetWorkResultBean<MyInsuranceData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid +
                "&pageSize=" + pageSize +
                "&pageNum=" + pageNum;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getVehicleOrderByPage(s, callback);
    }


    /**
     * 发送时机	分页获取加班险信息
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int pageSize ; //每页数据量大小
     * 3、int pageNum；//当前页数
     *
     * @param mContext
     * @param callback
     */
    public static void getOvertimeOrderByPage(Context mContext,
                                              int userid,
                                              int pageSize,
                                              int pageNum,
                                              NetCallback<NetWorkResultBean<MyInsuranceData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid +
                "&pageSize=" + pageSize +
                "&pageNum=" + pageNum;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getOvertimeOrderByPage(s, callback);
    }


    /**
     * 发送时机	用户点击报价历史，获取报价列表
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int pageSize ; //每页数据量大小
     * 3、int pageNum；//当前页数
     * 限制条件	参数1、2、3为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getPriceHistoryList(Context mContext,
                                           int userid,
                                           int pageSize,
                                           int pageNum,
                                           NetCallback<NetWorkResultBean<HistoryPriceData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid +
                "&pageSize=" + pageSize +
                "&pageNum=" + pageNum;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getPriceHistoryList(s, callback);
    }


    /**
     * 发送时机	用户点击某个报价，查看详情时调用，获取报价详情
     * 参数说明	1、int orderid ; //保单编号
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getHistoryPriceDetail(Context mContext,
                                             int orderid,
                                             NetCallback<NetWorkResultBean<HistoryPriceData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "orderid=" + orderid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getHistoryPriceDetail(s, callback);
    }


    /**
     * 发送时机	历史报价删除记录
     * 参数说明	1、int orderid ; //保单编号
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void deleteHistoryPrice(Context mContext,
                                          int orderid,
                                          NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "orderid=" + orderid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.deleteHistoryPrice(s, callback);
    }


    /**
     * 发送时机	用户加班险报案
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int orderid ; //保单编号
     * 限制条件	参数1、2、3为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void reportOvertime(Context mContext,
                                      int userid,
                                      int orderid,
                                      NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid +
                        "&orderid=" + orderid ;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.reportOvertime(s, callback);
    }


    /**
     * 获取4S店信息
     *
     * @param mContext
     * @param callback
     */
    public static void getFourServiceInfo(Context mContext,
                                          NetCallback<NetWorkResultBean<CommData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        git.getFourServiceInfo("", callback);
    }


    /**
     * 发送时机	付款时调用，先拿到支付对象，然后去支付，具体流程参见ping++
     * 参数说明
     * 1、int orderid；//保险订单编号
     * 2、double  money ; //支付金额，保留两位小数，单位元
     * 3、String phone ; //被保险人电话
     * 4、int type ;// 保险类别   0车险  1加班险  2驾驶险
     * 5、String channel ;// 支付渠道，参见ping++的可选项
     * 6、String client_ip ; //客户端ip
     * 限制条件	参数1、2、3、4、5、6为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getCharge(Context mContext,
                                 int orderid,
                                 double money,
                                 String phone,
                                 int type,
                                 String channel,
                                 String client_ip,
                                 NetCallback<ChargeBean> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "orderid=" + orderid +
                        "&money=" + money +
                        "&phone=" + phone +
                        "&type=" + type +
                        "&channel=" + channel +
                        "&client_ip=" + client_ip;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getCharge(s, callback);
    }


}
