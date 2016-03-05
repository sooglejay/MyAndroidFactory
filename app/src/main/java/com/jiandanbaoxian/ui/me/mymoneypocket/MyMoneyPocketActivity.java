package com.jiandanbaoxian.ui.me.mymoneypocket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.bean.aaa_MyMoneyPocketBean;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.MyMoneyPacketListAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.MyWalletData;
import com.jiandanbaoxian.model.NetWorkResultBean;
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
 * 我的-我的钱包
 */
public class MyMoneyPocketActivity extends BaseActivity {
    public static final int ACTION_TO_PULL_MONEY = 1000;

    private SwipeRefreshLayout swipeLayout;
    private AutoListView list_view;
    private TextView noResultsView;

    private TitleBar titleBar;
    private MyMoneyPacketListAdapter myCallPoliceListAdapter;
    private List<aaa_MyMoneyPocketBean> mListDatas = new ArrayList<>();
    private int userid;

    ProgressDialogUtil progressDialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pocket);
        progressDialogUtil = new ProgressDialogUtil(this);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyMoneyPocketActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMywalletInfo(userid);
            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("我的钱包", R.drawable.arrow_left, -1, "", "");


        myCallPoliceListAdapter = new MyMoneyPacketListAdapter(this, mListDatas);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        list_view = (AutoListView) findViewById(R.id.list_view);
        list_view.setAdapter(myCallPoliceListAdapter);
        list_view.setLoading(false);
        noResultsView = (TextView) findViewById(R.id.emptyElement);
        list_view.setEmptyView(noResultsView);

        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        getMywalletInfo(userid);
    }

    private void getMywalletInfo(int userid) {
        progressDialogUtil.show("正在获取数据...");
        UserRetrofitUtil.getMywalletInfo(this, userid, new NetCallback<NetWorkResultBean<Object>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                if (!TextUtils.isEmpty(message)) {
                    Toast.makeText(MyMoneyPocketActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                swipeLayout.setRefreshing(false);
                progressDialogUtil.hide();
            }

            @Override
            public void success(NetWorkResultBean<Object> myWalletDataNetWorkResultBean, Response response) {


                if (myWalletDataNetWorkResultBean != null) {
                    int status = myWalletDataNetWorkResultBean.getStatus();
                    switch (status) {
                        case HttpsURLConnection.HTTP_OK:

                            if (myWalletDataNetWorkResultBean.getData() != null) {
                                MyWalletData bean = JsonUtil.getSerializedObject(myWalletDataNetWorkResultBean.getData(),MyWalletData.class);
                                mListDatas.clear();

                                aaa_MyMoneyPocketBean b1 = new aaa_MyMoneyPocketBean();
                                if (bean.getManageFee() != null) {
                                    b1.setMoneyAmount(bean.getManageFee());
                                } else {
                                    b1.setMoneyAmount(0);
                                }
                                b1.setType(3);
                                b1.setMoneyKind("管理费");
                                mListDatas.add(b1);

                                aaa_MyMoneyPocketBean b2 = new aaa_MyMoneyPocketBean();
                                if (bean.getOvertimeFee() != null) {
                                    b2.setMoneyAmount(bean.getOvertimeFee());
                                } else {
                                    b2.setMoneyAmount(0);
                                }
                                b2.setMoneyKind("加班险费");
                                b2.setType(2);

                                mListDatas.add(b2);

                                aaa_MyMoneyPocketBean b3 = new aaa_MyMoneyPocketBean();
                                if (bean.getPaueFee() != null) {
                                    b3.setMoneyAmount(bean.getPaueFee());
                                } else {
                                    b3.setMoneyAmount(0);
                                }
                                b3.setMoneyKind("停保费");
                                b3.setType(1);
                                mListDatas.add(b3);

                                aaa_MyMoneyPocketBean b4 = new aaa_MyMoneyPocketBean();
                                if (bean.getWorkFee() != null) {
                                    b4.setMoneyAmount(bean.getWorkFee());
                                } else {
                                    b4.setMoneyAmount(0);
                                }
                                b4.setType(3);

                                b4.setMoneyKind("销售业绩费");
                                mListDatas.add(b4);


                                aaa_MyMoneyPocketBean b5 = new aaa_MyMoneyPocketBean();
                                if (bean.getShareFee() != null) {
                                    b5.setMoneyAmount(bean.getShareFee());
                                } else {
                                    b5.setMoneyAmount(0);
                                }
                                b5.setMoneyKind("分享费");
                                b5.setType(3);

                                mListDatas.add(b5);

                                myCallPoliceListAdapter.notifyDataSetChanged();
                                swipeLayout.setRefreshing(false);
                                progressDialogUtil.hide();
                            }
                            break;
                        default:
                            Toast.makeText(MyMoneyPocketActivity.this, myWalletDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                            break;
                    }
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTION_TO_PULL_MONEY:
                getMywalletInfo(userid);
                break;
        }
    }
}
