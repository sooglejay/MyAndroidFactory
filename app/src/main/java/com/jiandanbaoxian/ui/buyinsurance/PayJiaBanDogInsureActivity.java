package com.jiandanbaoxian.ui.buyinsurance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.InsuranceType;
import com.jiandanbaoxian.constant.PaymentChannel;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.Charge;
import com.jiandanbaoxian.model.ChargeBean;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Overtimeordertable;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.IpUtil;
import com.jiandanbaoxian.util.JsonUtil;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;
import com.pingplusplus.android.PaymentActivity;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 买保险-加班狗-确认支付信息
 */
public class PayJiaBanDogInsureActivity extends BaseActivity implements View.OnClickListener {
    private static final String ExtraKey = "ExtraKey";
    private static final int REQUEST_CODE_PAYMENT = 1;


    private TitleBar titleBar;
    private TextView tv_confirm_pay;

    private LinearLayout layout_union_card;
    private LinearLayout layout_wx;
    private LinearLayout layout_zfb;


    private ImageView iv_choose_zfb;
    private ImageView iv_choose_wx;
    private ImageView iv_choose_union_card;

    private PaymentChannel channel = PaymentChannel.CHANNEL_ALIPAY;//支付的方式,默认是支付宝

    private Overtimeordertable overtimeordertable;//加班险的具体信息

    private ProgressDialogUtil progressDialogUtil;

    public static void startActivity(Activity context, Overtimeordertable overtimeordertable) {
        Intent intent = new Intent(context, PayJiaBanDogInsureActivity.class);
        intent.putExtra(ExtraKey, overtimeordertable);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_jiaban_dog);
        overtimeordertable = getIntent().getParcelableExtra(ExtraKey);
        progressDialogUtil = new ProgressDialogUtil(this);
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
        tv_confirm_pay.setOnClickListener(this);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("支付", R.drawable.arrow_left, -1, "", "");

        layout_union_card = (LinearLayout) findViewById(R.id.layout_union_card);
        layout_wx = (LinearLayout) findViewById(R.id.layout_wx);
        layout_zfb = (LinearLayout) findViewById(R.id.layout_zfb);
        layout_union_card.setOnClickListener(this);
        layout_wx.setOnClickListener(this);
        layout_zfb.setOnClickListener(this);

        iv_choose_zfb = (ImageView) findViewById(R.id.iv_choose_zfb);
        iv_choose_wx = (ImageView) findViewById(R.id.iv_choose_wx);
        iv_choose_union_card = (ImageView) findViewById(R.id.iv_choose_union_card);

        iv_choose_zfb.setVisibility(View.VISIBLE);
        iv_choose_wx.setVisibility(View.GONE);
        iv_choose_union_card.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_union_card://银联
                iv_choose_union_card.setVisibility(View.VISIBLE);
                iv_choose_wx.setVisibility(View.GONE);
                iv_choose_zfb.setVisibility(View.GONE);
                channel = PaymentChannel.CHANNEL_UPACP;
                break;
            case R.id.layout_wx://微信
                iv_choose_union_card.setVisibility(View.GONE);
                iv_choose_wx.setVisibility(View.VISIBLE);
                iv_choose_zfb.setVisibility(View.GONE);
                channel = PaymentChannel.CHANNEL_WECHAT;
                break;
            case R.id.layout_zfb://支付宝
                iv_choose_union_card.setVisibility(View.GONE);
                iv_choose_wx.setVisibility(View.GONE);
                iv_choose_zfb.setVisibility(View.VISIBLE);
                channel = PaymentChannel.CHANNEL_ALIPAY;
                break;
            case R.id.tv_confirm_pay:
                progressDialogUtil.show("正在处理...");
                if (overtimeordertable == null) {
                    progressDialogUtil.hide();
                    Toast.makeText(this, "订单为空！无法支付", Toast.LENGTH_LONG).show();
                    return;

                }
                if (overtimeordertable.getId() == null) {
                    progressDialogUtil.hide();
                    Toast.makeText(this, "订单id为空！无法支付", Toast.LENGTH_LONG).show();
                    return;
                }
                int overtimeInsuranceId = overtimeordertable.getId();
                float fee = 1.00f;
                String phone = PreferenceUtil.load(this, PreferenceConstant.phone, "");
                String channelStr = "";
                switch (channel) {
                    case CHANNEL_WECHAT:
                        channelStr = StringConstant.CHANNEL_WECHAT;
                        break;
                    case CHANNEL_ALIPAY:
                        channelStr = StringConstant.CHANNEL_ALIPAY;
                        break;
                    case CHANNEL_UPACP:
                        channelStr = StringConstant.CHANNEL_UPACP;
                        break;
                    default:
                        break;
                }

                if (overtimeordertable.getId() != null) {
                    overtimeInsuranceId = overtimeordertable.getId();
                }
                if (overtimeordertable.getMoney() != null) {
                    fee = overtimeordertable.getMoney();
                }

                InsuranceType type = InsuranceType.OVERTIME;
                UserRetrofitUtil.getCharge(this, overtimeInsuranceId, fee, phone,
                        type.ordinal(), channelStr, IpUtil.getIPAddress(true), new NetCallback<NetWorkResultBean<Object>>(this) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {
                                progressDialogUtil.hide();
                                Toast.makeText(PayJiaBanDogInsureActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void success(NetWorkResultBean<Object> chargeNetWorkResultBean, Response response) {
                                progressDialogUtil.hide();


                                if (chargeNetWorkResultBean != null) {
                                    int status = chargeNetWorkResultBean.getStatus();
                                    switch (status) {
                                        case HttpsURLConnection.HTTP_OK:
                                            String message = chargeNetWorkResultBean.getMessage().toString();
                                            if (message.equals("buyed")) {
                                                Toast.makeText(PayJiaBanDogInsureActivity.this, "您已经买过加班险了，每周只能买一次，下周再来吧！等你哟！", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if (chargeNetWorkResultBean.getData() != null) {
                                                ChargeBean bean = JsonUtil.getSerializedObject(chargeNetWorkResultBean.getData(), ChargeBean.class);
                                                Intent intent = new Intent();
                                                String packageName = getPackageName();
                                                ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
                                                intent.setComponent(componentName);
                                                String json = new Gson().toJson(bean.getCharge());
                                                intent.putExtra(PaymentActivity.EXTRA_CHARGE, json);
                                                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                                            }

                                            break;
                                        default:
                                            Toast.makeText(PayJiaBanDogInsureActivity.this, chargeNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }


                            }
                        });
                break;
            default:
                break;
        }
    }


    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                showMsg(result, errorMsg, extraMsg);
            }
        }
    }

    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        if (null != msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null != msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(PayJiaBanDogInsureActivity.this);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("支付成功！", null);
        builder.create().show();
    }


}
