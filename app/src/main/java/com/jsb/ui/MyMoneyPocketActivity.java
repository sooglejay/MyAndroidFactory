package com.jsb.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jsb.Bean.aaa_MyMoneyPocketBean;
import com.jsb.R;
import com.jsb.adapter.MyMoneyPacketListAdapter;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

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


        aaa_MyMoneyPocketBean bean1 = new aaa_MyMoneyPocketBean();
        aaa_MyMoneyPocketBean bean2 = new aaa_MyMoneyPocketBean();
        aaa_MyMoneyPocketBean bean3 = new aaa_MyMoneyPocketBean();
        bean1.setMoneyKind("车险");
        bean2.setMoneyKind("驾驶险");
        bean3.setMoneyKind("加班狗");

        bean1.setMoneyAmount(556.00f);
        bean2.setMoneyAmount(80.00f);
        bean3.setMoneyAmount(120.00f);

        mListDatas.add(bean1);
        mListDatas.add(bean2);
        mListDatas.add(bean3);


        myCallPoliceListAdapter = new MyMoneyPacketListAdapter(this, mListDatas);
        mInsureList = (ListView)findViewById(R.id.list_view);
        mInsureList.setAdapter(myCallPoliceListAdapter);
    }
}
