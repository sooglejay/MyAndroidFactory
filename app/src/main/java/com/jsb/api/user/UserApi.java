package com.jsb.api.user;

import com.jsb.api.callback.NetCallback;
import com.jsb.model.CommData;
import com.jsb.model.FreedomData;
import com.jsb.model.NetWorkResultBean;
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
    public void getOvertimeInsuranceInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<Overtimeinsurance>> NetCallback);


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



}
