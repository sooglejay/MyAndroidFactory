package com.jiandanbaoxian.api.user;

import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.model.AccountData;
import com.jiandanbaoxian.model.Brand;
import com.jiandanbaoxian.model.ChargeBean;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.CommPriceData;
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
import com.jiandanbaoxian.model.RegionBean;
import com.jiandanbaoxian.model.RangeData;
import com.jiandanbaoxian.model.ReportData;
import com.jiandanbaoxian.model.SelfRecord;
import com.jiandanbaoxian.model.TeamData;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.model.VehicleTypeInfo;
import com.jiandanbaoxian.model.jugeOvertimeInsuranceOrder;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by JiangWei on 15-7-8.
 */
public interface UserApi {

    //http://www.insurance.com/ci/1/submitPhone?param=xxxxx

    /**
     * 获取验证码
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/submitPhone/")
    public void obtainVerifyCode(@Field("param") String params, NetCallback<NetWorkResultBean<CommData>> NetCallback);

    /**
     * 登录
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/loginVerifyPhoneAndCode/")
    public void login(@Field("param") String params, NetCallback<NetWorkResultBean<CommData>> NetCallback);

    /**
     * 获取获取在售加班险信息
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getOvertimeInsuranceInfo/")
    public void getOvertimeInsuranceInfo(@Field("param") String params, NetCallback<NetWorkResultBean<OvertimeData>> NetCallback);


    /**
     * 获取获取在售加班险信息
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/jugeOvertimeInsuranceOrder/")
    public void jugeOvertimeInsuranceOrder(@Field("param") String params, NetCallback<jugeOvertimeInsuranceOrder> NetCallback);


    /**
     * 设置提现等 操作时候的 密码
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/setWithdrawlPassword/")
    public void setWithdrawlPassword(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 设置提现等 操作时候的 密码
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveOvertimeInsuranceOrder/")
    public void saveOvertimeInsuranceOrder(@Field("param") String params, NetCallback<NetWorkResultBean<Overtimeordertable>> NetCallback);


    /**
     * 设置提现等 操作时候的 密码
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getPauseInfo/")
    public void getPauseInfo(@Field("param") String params, NetCallback<NetWorkResultBean<List<PauseData>>> NetCallback);


    /**
     * 按团长名字，电话，团名搜索团信息
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/searchTeam/")
    public void searchTeam(@Field("param") String params, NetCallback<NetWorkResultBean<List<FreedomData>>> NetCallback);


    /**
     * 加入团队时，给用户展示4个团队供选择
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getFourTeamInfo/")
    public void getFourTeamInfo(@Field("param") String params, NetCallback<NetWorkResultBean<List<FreedomData>>> NetCallback);


    /*
    参数说明
    1、int userid ; //用户编号
    2、string realname ; //姓名
    3、String city；//地址
            4、String idcardnum ;//身份证号
    5、String refereephone;//推荐人电话，没有就传"0"
    6、String companyname;//公司名字   无则传空“”
    7、String companyaddress;//公司地址  无则传空“”
    8、String service;//服务介绍
    本接口还有一个参数imagesData,放图片，图片名字每次加个随机数上防止名字冲突
    * @param params
     * @param NetCallback
     */
    @Multipart
    @POST("/fillInfoJoinTeam/")
    public void fillInfoJoinTeam(@Part("param") String params, @Part("imagesData") TypedFile imagesData, @Part("photoData") TypedFile photoData, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 发送时机	获取排名信息
     * 参数说明	1、int userid; //用户id
     * 限制条件	参数1为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getTeamRangeInfo/")
    public void getTeamRangeInfo(@Field("param") String params, NetCallback<NetWorkResultBean<RangeData>> NetCallback);

    /**
     * 发送时机	获取邀请信息
     * 参数说明	1、int userid; //用户id
     * 限制条件	参数1为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getInviteInfo/")
    public void getInviteInfo(@Field("param") String params, NetCallback<NetWorkResultBean<List<InviteInfo>>> NetCallback);


    /**
     * 发送时机	创建团队获取可选团员
     * 参数说明
     * 1、int userid; //用户id
     * 2、int pageSize;//每页容量
     * 3、int pageNum ;//页码
     * 限制条件	参数1、2、3为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getChoicers/")
    public void getChoicers(@Field("param") String params, NetCallback<NetWorkResultBean<List<Userstable>>> NetCallback);


    /**
     * 发送时机	团队创建好后，想添加团员时，获取可选人员
     * 参数说明	1、int userid; //用户id
     * 2、int pageSize;//每页容量
     * 3、int pageNum ;//页码
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getAvailable/")
    public void getAvailable(@Field("param") String params, NetCallback<NetWorkResultBean<List<Userstable>>> NetCallback);


    /**
     * 发送时机	添加团员时，选择好人员，提交
     * 参数说明
     * 1、int leaderid; //用户id(团长id)
     * 2、String ids;//选中的队员编号，多个以豆号隔开
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/addNewMember/")
    public void addNewMember(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 发送时机	创建团队异步验证团名是否可用
     * 参数说明	1、String teamname;//团队名字
     * 限制条件	参数1为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/verifyTeamName/")
    public void verifyTeamName(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 发送时机	创建团队提交
     * 参数说明
     * 1、int userid; //用户id
     * 2、String teamname;//团队名字
     * 3、String memberids;//多个以逗号隔开，例如： 1，2，3 小刘要做审核处理，给选定的人写邀请信息,或者调我的接口 ，没有勾选则传"",
     * 限制条件	参数1、2、3为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/createTeam/")
    public void createTeam(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 发送时机	邀请入团信息处理
     * 参数说明
     * 1、int inviteinfoid; //邀请信息id
     * 2、int received ;//0不接受  1 接受
     * 限制条件	参数1、2为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/dealInviting/")
    public void dealInviting(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 发送时机	团长提交审核结果
     * 参数说明
     * 1、int memberid; //请求用户id
     * 2、int teamid ;//申请入团的团队编号
     * 3、int  auditResult ;// 0未通过  1 通过
     * 限制条件	参数1、2、3为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/auditJoinRequest/")
    public void auditJoinRequest(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 发送时机	团长查看团队信息
     * 参数说明	1、int userid; //用户id
     * 限制条件	参数1为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getMyTeamInfo/")
    public void getMyTeamInfo(@Field("param") String params, NetCallback<NetWorkResultBean<TeamData>> NetCallback);


    /**
     * 发送时机	团长查看申请
     * 参数说明	1、int userid; //用户id
     * 限制条件	参数1为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getJoinRequest/")
    public void getJoinRequest(@Field("param") String params, NetCallback<NetWorkResultBean<TeamData>> NetCallback);

    /**
     * 发送时机	团长搜索团员
     * 参数说明	1、int userid; //团长id
     * 2、String searchParam ;//电话或姓名,成员列表直接放在data中 SelfRecord
     * 限制条件	参数1、2为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/searchMember/")
    public void searchMember(@Field("param") String params, NetCallback<NetWorkResultBean<List<SelfRecord>>> NetCallback);


    /**
     * 发送时机	查询我的顾问
     * 参数说明	1、int userid; //用户id
     * 限制条件	参数1为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getMyConsultant/")
    public void getMyConsultant(@Field("param") String params, NetCallback<NetWorkResultBean<ConsultantData>> NetCallback);


    /**
     * 发送时机	查询其他顾问
     * 参数说明
     * 1、int userid; //用户id
     * 2、int pageSize;//每页容量
     * 3、int pageNum;//页码
     * 限制条件	参数1、2、3为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getOtherConsultant/")
    public void getOtherConsultant(@Field("param") String params, NetCallback<NetWorkResultBean<ConsultantData>> NetCallback);


    /**
     * 发送时机	用户点击加入团队时，调用此接口获取默认信息，有则用户可以不重新填写
     * 参数说明	1、int userid ; //用户编号
     * 限制条件	参数1为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getSelfInfo/")
    public void getSelfInfo(@Field("param") String params, NetCallback<NetWorkResultBean<Userstable>> NetCallback);


    /**
     * 停保滑动时，验证密码
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/verifyPwd/")
    public void verifyPwd(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);

    /**
     * 用户重置手机号，提交手机号和验证码。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/resetSubmitPhoneAndVerifycode/")
    public void resetSubmitPhoneAndVerifycode(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.4.车险
     * 7.4.1.保存车辆信息
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveVehicleInfo/")
    public void saveVehicleInfo(@Field("param") String params, NetCallback<NetWorkResultBean<CommData>> NetCallback);

    /**
     * 保存报价信息
     * 发送时机	查看各个保险公司报价页面切换时调用，或者点击提交时调用。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveOfferPrice/")
    public void saveOfferPrice(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.4.3.选择报价方案
     * 发送时机	提交订单时，用于确定选择的是何种报价。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/selectPlan/")
    public void selectPlan(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.4.4.获取保险公司信息
     * 发送时机	获取所有保险公司信息，如网点 、政策，电话
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getInsuranceCompanyInfo/")
    public void getInsuranceCompanyInfo(@Field("param") String params, NetCallback<NetWorkResultBean<CommData>> NetCallback);


    /**
     * 7.4.5.保存订单信息
     * 发送时机	提交订单时保存订单信息
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/confirmVehicleOrder/")
    public void confirmVehicleOrder(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.7.2.保存限行停保
     * 发送时机	用户点击限行暂停时调用
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveLimitPauseInfo/")
    public void saveLimitPauseInfo(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.7.3.保存预约停保
     * 发送时机	用户选择预约暂停时调用
     * 参数说明	1、int userid ; //用户编号
     * 2、int orderid ; //保单编号
     * 3、float dayPrice ;// 暂停一天所得费用
     * 4、string  reservedays;// 预约日期，年月日多个以豆号隔开如“2015-09-07，2015-09-12”,保单开始结束时间在7.7.1接口中已返回，手机端在选择日期时要判断，只能选择从当前日期之后的2个工作日开始选，且不能选超过结束日期。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveReservePauseInfo/")
    public void saveReservePauseInfo(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.2.检测版本升级
     * 发送时机	启动页面，后台发送检测版本更新情况（用于比对本地和服务的版本）
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/checkUpdate/")
    public void checkUpdate(@Field("param") String params, NetCallback<NetWorkResultBean<CommData>> NetCallback);


    /**
     * 7.3.4 个人中心修改资料
     * 发送时机	个人中心修改资料。
     *
     * @param params
     * @param NetCallback
     */
    @Multipart
    @POST("/modifySelfInfo/")
    public void modifySelfInfo(@Part("param") String params, @Part("photoData") TypedFile photoData, NetCallback<NetWorkResultBean<Userstable>> NetCallback);


    /**
     * 7.7.4.取消停保
     * 发送时机	用户选择取消暂停调用
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/cancelPause/")
    public void cancelPause(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.7.4.取消停保
     * 发送时机	用户选择取消暂停调用
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getPauseHistory/")
    public void getPauseHistory(@Field("param") String params, NetCallback<NetWorkResultBean<PauseHistory>> NetCallback);


    /**
     * 7.8.1.我的钱包
     * 7.8.1.1.可用余额
     * 发送时机	用户点击我的钱包，获取个人钱包信息
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getMywalletInfo/")
    public void getMywalletInfo(@Field("param") String params, NetCallback<NetWorkResultBean<MyWalletData>> NetCallback);


    /**
     * 发送时机	添加提现账号
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、string bank_name;//银行名字（微信、支付宝就为微信支付宝）
     * 3、string account_num;//账号
     * 4、string account_name;//户名
     * 5、int accountType ;//提现账号的类型   0 银联   1 支付宝   2微信
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/addWithdrawlAccount/")
    public void addWithdrawlAccount(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 发送时机	获取提现账号
     * 参数说明	1、int userid ; //用户编号
     * 限制条件	参数1为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getWithdrawlAccount/")
    public void getWithdrawlAccount(@Field("param") String params, NetCallback<NetWorkResultBean<List<FinancialAccount>>> NetCallback);


    /**
     * 发送时机	删除提现账号
     * 参数说明	1、int userid ; //用户编号
     * 2、int accountid ;// 对应提现账号的编号
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/deleteWithdrawlAccount/")
    public void deleteWithdrawlAccount(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.8.1.2.提现
     * 发送时机	用户提现
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveWithdrawlInfo/")
    public void saveWithdrawlInfo(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.8.1.3.提现获取上一次账号信息
     * 发送时机	用户提现时，用于获取上一次填写的账号信息，可以作为默认值使用
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getLastAccountInfo/")
    public void getLastAccountInfo(@Field("param") String params, NetCallback<NetWorkResultBean<AccountData>> NetCallback);


    /**
     * 接口名称	getMyinsuranceListInfo
     * 发送时机	我的保险首页保险分类列表信息，每类均返回一个代表和该类的总数，点击后边有具体分页获取接口
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getMyinsuranceListInfo/")
    public void getMyinsuranceListInfo(@Field("param") String params, NetCallback<NetWorkResultBean<MyInsuranceData>> NetCallback);


    /**
     * 发送时机	获取加班明细
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int pageSize ; //每页数据量大小
     * 3、int pageNum；//当前页数
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getMyOvertimeInfo/")
    public void getMyOvertimeInfo(@Field("param") String params, NetCallback<NetWorkResultBean<MyWalletData>> NetCallback);


    /**
     * 发送时机	分页获取加班险信息
     * 参数说明	1、int userid ; //用户编号
     * 2、int pageSize ; //每页数据量大小
     * 3、int pageNum；//当前页数
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getOvertimeOrderByPage/")
    public void getOvertimeOrderByPage(@Field("param") String params, NetCallback<NetWorkResultBean<MyInsuranceData>> NetCallback);


    /**
     * 发送时机	分页获取驾驶险信息
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int pageSize ; //每页数据量大小
     * 3、int pageNum；//当前页数
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getDriverOrderByPage/")
    public void getDriverOrderByPage(@Field("param") String params, NetCallback<NetWorkResultBean<MyInsuranceData>> NetCallback);


    /**
     * 发送时机	分页获取车险信息
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int pageSize ; //每页数据量大小
     * 3、int pageNum；//当前页数
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getVehicleOrderByPage/")
    public void getVehicleOrderByPage(@Field("param") String params, NetCallback<NetWorkResultBean<MyInsuranceData>> NetCallback);


    /**
     * 发送时机	用户点击报价历史，获取报价列表
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int pageSize ; //每页数据量大小
     * 3、int pageNum；//当前页数
     * 限制条件	参数1、2、3为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getPriceHistoryList/")
    public void getPriceHistoryList(@Field("param") String params, NetCallback<NetWorkResultBean<HistoryPriceData>> NetCallback);


    /**
     * 发送时机	用户点击某个报价，查看详情时调用，获取报价详情
     * 参数说明	1、int orderid ; //保单编号
     * 限制条件	参数1为必填。
     * 限制条件	参数1、2、3为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getHistoryPriceDetail/")
    public void getHistoryPriceDetail(@Field("param") String params, NetCallback<NetWorkResultBean<HistoryPriceData>> NetCallback);


    /**
     * 发送时机	历史报价删除记录
     * 参数说明	1、int orderid ; //保单编号
     * 限制条件	参数1为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/deleteHistoryPrice/")
    public void deleteHistoryPrice(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 发送时机	用户点击我要报案时，获取可报案列表
     * 参数说明	1、int userid ; //用户编号
     * 限制条件	参数1为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getReportableInsurance/")
    public void getReportableInsurance(@Field("param") String params, NetCallback<NetWorkResultBean<ReportData>> NetCallback);


    /**
     * 发送时机	用户加班险报案
     * 参数说明
     * 1、int userid ; //用户编号
     * 2、int orderid ; //保单编号
     * 3、String companyaddress；//公司地址（定位得出，供报案比对）
     * 限制条件	参数1、2、3为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/reportOvertime/")
    public void reportOvertime(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 发送时机	提交入团请求
     * 参数说明
     * 1、int userid; //用户id
     * 2、int teamid ;//申请入团的团队编号
     * 限制条件	参数1、2为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/submitJoinRequest/")
    public void submitJoinRequest(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 获取4S店的信息
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getFourServiceInfo/")
    public void getFourServiceInfo(@Field("param") String params, NetCallback<NetWorkResultBean<CommData>> NetCallback);


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
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getCharge/")
    public void getCharge(@Field("param") String params, NetCallback<ChargeBean> NetCallback);


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
     *
     * @param params
     * @param NetCallback
     */
    @Multipart
    @POST("/applyCooperation/")
    public void applyCooperation(@Part("param") String params, @Part("imagesData") TypedFile typedFile, NetCallback<NetWorkResultBean<Integer>> NetCallback);


    /**
     * 发送时机	判断是否提交过入团请求，结合用户角色可以判断是否可以提交申请
     * 参数说明	1、int userid; //用户id
     * 限制条件	参数1为必填。
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/jugeJoinRequest/")
    public void jugeJoinRequest(@Field("param") String params, NetCallback<NetWorkResultBean<Object>> NetCallback);


    /**
     * 接口名称	getFourServiceBrands
     * 发送时机	获取所有4S品牌
     * 参数说明	1、String brand;// 品牌名字
     *
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getFourServiceBrands/")
    public void getFourServiceBrands(@Field("param") String params, NetCallback<NetWorkResultBean<List<Brand>>> NetCallback);




    /**

     省级行政区编码

     接口名称	getProvenceNo
     发送时机	获取省级行政区编码、和市、县级结合可实现三级菜单
     参数说明
     *
     * @param NetCallback
     */
    @GET("/getProvenceNo/")
    public void getProvenceNo(NetCallback<NetWorkResultBean<List<RegionBean>>> NetCallback);


    /**

     市级行政区编码
     接口名称	getCityNo
     发送时机	获取市级行政区编码
     参数说明	1、String  provenceNo;//上一接口获取的省的编码

     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getCityNo/")
    public void getCityNo(@Field("param") String params, NetCallback<NetWorkResultBean<List<RegionBean>>> NetCallback);



    /**
     接口名称	getCountyNo
     发送时机	获取县级行政区编码
     参数说明	1、String  cityNo;//上一接口获取的市的编码
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getCountyNo/")
    public void getCountyNo(@Field("param") String params, NetCallback<NetWorkResultBean<List<RegionBean>>> NetCallback);




    /**
     接口名称	getC_ly15
     发送时机	获取免赔额键值对,取得相应的免赔额对应的编码，以便和后台交互
     参数说明
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getC_ly15/")
    public void getC_ly15( NetCallback<NetWorkResultBean<Object>> NetCallback);



    /**
     接口名称	huanApplyPay
     发送时机	服务器调用华安支付申请接口，返回支付单号，供手机端去支付。手机端是调用华安的支付接口，完成支付！
     参数说明
     "String insureName ;//投保人姓名
     String compulsoryNo;//交强险保单号，无传0
     int compulsoryAmount ;//交强险，保费金额，单位分，无传0
     String commerceNo;//商业险单号 无传0
     Int commerceAmount;//商业险保费，单位分，无传0
     String countyNo;//区级行政区域代码
     Int compulsoryStartDate;//交强险起效时间 毫秒，无传0
     Int cmmerceStartDate ;// 商业险起效时间 毫秒，无传 0
     Int  type；//保险公司  0 华安  "
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/huanApplyPay/")
    public void huanApplyPay(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);



    /**
     接口名称	markPay
     发送时机	用户支付成功后，手机端调用此接口，修改我们本地数据库订单的支付结果。
     参数说明	1、int orderId;//我们数据库对应的保险编号
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/markPay/")
    public void markPay(@Field("param") String params, NetCallback<NetWorkResultBean<Object>> NetCallback);



    /**
     接口名称	jugeCertify
     发送时机	判断用户是否实名认证
     参数说明	1、int userid;//用户编号
     限制条件	参数1为必填。
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/jugeCertify/")
    public void jugeCertify(@Field("param") String params, NetCallback<NetWorkResultBean<Integer>> NetCallback);




    /**
     接口名称	huanDistribution
     发送时机	如果用户需要配送保单，调此接口，服务器会调用华安的配送接口。
     参数说明	"String forceNo;//交强险，保单号（华安的），无传0
     String commerceNo;//商业险保单号（华安的），无传0
     String address；//配送具体地址
     String  recieverPhone;//收件人电话
     String recieverName;//收件人姓名
     String provence_no;//省级行政区代码
     String city_no;//市级行政区代码
     String county_no;//区级行政区代码
     String  beSuredName;//被保险人姓名"
     返回结果
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/huanDistribution/")
    public void huanDistribution(@Field("param") String params, NetCallback<NetWorkResultBean<String>> NetCallback);














    /***************** 服务端接口 121  华安保险流程 ********************/

    //车型查询
    @FormUrlEncoded
    @POST("/vehcileTypeInfo/")
    public void vehicleTypeInfo(@Field("param") String params, NetCallback<NetWorkResultBean<List<VehicleTypeInfo>>> NetCallback);




    //上传车辆信息去 报价
    @FormUrlEncoded
    @POST("/quotaPrice/")
    public void quotaPrice(@Field("param") String params, NetCallback<NetWorkResultBean<CommPriceData>> NetCallback);





}
