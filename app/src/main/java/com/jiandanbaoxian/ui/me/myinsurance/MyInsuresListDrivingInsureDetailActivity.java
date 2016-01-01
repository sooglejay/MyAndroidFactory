package com.jiandanbaoxian.ui.me.myinsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.Driverordertable;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.buyinsurance.OrderCofirmOnDrivingInsureActivity;
import com.jiandanbaoxian.util.TimeUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;


/**
 * 我的-我的保险-车险
 */
public class MyInsuresListDrivingInsureDetailActivity extends BaseActivity {
    public final static String ExtrasKeyName = "ExtrasKeyName";
    private final static int ACTION_CALL = 110;


    private Activity context;
    private TitleBar mTitleBar;
    private LinearLayout layout_server_call;

    private TextView tv_insurance_agent_name;
    private TextView tv_insurant_name;
    private TextView tv_insurance_number;
    private TextView tv_insurance_duration;
    private TextView tv_insurance_scope;
    private TextView tv_insure_price;
    private Driverordertable driverordertable;

    private CheckBox cb_agree_license;
    private TextView tv_buy_insure;
    private boolean isAgreeWithLicence = true;

    public static void startActivity(Activity context, Driverordertable bean) {
        Intent intent = new Intent(context, MyInsuresListDrivingInsureDetailActivity.class);
        intent.putExtra(ExtrasKeyName, bean);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_insure_driving);
        context = this;

        setUpView();
        setUpListener();

    }

    private void setUpView() {
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.initTitleBarInfo("产品详情", R.drawable.arrow_left, -1, "", "");


        tv_buy_insure = (TextView) findViewById(R.id.tv_buy_insure);
        tv_buy_insure.setEnabled(true);
        tv_buy_insure.setBackgroundResource(R.drawable.btn_select_base_shape_0);
        tv_buy_insure.setTextColor(getResources().getColor(R.color.white_color));


        //许可协议
        cb_agree_license = (CheckBox) findViewById(R.id.cb_agree_license);
        cb_agree_license.setChecked(true);


        //服务电话
        layout_server_call = (LinearLayout) findViewById(R.id.layout_server_call);

        tv_insurance_agent_name = (TextView) findViewById(R.id.tv_insurance_agent_name);
        tv_insurant_name = (TextView) findViewById(R.id.tv_insurant_name);
        tv_insurance_number = (TextView) findViewById(R.id.tv_insurance_number);
        tv_insurance_duration = (TextView) findViewById(R.id.tv_insurance_duration);
        tv_insurance_scope = (TextView) findViewById(R.id.tv_insurance_scope);
        tv_insure_price = (TextView) findViewById(R.id.tv_insure_price);


        // 传递过来的model   并 显示
        driverordertable = getIntent().getParcelableExtra(MyInsuresListCarInsureDetailActivity.ExtrasKeyName);
        if (driverordertable != null) {
            if (driverordertable.getCompanyInfo() != null) {
                tv_insurance_agent_name.setText(TextUtils.isEmpty(driverordertable.getCompanyInfo().getCompanyname()) ? "" : driverordertable.getCompanyInfo().getCompanyname());
            } else {
                tv_insurance_agent_name.setText("null");
            }
            tv_insurant_name.setText(TextUtils.isEmpty(driverordertable.getName()) ? "" : driverordertable.getName());
            tv_insurance_number.setText(driverordertable.getId() == null ? "" : driverordertable.getId() + "");
            tv_insurance_duration.setText(TimeUtil.getTimeString(driverordertable.getStartdate()) + "起，" + TimeUtil.getTimeString(driverordertable.getEnddate()) + "止");
            tv_insurance_scope.setText("这个地方服务端没有字段");
            tv_insure_price.setText("这个地方服务端也没有字段");
        }

    }

    private void setUpListener() {
        mTitleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                context.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });


        //购买的TextView 监听器
        tv_buy_insure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAgreeWithLicence) {
                    startActivity(new Intent(context, OrderCofirmOnDrivingInsureActivity.class));
                } else {
                    Toast.makeText(context, "请您勾选同意保障条款！", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //协议 点击事件
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

        //服务电话
        layout_server_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.takePhoneCall(MyInsuresListDrivingInsureDetailActivity.this, StringConstant.call_police_number, ACTION_CALL);
            }
        });


    }


}
