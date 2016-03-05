package com.jiandanbaoxian.api.callback;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.jiandanbaoxian.model.ErrorData;
import com.jiandanbaoxian.model.NetWorkResultBean;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public abstract class NetCallback<T> implements Callback<T> {
    private Context context;

    public NetCallback(Context context) {
        this.context = context;
    }

    @Override
    public void failure(RetrofitError error) {
        String message = "";
        onFailure(error, message);

    }

    //    public abstract void onSuccess(Object obj, Response response);
    public abstract void onFailure(RetrofitError error, String message);

}
