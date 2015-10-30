package com.jsb.api.user;

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

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by JiangWei on 15-7-8.
 */
public interface UserApi {

    //http://www.insurance.com/ci/1/submitPhone?param=xxxxx

    /**
     * 获取验证码
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/submitPhone/")
    public void obtainVerifyCode(@Field("param") String params ,NetCallback<NetWorkResultBean<CommData>> NetCallback);

    /**
     * 登录
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/loginVerifyPhoneAndCode/")
    public void login(@Field("param") String params ,NetCallback<NetWorkResultBean<CommData>> NetCallback);

    /**
     * 获取获取在售加班险信息
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getOvertimeInsuranceInfo/")
    public void getOvertimeInsuranceInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<OvertimeData>> NetCallback);


    /**
     * 加班狗报案
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getReportableInsurance/")
    public void getReportableInsurance(@Field("param") String params ,NetCallback<NetWorkResultBean<ReportableInsurance>> NetCallback);



    /**
     * 设置提现等 操作时候的 密码
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/setWithdrawlPassword/")
    public void setWithdrawlPassword(@Field("param") String params ,NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 设置提现等 操作时候的 密码
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveOvertimeInsuranceOrder/")
    public void saveOvertimeInsuranceOrder(@Field("param") String params ,NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 设置提现等 操作时候的 密码
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getPauseInfo/")
    public void getPauseInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<List<PauseData>>> NetCallback);




    /**
     * 按团长名字，电话，团名搜索团信息
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/searchTeam/")
    public void searchTeam(@Field("param") String params ,NetCallback<NetWorkResultBean<List<TeamData>>> NetCallback);


    /**
     * 加入团队时，给用户展示4个团队供选择
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getFourTeamInfo/")
    public void getFourTeamInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<List<FreedomData>>> NetCallback);


    /**
     * 停保滑动时，验证密码
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/verifyPwd/")
    public void verifyPwd(@Field("param") String params ,NetCallback<NetWorkResultBean<String>> NetCallback);

    /**
     * 用户重置手机号，提交手机号和验证码。
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/resetSubmitPhoneAndVerifycode/")
    public void resetSubmitPhoneAndVerifycode(@Field("param") String params ,NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.4.车险
       7.4.1.保存车辆信息
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveVehicleInfo/")
    public void saveVehicleInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<CommData>> NetCallback);

    /**
     * 保存报价信息
     * 发送时机	查看各个保险公司报价页面切换时调用，或者点击提交时调用。
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveOfferPrice/")
    public void saveOfferPrice(@Field("param") String params ,NetCallback<NetWorkResultBean<String>> NetCallback);



    /**
     * 7.4.3.选择报价方案
     * 发送时机	提交订单时，用于确定选择的是何种报价。
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/selectPlan/")
    public void selectPlan(@Field("param") String params ,NetCallback<NetWorkResultBean<String>> NetCallback);



    /**
     * 7.4.4.获取保险公司信息
     * 发送时机	获取所有保险公司信息，如网点 、政策，电话
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getInsuranceCompanyInfo/")
    public void getInsuranceCompanyInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<CommData>> NetCallback);


    /**
     * 7.4.5.保存订单信息
     * 发送时机	提交订单时保存订单信息
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/confirmVehicleOrder/")
    public void confirmVehicleOrder(@Field("param") String params ,NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.7.2.保存限行停保
     * 发送时机	用户点击限行暂停时调用
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveLimitPauseInfo/")
    public void saveLimitPauseInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<String>> NetCallback);



    /**
     * 7.7.3.保存预约停保
     * 发送时机	用户选择预约暂停时调用
     * 参数说明	1、int userid ; //用户编号
     2、int orderid ; //保单编号
     3、float dayPrice ;// 暂停一天所得费用
     4、string  reservedays;// 预约日期，年月日多个以豆号隔开如“2015-09-07，2015-09-12”,保单开始结束时间在7.7.1接口中已返回，手机端在选择日期时要判断，只能选择从当前日期之后的2个工作日开始选，且不能选超过结束日期。
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveReservePauseInfo/")
    public void saveReservePauseInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<String>> NetCallback);



    /**
     * 7.2.检测版本升级
     * 发送时机	启动页面，后台发送检测版本更新情况（用于比对本地和服务的版本）
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/checkUpdate/")
    public void checkUpdate(@Field("param") String params ,NetCallback<NetWorkResultBean<CommData>> NetCallback);


    /**
     * 7.3.4 个人中心修改资料
     * 发送时机	个人中心修改资料。
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/modifySelfInfo/")
    public void modifySelfInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<Userstable>> NetCallback);


    /**
     * 7.7.4.取消停保
     * 发送时机	用户选择取消暂停调用
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/cancelPause/")
    public void cancelPause(@Field("param") String params ,NetCallback<NetWorkResultBean<String>> NetCallback);


    /**
     * 7.8.1.我的钱包
     7.8.1.1.可用余额
     * 发送时机	用户点击我的钱包，获取个人钱包信息
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getMywalletInfo/")
    public void getMywalletInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<MyWalletData>> NetCallback);



    /**
     * 7.8.1.2.提现
     * 发送时机	用户提现
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/saveWithdrawlInfo/")
    public void saveWithdrawlInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<String>> NetCallback);



    /**
     * 7.8.1.3.提现获取上一次账号信息
     * 发送时机	用户提现时，用于获取上一次填写的账号信息，可以作为默认值使用
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getLastAccountInfo/")
    public void getLastAccountInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<AccountData>> NetCallback);



    /**
     * 接口名称	getMyinsuranceListInfo
     * 发送时机	我的保险首页保险分类列表信息，每类均返回一个代表和该类的总数，点击后边有具体分页获取接口
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getMyinsuranceListInfo/")
    public void getMyinsuranceListInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<MyInsuranceData>> NetCallback);



}
