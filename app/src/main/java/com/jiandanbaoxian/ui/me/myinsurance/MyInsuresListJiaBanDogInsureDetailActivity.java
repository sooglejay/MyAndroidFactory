package com.jiandanbaoxian.ui.me.myinsurance;

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
import com.jiandanbaoxian.model.Overtimeordertable;
import com.jiandanbaoxian.model.jugeOvertimeInsuranceOrder;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.BrowserWebViewActivity;
import com.jiandanbaoxian.ui.LoginActivity;
import com.jiandanbaoxian.ui.buyinsurance.OrderCofirmJiaBanDogInsureActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.TimeUtil;
import com.jiandanbaoxian.widget.TitleBar;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的-我的保险-车险
 */
public class MyInsuresListJiaBanDogInsureDetailActivity extends BaseActivity {
    public final static String ExtrasKeyName = "ExtrasKeyName";
    private TitleBar mTitleBar;

    private Activity context;
    private ImageView iv_choose;
    private TextView tv_buy_insure;
    private boolean isAgreeWithLicence = true;

    private Overtimeordertable overtimeordertable;
    private LinearLayout layout_rule;

    private TextView tv_insurant_name;
    private TextView tv_insurance_duration;
    private TextView tv_insurance_agent_name;
    private TextView tv_company_address;
    private TextView tv_home_address;
    private TextView tv_coverage;//保险公司给你
    private TextView tv_money;//你给保险公司
    private TextView tv_reportdate;//出险时间

    public static void startActivity(Activity context, Overtimeordertable bean) {
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
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.initTitleBarInfo("产品详情", R.drawable.arrow_left, -1, "", "");


        tv_buy_insure = (TextView) findViewById(R.id.tv_buy_insure);
        tv_buy_insure.setEnabled(true);
        tv_buy_insure.setBackgroundResource(R.drawable.btn_select_base_shape_0);
        tv_buy_insure.setTextColor(getResources().getColor(R.color.white_color));


        tv_insurant_name = (TextView) findViewById(R.id.tv_insurant_name);
        tv_insurance_duration = (TextView) findViewById(R.id.tv_insurance_duration);
        tv_insurance_agent_name = (TextView) findViewById(R.id.tv_insurance_agent_name);
        tv_company_address = (TextView) findViewById(R.id.tv_company_address);
        tv_home_address = (TextView) findViewById(R.id.tv_home_address);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_coverage = (TextView) findViewById(R.id.tv_coverage);
        tv_reportdate = (TextView) findViewById(R.id.tv_reportdate);



        // 传递过来的model   并 显示
        overtimeordertable = getIntent().getParcelableExtra(ExtrasKeyName);
        if (overtimeordertable != null) {
            if (overtimeordertable.getUserid() != null) {
                tv_insurant_name.setText(overtimeordertable.getUserid() + "");
            }
            tv_insurance_duration.setText(TimeUtil.getTimeString(overtimeordertable.getStartdate()) + "起，" + TimeUtil.getTimeString(overtimeordertable.getEnddate()) + "止");
            tv_insurance_agent_name.setText(overtimeordertable.getCompanyname() + "");
            tv_company_address.setText(overtimeordertable.getCompanyaddress() + "");
            tv_home_address.setText(overtimeordertable.getHomeaddress() + "");
            tv_coverage.setText(overtimeordertable.getCoverage() + "");
            tv_money.setText(overtimeordertable.getMoney() + "");
            if (overtimeordertable.getReportdate() != null) {
                tv_reportdate.setText(TimeUtil.getTimeString(overtimeordertable.getReportdate()) + "");
            } else {
                tv_reportdate.setText("");
            }
        }

    }

    private void setUpListener() {
        mTitleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                finish();
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
                    String phone = PreferenceUtil.load(context, PreferenceConstant.phone, "");
                    if (TextUtils.isEmpty(phone)) {
                        Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, LoginActivity.class));
                    } else {
                        UserRetrofitUtil.jugeOvertimeInsuranceOrder(context, phone, new NetCallback<jugeOvertimeInsuranceOrder>(context) {
                                    @Override
                                    public void onFailure(RetrofitError error, String message) {
                                        if (TextUtils.isEmpty(message)) {
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void success(jugeOvertimeInsuranceOrder
                                                                jugeOvertimeInsuranceOrder, Response response) {
                                        String message = jugeOvertimeInsuranceOrder.getMessage();
                                        if (message.equals(StringConstant.buyed)) {
                                            Toast.makeText(context, "您已经买过加班险了，每周只能买一次，下周再来吧!", Toast.LENGTH_SHORT).show();
                                        } else if (overtimeordertable != null) {
                                            OrderCofirmJiaBanDogInsureActivity.startActivity(context, overtimeordertable);
                                        } else {
                                            Toast.makeText(context, "加保险信息为空！无法购买！", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                        );
                    }

                } else {
                    Toast.makeText(context, "请您勾选同意保障条款！", Toast.LENGTH_SHORT).show();
                }
            }
        });



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


        layout_rule = (LinearLayout) findViewById(R.id.layout_rule);
        layout_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BrowserWebViewActivity.startActivity(context, true);
            }
        });

    }

}
