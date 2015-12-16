package com.jsb.ui.me.myteam;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.TeamListAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.model.FreedomData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.ui.BaseActivity;
import com.jsb.util.ProgressDialogUtil;
import com.jsb.util.UIUtils;
import com.jsb.widget.AutoListView;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 加入团队
 */
public class FreeJoinTeamActivity extends BaseActivity {

    private TitleBar titleBar;
    private AutoListView list_view;
    private TextView noResultsView;
    private TeamListAdapter adapter;
    private List<FreedomData> mDatas = new ArrayList<>();
    private SwipeRefreshLayout swipeLayout;
    private Activity activity;

    private ProgressDialogUtil progressDialogUtil;

    private EditText et_search_team;
    private LinearLayout layout_clear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);
        activity = this;
        progressDialogUtil = new ProgressDialogUtil(this,true);
        setUpView();
        setUpListener();
    }


    private void setUpView() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        et_search_team = (EditText) findViewById(R.id.et_search_team);
        titleBar.initTitleBarInfo("加入团队", R.drawable.arrow_left, -1, "", "");
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        list_view = (AutoListView) findViewById(R.id.list_view);
        list_view.setLoading(false);
        adapter = new TeamListAdapter(this, mDatas);
        list_view.setAdapter(adapter);
        noResultsView = (TextView) findViewById(R.id.emptyElement);
        layout_clear = (LinearLayout) findViewById(R.id.layout_clear);
        layout_clear.setVisibility(View.GONE);
        list_view.setEmptyView(noResultsView);
    }

    private void setUpListener() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                activity.finish();
            }

            @Override
            public void onRightButtonClick(View v) {
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        et_search_team.addTextChangedListener(textWatcher);
        loadData();

        layout_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search_team.setText("");
            }
        });
      }

    private void loadData()
    {
        progressDialogUtil.show("正在获取数据...");
        UserRetrofitUtil.getFourTeamInfo(activity, new NetCallback<NetWorkResultBean<List<FreedomData>>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                swipeLayout.setRefreshing(false);
                if (!TextUtils.isEmpty(message)) {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
                progressDialogUtil.hide();
            }

            @Override
            public void success(NetWorkResultBean<List<FreedomData>> listNetWorkResultBean, Response response) {
                swipeLayout.setRefreshing(false);
                List<FreedomData> datas = listNetWorkResultBean.getData();
                if (datas != null) {
                    mDatas.clear();
                    mDatas.addAll(datas);
                    adapter.notifyDataSetChanged();
                }
                progressDialogUtil.hide();
            }
        });
    }


    /**
     * 搜索团队
     * @param key
     */
    private void searchTeam(String key)
    {
        UserRetrofitUtil.searchTeam(activity, key, new NetCallback<NetWorkResultBean<List<FreedomData>>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                mDatas.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(activity, "没有搜索到任何团队信息！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(NetWorkResultBean<List<FreedomData>> listNetWorkResultBean, Response response) {
                List<FreedomData> datas = listNetWorkResultBean.getData();
                mDatas.clear();
                if (datas != null) {
                    mDatas.addAll(datas);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!TextUtils.isEmpty(s))
            {
                swipeLayout.setEnabled(false);
                layout_clear.setVisibility(View.VISIBLE);
                searchTeam(s.toString());
            } else {
                swipeLayout.setEnabled(true);
                loadData();
                layout_clear.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
