package com.jiandanbaoxian.ui.buyinsurance.car_insurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.ApplyPayBean;
import com.jiandanbaoxian.model.CommPriceData;
import com.jiandanbaoxian.model.ConfirmOrderBean;
import com.jiandanbaoxian.model.HuanInsuranceBaseInfoData;
import com.jiandanbaoxian.model.HuanPriceData;
import com.jiandanbaoxian.model.HuanQueryStatusData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.BrowserWebViewActivity;
import com.jiandanbaoxian.util.CarNetCrashHandler;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sooglejay on 16/2/28.
 */
public class ComfirmOrderActivity extends BaseActivity {

    private Activity activity;
    private CommPriceData commPriceData;
    private boolean isValidToPay = false;
    private String city_no;
    long commercestartdate = 0;
    long compulsorystartdate = 0;

    private ProgressDialogUtil progressDialogUtil;

    private boolean isPayed = false;
    private String idcard_number = "";
    private String orderid = "";
    private String cal_app_no = "";
    private TitleBar titleBar;
    private EditText etInsureUserName;
    private EditText etInsureUserPhoneNumber;
    private EditText etInsureOrderReceiveUserName;
    private EditText etInsureOrderReceiveUserPhoneNumber;
    private EditText etInsureOrderReceiveUserAddress;
    private EditText etInsureRecommendUserPhone;
    private EditText etInsureOperationUserPhone;
    private LinearLayout layoutCommercialDatePicker;
    private TextView tvCommercialTotalPremium;
    private LinearLayout layoutCompulsoryDatePicker;
    private TextView tvCompulsoryTotalPremium;
    private TextView tvTotalPrice;
    private TextView tvPay;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-03-04 16:28:37 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        etInsureUserName = (EditText) findViewById(R.id.et_insure_user_name);
        etInsureUserPhoneNumber = (EditText) findViewById(R.id.et_insure_user_phone_number);
        etInsureOrderReceiveUserName = (EditText) findViewById(R.id.et_insure_order_receive_user_name);
        etInsureOrderReceiveUserPhoneNumber = (EditText) findViewById(R.id.et_insure_order_receive_user_phone_number);
        etInsureOrderReceiveUserAddress = (EditText) findViewById(R.id.et_insure_order_receive_user_address);
        etInsureRecommendUserPhone = (EditText) findViewById(R.id.et_insure_recommend_user_phone);
        etInsureOperationUserPhone = (EditText) findViewById(R.id.et_insure_operation_user_phone);
        layoutCommercialDatePicker = (LinearLayout) findViewById(R.id.layout_commercial_date_picker);
        tvCommercialTotalPremium = (TextView) findViewById(R.id.tv_commercial_total_premium);
        layoutCompulsoryDatePicker = (LinearLayout) findViewById(R.id.layout_compulsory_date_picker);
        tvCompulsoryTotalPremium = (TextView) findViewById(R.id.tv_compulsory_total_premium);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        tvPay = (TextView) findViewById(R.id.tv_pay);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        activity = this;
        progressDialogUtil = new ProgressDialogUtil(activity);

        commPriceData = getIntent().getParcelableExtra("CommPriceData");

        idcard_number = getIntent().getStringExtra("idcard_number");
        city_no = getIntent().getStringExtra("city_no");
        commercestartdate = getIntent().getLongExtra("commercestartdate", 0L);
        compulsorystartdate = getIntent().getLongExtra("compulsorystartdate", 0l);
        Log.e("qq", "commPriceData:" + commPriceData);
        Log.e("qq", "idcard_number:" + idcard_number);
        Log.e("qq", "city_no:" + city_no);

        findViews();
        setUpViews();
        setUpListener();
    }

    private void setUpListener() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commPriceData != null) {
                    final HuanPriceData huanPriceData = commPriceData.getHuanPriceData();
                    if (huanPriceData != null) {
                        HuanInsuranceBaseInfoData huanInsuranceBaseInfoData = huanPriceData.getCommerceBaseInfo();
                        cal_app_no = huanInsuranceBaseInfoData.getCal_app_no();
                        orderid = huanPriceData.getOrderId() + "";
                        final int type = 0;

                        final String insuranceUserName = etInsureUserName.getText().toString();
                        final String insuranceUserPhone = etInsureUserPhoneNumber.getText().toString();

                        final String insuranceReceiveUserName = etInsureOrderReceiveUserName.getText().toString();
                        final String insuranceReceivePhone = etInsureOrderReceiveUserPhoneNumber.getText().toString();
                        final String insuranceReceiveAddress = etInsureOrderReceiveUserAddress.getText().toString();

                        final String insuranceRecommendUserPhone = etInsureRecommendUserPhone.getText().toString();
                        final String insuranceOperationUserPhone = etInsureOperationUserPhone.getText().toString();

                        progressDialogUtil.show("正在生成订单号...");

                        isPayed = false;
                        UserRetrofitUtil.huanAuditInsuranceOrder(activity, insuranceUserName, insuranceUserPhone, idcard_number, cal_app_no, type, new NetCallback<NetWorkResultBean<ConfirmOrderBean>>(activity) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {
                                progressDialogUtil.hide();


                                //如果核保失败就 尝试从本地读取 核保信息，然后去申请支付
                                String commercialNo = PreferenceUtil.load(activity, PreferenceConstant.confirmCommercialNo, "");
                                String compulsoryNo = PreferenceUtil.load(activity, PreferenceConstant.ConfirmCompulsoryNo, "");
                                String compulsoryAmount = PreferenceUtil.load(activity, PreferenceConstant.ConfirmCompulsoryAmount, "");
                                String commercialAmount = PreferenceUtil.load(activity, PreferenceConstant.ConfirmCommercialAmount, "");
                                String compulsoryStartDate = PreferenceUtil.load(activity, PreferenceConstant.ConfirmCompulsoryStartDate, "");
                                String commercialStartDate = PreferenceUtil.load(activity, PreferenceConstant.ConfirmCommercialStartDate, "");
                                String type = PreferenceUtil.load(activity, PreferenceConstant.ConfirmType, "");
                                String city_no = PreferenceUtil.load(activity, PreferenceConstant.ConfirmCityNo, "");

                                String insuranceUserName = PreferenceUtil.load(activity, PreferenceConstant.insuranceUserName, "");
                                String insuranceUserPhone = PreferenceUtil.load(activity, PreferenceConstant.insuranceUserPhone, "");
                                String insuranceReceiveUserName = PreferenceUtil.load(activity, PreferenceConstant.insuranceReceiveUserName, "");
                                String insuranceReceivePhone = PreferenceUtil.load(activity, PreferenceConstant.insuranceReceivePhone, "");
                                String insuranceReceiveAddress = PreferenceUtil.load(activity, PreferenceConstant.insuranceReceiveAddress, "");
                                String insuranceRecommendUserPhone = PreferenceUtil.load(activity, PreferenceConstant.insuranceRecommendUserPhone, "");
                                String insuranceOperationUserPhone = PreferenceUtil.load(activity, PreferenceConstant.insuranceOperationUserPhone, "");


                                if (!TextUtils.isEmpty(commercialNo)) {

                                    isPayed = false;
                                    UserRetrofitUtil.huanApplyPay(activity, insuranceUserName, compulsoryNo, compulsoryAmount + "", commercialNo, commercialAmount + "", city_no, compulsoryStartDate + "", commercialStartDate + "", type + "", new NetCallback<NetWorkResultBean<ApplyPayBean>>(activity) {
                                        @Override
                                        public void onFailure(RetrofitError error, String message) {
                                            progressDialogUtil.hide();
                                            isPayed = false;

                                        }

                                        @Override
                                        public void success(NetWorkResultBean<ApplyPayBean> stringNetWorkResultBean, Response response) {
                                            progressDialogUtil.hide();
                                            isPayed = true;
                                            try {
                                                String ply_app_no = stringNetWorkResultBean.getData().getPly_app_no();
                                                BrowserWebViewActivity.startActivity(activity, "http://agenttest.sinosafe.com.cn/tstpayonline/recvMerchantAction.do?orderId=" + ply_app_no, "申请支付");
                                            } catch (Exception e) {
                                                Toast.makeText(activity, "支付失败！获取不到订单号!请转人工服务！", Toast.LENGTH_SHORT).show();
                                                CarNetCrashHandler.getInstance().savaInfoToSD(activity, e);
                                                CarNetCrashHandler.getInstance().savaInfoToSD(activity, response.toString());

                                            }
                                        }
                                    });


                                }


                            }

                            @Override
                            public void success(final NetWorkResultBean<ConfirmOrderBean> confirmOrderBeanNetWorkResultBean, Response response) {

                                if (confirmOrderBeanNetWorkResultBean != null) {
                                    final ConfirmOrderBean bean = confirmOrderBeanNetWorkResultBean.getData();

                                    PreferenceUtil.save(activity, PreferenceConstant.confirmCommercialNo, bean.getCommerceNo());
                                    PreferenceUtil.save(activity, PreferenceConstant.ConfirmCompulsoryNo, bean.getCompulsoryNo());
                                    PreferenceUtil.save(activity, PreferenceConstant.ConfirmCompulsoryAmount, huanPriceData.getCompulsoryAmount() + "");
                                    PreferenceUtil.save(activity, PreferenceConstant.ConfirmCommercialAmount, huanPriceData.getCommerceAmount() + "");
                                    PreferenceUtil.save(activity, PreferenceConstant.ConfirmCityNo, city_no + "");
                                    PreferenceUtil.save(activity, PreferenceConstant.ConfirmCommercialStartDate, commercestartdate + "");
                                    PreferenceUtil.save(activity, PreferenceConstant.ConfirmCompulsoryStartDate, compulsorystartdate + "");
                                    PreferenceUtil.save(activity, PreferenceConstant.ConfirmType, type + "");
                                    PreferenceUtil.save(activity, PreferenceConstant.ConfirmCalAppNo, cal_app_no + "");


                                    String insuranceUserName = etInsureUserName.getText().toString();
                                    String insuranceUserPhone = etInsureUserPhoneNumber.getText().toString();

                                    String insuranceReceiveUserName = etInsureOrderReceiveUserName.getText().toString();
                                    String insuranceReceivePhone = etInsureOrderReceiveUserPhoneNumber.getText().toString();
                                    String insuranceReceiveAddress = etInsureOrderReceiveUserAddress.getText().toString();

                                    String insuranceRecommendUserPhone = etInsureRecommendUserPhone.getText().toString();
                                    String insuranceOperationUserPhone = etInsureOperationUserPhone.getText().toString();

                                    PreferenceUtil.save(activity, PreferenceConstant.insuranceUserName, insuranceUserName);
                                    PreferenceUtil.save(activity, PreferenceConstant.insuranceUserPhone, insuranceUserPhone);
                                    PreferenceUtil.save(activity, PreferenceConstant.insuranceReceiveUserName, insuranceReceiveUserName);
                                    PreferenceUtil.save(activity, PreferenceConstant.insuranceReceivePhone, insuranceReceivePhone);
                                    PreferenceUtil.save(activity, PreferenceConstant.insuranceReceiveAddress, insuranceReceiveAddress);
                                    PreferenceUtil.save(activity, PreferenceConstant.insuranceRecommendUserPhone, insuranceRecommendUserPhone);
                                    PreferenceUtil.save(activity, PreferenceConstant.insuranceOperationUserPhone, insuranceOperationUserPhone);


                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            CarNetCrashHandler.getInstance().savaInfoToSD(activity, "commercialNo:" + bean.getCommerceNo() + "\n compulsoryNo:" + bean.getCompulsoryNo());
                                        }
                                    }).start();


                                    UserRetrofitUtil.huanApplyPay(activity, insuranceUserName, bean.getCompulsoryNo(), huanPriceData.getCompulsoryAmount() + "", bean.getCommerceNo(), huanPriceData.getCommerceAmount() + "", city_no, compulsorystartdate + "", commercestartdate + "", type + "", new NetCallback<NetWorkResultBean<ApplyPayBean>>(activity) {
                                        @Override
                                        public void onFailure(RetrofitError error, String message) {
                                            progressDialogUtil.hide();
                                            isPayed = false;

                                        }

                                        @Override
                                        public void success(NetWorkResultBean<ApplyPayBean> stringNetWorkResultBean, final Response response) {
                                            progressDialogUtil.hide();
                                            isPayed = true;
                                            try {
                                                String ply_app_no = stringNetWorkResultBean.getData().getPly_app_no();
                                                BrowserWebViewActivity.startActivity(activity, "http://agenttest.sinosafe.com.cn/tstpayonline/recvMerchantAction.do?orderId=" + ply_app_no, "申请支付");
                                            } catch (final Exception e) {
                                                Toast.makeText(activity, "支付失败！获取不到订单号!请转人工服务！", Toast.LENGTH_SHORT).show();
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        CarNetCrashHandler.getInstance().savaInfoToSD(activity, e);
                                                        CarNetCrashHandler.getInstance().savaInfoToSD(activity, response.toString());
                                                    }
                                                }).start();


                                            }
                                        }
                                    });
                                } else {
                                    progressDialogUtil.hide();

                                }


                            }
                        });

                    }
                }
            }
        });
    }

    private void setUpViews() {
        titleBar.initTitleBarInfo("确认投保信息", R.drawable.arrow_left, -1, "", "");

        tvPay.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tvPay.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));


        etInsureOrderReceiveUserAddress.addTextChangedListener(textWatcher);
        etInsureOrderReceiveUserName.addTextChangedListener(textWatcher);
        etInsureOrderReceiveUserPhoneNumber.addTextChangedListener(textWatcher);

        etInsureOperationUserPhone.addTextChangedListener(textWatcher);
        etInsureRecommendUserPhone.addTextChangedListener(textWatcher);

        etInsureUserName.addTextChangedListener(textWatcher);
        etInsureUserPhoneNumber.addTextChangedListener(textWatcher);


        try {
            tvCommercialTotalPremium.setText(commPriceData.getHuanPriceData().getCommerceAmount() + "");
            tvCompulsoryTotalPremium.setText(commPriceData.getHuanPriceData().getCompulsoryAmount() + "");
        } catch (Exception e) {

        }

    }


    public static void startActivity(Activity activity, CommPriceData commPriceData, String idcard_number, String city_no, long commercestartdate, long compulsorystartdate) {
        Intent intent = new Intent(activity, ComfirmOrderActivity.class);
        intent.putExtra("CommPriceData", commPriceData);
        intent.putExtra("idcard_number", idcard_number);
        intent.putExtra("city_no", city_no);
        intent.putExtra("compulsorystartdate", compulsorystartdate);
        intent.putExtra("commercestartdate", commercestartdate);
        activity.startActivity(intent);
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (TextUtils.isEmpty(etInsureOrderReceiveUserAddress.getText().toString()) ||
                    TextUtils.isEmpty(etInsureOrderReceiveUserName.getText().toString()) ||
                    TextUtils.isEmpty(etInsureOrderReceiveUserPhoneNumber.getText().toString()) ||

                    TextUtils.isEmpty(etInsureRecommendUserPhone.getText().toString()) ||
                    TextUtils.isEmpty(etInsureOperationUserPhone.getText().toString()) ||
                    TextUtils.isEmpty(etInsureUserName.getText().toString()) ||
                    TextUtils.isEmpty(etInsureUserPhoneNumber.getText().toString())) {

                isValidToPay = false;
                tvPay.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                tvPay.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                isValidToPay = true;
                tvPay.setBackgroundResource(R.drawable.btn_select_base);
                tvPay.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        String insuranceUserName = etInsureUserName.getText().toString();
        String insuranceUserPhone = etInsureUserPhoneNumber.getText().toString();

        String insuranceReceiveUserName = etInsureOrderReceiveUserName.getText().toString();
        String insuranceReceivePhone = etInsureOrderReceiveUserPhoneNumber.getText().toString();
        String insuranceReceiveAddress = etInsureOrderReceiveUserAddress.getText().toString();

        String insuranceRecommendUserPhone = etInsureRecommendUserPhone.getText().toString();
        String insuranceOperationUserPhone = etInsureOperationUserPhone.getText().toString();

        PreferenceUtil.save(this, PreferenceConstant.insuranceUserName, insuranceUserName);
        PreferenceUtil.save(this, PreferenceConstant.insuranceUserPhone, insuranceUserPhone);
        PreferenceUtil.save(this, PreferenceConstant.insuranceReceiveUserName, insuranceReceiveUserName);
        PreferenceUtil.save(this, PreferenceConstant.insuranceReceivePhone, insuranceReceivePhone);
        PreferenceUtil.save(this, PreferenceConstant.insuranceReceiveAddress, insuranceReceiveAddress);
        PreferenceUtil.save(this, PreferenceConstant.insuranceRecommendUserPhone, insuranceRecommendUserPhone);
        PreferenceUtil.save(this, PreferenceConstant.insuranceOperationUserPhone, insuranceOperationUserPhone);
    }

    @Override
    protected void onResume() {
        super.onResume();


        final String insuranceUserName = PreferenceUtil.load(this, PreferenceConstant.insuranceUserName, "");
        final String insuranceUserPhone = PreferenceUtil.load(this, PreferenceConstant.insuranceUserPhone, "");

        final String insuranceReceiveUserName = PreferenceUtil.load(this, PreferenceConstant.insuranceReceiveUserName, "");
        final String insuranceReceivePhone = PreferenceUtil.load(this, PreferenceConstant.insuranceReceivePhone, "");
        final String insuranceReceiveAddress = PreferenceUtil.load(this, PreferenceConstant.insuranceReceiveAddress, "");

        final String insuranceRecommendUserPhone = PreferenceUtil.load(this, PreferenceConstant.insuranceRecommendUserPhone, "");
        final String insuranceOperationUserPhone = PreferenceUtil.load(this, PreferenceConstant.insuranceOperationUserPhone, "");


        etInsureOrderReceiveUserAddress.setText(insuranceReceiveAddress);
        etInsureOrderReceiveUserName.setText(insuranceUserName);
        etInsureOrderReceiveUserPhoneNumber.setText(insuranceReceivePhone);
        etInsureOperationUserPhone.setText(insuranceOperationUserPhone);
        etInsureRecommendUserPhone.setText(insuranceRecommendUserPhone);
        etInsureUserName.setText(insuranceReceiveUserName);
        etInsureUserPhoneNumber.setText(insuranceUserPhone);


        if (isPayed) {
            String commercialNo = PreferenceUtil.load(activity, PreferenceConstant.confirmCommercialNo, "");
            String compulsoryNo = PreferenceUtil.load(activity, PreferenceConstant.ConfirmCompulsoryNo, "");
            final String type = PreferenceUtil.load(activity, PreferenceConstant.ConfirmType, "");

            UserRetrofitUtil.queryStatus(activity, compulsoryNo, commercialNo, type, new NetCallback<NetWorkResultBean<HuanQueryStatusData>>(activity) {
                @Override
                public void onFailure(RetrofitError error, String message) {

                }

                @Override
                public void success(NetWorkResultBean<HuanQueryStatusData> huanQueryStatusDataNetWorkResultBean, Response response) {

                    if (huanQueryStatusDataNetWorkResultBean != null) {
                        HuanQueryStatusData bean = huanQueryStatusDataNetWorkResultBean.getData();
                        int status = bean.getPay_status();
                        String des = "";
                        switch (status) {

//                            pay_status	int	改单状态码：-2 待核保;-1 预核保待付费;0 未确认未收款;1 已确认未收款;5 已确认已收款.	Y

                            case -2:
                                des = "待核保";
                                break;
                            case -1:
                                des = "预核保待付费";
                                break;
                            case 0:
                                des = "未确认未收款";
                                break;
                            case 1:
                                des = "已确认未收款";
                                break;
                            case 5:
                                des = "已确认已收款";

                                UserRetrofitUtil.confirmVehicleOrder(activity, insuranceUserName, orderid, insuranceUserPhone, idcard_number, insuranceReceiveUserName, insuranceReceivePhone, insuranceReceiveAddress, insuranceRecommendUserPhone, insuranceOperationUserPhone, city_no, type, cal_app_no, new NetCallback<NetWorkResultBean<ConfirmOrderBean>>(activity) {
                                    @Override
                                    public void onFailure(RetrofitError error, String message) {

                                    }

                                    @Override
                                    public void success(NetWorkResultBean<ConfirmOrderBean> confirmOrderBeanNetWorkResultBean, Response response) {
                                        Toast.makeText(activity, "confirmVehicleOrder 本地数据库核保成功！", Toast.LENGTH_SHORT).show();

                                        UserRetrofitUtil.selectPlan(activity, commPriceData.getHuanPriceData().getPriceId() + "", orderid + "", new NetCallback<NetWorkResultBean<String>>(activity) {
                                            @Override
                                            public void onFailure(RetrofitError error, String message) {

                                            }

                                            @Override
                                            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                                Toast.makeText(activity, "selectPlan 本地数据库 标志位成功！", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                });
                                break;

                        }
                        if (!TextUtils.isEmpty(des))
                            Toast.makeText(activity, des, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

}
