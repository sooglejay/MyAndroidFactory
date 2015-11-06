package com.jsb.ui;

import android.os.Bundle;
import android.view.View;

import com.jsb.R;
import com.jsb.util.ShareUtils;
import com.jsb.util.ShareUtilsTest;
import com.jsb.util.UIUtils;
import com.jsb.widget.TitleBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 首页-分享
 */
public class ShareActivity extends BaseActivity {

    private TitleBar titleBar = null;
    private ShareUtilsTest shareUtilsTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        setUp();
    }
    private void setUp() {
        shareUtilsTest = new ShareUtilsTest();
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("分享", R.drawable.arrow_left, -1, "", "点我分享");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                ShareActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {
                shareUtilsTest.addCustomPlatforms(ShareActivity.this);
            }
        });
    }


}
