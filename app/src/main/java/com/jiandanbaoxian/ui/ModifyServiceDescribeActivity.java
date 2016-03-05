package com.jiandanbaoxian.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by JammyQtheLab on 2015/12/14.
 */
public class ModifyServiceDescribeActivity extends BaseActivity {
    public static final int REFRESH = 1000;

    private TitleBar titleBar;
    private EditText editText;
    private Activity activity;
    private int userid = -1;

    private String serviceDescribe="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_service_describe);
        activity = this;
        userid = PreferenceUtil.load(activity, PreferenceConstant.userid,-1);
        serviceDescribe = getIntent().getExtras().getString("serviceDescribe","");
        findViews();
        setUpViews();
        setUpListeners();
        new FreshWordsThread().start();
    }

    private void setUpListeners() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {
                 final String newStr = editText.getText().toString();
                UserRetrofitUtil.modifySelfInfo(activity, userid, -1, -1,newStr, "-1", "-1","-1", "-1", null, new NetCallback<NetWorkResultBean<Object>>(activity) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void success(NetWorkResultBean<Object> userstableNetWorkResultBean, Response response) {

                        if (userstableNetWorkResultBean != null) {
                            int status = userstableNetWorkResultBean.getStatus();
                            switch (status) {
                                case HttpsURLConnection.HTTP_OK:

                                    Intent intent = getIntent();
                                    intent.putExtra("MODIFY_SERVICE_DESCRIBE", newStr);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                    break;
                                default:
                                    Toast.makeText(activity, userstableNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }






                    }
                });

            }
        });

    }

    private void setUpViews() {
        titleBar.initTitleBarInfo("修改服务介绍", R.drawable.arrow_left, -1, "", "完成");

    }

    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        editText = (EditText) findViewById(R.id.et_phone_number);
        editText.setText(serviceDescribe);
        editText.setSelection(serviceDescribe.length());
    }


    /**
     * 主线程更新ui
     */
    private class FreshWordsThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            refreshUiHandler.sendEmptyMessage(REFRESH);
        }
    }

    //主线程中的handler
    private RefreshUiHandler refreshUiHandler = new RefreshUiHandler();

    private class RefreshUiHandler extends Handler {
        /**
         * 接受子线程传递的消息机制
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case REFRESH: {
                    //输入框获取焦点
                    UIUtils.showSoftInput(ModifyServiceDescribeActivity.this, editText);
                    editText.requestFocus();
                    break;
                }

            }
        }

    }

    @Override
    public void finish() {
        //隐藏键盘
        if (editText != null) {
            UIUtils.setHideSoftInput(this, editText);
        }
        super.finish();
    }


}
