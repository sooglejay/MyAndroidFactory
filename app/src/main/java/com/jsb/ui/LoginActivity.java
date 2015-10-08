package com.jsb.ui;

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
import com.jsb.widget.TitleBar;

/**
 * Created by Administrator on 2015/9/20.
 */
public class LoginActivity extends BaseActivity {
    private TitleBar titleBar;

    private boolean loginTvFlag = false;
    private TextView tv_login;
    private TextView tv_obtain_verify_code;
    private EditText et_phone_number;
    private EditText et_verify_code;
    private CountDownTimer mCountTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUp();
        setLisenter();
    }

    private void setUp() {
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

        et_verify_code.addTextChangedListener(textWatcher);
        et_phone_number.addTextChangedListener(textWatcher);

    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                LoginActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });



        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginTvFlag) {
                    Toast.makeText(LoginActivity.this, "请输入手机号码和验证码登录！", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

        tv_obtain_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPhoneNumberValid(et_phone_number.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号码!", Toast.LENGTH_SHORT).show();
                } else {
                    mCountTimer = new CountDownTimer(6*1000, 1000) {
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
                }
            }
        });
    }


    /**
     * 简单判断手机号码合法性
     * @param phoneNumber
     * @return
     */
    private boolean checkPhoneNumberValid(String phoneNumber)
    {
        if(!TextUtils.isEmpty(phoneNumber)&&phoneNumber.length()==11)
            return true;
        return false;
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(et_phone_number.getText()) || TextUtils.isEmpty(et_verify_code.getText())) {
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
}
