package com.jsb.ui;

import android.os.Bundle;
import android.view.View;

import com.jsb.R;
import com.jsb.widget.TitleBar;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;

/**
 * Created by Administrator on 2015/9/19.
 */
public class MyInsureActivity extends BaseActivity {

    private TitleBar titleBar;

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
    }
}
