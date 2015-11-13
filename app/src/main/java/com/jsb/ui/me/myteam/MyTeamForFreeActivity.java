package com.jsb.ui.me.myteam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsb.R;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.Userstable;
import com.jsb.ui.BaseActivity;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.UIUtils;
import com.jsb.widget.TitleBar;

import pl.droidsonroids.gif.GifImageView;

/**
 * 我的-我的保险
 */
public class MyTeamForFreeActivity extends BaseActivity {
    private static final String ExtraKey = "ExtraKey";
    private TitleBar titleBar;
    private GifImageView gifImageView;

    private int userid = -1;
    private Userstable userstable;
    public static void startActivity(Activity context, Userstable userstable) {
        Intent intent = new Intent(context, MyTeamForFreeActivity.class);
        intent.putExtra(ExtraKey, userstable);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team_for_free);
        userstable = getIntent().getParcelableExtra(ExtraKey);

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
                MyTeamForFreeActivity.this.startActivity(new Intent(MyTeamForFreeActivity.this,FreeJoinTeamActivity.class));
            }
        });


        findViewById(R.id.layout_join_team).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTeamForFreeActivity.this.startActivity(new Intent(MyTeamForFreeActivity.this,CertificationActivity.class));
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
