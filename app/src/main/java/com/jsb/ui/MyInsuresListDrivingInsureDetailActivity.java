package com.jsb.ui;

import android.os.Bundle;
import android.view.View;

import com.jsb.R;
import com.jsb.widget.TitleBar;

/**
 * 我的-我的保险-车险
 */
public class MyInsuresListDrivingInsureDetailActivity extends BaseActivity {

    private TitleBar mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_insure_driving);

        mTitleBar = (TitleBar)findViewById(R.id.title_bar);
        mTitleBar.initTitleBarInfo("产品详情", R.drawable.arrow_left, -1, "", "");
        mTitleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyInsuresListDrivingInsureDetailActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

    }

}
