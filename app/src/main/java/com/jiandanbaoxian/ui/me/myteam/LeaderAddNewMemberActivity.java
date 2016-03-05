package com.jiandanbaoxian.ui.me.myteam;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.CreateTeamAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.fragment.DialogFragmentCreater;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.AutoListView;
import com.jiandanbaoxian.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by JammyQtheLab on 2015/11/12.
 */
public class LeaderAddNewMemberActivity extends BaseActivity {

    private DialogFragmentCreater dialogFragmentCreater;

    private int userid;
    private CreateTeamAdapter adapter;
    private List<Object> mDatas = new ArrayList<>();
    private int pageSize = 10;
    private int pageNum = 1;

    private Activity activity;
    private TitleBar titleBar;
    private EditText etTeamName;
    private SwipeRefreshLayout swipeLayout;
    private AutoListView listView;

    private TextView noResultsView;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-12 07:39:38 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        etTeamName = (EditText) findViewById(R.id.et_team_name);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        listView = (AutoListView) findViewById(R.id.list_view);
        noResultsView = (TextView) findViewById(R.id.emptyElement);
        listView.setLoading(false);


        titleBar.initTitleBarInfo("新增团员", R.drawable.arrow_left, -1, "", "确定");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                activity.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

                final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(activity);
                progressDialogUtil.show("正在提交请求...");
                String memberids = "";
                int memberAmount = 0;
                for (Object object : mDatas) {
                    if (object instanceof Userstable) {
                        Userstable bean = (Userstable) object;
                        if (bean.getSuperFlag() != null && bean.getSuperFlag()) {
                            memberids += bean.getId() + ",";
                            memberAmount++;
                        }
                    }
                }
                final int finalMemberAmount = memberAmount;
                UserRetrofitUtil.addNewMember(activity, userid, memberids, new NetCallback<NetWorkResultBean<Object>>(activity) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        progressDialogUtil.hide();
                        Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void success(NetWorkResultBean<Object> stringNetWorkResultBean, Response response) {
                        progressDialogUtil.hide();

                        if (stringNetWorkResultBean != null) {
                            int status = stringNetWorkResultBean.getStatus();
                            switch (status) {
                                case HttpsURLConnection.HTTP_OK:

                                    if (finalMemberAmount > 0) {//如果选择了 邀请的成员，就弹出对话框
                                        dialogFragmentCreater.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                                            @Override
                                            public void viewClick(String tag) {
                                                activity.finish();
                                            }

                                            @Override
                                            public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                                                if (tv_content instanceof TextView) {
                                                    String text = "您已向选择的团员发出了成团邀请，等待他们回复！";
                                                    ((TextView) tv_content).setText(text);
                                                }
                                            }
                                        });
                                        dialogFragmentCreater.showDialog(activity, DialogFragmentCreater.DialogShowSingleChoiceDialog);
                                    } else {
                                        Toast.makeText(activity, "邀请发送成功！", Toast.LENGTH_SHORT).show();
                                        activity.finish();
                                    }
                                    break;
                                default:
                                    Toast.makeText(activity, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }


                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_add_new_member);

        activity = this;
        findViews();
        setUpListener();
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        adapter = new CreateTeamAdapter(mDatas, this);
        listView.setAdapter(adapter);
        listView.setEmptyView(noResultsView);
        dialogFragmentCreater = new DialogFragmentCreater();
        dialogFragmentCreater.setDialogContext(this, getSupportFragmentManager());
        getAvailable();
    }

    private void setUpListener() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                pageNum = 0;
                getAvailable();
            }
        });
        listView.setOnLoadListener(new AutoListView.OnLoadListener() {
            @Override
            public void onLoad() {
                pageNum++;
                getAvailable();
            }
        });

    }

    private void getAvailable() {
        swipeLayout.setEnabled(false);
        UserRetrofitUtil.getAvailable(this, userid, pageSize, pageNum, new NetCallback<NetWorkResultBean<Object>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                swipeLayout.setEnabled(true);
                swipeLayout.setRefreshing(false);
                listView.setLoading(false);

                Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void success(NetWorkResultBean<Object> listNetWorkResultBean, Response response) {


                if (listNetWorkResultBean != null) {
                    int status = listNetWorkResultBean.getStatus();
                    switch (status) {
                        case HttpsURLConnection.HTTP_OK:
                            if (listNetWorkResultBean != null && listNetWorkResultBean.getData() != null) {
                                List<Userstable> userstableList = (List<Userstable>) listNetWorkResultBean.getData();
                                if (pageNum == 1) {
                                    if (userstableList.size() > 0) {
                                        mDatas.add("选择团员");
                                    } else {
                                        mDatas.add("没有可选团员");
                                    }
                                }
                                mDatas.addAll(userstableList);
                            }
                            adapter.notifyDataSetChanged();
                            swipeLayout.setEnabled(true);
                            swipeLayout.setRefreshing(false);
                            listView.setLoading(false);
                            break;
                        default:
                            Toast.makeText(activity, listNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                            break;
                    }
                }


            }
        });
    }

}
