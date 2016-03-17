package com.jiandanbaoxian.ui.buyinsurance.car_insurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.VehicleTypeInfoAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.RegionBean;
import com.jiandanbaoxian.model.VehicleTypeInfo;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.JsonUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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


    private String licenseplate = "";
    private String frameNumber = "";
    private String model_name = "";
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
                                     String transfer, String transferDate, String registrationDateString, String issueDateString, String idcardNum,String model_name) {
        Intent intent = new Intent(activity, CarInsurancePickCarTypeActivity.class);
        intent.putExtra("licenseplate", licenseplate);
        intent.putExtra("engineNumber", engineNumber);
        intent.putExtra("frameNumber", frameNumber);
        intent.putExtra("model_name", model_name);
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
                            province_no, province_name, city_no, city_name, country_no, country_name, transfer, transferDate, regiterationDateString, issueDateString, idcardNum);
                } else {
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
        //1查询车型
        UserRetrofitUtil.vehcileTypeInfo(this, province_no, licenseplate, frameNumber,model_name, type, new NetCallback<NetWorkResultBean<Object>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                progressDialogUtil.hide();
                Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void success(NetWorkResultBean<Object> listNetWorkResultBean, Response response) {
                progressDialogUtil.hide();
                if (listNetWorkResultBean != null) {
                    int status = listNetWorkResultBean.getStatus();
                    switch (status) {
                        case HttpsURLConnection.HTTP_OK:


                            List<VehicleTypeInfo> datas = null;
                            Object obj = listNetWorkResultBean.getData();
                            if (obj instanceof String) {
                                Toast.makeText(activity, listNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                return;
                            }
                            try {
                                String json = com.jiandanbaoxian.util.JsonUtil.toJson(obj);
                                datas = com.jiandanbaoxian.util.JsonUtil.fromJson(json, new TypeToken<List<VehicleTypeInfo>>() {
                                }.getType());
                            } catch (Exception e) {
                                Log.e("qw", "出错啦！！！!");
                            }
                            if (datas == null) {
                                Toast.makeText(activity, "系统异常！", Toast.LENGTH_LONG).show();
                                return;
                            }

                            vehicleTypeInfos.clear();
                            vehicleTypeInfos.addAll(datas);


                            adapter.notifyDataSetChanged();
                            break;
                        default:
                            Toast.makeText(activity, listNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }
        });
    }


}
