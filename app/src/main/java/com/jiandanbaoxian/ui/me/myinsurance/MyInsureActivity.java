package com.jiandanbaoxian.ui.me.myinsurance;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.MyInsuresListAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.Driverordertable;
import com.jiandanbaoxian.model.InsuranceItemData;
import com.jiandanbaoxian.model.MyInsuranceData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Overtimeordertable;
import com.jiandanbaoxian.model.Vehicleordertable;
import com.jiandanbaoxian.model.Vehicletable;
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
 * 我的-我的保险
 */
public class MyInsureActivity extends BaseActivity {

    private TitleBar titleBar;
    private SwipeRefreshLayout swipeLayout;
    private AutoListView mInsureList;
    private TextView noResultsView;

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
    ProgressDialogUtil progressDialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_insure);
        progressDialogUtil = new ProgressDialogUtil(this);
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
        noResultsView = (TextView) findViewById(R.id.emptyElement);
        mInsureList.setEmptyView(noResultsView);

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

        progressDialogUtil.show("正在获取数据...");
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
        UserRetrofitUtil.getVehicleOrderByPage(this, userid, pageSize, pageNum, new NetCallback<NetWorkResultBean<Object>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                progressDialogUtil.hide();
                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);
                Toast.makeText(MyInsureActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void success(NetWorkResultBean<Object> myInsuranceDataNetWorkResultBean, Response response) {
                //结束下拉刷新
                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);

                if (myInsuranceDataNetWorkResultBean != null) {
                    int status = myInsuranceDataNetWorkResultBean.getStatus();
                    switch (status) {
                        case HttpsURLConnection.HTTP_OK:

                            if (myInsuranceDataNetWorkResultBean.getData() != null) {
                                MyInsuranceData bean = (MyInsuranceData) myInsuranceDataNetWorkResultBean.getData();
                                vehicleordertables.addAll(bean.getVehicleorderRecords());
                                pageNum_vehicleordertables++;
                                getVehicleOrderByPage(userid, pageSize, pageNum_vehicleordertables);
                            } else {
                                success_count_v = 1;
                                if (success_count_d + success_count_o + success_count_v == 3) {
                                    progressDialogUtil.hide();
                                    myInsuresListAdapter.notifyDataSetChanged();
                                }
                            }

                            break;
                        default:
                            Toast.makeText(MyInsureActivity.this, myInsuranceDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                            break;
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
        UserRetrofitUtil.getDriverOrderByPage(this, userid, pageSize, pageNum, new NetCallback<NetWorkResultBean<Object>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                progressDialogUtil.hide();
                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);
                Toast.makeText(MyInsureActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void success(NetWorkResultBean<Object> myInsuranceDataNetWorkResultBean, Response response) {
                //结束下拉刷新
                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);
                if (myInsuranceDataNetWorkResultBean != null) {
                    int status = myInsuranceDataNetWorkResultBean.getStatus();
                    switch (status) {
                        case HttpsURLConnection.HTTP_OK:

                            if (myInsuranceDataNetWorkResultBean.getData() != null) {
                                MyInsuranceData bean = (MyInsuranceData) myInsuranceDataNetWorkResultBean.getData();
                                if (bean.getDriverorderRecords() != null) {
                                    driverordertables.addAll(bean.getDriverorderRecords());
                                    pageNum_driverordertables++;
                                    getDriverOrderByPage(userid, pageSize, pageNum_driverordertables);
                                } else {
                                    success_count_d = 1;
                                    if (success_count_d + success_count_o + success_count_v == 3) {
                                        progressDialogUtil.hide();
                                        myInsuresListAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            break;
                        default:
                            Toast.makeText(MyInsureActivity.this, myInsuranceDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                            break;
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
        UserRetrofitUtil.getOvertimeOrderByPage(this, userid, pageSize, pageNum, new NetCallback<NetWorkResultBean<Object>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                progressDialogUtil.hide();
                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);
                Toast.makeText(MyInsureActivity.this, "请检查网络设置", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void success(NetWorkResultBean<Object> myInsuranceDataNetWorkResultBean, Response response) {
                //结束下拉刷新
                swipeLayout.setRefreshing(false);
                swipeLayout.setEnabled(true);
                mInsureList.setLoading(false);

                if (myInsuranceDataNetWorkResultBean != null) {
                    int status = myInsuranceDataNetWorkResultBean.getStatus();
                    switch (status) {
                        case HttpsURLConnection.HTTP_OK:

                            if (myInsuranceDataNetWorkResultBean.getData() != null) {
                                MyInsuranceData bean = (MyInsuranceData) myInsuranceDataNetWorkResultBean.getData();
                                if (bean.getOvertimeorderRecords() != null) {
                                    overtimeordertables.addAll(bean.getOvertimeorderRecords());
                                    pageNum_overtimeordertables++;
                                    getOvertimeOrderByPage(userid, pageSize, pageNum_overtimeordertables);
                                } else {
                                    success_count_o = 1;
                                    if (success_count_d + success_count_o + success_count_v == 3) {
                                        progressDialogUtil.hide();
                                        myInsuresListAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            break;
                        default:
                            Toast.makeText(MyInsureActivity.this, myInsuranceDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                            break;
                    }
                }


            }
        });
    }

}
