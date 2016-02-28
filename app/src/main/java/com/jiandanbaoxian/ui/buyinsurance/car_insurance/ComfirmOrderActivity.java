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

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.model.CommPriceData;
import com.jiandanbaoxian.model.HuanInsuranceBaseInfoData;
import com.jiandanbaoxian.model.HuanPriceData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.ui.BaseActivity;
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
    private String  idcard_number = "";
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
        commPriceData = getIntent().getParcelableExtra("CommPriceData");
        idcard_number = getIntent().getParcelableExtra("idcard_number");
        Log.e("qq","commPriceData:"+commPriceData);
        Log.e("qq","idcard_number:"+idcard_number);

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
                        String cal_app_no=huanInsuranceBaseInfoData.getCal_app_no();

                        String insuranceUserName = etInsureUserName.getText().toString();
                        String insuranceUserPhone = etInsureUserPhoneNumber.getText().toString();

                        String insuranceReceiveUserName = etInsureOrderReceiveUserName.getText().toString();
                        String insuranceReceivePhone = etInsureOrderReceiveUserPhoneNumber.getText().toString();
                        String insuranceReceiveAddress = etInsureOrderReceiveUserAddress.getText().toString();

                        String insuranceRecommendUser = etInsureRecommendUserPhone.getText().toString();
                        String insuranceOperationUserPhone = etInsureOperationUserPhone.getText().toString();




                        UserRetrofitUtil.confirmVehicleOrder(activity, "林宗钱",401, "13700000000", "320681198612020056", "蒋阿斯顿", "13678054218", "四川成都", "13678054217", "13678054216", "四川省", 0, "P121300011456642174960292000", new NetCallback<NetWorkResultBean<String>>(activity) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {

                            }

                            @Override
                            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

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

    }


    public static void startActivity(Activity activity, CommPriceData commPriceData,String idcard_number) {
        Intent intent = new Intent(activity, ComfirmOrderActivity.class);
        intent.putExtra("CommPriceData", commPriceData);
        intent.putExtra("idcard_number", idcard_number);
        activity.startActivity(intent);
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            if(
//                    TextUtils.isEmpty(etInsureOperationUserName.getText().toString())||
//                    TextUtils.isEmpty(etInsureOrderReceiveUserAddress.getText().toString())||
//                    TextUtils.isEmpty(etInsureOrderReceiveUserName.getText().toString())||
//                    TextUtils.isEmpty(etInsureOrderReceiveUserPhoneNumber.getText().toString())||
//                    TextUtils.isEmpty(etInsureRecommendUserName.getText().toString())||
//                    TextUtils.isEmpty(etInsureUserName.getText().toString())||
//

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
