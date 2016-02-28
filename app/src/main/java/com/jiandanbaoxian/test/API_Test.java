package com.jiandanbaoxian.test;

import android.app.Activity;

import com.google.gson.Gson;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.RegionBean;
import com.jiandanbaoxian.model.VehicleTypeInfo;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sooglejay on 16/2/25.
 */
public class API_Test {
    public static void test(Activity activity)
    {
        UserRetrofitUtil.vehcileTypeInfo(activity, "370602", "鲁Y82599", "LSGJT62U87S009773", "0", new NetCallback<NetWorkResultBean<List<VehicleTypeInfo>>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {

            }

            @Override
            public void success(NetWorkResultBean<List<VehicleTypeInfo>> listNetWorkResultBean, Response response) {

            }
        });
    }

    public static final void getProvince(Activity activity)
    {
        UserRetrofitUtil.getProvenceNo(activity, new NetCallback<NetWorkResultBean<List<RegionBean>>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {

            }

            @Override
            public void success(NetWorkResultBean<List<RegionBean>> listNetWorkResultBean, Response response) {

            }
        });
    }

    public static void main(String[] args) {
//        String j =" [{"amt":0,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":1,"insrnc_cde":"030101","insrnc_name":"车辆损失险","number":-1,"premium":-1.0,"remark":""},{"amt":306006009,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":1,"insrnc_cde":"030102","insrnc_name":"第三方责任险","number":-1,"premium":-1.0,"remark":""},{"amt":10000,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":1,"insrnc_cde":"030104","insrnc_name":"司机座位险","number":-1,"premium":-1.0,"remark":""},{"amt":10000,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":1,"insrnc_cde":"030105","insrnc_name":"乘客座位险","number":-1,"premium":-1.0,"remark":""},{"amt":0,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":0,"insrnc_cde":"030107","insrnc_name":"盗抢险","number":-1,"premium":-1.0,"remark":""},{"amt":0,"bullet_glass":-1,"c_ly15":-1,"franchise_flag":1,"insrnc_cde":"030116","insrnc_name":"自燃险","number":-1,"premium":-1.0,"remark":""}]";
        Gson gson = new Gson();
//        String obj = gson.toJson(j);
//        System.out.print(obj);

    }
}
