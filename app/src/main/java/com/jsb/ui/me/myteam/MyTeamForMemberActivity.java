package com.jsb.ui.me.myteam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.MyTeamForMemberAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.RangeData;
import com.jsb.model.RangeRecord;
import com.jsb.model.Userstable;
import com.jsb.ui.BaseActivity;
import com.jsb.ui.ModifyUserInfoActivity;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.UIUtils;
import com.jsb.widget.AutoListView;
import com.jsb.widget.PopWindowUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by JammyQtheLab on 2015/11/10.
 */
public class MyTeamForMemberActivity extends BaseActivity {
    private static final String ExtraKey = "ExtraKey";
    private Userstable userstable;

    public static void startActivity(Activity context, Userstable userstable) {
        Intent intent = new Intent(context, MyTeamForMemberActivity.class);
        intent.putExtra(ExtraKey, userstable);
        context.startActivity(intent);
    }

    private SwipeRefreshLayout swipeLayout;
    private AutoListView listView;

    private List<Object> mDatas = new ArrayList<>();
    private MyTeamForMemberAdapter adapter;
    private int userid = -1;
    private Activity activity;


    private ImageView iv_notification;
    private TextView tv_title;
    private LinearLayout layout_notification;
    private LinearLayout right_iv_operation;

    private PopWindowUtils mPopWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team_for_member);
        activity = this;
        userstable = getIntent().getExtras().getParcelable(ExtraKey);
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        mPopWindow = new PopWindowUtils(this);
        setUpViews();
        setUpListener();
    }

    private void setUpListener() {

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                getTeamRangeInfo();
            }
        });


        findViewById(R.id.left_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        findViewById(R.id.right_iv_operation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopWindow.isShowing()) {
                    mPopWindow.dismiss();
                    return;
                }
                mPopWindow.showPopWindowInMyTeamForMember(v, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.layout_create_team:
                                break;
                            case R.id.layout_modify_info:
                                ModifyUserInfoActivity.startActivity(activity,userstable);
                                break;
                            case R.id.layout_check_rule:
                                break;
                        }
                        mPopWindow.dismiss();
                    }
                });
            }
        });


        findViewById(R.id.layout_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "点击消息", Toast.LENGTH_SHORT).show();
                iv_notification.setImageResource(R.drawable.icon_no_notification);
            }
        });
    }

    private void setUpViews() {

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        listView = (AutoListView) findViewById(R.id.list_view);

        layout_notification = (LinearLayout) findViewById(R.id.layout_notification);
        iv_notification = (ImageView) findViewById(R.id.iv_notification);
        tv_title = (TextView) findViewById(R.id.title_tv);
        tv_title.setText("我的团队");


        adapter = new MyTeamForMemberAdapter(this, mDatas);
        listView.setAdapter(adapter);
        listView.setLoading(false);
        getTeamRangeInfo();
    }

    private void getTeamRangeInfo() {
        if (userid != -1) {
            UserRetrofitUtil.getTeamRangeInfo(this, userid, new NetCallback<NetWorkResultBean<RangeData>>(this) {
                @Override
                public void onFailure(RetrofitError error, String message) {
                    swipeLayout.setRefreshing(false);
                }

                @Override
                public void success(NetWorkResultBean<RangeData> rangeDataNetWorkResultBean, Response response) {
                    RangeData data = rangeDataNetWorkResultBean.getData();
                    if (data.getRangeOfMonth() != null) {
                        List<RangeRecord> monthDataList = data.getRangeOfMonth();
                        if (monthDataList.size() > 0) {
                            mDatas.add("月度排名");
                            mDatas.addAll(monthDataList);
                        }
                    }
                    if (data.getRangeOfYear() != null) {
                        List<RangeRecord> yearDataList = data.getRangeOfYear();
                        if (yearDataList.size() > 0) {
                            int amount = yearDataList.size();
                            mDatas.add("年度排名" + "(" + amount + "人/团)");
                            mDatas.addAll(yearDataList);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    swipeLayout.setRefreshing(false);
                }
            });
        } else {
            swipeLayout.setRefreshing(false);
        }
    }

}
