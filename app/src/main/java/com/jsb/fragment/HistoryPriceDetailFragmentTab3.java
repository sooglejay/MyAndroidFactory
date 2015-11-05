package com.jsb.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jsb.R;
import com.jsb.adapter.DetailCarInsureTab3ServiceStationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史报价-点击item-具体的报价页面-tab3
 */
public class HistoryPriceDetailFragmentTab3 extends BaseFragment {

    private ListView listView;
    private List<Object> listData = new ArrayList<>();
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
        listData.clear();
        listData.add("4s店（4家）");
        listData.add(1);
        listData.add(1);
        listData.add(1);
        listData.add(1);
        listData.add("修理厂（4家）");
        listData.add(1);
        listData.add(1);
        listData.add(1);
        listData.add(1);
        mAdapter = new DetailCarInsureTab3ServiceStationAdapter(this.getActivity(), listData);
        listView.setAdapter(mAdapter);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
