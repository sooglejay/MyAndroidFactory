package com.jiandanbaoxian.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的-修改密码
 */
public class MyModifyPasswordActivity extends BaseActivity {

    public static final String TITLE_STRING = "title_string";
    public static final int REFRESH = 1000;
    private TitleBar titleBar;
    private String titleString;


    private EditText et_verification_number;
    private TextView tv_obtain_verify_code;
    private TextView tv_confirm;

    private CountDownTimer mCountTimer;

    private String phoneString = "";
    private String verifyCodeStringService = "";
    private String verifyCodeStringUser = "";

    private EditText et_new_password;
    private EditText et_re_new_password;
    private String newPasswordString = "";


    private ProgressDialogUtil progressDialogUtil;

    public static void startModifyPasswordActivity(Activity context, String titleStr) {
        Intent intent = new Intent(context, MyModifyPasswordActivity.class);
        intent.putExtra(TITLE_STRING, titleStr);
        context.startActivity(intent);
    }

    public static void startModifyPasswordActivity(Activity context) {
        Intent intent = new Intent(context, MyModifyPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_modify_password);
        phoneString = PreferenceUtil.load(this, PreferenceConstant.phone, "");
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyModifyPasswordActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
        tv_obtain_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserRetrofitUtil.obtainVerifyCode(MyModifyPasswordActivity.this, phoneString, 2, new NetCallback<NetWorkResultBean<Object>>(MyModifyPasswordActivity.this) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        Toast.makeText(MyModifyPasswordActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void success(NetWorkResultBean<Object> submitPhoneNetWorkResultBean, Response response) {
                        if (submitPhoneNetWorkResultBean != null) {
                            int status = submitPhoneNetWorkResultBean.getStatus();
                            switch (status) {
                                case HttpsURLConnection.HTTP_OK:
                                    if (submitPhoneNetWorkResultBean.getData() != null) {
                                        CommData bean = (CommData) submitPhoneNetWorkResultBean.getData();
                                        Toast.makeText(MyModifyPasswordActivity.this, "获取验证码成功！短信已经下发至您的手机上", Toast.LENGTH_SHORT).show();
                                        verifyCodeStringService = bean.getVerifyCode();
                                    }
                                    break;
                                default:
                                    Toast.makeText(MyModifyPasswordActivity.this, submitPhoneNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }


                    }
                });

                mCountTimer = new CountDownTimer(60 * 1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        tv_obtain_verify_code.setText("" + millisUntilFinished / 1000 + "秒后重试");
                        tv_obtain_verify_code.setEnabled(false);
                        tv_obtain_verify_code.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
                    }

                    public void onFinish() {
                        tv_obtain_verify_code.setText("重新获取验证码");
                        tv_obtain_verify_code.setTextColor(getResources().getColor(R.color.white_color));
                        tv_obtain_verify_code.setEnabled(true);
                    }
                };
                mCountTimer.start();
                et_verification_number.requestFocus();


            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断验证码
                if (TextUtils.isEmpty(verifyCodeStringService)) {
                    Toast.makeText(MyModifyPasswordActivity.this, "请点击获取验证码！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!verifyCodeStringService.equals(verifyCodeStringUser)) {
                    Toast.makeText(MyModifyPasswordActivity.this, "验证码不正确！请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    //进入密码判断
                    newPasswordString = et_new_password.getText().toString();
                    if (TextUtils.isEmpty(newPasswordString)) {
                        Toast.makeText(MyModifyPasswordActivity.this, "新密码不能为空！", Toast.LENGTH_SHORT).show();

                    } else if (newPasswordString.equals(et_re_new_password.getText().toString())) {


                        progressDialogUtil.show("正在提交数据...");
                        UserRetrofitUtil.setWithdrawlPassword(MyModifyPasswordActivity.this, phoneString, verifyCodeStringService, newPasswordString, new NetCallback<NetWorkResultBean<Object>>(MyModifyPasswordActivity.this) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {
                                progressDialogUtil.hide();
                                Toast.makeText(MyModifyPasswordActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void success(NetWorkResultBean<Object> stringNetWorkResultBean, Response response) {
                                progressDialogUtil.hide();
                                if (stringNetWorkResultBean != null) {
                                    int status = stringNetWorkResultBean.getStatus();
                                    switch (status) {
                                        case HttpsURLConnection.HTTP_OK:
                                            PreferenceUtil.save(MyModifyPasswordActivity.this, PreferenceConstant.pwd, newPasswordString);
                                            Toast.makeText(MyModifyPasswordActivity.this, "密码设置成功！！", Toast.LENGTH_SHORT).show();
                                            MyModifyPasswordActivity.this.finish();
                                            break;
                                        default:
                                            Toast.makeText(MyModifyPasswordActivity.this, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }


                            }
                        });

                    } else {
                        Toast.makeText(MyModifyPasswordActivity.this, "两次输入密码不匹配！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void setUp() {
        titleString = getIntent().getStringExtra(TITLE_STRING);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(TextUtils.isEmpty(titleString) ? StringConstant.modifyPassword : titleString, R.drawable.arrow_left, -1, "", "");

        progressDialogUtil = new ProgressDialogUtil(this, true);

        tv_obtain_verify_code = (TextView) findViewById(R.id.tv_obtain_verify_code);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);

        tv_confirm.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tv_confirm.setClickable(true);
        tv_confirm.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));

        et_verification_number = (EditText) findViewById(R.id.et_verification_number);

        et_re_new_password = (EditText) findViewById(R.id.et_re_new_password);
        et_new_password = (EditText) findViewById(R.id.et_new_password);

        et_verification_number.addTextChangedListener(textWatcher);
        et_new_password.addTextChangedListener(textWatcher);
        et_re_new_password.addTextChangedListener(textWatcher);


        new FreshWordsThread().start();

    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            verifyCodeStringUser = et_verification_number.getText().toString();

            if (TextUtils.isEmpty(phoneString) || TextUtils.isEmpty(verifyCodeStringUser) || TextUtils.isEmpty(et_new_password.getText().toString()) || TextUtils.isEmpty(et_re_new_password.getText().toString())) {
                tv_confirm.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                tv_confirm.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                tv_confirm.setBackgroundResource(R.drawable.btn_select_base);
                tv_confirm.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * 简单判断手机号码合法性
     *
     * @param phoneNumber
     * @return
     */
    private boolean checkPhoneNumberValid(String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() == 11)
            return true;
        return false;
    }


    @Override
    public void finish() {
        //隐藏键盘
        if (et_verification_number != null) {
            UIUtils.setHideSoftInput(this, et_verification_number);
        }
        super.finish();
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

    class RefreshUiHandler extends Handler {
        /**
         * 接受子线程传递的消息机制
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case REFRESH: {
                    //手机号码输入框获取焦点
                    UIUtils.showSoftInput(MyModifyPasswordActivity.this, et_verification_number);
                    et_verification_number.requestFocus();
                    break;
                }

            }
        }

    }
}
