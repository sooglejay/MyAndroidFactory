package com.jsb.ui;

import android.os.Bundle;
import android.view.View;

import com.jsb.R;
import com.jsb.widget.TitleBar;

/**
 * 首页-分享
 */
public class ShareActivity extends BaseActivity {

    private TitleBar titleBar = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        setUp();
    }
    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("分享",R.drawable.arrow_left,-1,"","");

        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                ShareActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

}
