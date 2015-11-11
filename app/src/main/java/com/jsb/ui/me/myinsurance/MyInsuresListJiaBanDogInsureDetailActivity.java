package com.jsb.ui.me.myinsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.model.Overtimeordertable;
import com.jsb.ui.BaseActivity;
import com.jsb.ui.buyinsurance.OrderCofirmOnDrivingInsureActivity;
import com.jsb.util.TimeUtil;
import com.jsb.widget.TitleBar;

/**
 * 我的-我的保险-车险
 */
public class MyInsuresListJiaBanDogInsureDetailActivity extends BaseActivity {
    public final static String ExtrasKeyName = "ExtrasKeyName";
    private TitleBar mTitleBar;

    private Activity context;
    private CheckBox cb_agree_license;
    private TextView tv_buy_insure;
    private boolean isAgreeWithLicence = true;

    private Overtimeordertable overtimeordertable;

    private TextView tv_insurant_name;
    private TextView tv_insurance_duration;
    private TextView tv_insurance_agent_name;
    private TextView tv_company_address;
    private TextView tv_home_address;
    private TextView tv_jbpf;

    public static void startActivity(Activity context,Overtimeordertable bean)
    {
        Intent intent = new Intent(context, MyInsuresListJiaBanDogInsureDetailActivity.class);
        intent.putExtra(ExtrasKeyName, bean);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_insure_jiabandog);
        context = this;

        setUpView();
        setUpListener();

    }

    private void setUpView() {
        mTitleBar = (TitleBar)findViewById(R.id.title_bar);
        mTitleBar.initTitleBarInfo("产品详情", R.drawable.arrow_left, -1, "", "");


        tv_buy_insure = (TextView) findViewById(R.id.tv_buy_insure);
        tv_buy_insure.setEnabled(true);
        tv_buy_insure.setBackgroundResource(R.drawable.btn_select_base_shape_0);
        tv_buy_insure.setTextColor(getResources().getColor(R.color.white_color));


        tv_insurant_name = (TextView)findViewById(R.id.tv_insurant_name);
        tv_insurance_duration = (TextView)findViewById(R.id.tv_insurance_duration);
        tv_insurance_agent_name = (TextView)findViewById(R.id.tv_insurance_agent_name);
        tv_company_address = (TextView)findViewById(R.id.tv_company_address);
        tv_home_address = (TextView)findViewById(R.id.tv_home_address);
        tv_jbpf = (TextView)findViewById(R.id.tv_jbpf);

        //许可协议
        cb_agree_license = (CheckBox) findViewById(R.id.cb_agree_license);
        cb_agree_license.setChecked(true);



        // 传递过来的model   并 显示
        overtimeordertable = getIntent().getParcelableExtra(MyInsuresListCarInsureDetailActivity.ExtrasKeyName);
        if (overtimeordertable != null) {
            if(overtimeordertable.getUserid()!=null)
            {
                tv_insurant_name.setText(overtimeordertable.getUserid()+"");
            }
            tv_insurance_duration.setText(TimeUtil.getTimeString(overtimeordertable.getStartdate()) + "起，" + TimeUtil.getTimeString(overtimeordertable.getEnddate()) + "止");
            tv_insurance_agent_name.setText("没有字段");
            tv_company_address.setText(overtimeordertable.getCompanyaddress()+"");
            tv_home_address.setText(overtimeordertable.getHomeaddress()+"");
            tv_jbpf.setText(overtimeordertable.getMoney()+"");
        }

    }

    private void setUpListener() {
        mTitleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyInsuresListJiaBanDogInsureDetailActivity.this.finish();
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


    }

}
