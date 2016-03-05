package com.jiandanbaoxian.ui.buyinsurance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.OvertimeData;
import com.jiandanbaoxian.model.Overtimeordertable;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.JsonUtil;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 买保险-加班狗-确认订单
 */
public class OrderCofirmPengciInsureActivity extends BaseActivity implements
        AMapLocationListener {
    private static final String ExtraKey = "ExtraKey";
    private LocationManagerProxy mLocationManagerProxy;


    private boolean tvPayFlag = false;
    private TitleBar titleBar;
    private TextView tv_pay;
    private EditText et_insure_user_name;
    private EditText et_company_address;
    private EditText et_company_name;
    private EditText et_home_address;
    private ImageView iv_location;

    private ProgressDialogUtil progressDialogUtil;


    private Context context;

    private OvertimeData overtimeData;//加班险的具体信息


    private double lat = 0;//
    private double lng = 0;

    public static void startActivity(Activity context, OvertimeData overtimeData) {
        Intent intent = new Intent(context, OrderCofirmPengciInsureActivity.class);
        intent.putExtra(ExtraKey, overtimeData);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm_pengci_insure);

        overtimeData = getIntent().getParcelableExtra(ExtraKey);
        setUp();
        setLisenter();
        initLocationManager();
    }

    /**
     * 初始化定位
     */
    private void initLocationManager() {
        // 初始化定位，只采用网络定位
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.setGpsEnable(false);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用destroy()方法
        // 其中如果间隔时间为-1，则定位只定一次,
        // 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, -1, 15, this);

    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                OrderCofirmPengciInsureActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

        //手动定位
        iv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLocationManager();//由于高德定位管理器是单例对象，可以重复调用
            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("确认订单", R.drawable.arrow_left, -1, "", "");


        context = this;
        progressDialogUtil = new ProgressDialogUtil(this);

        et_insure_user_name = (EditText) findViewById(R.id.et_insure_user_name);
        et_company_address = (EditText) findViewById(R.id.et_company_address);
        et_home_address = (EditText) findViewById(R.id.et_home_address);
        iv_location = (ImageView) findViewById(R.id.iv_location);

        et_insure_user_name.setText(PreferenceUtil.load(this, PreferenceConstant.name, ""));

        et_insure_user_name.addTextChangedListener(textWatcher);
        et_company_address.addTextChangedListener(textWatcher);
        et_home_address.addTextChangedListener(textWatcher);


        tv_pay = (TextView) findViewById(R.id.tv_pay);
        tv_pay.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tv_pay.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvPayFlag) {
                    //如果输入框都有输入

                    int overtimeInsuranceId = 0;
                    if (overtimeData != null && overtimeData.getOvertimeInsurance() != null) {
                        overtimeInsuranceId = overtimeData.getOvertimeInsurance().getId();
                    }
                    progressDialogUtil.show("正在保存订单...");
                    UserRetrofitUtil.saveOvertimeInsuranceOrder(context,
                            PreferenceUtil.load(context, PreferenceConstant.phone, ""),
                            overtimeInsuranceId,
                            1,
                            et_company_address.getText().toString(),
                            et_home_address.getText().toString(),
                            lat,
                            lng,
                            et_insure_user_name.getText().toString(),
                            et_company_name.getText().toString(),
                            new NetCallback<NetWorkResultBean<Object>>(context) {
                                @Override
                                public void onFailure(RetrofitError error, String message) {
                                    progressDialogUtil.hide();
                                    if (!TextUtils.isEmpty(message)) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void success(NetWorkResultBean<Object> stringNetWorkResultBean, Response response) {
                                    progressDialogUtil.hide();


                                    if (stringNetWorkResultBean != null) {
                                        int status = stringNetWorkResultBean.getStatus();
                                        switch (status) {
                                            case HttpsURLConnection.HTTP_OK:
                                                if (stringNetWorkResultBean.getData()!=null) {
                                                    Overtimeordertable bean = JsonUtil.getSerializedObject(stringNetWorkResultBean.getData(),Overtimeordertable.class);
                                                    PayJiaBanDogInsureActivity.startActivity(OrderCofirmPengciInsureActivity.this, bean);
                                                    OrderCofirmPengciInsureActivity.this.finish();
                                                }
                                                break;
                                            default:
                                                Toast.makeText(context, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                break;
                                        }
                                    }




                                }
                            });

                } else {
                    //如果输入框没有输入 信息
                    Toast.makeText(context, "请先完善以上信息！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (
                    TextUtils.isEmpty(et_insure_user_name.getText())
                            || TextUtils.isEmpty(et_company_address.getText())
                            || TextUtils.isEmpty(et_home_address.getText())
                    ) {
                tvPayFlag = false;
                tv_pay.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                tv_pay.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                tvPayFlag = true;
                tv_pay.setBackgroundResource(R.drawable.btn_select_base);
                tv_pay.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            // 定位成功回调信息，设置相关消息

            if (et_company_address != null) {
                et_company_address.setText(aMapLocation.getPoiName() + "");
            }
            lat = aMapLocation.getLatitude();
            lng = aMapLocation.getLongitude();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        // 移除定位请求
        mLocationManagerProxy.removeUpdates(this);
        // 销毁定位
        mLocationManagerProxy.destroy();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

}
