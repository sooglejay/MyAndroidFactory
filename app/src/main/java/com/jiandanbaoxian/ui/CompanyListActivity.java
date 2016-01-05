package com.jiandanbaoxian.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.CompanyListAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.Brand;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.InsuranceCompanyInfo;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by JammyQtheLab on 2015/12/16.
 */
public class CompanyListActivity extends BaseActivity {
    private SwipeRefreshLayout swipeLayout;
    private ListView list_view;
    private TextView noResultsView;
    private List<InsuranceCompanyInfo> companyList = new ArrayList<>();

    private TitleBar titleBar;

    private Activity activity;

    private CompanyListAdapter adapter;
    String default_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        activity = this;
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        list_view = (ListView) findViewById(R.id.list_view);
        noResultsView = (TextView) findViewById(R.id.emptyElement);

        adapter = new CompanyListAdapter(companyList, this);
        list_view.setAdapter(adapter);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFourServiceBrands();
            }
        });


        titleBar.initTitleBarInfo("选择公司", R.drawable.arrow_left, -1, "", "确定");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {
                String company_name = "";
                for (InsuranceCompanyInfo companyInfo : companyList) {
                    if (companyInfo.isSelected()) {
                        company_name = companyInfo.getCompanyname();
                        break;
                    }
                }
                int userid = PreferenceUtil.load(activity, PreferenceConstant.userid, -1);
                final String finalCompany_name = company_name;
                UserRetrofitUtil.modifySelfInfo(activity, userid, -1, -1, "-1", "-1", "-1", "-1", company_name, null, new NetCallback<NetWorkResultBean<Userstable>>(activity) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        Toast.makeText(activity, "服务器无响应，请检查网络！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void success(NetWorkResultBean<Userstable> userstableNetWorkResultBean, Response response) {
                        Intent intent = getIntent();
                        intent.putExtra("company_name", finalCompany_name);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });


            }
        });

        default_name = getIntent().getStringExtra("company_name");

        getFourServiceBrands();
    }


    private void getFourServiceBrands() {
        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(activity);
        progressDialogUtil.show("正在获取信息...");
        UserRetrofitUtil.getInsuranceCompanyInfo(activity, new NetCallback<NetWorkResultBean<CommData>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                progressDialogUtil.hide();
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void success(NetWorkResultBean<CommData> brandNetWorkResultBean, Response response) {
                progressDialogUtil.hide();

                swipeLayout.setRefreshing(false);

                if (brandNetWorkResultBean.getData() != null) {
                    companyList.clear();
                    companyList.addAll(brandNetWorkResultBean.getData().getInsurancecompanyInfo());
                }
                for (InsuranceCompanyInfo brand : companyList) {
                    if (brand.getCompanyname().equals(default_name)) {
                        brand.setIsSelected(true);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();


            }
        });
    }
}
