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
public class OrderCofirmJiaBanDogActivity extends BaseActivity {
    private boolean tvPayFlag = false;
    private TitleBar titleBar;
    private TextView tv_pay;
    private EditText et_insure_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm_jiaban_dog_insure);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                OrderCofirmJiaBanDogActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("确认订单", R.drawable.arrow_left, -1, "", "");

        et_insure_user_name = (EditText) findViewById(R.id.et_insure_user_name);
        et_insure_user_name.addTextChangedListener(textWatcher);
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        tv_pay.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tv_pay.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvPayFlag) {
                    startActivity(new Intent(OrderCofirmJiaBanDogActivity.this, PayJiaBanDogInsureActivity.class));
                } else {
                    Toast.makeText(OrderCofirmJiaBanDogActivity.this, "请先完善以上信息！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(et_insure_user_name.getText())) {
                tvPayFlag = false;
                tv_pay.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                tv_pay.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                tvPayFlag = true;
                tv_pay.setBackgroundResource(R.drawable.btn_select_base);
                tv_pay.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
