package com.jsb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.jsb.R;
import com.jsb.adapter.MyInsuresListAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.Driverordertable;
import com.jsb.model.MyInsuranceData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.Overtimeordertable;
import com.jsb.model.Vehicleordertable;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.UIUtils;
import com.jsb.widget.AutoListView;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的-我的保险
 */
public class MyTeamActivity extends BaseActivity {

    private TitleBar titleBar;
    private GifImageView gifImageView;

    private int userid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyTeamActivity.this.finish();
            }
            @Override
            public void onRightButtonClick(View v) {
            }
        });


        findViewById(R.id.layout_join_team).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTeamActivity.this.startActivity(new Intent(MyTeamActivity.this,CertificationActivity.class));
            }
        });
    }

    private void setUp() {
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);


        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("我的团队", R.drawable.arrow_left, -1, "", "规则");

        gifImageView = (GifImageView)findViewById(R.id.gif_view);
        UIUtils.setGifView(this,gifImageView,R.drawable.gif_join_team);

    }

}
