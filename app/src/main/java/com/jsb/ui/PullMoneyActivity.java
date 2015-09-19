package com.jsb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
    private LinearLayout layout_add_new_card;
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
              BrowserActivity.startActivity(PullMoneyActivity.this,true);
            }
        });
    }

    private void setUp() {
        mListView = (ListView)findViewById(R.id.card_listview);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("取钱", R.drawable.arrow_left, -1, "", "取钱说明");

        mDatas.add("火热来咯阿萨克斯");
        mDatas.add("火热来咯阿萨克斯");
        mDatas.add("火热来咯阿萨克斯");
        mDatas.add("火热来咯阿萨克斯");

        cardListAdapter = new CardListAdapter(this,mDatas);
        mListView.setAdapter(cardListAdapter);

        layout_add_new_card = (LinearLayout)findViewById(R.id.layout_add_new_card);
        layout_add_new_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PullMoneyActivity.this,AddCardActivity.class));
            }
        });

    }
}
