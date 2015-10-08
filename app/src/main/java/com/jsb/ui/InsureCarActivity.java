package com.jsb.ui;

import android.content.Intent;
import android.os.Bundle;
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
 * Created by Administrator on 2015/9/18.
 */
public class InsureCarActivity extends BaseActivity {

    private boolean tvEnquiryFlag = false;
    private TitleBar titleBar;
    private EditText etUserName;
    private EditText etCarNumber;
    private EditText etCarJiaNumber;
    private EditText etFadongjiNumber;
    private EditText etSignDate;
    private EditText etUserIdCardNumber;
    private EditText etStartInsureDate;
    private TextView tv_enquiry;
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



        tv_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvEnquiryFlag)
                {
                    Toast.makeText(InsureCarActivity.this, "请先完善以上信息！", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void setUp() {
        tv_enquiry = (TextView) findViewById(R.id.tv_enquiry);
        tv_enquiry.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tv_enquiry.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("车险", R.drawable.arrow_left, -1, "", "");


        etUserName = (EditText)findViewById(R.id.et_user_name);
        etCarNumber = (EditText)findViewById(R.id.et_car_number);
        etCarJiaNumber = (EditText)findViewById(R.id.et_car_jia_number);
        etFadongjiNumber = (EditText)findViewById(R.id.et_car_fadongji_number);
        etSignDate = (EditText)findViewById(R.id.et_sign_date);


        etUserName.addTextChangedListener(textWatcher);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(etUserName.getText())) {
                tvEnquiryFlag = false;
                tv_enquiry.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                tv_enquiry.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                tvEnquiryFlag = true;
                tv_enquiry.setBackgroundResource(R.drawable.btn_select_base);
                tv_enquiry.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
