package com.jsb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.SpinnerDropDownAdapter;
import com.jsb.constant.StringConstant;
import com.jsb.event.BusEvent;
import com.jsb.ui.BrowserActivity;
import com.jsb.ui.PullMoneyActivity;
import com.jsb.ui.TimePickerActivity;
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
    private Spinner week_number_spinner;
    private Spinner car_number_spinner;

    private List<String> mCarNumbersStringList = new ArrayList<>();
    private List<String> mWeekNumbersStringList = new ArrayList<>();
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_date_interval;
    private Switch weekSwitchTabView;
    private Switch dateSwitchTabView;
    private LinearLayout datePickerLayout;


    private RightControlFragment rightControlFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        rightControlFragment = new RightControlFragment();//涉及到权限操作时，需要临时输入密码并验证


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


        //提现
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
        weekSwitchTabView.setChecked(false);
        weekSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "开", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "关", Toast.LENGTH_SHORT).show();

                }
            }

        });




        // 滑动按钮-选择预约停保的时间
        dateSwitchTabView = (Switch) view.findViewById(R.id.date_switch_tab_view);
        dateSwitchTabView.setChecked(false);//默认是关闭
        dateSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Toast.makeText(getActivity(), "开", Toast.LENGTH_SHORT).show();
                    rightControlFragment.showDialog(ShutInsureFragment.this.getActivity(),ShutInsureFragment.this.getFragmentManager(),RightControlFragment.Dialog_RightControl);
                 } else {
                    Toast.makeText(getActivity(), "关", Toast.LENGTH_SHORT).show();
                }
            }

        });



        //预约停保  的textView，显示开始和结束的时间文本和时间间隔
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


        //车牌号Spinner
        car_number_spinner = (Spinner)view.findViewById(R.id.car_number_spinner);
        mCarNumbersStringList = Arrays.asList(getResources().getStringArray(R.array.carNumberArray));
        car_number_spinner.setAdapter(new SpinnerDropDownAdapter(this.getActivity(),mCarNumbersStringList));

        //周Spinner
        week_number_spinner = (Spinner)view.findViewById(R.id.week_number_spinner);
        mWeekNumbersStringList = Arrays.asList(getResources().getStringArray(R.array.weekArray));
        week_number_spinner.setAdapter(new SpinnerDropDownAdapter(this.getActivity(),mWeekNumbersStringList));
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * EventBus 广播
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
