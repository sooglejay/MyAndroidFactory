package com.jiandanbaoxian.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.widget.TitleBar;

/**
 * Created by Jerry on 16/8/10.
 */
public class WebViewTest extends Activity {

    WebView webView;
    TitleBar titleBar;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeLayout;
    String webViewURL="http://220.166.172.33";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test);

        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        WebChromeClient client = new AppChromeWebClient(titleBar, progressBar, swipeLayout);
        webView.setWebChromeClient(client);
        webView.setWebViewClient(new AppWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(webViewURL);
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

        titleBar.initTitleBarInfo("", R.drawable.arrow_left, -1, "", "");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();   //后退
                    return;    //已处理
                }
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

    }
    //WebView 相关
    private static final class AppChromeWebClient extends WebChromeClient {
        private ProgressBar bar;
        private SwipeRefreshLayout swipeRefreshLayout;
        private TitleBar titleBar;

        public AppChromeWebClient(TitleBar titleBar, ProgressBar bar, SwipeRefreshLayout swipeRefreshLayout) {
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

    private static final class AppWebClient extends WebViewClient {

    }

}
