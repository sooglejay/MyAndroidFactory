package com.jsb.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.TeamListAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.model.FreedomData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.util.ProgressDialogUtil;
import com.jsb.util.UIUtils;
import com.jsb.widget.AutoListView;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by JammyQtheLab on 2015/11/10.
 */
public class JoinTeamActivity extends BaseActivity {
    private TitleBar titleBar;
    private AutoListView list_view;
    private TeamListAdapter adapter;
    private List<FreedomData> mDatas = new ArrayList<>();
    private SwipeRefreshLayout swipeLayout;
    private Activity activity;

    private ProgressDialogUtil progressDialogUtil;

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
        titleBar.initTitleBarInfo("加入团队", R.drawable.arrow_left, -1, "", "");
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        list_view = (AutoListView) findViewById(R.id.list_view);
        list_view.setLoading(false);
        adapter = new TeamListAdapter(this, mDatas);
        list_view.setAdapter(adapter);
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
                mDatas.clear();
                loadData();
            }
        });
        loadData();
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
                    mDatas.addAll(datas);
                    adapter.notifyDataSetChanged();
                }
                progressDialogUtil.hide();
            }
        });

    }
}
