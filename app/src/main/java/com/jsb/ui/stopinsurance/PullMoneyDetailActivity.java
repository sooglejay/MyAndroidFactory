package com.jsb.ui.stopinsurance;

import android.app.Activity;
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

    private Activity activity;
    private TitleBar titleBar;
    private TextView tvConfirm;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-12 20:16:35 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar)findViewById( R.id.title_bar );
        tvConfirm = (TextView)findViewById( R.id.tv_confirm );
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_money_detail);
        activity = this;
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
    }
}
