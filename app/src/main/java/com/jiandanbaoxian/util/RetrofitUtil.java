package com.jiandanbaoxian.util;

import android.content.Context;

import com.jiandanbaoxian.constant.NetWorkConstant;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RetrofitUtil {


    /**
     * 创建RestAdapter
     *
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

    /**
     * 创建RestAdapter
     *
     * @param context
     * @return restAdapter
     */
    protected static RestAdapter getRestAdapterWithLongTimeOut(Context context) {


        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(1000 * 30, TimeUnit.MILLISECONDS);
        client.setReadTimeout(1000 * 40, TimeUnit.MILLISECONDS);
        final OkClient okClient = new OkClient(client);


        final RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {

            }
        };
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(NetWorkConstant.API_SERVER_URL).setRequestInterceptor(requestInterceptor).setLogLevel(RestAdapter.LogLevel.FULL).setClient(okClient).build();
        return restAdapter;
    }



}
