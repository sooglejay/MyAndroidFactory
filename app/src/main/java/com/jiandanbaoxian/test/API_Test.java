package com.jiandanbaoxian.test;

import android.app.Activity;

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

}
