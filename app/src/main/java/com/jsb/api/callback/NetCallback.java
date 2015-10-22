package com.jsb.api.callback;

import android.content.Context;

import retrofit.Callback;
import retrofit.RetrofitError;
/**
 * Created by xuejiebang-android on 15/7/8.
 */
public abstract class NetCallback<T> implements Callback<T> {
    private Context context;

    public NetCallback(Context context) {
        this.context = context;
    }

    @Override
    public void failure(RetrofitError error) {
        onFailure(error);
    }

    //    public abstract void onSuccess(Object obj, Response response);
    public abstract void onFailure(RetrofitError error);
}
