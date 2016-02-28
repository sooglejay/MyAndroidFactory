package com.jiandanbaoxian.ui.buyinsurance.car_insurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.HuaanInsuranceItemAdapter;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.model.CommPriceData;
import com.jiandanbaoxian.model.HuanPriceData;
import com.jiandanbaoxian.model.InsuranceItemData;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.widget.TitleBar;

import java.util.List;

/**
 * Created by sooglejay on 16/2/28.
 */
public class PriceReportDetailsActivity extends BaseActivity {
    private Activity activity;
    private String  idcard_number;
    private CommPriceData commPriceData;


    public static void startActivity(Activity activity, CommPriceData data,String idcard_number) {
        Intent intent = new Intent(activity, PriceReportDetailsActivity.class);
        intent.putExtra("CommPriceData", data);
        intent.putExtra("idcard_number", idcard_number);
        activity.startActivityForResult(intent, 0);

    }

    View headerView;
    View footerView;
    private TitleBar titleBar;
    private LinearLayout layoutCommercialDatePicker;
    private TextView tvCommercialTotalPremium;
    private LinearLayout layoutCompulsoryDatePicker;
    private TextView tvCompulsoryTotalPremium;
    private ListView listView;
    private TextView tvQuotaPrice;
    private HuaanInsuranceItemAdapter adapter;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-02-28 11:49:35 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        headerView = View.inflate(activity, R.layout.header_huanan_details, null);
        footerView = View.inflate(activity, R.layout.footer_to_pay, null);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        layoutCommercialDatePicker = (LinearLayout) headerView.findViewById(R.id.layout_commercial_date_picker);
        tvCommercialTotalPremium = (TextView) headerView.findViewById(R.id.tv_commercial_total_premium);
        layoutCompulsoryDatePicker = (LinearLayout) headerView.findViewById(R.id.layout_compulsory_date_picker);
        tvCompulsoryTotalPremium = (TextView) headerView.findViewById(R.id.tv_compulsory_total_premium);
        listView = (ListView) findViewById(R.id.list_view);
        tvQuotaPrice = (TextView) footerView.findViewById(R.id.tv_quota_price);
        listView.addFooterView(footerView);
        listView.addHeaderView(headerView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaaaa_activity_price_report_huaan_details);
        activity = this;
        findViews();
        setUpViews();
        setUpListener();

    }

    private void setUpViews() {
        titleBar.initTitleBarInfo("报价详情", R.drawable.arrow_left, -1, "", "");
        getPriceReportData();

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
        tvQuotaPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "hello world ", Toast.LENGTH_LONG).show();
                ComfirmOrderActivity.startActivity(activity,commPriceData,idcard_number);
//                UserRetrofitUtil.confirmVehicleOrder(activity,);
            }
        });
    }

    public void getPriceReportData() {
        commPriceData = getIntent().getParcelableExtra("CommPriceData");
        idcard_number = getIntent().getStringExtra("idcard_number");
        if (commPriceData != null) {
            HuanPriceData bean = commPriceData.getHuanPriceData();
            if (bean != null) {
                tvCommercialTotalPremium.setText(bean.getCommerceAmount() + "");
                tvCompulsoryTotalPremium.setText(bean.getCompulsoryAmount() + "");
                List<InsuranceItemData> insuranceItemDataList = bean.getInsuranceItem();
                adapter = new HuaanInsuranceItemAdapter(insuranceItemDataList, activity);
                listView.setAdapter(adapter);
            }
        } else {
            Toast.makeText(activity, "是空", Toast.LENGTH_LONG).show();
        }
    }
}
