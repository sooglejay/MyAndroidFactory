package com.jiandanbaoxian.ui;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.util.ImageUtils;
import com.jiandanbaoxian.util.JsonUtil;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.RoundImageView;
import com.jiandanbaoxian.widget.citypicker.ChooseCityActivity;
import com.jiandanbaoxian.widget.imagepicker.MultiImageSelectorActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.mime.TypedFile;

/**
 * Created by JammyQtheLab on 2015/11/11.
 */
public class ModifyUserInfoActivity extends BaseActivity implements
        AMapLocationListener {
    private LocationManagerProxy mLocationManagerProxy;
    public static final int MODIFY_PHONE_NUMBER = 100;//修改手机号码
    public static final int MODIFY_EMPLOYEE_NUMBER = 101;//修改员工工号
    public static final int MODIFY_SERVICE_DESCRIBE = 102;//修改服务介绍
    private final static int MODIFY_CITY = 103;
    private final static int MODIFY_COMPANY = 104;
    private final static int MODIFY_COMPANY_ADDRESS = 105;

    private String cityNameStr;//传递给选择城市的activity


    private FrameLayout layoutAvatar;
    private ImageView iv_avatar_background;
    private RoundImageView ivAvatar;
    private LinearLayout layoutModifyMobile;
    private TextView tvMobileNumber;
    private TextView tv_user_name;
    private LinearLayout layoutModifyCity;
    private TextView tvCityName;
    private LinearLayout layoutModifyCompanyName;
    private TextView tvCompanyName;
    private LinearLayout layoutModifyCompanyAddress;
    private TextView tvCompanyAddress;
    private TextView tv_service_describe;
    private TextView tv_employ_number;


    private ScrollView layout_scroll_view;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-11 17:02:44 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        layoutAvatar = (FrameLayout) findViewById(R.id.layout_avatar);
        layout_scroll_view = (ScrollView) findViewById(R.id.layout_scroll_view);
        iv_avatar_background = (ImageView) findViewById(R.id.iv_avatar_background);
        ivAvatar = (RoundImageView) findViewById(R.id.iv_avatar);
        layoutModifyMobile = (LinearLayout) findViewById(R.id.layout_modify_mobile);
        tvMobileNumber = (TextView) findViewById(R.id.tv_mobile_number);
        tv_employ_number = (TextView) findViewById(R.id.tv_employ_number);
        layoutModifyCity = (LinearLayout) findViewById(R.id.layout_modify_city);
        tvCityName = (TextView) findViewById(R.id.tv_city_name);
        layoutModifyCompanyName = (LinearLayout) findViewById(R.id.layout_modify_company_name);
        tvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        layoutModifyCompanyAddress = (LinearLayout) findViewById(R.id.layout_modify_company_address);
        tvCompanyAddress = (TextView) findViewById(R.id.tv_company_address);
        tv_service_describe = (TextView) findViewById(R.id.tv_service_describe);


        ImageLoader.getInstance().displayImage(StringConstant.Avatar_original.replace("XXX", userid + ""), iv_avatar_background, ImageUtils.getOptions());
        ImageLoader.getInstance().displayImage(StringConstant.Avatar_original.replace("XXX", userid + ""), ivAvatar, ImageUtils.getOptions());
    }


    private ArrayList<String> imageList = new ArrayList<>();
    private String resultPath;//图片最终位置

    private int userid = -1;
    private Activity activity;
    private static final String ExtraKey = "ExtraKey";
    private Userstable userstable;

    private String serviceDescribe = "";
    private String companyAddress = "";
    private String companyName = "";
    private String workNum = "";

    public static void startActivity(Activity activity, Userstable userstable) {
        Intent intent = new Intent(activity, ModifyUserInfoActivity.class);
        intent.putExtra(ExtraKey, userstable);
        activity.startActivity(intent);
    }

    private void setUpViews() {
        try {
            companyName = userstable.getCompanyname();
            serviceDescribe = userstable.getService() + "";
            cityNameStr = userstable.getCity();
            workNum = userstable.getWorknum() + "";
            if (userstable.getCompany() != null)
                companyAddress = userstable.getCompanyaddress() + "";

            tvCityName.setText(cityNameStr);
            tv_employ_number.setText(workNum);
            tvMobileNumber.setText(userstable.getPhone() + "");
            tv_user_name.setText(userstable.getName() + "");
            tv_service_describe.setText(serviceDescribe);
            tvCompanyAddress.setText(companyAddress);
            tvCompanyName.setText(companyName);

        } catch (NullPointerException npe) {
            Toast.makeText(activity, npe.toString(), Toast.LENGTH_SHORT).show();
            Log.e("retrofit", npe.toString());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_info);
        activity = this;
        userstable = getIntent().getExtras().getParcelable(ExtraKey);
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        findViews();
        setUpListeners();
        initLocationManager();
        refreshUserInfo();
    }

    private void refreshUserInfo() {
        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(activity);
        progressDialogUtil.show("正在获取用户身份信息...");
        UserRetrofitUtil.getSelfInfo(activity, userid, new NetCallback<NetWorkResultBean<Object>>(activity) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        progressDialogUtil.hide();
                        if (TextUtils.isEmpty(message)) {
                            Toast.makeText(activity, "无法连接网络：" + error.toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void success(NetWorkResultBean<Object> userstableNetWorkResultBean, Response response) {
                        progressDialogUtil.hide();

                        if (userstableNetWorkResultBean != null) {
                            int status = userstableNetWorkResultBean.getStatus();
                            switch (status) {
                                case HttpsURLConnection.HTTP_OK:
                                    if (userstableNetWorkResultBean.getData() != null) {
                                        userstable = JsonUtil.getSerializedObject(userstableNetWorkResultBean.getData(),Userstable.class);
                                        setUpViews();
                                    }
                                    break;
                                default:
                                    Toast.makeText(activity, userstableNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }


                    }
                }
        );
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

    private void setUpListeners() {

        //不能修改手机号码
//        findViewById(R.id.layout_modify_mobile).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(activity, ModifyUserPhoneNumberActivity.class);
//                activity.startActivityForResult(intent, MODIFY_PHONE_NUMBER);
//            }
//        });

        findViewById(R.id.layout_employee_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ModifyEmployeeNumberActivity.class);
                intent.putExtra("workNum", workNum);

                activity.startActivityForResult(intent, MODIFY_EMPLOYEE_NUMBER);
            }
        });

        findViewById(R.id.layout_service_describe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ModifyServiceDescribeActivity.class);
                intent.putExtra("serviceDescribe", serviceDescribe);
                activity.startActivityForResult(intent, MODIFY_SERVICE_DESCRIBE);
            }
        });


        layoutModifyCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChooseCityActivity.class);
                intent.putExtra(ExtraConstants.EXTRA_CITY_NAME, cityNameStr);
                intent.putExtra("is_edit", true);
                startActivityForResult(intent, MODIFY_CITY);
            }
        });

        layoutModifyCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CompanyListActivity.class);
                intent.putExtra("company_name", companyName);
                startActivityForResult(intent, MODIFY_COMPANY);
            }
        });

        //返回
        findViewById(R.id.layout_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });


        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtils.startPickPhoto(activity, imageList, 1, false);
            }
        });

        layout_scroll_view.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {

            }
        });

        layoutModifyCompanyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ModifyCompanyAddressActivity.class);
                intent.putExtra("companyAddress", companyAddress);
                activity.startActivityForResult(intent, MODIFY_COMPANY_ADDRESS);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //若是从图库选择图
            case ImageUtils.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    // 获取返回的图片列表
                    List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    imageList.clear();
                    imageList.addAll(paths);
                    if (imageList.size() > 0) {
                        resultPath = ImageUtils.getImageFolderPath(this) + File.separator + System.currentTimeMillis() + ".jpg";
                        ImageUtils.cropImage(this, Uri.fromFile(new File(imageList.get(0))), resultPath, 1, 1);
                    }
                }
                break;

            //裁剪图片
            case ImageUtils.REQUEST_CODE_CROP_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    //添加图片到list并且显示出来
                    //上传图片
                    if (!TextUtils.isEmpty(resultPath)) {

                        File file = new File(resultPath);
                        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(activity);
                        progressDialogUtil.show("正在上传图片...");
                        TypedFile typedFile = new TypedFile(ImageUtils.mimeType, file);
                        UserRetrofitUtil.modifySelfInfo(activity, userid, -1, -1, "-1", "-1", "-1", "-1", "-1", typedFile, new NetCallback<NetWorkResultBean<Object>>(activity) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {
                                progressDialogUtil.hide();
                                Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void success(NetWorkResultBean<Object> userstableNetWorkResultBean, Response response) {
                                progressDialogUtil.hide();

                                if (userstableNetWorkResultBean != null) {
                                    int status = userstableNetWorkResultBean.getStatus();
                                    switch (status) {
                                        case HttpsURLConnection.HTTP_OK:
                                            ImageLoader.getInstance().displayImage("file://" + resultPath, ivAvatar, ImageUtils.getOptions());
                                            ImageLoader.getInstance().displayImage("file://" + resultPath, iv_avatar_background, ImageUtils.getOptions());

                                            break;
                                        default:
                                            Toast.makeText(activity, userstableNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }



                            }
                        });
                    }
                }
                break;
            case MODIFY_PHONE_NUMBER: //修改手机号码
                if (resultCode == Activity.RESULT_OK) {
                    String newPhoneStr = data.getExtras().getString("MODIFY_PHONE_NUMBER");
                    tvMobileNumber.setText(newPhoneStr);
                }
                break;

            case MODIFY_EMPLOYEE_NUMBER: //修改员工号
                if (resultCode == Activity.RESULT_OK) {
                    workNum = data.getExtras().getString("MODIFY_EMPLOYEE_NUMBER");
                    tv_employ_number.setText(workNum);
                }
                break;

            case MODIFY_SERVICE_DESCRIBE: //修改服务介绍
                if (resultCode == Activity.RESULT_OK) {
                    serviceDescribe = data.getExtras().getString("MODIFY_SERVICE_DESCRIBE");
                    tv_service_describe.setText(serviceDescribe);
                }
                break;


            case MODIFY_CITY:
                if (resultCode == Activity.RESULT_OK) {
                    cityNameStr = data.getExtras().getString(ExtraConstants.EXTRA_CITY_NAME);
                    if (tvCityName != null) {
                        tvCityName.setText(cityNameStr);
                    }
                }
                break;

            case MODIFY_COMPANY:
                if (resultCode == Activity.RESULT_OK) {
                    companyName = data.getExtras().getString("company_name");
                    if (tvCompanyName != null) {
                        tvCompanyName.setText(companyName);
                    }
                }
                break;


            case MODIFY_COMPANY_ADDRESS:
                if (resultCode == Activity.RESULT_OK) {
                    companyAddress = data.getExtras().getString("company_address");
                    if (tvCompanyAddress != null) {
                        tvCompanyAddress.setText(companyAddress);
                    }
                }
                break;


            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            // 定位成功回调信息，设置相关消息
            if (aMapLocation != null) {
                if (userstable == null || TextUtils.isEmpty(userstable.getCity())) {
                    String province = aMapLocation.getProvince();
                    String city = aMapLocation.getCity();
                    if (city.contains(province)) {
                        cityNameStr = city;
                        tvCityName.setText(city);
                    } else {
                        cityNameStr = province + city;
                        tvCityName.setText(province + city);
                    }
                } else {
                    cityNameStr = userstable.getCity();
                    tvCityName.setText(cityNameStr);
                }
            }
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
        if (mLocationManagerProxy != null) {// 移除定位请求
            mLocationManagerProxy.removeUpdates(this);
            // 销毁定位
            mLocationManagerProxy.destroy();
        }
    }


}
