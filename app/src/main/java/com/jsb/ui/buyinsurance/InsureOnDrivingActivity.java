package com.jsb.ui.buyinsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.ui.BaseActivity;
import com.jsb.util.ProgressDialogUtil;
import com.jsb.widget.TitleBar;

/**
 * 买保险-驾驶险
 */
public class InsureOnDrivingActivity extends BaseActivity {
    private TitleBar titleBar;
    private CheckBox cb_agree_license;
    private TextView tv_buy_insure;
    private ProgressDialogUtil progressDialogUtil;

    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insure_on_driving_activity);
        activity = this;
        progressDialogUtil = new ProgressDialogUtil(this,true);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        tv_buy_insure = (TextView) findViewById(R.id.tv_buy_insure);
        tv_buy_insure.setEnabled(true);
        tv_buy_insure.setBackgroundResource(R.drawable.btn_select_base_shape_0);
        tv_buy_insure.setTextColor(getResources().getColor(R.color.white_color));
        tv_buy_insure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, OrderCofirmOnDrivingInsureActivity.class));
            }
        });
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("驾驶险", R.drawable.arrow_left, -1, "", "");
        cb_agree_license = (CheckBox) findViewById(R.id.cb_agree_license);
        cb_agree_license.setChecked(true);
        cb_agree_license.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_agree_license.setButtonDrawable(R.drawable.icon_choose_selected);
                    tv_buy_insure.setEnabled(true);
                    tv_buy_insure.setBackgroundResource(R.drawable.btn_select_base_shape_0);
                    tv_buy_insure.setTextColor(getResources().getColor(R.color.white_color));
                } else {
                    cb_agree_license.setButtonDrawable(R.drawable.icon_choose);
                    tv_buy_insure.setEnabled(false);
                    tv_buy_insure.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                    tv_buy_insure.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
                }
            }
        });

    }

}
