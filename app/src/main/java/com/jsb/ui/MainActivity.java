package com.jsb.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.IntConstant;
import com.jsb.constant.PreferenceConstant;
import com.jsb.constant.StringConstant;
import com.jsb.event.BusEvent;
import com.jsb.fragment.DialogFragmentCreater;
import com.jsb.fragment.ServerConsultorFragment;
import com.jsb.fragment.ShutInsureFragment;
import com.jsb.fragment.BuyInsureFragment;
import com.jsb.fragment.MeFragment;
import com.jsb.model.CommData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.util.CheckNetWorkUtil;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.UpdateVersionUtil;
import com.jsb.widget.ScrollableViewPager;
import com.jsb.widget.TabBar;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Main Activity
 */
public class MainActivity extends BaseActivity {

    public static final String ACTION_HOME_ACTIVITY_CONTROL = "action_scroll_switch_tab";
    public static final String EXTRA_VIEWPAGER_SCROLLABLE = "extra_scroll_switch_tab";

    private ScrollableViewPager viewPager = null;
    private TabBar tabBar = null;
    private View lineView = null;
    private ViewPagerAdapter viewPagerAdapter = null;
    private int currentPos = 0;
    private BroadcastReceiver tabBarStatusReceiver;

    private UpdateVersionUtil updateVersionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
    }

    private void setUp() {
        lineView = findViewById(R.id.line_view);
        tabBar = (TabBar) findViewById(R.id.home_bottomBar);
        initViewPager();
        checkUpdate();
    }

    /**
     * 检查版本更新
     */
    private void checkUpdate() {
        UserRetrofitUtil.checkUpdate(this, new NetCallback<NetWorkResultBean<CommData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<CommData> commDataNetWorkResultBean, Response response) {
                String versionCode = commDataNetWorkResultBean.getData().getVersion();
                String downLoadURL = commDataNetWorkResultBean.getData().getDownLoadUrl();
                updateVersionUtil = new UpdateVersionUtil(MainActivity.this, downLoadURL, versionCode);
                updateVersionUtil.checkVerisonUpdate();
            }
        });
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {

        viewPager = (ScrollableViewPager) findViewById(R.id.view_pager);
        viewPager.removeAllViews();

        viewPager.setOffscreenPageLimit(10);
        viewPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabBar.changeImageView(viewPager.getCurrentItem(), position, positionOffset, positionOffsetPixels);


            }

            @Override
            public void onPageSelected(int position) {
                tabBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //选中当前
        viewPager.setCurrentItem(currentPos);
        tabBar.selectTab(viewPager.getCurrentItem());
        tabBar.setOnTabClickListener(new TabBar.OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                viewPager.setCurrentItem(position, false);
            }
        });
    }

    /**
     * 首页viewpager的adapter
     */
    private static final class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new ShutInsureFragment();
                    break;
                case 1:
                    fragment = new BuyInsureFragment();
                    break;
                case 2:
                    fragment = new ServerConsultorFragment();
                    break;
                case 3:
                    fragment = new MeFragment();
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


    /**
     * EventBus 广播
     *
     * @param event
     */
    public void onEventMainThread(BusEvent event) {
        switch (event.getMsg()) {
            case BusEvent.MSG_Login_Success:
                if (viewPager != null) {
                    viewPager.setCurrentItem(0, false);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 注册控制tabbar的广播
     */
    private void registerTabBarStatusReceiver() {
        IntentFilter filter = new IntentFilter(ACTION_HOME_ACTIVITY_CONTROL);
        tabBarStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent != null) {
                    //是否viewpager可滑动
                    if (intent.hasExtra(EXTRA_VIEWPAGER_SCROLLABLE)) {
                        if (intent.getBooleanExtra(EXTRA_VIEWPAGER_SCROLLABLE, true)) {
                            if (!viewPager.isScroll()) {
                                viewPager.setScrollable(true);
                            }
                        } else {
                            if (viewPager.isScroll()) {
                                viewPager.setScrollable(false);
                            }
                        }
                    }
                }
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(tabBarStatusReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerTabBarStatusReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (tabBarStatusReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(tabBarStatusReceiver);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("真的要关闭安心车险么?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                }).setNegativeButton("取消", null).create().show();
    }

}

