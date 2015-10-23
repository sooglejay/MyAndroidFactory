package com.jsb.api.callback;

import android.content.Context;

import retrofit.Callback;
import retrofit.RetrofitError;

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
