package com.jiandanbaoxian.ui.stopinsurance;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 主页面-取钱-添加新卡
 */
public class AddCardDetailActivity extends BaseActivity {
    public static final int REFRESH = 1000;
    private CountDownTimer mCountTimer;

    private String card;
    private String name;


    private String phoneString = "";
    private String verifyCodeStringService = "";
    private String verifyCodeStringUser = "";


    private Activity activity;

    private int userid;
    private TitleBar titleBar;
    private EditText etPhoneNumber;
    private EditText etCardName;
    private EditText etVerificationNumber;
    private TextView tvObtainVerifyCode;
    private ImageView ivAgree;
    private TextView tvLicence;
    private Button btnNextStep;

    private boolean isAgree = true;//是否同意服务协议

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-01-06 13:27:07 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        etCardName = (EditText) findViewById(R.id.et_card_name);
        etVerificationNumber = (EditText) findViewById(R.id.et_verification_number);
        tvObtainVerifyCode = (TextView) findViewById(R.id.tv_obtain_verify_code);
        ivAgree = (ImageView) findViewById(R.id.iv_agree);
        tvLicence = (TextView) findViewById(R.id.tv_licence);
        btnNextStep = (Button) findViewById(R.id.btn_next_step);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card_detail);
        activity = this;
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        card = getIntent().getExtras().getString("card");
        name = getIntent().getExtras().getString("name");

        findViews();
        setUpViews();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                AddCardDetailActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

        tvObtainVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkPhoneNumberValid(etPhoneNumber.getText().toString())) {
                    Toast.makeText(AddCardDetailActivity.this, "请输入正确的手机号码!", Toast.LENGTH_SHORT).show();
                } else {
                    mCountTimer.start();
                    phoneString = etPhoneNumber.getText().toString();
                    UserRetrofitUtil.obtainVerifyCode(AddCardDetailActivity.this, phoneString, 0, new NetCallback<NetWorkResultBean<Object>>(AddCardDetailActivity.this) {
                        @Override
                        public void onFailure(RetrofitError error, String message) {
                            Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();
                            mCountTimer.onFinish();
                            mCountTimer.cancel();

                        }

                        @Override
                        public void success(NetWorkResultBean<Object> submitPhoneNetWorkResultBean, Response response) {

                            if (submitPhoneNetWorkResultBean != null) {
                                int status = submitPhoneNetWorkResultBean.getStatus();
                                switch (status) {
                                    case HttpsURLConnection.HTTP_OK:
                                        if (submitPhoneNetWorkResultBean.getData() != null) {
                                            CommData bean = (CommData) submitPhoneNetWorkResultBean.getData();
                                            Toast.makeText(AddCardDetailActivity.this, "获取验证码成功！短信已经下发至您的手机上", Toast.LENGTH_SHORT).show();
                                            verifyCodeStringService = bean.getVerifyCode();
                                        }
                                        break;
                                    default:
                                        Toast.makeText(activity, submitPhoneNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }


                        }
                    });


                }
            }
        });


        ivAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAgree) {
                    isAgree = false;
                    ivAgree.setImageResource(R.drawable.icon_choose);
                    btnNextStep.setEnabled(false);
                    btnNextStep.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));


                } else {
                    isAgree = true;
                    ivAgree.setImageResource(R.drawable.icon_choose_selected);
                    btnNextStep.setEnabled(true);
                    btnNextStep.setTextColor(getResources().getColor(R.color.white_color));

                }
            }
        });


    }

    private void setUpViews() {
        titleBar.initTitleBarInfo("填写银行卡信息", R.drawable.arrow_left, -1, "", "");

        etCardName.addTextChangedListener(textWatcher);
        etPhoneNumber.addTextChangedListener(textWatcher);
        etVerificationNumber.addTextChangedListener(textWatcher);
        btnNextStep.setEnabled(false);
        btnNextStep.setClickable(true);
        btnNextStep.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCodeStringUser = etVerificationNumber.getText().toString();
                if (!isAgree) {
                    Toast.makeText(AddCardDetailActivity.this, "请阅读并勾选服务协议！", Toast.LENGTH_SHORT).show();

                } else if (!btnNextStep.isEnabled()) {
                    Toast.makeText(AddCardDetailActivity.this, "请完成所有信息的输入！", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(verifyCodeStringService)) {
                    Toast.makeText(AddCardDetailActivity.this, "请输入手机号获取验证码！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!verifyCodeStringService.equals(verifyCodeStringUser)) {
                    Toast.makeText(AddCardDetailActivity.this, "验证码不正确！请重新输入", Toast.LENGTH_SHORT).show();
                } else {

                    String verifyVode = etVerificationNumber.getText().toString();
                    UserRetrofitUtil.addWithdrawlAccount(activity, userid, etCardName.getText().toString(), card, name, 0, verifyVode, new NetCallback<NetWorkResultBean<Object>>(activity) {
                        @Override
                        public void onFailure(RetrofitError error, String message) {
                            Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void success(NetWorkResultBean<Object> stringNetWorkResultBean, Response response) {
                            if (stringNetWorkResultBean != null) {
                                int status = stringNetWorkResultBean.getStatus();
                                switch (status) {
                                    case HttpsURLConnection.HTTP_OK:
                                        Toast.makeText(activity, "添加新卡成功！", Toast.LENGTH_SHORT).show();
                                        activity.finish();
                                        break;
                                    default:
                                        Toast.makeText(activity, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }


                        }
                    });
                }
            }
        });


        new FreshWordsThread().start();

        mCountTimer = new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                //让验证码输入框获取焦点
                etVerificationNumber.requestFocus();
                tvObtainVerifyCode.setText("" + millisUntilFinished / 1000 + "秒后重试");
                tvObtainVerifyCode.setEnabled(false);
                tvObtainVerifyCode.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            }

            public void onFinish() {
                tvObtainVerifyCode.setText("重新获取验证码");
                tvObtainVerifyCode.setTextColor(getResources().getColor(R.color.white_color));
                tvObtainVerifyCode.setEnabled(true);
            }

        };

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isAgree || TextUtils.isEmpty(etVerificationNumber.getText()) || TextUtils.isEmpty(etPhoneNumber.getText()) || TextUtils.isEmpty(etCardName.getText())) {
                btnNextStep.setEnabled(false);
                btnNextStep.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                btnNextStep.setEnabled(true);
                btnNextStep.setTextColor(getResources().getColor(R.color.white_color));

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void finish() {
        //隐藏键盘
        if (etCardName != null) {
            UIUtils.setHideSoftInput(this, etCardName);
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
                    //手机号码输入框获取焦点
                    UIUtils.showSoftInput(AddCardDetailActivity.this, etCardName);
                    etCardName.requestFocus();
                    break;
                }

            }
        }
    }

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

}


