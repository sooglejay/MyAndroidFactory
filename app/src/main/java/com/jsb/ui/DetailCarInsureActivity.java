package com.jsb.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jsb.R;
import com.jsb.fragment.BuyInsureFragment;
import com.jsb.fragment.DetailCarInsureClaimPolicyFragment_Tab2;
import com.jsb.fragment.DetailCarInsureContentFragment_Tab1;
import com.jsb.fragment.DetailCarInsureServiceStationFragment_Tab3;
import com.jsb.fragment.MeFragment;
import com.jsb.fragment.ShutInsureFragment;
import com.jsb.widget.PagerSlidingTabStrip;
import com.jsb.widget.TitleBar;

/**
 * Created by Administrator on 2015/10/10.
 */
public class DetailCarInsureActivity extends BaseActivity {

    private TitleBar mTitleBar;
    private PagerSlidingTabStrip mTabs;
    private ViewPager viewPager;
    private ViewPagerAdapter mAdapter;

    private int selectTabTextColor ;
    private int unSelectTabTextColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_car_insure);
        selectTabTextColor = getResources().getColor(R.color.base_color);
        unSelectTabTextColor = getResources().getColor(R.color.gray_color);

        mTitleBar = (TitleBar)findViewById(R.id.title_bar);
        mTitleBar.initTitleBarInfo("保险详情",R.drawable.arrow_left,-1,"","");
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
//
//		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
//				.getDisplayMetrics());
//		pager.setPageMargin(pageMargin);

        mTabs.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTabs.setSelectTabTextColor(position, selectTabTextColor, unSelectTabTextColor);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTitleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                DetailCarInsureActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = { "保单内容", "理赔政策", "合作网点" };

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new DetailCarInsureContentFragment_Tab1();
                    break;
                case 1:
                    fragment = new DetailCarInsureClaimPolicyFragment_Tab2();
                    break;
                case 2:
                    fragment = new DetailCarInsureServiceStationFragment_Tab3();
                    break;
                default:
                    break;
            }
            return fragment;
        }

    }
}
