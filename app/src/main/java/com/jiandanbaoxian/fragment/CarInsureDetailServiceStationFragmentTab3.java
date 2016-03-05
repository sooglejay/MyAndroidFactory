package com.jiandanbaoxian.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.DetailCarInsureTab3ServiceStationAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.FourService;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.util.JsonUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.AutoListView;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的保险-车险-tab3
 */
public class CarInsureDetailServiceStationFragmentTab3 extends BaseFragment {
    private SwipeRefreshLayout swipe_layout;
    private AutoListView listView;
    private List<FourService> listData = new ArrayList<>();
    private DetailCarInsureTab3ServiceStationAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_car_insure_tab3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {

        listView = (AutoListView) view.findViewById(R.id.list_view);
        swipe_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        UIUtils.initSwipeRefreshLayout(swipe_layout);
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listData.clear();
                getFourServiceInfo();
            }
        });
        mAdapter = new DetailCarInsureTab3ServiceStationAdapter(this.getActivity(), listData);
        listView.setAdapter(mAdapter);
        listView.setLoading(false);
        getFourServiceInfo();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    private void getFourServiceInfo() {
        UserRetrofitUtil.getFourServiceInfo(this.getActivity(), new NetCallback<NetWorkResultBean<Object>>(this.getActivity()) {
            @Override
            public void onFailure(RetrofitError error, String message) {

                swipe_layout.setRefreshing(false);

            }

            @Override
            public void success(NetWorkResultBean<Object> commDataNetWorkResultBean, Response response) {
                swipe_layout.setRefreshing(false);
                if (commDataNetWorkResultBean != null) {
                    int status = commDataNetWorkResultBean.getStatus();
                    switch (status) {
                        case HttpsURLConnection.HTTP_OK:
                            CommData bean = JsonUtil.getSerializedObject(commDataNetWorkResultBean.getData(),CommData.class);
                            if (bean != null && bean.getFourService() != null) {
                                listData.clear();
                                listData.addAll(bean.getFourService());
                                mAdapter.notifyDataSetChanged();
                            }
                            break;
                        default:
                            Toast.makeText(getActivity(), commDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }
        });
    }

}
