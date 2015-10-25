package com.jsb.api.user;

import com.jsb.api.callback.NetCallback;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.getOvertimeInsuranceInfo;
import com.jsb.model.loginVerifyPhoneAndCode;
import com.jsb.model.submitPhone;

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
    public void obtainVerifyCode(@Field("param") String params ,NetCallback<NetWorkResultBean<submitPhone>> NetCallback);

    /**
     * 登录
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/loginVerifyPhoneAndCode/")
    public void login(@Field("param") String params ,NetCallback<NetWorkResultBean<loginVerifyPhoneAndCode>> NetCallback);

    /**
     * 获取获取在售加班险信息
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getOvertimeInsuranceInfo/")
    public void getOvertimeInsuranceInfo(@Field("param") String params ,NetCallback<NetWorkResultBean<getOvertimeInsuranceInfo>> NetCallback);


    /**
     * 加班狗报案
     * @param params
     * @param NetCallback
     */
    @FormUrlEncoded
    @POST("/getReportableInsurance/")
    public void getReportableInsurance(@Field("param") String params ,NetCallback<NetWorkResultBean<getOvertimeInsuranceInfo>> NetCallback);






}
