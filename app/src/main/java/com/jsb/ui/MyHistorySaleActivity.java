package com.jsb.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.adapter.MyHistorySaleAdapter;
import com.jsb.Bean.aaa_HistorySaleBean;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.HistoryPriceData;
import com.jsb.model.InsuranceCompanyInfo;
import com.jsb.model.Insurancecompanyprice;
import com.jsb.model.NetWorkResultBean;
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
 * 我的-我的历史记录
 */
public class MyHistorySaleActivity extends BaseActivity {

    private TitleBar titleBar;

    private List<Vehicleordertable> mDatas = new ArrayList<>();
    private MyHistorySaleAdapter mAdapter;

    private SwipeRefreshLayout swipeLayout;
    private AutoListView list_view;

    private View footerOperationView;
    private TextView tv_delete;
    private TextView tv_cancel;


    private int userid = -1;
    private int pageSize = 10;
    private int pageNum = 1;

    private Activity context;

    private boolean isAllSelected = false;//全选操作 的辅助变量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history_sale);

        context = this;
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyHistorySaleActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {
                //如果不为空，说明是 全选按钮
                if (!TextUtils.isEmpty(titleBar.getRightTv())) {
                    if (!isAllSelected) {
                        isAllSelected = true;
                        for (Vehicleordertable bean : mDatas) {
                            bean.setSuper_status(MyHistorySaleAdapter.VISIBLE_SELECTED);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        isAllSelected = false;
                        for (Vehicleordertable bean : mDatas) {
                            bean.setSuper_status(MyHistorySaleAdapter.VISIBLE_UNSELECTED);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteItem(true);
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteItem(false);
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                loadDataFromNet(userid);
            }
        });
        list_view.setOnLoadListener(new AutoListView.OnLoadListener() {
            @Override
            public void onLoad() {
                if (swipeLayout != null && !swipeLayout.isRefreshing()) {
                    list_view.setLoading(true);
                    loadDataFromNet(userid);
                }
            }
        });

    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);


        footerOperationView = findViewById(R.id.layout_operate);
        footerOperationView.setVisibility(View.GONE);
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);

        titleBar.initTitleBarInfo("历史报价", R.drawable.arrow_left, -1, "", "");

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        list_view = (AutoListView) findViewById(R.id.list_view);
        list_view.setLoading(false);

        mAdapter = new MyHistorySaleAdapter(this, mDatas, footerOperationView, list_view, titleBar);
        list_view.setAdapter(mAdapter);
        loadDataFromNet(userid);
    }

    private void loadDataFromNet(int userid) {
        swipeLayout.setEnabled(false);
        UserRetrofitUtil.getPriceHistoryList(this, userid, pageSize, pageNum, new NetCallback<NetWorkResultBean<HistoryPriceData>>(this) {
                    @Override
                    public void onFailure(RetrofitError error) {
                        swipeLayout.setRefreshing(false);
                        swipeLayout.setEnabled(true);
                        list_view.setLoading(false);
                    }

                    @Override
                    public void success(NetWorkResultBean<HistoryPriceData> historyPriceDataNetWorkResultBean, Response response) {
                        //结束下拉刷新
                        swipeLayout.setRefreshing(false);
                        swipeLayout.setEnabled(true);
                        list_view.setLoading(false);
                        HistoryPriceData bean = historyPriceDataNetWorkResultBean.getData();
                        if (bean.getVehicleorderAmount() > 0) {
                            mDatas.addAll(bean.getVehicleorderRecords());
                            pageNum++;
                        } else {
                            for (int i = 0; i < 4; i++) {
                                Vehicleordertable b = new Vehicleordertable();
                                Insurancecompanyprice p = new Insurancecompanyprice();
                                InsuranceCompanyInfo c = new InsuranceCompanyInfo();
                                c.setCompanyname("mock");
                                p.setCompany(c);
                                b.setInsurancecompanyprices(p);
                                b.setStatus(0);
                                b.setMoney(100.0f);
                                mDatas.add(b);
                            }
                            for (int i = 0; i < 4; i++) {
                                Vehicleordertable b = new Vehicleordertable();
                                Insurancecompanyprice p = new Insurancecompanyprice();
                                InsuranceCompanyInfo c = new InsuranceCompanyInfo();
                                c.setCompanyname("付出卡");
                                p.setCompany(c);
                                b.setInsurancecompanyprices(p);
                                b.setDeleted(1);
                                b.setMoney(100.0f);
                                mDatas.add(b);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        if (footerOperationView != null && footerOperationView.getVisibility() == View.VISIBLE) {
            footerOperationView.setVisibility(View.GONE);
            if (titleBar != null)
            {
                titleBar.setRightTv("", -1);
            }
            for (Vehicleordertable bean : mDatas) {
                bean.setSuper_status(MyHistorySaleAdapter.GONE_UNSELECTED);
            }
            mAdapter.notifyDataSetChanged();
        }else {
            super.onBackPressed();
        }
    }
}
