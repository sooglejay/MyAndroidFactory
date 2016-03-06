package com.jiandanbaoxian.ui.buyinsurance.car_insurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.model.CommPriceData;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.widget.TitleBar;

/**
 * Created by sooglejay on 16/2/27.
 */
public class PriceReportActivity extends BaseActivity {

    private Activity activity;

    private CommPriceData commPriceData;
    private String idcard_number;
    private String city_no;
    private String country_no;
    private String province_no;
    long commercestartdate = 0;
    long compulsorystartdate = 0;



    private TitleBar titleBar;
    private TextView tvCompanyNumberDes;
    private LinearLayout layoutHuaanInsuranceAgent;
    private LinearLayout layoutToShowDetails;
    private ImageView ivToShowDetails;
    private LinearLayout layoutShowDetail;
    private LinearLayout layoutExpandView;
    private TextView tvCommercialTotalPrice;
    private TextView tvCompulsoryTotalPrice;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-02-28 10:14:27 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        tvCompanyNumberDes = (TextView) findViewById(R.id.tv_company_number_des);
        layoutHuaanInsuranceAgent = (LinearLayout) findViewById(R.id.layout_huaan_insurance_agent);
        layoutToShowDetails = (LinearLayout) findViewById(R.id.layout_to_show_details);
        ivToShowDetails = (ImageView) findViewById(R.id.iv_to_show_details);
        layoutShowDetail = (LinearLayout) findViewById(R.id.layout_show_detail);
        layoutExpandView = (LinearLayout) findViewById(R.id.layout_expand_view);
        tvCommercialTotalPrice = (TextView) findViewById(R.id.tv_commercial_total_price);
        tvCompulsoryTotalPrice = (TextView) findViewById(R.id.tv_compulsory_total_price);
    }

    public static void startActivity(Activity activity, CommPriceData data,String idcard_number,String city_no,String country_no,String province_no,long compulsorystartdate,long commercestartdate) {
        Intent intent = new Intent(activity, PriceReportActivity.class);
        intent.putExtra("CommPriceData", data);
        intent.putExtra("idcard_number", idcard_number);
        intent.putExtra("city_no", city_no);
        intent.putExtra("country_no", country_no);
        intent.putExtra("province_no", province_no);
        intent.putExtra("compulsorystartdate", compulsorystartdate);
        intent.putExtra("commercestartdate", commercestartdate);
        activity.startActivityForResult(intent, 0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_report);
        activity = this;
        getPriceReportData();
        findViews();

        setUpViews();
        setUpListener();

    }

    public void setUpViews() {
        titleBar.initTitleBarInfo("报价结果", R.drawable.arrow_left, -1, "", "");

    }

    public void setUpListener() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

        layoutHuaanInsuranceAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriceReportDetailTabHostActivity.startActivity(activity, commPriceData, idcard_number, country_no, city_no, province_no, compulsorystartdate, commercestartdate);
            }
        });
    }


    public void getPriceReportData() {
        commPriceData = getIntent().getParcelableExtra("CommPriceData");
        idcard_number = getIntent().getStringExtra("idcard_number");
        city_no = getIntent().getStringExtra("city_no");
        country_no = getIntent().getStringExtra("country_no");
        province_no = getIntent().getStringExtra("province_no");
        commercestartdate = getIntent().getLongExtra("commercestartdate", 0L);
        compulsorystartdate = getIntent().getLongExtra("compulsorystartdate", 0l);

    }

}
