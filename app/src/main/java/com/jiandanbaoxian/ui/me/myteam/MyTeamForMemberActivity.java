package com.jiandanbaoxian.ui.me.myteam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.MyTeamForMemberAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.InviteInfo;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.RangeData;
import com.jiandanbaoxian.model.RangeRecord;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.BrowserActivity;
import com.jiandanbaoxian.ui.ModifyUserInfoActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.AutoListView;
import com.jiandanbaoxian.widget.PopWindowUtils;

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
    private TextView noResultsView;

    private List<Object> mDatas = new ArrayList<>();
    private MyTeamForMemberAdapter adapter;
    private int userid = -1;
    private Activity activity;


    private ImageView iv_notification;
    private TextView tv_title;
    private RelativeLayout container;
    private LinearLayout layout_notification;
    private LinearLayout right_iv_operation;

    private PopWindowUtils mPopWindow;

    private PopWindowUtils popWindowUtilsMemberConsiderRequest;//团员 处理请求
    private List<InviteInfo> inviteInfoList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team_for_member);
        activity = this;
        userstable = getIntent().getExtras().getParcelable(ExtraKey);
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        mPopWindow = new PopWindowUtils(this);
        popWindowUtilsMemberConsiderRequest = new PopWindowUtils(this);
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
                                activity.startActivity(new Intent(activity, CreateTeamActivity.class));
                                break;
                            case R.id.layout_modify_info:
                                ModifyUserInfoActivity.startActivity(activity, userstable);
                                break;
                            case R.id.layout_check_rule:
                                BrowserActivity.startActivity(activity, StringConstant.CreateTeamRule, "创建团队规则");
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
                getInviteInfo();
            }
        });
    }

    private void setUpViews() {

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        listView = (AutoListView) findViewById(R.id.list_view);

        container = (RelativeLayout) findViewById(R.id.container);
        layout_notification = (LinearLayout) findViewById(R.id.layout_notification);
        iv_notification = (ImageView) findViewById(R.id.iv_notification);
        tv_title = (TextView) findViewById(R.id.title_tv);
        tv_title.setText("我的团队");


        adapter = new MyTeamForMemberAdapter(this, mDatas);
        listView.setAdapter(adapter);
        listView.setLoading(false);
        noResultsView = (TextView) findViewById(R.id.emptyElement);
        listView.setEmptyView(noResultsView);
        getTeamRangeInfo();
    }

    private void getTeamRangeInfo() {
        if (userid != -1) {
            UserRetrofitUtil.getTeamRangeInfo(this, userid, 100,1,new NetCallback<NetWorkResultBean<RangeData>>(this) {
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


    private void getInviteInfo() {
        UserRetrofitUtil.getInviteInfo(this, userid, new NetCallback<NetWorkResultBean<List<InviteInfo>>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                if (!TextUtils.isEmpty(message)) {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
                iv_notification.setImageResource(R.drawable.icon_no_notification);

            }

            @Override
            public void success(NetWorkResultBean<List<InviteInfo>> listNetWorkResultBean, Response response) {
                inviteInfoList.clear();
                if (listNetWorkResultBean != null) {
                    List<InviteInfo> inviteInfos = listNetWorkResultBean.getData();
                    if (inviteInfos != null) {
                        inviteInfoList.addAll(inviteInfos);
                        popWindowUtilsMemberConsiderRequest.showPopWindowInMyTeamForMemberConsiderRequest(container, activity, inviteInfoList);
                        iv_notification.setImageResource(R.drawable.icon_has_notification);
                    } else {
                        InviteInfo inviteInfo = new InviteInfo();
                        inviteInfo.setId(1);
                        inviteInfo.setInviterid(100);
                        inviteInfo.setInvitername("看到我就说明你是团员，但是没有任何加团邀请");
                        inviteInfoList.add(inviteInfo);
                        popWindowUtilsMemberConsiderRequest.showPopWindowInMyTeamForMemberConsiderRequest(container, activity, inviteInfoList);

                    }
                }

            }
        });
    }

}
