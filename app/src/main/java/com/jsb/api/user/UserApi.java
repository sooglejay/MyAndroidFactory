package com.jsb.api.user;

import com.jsb.api.callback.NetCallback;
import com.jsb.constant.NetWorkConstant;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.submitPhone;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by JiangWei on 15-7-8.
 */
public interface UserApi {

    //http://www.insurance.com/ci/1/submitPhone?param=xxxxx

    @FormUrlEncoded
    @POST("/submitPhone/")
    public void obtainVerifyCode(@Field(NetWorkConstant.PARAMS) String params ,NetCallback<NetWorkResultBean<submitPhone>> NetCallback);




}
