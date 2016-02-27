package com.jiandanbaoxian.api.user;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.model.AccountData;
import com.jiandanbaoxian.model.Brand;
import com.jiandanbaoxian.model.ChargeBean;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.ConsultantData;
import com.jiandanbaoxian.model.FinancialAccount;
import com.jiandanbaoxian.model.FreedomData;
import com.jiandanbaoxian.model.HistoryPriceData;
import com.jiandanbaoxian.model.InviteInfo;
import com.jiandanbaoxian.model.MyInsuranceData;
import com.jiandanbaoxian.model.MyWalletData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.OvertimeData;
import com.jiandanbaoxian.model.Overtimeordertable;
import com.jiandanbaoxian.model.PauseData;
import com.jiandanbaoxian.model.PauseHistory;
import com.jiandanbaoxian.model.RangeData;
import com.jiandanbaoxian.model.RegionBean;
import com.jiandanbaoxian.model.ReportData;
import com.jiandanbaoxian.model.SelfRecord;
import com.jiandanbaoxian.model.TeamData;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.model.VehicleTypeInfo;
import com.jiandanbaoxian.model.jugeOvertimeInsuranceOrder;
import com.jiandanbaoxian.util.Base64Util;
import com.jiandanbaoxian.util.MD5Util;
import com.jiandanbaoxian.util.RetrofitUtil;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.mime.TypedFile;

public class UserRetrofitUtil extends RetrofitUtil {

    /**
     * 获取验证码
     *
     * @param mContext
     * @param phone
     * @param callback String phone； //手机号
     *                 Int type;//0 登陆获取验证码，1 设置密码获取验证码，2 重置密码，3添加提现账号
     */
    public static void obtainVerifyCode(Context mContext, String phone, int type, NetCallback<NetWorkResultBean<CommData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String p = new String("phone=");
        String k = p + phone + "&type=" + type;
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
     * 发送时机	判断本周是否购买过
     * 参数说明	1、String phone;//用户电话
     *
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
        git.jugeOvertimeInsuranceOrder(s, callback);
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
     * 发送时机	提交订单时保存订单信息
     * 参数说明
     * 参数说明	1、String phone； // 被保人电话
     * 2、Int overtimeInsuranceId;//上一个接口返回的加班险id
     * 3、float money； // 保费
     * 4、String companyaddress； // 公司地址
     * 5、String homeaddress; //家庭地址
     * 6、float  lat;//纬度   8位小数
     * 7、float lng;//经度   8位小数
     * 8、string username;//被保险人姓名
     * 9、string companyname;//被保险人公司名字
     *
     * @param mContext
     * @param callback
     */
    public static void saveOvertimeInsuranceOrder(Context mContext,
                                                  String phone,
                                                  int overtimeInsuranceId,
                                                  float money,
                                                  String companyaddress,
                                                  String homeaddress,
                                                  double lat,
                                                  double lng,
                                                  String username,
                                                  String companyname,
                                                  NetCallback<NetWorkResultBean<Overtimeordertable>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String key = "phone=" + phone +
                "&overtimeInsuranceId=" + overtimeInsuranceId +
                "&money=" + money +
                "&companyaddress=" + companyaddress +
                "&homeaddress=" + homeaddress +
                "&lat=" + lat +
                "&lng=" + lng +
                "&username=" + username +
                "&companyname=" + companyname;
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
    public static void searchTeam(Context mContext, String searchParam, NetCallback<NetWorkResultBean<List<FreedomData>>> callback) {
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
     * 接口名称	fillInfoJoinTeam
     * 发送时机	加入团队保存个人身份信息
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、string realname ; //姓名
     * 3、String city；//地址
     * 4、String idcardnum ;//身份证号
     * 5、String refereephone;//推荐人电话，没有就传"0"
     * 6、int companyid;//公司编号  无则传-1
     * 7、int fourServiceId;//4s店编号  无则传-1
     * 8、String service;//服务介绍
     * 9、String worknum;//保险公司，员工工号，无传 “-1”
     * 10、String otherCompanyName;//其他公司的名字，无传 “-1”
     * 11、String companyAddress; //公司地址，无传 “-1”
     * 本接口还有两个单独的参数：一个imagesData,放图片（可多张身份证jpg），图片名字每次加个随机数上防止名字冲突，另一个photoData(一张头像图jpg),请求体要设置提交格式。   *
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
                                        String otherCompanyName,
                                        String companyAddress,
                                        TypedFile imagesData,
                                        TypedFile photoData,
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
                        "&worknum=" + worknum +
                        "&otherCompanyName=" + otherCompanyName +
                        "&companyAddress=" + companyAddress;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.fillInfoJoinTeam(s, imagesData, photoData, callback);
    }

    /**
     * 发送时机	用户点击加入团队时，调用此接口获取默认信息，有则用户可以不重新填写
     * 参数说明
     * int userid; //用户id
     * int  pageSize;//容量
     * int pageNum;//页码
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getTeamRangeInfo(Context mContext, int userid, int pageSize, int pageNum, NetCallback<NetWorkResultBean<RangeData>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid +
                "&pageSize=" + pageSize +
                "&pageNum=" + pageNum;
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
                                      NetCallback<NetWorkResultBean<TeamData>> callback) {
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
                                    NetCallback<NetWorkResultBean<List<SelfRecord>>> callback) {
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
     * 接口名称	saveVehicleInfo
     * 发送时机	用户扫描完后，进入下一步，或者跳转页面时，调此接口保存车辆信息，并生成订单号，服务器会调用华安的车型查询、折旧和报价三个接口，并返回报价结果
     * 参数说明	"
     * int userid； // 用户编号（当前登陆者）
     * String licenseplate； // 车牌号
     * String enginenumber； // 发动机号
     * String framenumber； // 车架号
     * Int seatingcapacity； // 几座
     * Int registrationDate； // 登记时间 （毫秒）
     * String ownerName； // 车主姓名
     * Int commercestartdate； //商业险起效日期（毫秒），没有则给0
     * Int compulsorystartdate ; //交强险起效日期（毫秒），没有则给0
     * 10、int issueDate;//发证日期（毫秒）
     * 11、String provence ;//省名字
     * 12、String  provnce_no ;//省级行政区域代码，提供了接口
     * 13、String city_no;//市行政区域代码，提供了接口
     * 14、String county_no;//省级行政区域代码，提供了接口
     * 15、int  transfer;//是否过户    0否  1是
     * 16、int  transferDate;//  若过户   过户日期（毫秒），否则传0
     * 17、String idcardNum;//   身份证号
     * 18、String phone;//投保人电话
     * 19、int compulsoryAmt ;//交强险保额（元）
     * 20、String insuranceItems ;//对应华安中保险种类
     * <p/>
     * 5.4.9对象生成的json数组,d对象中 没有的值就给-1、amt属性给0/0.0。
     * 只买了两个险的示例：[{"amt":0,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":-1,"insrnc_cde":"030101","insrnc_name":"","number":-1,"premium":0,"remark":"-1"},
     * {"amt":0,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":-1,"insrnc_cde":"030103","insrnc_name":"","number":-1,"premium":0,"remark":"-1"}]
     * <p/>
     * 21、int type;// 0 华安保险"
     * 限制条件	"参数1--21为必填，若用户未填7(车主姓名) 就默认传userid值
     * 8、9若只选则了一个（至少选择一个），则未选中的给默认值0"
     * 返回结果	"参见对象5.4.12
     * "
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
                                       String registrationDate,
                                       String ownerName,
                                       String commercestartdate,
                                       String compulsorystartdate,
                                       String issueDate,
                                       String provence,
                                       String provnce_no,
                                       String city_no,
                                       String county_no,
                                       String transfer,
                                       String transferDate,
                                       String idcardNum,
                                       String phone,
                                       int compulsoryAmt,
                                       String insuranceItems,
                                       int type,

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
                "&compulsorystartdate=" + compulsorystartdate +
                "&issueDate=" + issueDate +
                "&provence=" + provence +
                "&provnce_no=" + provnce_no +
                "&city_no=" + city_no +
                "&county_no=" + county_no +
                "&transfer=" + transfer +
                "&transferDate=" + transferDate +
                "&idcardNum=" + idcardNum +
                "&phone=" + phone +
                "&compulsoryAmt=" + compulsoryAmt +
                "&insuranceItems=" + insuranceItems +
                "&type=" + type;

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
     * 参数说明	"
     * 1、Int  priceid； // 报价编号
     * 2、Int  orderid； // 订单编号"
     * <p/>
     * 发送时机	提交订单时，用于确定选择的是何种报价。
     *
     * @param mContext
     * @param callback
     */
    public static void selectPlan(Context mContext,
                                  int priceid,
                                  int orderid,
                                  NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "priceid=" + priceid + "&orderid=" + orderid;
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
     * <p/>
     * 接口名称	confirmVehicleOrder
     * 发送时机	服务器会调用华安的核保接口
     * 参数说明	"
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
     * 11、int type;//  0 华安保险
     * 12、String cal_app_no;// 华安保险报价返回的报价单号"
     * 限制条件	参数1、2，3，4、5、6、7、8、9、10、11、12为必填。
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
                                           int type,
                                           String cal_app_no,
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
                "&provence=" + provence +
                "&type=" + type +
                "&cal_app_no=" + cal_app_no;
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
     * 个人中心修改资料。
     * 1、int userid； // 用户编号
     * 2、int companyid；// 公司编号
     * 3、int  fourServiceId; // 4s店编号
     * 4、String service; //服务介绍
     * 5、String city;//所在城市
     * 6、String companyaddress;// 公司地址
     * 7、String worknum;//工号
     * 8、String  companyname;//公司名字，单用户在可选的公司中找不到自己的公司时，手动输入自己公司名，无传-1
     * 此接口还包括一个photoData参数，存放头像图片，上传文件必须设置post提交格式！
     *
     * @param mContext
     * @param callback
     */
    public static void modifySelfInfo(Context mContext,
                                      int userid,
                                      int companyid,
                                      int fourServiceId,
                                      String service,
                                      String city,
                                      String companyaddress,
                                      String worknum,
                                      String companyname,
                                      TypedFile photoData,
                                      NetCallback<NetWorkResultBean<Userstable>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid +
                "&companyid=" + companyid +
                "&fourServiceId=" + fourServiceId +
                "&service=" + service +
                "&city=" + city +
                "&companyaddress=" + companyaddress +
                "&worknum=" + worknum +
                "&companyname=" + companyname;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.modifySelfInfo(s, photoData, callback);

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
                                         String amount,
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
        Log.e("retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        Log.e("jwjw", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.saveWithdrawlInfo(s, callback);
    }


    /**
     * 发送时机	添加提现账号
     * 参数说明
     * <p/>
     * String  verifyCode;//手机验证码
     * <p/>
     * <p/>
     * 1、int userid ; //用户编号
     * 2、string bank_name;//银行名字（微信、支付宝就为微信支付宝）
     * 3、string account_num;//账号
     * 4、string account_name;//户名
     * 5、int accountType ;//提现账号的类型   0 银联   1 支付宝   2微信
     *
     * @param mContext
     * @param callback
     */
    public static void addWithdrawlAccount(Context mContext,
                                           int userid,
                                           String bank_name,
                                           String account_num,
                                           String account_name,
                                           int accountType,
                                           String verifyCode,
                                           NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid +
                "&bank_name=" + bank_name +
                "&account_num=" + account_num +
                "&account_name=" + account_name +
                "&accountType=" + accountType +
                "&verifyCode=" + verifyCode;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.addWithdrawlAccount(s, callback);
    }


    /**
     * 发送时机	删除提现账号
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int accountid ;// 对应提现账号的编号
     *
     * @param mContext
     * @param callback
     */
    public static void deleteWithdrawlAccount(Context mContext,
                                              int userid,
                                              int accountid,
                                              NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid +
                "&accountid=" + accountid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.deleteWithdrawlAccount(s, callback);
    }


    /**
     * 发送时机	获取提现账号
     * 参数说明	1、int userid ; //用户编号
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void getWithdrawlAccount(Context mContext,
                                           int userid,
                                           NetCallback<NetWorkResultBean<List<FinancialAccount>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getWithdrawlAccount(s, callback);
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
                        "&orderid=" + orderid;
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


    /**
     * 接口名称	applyCooperation
     * 发送时机	提交申请合作
     * 参数说明
     * 1、string name;//4s店名字
     * 2、int brand;//品牌编号
     * 3、string address;//4s店地址
     * 4、string phone;//电话
     * 5、string service;//服务介绍
     * 6、string managername;//服务经理姓名
     * 7、string certification_num;//执照编号
     * 8、float lat;//4s店纬度
     * 9、float lng;//4s店经度
     * 本接口还有一个参数imagesData,放图片(营业执照图片)，图片名字每次加个随机数上防止名字冲突，post要设置请求体格式。
     * 限制条件	参数1、2、3、4、5、6、7、8、9为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void applyCooperation(Context mContext,
                                        String name,
                                        int brand,
                                        String address,
                                        String phone,
                                        String service,
                                        String managername,
                                        String certification_num,
                                        double lat,
                                        double lng,
                                        TypedFile typedFile,
                                        NetCallback<NetWorkResultBean<Integer>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "name=" + name +
                        "&brand=" + brand +
                        "&address=" + address +
                        "&phone=" + phone +
                        "&service=" + service +
                        "&managername=" + managername +
                        "&certification_num=" + certification_num +
                        "&lat=" + lat +
                        "&lng=" + lng;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.applyCooperation(s, typedFile, callback);
    }


    /**
     * 发送时机	判断是否提交过入团请求，结合用户角色可以判断是否可以提交申请
     * 参数说明	1、int userid; //用户id
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void jugeJoinRequest(Context mContext,
                                       int userid,
                                       NetCallback<NetWorkResultBean<Object>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.jugeJoinRequest(s, callback);
    }


    /**
     * 发送时机	判断是否提交过入团请求，结合用户角色可以判断是否可以提交申请
     * 未认证：
     * {
     * "status": 200,
     * "message": "未实名认证！",
     * "data": 0
     * }
     * 待审核：
     * {
     * "status": 200,
     * "message": "待审核！",
     * "data": 1
     * }
     * 审核通过：
     * {
     * "status": 200,
     * "message": "认证通过！",
     * "data": 2
     * }
     * 审核未通过：
     * {
     * "status": 200,
     * "message": "未通过认证！",
     * "data": 3
     * }
     * 限制条件	参数1为必填。
     *
     * @param mContext
     * @param callback
     */
    public static void jugeCertify(Context mContext,
                                   int userid,
                                   NetCallback<NetWorkResultBean<Integer>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.jugeCertify(s, callback);
    }


    /**
     * 接口名称	getFourServiceBrands
     * 发送时机	获取所有4S品牌
     * 参数说明	1、String brand;// 品牌名字
     *
     * @param mContext
     * @param callback
     */
    public static void getFourServiceBrands(Context mContext,
                                            String brand,
                                            NetCallback<NetWorkResultBean<List<Brand>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "brand=" + brand;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getFourServiceBrands(s, callback);
    }


    /**
     * 接口名称	huanDistribution
     * 发送时机	如果用户需要配送保单，调此接口，服务器会调用华安的配送接口。
     * 参数说明	"
     * String forceNo;//交强险，保单号（华安的），无传0
     * String commerceNo;//商业险保单号（华安的），无传0
     * String address；//配送具体地址
     * String recieverPhone;//收件人电话
     * String recieverName;//收件人姓名
     * String provence_no;//省级行政区代码
     * String city_no;//市级行政区代码
     * String county_no;//区级行政区代码
     * String beSuredName;//被保险人姓名"
     * 返回结果
     *
     * @param mContext
     * @param callback
     */
    public static void huanDistribution(Context mContext,
                                        String forceNo,
                                        String commerceNo,
                                        String address,
                                        String recieverPhone,
                                        String recieverName,
                                        String provence_no,
                                        String city_no,
                                        String county_no,
                                        String beSuredName,
                                        NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "forceNo=" + forceNo +
                        "&commerceNo=" + commerceNo +
                        "&address=" + address +
                        "&recieverPhone=" + recieverPhone +
                        "&recieverName=" + recieverName +
                        "&provence_no=" + provence_no +
                        "&city_no=" + city_no +
                        "&county_no=" + county_no +
                        "&beSuredName=" + beSuredName;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.huanDistribution(s, callback);
    }


    /**
     * 接口名称	markPay
     * 发送时机	用户支付成功后，手机端调用此接口，修改我们本地数据库订单的支付结果。
     * 参数说明	1、int orderId;//我们数据库对应的保险编号
     *
     * @param mContext
     * @param callback
     */
    public static void markPay(Context mContext,
                               String orderId,
                               NetCallback<NetWorkResultBean<Object>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "orderId=" + orderId;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.markPay(s, callback);
    }


    /**
     * 接口名称	huanApplyPay
     * 发送时机	服务器调用华安支付申请接口，返回支付单号，供手机端去支付。手机端是调用华安的支付接口，完成支付！
     * 参数说明
     * "
     * String insureName ;//投保人姓名
     * String compulsoryNo;//交强险保单号，无传0
     * int compulsoryAmount ;//交强险，保费金额，单位分，无传0
     * String commerceNo;//商业险单号 无传0
     * Int commerceAmount;//商业险保费，单位分，无传0
     * String countyNo;//区级行政区域代码
     * Int compulsoryStartDate;//交强险起效时间 毫秒，无传0
     * Int cmmerceStartDate ;// 商业险起效时间 毫秒，无传 0
     * Int  type；//保险公司  0 华安  "
     *
     * @param mContext
     * @param callback
     */
    public static void huanApplyPay(Context mContext,
                                    String insureName,
                                    String compulsoryNo,
                                    String compulsoryAmount,
                                    String commerceNo,
                                    String commerceAmount,
                                    String countyNo,
                                    String compulsoryStartDate,
                                    String cmmerceStartDate,
                                    String type,
                                    NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "insureName=" + insureName +
                        "&compulsoryNo=" + compulsoryNo +
                        "&compulsoryAmount=" + compulsoryAmount +
                        "&commerceNo=" + commerceNo +
                        "&commerceAmount=" + commerceAmount +
                        "&countyNo=" + countyNo +
                        "&compulsoryStartDate=" + compulsoryStartDate +
                        "&cmmerceStartDate=" + cmmerceStartDate +
                        "&type=" + type;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.huanApplyPay(s, callback);
    }


    /**
     * 接口名称	getC_ly15
     * 发送时机	获取免赔额键值对,取得相应的免赔额对应的编码，以便和后台交互
     * "{
     * "status": 0,
     * "message": "获取免赔额列表(编码和值)成功！",
     * "data": {
     * "363003": 300,
     * "363004": 500,
     * "363006": 1000,
     * "363008": 2000
     * }
     * }"
     *
     * @param mContext
     * @param callback
     */
    public static void getC_ly15(Context mContext,
                                 NetCallback<NetWorkResultBean<Object>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        git.getC_ly15(callback);
    }

    /**
     * 接口名称	getCountyNo
     * 发送时机	获取县级行政区编码
     * 参数说明	1、String  cityNo;//上一接口获取的市的编码
     *
     * @param mContext
     * @param callback
     */
    public static void getCountyNo(Context mContext,
                                   String cityNo,
                                   NetCallback<NetWorkResultBean<List<RegionBean>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "cityNo=" + cityNo;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getCountyNo(s, callback);
    }


    /**
     * 市级行政区编码
     * 接口名称	getCityNo
     * 发送时机	获取市级行政区编码
     * 参数说明	1、String  provenceNo;//上一接口获取的省的编码
     *
     * @param mContext
     * @param callback
     */
    public static void getCityNo(Context mContext,
                                 String provenceNo,
                                 NetCallback<NetWorkResultBean<List<RegionBean>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "provenceNo=" + provenceNo;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.getCityNo(s, callback);
    }


    /**
     * 省级行政区编码
     * 接口名称	getProvenceNo
     * 发送时机	获取省级行政区编码、和市、县级结合可实现三级菜单
     * 参数说明
     *
     * @param mContext
     * @param callback
     */
    public static void getProvenceNo(Context mContext,
                                     NetCallback<NetWorkResultBean<List<RegionBean>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        git.getProvenceNo(callback);
    }


    /***************** 服务端接口 121  华安保险流程 ********************/

    /**
     * 接口名称	vehcileTypeInfo
     * 发送时机	查询华安车型信息，供用户选择具体车型
     * 参数说明
     * "1、String provence_no;//省级行政区代码
     * 2、String licenseplate;//车牌号
     * 3、String framenumber;//车架号
     * 4、int  type ;// 0 华安保险"
     *
     * @param mContext
     * @param provenceNo
     * @param callback
     */
    public static void vehcileTypeInfo(Context mContext,
                                       String provenceNo,
                                       String licenseplate,
                                       String framenumber,
                                       String type,
                                       NetCallback<NetWorkResultBean<List<VehicleTypeInfo>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "provence_no=" + provenceNo +
                        "&licenseplate=" + licenseplate +
                        "&framenumber=" + framenumber +
                        "&type=" + type;

        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        git.vehicleTypeInfo(s, callback);
    }

    /**
     * 接口名称	quotaPrice
     * 发送时机	用户扫描完后，进入下一步，或者跳转页面时，调此接口保存车辆信息，并生成报价单号，服务器会调用华安的折旧和报价接口，并返回报价结果
     * 参数说明	"
     * int userid； // 用户编号（当前登陆者）
     * String licenseplate； // 车牌号
     * String enginenumber； // 发动机号
     * String framenumber； // 车架号
     * Int seatingcapacity； // 几座
     * Float newValue； // 新车购置价
     * String model_code； // 车型代码
     * Int registrationDate； // 登记时间 （毫秒）
     * String ownerName； // 车主姓名
     * Int commercestartdate； //商业险起效日期（毫秒），没有则给0
     * Int compulsorystartdate ; //交强险起效日期（毫秒），没有则给0
     * 12、int issueDate;//发证日期（毫秒）
     * 13、String provence ;//省名字
     * 14、String  provnce_no ;//省级行政区域代码，提供了接口
     * 15、String city_no;//市行政区域代码，提供了接口
     * 16、String county_no;//县级行政区域代码，提供了接口
     * 17、int  transfer;//是否过户    0否  1是
     * 18、int  transferDate;//  若过户   过户日期（毫秒），否则传0
     * 19、String idcardNum;//   身份证号
     * 20、String phone;//投保人电话
     * 21、int compulsoryAmt ;//交强险保额（元）
     * 22、String insuranceItems ;//对应华安中保险种类5.4.9对象生成的json数组,对象中 没有的值就给-1、amt属性给0/0.0。车损险和自燃险保额也可以给默认值0，服务器自己赋实际值。只买了两个险的示例：[{"amt":0,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":-1,"insrnc_cde":"030101","insrnc_name":"","number":-1,"premium":0,"remark":"-1"},
     * {"amt":0,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":-1,"insrnc_cde":"030103","insrnc_name":"","number":-1,"premium":0,"remark":"-1"}]
     * 23、int type;// 0 华安保险"
     * 限制条件	"参数1--23为必填，若用户未填9就默认传userid值
     * 10、11若只选则了一个（至少选择一个），则未选中的给默认值0"
     * 返回结果	"参见对象5.4.12
     * <p/>
     * [{"amt":0,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":-1,"insrnc_cde":"030101","insrnc_name":"","number":-1,"premium":0,"remark":"-1"},
     * {"amt":0,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":-1,"insrnc_cde":"030103","insrnc_name":"","number":-1,"premium":0,"remark":"-1"}]
     * <p/>
     * <p/>
     * <p/>
     * "
     */
    public static void quotaPrice(Context mContext,
                                  String userid,
                                  String licenseplate,
                                  String enginenumber,
                                  String framenumber,
                                  String seatingcapacity,
                                  String newValue,
                                  String model_code,
                                  String registrationDate,
                                  String ownerName,
                                  String commercestartdate,
                                  String compulsorystartdate,
                                  String issueDate,
                                  String provence,
                                  String provnce_no,
                                  String city_no,
                                  String county_no,
                                  String transfer,
                                  String transferDate,
                                  String idcardNum,
                                  String phone,
                                  String compulsoryAmt,
                                  String insuranceItems,
                                  String type,
                                  NetCallback<NetWorkResultBean<List<VehicleTypeInfo>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k =
                "userid=" + 48 +
                        "&licenseplate=" + "鲁Y6U166" +
                        "&enginenumber=" + "1201279207" +
                        "&framenumber=" +"LGWCAC1A2CC000143" +
                        "&seatingcapacity=" + "0" +
                        "&newValue=" + "76500" +
                        "&model_code=" + "DED1119BDC" +
                        "&registrationDate=" + "1330531200000" +
                        "&ownerName=" + "林宗钱" +
                        "&commercestartdate=" + commercestartdate+
                        "&compulsorystartdate=" +0+
                        "&issueDate=" + "1330531200000" +
                        "&provence=" + "山东省" +
                        "&provence_no=" + "370000" +
                        "&city_no=" + "370600" +
                        "&county_no=" + "370613" +
                        "&transfer=" + 0 +
                        "&transferDate=" + 0 +
                        "&idcardNum=" + "320681198612020056" +
                        "&phone=" + "13700000000" +
                        "&compulsoryAmt=" + 1000 +
                        "&insuranceItems=" + insuranceItems +
                        "&type=" + "0";

        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "\n 加密前参数:" + k + "\n加密后参数:" + s);
        Log.e("qq",  insuranceItems);
        git.quotaPrice(s, callback);
    }


}
