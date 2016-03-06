package com.jiandanbaoxian.ui.buyinsurance.car_insurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.model.CommPriceData;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.widget.PagerSlidingTabStrip;
import com.jiandanbaoxian.widget.TitleBar;

/**
 * 车险－报价后的 一个 保险公司的报价详情
 */
public class PriceReportDetailTabHostActivity extends BaseActivity {

    public final static String ExtrasKeyName = "ExtrasKeyName";
    private TitleBar mTitleBar;
    private PagerSlidingTabStrip mTabs;
    private ViewPager viewPager;
    private ViewPagerAdapter mAdapter;

    private int selectTabTextColor;
    private int unSelectTabTextColor;


    public static void startActivity(Activity activity, CommPriceData data, String idcard_number, String country_no, String city_no, String province_no, long compulsorystartdate, long commercestartdate) {
        Intent intent = new Intent(activity, PriceReportDetailTabHostActivity.class);
        intent.putExtra("CommPriceData", data);
        intent.putExtra("idcard_number", idcard_number);
        intent.putExtra("city_no", city_no);
        intent.putExtra("country_no", country_no);
        intent.putExtra("province_no", province_no);
        intent.putExtra("compulsorystartdate", compulsorystartdate);
        intent.putExtra("commercestartdate", commercestartdate);
        activity.startActivityForResult(intent, 0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_insure_car);


        selectTabTextColor = getResources().getColor(R.color.base_color);
        unSelectTabTextColor = getResources().getColor(R.color.gray_color);

        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.initTitleBarInfo("保险详情", R.drawable.arrow_left, -1, "", "");
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
                PriceReportDetailTabHostActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"报价详情", "理赔政策", "合作网点"};

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
                    fragment = new PriceReportDetailsFragment1Activity();
                    break;
                case 1:
                    fragment = new PriceReportDetailsFragment1Activity();
                    break;
                case 2:
                    fragment = new PriceReportDetailsFragment3Activity();
                    break;
                default:
                    break;
            }
            return fragment;
        }

    }
}
