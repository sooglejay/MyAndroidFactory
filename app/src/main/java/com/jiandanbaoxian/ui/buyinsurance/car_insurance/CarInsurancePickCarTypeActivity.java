package com.jiandanbaoxian.ui.buyinsurance.car_insurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.VehicleTypeInfoAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.VehicleTypeInfo;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sooglejay on 16/2/24.
 */
public class CarInsurancePickCarTypeActivity extends BaseActivity {
    private Activity activity;
    private List<VehicleTypeInfo> vehicleTypeInfos = new ArrayList<>();
    private VehicleTypeInfoAdapter adapter;
    private boolean isValidToNextActivity = false;
    private ProgressDialogUtil progressDialogUtil;


    private String province_no = "";
    private String province_name = "";

    private String city_no = "";
    private String city_name = "";

    private String country_no = "";
    private String country_name = "";
    private String model_code = "";
    private float newValue = 0;
    private String transfer = "";
    private String transferDate = "";
    private String regiterationDateString = "";
    private String issueDateString = "";


    private String provence_no = "";
    private String licenseplate = "";
    private String frameNumber = "";
    private String engineNumber = "";
    private String userName = "";
    private String idcardNum = "";
    private String type = "0";
    private TitleBar titleBar;
    private ListView listView;
    private TextView tvEnquiry;


    View footer;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-02-24 17:03:44 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        footer = View.inflate(activity, R.layout.footer_picker_car_model, null);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        listView = (ListView) findViewById(R.id.list_view);
        tvEnquiry = (TextView) footer.findViewById(R.id.tv_enquiry);
        listView.addFooterView(footer);
    }

    public static void startActivity(Activity activity, String licenseplate, String engineNumber, String frameNumber, String userName,
                                     String province_no, String province_name, String city_no, String city_name, String country_no, String country_name,
                                     String transfer, String transferDate, String registrationDateString, String issueDateString, String idcardNum) {
        Intent intent = new Intent(activity, CarInsurancePickCarTypeActivity.class);
        intent.putExtra("licenseplate", licenseplate);
        intent.putExtra("engineNumber", engineNumber);
        intent.putExtra("frameNumber", frameNumber);
        intent.putExtra("userName", userName);
        intent.putExtra("province_no", province_no);
        intent.putExtra("province_name", province_name);
        intent.putExtra("city_no", city_no);
        intent.putExtra("city_name", city_name);
        intent.putExtra("country_no", country_no);
        intent.putExtra("country_name", country_name);
        intent.putExtra("transfer", transfer);
        intent.putExtra("transferDate", transferDate);
        intent.putExtra("registrationDateString", registrationDateString);
        intent.putExtra("issueDateString", issueDateString);
        intent.putExtra("idcardNum", idcardNum);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_car_model_activity);
        activity = this;
        progressDialogUtil = new ProgressDialogUtil(activity);

        licenseplate = getIntent().getStringExtra("licenseplate");
        engineNumber = getIntent().getStringExtra("engineNumber");
        frameNumber = getIntent().getStringExtra("frameNumber");
        userName = getIntent().getStringExtra("userName");
        province_no = getIntent().getStringExtra("province_no");
        province_name = getIntent().getStringExtra("province_name");
        city_no = getIntent().getStringExtra("city_no");
        city_name = getIntent().getStringExtra("city_name");
        country_no = getIntent().getStringExtra("country_no");
        country_name = getIntent().getStringExtra("country_name");
        transfer = getIntent().getStringExtra("transfer");
        transferDate = getIntent().getStringExtra("transferDate");
        regiterationDateString = getIntent().getStringExtra("registrationDateString");
        issueDateString = getIntent().getStringExtra("issueDateString");
        idcardNum = getIntent().getStringExtra("idcardNum");

        findViews();
        setUp();
        setLisenter();
        getVehicleTypeInfo();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                activity.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

        tvEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidToNextActivity) {
                    CarInsurancePricePlanActivity.startActivity(activity, licenseplate, engineNumber, model_code, newValue, frameNumber, userName,
                            provence_no, province_name, city_no, city_name, country_no, country_name, transfer, transferDate, regiterationDateString, issueDateString, idcardNum);
                } else {
                    CarInsurancePricePlanActivity.startActivity(activity, licenseplate, engineNumber, model_code, newValue, frameNumber, userName,
                            provence_no, province_name, city_no, city_name, country_no, country_name, transfer, transferDate, regiterationDateString, issueDateString, idcardNum);

                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > -1) {
                    tvEnquiry.setBackgroundResource(R.drawable.btn_select_base);
                    tvEnquiry.setTextColor(getResources().getColor(R.color.white_color));
                    isValidToNextActivity = true;

                    for (VehicleTypeInfo bean : vehicleTypeInfos)
                        bean.setIsSelected(false);
                    vehicleTypeInfos.get(position).setIsSelected(true);
                    model_code = vehicleTypeInfos.get(position).getModel_code();
                    newValue = vehicleTypeInfos.get(position).getCar_price();
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }

    private void setUp() {

        titleBar.initTitleBarInfo("选择车型", R.drawable.arrow_left, -1, "", "");
        tvEnquiry.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tvEnquiry.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));


        adapter = new VehicleTypeInfoAdapter(vehicleTypeInfos, activity);
        listView.setAdapter(adapter);


    }


    public void getVehicleTypeInfo() {
        progressDialogUtil.show("正在查询...");
        UserRetrofitUtil.vehcileTypeInfo(this, province_no, licenseplate, frameNumber, type, new NetCallback<NetWorkResultBean<List<VehicleTypeInfo>>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                progressDialogUtil.hide();
            }

            @Override
            public void success(NetWorkResultBean<List<VehicleTypeInfo>> listNetWorkResultBean, Response response) {
                if (listNetWorkResultBean != null) {
                    vehicleTypeInfos.addAll(listNetWorkResultBean.getData());
                    adapter.notifyDataSetChanged();
                }
                progressDialogUtil.hide();

            }
        });
    }


}
