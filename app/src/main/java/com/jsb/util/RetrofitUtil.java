package com.jsb.util;

import android.content.Context;

import com.jsb.constant.NetWorkConstant;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Header;

public class RetrofitUtil {



    /**
     * 创建RestAdapter
     * @param context
     * @return restAdapter
     */
    protected static RestAdapter getRestAdapter(Context context) {
        final RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {

            }
        };
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(NetWorkConstant.API_SERVER_URL).setRequestInterceptor(requestInterceptor).setLogLevel(RestAdapter.LogLevel.FULL).build();
        return restAdapter;
    }

}
