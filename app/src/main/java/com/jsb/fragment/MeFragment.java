package com.jsb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsb.constant.StringConstant;
import com.jsb.R;
import com.jsb.ui.MyCallPoliceActivity;
import com.jsb.ui.MyHistorySaleActivity;
import com.jsb.ui.MyInsureActivity;
import com.jsb.ui.MyMoneyPocketActivity;
import com.jsb.widget.TitleBar;

/**
 * 我的-主框架-tab3
 */
public class MeFragment extends BaseFragment {

    private TitleBar titleBar;
    private View layout_my_insure;
    private View layout_my_history_sale;
    private View layout_my_call_plice;
    private View layout_my_money_packet;
    private View layout_my_share;
    private View layout_my_team;

    private TextView my_money_packet;
    private TextView my_share;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }


    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar)view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.me,-1,-1,"","");

        //我的保险
        view.findViewById(R.id.layout_my_insure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyInsureActivity.class));
            }
        });

        //我的历史报价
        view.findViewById(R.id.layout_my_history_sale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyHistorySaleActivity.class));
            }
        });

        //我的报案
        view.findViewById(R.id.layout_my_call_plice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyCallPoliceActivity.class));
            }
        });

        //我的钱包
        view.findViewById(R.id.layout_my_money_packet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyMoneyPocketActivity.class));
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
