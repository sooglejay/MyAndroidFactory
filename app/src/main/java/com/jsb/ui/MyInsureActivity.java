package com.jsb.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.MyInsuresListAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.Driverordertable;
import com.jsb.model.MyInsuranceData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.Overtimeordertable;
import com.jsb.model.Vehicleordertable;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.UIUtils;
import com.jsb.widget.AutoListView;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的-我的保险
 */
public class MyInsureActivity extends BaseActivity {

    private TitleBar titleBar;
    private SwipeRefreshLayout swipeLayout;
    private AutoListView mInsureList;
    private MyInsuresListAdapter myInsuresListAdapter;
    private List<Object> mInsureListDatas = new ArrayList<>();

    private List<Vehicleordertable> vehicleordertables = new ArrayList<>();
    private List<Driverordertable> driverordertables = new ArrayList<>();
    private List<Overtimeordertable> overtimeordertables = new ArrayList<>();


    private int pageSize = 10;


    //起始页
    private int pageNum_vehicleordertables = 1;
    private int pageNum_driverordertables = 1;
    private int pageNum_overtimeordertables = 1;


    private int success_count_v = 0;//车险
    private int success_count_d = 0;//驾驶险
    private int success_count_o = 0;//加班险

    private int userid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_insure);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyInsureActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        userid = PreferenceUtil.load(MyInsureActivity.this, PreferenceConstant.userid, -1);


        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("我的保险", R.drawable.arrow_left, -1, "", "");

        myInsuresListAdapter = new MyInsuresListAdapter(this, mInsureListDatas, vehicleordertables, driverordertables, overtimeordertables);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        mInsureList = (AutoListView) findViewById(R.id.list_view);
        mInsureList.setAdapter(myInsuresListAdapter);
        mInsureList.setLoading(false);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                driverordertables.clear();
                overtimeordertables.clear();
                vehicleordertables.clear();
                pageNum_vehicleordertables = 1;
                pageNum_driverordertables = 1;
                pageNum_overtimeordertables = 1;
                loadDataFromNet(userid);
            }
        });
        mInsureList.setOnLoadListener(new AutoListView.OnLoadListener() {
            @Override
            public void onLoad() {
                if (swipeLayout != null && !swipeLayout.isRefreshing()) {
                    mInsureList.setLoading(true);
                    loadDataFromNet(userid);
                }
            }
        });

        loadDataFromNet(userid);
    }

    /**
     * 请求网络数据
     *
     * @param userid
     */
    private void loadDataFromNet(int userid) {
        getVehicleOrderByPage(userid, pageSize, pageNum_vehicleordertables);
        getDriverOrderByPage(userid, pageSize, pageNum_driverordertables);
        getOvertimeOrderByPage(userid, pageSize, pageNum_overtimeordertables);
    }


    /**
     * 分页获取车险信息
     *
     * @param userid
     * @param pageSize
     * @param pageNum
     */
    private void getVehicleOrderByPage(final int userid, final int pageSize, int pageNum) {
        swipeLayout.setEnabled(false);
        UserRetrofitUtil.getVehicleOrderByPage(this, userid, pageSize, pageNum, new NetCallback<NetWorkResultBean<MyInsuranceData>>(this) {
            @Override
            public void onFailure(RetrofitError error,String message) {
                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);
                if(!TextUtils.isEmpty(message)) {
                    Toast.makeText(MyInsureActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void success(NetWorkResultBean<MyInsuranceData> myInsuranceDataNetWorkResultBean, Response response) {
                //结束下拉刷新
                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);
                if (myInsuranceDataNetWorkResultBean.getData().getVehicleorderRecords() != null ) {
                    vehicleordertables.addAll(myInsuranceDataNetWorkResultBean.getData().getVehicleorderRecords());
                    pageNum_vehicleordertables++;
                    getVehicleOrderByPage(userid, pageSize, pageNum_vehicleordertables);
                }else {
                    success_count_v = 1;
                    if(success_count_d+success_count_o+success_count_v==3)
                    {
                        myInsuresListAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }


    /**
     * 分页获取驾驶险信息
     *
     * @param userid
     * @param pageSize
     * @param pageNum
     */
    private void getDriverOrderByPage(final int userid, final int pageSize, int pageNum) {
        swipeLayout.setEnabled(false);
        UserRetrofitUtil.getDriverOrderByPage(this, userid, pageSize, pageNum, new NetCallback<NetWorkResultBean<MyInsuranceData>>(this) {
            @Override
            public void onFailure(RetrofitError error,String message) {

                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);
                if(!TextUtils.isEmpty(message)) {
                    Toast.makeText(MyInsureActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void success(NetWorkResultBean<MyInsuranceData> myInsuranceDataNetWorkResultBean, Response response) {
                //结束下拉刷新
                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);
                if (myInsuranceDataNetWorkResultBean.getData().getDriverorderRecords() != null) {
                    driverordertables.addAll(myInsuranceDataNetWorkResultBean.getData().getDriverorderRecords());
                    pageNum_driverordertables++;
                    getDriverOrderByPage(userid, pageSize, pageNum_driverordertables);
                }else {
                    success_count_d = 1;
                    if(success_count_d+success_count_o+success_count_v==3)
                    {
                        myInsuresListAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }

    /**
     * 分页获取加班险信息
     *
     * @param userid
     * @param pageSize
     * @param pageNum
     */
    private void getOvertimeOrderByPage(final int userid, final int pageSize, int pageNum) {
        swipeLayout.setEnabled(false);
        UserRetrofitUtil.getOvertimeOrderByPage(this, userid, pageSize, pageNum, new NetCallback<NetWorkResultBean<MyInsuranceData>>(this) {
            @Override
            public void onFailure(RetrofitError error,String message) {
                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);
                if(!TextUtils.isEmpty(message)) {
                    Toast.makeText(MyInsureActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void success(NetWorkResultBean<MyInsuranceData> myInsuranceDataNetWorkResultBean, Response response) {
                //结束下拉刷新
                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);

                if (myInsuranceDataNetWorkResultBean.getData().getOvertimeorderRecords() != null) {
                    overtimeordertables.addAll(myInsuranceDataNetWorkResultBean.getData().getOvertimeorderRecords());
                    pageNum_overtimeordertables++;
                    getOvertimeOrderByPage(userid, pageSize, pageNum_overtimeordertables);
                }
                else {
                    success_count_o = 1;
                    if(success_count_d+success_count_o+success_count_v==3)
                    {
                        myInsuresListAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }

}
