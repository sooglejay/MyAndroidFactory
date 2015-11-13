package com.jsb.ui.me.myteam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.LeaderSearchMemberAdapter;
import com.jsb.adapter.MyTeamForMemberAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.RangeData;
import com.jsb.model.RangeRecord;
import com.jsb.model.SelfRecord;
import com.jsb.model.TeamData;
import com.jsb.model.Userstable;
import com.jsb.ui.BaseActivity;
import com.jsb.ui.BrowserActivity;
import com.jsb.ui.ModifyUserInfoActivity;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.UIUtils;
import com.jsb.widget.AutoListView;
import com.jsb.widget.PopWindowUtils;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by JammyQtheLab on 2015/11/10.
 */
public class MyTeamForLeaderActivity extends BaseActivity {
    private static final String ExtraKey = "ExtraKey";
    private Userstable userstable;

    public static void startActivity(Activity context, Userstable userstable) {
        Intent intent = new Intent(context, MyTeamForLeaderActivity.class);
        intent.putExtra(ExtraKey, userstable);
        context.startActivity(intent);
    }


    //header

    private View headerView;
    private TextView tvYearAmount;
    private TextView tvTeamName;
    private TextView tvMemberAmount;
    private TextView tvMonthAmount;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-12 10:57:56 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViewsInHeaderView() {
        headerView = View.inflate(this, R.layout.header_my_team_for_leader, null);
        tvYearAmount = (TextView) headerView.findViewById(R.id.tv_year_amount);
        tvTeamName = (TextView) headerView.findViewById(R.id.tv_team_name);
        tvMemberAmount = (TextView) headerView.findViewById(R.id.tv_member_amount);
        tvMonthAmount = (TextView) headerView.findViewById(R.id.tv_month_amount);
        listView.addHeaderView(headerView);

        if (userstable != null) {
            tvTeamName.setText(!TextUtils.isEmpty(userstable.getName()) ? userstable.getName() : "");
        }

    }


    private RelativeLayout container;
    private LinearLayout leftContainer;
    private ImageView leftIv;
    private TextView titleTv;
    private LinearLayout layoutNotification;
    private ImageView ivNotification;
    private LinearLayout rightIvOperation;
    private LinearLayout layoutSearch;
    private EditText etSearchTeam;
    private SwipeRefreshLayout swipeLayout;
    private AutoListView listView;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-12 11:05:13 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        container = (RelativeLayout) findViewById(R.id.container);
        leftContainer = (LinearLayout) findViewById(R.id.left_container);
        leftIv = (ImageView) findViewById(R.id.left_iv);
        titleTv = (TextView) findViewById(R.id.title_tv);
        layoutNotification = (LinearLayout) findViewById(R.id.layout_notification);
        ivNotification = (ImageView) findViewById(R.id.iv_notification);
        rightIvOperation = (LinearLayout) findViewById(R.id.right_iv_operation);
        layoutSearch = (LinearLayout) findViewById(R.id.layout_search);
        etSearchTeam = (EditText) findViewById(R.id.et_search_team);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        listView = (AutoListView) findViewById(R.id.list_view);
    }


    private LinearLayout layout_clear;
    private int userid;
    private Activity activity;
    private PopWindowUtils mPopWindow;
    private List<Object> mDatas = new ArrayList<>();
    private List<SelfRecord> mDatas_Search = new ArrayList<>();
    private MyTeamForMemberAdapter adapter;
    private LeaderSearchMemberAdapter leaderSearchMemberAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team_for_leader);
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        mPopWindow = new PopWindowUtils(this);
        userstable = getIntent().getExtras().getParcelable(ExtraKey);

        //顺序必须是这样
        findViews();
        findViewsInHeaderView();
        setUpViews();
        setUpListener();
    }
    private void setUpViews() {
        activity = this;
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        adapter = new MyTeamForMemberAdapter(this, mDatas);
        leaderSearchMemberAdapter = new LeaderSearchMemberAdapter(mDatas_Search, this);
        listView.setAdapter(adapter);
        listView.setLoading(false);

        //搜索团员
        etSearchTeam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    layout_clear.setVisibility(View.VISIBLE);
                    if (listView.getHeaderViewsCount() > 0) {
                        listView.removeHeaderView(headerView);
                    }
                    listView.setAdapter(leaderSearchMemberAdapter);
                    searchMember(userid, s.toString());
                } else {
                    layout_clear.setVisibility(View.GONE);
                    if (listView.getHeaderViewsCount() < 1) {
                        listView.addHeaderView(headerView);
                    }
                    listView.setAdapter(adapter);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        getTeamRangeInfo();

        //团长获取本团队的信息
        getMyTeamInfo();
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
                mPopWindow.showPopWindowInMyTeamForLeader(v, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.layout_add_new_member:
                                activity.startActivity(new Intent(activity, LeaderAddNewMemberActivity.class));
                                break;
                            case R.id.layout_modify_info:
                                ModifyUserInfoActivity.startActivity(activity, userstable);
                                break;
                            case R.id.layout_check_rule:
                                BrowserActivity.startActivity(activity,true);
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
                ivNotification.setImageResource(R.drawable.icon_no_notification);
            }
        });


        //清除搜索框的内容
        layout_clear = (LinearLayout) findViewById(R.id.layout_clear);
        findViewById(R.id.layout_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearchTeam.setText("");
                etSearchTeam.clearFocus();
            }
        });

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
                    mDatas.clear();
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


    private void getMyTeamInfo() {
        UserRetrofitUtil.getMyTeamInfo(this, userid, new NetCallback<NetWorkResultBean<TeamData>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {

            }

            @Override
            public void success(NetWorkResultBean<TeamData> teamDataNetWorkResultBean, Response response) {
                TeamData bean = teamDataNetWorkResultBean.getData();
                if (bean != null) {

                    //团队人数
                    if (bean.getPersons() != null) {
                        tvMemberAmount.setText(bean.getPersons() + "人");
                    } else {
                        tvMemberAmount.setText("");
                    }

                    //月度保费
                    if (bean.getTotalFeeOfMonth() != null) {
                        tvMonthAmount.setText(bean.getTotalFeeOfMonth() + "");
                    } else {
                        tvMonthAmount.setText("");
                    }

                    //年度保费
                    if (bean.getTotalFeeOfYear() != null) {
                        tvYearAmount.setText(bean.getTotalFeeOfYear() + "");
                    } else {
                        tvYearAmount.setText("");
                    }

                }
            }
        });
    }


    private void searchMember(int userid, String searchParam) {
        UserRetrofitUtil.searchMember(this, userid, searchParam, new NetCallback<NetWorkResultBean<List<SelfRecord>>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void success(NetWorkResultBean<List<SelfRecord>> selfRecordNetWorkResultBean, Response response) {
                swipeLayout.setRefreshing(false);
                mDatas_Search.clear();
                List<SelfRecord> datas = selfRecordNetWorkResultBean.getData();
                if (datas != null) {
                    mDatas_Search.addAll(datas);
                }else {
                       Toast.makeText(activity,selfRecordNetWorkResultBean.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
                leaderSearchMemberAdapter.notifyDataSetChanged();
            }
        });
    }

}
