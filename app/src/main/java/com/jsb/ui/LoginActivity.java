package com.jsb.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.widget.TitleBar;

/**
 * Created by Administrator on 2015/9/20.
 */
public class LoginActivity extends BaseActivity {
    private TitleBar titleBar;

    private Button btn_login ;
    private EditText et_phone_number ;
    private EditText et_verification_number ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUp();
        setLisenter();
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
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("快捷登录", R.drawable.arrow_left, -1, "", "");


        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setEnabled(false);
        btn_login.setClickable(true);
        btn_login.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btn_login.isEnabled()) {
                    Toast.makeText(LoginActivity.this,"请输入手机号码和验证码登录！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,"正在登录！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        et_verification_number = (EditText)findViewById(R.id.et_verification_number);
        et_phone_number = (EditText)findViewById(R.id.et_phone_number);

        et_verification_number.addTextChangedListener(textWatcher);
        et_phone_number.addTextChangedListener(textWatcher);

    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(TextUtils.isEmpty(et_phone_number.getText())||TextUtils.isEmpty(et_verification_number.getText()))
            {
                btn_login.setEnabled(false);
                btn_login.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            }else {
                btn_login.setEnabled(true);
                btn_login.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
