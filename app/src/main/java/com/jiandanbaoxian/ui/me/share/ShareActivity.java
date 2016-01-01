package com.jiandanbaoxian.ui.me.share;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.ShareUtils;
import com.jiandanbaoxian.util.ShareUtilsTest;
import com.jiandanbaoxian.widget.TitleBar;

/**
 * 首页-分享
 */
public class ShareActivity extends BaseActivity {

    private TitleBar titleBar = null;
    private ShareUtilsTest shareUtilsTest;
    private Activity activity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        activity = this;
        setUp();
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("", R.drawable.arrow_left, -1, "", "分享");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                ShareActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {
                new ShareUtilsTest(activity,"http://img0.imgtn.bdimg.com/it/u=4096430706,2666285308&fm=21&gp=0.jpg");
//                ShareUtils.openShare(activity,"分享内容","http://wanbaoe.com/","http://img0.imgtn.bdimg.com/it/u=4096430706,2666285308&fm=21&gp=0.jpg");

            }
        });
    }


}
