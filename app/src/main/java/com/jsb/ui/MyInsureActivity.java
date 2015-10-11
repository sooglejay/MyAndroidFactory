package com.jsb.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jsb.R;
import com.jsb.adapter.MyInsuresListAdapter;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的-我的保险
 */
public class MyInsureActivity extends BaseActivity {

    private TitleBar titleBar;
    private ListView mInsureList;
    private MyInsuresListAdapter myInsuresListAdapter;
    private List<String> mInsureListDatas = new ArrayList<>();

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
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("我的保险", R.drawable.arrow_left, -1, "", "");


        mInsureListDatas.add("车险");
        mInsureListDatas.add("驾驶险");
        mInsureListDatas.add("加班狗");
        myInsuresListAdapter = new MyInsuresListAdapter(this,mInsureListDatas);
        mInsureList = (ListView)findViewById(R.id.list_view);
        mInsureList.setAdapter(myInsuresListAdapter);

    }
}
