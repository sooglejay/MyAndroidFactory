package com.jsb.ui;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.jsb.R;
import com.jsb.constant.ExtraConstants;
import com.jsb.util.ImageUtils;
import com.jsb.widget.TitleBar;
import com.jsb.widget.imagepicker.MultiImageSelectorActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/12/14.
 */
public class ApplyEcoActivity extends BaseActivity implements
        AMapLocationListener {
    private LocationManagerProxy mLocationManagerProxy;


    private Activity activity;
    private ArrayList<String> imageList = new ArrayList<>();
    private String resultPath;//图片最终位置


    private TitleBar titleBar;
    private EditText etPhoneNumber;
    private EditText etCompanyAddress;
    private ImageView ivLocation;
    private EditText etZhizhaobianhao;
    private EditText etManage;
    private EditText etManagePhone;
    private EditText etServerDescribe;
    private ImageView ivCard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_eco);
        activity = this;
        findViews();
        setUpViews();
        setUpListeners();
        initLocationManager();
    }

    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        etCompanyAddress = (EditText) findViewById(R.id.et_company_address);
        ivLocation = (ImageView) findViewById(R.id.iv_location);
        etZhizhaobianhao = (EditText) findViewById(R.id.et_zhizhaobianhao);
        etManage = (EditText) findViewById(R.id.et_manage);
        etManagePhone = (EditText) findViewById(R.id.et_manage_phone);
        etServerDescribe = (EditText) findViewById(R.id.et_server_describe);
        ivCard = (ImageView) findViewById(R.id.iv_card);

    }

    private void setUpViews() {
        titleBar.initTitleBarInfo("申请合作", R.drawable.arrow_left, -1, "", "确定");

    }

    private void setUpListeners() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {
                Toast.makeText(activity, "没有接口可用！", Toast.LENGTH_SHORT).show();

            }
        });


        ivCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUtils.startPickPhoto(activity, imageList, 1, false);

            }
        });

        ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLocationManager();
            }
        });


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
                etCompanyAddress.setText(aMapLocation.getPoiName());
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
