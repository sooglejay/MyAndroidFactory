package com.jiandanbaoxian.ui.me.mycallpolice;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.MyCallPoliceListAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.fragment.DialogFragmentCreater;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Overtimeordertable;
import com.jiandanbaoxian.model.ReportData;
import com.jiandanbaoxian.model.ReportableInsurance;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.AutoListView;
import com.jiandanbaoxian.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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
    private TextView noResultsView;

    private TitleBar titleBar;
    private MyCallPoliceListAdapter myCallPoliceListAdapter;
    private List<Object> mListDatas = new ArrayList<>();
    private DialogFragmentCreater dialogFragmentCreater;

    private int userid = -1;

    double lat, lng;
    private Overtimeordertable overtimeReportableData;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_call_police);
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        activity = this;
        setUp();
        setLisenter();
        getReportableInsurance(userid);
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
        myCallPoliceListAdapter.setCallBack(new MyCallPoliceListAdapter.JiaBanGouBaoAnCallBack() {
            @Override
            public void onClick(Overtimeordertable object) {
                overtimeReportableData = object;
                initLocationManager();
            }
        });
        list_view.setAdapter(myCallPoliceListAdapter);
        noResultsView = (TextView) findViewById(R.id.emptyElement);
        list_view.setEmptyView(noResultsView);

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
        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(activity);
        progressDialogUtil.show("正在获取信息...");
        UserRetrofitUtil.getReportableInsurance(this, userid, new NetCallback<NetWorkResultBean<Object>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                progressDialogUtil.hide();
                swipeLayout.setRefreshing(false);
                Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void success(NetWorkResultBean<Object> reportDataNetWorkResultBean, Response response) {
                progressDialogUtil.hide();

                if (reportDataNetWorkResultBean != null) {
                    int status = reportDataNetWorkResultBean.getStatus();
                    switch (status) {
                        case HttpsURLConnection.HTTP_OK:
                            if (reportDataNetWorkResultBean.getData() != null) {
                                mListDatas.clear();
                                ReportData data = (ReportData) reportDataNetWorkResultBean.getData();
                                if (data.getReportableInsurance() != null) {
                                    ReportableInsurance reportableInsurance = data.getReportableInsurance();
                                    if (reportableInsurance.getOvertimeReportableData() != null) {
                                        overtimeReportableData = reportableInsurance.getOvertimeReportableData();
                                        mListDatas.add(overtimeReportableData);
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
                            break;
                        default:
                            Toast.makeText(activity, reportDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                            break;
                    }
                }


            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            // 定位成功回调信息，设置相关消息
            lat = aMapLocation.getLatitude();
            lng = aMapLocation.getLongitude();
            double lat_o = overtimeReportableData.getLat();
            double lng_o = overtimeReportableData.getLng();
            if (Math.abs(lat_o - lat) < 0.5 && Math.abs(lng_o - lng) < 0.5) {

                UserRetrofitUtil.reportOvertime(activity, userid, overtimeReportableData.getId(), new NetCallback<NetWorkResultBean<Object>>(activity) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void success(NetWorkResultBean<Object> stringNetWorkResultBean, Response response) {

                        if (stringNetWorkResultBean != null) {
                            int status = stringNetWorkResultBean.getStatus();
                            switch (status) {
                                case HttpsURLConnection.HTTP_OK:
                                    String message;
                                    if (TextUtils.isEmpty(message = stringNetWorkResultBean.getMessage().toString())) {
                                        if (message.contains("ok")) {
                                            Toast.makeText(activity, "报案成功！经纬度差在0.1之内", Toast.LENGTH_SHORT).show();
                                            getReportableInsurance(userid);
                                        } else {
                                            Toast.makeText(activity, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    break;
                                default:
                                    Toast.makeText(activity, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }


                    }
                });
            } else {
                Toast.makeText(activity, "报案失败！经度差：" + Math.abs(lat_o - lat) + "    纬度差：" + Math.abs(lng_o - lng), Toast.LENGTH_SHORT).show();
            }
        } else {

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
