package com.jiandanbaoxian.ui.buyinsurance.car_insurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.fragment.DialogFragmentCreater;
import com.jiandanbaoxian.model.CommPriceData;
import com.jiandanbaoxian.model.ConfirmOrderBean;
import com.jiandanbaoxian.model.HuanInsuranceBaseInfoData;
import com.jiandanbaoxian.model.HuanPriceData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.BrowserWebViewActivity;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sooglejay on 16/3/1.
 */
public class PayActivity extends BaseActivity {

    private Activity activity;
    private ProgressDialogUtil progressDialogUtil;

    private CommPriceData commPriceData;
    private ConfirmOrderBean confirmOrderBean;
    private String userName;
    private String country_no;
    long commercestartdate = 0;
    long compulsorystartdate = 0;
    private DialogFragmentCreater dialogFragmentCreater;

    private TitleBar titleBar;
    private LinearLayout layoutCommercialDatePicker;
    private TextView tvCommercialTotalPremium;
    private LinearLayout layoutCompulsoryDatePicker;
    private TextView tvCompulsoryTotalPremium;
    private TextView tvPay;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-03-02 15:15:01 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        layoutCommercialDatePicker = (LinearLayout) findViewById(R.id.layout_commercial_date_picker);
        tvCommercialTotalPremium = (TextView) findViewById(R.id.tv_commercial_total_premium);
        layoutCompulsoryDatePicker = (LinearLayout) findViewById(R.id.layout_compulsory_date_picker);
        tvCompulsoryTotalPremium = (TextView) findViewById(R.id.tv_compulsory_total_premium);
        tvPay = (TextView) findViewById(R.id.tv_pay);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_huaan);
        activity = this;
        progressDialogUtil = new ProgressDialogUtil(activity);
        dialogFragmentCreater = new DialogFragmentCreater();//涉及到权限操作时，需要临时输入密码并验证

        dialogFragmentCreater.setDialogContext(activity,getSupportFragmentManager());


//        commPriceData = getIntent().getParcelableExtra("CommPriceData");
//        confirmOrderBean = getIntent().getParcelableExtra("ConfirmOrderBean");
//
//
//        userName = getIntent().getStringExtra("insuranceUserName");
//        country_no = getIntent().getStringExtra("country_no");
//        commercestartdate = getIntent().getLongExtra("commercestartdate", 0L);
//        compulsorystartdate = getIntent().getLongExtra("compulsorystartdate", 0l);


        findViews();
        setUpViews();
        setUpListener();
    }

    private void setUpListener() {

        dialogFragmentCreater.setWebViewURL("http://www.wanbaoe.com/1.html");
        dialogFragmentCreater.setOnLicenseDialogClickListener(new DialogFragmentCreater.OnLicenseDialogClickListener() {
            @Override
            public void onClick(View view, boolean isAgree) {
                switch (view.getId())
                {
                    case R.id.layout_confirm:
                        if(!isAgree){
                            Toast.makeText(activity,"请同意服务协议！",Toast.LENGTH_LONG).show();
                            return;
                        }
                        dialogFragmentCreater.dismiss();
                        break;
                }

            }
        });
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

                dialogFragmentCreater.showDialog(activity,DialogFragmentCreater.DialogShowLicenseDialog);
//
//                UserRetrofitUtil.huanApplyPay(activity, "陈传渠", "WX5309903602016000034",  "950",
//                        "WX5309903802016000034","2261.65", "370200", "1456824490000", "1456824490000", "0", new NetCallback<NetWorkResultBean<String>>(activity) {
//                            @Override
//                            public void onFailure(RetrofitError error, String message) {
//
//                            }
//
//                            @Override
//                            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
//
//                            }
//                        });
//                BrowserWebViewActivity.startActivity(activity,"http://agenttest.sinosafe.com.cn/tstpayonline/recvMerchantAction.do?orderId=166000434996","申请支付");
            }


        });
    }

    private void setUpViews() {

        titleBar.initTitleBarInfo("确认支付", R.drawable.arrow_left, -1, "", "");
    }


    public static void startActivity(Activity activity, String country_no, long commercestartdate, long compulsorystartdate, String insuranceUserName, CommPriceData commPriceData, ConfirmOrderBean confirmOrderBean) {
        Intent intent = new Intent(activity, PayActivity.class);
        intent.putExtra("insuranceUserName", insuranceUserName);
        intent.putExtra("CommPriceData", commPriceData);
        intent.putExtra("ConfirmOrderBean", confirmOrderBean);
        intent.putExtra("country_no", country_no);
        intent.putExtra("commercestartdate", commercestartdate);
        intent.putExtra("compulsorystartdate", compulsorystartdate);
        activity.startActivity(intent);
    }

}
