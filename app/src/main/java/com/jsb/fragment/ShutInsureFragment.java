package com.jsb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.constant.StringConstant;
import com.jsb.event.BusEvent;
import com.jsb.model.Banner;
import com.jsb.ui.BrowserActivity;
import com.jsb.ui.PullMoneyActivity;
import com.jsb.ui.TimePickerActivity;
import com.jsb.widget.BannerView;
import com.jsb.widget.TitleBar;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.Switch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShutInsureFragment extends BaseFragment {
   public static final int ACTION_TIME_STRING = 1000;

    private TitleBar titleBar;
    private TextView tvPullMoney;
    private LinearLayout layoutRule;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_date_interval;
    private BannerView bannerView;
    private List<Banner> bannerDatas = new ArrayList<>();
    private Switch weekSwitchTabView;
    private Switch dateSwitchTabView;
    private LinearLayout datePickerLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        initTitleBar(view);

        //提现
        tvPullMoney = (TextView) view.findViewById(R.id.tv_pull_money);
        tvPullMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), PullMoneyActivity.class));
            }
        });

        layoutRule = (LinearLayout) view.findViewById(R.id.layout_rule);
        layoutRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserActivity.startActivity(getActivity(), true);
            }
        });


        //限行停保 滑动按钮
        weekSwitchTabView = (Switch) view.findViewById(R.id.week_switch_tab_view);
        weekSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch aSwitch, boolean b) {
                if(b)
                {
                    Toast.makeText(getActivity(),"focus!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(),"没有 关注!:"+aSwitch.toString(),Toast.LENGTH_LONG).show();

                }
            }
        });
        //预约停保
        tv_start_date = (TextView)view.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView)view.findViewById(R.id.tv_end_date);
        tv_date_interval = (TextView)view.findViewById(R.id.tv_date_interval);
        // 滑动按钮
        dateSwitchTabView = (Switch) view.findViewById(R.id.date_switch_tab_view);
        dateSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch aSwitch, boolean b) {
                if(b)
                {
                    Toast.makeText(getActivity(),"dateSwitchTabView:focus!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(),"dateSwitchTabView:没有 关注!:"+aSwitch.toString(),Toast.LENGTH_LONG).show();

                }
            }
        });

        datePickerLayout = (LinearLayout)view.findViewById(R.id.layout_date_picker);
        datePickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivityForResult(new Intent(getActivity(), TimePickerActivity.class),ACTION_TIME_STRING);
            }
        });

        initBannerView(view);
        initSpinner(view);

    }

    private void initTitleBar(View view) {
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.shutInsure, -1, -1, StringConstant.empty, StringConstant.share);
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
    }


    /**
     *
     * @param view
     */
    public void initSpinner(View view)
    {


        //选择车型
        Spinner carNumberSpinner = (Spinner)view.findViewById(R.id.car_number_spinner);
        //每周停保 Spinner
        Spinner weekAppointSpinner = (Spinner)view.findViewById(R.id.week_spinner);
         List<String> carDataSet = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.license_plate_number_array)));
         List<String> weekDataSet = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.week_array)));

        ArrayAdapter<String> carNumberSpinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.aaaa_row_spn, carDataSet);
        ArrayAdapter<String> weekAppointAdapter = new ArrayAdapter<>(getActivity(), R.layout.aaaa_row_spn, weekDataSet);
        carNumberSpinnerAdapter.setDropDownViewResource(R.layout.aaaaaa_row_spn_dropdown);
        weekAppointAdapter.setDropDownViewResource(R.layout.aaaaaa_row_spn_dropdown);
        carNumberSpinner.setAdapter(carNumberSpinnerAdapter);
        weekAppointSpinner.setAdapter(weekAppointAdapter);

    }
    public void initBannerView(View view)
    {
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


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * EventBus
     * @param event
     */
    public void onEventMainThread(BusEvent event) {
        switch (event.getMsg()) {
            case BusEvent.MSG_INT_TIME:
                String startTimeStr = event.getStart_time();
                String endTimeStr = event.getEnd_time();
                String timeIntvalStr = event.getInterval_time();
                tv_start_date.setText(startTimeStr);
                tv_end_date.setText(endTimeStr);
                tv_date_interval.setText(timeIntvalStr);
                break;
            default:
                break;
        }
    }

}
