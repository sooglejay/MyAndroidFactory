package com.jiandanbaoxian.ui.buyinsurance.car_insurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.HuaanInsuranceItemAdapter;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.fragment.BaseFragment;
import com.jiandanbaoxian.model.CommPriceData;
import com.jiandanbaoxian.model.HuanPriceData;
import com.jiandanbaoxian.model.InsuranceItemData;
import com.jiandanbaoxian.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sooglejay on 16/2/28.
 */
public class PriceReportDetailsFragment1Activity extends BaseFragment {
    private Activity activity;
    private String idcard_number;
    private CommPriceData commPriceData;

    private String city_no;
    private String province_no;
    private String country_no;
    long commercestartdate = 0;
    long compulsorystartdate = 0;


    private InsuranceItemData compulsoryInsurance;//交强险单独拿出


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.aaaaa_activity_price_report_huaan_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        activity = this.getActivity();
        findViews(view);
        setUpViews();
        setUpListener();

    }


    View headerView;
    View footerView;
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
    private void findViews(View view) {
        headerView = View.inflate(activity, R.layout.header_huanan_details, null);
        footerView = View.inflate(activity, R.layout.footer_to_pay, null);
        layoutCommercialDatePicker = (LinearLayout) headerView.findViewById(R.id.layout_commercial_date_picker);
        tvCommercialTotalPremium = (TextView) headerView.findViewById(R.id.tv_commercial_total_premium);
        layoutCompulsoryDatePicker = (LinearLayout) headerView.findViewById(R.id.layout_compulsory_date_picker);
        tvCompulsoryTotalPremium = (TextView) headerView.findViewById(R.id.tv_compulsory_total_premium);
        listView = (ListView) view.findViewById(R.id.list_view);
        tvQuotaPrice = (TextView) footerView.findViewById(R.id.tv_quota_price);
        listView.addFooterView(footerView);
        listView.addHeaderView(headerView);
    }



    private void setUpViews() {
        getPriceReportData();

    }

    private void setUpListener() {

        tvQuotaPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "hello world ", Toast.LENGTH_LONG).show();
                ComfirmOrderActivity.startActivity(activity, commPriceData, idcard_number, city_no, country_no, province_no, commercestartdate, compulsorystartdate);
//                UserRetrofitUtil.confirmVehicleOrder(activity,);
            }
        });
    }

    public void getPriceReportData() {
        commPriceData = activity.getIntent().getParcelableExtra("CommPriceData");
        idcard_number = activity.getIntent().getStringExtra("idcard_number");
        city_no = activity.getIntent().getStringExtra("city_no");
        country_no = activity.getIntent().getStringExtra("country_no");
        province_no = activity.getIntent().getStringExtra("province_no");
        commercestartdate = activity.getIntent().getLongExtra("commercestartdate", 0L);
        compulsorystartdate = activity.getIntent().getLongExtra("compulsorystartdate", 0L);
        if (commPriceData != null) {
            HuanPriceData bean = commPriceData.getHuanPriceData();
            if (bean != null) {
                tvCommercialTotalPremium.setText(StringConstant.RMB+""+bean.getCommerceAmount() + "");
                tvCompulsoryTotalPremium.setText(StringConstant.RMB+""+bean.getCompulsoryAmount() + "");

                List<InsuranceItemData> insuranceItemDataList = new ArrayList<>();
                for (InsuranceItemData itemData : bean.getInsuranceItem()) {
                    if (itemData.getInsrnc_name().contains("交强")) {
                        compulsoryInsurance = itemData;
                    } else {
                        insuranceItemDataList.add(itemData);
                    }
                }
                adapter = new HuaanInsuranceItemAdapter(insuranceItemDataList, activity);
                listView.setAdapter(adapter);
            }
        } else {
            Toast.makeText(activity, "没有数据！", Toast.LENGTH_LONG).show();
        }
    }
}
