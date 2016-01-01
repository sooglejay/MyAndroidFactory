package com.jiandanbaoxian.ui.me.myteam;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.SearchMemberAdapter;
import com.jiandanbaoxian.adapter.MyTeamForMemberAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.fragment.DialogFragmentCreater;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.RangeData;
import com.jiandanbaoxian.model.RangeRecord;
import com.jiandanbaoxian.model.SelfRecord;
import com.jiandanbaoxian.model.TeamData;
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
    private TextView noResultsView;

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
    private SearchMemberAdapter searchMemberAdapter;

    private List<Userstable> userstableList = new ArrayList<>();//团长查看加团请求等消息
    private DialogFragmentCreater dialogFragmentController;

    private TeamData teamData;//团长查看团队信息，这个对象就是团队信息对象
    private PopWindowUtils popWindowUtilsLeaderConsiderRequest;//leader  check  the request

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team_for_leader);
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        mPopWindow = new PopWindowUtils(this);
        userstable = getIntent().getExtras().getParcelable(ExtraKey);
        popWindowUtilsLeaderConsiderRequest = new PopWindowUtils(this);
        dialogFragmentController = new DialogFragmentCreater();
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
        searchMemberAdapter = new SearchMemberAdapter(mDatas_Search, this);
        listView.setAdapter(adapter);
        listView.setLoading(false);
        noResultsView = (TextView) findViewById(R.id.emptyElement);
        listView.setEmptyView(noResultsView);


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
                    listView.setAdapter(searchMemberAdapter);
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
                                BrowserActivity.startActivity(activity, true);
                                break;
                        }
                        mPopWindow.dismiss();
                    }
                });
            }
        });

        findViewById(R.id.layout_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                getJoinRequest();
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
                teamData = teamDataNetWorkResultBean.getData();

                if (teamData != null) {

                    //团队人数
                    if (teamData.getPersons() != null) {
                        tvMemberAmount.setText(teamData.getPersons() + "人");
                    } else {
                        tvMemberAmount.setText("");
                    }

                    //月度保费
                    if (teamData.getTotalFeeOfMonth() != null) {
                        tvMonthAmount.setText(teamData.getTotalFeeOfMonth() + "");
                    } else {
                        tvMonthAmount.setText("");
                    }

                    //年度保费
                    if (teamData.getTotalFeeOfYear() != null) {
                        tvYearAmount.setText(teamData.getTotalFeeOfYear() + "");
                    } else {
                        tvYearAmount.setText("");
                    }
                }
                getJoinRequest();//团长获取加团信息
            }
        });
    }


    //团长搜索团员
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
                } else {
                    Toast.makeText(activity, selfRecordNetWorkResultBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                searchMemberAdapter.notifyDataSetChanged();
            }
        });
    }

    //团长查看入团请求，网络请求方法
    private void getJoinRequest() {
        if (teamData == null) {
            Toast.makeText(activity, "团队信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        UserRetrofitUtil.getJoinRequest(activity, userid, new NetCallback<NetWorkResultBean<TeamData>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                Userstable userstable = new Userstable();
                userstable.setName("看到我就说明你是团长，但是没有你任何邀请信息,弹出此对话框，只为测试！");
                userstable.setId(13);
                userstableList.add(userstable);
                popWindowUtilsLeaderConsiderRequest.showPopWindowInMyTeamForLeaderConsiderRequest(container, activity, userstableList, teamData);
                if (!TextUtils.isEmpty(message)) {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(NetWorkResultBean<TeamData> stringNetWorkResultBean, Response response) {
                userstableList.clear();
                TeamData bean = stringNetWorkResultBean.getData();
                if (bean != null && bean.getNewMembers() != null) {
                    userstableList.addAll(bean.getNewMembers());
                    popWindowUtilsLeaderConsiderRequest.showPopWindowInMyTeamForLeaderConsiderRequest(container, activity, userstableList, teamData);
                    ivNotification.setImageResource(R.drawable.icon_has_notification);
                } else {
                    ivNotification.setImageResource(R.drawable.icon_no_notification);
                }
            }
        });
    }
}
