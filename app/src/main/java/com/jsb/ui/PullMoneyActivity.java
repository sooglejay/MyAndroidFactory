package com.jsb.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jsb.R;
import com.jsb.adapter.CardListAdapter;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/13.
 */
public class PullMoneyActivity extends BaseActivity {

    private TitleBar titleBar;
    private ListView mListView;
    private CardListAdapter cardListAdapter;
    private List<String>mDatas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_money);
        setUp();
        setLisenter();
    }

    private void setLisenter() {


        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                PullMoneyActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        mListView = (ListView)findViewById(R.id.card_listview);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("取钱", R.drawable.back_arrow, -1, "", "取钱说明");

        mDatas.add("火热来咯阿萨克斯");
        mDatas.add("火热来咯阿萨克斯");
        mDatas.add("火热来咯阿萨克斯");
        mDatas.add("火热来咯阿萨克斯");

        cardListAdapter = new CardListAdapter(this,mDatas);
        mListView.setAdapter(cardListAdapter);

    }
}
