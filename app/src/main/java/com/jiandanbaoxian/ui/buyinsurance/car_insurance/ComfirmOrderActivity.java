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
import com.jiandanbaoxian.util.JsonUtil;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;

import net.sf.json.JSON;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sooglejay on 16/2/28.
 */
public class ComfirmOrderActivity extends BaseActivity {

    private Activity activity;
    private CommPriceData commPriceData;
    private boolean isValidToPay = false;
    private String province_no;
    private String city_no;
    private String country_no;
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
        province_no = getIntent().getStringExtra("province_no");
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

                        UserRetrofitUtil.huanAuditInsuranceOrder(activity, insuranceUserName, insuranceUserPhone, idcard_number, cal_app_no, type, new NetCallback<NetWorkResultBean<Object>>(activity) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {
                                progressDialogUtil.hide();
                            }

                            @Override
                            public void success(NetWorkResultBean<Object> objectNetWorkResultBean, Response response) {
                                progressDialogUtil.hide();
                                if (objectNetWorkResultBean != null) {
                                    int status = objectNetWorkResultBean.getStatus();
                                    switch (status) {
                                        case 200://请求成功！
                                            if (objectNetWorkResultBean.getData() instanceof ConfirmOrderBean) {
                                                final ConfirmOrderBean bean = JsonUtil.getSerializedObject(objectNetWorkResultBean.getData(),ConfirmOrderBean.class);

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


                                                isPayed = false;
                                                UserRetrofitUtil.huanApplyPay(activity, insuranceUserName, bean.getCompulsoryNo(), huanPriceData.getCompulsoryAmount() + "", bean.getCommerceNo(), huanPriceData.getCommerceAmount() + "", city_no, compulsorystartdate + "", commercestartdate + "", type + "", new NetCallback<NetWorkResultBean<Object>>(activity) {
                                                    @Override
                                                    public void onFailure(RetrofitError error, String message) {
                                                        progressDialogUtil.hide();

                                                    }

                                                    @Override
                                                    public void success(NetWorkResultBean<Object> stringNetWorkResultBean, final Response response) {

                                                        if (stringNetWorkResultBean != null) {
                                                            progressDialogUtil.hide();
                                                            isPayed = true;

                                                            int status = stringNetWorkResultBean.getStatus();
                                                            switch (status) {
                                                                case 200://请求成功！
                                                                    if (stringNetWorkResultBean.getData() instanceof ApplyPayBean) {
                                                                        ApplyPayBean bean = JsonUtil.getSerializedObject(stringNetWorkResultBean.getData(),ApplyPayBean.class);
                                                                        String ply_app_no = bean.getPly_app_no();
                                                                        BrowserWebViewActivity.startActivity(activity, "http://agenttest.sinosafe.com.cn/tstpayonline/recvMerchantAction.do?orderId=" + ply_app_no, "申请支付");
                                                                    }
                                                                    break;
                                                                default:
                                                                    Toast.makeText(activity, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                                    break;
                                                            }


                                                        }

                                                    }
                                                });
                                            }
                                            break;
                                        default:
                                            //请求失败
                                            Toast.makeText(activity, objectNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                            break;
                                    }

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


    public static void startActivity(Activity activity, CommPriceData commPriceData, String idcard_number, String city_no, String province_no, long commercestartdate, long compulsorystartdate) {
        Intent intent = new Intent(activity, ComfirmOrderActivity.class);
        intent.putExtra("CommPriceData", commPriceData);
        intent.putExtra("idcard_number", idcard_number);
        intent.putExtra("city_no", city_no);
        intent.putExtra("province_no", province_no);
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
            final String commercialNo = PreferenceUtil.load(activity, PreferenceConstant.confirmCommercialNo, "");
            final String compulsoryNo = PreferenceUtil.load(activity, PreferenceConstant.ConfirmCompulsoryNo, "");
            final String type = PreferenceUtil.load(activity, PreferenceConstant.ConfirmType, "");

            UserRetrofitUtil.queryStatus(activity, compulsoryNo, commercialNo, type, new NetCallback<NetWorkResultBean<Object>>(activity) {
                @Override
                public void onFailure(RetrofitError error, String message) {

                }

                @Override
                public void success(NetWorkResultBean<Object> huanQueryStatusDataNetWorkResultBean, Response response) {

                    if (huanQueryStatusDataNetWorkResultBean != null) {
                        int httpStatus = huanQueryStatusDataNetWorkResultBean.getStatus();
                        switch (httpStatus) {
                            case 200: {

                                if (huanQueryStatusDataNetWorkResultBean.getData() instanceof HuanInsuranceBaseInfoData) {
                                    HuanQueryStatusData bean = JsonUtil.getSerializedObject(huanQueryStatusDataNetWorkResultBean.getData(),HuanQueryStatusData.class);
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

                                            UserRetrofitUtil.confirmVehicleOrder(activity, insuranceUserName, orderid, insuranceUserPhone, idcard_number, insuranceReceiveUserName, insuranceReceivePhone, insuranceReceiveAddress, insuranceRecommendUserPhone, insuranceOperationUserPhone, city_no, type, cal_app_no, new NetCallback<NetWorkResultBean<Object>>(activity) {
                                                @Override
                                                public void onFailure(RetrofitError error, String message) {

                                                }

                                                @Override
                                                public void success(NetWorkResultBean<Object> confirmOrderBeanNetWorkResultBean, Response response) {

                                                    if (confirmOrderBeanNetWorkResultBean != null) {
                                                        int httpStatus = confirmOrderBeanNetWorkResultBean.getStatus();
                                                        switch (httpStatus) {
                                                            case 200: {
                                                                Toast.makeText(activity, confirmOrderBeanNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                            }
                                                            break;
                                                            default:
                                                                Toast.makeText(activity, confirmOrderBeanNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                                break;
                                                        }
                                                    }

                                                    UserRetrofitUtil.selectPlan(activity, commPriceData.getHuanPriceData().getPriceId() + "", orderid + "", new NetCallback<NetWorkResultBean<Object>>(activity) {
                                                        @Override
                                                        public void onFailure(RetrofitError error, String message) {

                                                        }

                                                        @Override
                                                        public void success(NetWorkResultBean<Object> stringNetWorkResultBean, Response response) {

                                                            if (stringNetWorkResultBean != null) {
                                                                int httpStatus = stringNetWorkResultBean.getStatus();
                                                                switch (httpStatus) {
                                                                    case 200: {
                                                                        Toast.makeText(activity, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                                    }
                                                                    break;
                                                                    default:
                                                                        Toast.makeText(activity, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                                        break;
                                                                }
                                                            }

                                                        }
                                                    });
                                                }
                                            });

                                            UserRetrofitUtil.huanDistribution(activity, compulsoryNo, commercialNo, insuranceReceiveAddress, insuranceReceivePhone, insuranceReceiveUserName, province_no, city_no, country_no, insuranceUserName, new NetCallback<NetWorkResultBean<Object>>(activity) {
                                                @Override
                                                public void onFailure(RetrofitError error, String message) {
                                                    Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();

                                                }

                                                @Override
                                                public void success(NetWorkResultBean<Object> huanQueryStatusDataNetWorkResultBean, Response response) {
                                                    if (huanQueryStatusDataNetWorkResultBean != null) {
                                                        int status = huanQueryStatusDataNetWorkResultBean.getStatus();
                                                        switch (status) {
                                                            case HttpsURLConnection.HTTP_OK:
                                                                Toast.makeText(activity, huanQueryStatusDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                                break;
                                                            default:
                                                                Toast.makeText(activity, huanQueryStatusDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                                break;
                                                        }
                                                    }
                                                }
                                            });

                                    }
                                    if (!TextUtils.isEmpty(des)) {
                                        Toast.makeText(activity, des, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            break;
                            default:
                                Toast.makeText(activity, huanQueryStatusDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });
        }
    }


}
