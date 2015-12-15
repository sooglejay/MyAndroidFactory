package com.jsb.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jsb.R;
import com.jsb.widget.TitleBar;
import com.jsb.widget.jazzyviewpager.JazzyViewPager;
import com.umeng.analytics.MobclickAgent;

public class ServerConsultorFragment extends BaseFragment {
    private ImageView iv_dot_0;
    private ImageView iv_dot_1;
    private ImageView iv_dot_2;
    private ImageView iv_dot_3;
    private Context context;
    private JazzyViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter = null;
    private TitleBar titleBar;
    private FrameLayout layout_viewpager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //统计页面
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

    private void setUp(View view, Bundle savedInstanceState) {
        viewPager = (JazzyViewPager) view.findViewById(R.id.pager);
        layout_viewpager = (FrameLayout) view.findViewById(R.id.layout_viewpager);
        iv_dot_0 = (ImageView) view.findViewById(R.id.dot_0);
        iv_dot_0.setImageResource(R.drawable.dot_0);
        iv_dot_1 = (ImageView) view.findViewById(R.id.dot_1);
        iv_dot_2 = (ImageView) view.findViewById(R.id.dot_2);
        iv_dot_3 = (ImageView) view.findViewById(R.id.dot_3);
        viewPager.setOffscreenPageLimit(20);
        viewPager.setCurrentItem(1, true);
        viewPager.setPageMargin(-140);

        viewPagerAdapter = new ViewPagerAdapter(this.getActivity(), this.getActivity().getSupportFragmentManager(), viewPager);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.Tablet);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        iv_dot_0.setImageResource(R.drawable.dot_0);
                        iv_dot_1.setImageResource(R.drawable.dot_4);
                        iv_dot_2.setImageResource(R.drawable.dot_4);
                        iv_dot_3.setImageResource(R.drawable.dot_4);
                        break;
                    case 1:
                        iv_dot_0.setImageResource(R.drawable.dot_4);
                        iv_dot_1.setImageResource(R.drawable.dot_1);
                        iv_dot_2.setImageResource(R.drawable.dot_4);
                        iv_dot_3.setImageResource(R.drawable.dot_4);
                        break;
                    case 2:
                        iv_dot_0.setImageResource(R.drawable.dot_4);
                        iv_dot_1.setImageResource(R.drawable.dot_4);
                        iv_dot_2.setImageResource(R.drawable.dot_2);
                        iv_dot_3.setImageResource(R.drawable.dot_4);
                        break;
                    case 3:
                        iv_dot_0.setImageResource(R.drawable.dot_4);
                        iv_dot_1.setImageResource(R.drawable.dot_4);
                        iv_dot_2.setImageResource(R.drawable.dot_4);
                        iv_dot_3.setImageResource(R.drawable.dot_0);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        layout_viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });

        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("服务顾问", -1, -1, "", "");




    }

    /**
     * 首页viewpager的adapter
     */
    private static final class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(Context context, FragmentManager fm, JazzyViewPager mJazzy) {
            super(fm);
            this.mJazzy = mJazzy;
        }

        private JazzyViewPager mJazzy;

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Object obj = super.instantiateItem(container, position);
            mJazzy.setObjectForPosition(obj, position);
            return obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {
            container.removeView(mJazzy.findViewFromObject(position));
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    ConsultFragmentPerPage page0 = new ConsultFragmentPerPage();
                    page0.setPosition(position);
                    fragment = page0;
                    break;
                case 1:
                    ConsultFragmentPerPage page1 = new ConsultFragmentPerPage();
                    page1.setPosition(position);
                    fragment = page1;
                    break;
                case 2:
                    ConsultFragmentPerPage page2 = new ConsultFragmentPerPage();
                    page2.setPosition(position);
                    fragment = page2;
                    break;
                case 3:
                    ConsultFragmentPerPage page3 = new ConsultFragmentPerPage();
                    page3.setPosition(position);
                    fragment = page3;
                    break;
                default:
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
