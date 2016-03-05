package com.jiandanbaoxian.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.DetailCarInsureTab3ServiceStationAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.FourService;
import com.jiandanbaoxian.model.NetWorkResultBean;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 历史报价-点击item-具体的报价页面-tab3
 */
public class HistoryPriceDetailFragmentTab3 extends BaseFragment {


    private ListView listView;
    private List<FourService> listData = new ArrayList<>();
    private DetailCarInsureTab3ServiceStationAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_price_detail_tab3, container, false);
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
        UserRetrofitUtil.getFourServiceInfo(this.getActivity(), new NetCallback<NetWorkResultBean<Object>>(this.getActivity()) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                Toast.makeText(getActivity(), "请检查网络设置", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void success(NetWorkResultBean<Object> commDataNetWorkResultBean, Response response) {

                if (commDataNetWorkResultBean != null) {
                    int status = commDataNetWorkResultBean.getStatus();
                    switch (status) {
                        case HttpsURLConnection.HTTP_OK:
                            CommData bean = (CommData) commDataNetWorkResultBean.getData();
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
