package com.jsb.fragment;

import android.content.Context;
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

import com.jsb.R;
import com.jsb.widget.TitleBar;
import com.jsb.widget.jazzyviewpager.JazzyViewPager;
import com.umeng.analytics.MobclickAgent;

public class ServerConsultorFragment extends BaseFragment {
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
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }
    private void setUp(View view, Bundle savedInstanceState) {
        viewPager = (JazzyViewPager) view.findViewById(R.id.pager);
        layout_viewpager = (FrameLayout) view.findViewById(R.id.layout_viewpager);

        viewPager.setOffscreenPageLimit(20);
        viewPager.setPageMargin(-140);

        viewPagerAdapter = new ViewPagerAdapter(this.getActivity(), this.getActivity().getSupportFragmentManager(),viewPager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.Tablet);
        layout_viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });

        titleBar = (TitleBar)view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("服务顾问",-1,-1,"","");


    }

    /**
     * 首页viewpager的adapter
     */
    private static final class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(Context context, FragmentManager fm,JazzyViewPager mJazzy) {
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
                    fragment = new ConsultFragmentPerPage();
                    break;
                case 1:
                    fragment = new ConsultFragmentPerPage();
                    break;
                case 2:
                    fragment = new ConsultFragmentPerPage();
                    break;
                case 3:
                    fragment = new ConsultFragmentPerPage();
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
