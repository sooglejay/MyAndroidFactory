package com.jiandanbaoxian.ui.me.myteam;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.CityUtil;
import com.jiandanbaoxian.util.ImageUtils;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.PopWindowUtils;
import com.jiandanbaoxian.widget.TitleBar;
import com.jiandanbaoxian.widget.citypicker.ChooseCityActivity;
import com.jiandanbaoxian.widget.imagepicker.MultiImageSelectorActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


/**
 * Created by JammyQtheLab on 2015/11/7.
 */
public class CertificationActivity extends BaseActivity implements
        AMapLocationListener {
    private LocationManagerProxy mLocationManagerProxy;

    private final static int ACTION_CHOOSE_CITY = 1000;
    private final static int ACTION_REQUEST_IMAGE = 1001;
    private Activity context;

    private int userid = -1;

    private List<String> provinceList = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();
    private CityUtil cityUtil;
    private PopWindowUtils popWindowUtils;


    private String cityNameStr;//传递给选择城市的activity

    private ArrayList<String> imageList = new ArrayList<>();
    private String resultPath;//图片最终位置


    private TitleBar titleBar;
    private EditText etUserName;
    private EditText etIdCardNumber;
    private LinearLayout layoutChooseCity;
    private TextView tvCity;
    private EditText etServerDescribe;
    private ImageView ivCard;
    private TextView tvSubmit;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-12-16 02:03:42 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etIdCardNumber = (EditText) findViewById(R.id.et_id_card_number);
        layoutChooseCity = (LinearLayout) findViewById(R.id.layout_choose_city);
        tvCity = (TextView) findViewById(R.id.tv_city);
        etServerDescribe = (EditText) findViewById(R.id.et_server_describe);
        ivCard = (ImageView) findViewById(R.id.iv_card);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        cityUtil = new CityUtil(this);
        popWindowUtils = new PopWindowUtils(this);
        context = this;
        provinceList = cityUtil.getProvinceList();
        cityList = cityUtil.getCityList(provinceList.get(0));
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        //裁剪后的图片地址

        findViews();
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
                context.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

        layoutChooseCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChooseCityActivity.class);
                intent.putExtra(ExtraConstants.EXTRA_CITY_NAME, cityNameStr);
                startActivityForResult(intent, ACTION_CHOOSE_CITY);
            }
        });


        ivCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtils.startPickPhoto(context, imageList, 1, false);
            }
        });


        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = etUserName.getText().toString();
                final String idCardNumber = etIdCardNumber.getText().toString();
                final String serverDescribe = etServerDescribe.getText().toString();
                if (TextUtils.isEmpty(resultPath)) {
                    Toast.makeText(context, "请选择身份证正面图片！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(context, "请输入姓名！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(idCardNumber)) {
                    Toast.makeText(context, "请输入身份证号码！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(cityNameStr)) {
                    Toast.makeText(context, "请选择城市", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(serverDescribe)) {
                    Toast.makeText(context, "请输入服务介绍！", Toast.LENGTH_SHORT).show();
                } else {

                    final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(context);
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressDialogUtil.show("正在处理...");
                        }

                        @Override
                        protected String doInBackground(String... params) {
                            String result = "";
                            try {
                                result = ImageUtils.saveBitmap2file(context, ImageUtils.compressImage(context, params[0]));
                            } catch (Exception e) {
                                result = "";
                            } finally {
                                return result;
                            }
                        }

                        @Override
                        protected void onPostExecute(String compressPath) {
                            if (!TextUtils.isEmpty(compressPath)) {
                                File file = new File(compressPath);
                                TypedFile fileToSend = new TypedFile(ImageUtils.mimeType, file);
                                UserRetrofitUtil.fillInfoJoinTeam(context, userid, userName, cityNameStr, idCardNumber, 0 + "", -1, -1, serverDescribe, "-1", -1 + "", -1 + "", fileToSend, fileToSend, new NetCallback<NetWorkResultBean<String>>(context) {
                                    @Override
                                    public void onFailure(RetrofitError error, String message) {
                                        progressDialogUtil.hide();
                                    }

                                    @Override
                                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                        progressDialogUtil.hide();
                                        Toast.makeText(context, "认证申请已经提交！", Toast.LENGTH_SHORT).show();
                                        context.finish();
                                    }
                                });
                            } else {
                                progressDialogUtil.hide();
                            }
                        }
                    }.execute(resultPath);
                }
            }
        });
    }

    private void setUp() {

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("实名认证", R.drawable.arrow_left, -1, "", "");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case ACTION_CHOOSE_CITY:
                if (resultCode == Activity.RESULT_OK) {
                    String provinceNameStr = data.getExtras().getString(ExtraConstants.EXTRA_PROVINCE_NAME);
                    cityNameStr = data.getExtras().getString(ExtraConstants.EXTRA_CITY_NAME);
                    if (!cityNameStr.contains(provinceNameStr)) {
                        cityNameStr = provinceNameStr + cityNameStr;
                    }
                    if (tvCity != null) {
                        tvCity.setText(cityNameStr);
                    }
                } else {
                    initLocationManager();
                }
                break;

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
                        ImageLoader.getInstance().displayImage("file://" + resultPath, ivCard, ImageUtils.getOptions());
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
                String province = aMapLocation.getProvince();
                String city = aMapLocation.getCity();
                if (city.contains(province)) {
                    cityNameStr = city;
                    tvCity.setText(city);
                } else {
                    cityNameStr = province + city;
                    tvCity.setText(province + city);
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
