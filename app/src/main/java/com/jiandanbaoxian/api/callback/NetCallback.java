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
//        try {
//            InputStream in = error.getResponse().getBody().in();
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//            String line = "";
//            StringBuffer sb = new StringBuffer();
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//            String result = sb.toString();
//            JSONObject jsonObject = new JSONObject(result);
//            message = jsonObject.getString("message");
//            br.close();
//            in.close();
//        } catch (Exception e) {
//            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//            if (activeNetworkInfo == null) {
//                message = "无法连接网络";
//            }
//            if (error != null && error.getResponse() != null) {
//                int status = error.getResponse().getStatus();
//            }
//        } finally {
//            onFailure(error, message);
//            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
//        }



        Log.e("qw", "error: " + error.toString());
        ErrorData errorBodyAs;
        try {

//            String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
//            Log.v("qw", json.toString());
//

            Object body  = error.getBody();
            Object  response = error.getResponse();
            Object  s = error.getSuccessType();

            errorBodyAs = (ErrorData) error.getBodyAs(ErrorData.class);
            if (errorBodyAs != null)
                Log.e("qw", errorBodyAs.toString());
            else {
                Log.e("qw", "errorBodyAs is null ");
            }
        } catch (Exception e) {

        }
        onFailure(error, message);

    }

    //    public abstract void onSuccess(Object obj, Response response);
    public abstract void onFailure(RetrofitError error, String message);

}
