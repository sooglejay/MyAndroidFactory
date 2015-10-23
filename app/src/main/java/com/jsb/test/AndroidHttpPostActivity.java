package com.jsb.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsb.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class AndroidHttpPostActivity extends Activity {

    Button bt_test;
    TextView result;

    String string;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_activity_test_http);
        result = (TextView) findViewById(R.id.result);
        bt_test = (Button) findViewById(R.id.bt_test);
        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = "phone=13678054215";
                String base64Str = Base64.encodeToString(phone.getBytes(), Base64.DEFAULT);
                Log.e("jwjw", "phone:" + phone + "  base64Str:" + base64Str);


                Log.e("jwjw", "phone:" + phone);
                Log.e("jwjw", "base64Str:" + base64Str);
                RequestParams params = new RequestParams();
//              params.addHeader("name", "value");
//              params.addQueryStringParameter("name", "value");

// 只包含字符串参数时默认使用BodyParamsEntity，
// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
//                params.addBodyParameter("param", base64Str);

// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
//                params.addBodyParameter("file", new File("path"));
//                ...

                HttpUtils http = new HttpUtils();
                http.send(HttpRequest.HttpMethod.POST,
                        "http://120.25.227.69:8080/insurance/ci/1/checkUpdate/",
                        params,
                        new RequestCallBack<String>() {

                            @Override
                            public void onStart() {
                                result.setText("conn...");
                            }

                            @Override
                            public void onLoading(long total, long current, boolean isUploading) {
                                if (isUploading) {
                                    result.setText("upload: " + current + "/" + total);
                                } else {
                                    result.setText("reply: " + current + "/" + total);
                                }
                            }

                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                result.setText("reply: " + responseInfo.result);
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                result.setText("失败：" + " errr:" + error.getMessage() + error.getExceptionCode() + ":" + msg);
                                Log.e("jwjw", "error:" + error.getMessage());
                            }
                        });


            }
        });
    }
}