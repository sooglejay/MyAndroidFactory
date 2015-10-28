package com.jsb.api.user;

import com.jsb.api.callback.NetCallback;
import com.jsb.model.CommData;
import com.jsb.model.FreedomData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.OvertimeData;
import com.jsb.model.Overtimeinsurance;
import com.jsb.model.PauseData;
import com.jsb.model.ReportableInsurance;
import com.jsb.model.TeamData;

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
     * 7.2.检测版本升级
     * 发送时机	启动页面，后台发送检测版本更新情况（用于比对本地和服务的版本）
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/checkUpdate/")
    public void checkUpdate(@Field("param") String params ,NetCallback<NetWorkResultBean<CommData>> NetCallback);



}
