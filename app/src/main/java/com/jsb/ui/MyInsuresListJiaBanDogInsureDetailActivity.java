package com.jsb.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jsb.R;
import com.jsb.fragment.CarInsureDetailClaimPolicyFragmentTab2;
import com.jsb.fragment.CarInsureDetailContentFragmentTab1;
import com.jsb.fragment.CarInsureDetailServiceStationFragmentTab3;
import com.jsb.widget.PagerSlidingTabStrip;
import com.jsb.widget.TitleBar;

/**
 * 我的-我的保险-车险
 */
public class MyInsuresListJiaBanDogInsureDetailActivity extends BaseActivity {

    private TitleBar mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_insure_jiabandog);

        mTitleBar = (TitleBar)findViewById(R.id.title_bar);
        mTitleBar.initTitleBarInfo("产品详情", R.drawable.arrow_left, -1, "", "");
        mTitleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyInsuresListJiaBanDogInsureDetailActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

    }

}
