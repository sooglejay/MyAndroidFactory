package com.jsb.ui.me.myteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsb.R;
import com.jsb.constant.PreferenceConstant;
import com.jsb.ui.BaseActivity;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.UIUtils;
import com.jsb.widget.TitleBar;

import pl.droidsonroids.gif.GifImageView;

/**
 * 我的-我的保险
 */
public class MyTeamForFreeActivity extends BaseActivity {

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
                MyTeamForFreeActivity.this.finish();
            }
            @Override
            public void onRightButtonClick(View v) {
            }
        });


        findViewById(R.id.layout_join_team).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTeamForFreeActivity.this.startActivity(new Intent(MyTeamForFreeActivity.this,JoinTeamActivity.class));
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
