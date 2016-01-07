package com.jiandanbaoxian.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.jiandanbaoxian.constant.ExtraConstants;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.util.ImageUtils;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import uk.co.senab.photoview.PhotoView;

/**
 * 浏览器工具类
 */
public class BrowserImageViewActivity extends BaseActivity {
    private String url = "";
    private String title = "";
    private PhotoView iv_rule;
    private ProgressBar progressBar;
    private TitleBar titleBar;
    private SwipeRefreshLayout swipeLayout;
    private boolean isShowRule = false;

    public static void startActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, BrowserImageViewActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_URL, url);
        intent.putExtra(ExtraConstants.EXTRA_title, title);
        context.startActivity(intent);
    }



    public static void startActivity(Context context, boolean isShowRule) {
        Intent intent = new Intent(context, BrowserImageViewActivity.class);
        intent.putExtra("rule", isShowRule);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_image);
        setUp();
    }

    private void setUp() {

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        swipeLayout.setEnabled(false);


        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        iv_rule = (PhotoView) findViewById(R.id.iv_rule);
        url = getIntent().getStringExtra(ExtraConstants.EXTRA_URL);
        title = getIntent().getStringExtra(ExtraConstants.EXTRA_title);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(title, R.drawable.arrow_left, -1, "", "");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });


        ImageLoader.getInstance().displayImage(url, iv_rule, ImageUtils.getOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(View.GONE);

            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }


}
