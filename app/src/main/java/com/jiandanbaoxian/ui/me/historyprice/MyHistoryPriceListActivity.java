package com.jiandanbaoxian.ui.me.historyprice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.MyHistorySaleAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.event.BusEvent;
import com.jiandanbaoxian.model.HistoryPriceData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Vehicleordertable;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.JsonUtil;
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
 * 我的-我的历史记录
 */
public class MyHistoryPriceListActivity extends BaseActivity {

    private TitleBar titleBar;

    private List<Vehicleordertable> mDatas = new ArrayList<>();
    private MyHistorySaleAdapter mAdapter;

    private SwipeRefreshLayout swipeLayout;
    private AutoListView list_view;
    private TextView noResultsView;

    private View footerOperationView;
    private TextView tv_delete;
    private TextView tv_cancel;


    private int userid = -1;
    private int pageSize = 10;
    private int pageNum = 1;

    private Activity context;

    private boolean isAllSelected = false;//全选操作 的辅助变量

    private ProgressDialogUtil progressDialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history_sale);
        context = this;
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        progressDialogUtil = new ProgressDialogUtil(this, true);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyHistoryPriceListActivity.this.finish();
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
                mDatas.clear();
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

        titleBar.initTitleBarInfo(context.getResources().getString(R.string.my_order), R.drawable.arrow_left, -1, "", "");

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        list_view = (AutoListView) findViewById(R.id.list_view);
        list_view.setLoading(false);

        mAdapter = new MyHistorySaleAdapter(this, mDatas, footerOperationView, list_view, titleBar, progressDialogUtil);
        list_view.setAdapter(mAdapter);
        noResultsView = (TextView) findViewById(R.id.emptyElement);
        list_view.setEmptyView(noResultsView);


        loadDataFromNet(userid);
    }

    private void loadDataFromNet(int userid) {
        swipeLayout.setEnabled(false);
        progressDialogUtil.show("正在获取数据...");
        UserRetrofitUtil.getPriceHistoryList(this, userid, pageSize, pageNum, new NetCallback<NetWorkResultBean<Object>>(this) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        progressDialogUtil.hide();
                        swipeLayout.setRefreshing(false);
                        swipeLayout.setEnabled(true);
                        list_view.setLoading(false);
                        Toast.makeText(context, "请检查网络设置", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void success(NetWorkResultBean<Object> historyPriceDataNetWorkResultBean, Response response) {
                        progressDialogUtil.hide();

                        //结束下拉刷新
                        swipeLayout.setRefreshing(false);
                        swipeLayout.setEnabled(true);
                        list_view.setLoading(false);

                        if (historyPriceDataNetWorkResultBean != null) {
                            int status = historyPriceDataNetWorkResultBean.getStatus();
                            switch (status) {
                                case HttpsURLConnection.HTTP_OK:
                                    if (historyPriceDataNetWorkResultBean.getData() != null) {
                                        HistoryPriceData bean = JsonUtil.getSerializedObject(historyPriceDataNetWorkResultBean.getData(), HistoryPriceData.class);
                                        if (bean != null && bean.getVehicleorderRecords() != null) {
                                            List<Vehicleordertable> datas = bean.getVehicleorderRecords();
                                            for (Vehicleordertable b : datas) {
                                                b.setSuper_status(MyHistorySaleAdapter.GONE_UNSELECTED);
                                            }
                                            mDatas.addAll(datas);
                                            pageNum++;
                                        }
                                        mAdapter.notifyDataSetChanged();
                                    }
                                    break;
                                default:
                                    Toast.makeText(context, historyPriceDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }


                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        if (footerOperationView != null && footerOperationView.getVisibility() == View.VISIBLE) {
            footerOperationView.setVisibility(View.GONE);
            if (titleBar != null) {
                titleBar.setRightTv("", -1);
            }
            for (Vehicleordertable bean : mDatas) {
                bean.setSuper_status(MyHistorySaleAdapter.GONE_UNSELECTED);
            }
            mAdapter.notifyDataSetChanged();
        } else {
            super.onBackPressed();
        }
    }


    /**
     * EventBus 广播
     *
     * @param event
     */
    public void onEventMainThread(BusEvent event) {
        switch (event.getMsg()) {
            case BusEvent.MSG_RefreshDataInHistoryPrice:
                pageNum = 1;
                mDatas.clear();
                loadDataFromNet(userid);
                break;
            default:
                break;
        }
    }


}
