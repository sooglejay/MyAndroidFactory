package com.jiandanbaoxian.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.BrandListAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.model.Brand;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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
        UserRetrofitUtil.getFourServiceBrands(activity, "", new NetCallback<NetWorkResultBean<Object>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                progressDialogUtil.hide();
                swipeLayout.setRefreshing(false);
                Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void success(NetWorkResultBean<Object> brandNetWorkResultBean, Response response) {
                progressDialogUtil.hide();

                swipeLayout.setRefreshing(false);
                if (brandNetWorkResultBean != null) {
                    int status = brandNetWorkResultBean.getStatus();
                    switch (status) {
                        case HttpsURLConnection.HTTP_OK:
                            if (brandNetWorkResultBean.getData() != null) {
                                brandList.clear();
                                List<Brand> brandList = null;
                                Object obj = brandNetWorkResultBean.getData();
                                if (obj instanceof String) {
                                    Toast.makeText(activity, brandNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    return;
                                }
                                try {
                                    String json = com.jiandanbaoxian.util.JsonUtil.toJson(obj);
                                    brandList = com.jiandanbaoxian.util.JsonUtil.fromJson(json, new TypeToken<List<Brand>>() {
                                    }.getType());
                                } catch (Exception e) {
                                    Log.e("qw", "出错啦！！！!");
                                }
                                if (brandList == null) {
                                    Toast.makeText(activity, "系统异常！", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                brandList.addAll(brandList);
                            }
                            for (Brand brand : brandList) {
                                if (brand.getBrand_name().equals(default_name)) {
                                    brand.setIsSelected(true);
                                    break;
                                } else if (brand.getBrand_name().equals("修理厂")) {
                                    brand.setIsSelected(true);
                                }
                            }
                            adapter.notifyDataSetChanged();
                            break;
                        default:
                            Toast.makeText(activity, brandNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                            break;
                    }
                }


            }
        });
    }
}
