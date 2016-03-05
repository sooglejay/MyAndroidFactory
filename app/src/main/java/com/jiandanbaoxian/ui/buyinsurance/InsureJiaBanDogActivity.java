package com.jiandanbaoxian.ui.buyinsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.OvertimeData;
import com.jiandanbaoxian.model.Overtimeinsurance;
import com.jiandanbaoxian.model.jugeOvertimeInsuranceOrder;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.BrowserImageViewActivity;
import com.jiandanbaoxian.ui.BrowserWebViewActivity;
import com.jiandanbaoxian.ui.LoginActivity;
import com.jiandanbaoxian.util.JsonUtil;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 买保险-加班狗
 */
public class InsureJiaBanDogActivity extends BaseActivity {
    private TitleBar titleBar;
    private ImageView iv_choose;
    private TextView tv_rule;
    private LinearLayout layout_rule;
    private LinearLayout layout_coverage;
    private TextView tv_buy_insure;
    private TextView tv_time_shengxiao;
    private TextView tv_amount;
    private TextView tv_coverage;


    private boolean isAgreeWithLicence = true;
    private SimpleDateFormat df_yyyy_m_d = new SimpleDateFormat("yyyy-MM-dd");

    private ProgressDialogUtil progressDialogUtil;

    private OvertimeData overtimeData;// 加班险 的具体信息对象

    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insure_jiaban_dog);
        progressDialogUtil = new ProgressDialogUtil(this, true);
        context = this;
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                context.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        tv_time_shengxiao = (TextView) findViewById(R.id.tv_time_shengxiao);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_rule = (TextView) findViewById(R.id.tv_rule);
        layout_rule = (LinearLayout) findViewById(R.id.layout_rule);
        layout_coverage = (LinearLayout) findViewById(R.id.layout_coverage);
        layout_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BrowserWebViewActivity.startActivity(context, true);

            }
        });
        //加班险规则
        layout_coverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BrowserImageViewActivity.startActivity(context, StringConstant.OvertimeRule, "加班险规则");
            }
        });
        tv_coverage = (TextView) findViewById(R.id.tv_coverage);//加班赔付
        tv_buy_insure = (TextView) findViewById(R.id.tv_buy_insure);
        tv_buy_insure.setEnabled(true);
        tv_buy_insure.setBackgroundResource(R.drawable.btn_select_base_shape_0);
        tv_buy_insure.setTextColor(getResources().getColor(R.color.white_color));
        tv_buy_insure.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 if (isAgreeWithLicence) {


                                                     String phone = PreferenceUtil.load(context, PreferenceConstant.phone, "");
                                                     if (TextUtils.isEmpty(phone)) {
                                                         Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show();
                                                         startActivity(new Intent(context, LoginActivity.class));
                                                     } else {
                                                         UserRetrofitUtil.jugeOvertimeInsuranceOrder(context, phone, new NetCallback<NetWorkResultBean<Object>>(context) {
                                                                     @Override
                                                                     public void onFailure(RetrofitError error, String message) {
                                                                         Toast.makeText(context, "请检查网络设置", Toast.LENGTH_SHORT).show();

                                                                     }

                                                                     @Override
                                                                     public void success(NetWorkResultBean<Object> jugeOvertimeInsuranceOrder, Response response) {

                                                                         if (jugeOvertimeInsuranceOrder != null) {
                                                                             int status = jugeOvertimeInsuranceOrder.getStatus();
                                                                             switch (status) {
                                                                                 case HttpsURLConnection.HTTP_OK:
                                                                                     String message = jugeOvertimeInsuranceOrder.getMessage().toString();
                                                                                     if (message.equals(StringConstant.buyed)) {
                                                                                         Toast.makeText(context, "您已经买过加班险了，每周只能买一次，下周再来吧！等你哟！", Toast.LENGTH_SHORT).show();
                                                                                     } else if (overtimeData != null) {
                                                                                         OrderCofirmJiaBanDogInsureActivity.startActivity(context, overtimeData);
                                                                                     } else {
                                                                                         Toast.makeText(context, "加保险信息为空！无法购买！", Toast.LENGTH_SHORT).show();
                                                                                     }
                                                                                     break;
                                                                                 default:
                                                                                     Toast.makeText(context, jugeOvertimeInsuranceOrder.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                                                     break;
                                                                             }
                                                                         }


                                                                     }
                                                                 }

                                                         );

                                                     }

                                                 } else {
                                                     Toast.makeText(context, "请您勾选同意保障条款！", Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         }

        );
        titleBar = (TitleBar) findViewById(R.id.title_bar);

        titleBar.initTitleBarInfo("加班狗", R.drawable.arrow_left, -1, "", "");
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

        progressDialogUtil.show("正在获取加班险信息...");
        UserRetrofitUtil.getOvertimeInsuranceInfo(this, new NetCallback<NetWorkResultBean<Object>>(context)

                {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        progressDialogUtil.hide();
                        Toast.makeText(context, "请检查网络设置", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void success
                            (NetWorkResultBean<Object> overtimeinsuranceNetWorkResultBean, Response
                                    response) {
                        progressDialogUtil.hide();


                        if (overtimeinsuranceNetWorkResultBean != null) {
                            int status = overtimeinsuranceNetWorkResultBean.getStatus();
                            switch (status) {
                                case HttpsURLConnection.HTTP_OK:
                                    if (overtimeinsuranceNetWorkResultBean.getData() != null) {
                                        overtimeData = JsonUtil.getSerializedObject(overtimeinsuranceNetWorkResultBean.getData(),OvertimeData.class);
                                    }
                                    Overtimeinsurance bean = overtimeData.getOvertimeInsurance();
                                    if (bean != null && bean.getReleasetime() != null) {
                                        //生效时间
                                        tv_time_shengxiao.setText("生效时间：" + df_yyyy_m_d.format(new Date(bean.getReleasetime())) + "");
                                    } else {
                                        tv_time_shengxiao.setText("生效时间：" + df_yyyy_m_d.format(new Date()) + "");
                                    }
                                    if (bean.getResidue() != null) {
                                        tv_amount.setText("本期商品数还剩" + bean.getResidue() + "份");
                                    } else {
                                        tv_amount.setText("本期商品数还剩" + 0 + "份");
                                    }
                                    if (bean != null && bean.getCoverage() != null) {
                                        tv_coverage.append("" + bean.getCoverage() + "元");
                                    }
                                    break;
                                default:
                                    Toast.makeText(context, overtimeinsuranceNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }


                    }
                }

        );
    }
}
