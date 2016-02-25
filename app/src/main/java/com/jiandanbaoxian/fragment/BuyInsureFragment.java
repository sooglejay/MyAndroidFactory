package com.jiandanbaoxian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.ui.buyinsurance.car_insurance.CarInsuranceBaseInfoActivity;
import com.jiandanbaoxian.ui.buyinsurance.InsureJiaBanDogActivity;
import com.jiandanbaoxian.ui.buyinsurance.InsureOnDrivingActivity;
import com.jiandanbaoxian.ui.buyinsurance.PengciActivity;
import com.jiandanbaoxian.widget.TitleBar;
import com.umeng.analytics.MobclickAgent;


/**
 * 买保险-主框架-tab2
 */
public class BuyInsureFragment extends BaseFragment {


    private TitleBar titleBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //统计页面
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.buyInsure, -1, -1, "", "");

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
        view.findViewById(R.id.layout_insure_pengci).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), PengciActivity.class));
            }
        });


        view.findViewById(R.id.layout_insure_car).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CarInsuranceBaseInfoActivity.class));

            }
        });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
