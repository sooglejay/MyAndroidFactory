package com.jsb.ui;

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

import com.jsb.constant.ExtraConstants;
import com.jsb.R;
import com.jsb.util.UIUtils;
import com.jsb.widget.TitleBar;

public class BrowserActivity extends BaseActivity {
    private String url = "";
    private WebView webView;
    private ProgressBar progressBar;
    private TitleBar titleBar;
    private SwipeRefreshLayout swipeLayout;
    private boolean isShowRule = false;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(ExtraConstants.EXTRA_URL, url);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, boolean isShowRule) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra("rule", isShowRule);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
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
        isShowRule = getIntent().getBooleanExtra("rule", false);
        WebChromeClient client = new XjbChromeWebClient(titleBar, progressBar,swipeLayout);
        webView.setWebChromeClient(client);
        webView.setWebViewClient(new XjbWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        if(!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);
        }
        else if(isShowRule) {
//            webView.loadData("<ul>哈哈哈哈哈哈哈哈</ul>", "text/html", "UTF-8");
            webView.loadData(s, "text/html; charset=UTF-8", null);
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

   private String s = "<html><head><title>停保费规则</title></head><body style=\"font-size:12px;\">\n" +
           "\t\t\t\t\n" +
           "<h5>设立省级分公司，由保险公司总公司提出申请；设立其他分支机构，由保险公司总公司提出申请，或者由省级分公司持总公司批准文件提出申请。</p>在计划单列市申请设立分支机构，还可以由保险公司根据本规定第四条第三款指定的分支机构持总公司批准文件提出申请。"+


           "</p>第十八条</p>设立分支机构，应当提出设立申请，并符合下列条件：" +

           "</p>（一）上一年度偿付能力充足，提交申请前连续2个季度偿付能力均为充足；\n" +
           " \n" +
           "</p>（二）保险公司具备良好的公司治理结构，内控健全；\n" +
           " \n" +
           "</p>（三）申请人具备完善的分支机构管理制度；\n" +
           " \n" +
           "</p>（四）对拟设立分支机构的可行性已进行充分论证；\n" +
           " \n" +
           "</p>（五）在住所地以外的省、自治区、直辖市申请设立省级分公司以外其他分支机构的，该省级分公司已经开业；\n" +
           " \n" +
           "</p>（六）申请人\"最近2年内\"无受金融监管机构重大行政处罚的记录，不存在因涉嫌重大违法行为正在受到中国保监会立案调查的情形；\n" +
           " \n" +
           "</p>（七）申请设立省级分公司以外其他分支机构，在拟设地所在的省、自治区、直辖市内，省级分公司\"最近2年内\"无受金融监管机构重大行政处罚的记录，已设立的其他分支机构\"最近6个月内\"无受重大保险行政处罚的记录；"+
           "</body></html>";
}
