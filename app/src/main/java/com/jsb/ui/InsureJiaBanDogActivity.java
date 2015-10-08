package com.jsb.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.widget.TitleBar;

/**
 * Created by Administrator on 2015/9/18.
 */
public class InsureJiaBanDogActivity extends BaseActivity {
    private TitleBar titleBar;
    private CheckBox cb_agree_license;
    private TextView tv_buy_insure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insure_jiaban_dog);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                InsureJiaBanDogActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        tv_buy_insure = (TextView) findViewById(R.id.tv_buy_insure);
        tv_buy_insure.setEnabled(false);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("加班狗", R.drawable.arrow_left, -1, "", "");
        cb_agree_license = (CheckBox) findViewById(R.id.cb_agree_license);
        cb_agree_license.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    tv_buy_insure.setEnabled(true);
                }
                else {
                    tv_buy_insure.setEnabled(false);
                }
            }
        });

    }

}
