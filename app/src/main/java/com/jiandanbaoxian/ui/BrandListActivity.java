package com.jiandanbaoxian.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.BrandListAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.model.Brand;
import com.jiandanbaoxian.model.NetWorkResultBean;
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
public class BrandListActivity extends BaseActivity {
    private SwipeRefreshLayout swipeLayout;
    private ListView list_view;
    private TextView noResultsView;
    private List<Brand> brandList = new ArrayList<>();

    private TitleBar titleBar;

    private Activity activity;

    private BrandListAdapter adapter;
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

        adapter = new BrandListAdapter(brandList, this);
        list_view.setAdapter(adapter);
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFourServiceBrands();
            }
        });


        titleBar.initTitleBarInfo("选择品牌", R.drawable.arrow_left, -1, "", "确定");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {
                String brand_name = "";
                Integer id = -1;
                for (Brand brand : brandList) {
                    if (brand.isSelected()) {
                        brand_name = brand.getBrand_name();
                        id = brand.getId();
                        break;
                    }
                }
                Intent intent = getIntent();
                intent.putExtra("brand_name", brand_name);
                intent.putExtra("id", id);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        default_name = getIntent().getStringExtra("brand_name");

        getFourServiceBrands();
    }


    private void getFourServiceBrands() {
        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(activity);
        progressDialogUtil.show("正在获取信息...");
        UserRetrofitUtil.getFourServiceBrands(activity, "", new NetCallback<NetWorkResultBean<List<Brand>>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                progressDialogUtil.hide();
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void success(NetWorkResultBean<List<Brand>> brandNetWorkResultBean, Response response) {
                progressDialogUtil.hide();

                swipeLayout.setRefreshing(false);

                if (brandNetWorkResultBean.getData() != null) {
                    brandList.clear();
                    brandList.addAll(brandNetWorkResultBean.getData());
                }
                for (Brand brand : brandList) {
                    if (brand.getBrand_name().equals(default_name)) {
                        brand.setIsSelected(true);
                        break;
                    }else if (brand.getBrand_name().equals("修理厂")) {
                        brand.setIsSelected(true);
                    }
                }
                adapter.notifyDataSetChanged();

            }
        });
    }
}
