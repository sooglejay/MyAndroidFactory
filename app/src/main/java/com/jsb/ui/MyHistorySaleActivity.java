package com.jsb.ui;

import android.os.Bundle;
import android.view.View;

import com.jsb.R;
import com.jsb.widget.TitleBar;

/**
 * 我的-我的历史记录
 */
public class MyHistorySaleActivity extends BaseActivity {

    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history_sale);
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

            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("历史报价", R.drawable.arrow_left, -1, "", "");
    }

}
