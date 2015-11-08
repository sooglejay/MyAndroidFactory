package com.jsb.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.DetailCarInsureTab3ServiceStationAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.model.CommData;
import com.jsb.model.FourService;
import com.jsb.model.NetWorkResultBean;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的保险-车险-tab3
 */
public class CarInsureDetailServiceStationFragmentTab3 extends BaseFragment {

    private ListView listView;
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

        listView = (ListView) view.findViewById(R.id.list_view);
        mAdapter = new DetailCarInsureTab3ServiceStationAdapter(this.getActivity(), listData);
        listView.setAdapter(mAdapter);
        getFourServiceInfo();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    private void getFourServiceInfo() {
        UserRetrofitUtil.getFourServiceInfo(this.getActivity(), new NetCallback<NetWorkResultBean<CommData>>(this.getActivity()) {
            @Override
            public void onFailure(RetrofitError error ,String message) {
                if(!TextUtils.isEmpty(message)) {
                    Toast.makeText(CarInsureDetailServiceStationFragmentTab3.this.getActivity(), message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void success(NetWorkResultBean<CommData> commDataNetWorkResultBean, Response response) {
                if (commDataNetWorkResultBean.getData() != null && commDataNetWorkResultBean.getData().getFourService() != null)
                {
                    listData.addAll(commDataNetWorkResultBean.getData().getFourService());
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
