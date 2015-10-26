package com.jsb.api.user;

import android.content.Context;
import android.util.Log;

import com.jsb.api.callback.NetCallback;
import com.jsb.model.CommData;
import com.jsb.model.FreedomData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.Overtimeinsurance;
import com.jsb.model.PauseData;
import com.jsb.model.ReportableInsurance;
import com.jsb.model.TeamData;
import com.jsb.util.Base64Util;
import com.jsb.util.MD5Util;
import com.jsb.util.RetrofitUtil;

import java.util.List;

import retrofit.RestAdapter;

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
    public static void getOvertimeInsuranceInfo(Context mContext, NetCallback<NetWorkResultBean<Overtimeinsurance>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        git.getOvertimeInsuranceInfo("", callback);
    }


    /**
     * 加班狗报案
     *
     * @param mContext
     * @param userid
     * @param callback
     */
    public static void getReportableInsurance(Context mContext, int userid, NetCallback<NetWorkResultBean<ReportableInsurance>> callback) {
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
     * 提交订单时保存订单信息
     *
     * @param mContext
     * @param callback
     */
    public static void saveOvertimeInsuranceOrder(Context mContext,
                                                  String name,
                                                  Integer orderid,
                                                  String phone,
                                                  String idcardnum,
                                                  String recievename,
                                                  String receivephone,
                                                  String recieveaddress,
                                                  String refereephone,
                                                  String operatorphone,
                                                  NetCallback<NetWorkResultBean<String>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String key =
                "name=" + name +
                        "&orderid=" + orderid +
                        "&phone=" + phone +
                        "&idcardnum=" + idcardnum +
                        "&recievename=" + recievename +
                        "&receivephone=" + receivephone +
                        "&recieveaddress=" + recieveaddress +
                        "&refereephone=" + refereephone +
                        "&operatorphone=" + operatorphone;
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
    public static void getPauseInfo(Context mContext,int userid , NetCallback<NetWorkResultBean<List<PauseData>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "userid=" + userid;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.getPauseInfo(s, callback);
    }


    /**
     * 按团长名字，电话，团名搜索团信息
     *
     * @param mContext
     * @param callback
     */
    public static void searchTeam(Context mContext,String searchParam , NetCallback<NetWorkResultBean<List<TeamData>>> callback) {
        RestAdapter restAdapter = getRestAdapter(mContext);
        UserApi git = restAdapter.create(UserApi.class);
        String k = "searchParam=" + searchParam;
        String s = Base64Util.encode(k.getBytes());
        Log.e("Retrofit", "original:" + k + "\nbase64:" + s);
        git.searchTeam(s, callback);
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

}
