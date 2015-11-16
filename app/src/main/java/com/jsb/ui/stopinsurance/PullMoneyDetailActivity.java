package com.jsb.ui.stopinsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.ui.BaseActivity;
import com.jsb.widget.TitleBar;

/**
 * Created by JammyQtheLab on 2015/11/12.
 */
public class PullMoneyDetailActivity extends BaseActivity{

    private static final String ExtraKey = "moneyStr";
    private Activity activity;
    private TitleBar titleBar;
    private TextView tvConfirm;
    private TextView tv_pull_money_amount;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-12 20:16:35 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar)findViewById( R.id.title_bar );
        tvConfirm = (TextView)findViewById( R.id.tv_confirm );
        tv_pull_money_amount = (TextView) findViewById(R.id.tv_pull_money_amount);
    }


    private String moneyStr;

    public static void startActivity(Activity activity, String money) {
        Intent intent = new Intent(activity, PullMoneyDetailActivity.class);
        intent.putExtra(ExtraKey, money);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_money_detail);
        activity = this;
        moneyStr = getIntent().getExtras().getString(ExtraKey, "0");
        findViews();
        setUpViews();
    }


    private void setUpViews() {

        titleBar.initTitleBarInfo("提现详情",R.drawable.arrow_left,-1,"","");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                activity.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });


        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        tv_pull_money_amount.setText("提现金额:" + moneyStr);
    }
}
