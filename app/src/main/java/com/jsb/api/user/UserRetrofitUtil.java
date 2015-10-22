package com.jsb.api.user;

import android.content.Context;
import android.util.Base64;

import com.jsb.api.callback.NetCallback;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.submitPhone;
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
        String k = ("phone="+phone);
        String s = Base64.encodeToString(k.getBytes(), Base64.DEFAULT);
        String params= Base64.encodeToString(k.getBytes(), Base64.DEFAULT).toString();
        git.obtainVerifyCode(s, callback);
    }
}
