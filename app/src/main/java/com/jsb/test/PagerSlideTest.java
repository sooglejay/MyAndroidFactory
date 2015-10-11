package com.jsb.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jsb.R;
import com.jsb.fragment.CarInsureDetailClaimPolicyFragmentTab2;
import com.jsb.fragment.CarInsureDetailContentFragmentTab1;
import com.jsb.fragment.CarInsureDetailServiceStationFragmentTab3;
import com.jsb.widget.PagerSlidingTabStrip;

public class PagerSlideTest extends FragmentActivity {

	private final Handler handler = new Handler();

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private int selectTabTextColor ;
	private int unSelectTabTextColor;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aaaaaaa_pager_slide);
		  selectTabTextColor = getResources().getColor(R.color.base_color);
		  unSelectTabTextColor = getResources().getColor(R.color.black_gray_color);

		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);
//
//		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
//				.getDisplayMetrics());
//		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);
		tabs.setTextColor(getResources().getColor(R.color.red_color));


		pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				tabs.setSelectTabTextColor(position, selectTabTextColor, unSelectTabTextColor);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}



	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "保单内容", "理赔政策", "合作网点" };

		public MyPagerAdapter(FragmentManager fm) {
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
					fragment = new CarInsureDetailContentFragmentTab1();
					break;
				case 1:
					fragment = new CarInsureDetailClaimPolicyFragmentTab2();
					break;
				case 2:
					fragment = new CarInsureDetailServiceStationFragmentTab3();
					break;
				default:
					break;
			}
			return fragment;
		}

	}
}
