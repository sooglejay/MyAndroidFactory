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
import com.jsb.model.Banner;
import com.jsb.ui.BrowserActivity;
import com.jsb.ui.InsureCarActivity;
import com.jsb.ui.InsureJiaBanDogActivity;
import com.jsb.ui.InsureOnDrivingActivity;
import com.jsb.widget.BannerView;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;


/**
 * 买保险-主框架-tab2
 */
public class BuyInsureFragment extends BaseFragment {


    private TitleBar titleBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar)view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.buyInsure,-1,-1,"","");

        view.findViewById(R.id.layout_insure_jiaban).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), InsureJiaBanDogActivity.class));

            }
        });
        view.findViewById(R.id.layout_insure_driving).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), InsureOnDrivingActivity.class));

            }
        });
        view.findViewById(R.id.layout_insure_line).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getActivity().startActivity(new Intent(getActivity(), InsureJiaBanDogActivity.class));
             }
         });


        view.findViewById(R.id.layout_insure_car).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), InsureCarActivity.class));

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
