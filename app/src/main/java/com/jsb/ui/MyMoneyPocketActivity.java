package com.jsb.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jsb.Bean.aaa_MyMoneyPocketBean;
import com.jsb.R;
import com.jsb.adapter.MyMoneyPacketListAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.MyWalletData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.util.PreferenceUtil;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的-我的钱包
 */
public class MyMoneyPocketActivity extends BaseActivity {
    private ListView mInsureList;
    private TitleBar titleBar;
    private MyMoneyPacketListAdapter myCallPoliceListAdapter;
    private List<aaa_MyMoneyPocketBean> mListDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pocket);
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
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("我的钱包", R.drawable.arrow_left, -1, "", "");


        myCallPoliceListAdapter = new MyMoneyPacketListAdapter(this, mListDatas);
        mInsureList = (ListView) findViewById(R.id.list_view);
        mInsureList.setAdapter(myCallPoliceListAdapter);


        int userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        getMywalletInfo(userid);
    }

    private void getMywalletInfo(int userid) {
        UserRetrofitUtil.getMywalletInfo(this, 1, new NetCallback<NetWorkResultBean<MyWalletData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<MyWalletData> myWalletDataNetWorkResultBean, Response response) {
                MyWalletData bean = myWalletDataNetWorkResultBean.getData();
                mListDatas.clear();

                aaa_MyMoneyPocketBean b1 = new aaa_MyMoneyPocketBean();
                if (bean.getManageFee() != null) {
                    b1.setMoneyAmount(bean.getManageFee());
                } else {
                    b1.setMoneyAmount(0);
                }
                b1.setMoneyKind("管理费");
                mListDatas.add(b1);

                aaa_MyMoneyPocketBean b2 = new aaa_MyMoneyPocketBean();
                if (bean.getOvertimeFee() != null) {
                    b2.setMoneyAmount(bean.getOvertimeFee());
                } else {
                    b2.setMoneyAmount(0);
                }
                b2.setMoneyKind("加班险费");
                mListDatas.add(b2);

                aaa_MyMoneyPocketBean b3 = new aaa_MyMoneyPocketBean();
                if (bean.getPaueFee() != null) {
                    b3.setMoneyAmount(bean.getPaueFee());
                } else {
                    b3.setMoneyAmount(0);
                }
                b3.setMoneyKind("停保费");
                mListDatas.add(b3);

                aaa_MyMoneyPocketBean b4 = new aaa_MyMoneyPocketBean();
                if (bean.getWorkFee() != null) {
                    b4.setMoneyAmount(bean.getWorkFee());
                } else {
                    b4.setMoneyAmount(0);
                }
                b4.setMoneyKind("销售业绩费");
                mListDatas.add(b4);


                aaa_MyMoneyPocketBean b5 = new aaa_MyMoneyPocketBean();
                if (bean.getShareFee() != null) {
                    b5.setMoneyAmount(bean.getShareFee());
                } else {
                    b5.setMoneyAmount(0);
                }
                b5.setMoneyKind("分享费");
                mListDatas.add(b5);


                myCallPoliceListAdapter.notifyDataSetChanged();

            }
        });
    }
}
