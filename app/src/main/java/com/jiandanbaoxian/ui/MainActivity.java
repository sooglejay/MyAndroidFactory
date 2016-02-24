package com.jiandanbaoxian.ui;

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
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.event.BusEvent;
import com.jiandanbaoxian.fragment.BuyInsureFragment;
import com.jiandanbaoxian.fragment.MeFragment;
import com.jiandanbaoxian.fragment.ServerConsultorFragment;
import com.jiandanbaoxian.fragment.ShutInsureFragment;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.util.UpdateVersionUtil;
import com.jiandanbaoxian.widget.ScrollableViewPager;
import com.jiandanbaoxian.widget.TabBar;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

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
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
    }

    private void setUp() {
        lineView = findViewById(R.id.line_view);
        tabBar = (TabBar) findViewById(R.id.home_bottomBar);
        initViewPager();
    }

    /**
     * 检查版本更新   Version code =2  版本 中就开始去掉原生更新，转而使用友盟自动更新
     */
    private void checkUpdate() {
        UserRetrofitUtil.checkUpdate(this, new NetCallback<NetWorkResultBean<CommData>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                if (!TextUtils.isEmpty(message)) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
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
                    fragment = new BuyInsureFragment();
                    break;
                case 1://change  the order
                    fragment = new ShutInsureFragment();
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
        MobclickAgent.onResume(this);       //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (tabBarStatusReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(tabBarStatusReceiver);
        }
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("真的要退出么?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                }).setNegativeButton("取消", null).create().show();
    }
}

