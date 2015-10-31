package com.jsb.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.event.BusEvent;
import com.jsb.model.CommData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.ProgressDialogUtil;
import com.jsb.util.UIUtils;
import com.jsb.widget.TitleBar;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    private TitleBar titleBar;

    private boolean loginTvFlag = false;
    private TextView tv_login;
    private TextView tv_obtain_verify_code;
    private EditText et_phone_number;
    private EditText et_verify_code;
    private CountDownTimer mCountTimer;


    private String phoneString = "";
    private String verifyCodeStringService = "";
    private String verifyCodeStringUser = "";


    private ProgressDialogUtil mProgressUtil;


    public static void startLoginActivity(Context context)
    {
        context.startActivity(new Intent(context,LoginActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUp();
        setLisenter();
    }

    private void setUp() {
        mProgressUtil = new ProgressDialogUtil(this);


        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("快捷登录", R.drawable.arrow_left, -1, "", "");


        tv_obtain_verify_code = (TextView) findViewById(R.id.tv_obtain_verify_code);
        tv_login = (TextView) findViewById(R.id.tv_login);
        loginTvFlag = false;
        tv_login.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tv_login.setClickable(true);
        tv_login.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));


        et_verify_code = (EditText) findViewById(R.id.et_verification_number);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);



        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //手机号码输入框获取焦点
                UIUtils.showSoftInput(LoginActivity.this,et_phone_number);
                et_phone_number.requestFocus();
            }
        }, 100);  //在500毫秒后打开



        et_verify_code.addTextChangedListener(textWatcher);
        et_phone_number.addTextChangedListener(textWatcher);



        mCountTimer = new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                //让验证码输入框获取焦点
                et_verify_code.requestFocus();
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


    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                LoginActivity.this.finish();
            }
            @Override
            public void onRightButtonClick(View v) {
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(verifyCodeStringService)) {
                    Toast.makeText(LoginActivity.this, "请输入手机号获取验证码！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!verifyCodeStringService.equals(verifyCodeStringUser)) {
                    Toast.makeText(LoginActivity.this, "验证码不正确！请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressUtil.show("正在登录...");
                    UserRetrofitUtil.login(LoginActivity.this, phoneString, verifyCodeStringUser, new NetCallback<NetWorkResultBean<CommData>>(LoginActivity.this) {
                        @Override
                        public void onFailure(RetrofitError error) {
                            mProgressUtil.hide();
                            mCountTimer.onFinish();
                        }

                        @Override
                        public void success(NetWorkResultBean<CommData> commDataNetWorkResultBean, Response response) {
                            CommData bean = commDataNetWorkResultBean.getData();
                            //保存用户信息
                            PreferenceUtil.save(LoginActivity.this, PreferenceConstant.userid, bean.getUserid());
                            PreferenceUtil.save(LoginActivity.this, PreferenceConstant.name, bean.getUserInfo().getName());
                            PreferenceUtil.save(LoginActivity.this, PreferenceConstant.phone, bean.getUserInfo().getPhone());
                            PreferenceUtil.save(LoginActivity.this, PreferenceConstant.pwd, bean.getUserInfo().getPwd());
                            mProgressUtil.hide();
                            EventBus.getDefault().post(new BusEvent(BusEvent.MSG_Login_Success));
                            LoginActivity.this.finish();
                        }
                    });

                }
            }
        });

        tv_obtain_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountTimer.start();
                if (!checkPhoneNumberValid(et_phone_number.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号码!", Toast.LENGTH_SHORT).show();
                } else {
                    phoneString = et_phone_number.getText().toString();
                    UserRetrofitUtil.obtainVerifyCode(LoginActivity.this, phoneString, new NetCallback<NetWorkResultBean<CommData>>(LoginActivity.this) {
                        @Override
                        public void onFailure(RetrofitError error) {
                            Toast.makeText(LoginActivity.this, "无法连接网络！", Toast.LENGTH_SHORT).show();
                            mCountTimer.onFinish();
                            mCountTimer.cancel();

                        }

                        @Override
                        public void success(NetWorkResultBean<CommData> submitPhoneNetWorkResultBean, Response response) {
                            Toast.makeText(LoginActivity.this, "获取验证码成功！短信已经下发至您的手机上", Toast.LENGTH_SHORT).show();
                            verifyCodeStringService = submitPhoneNetWorkResultBean.getData().getVerifyCode();
                        }
                    });





                }
            }
        });
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


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            phoneString = et_phone_number.getText().toString();
            verifyCodeStringUser = et_verify_code.getText().toString();

            if (TextUtils.isEmpty(phoneString) || TextUtils.isEmpty(verifyCodeStringUser)) {
                loginTvFlag = false;
                tv_login.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                tv_login.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                loginTvFlag = true;
                tv_login.setBackgroundResource(R.drawable.btn_select_base);
                tv_login.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void finish() {
        //隐藏键盘
        if(et_phone_number!=null)
        {
            UIUtils.setHideSoftInput(this,et_phone_number);
        }
        super.finish();
    }
}
