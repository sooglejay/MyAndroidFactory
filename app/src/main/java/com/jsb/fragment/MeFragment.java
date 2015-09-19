package com.jsb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsb.constant.StringConstant;
import com.jsb.R;
import com.jsb.ui.MyCallPoliceActivity;
import com.jsb.ui.MyHistorySaleActivity;
import com.jsb.ui.MyInsureActivity;
import com.jsb.ui.MyModifyPasswordActivity;
import com.jsb.ui.MyMoneyPacketActivity;
import com.jsb.ui.ShareActivity;
import com.jsb.widget.TitleBar;
import com.rey.material.widget.Button;

public class MeFragment extends BaseFragment {

    private TitleBar titleBar;
    private Button my_insure;
    private Button my_history_sale;
    private Button my_call_plice;

    private Button my_money_packet;
    private Button my_share;
    private Button my_modify_password;

    private Button my_clear_cache;
    private Button my_logout;



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

        my_share = (Button)view.findViewById(R.id.my_share);
        my_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ShareActivity.class));
            }
        });

        my_insure = (Button)view.findViewById(R.id.my_insure);
        my_insure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyInsureActivity.class));
            }
        });
        my_history_sale = (Button)view.findViewById(R.id.my_history_sale);
        my_history_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyHistorySaleActivity.class));
            }
        });
        my_call_plice = (Button)view.findViewById(R.id.my_call_plice);
        my_call_plice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyCallPoliceActivity.class));
            }
        });
        my_money_packet = (Button)view.findViewById(R.id.my_money_packet);
        my_money_packet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyMoneyPacketActivity.class));
            }
        });
        my_modify_password = (Button)view.findViewById(R.id.my_modify_password);
        my_modify_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyModifyPasswordActivity.class));
            }
        });



    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
