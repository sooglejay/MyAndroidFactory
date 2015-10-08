package com.jsb.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.widget.TitleBar;

/**
 * Created by Administrator on 2015/9/18.
 */
public class PayJiaBanDogInsureActivity extends BaseActivity {
    private TitleBar titleBar;
    private TextView tv_confirm_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_jiaban_dog);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                PayJiaBanDogInsureActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        tv_confirm_pay = (TextView) findViewById(R.id.tv_confirm_pay);
        tv_confirm_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PayJiaBanDogInsureActivity.this,"谢谢惠顾！",Toast.LENGTH_SHORT).show();
            }
        });
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("支付", R.drawable.arrow_left, -1, "", "");



    }

}
