package com.jsb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.constant.StringConstant;
import com.jsb.event.BusEvent;
import com.jsb.ui.BrowserActivity;
import com.jsb.ui.PullMoneyActivity;
import com.jsb.ui.TimePickerActivity;
import com.jsb.widget.ChoosePopWindowView;
import com.jsb.widget.TitleBar;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 停保险-主框架-tab1
 */
public class ShutInsureFragment extends BaseFragment {
   public static final int ACTION_TIME_STRING = 1000;

    private TitleBar titleBar;
    private TextView tvPullMoney;
    private TextView layout_rule;
    private TextView tvChooseCarNumber;
    private TextView tvChooseWeekNumber;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_date_interval;
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
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.shutInsure, -1, -1, StringConstant.empty, StringConstant.share);
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {

            }

            @Override
            public void onRightButtonClick(View v) {
                Toast.makeText(getActivity(), "hello,share", Toast.LENGTH_SHORT).show();
            }
        });


        //体现
        tvPullMoney = (TextView) view.findViewById(R.id.tv_pull_money);
        tvPullMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), PullMoneyActivity.class));
            }
        });


        //停保规则
        layout_rule = (TextView) view.findViewById(R.id.layout_rule);
        layout_rule.setOnClickListener(new View.OnClickListener() {
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
                if (b) {
                    Toast.makeText(getActivity(), "开", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "关", Toast.LENGTH_SHORT).show();

                }
            }
        });




        // 滑动按钮
        dateSwitchTabView = (Switch) view.findViewById(R.id.date_switch_tab_view);
        dateSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch aSwitch, boolean b) {
                if(b)
                {
                    Toast.makeText(getActivity(),"开",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),"关",Toast.LENGTH_SHORT).show();

                }
            }
        });



        //预约停保  的textView
        tv_start_date = (TextView)view.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView)view.findViewById(R.id.tv_end_date);
        tv_date_interval = (TextView)view.findViewById(R.id.tv_date_interval);


        //选择预约停保时间
        datePickerLayout = (LinearLayout)view.findViewById(R.id.layout_date_picker);
        datePickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivityForResult(new Intent(getActivity(), TimePickerActivity.class), ACTION_TIME_STRING);
            }
        });


        //选择 车牌号
        List<String> carNumberDataList = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.carNumberArray)));
        tvChooseCarNumber = (TextView)view.findViewById(R.id.carNumberTv);
        final ChoosePopWindowView popWindowView = new ChoosePopWindowView(getActivity(),carNumberDataList,ChoosePopWindowView.CHOOSE_POP_CAR);
        tvChooseCarNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowView.show(tvChooseCarNumber, tvChooseCarNumber);

            }
        });

        //选择 限行停保的 周天
        List<String> weekNumberDataList = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.weekArray)));
        tvChooseWeekNumber = (TextView)view.findViewById(R.id.tv_choose_week);
        final ChoosePopWindowView popWindowChooseWeekNumber = new ChoosePopWindowView(getActivity(),weekNumberDataList,ChoosePopWindowView.CHOOSE_POP_WEEK);
        tvChooseWeekNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowChooseWeekNumber.show(tvChooseWeekNumber,tvChooseWeekNumber);
            }
        });
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

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
