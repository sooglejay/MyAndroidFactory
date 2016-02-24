package com.jiandanbaoxian.ui.me.historyprice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.fragment.HistoryPriceDetailFragmentTab1;
import com.jiandanbaoxian.model.Vehicleordertable;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.widget.PagerSlidingTabStrip;
import com.jiandanbaoxian.widget.TitleBar;

/**
 * Created by JammyQtheLab on 2015/11/5.
 */
public class MyHistoryPriceItemActivity extends BaseActivity {


    public final static String ExtrasKeyName = "ExtrasKeyName";
    private TitleBar mTitleBar;
    private PagerSlidingTabStrip mTabs;
    private ViewPager viewPager;
    private ViewPagerAdapter mAdapter;

    private int selectTabTextColor;
    private int unSelectTabTextColor;


    private String carNumberString;
    private String carFaDongJiNumber;
    private String carJiaNumber;
    private String userNameString;

    private long commercialTimeLong;
    private long jqxTimeLong;
    private long fazhengTimeLong;
    private long signInTimeLong;

    public static void startActivity(
            Activity activity,
            String carNumberString,
            String carFaDongJiNumber,
            String carJiaNumber,
            String userNameString,
            long commercialTimeLong,
            long jqxTimeLong,
            long fazhengTimeLong,
            long signInTimeLong
    ) {
        Intent intent = new Intent(activity, MyHistoryPriceItemActivity.class);

        intent.putExtra("carNumberString", carNumberString);
        intent.putExtra("carFaDongJiNumber", carFaDongJiNumber);
        intent.putExtra("carJiaNumber", carJiaNumber);
        intent.putExtra("userNameString", userNameString);

        intent.putExtra("commercialTimeLong", commercialTimeLong);
        intent.putExtra("jqxTimeLong", jqxTimeLong);
        intent.putExtra("fazhengTimeLong", fazhengTimeLong);
        intent.putExtra("signInTimeLong", signInTimeLong);

        activity.startActivity(intent);
    }


    public static void startActivity(Activity context, Vehicleordertable bean) {
        Intent intent = new Intent(context, MyHistoryPriceItemActivity.class);
        intent.putExtra(ExtrasKeyName, bean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history_price_detail);

        getExtras();

        selectTabTextColor = getResources().getColor(R.color.base_color);
        unSelectTabTextColor = getResources().getColor(R.color.gray_color);

        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.initTitleBarInfo("华安保险", R.drawable.arrow_left, -1, "", "");
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
                MyHistoryPriceItemActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

    }

    public void getExtras() {
        Intent intent = getIntent();
        carNumberString = intent.getStringExtra("carNumberString");
        carFaDongJiNumber = intent.getStringExtra("carFaDongJiNumber");
        carJiaNumber = intent.getStringExtra("carJiaNumber");
        userNameString = intent.getStringExtra("userNameString");


        commercialTimeLong = intent.getLongExtra("commercialTimeLong", 0);
        jqxTimeLong = intent.getLongExtra("jqxTimeLong", 0);
        fazhengTimeLong = intent.getLongExtra("fazhengTimeLong", 0);
        signInTimeLong = intent.getLongExtra("signInTimeLong", 0);

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"详细报价", "理赔政策", "合作网点"};

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
            HistoryPriceDetailFragmentTab1 fragment = null;
            switch (position) {
                case 0:
                    fragment = new HistoryPriceDetailFragmentTab1();
                    break;
                case 1:
                    fragment = new HistoryPriceDetailFragmentTab1();
                    break;
                case 2:
                    fragment = new HistoryPriceDetailFragmentTab1();
                    break;
                default:
                    break;
            }
            fragment.setExtras(carNumberString, carFaDongJiNumber, carJiaNumber, userNameString, commercialTimeLong, jqxTimeLong, fazhengTimeLong, signInTimeLong);
            return fragment;
        }

    }
}
