package com.jsb.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.getOvertimeInsuranceInfo;

import retrofit.RetrofitError;
import retrofit.client.Response;

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

                getReportableInsurance();
            }


        });

    }
    private void getOvertimeInsuranceInfo() {
        UserRetrofitUtil.getOvertimeInsuranceInfo(AndroidHttpPostActivity.this, new NetCallback<NetWorkResultBean<getOvertimeInsuranceInfo>>(AndroidHttpPostActivity.this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<getOvertimeInsuranceInfo> getOvertimeInsuranceInfoNetWorkResultBean, Response response) {

            }
        });
    }

     private void getReportableInsurance() {
        UserRetrofitUtil.getReportableInsurance(AndroidHttpPostActivity.this,7, new NetCallback<NetWorkResultBean<getOvertimeInsuranceInfo>>(AndroidHttpPostActivity.this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<getOvertimeInsuranceInfo> getOvertimeInsuranceInfoNetWorkResultBean, Response response) {

            }
        });
    }



}