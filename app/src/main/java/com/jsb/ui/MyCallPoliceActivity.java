package com.jsb.ui;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.jsb.R;
import com.jsb.adapter.MyCallPoliceListAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.event.BusEvent;
import com.jsb.fragment.DialogFragmentCreater;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.ReportData;
import com.jsb.model.ReportableInsurance;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.UIUtils;
import com.jsb.widget.AutoListView;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的-我的报案
 */
public class MyCallPoliceActivity extends BaseActivity implements
        AMapLocationListener {
    private LocationManagerProxy mLocationManagerProxy;

    public static final int REQUEST_CODE_CALL = 1000;
    private SwipeRefreshLayout swipeLayout;
    private AutoListView list_view;

    private TitleBar titleBar;
    private MyCallPoliceListAdapter myCallPoliceListAdapter;
    private List<Object> mListDatas = new ArrayList<>();
    private DialogFragmentCreater dialogFragmentCreater;

    private int userid = -1;

    double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_call_police);
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyCallPoliceActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListDatas.clear();
                getReportableInsurance(userid);
            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("我的报案", R.drawable.arrow_left, -1, "", "");

        dialogFragmentCreater = new DialogFragmentCreater();
        dialogFragmentCreater.setDialogContext(this, getSupportFragmentManager());


        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        list_view = (AutoListView) findViewById(R.id.list_view);
        list_view.setLoading(false);

        myCallPoliceListAdapter = new MyCallPoliceListAdapter(this, mListDatas, dialogFragmentCreater);
        list_view.setAdapter(myCallPoliceListAdapter);

        getReportableInsurance(userid);
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
        if (requestCode == REQUEST_CODE_CALL) {
            //重新生成一个 DialogFragment
            DialogFragmentCreater dialogFragmentCreater = new DialogFragmentCreater();
            dialogFragmentCreater.setDialogContext(this, getSupportFragmentManager());
            myCallPoliceListAdapter.setResultDialg(dialogFragmentCreater);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void getReportableInsurance(int userid) {
        UserRetrofitUtil.getReportableInsurance(this, userid, new NetCallback<NetWorkResultBean<ReportData>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                if (!TextUtils.isEmpty(message)) {
                    Toast.makeText(MyCallPoliceActivity.this, message, Toast.LENGTH_SHORT).show();

                }
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void success(NetWorkResultBean<ReportData> reportDataNetWorkResultBean, Response response) {
                ReportData data = reportDataNetWorkResultBean.getData();
                if (data.getReportableInsurance() != null) {
                    ReportableInsurance reportableInsurance = data.getReportableInsurance();
                    if (reportableInsurance.getOvertimeReportableData() != null) {
                        mListDatas.addAll(data.getReportableInsurance().getOvertimeReportableData());
                    }
                    if (reportableInsurance.getDriverReportableData() != null) {
                        mListDatas.addAll(data.getReportableInsurance().getDriverReportableData());
                    }
                    if (reportableInsurance.getVehicleReportableData() != null) {
                        mListDatas.addAll(data.getReportableInsurance().getVehicleReportableData());
                    }
                    myCallPoliceListAdapter.notifyDataSetChanged();
                }
                swipeLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            // 定位成功回调信息，设置相关消息
            lat = aMapLocation.getLatitude();
            lng = aMapLocation.getLongitude();
            myCallPoliceListAdapter.checkLocation(lat,lng);
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
        if(mLocationManagerProxy!=null) {// 移除定位请求
            mLocationManagerProxy.removeUpdates(this);
            // 销毁定位
            mLocationManagerProxy.destroy();
        }
    }

    /**
     * EventBus 广播
     *
     * @param event
     */
    public void onEventMainThread(BusEvent event) {
        switch (event.getMsg()) {
            case BusEvent.MSG_RefreshDataInCallPolice:
                initLocationManager();
                break;
            default:
                break;
        }
    }


}
