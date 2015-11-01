package com.jsb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.OvertimeData;
import com.jsb.model.Overtimeinsurance;
import com.jsb.util.ProgressDialogUtil;
import com.jsb.widget.TitleBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 买保险-加班狗
 */
public class InsureJiaBanDogActivity extends BaseActivity {
    private TitleBar titleBar;
    private CheckBox cb_agree_license;
    private TextView tv_buy_insure;
    private TextView tv_time_shengxiao;
    private TextView tv_amount;


    private boolean isAgreeWithLicence = true;
    private SimpleDateFormat df_yyyy_m_d =new SimpleDateFormat("yyyy-MM-dd");

    private ProgressDialogUtil progressDialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insure_jiaban_dog);
        progressDialogUtil = new ProgressDialogUtil(this);
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
        tv_time_shengxiao = (TextView) findViewById(R.id.tv_time_shengxiao);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_buy_insure = (TextView) findViewById(R.id.tv_buy_insure);
        tv_buy_insure.setEnabled(true);
        tv_buy_insure.setBackgroundResource(R.drawable.btn_select_base_shape_0);
        tv_buy_insure.setTextColor(getResources().getColor(R.color.white_color));
        tv_buy_insure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAgreeWithLicence) {
                    startActivity(new Intent(InsureJiaBanDogActivity.this, OrderCofirmJiaBanDogInsureActivity.class));
                } else {
                    Toast.makeText(InsureJiaBanDogActivity.this, "请您勾选同意保障条款！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("加班狗", R.drawable.arrow_left, -1, "", "");
        cb_agree_license = (CheckBox) findViewById(R.id.cb_agree_license);
        cb_agree_license.setChecked(true);
        cb_agree_license.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isAgreeWithLicence = true;
                    cb_agree_license.setButtonDrawable(R.drawable.icon_choose_selected);
                    tv_buy_insure.setBackgroundResource(R.drawable.btn_select_base_shape_0);
                    tv_buy_insure.setTextColor(getResources().getColor(R.color.white_color));
                } else {
                    cb_agree_license.setButtonDrawable(R.drawable.icon_choose);
                    isAgreeWithLicence = false;
                    tv_buy_insure.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                    tv_buy_insure.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
                }
            }
        });

        progressDialogUtil.show("正在获取加班险信息...");
        UserRetrofitUtil.getOvertimeInsuranceInfo(this, new NetCallback<NetWorkResultBean<OvertimeData>>(InsureJiaBanDogActivity.this) {
            @Override
            public void onFailure(RetrofitError error) {
                progressDialogUtil.hide();
            }

            @Override
            public void success(NetWorkResultBean<OvertimeData> overtimeinsuranceNetWorkResultBean, Response response) {
                progressDialogUtil.hide();
                Overtimeinsurance bean= overtimeinsuranceNetWorkResultBean.getData().getOvertimeInsurance();
                if(bean.getReleasetime()!=null) {
                    //生效时间
                    tv_time_shengxiao.setText("生效时间：" + df_yyyy_m_d.format(new Date(bean.getReleasetime())) + "");
                }else {
                    tv_time_shengxiao.setText("生效时间：" + df_yyyy_m_d.format(new Date()) + "");
                }

                if(bean.getResidue()!=null)
                {
                    tv_amount.setText("本期商品数还剩"+bean.getResidue()+"份");
                }else {
                    tv_amount.setText("本期商品数还剩"+0+"份");
                }
            }
        });
    }
}
