package com.jsb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.constant.StringConstant;
import com.jsb.R;
import com.jsb.model.Banner;
import com.jsb.ui.BrowserActivity;
import com.jsb.ui.PullMoneyActivity;
import com.jsb.widget.BannerView;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class ShutInsureFragment extends BaseFragment {

    private TitleBar titleBar;
    private TextView tvPullMoney;
    private TextView tvRule;
    private Spinner licensePlateNumberSpinner;
    private Spinner weekSpinner;
    private Spinner dateSpinner;
    private BannerView bannerView;
    private List<Banner> bannerDatas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        tvPullMoney = (TextView) view.findViewById(R.id.tv_pull_money);
        tvRule = (TextView) view.findViewById(R.id.tv_rule);

        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.shutInsure, -1, -1, StringConstant.empty, StringConstant.share);


        //选择车型
        licensePlateNumberSpinner = (Spinner) view.findViewById(R.id.license_plate_number_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.license_plate_number_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        licensePlateNumberSpinner.setAdapter(adapter);


        //广告
        Banner b1 = new Banner();
        Banner b2 = new Banner();
        Banner b3 = new Banner();
        Banner b4 = new Banner();
        b1.setUrl("http://img4.imgtn.bdimg.com/it/u=2297119962,3821452646&fm=21&gp=0.jpg");
        b2.setUrl("http://img0.imgtn.bdimg.com/it/u=2217897727,3687268377&fm=21&gp=0.jpg");
        b3.setUrl("http://img4.imgtn.bdimg.com/it/u=1704061436,275613074&fm=21&gp=0.jpg");
        b4.setUrl("http://img4.imgtn.bdimg.com/it/u=2297119962,3821452646&fm=21&gp=0.jpg");

        b1.setFile_location("http://img4.imgtn.bdimg.com/it/u=2297119962,3821452646&fm=21&gp=0.jpg");
        b2.setFile_location("http://img0.imgtn.bdimg.com/it/u=2217897727,3687268377&fm=21&gp=0.jpg");
        b3.setFile_location("http://img4.imgtn.bdimg.com/it/u=1704061436,275613074&fm=21&gp=0.jpg");
        b4.setFile_location("http://g.hiphotos.baidu.com/image/h%3D360/sign=caa2d267cfef7609230b9f991edca301/6d81800a19d8bc3e7763d030868ba61ea9d345e5.jpg");

        bannerDatas.add(b1);
        bannerDatas.add(b2);
        bannerDatas.add(b3);
        bannerDatas.add(b4);
        //init banner view
        bannerView = (BannerView) view.findViewById(R.id.banner_view);
        bannerView.initBannerAdapter(getActivity(), bannerDatas, new BannerView.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                BrowserActivity.startActivity(getActivity(), bannerDatas.get(position).getUrl());
            }
        });


        //每周停保 Spinner
        weekSpinner = (Spinner) view.findViewById(R.id.week_spinner);
        ArrayAdapter<CharSequence> weekSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.week_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekSpinner.setAdapter(weekSpinnerAdapter);


        //预约停保 Spinner
        dateSpinner = (Spinner) view.findViewById(R.id.date_spinner);
        ArrayAdapter<CharSequence> dateSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.week_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(dateSpinnerAdapter);

        setLisenter();
    }

    private void setLisenter() {

        //titleBar 的点击事件
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {

            }

            @Override
            public void onRightButtonClick(View v) {
                Toast.makeText(getActivity(), "hello,share", Toast.LENGTH_SHORT).show();
            }
        });

        //选择车牌号码
        licensePlateNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

            }
        });

        //点击提现
        tvPullMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), PullMoneyActivity.class));
            }
        });

        //点击规则
        tvRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserActivity.startActivity(getActivity(), true);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
