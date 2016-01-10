package com.jiandanbaoxian.ui.buyinsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.jugeOvertimeInsuranceOrder;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.BrowserWebViewActivity;
import com.jiandanbaoxian.ui.LoginActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 买保险-驾驶险
 */
public class InsureOnDrivingActivity extends BaseActivity {
    private TitleBar titleBar;
    private ImageView iv_choose;
    private LinearLayout layout_rule;
    private TextView tv_buy_insure;
    private ProgressDialogUtil progressDialogUtil;
    private Activity activity;
    private boolean isAgreeWithLicence = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insure_on_driving_activity);
        activity = this;
        progressDialogUtil = new ProgressDialogUtil(this, true);
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
        layout_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BrowserWebViewActivity.startActivity(activity, true);
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
                if (isAgreeWithLicence) {
                    String phone = PreferenceUtil.load(activity, PreferenceConstant.phone, "");
                    if (TextUtils.isEmpty(phone)) {
                        Toast.makeText(activity, "请先登录！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity, LoginActivity.class));
                    } else {
                        startActivity(new Intent(activity, OrderCofirmOnDrivingInsureActivity.class));
                    }
                } else {
                    Toast.makeText(activity, "请您勾选同意保障条款！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("驾驶险", R.drawable.arrow_left, -1, "", "");
        layout_rule = (LinearLayout) findViewById(R.id.layout_rule);
        iv_choose = (ImageView) findViewById(R.id.iv_choose);
        iv_choose.setImageResource(R.drawable.icon_choose_selected);
        iv_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAgreeWithLicence) {
                    isAgreeWithLicence = true;
                    iv_choose.setImageResource(R.drawable.icon_choose_selected);
                    tv_buy_insure.setBackgroundResource(R.drawable.btn_select_base_shape_0);
                    tv_buy_insure.setTextColor(getResources().getColor(R.color.white_color));
                } else {
                    iv_choose.setImageResource(R.drawable.icon_choose);
                    isAgreeWithLicence = false;
                    tv_buy_insure.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                    tv_buy_insure.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
                }
            }
        });


    }

}
