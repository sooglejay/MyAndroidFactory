package com.jiandanbaoxian.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jiandanbaoxian.constant.ExtraConstants;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;

/**
 * 浏览器工具类
 */
public class BrowserWebViewActivity extends BaseActivity {
    private String url = "";
    private String title = "";
    private WebView webView;
    private ProgressBar progressBar;
    private TitleBar titleBar;
    private SwipeRefreshLayout swipeLayout;
    private boolean isShowRule = false;

    public static void startActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, BrowserWebViewActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_URL, url);
        intent.putExtra(ExtraConstants.EXTRA_title, title);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, boolean isShowRule) {
        Intent intent = new Intent(context, BrowserWebViewActivity.class);
        intent.putExtra("rule", isShowRule);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_text);
        setUp();
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("", R.drawable.arrow_left, -1, "", "");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        webView = (WebView) findViewById(R.id.webView);
        url = getIntent().getStringExtra(ExtraConstants.EXTRA_URL);
//        title = getIntent().getStringExtra(ExtraConstants.EXTRA_title);
//        titleBar.initTitleBarInfo(title, R.drawable.arrow_left, -1, "", "");

        isShowRule = getIntent().getBooleanExtra("rule", false);
        WebChromeClient client = new XjbChromeWebClient(titleBar, progressBar, swipeLayout);
        webView.setWebChromeClient(client);
        webView.setWebViewClient(new XjbWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);
        } else if (isShowRule) {
//            webView.loadData("<ul>哈哈哈哈哈哈哈哈</ul>", "text/html", "UTF-8");
            webView.loadData(StringConstant.SERVER_RULE, "text/html; charset=UTF-8", null);
        }
        UIUtils.initSwipeRefreshLayout(swipeLayout);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {

                        webView.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    }

    private static final class XjbChromeWebClient extends WebChromeClient {
        private ProgressBar bar;
        private SwipeRefreshLayout swipeRefreshLayout;
        private TitleBar titleBar;

        public XjbChromeWebClient(TitleBar titleBar, ProgressBar bar, SwipeRefreshLayout swipeRefreshLayout) {
            this.bar = bar;
            this.swipeRefreshLayout = swipeRefreshLayout;
            this.titleBar = titleBar;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress < 100) {

            } else {
                if (bar.getVisibility() == View.VISIBLE) {
                    bar.setVisibility(View.GONE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            titleBar.updateTitle(title);
        }
    }

    private static final class XjbWebClient extends WebViewClient {

    }

}
