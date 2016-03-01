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
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.model.CommPriceData;
import com.jiandanbaoxian.model.ConfirmOrderBean;
import com.jiandanbaoxian.model.HuanInsuranceBaseInfoData;
import com.jiandanbaoxian.model.HuanPriceData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.ui.BaseActivity;
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
    private String country_no;
    long commercestartdate = 0;
    long compulsorystartdate = 0;

    private String idcard_number = "";
    private ProgressDialogUtil progressDialogUtil;
    private TitleBar titleBar;
    private EditText etInsureUserName;
    private EditText etInsureUserPhoneNumber;
    private EditText etInsureOrderReceiveUserName;
    private EditText etInsureOrderReceiveUserPhoneNumber;
    private EditText etInsureOrderReceiveUserAddress;
    private EditText etInsureRecommendUserPhone;
    private EditText etInsureOperationUserPhone;
    private TextView tvPay;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-02-28 15:19:19 by Android Layout Finder
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
        country_no = getIntent().getStringExtra("country_no");
        commercestartdate = getIntent().getLongExtra("commercestartdate", 0L);
        compulsorystartdate = getIntent().getLongExtra("compulsorystartdate", 0l);
        Log.e("qq", "commPriceData:" + commPriceData);
        Log.e("qq", "idcard_number:" + idcard_number);
        Log.e("qq", "country_no:" + country_no);

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
                    HuanPriceData huanPriceData = commPriceData.getHuanPriceData();
                    if (huanPriceData != null) {
                        HuanInsuranceBaseInfoData huanInsuranceBaseInfoData = huanPriceData.getCommerceBaseInfo();
                        String cal_app_no = huanInsuranceBaseInfoData.getCal_app_no();
                        String orderid = huanPriceData.getOrderId() + "";

                        final String insuranceUserName = etInsureUserName.getText().toString();
                        String insuranceUserPhone = etInsureUserPhoneNumber.getText().toString();

                        String insuranceReceiveUserName = etInsureOrderReceiveUserName.getText().toString();
                        String insuranceReceivePhone = etInsureOrderReceiveUserPhoneNumber.getText().toString();
                        String insuranceReceiveAddress = etInsureOrderReceiveUserAddress.getText().toString();

                        String insuranceRecommendUserPhone = etInsureRecommendUserPhone.getText().toString();
                        String insuranceOperationUserPhone = etInsureOperationUserPhone.getText().toString();

                        progressDialogUtil.show("正在生成订单号...");
                        UserRetrofitUtil.confirmVehicleOrder(activity, insuranceUserName, orderid, insuranceUserPhone, idcard_number, insuranceReceiveUserName, insuranceReceivePhone, insuranceReceiveAddress, insuranceRecommendUserPhone, insuranceOperationUserPhone, "四川省", 0, cal_app_no, new NetCallback<NetWorkResultBean<ConfirmOrderBean>>(activity) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {
                                progressDialogUtil.hide();
                            }

                            @Override
                            public void success(NetWorkResultBean<ConfirmOrderBean> confirmOrderBeanNetWorkResultBean, Response response) {
                                progressDialogUtil.hide();
                                if (confirmOrderBeanNetWorkResultBean != null && confirmOrderBeanNetWorkResultBean.getData() != null) {

                                    PayActivity.startActivity(activity, country_no, commercestartdate, compulsorystartdate, insuranceUserName, commPriceData, confirmOrderBeanNetWorkResultBean.getData());
                                }
                                else {
                                    Toast.makeText(activity,"返回值为空！",Toast.LENGTH_SHORT).show();
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


    }


    public static void startActivity(Activity activity, CommPriceData commPriceData, String idcard_number, String country_no, long commercestartdate, long compulsorystartdate) {
        Intent intent = new Intent(activity, ComfirmOrderActivity.class);
        intent.putExtra("CommPriceData", commPriceData);
        intent.putExtra("idcard_number", idcard_number);
        intent.putExtra("country_no", country_no);
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

}
