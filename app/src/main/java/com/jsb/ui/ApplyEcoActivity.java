package com.jsb.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jsb.R;
import com.jsb.widget.TitleBar;

/**
 * Created by JammyQtheLab on 2015/12/14.
 */
public class ApplyEcoActivity extends BaseActivity {
    private TitleBar titleBar;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_eco);
        activity = this;
        findViews();
        setUpViews();
        setUpListeners();
    }

    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);

    }

    private void setUpViews() {
        titleBar.initTitleBarInfo("申请合作", R.drawable.arrow_left, -1, "", "");

    }

    private void setUpListeners() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

    }

}
