package com.jsb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.widget.TitleBar;


/**
 * Created by Administrator on 2015/9/18.
 */
public class InsureCarActivity extends BaseActivity {

    private TitleBar titleBar;
    private EditText etUserName;
    private EditText etCarNumber;
    private EditText etCarJiaNumber;
    private EditText etFadongjiNumber;
    private EditText etSignDate;
    private EditText etUserIdCardNumber;
    private EditText etStartInsureDate;
    private TextView tvNextStep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insure_car);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                InsureCarActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("车险", R.drawable.arrow_left, -1, "", "");


        etUserName = (EditText)findViewById(R.id.et_user_name);
        etCarNumber = (EditText)findViewById(R.id.et_car_number);
        etCarJiaNumber = (EditText)findViewById(R.id.et_car_jia_number);
        etFadongjiNumber = (EditText)findViewById(R.id.et_car_fadongji_number);
        etSignDate = (EditText)findViewById(R.id.et_sign_date);

    }

}
