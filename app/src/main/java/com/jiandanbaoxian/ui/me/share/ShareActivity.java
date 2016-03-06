package com.jiandanbaoxian.ui.me.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.BrowserImageViewActivity;
import com.jiandanbaoxian.util.ShareUtils;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;
import com.jiandanbaoxian.widget.imagepicker.bean.Image;

/**
 * 首页-分享
 */
public class ShareActivity extends BaseActivity {

    private TitleBar titleBar;
    private ImageView iv_qrcode;
    private FrameLayout layout_reward_rule;
    private ShareUtils shareUtils;
    private Activity activity;

    String  qrCodeImagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        activity = this;
        setUp();
        qrCodeImagePath =  UIUtils.generaterQRCode(this, "http://www.wanbaoe.com/",iv_qrcode,(int)UIUtils.dp2px(this,180),(int)UIUtils.dp2px(this,180));

    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        iv_qrcode = (ImageView) findViewById(R.id.iv_qrcode);
        layout_reward_rule = (FrameLayout) findViewById(R.id.layout_reward_rule);
        titleBar.initTitleBarInfo("", R.drawable.arrow_left, -1, "", "分享");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                ShareActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {
                new ShareUtils(activity, qrCodeImagePath);

            }
        });

        layout_reward_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserImageViewActivity.startActivity(activity, StringConstant.PauseRule, "奖励规则");
            }
        });
    }


}
