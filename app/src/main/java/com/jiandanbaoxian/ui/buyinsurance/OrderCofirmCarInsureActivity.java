package com.jiandanbaoxian.ui.buyinsurance;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.ExtraConstants;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.RegionBean;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.LoginActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;
import com.jiandanbaoxian.widget.citypicker.ChooseCityActivity;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 买保险-车险-确认订单信息
 */
public class OrderCofirmCarInsureActivity extends BaseActivity implements
        AMapLocationListener {
    private final static int CHOOSE_CITY = 103;


    private TitleBar titleBar;
    private EditText etInsureUserName;
    private EditText etInsureUserMobileNumber;
    private EditText etUserIdcardNumber;
    private EditText etReceiveUserName;
    private EditText etReceiveUserMobileNumber;
    private EditText etCompanyAddress;
    private ImageView ivLocation;
    private EditText etRecommendUserName;
    private ImageView ivChoose;
    private LinearLayout layoutRule;
    private TextView tvRule;
    private TextView tvLicence;
    private TextView tvPayingAgent;
    private TextView tvBuyInsure;
    private EditText etInsureArea;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-02-21 01:36:22 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        etInsureUserName = (EditText) findViewById(R.id.et_insure_user_name);
        etInsureUserMobileNumber = (EditText) findViewById(R.id.et_insure_user_mobile_number);
        etUserIdcardNumber = (EditText) findViewById(R.id.et_user_idcard_number);
        etReceiveUserName = (EditText) findViewById(R.id.et_receive_user_name);
        etReceiveUserMobileNumber = (EditText) findViewById(R.id.et_receive_user_mobile_number);
        etCompanyAddress = (EditText) findViewById(R.id.et_company_address);
        ivLocation = (ImageView) findViewById(R.id.iv_location);
        etRecommendUserName = (EditText) findViewById(R.id.et_recommend_user_name);
        ivChoose = (ImageView) findViewById(R.id.iv_choose);
        layoutRule = (LinearLayout) findViewById(R.id.layout_rule);
        tvRule = (TextView) findViewById(R.id.tv_rule);
        tvLicence = (TextView) findViewById(R.id.tv_licence);
        tvPayingAgent = (TextView) findViewById(R.id.tv_paying_agent);
        tvBuyInsure = (TextView) findViewById(R.id.tv_buy_insure);
        etInsureArea = (EditText) findViewById(R.id.et_insure_area);
    }


    private Activity activity;
    private boolean isAgreeWithLicence = true;
    private boolean tvEnquiryFlag = false;
    private ProgressDialogUtil progressDialogUtil;
    private int userid = -1;


    String carNumberString;
    String carFaDongJiNumber;
    String carJiaNumber;
    String userNameString;

    long commercialTimeLong;//商业险的日期
    long fazhengTimeLong; //发证日期
    long jqxTimeLong;//交强险的日期
    long signInTimeLong;//登记日期

    private LocationManagerProxy mLocationManagerProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm_car_insure);
        activity = this;
        initLocationManager();

        carNumberString = getIntent().getStringExtra("carNumberString");
        carFaDongJiNumber = getIntent().getStringExtra("carFaDongJiNumber");
        carJiaNumber = getIntent().getStringExtra("carJiaNumber");
        userNameString = getIntent().getStringExtra("userNameString");
        commercialTimeLong = getIntent().getLongExtra("commercialTimeLong", 0);
        jqxTimeLong = getIntent().getLongExtra("jqxTimeLong", 0);
        fazhengTimeLong = getIntent().getLongExtra("fazhengTimeLong", 0);
        signInTimeLong = getIntent().getLongExtra("signInTimeLong", 0);


        setUp();
        setLisenter();


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
                activity.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });


        ivChoose = (ImageView) findViewById(R.id.iv_choose);
        ivChoose.setImageResource(R.drawable.icon_choose_selected);
        ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAgreeWithLicence) {
                    isAgreeWithLicence = true;
                    ivChoose.setImageResource(R.drawable.icon_choose_selected);
                    tvBuyInsure.setBackgroundResource(R.drawable.btn_select_base_shape_0);
                    tvBuyInsure.setTextColor(getResources().getColor(R.color.white_color));
                } else {
                    ivChoose.setImageResource(R.drawable.icon_choose);
                    isAgreeWithLicence = false;
                    tvBuyInsure.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                    tvBuyInsure.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
                }
            }
        });


        tvBuyInsure = (TextView) findViewById(R.id.tv_buy_insure);
        tvBuyInsure.setEnabled(true);
        tvBuyInsure.setBackgroundResource(R.drawable.btn_select_base_shape_0);
        tvBuyInsure.setTextColor(getResources().getColor(R.color.white_color));
        tvBuyInsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAgreeWithLicence) {
                    String phone = PreferenceUtil.load(activity, PreferenceConstant.phone, "");
                    if (TextUtils.isEmpty(phone)) {
                        Toast.makeText(activity, "请先登录！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity, LoginActivity.class));
                    } else {
//                        String provence,
//                        String provnce_no,
//                        String city_no,
//                        String county_no,
//                        int transfer,
//                        int transferDate,
//                        String idcardNum,
//                        String phone,
//                        int compulsoryAmt,
//                        String insuranceItems,
//                        int type,


                        int transfer = fazhengTimeLong > signInTimeLong ? 1 : 0;
                        long transferDate = 0;
                        String idcardNum = etUserIdcardNumber.getText().toString();

                        progressDialogUtil.show("正在生成订单...");
                        userid = PreferenceUtil.load(activity, PreferenceConstant.userid, -1);

                    }

                } else {
                    Toast.makeText(activity, "请您勾选同意保障条款！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etInsureArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChooseCityActivity.class);
                intent.putExtra(ExtraConstants.EXTRA_CITY_NAME, "");
                intent.putExtra("is_edit", false);
                startActivityForResult(intent, CHOOSE_CITY);

            }
        });


        tvLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "正在写哦亲！", Toast.LENGTH_SHORT).show();

            }
        });

        ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLocationManager();
            }
        });

    }

    private void setUp() {
        findViews();
        titleBar.initTitleBarInfo("确认订单", R.drawable.arrow_left, -1, "", "");
        progressDialogUtil = new ProgressDialogUtil(this);


        etRecommendUserName.addTextChangedListener(textWatcher);
        etUserIdcardNumber.addTextChangedListener(textWatcher);
        etReceiveUserMobileNumber.addTextChangedListener(textWatcher);
        etReceiveUserName.addTextChangedListener(textWatcher);
        etCompanyAddress.addTextChangedListener(textWatcher);
        etInsureUserMobileNumber.addTextChangedListener(textWatcher);
        etInsureUserName.addTextChangedListener(textWatcher);
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (
                    TextUtils.isEmpty(etCompanyAddress.getText()) ||
                            TextUtils.isEmpty(etInsureUserMobileNumber.getText()) ||
                            TextUtils.isEmpty(etInsureUserName.getText()) ||
                            TextUtils.isEmpty(etReceiveUserName.getText()) ||
                            TextUtils.isEmpty(etReceiveUserMobileNumber.getText()) ||
                            TextUtils.isEmpty(etUserIdcardNumber.getText()) ||
                            TextUtils.isEmpty(etRecommendUserName.getText())
                    ) {
                tvEnquiryFlag = false;
                tvBuyInsure.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                tvBuyInsure.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                tvEnquiryFlag = true;
                tvBuyInsure.setBackgroundResource(R.drawable.btn_select_base);
                tvBuyInsure.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_CITY:
                    if (resultCode == Activity.RESULT_OK) {
                        String cityNameStr = data.getExtras().getString(ExtraConstants.EXTRA_CITY_NAME);
//                    if (tvCityName != null) {
//                        tvCityName.setText(cityNameStr);
//                    }
                        etInsureArea.setText(cityNameStr);
                    }
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            // 定位成功回调信息，设置相关消息

            if (etCompanyAddress != null) {
                etCompanyAddress.setText(aMapLocation.getPoiName() + "");
            }
//            lat = aMapLocation.getLatitude();
//            lng = aMapLocation.getLongitude();
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


}
