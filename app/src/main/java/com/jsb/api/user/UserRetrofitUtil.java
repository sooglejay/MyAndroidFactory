package com.jsb.api.user;

import android.content.Context;
import android.util.Log;

import com.jsb.api.callback.NetCallback;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.loginVerifyPhoneAndCode;
import com.jsb.model.submitPhone;
import com.jsb.util.Base64Util;
import com.jsb.util.RetrofitUtil;

import retrofit.RestAdapter;

public class UserRetrofitUtil extends RetrofitUtil {

    /**
     * 获取验证码
     * @param mContext
     * @param phone
     * @param callback
     */
    public static void obtainVerifyCode(Context mContext,String phone,NetCallback<NetWorkResultBean<submitPhone>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String p =new String("phone=");
        String k = p+phone;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit","s="+s);
        git.obtainVerifyCode(s, callback);
    }



     /**
     * 获取验证码
     * @param mContext
     * @param phone
     * @param callback
     */
    public static void login(Context mContext,String phone,String verifyCode,NetCallback<NetWorkResultBean<loginVerifyPhoneAndCode>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "phone="+phone+"verifyCode="+verifyCode;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit","s="+s);
        git.login(s, callback);
    }



}
