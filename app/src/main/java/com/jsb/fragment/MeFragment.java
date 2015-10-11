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
import com.jsb.ui.MyModifyPasswordActivity;
import com.jsb.ui.MyMoneyPacketActivity;
import com.jsb.ui.ShareActivity;
import com.jsb.widget.TitleBar;
import com.rey.material.widget.Button;
/**
 * 我的-主框架-tab3
 */
public class MeFragment extends BaseFragment {

    private TitleBar titleBar;
    private TextView my_insure;
    private TextView my_history_sale;
    private TextView my_call_plice;

    private TextView my_money_packet;
    private TextView my_share;
    private TextView my_modify_password;



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

        my_share = (TextView)view.findViewById(R.id.my_share);
        my_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ShareActivity.class));
            }
        });

        my_insure = (TextView)view.findViewById(R.id.my_insure);
        my_insure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyInsureActivity.class));
            }
        });
        my_history_sale = (TextView)view.findViewById(R.id.my_history_sale);
        my_history_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyHistorySaleActivity.class));
            }
        });
        my_call_plice = (TextView)view.findViewById(R.id.my_call_plice);
        my_call_plice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyCallPoliceActivity.class));
            }
        });
        my_money_packet = (TextView)view.findViewById(R.id.my_money_packet);
        my_money_packet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyMoneyPacketActivity.class));
            }
        });
        my_modify_password = (TextView)view.findViewById(R.id.my_modify_password);
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
