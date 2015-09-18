package com.jsb.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.constant.ExtraConstants;
import com.jsb.event.BusEvent;
import com.jsb.widget.DatePicker.bizs.themes.DPCNTheme;
import com.jsb.widget.DatePicker.bizs.themes.DPTManager;
import com.jsb.widget.DatePicker.cons.DPMode;
import com.jsb.widget.DatePicker.views.DatePicker;
import com.jsb.widget.TitleBar;
import com.rey.material.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

public class TimePickerActivity extends BaseActivity {

    private TitleBar mTitleBar;
    private TextView mTvStartTime;
    private TextView mTvEndTime;

    private Button btnConfirmTime, btnCancelTime;

    private int count = 0 , intervalDays;
    private String timeStartStr = "";
    private String timeEndStr = "";
    private Calendar nowDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        Toast.makeText(TimePickerActivity.this, "请选择开始时间！", Toast.LENGTH_SHORT).show();
        setUpView();
        setUpLisenter();
    }

    private void setUpLisenter() {
        mTitleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                TimePickerActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {
            }
        });
    }

    private void setUpView() {
        mTvStartTime = (TextView) findViewById(R.id.tv_start_time);
        mTvEndTime = (TextView) findViewById(R.id.tv_end_time);

        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.initTitleBarInfo("选择预约停保时间", R.drawable.arrow_left, -1, "", "");
        //时间控件的配置
        DPTManager.getInstance().initCalendar(new DPCNTheme());

        DatePicker picker = (DatePicker) findViewById(R.id.date_picker);
        picker.setDate(nowDate.get(Calendar.YEAR), nowDate.get(Calendar.MONTH));
        picker.setMode(DPMode.SINGLE);
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {

                switch (validStartTime(date)) {
                    case -100:
                        Toast.makeText(TimePickerActivity.this, "开始时间不能与今天的相隔超过一年！", Toast.LENGTH_SHORT).show();
                        return;
                    case -1:
                        Toast.makeText(TimePickerActivity.this, "开始时间不能从过去开始！", Toast.LENGTH_SHORT).show();
                        return;
                }
                if (count == 0) {
                    timeStartStr = date;
                    mTvStartTime.setText(date);
                } else {
                    timeEndStr = date;
                    mTvEndTime.setText(date);
                }
                count++;
                if (count < 2) {
                    Toast.makeText(TimePickerActivity.this, "请再选择结束时间！", Toast.LENGTH_SHORT).show();
                    return;
                }
                intervalDays =   daysBetween(timeStartStr,timeEndStr);
                switch (validEndTime(timeStartStr, timeEndStr)) {
                    case -100:
                        Toast.makeText(TimePickerActivity.this, "时间间隔不能超过一年！", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(TimePickerActivity.this, "时间间隔不能为0！", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(TimePickerActivity.this, "时间间隔不能为负！", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        btnConfirmTime = (Button) findViewById(R.id.btn_confirm_picker_time);
        btnConfirmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusEvent newEvent = new BusEvent(BusEvent.MSG_INT_TIME);
                newEvent.setStart_time(timeStartStr);
                newEvent.setEnd_time(timeEndStr);
                newEvent.setInterval_time(intervalDays + "天");
                EventBus.getDefault().post(newEvent);
                TimePickerActivity.this.finish();
            }
        });
        btnCancelTime = (Button) findViewById(R.id.btn_cancel_picker_time);
        btnCancelTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                timeEndStr = "";
                mTvStartTime.setText("");
                mTvEndTime.setText("");
                timeStartStr = "";
                nowDate = Calendar.getInstance();
            }
        });
    }

    /**
     * @param startTimeStr
     * @param endTimeStr
     * @return -1
     */
    public int validEndTime(String startTimeStr, String endTimeStr) {
        String startTimeArray[] = startTimeStr.split("-");
        String endTimeArray[] = endTimeStr.split("-");

        int start_y = Integer.parseInt(startTimeArray[0]);
        int start_m = Integer.parseInt(startTimeArray[1]);
        int start_d = Integer.parseInt(startTimeArray[2]);

        int end_y = Integer.parseInt(endTimeArray[0]);
        int end_m = Integer.parseInt(endTimeArray[1]);
        int end_d = Integer.parseInt(endTimeArray[2]);

        if (end_y == start_y) {
            if (end_m == start_m) {
                if (end_d == start_d) {
                    return 0;//时间相同
                } else if (end_d > start_d) {
                    return 1;
                } else {
                    return -1;
                }
            } else if (end_m > start_m) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return -100;//时间间隔不能超过一年
        }
    }

    public int validStartTime(String startTime) {
        String startTimeArray[] = startTime.split("-");

        int start_y = Integer.parseInt(startTimeArray[0]);
        int start_m = Integer.parseInt(startTimeArray[1]);
        int start_d = Integer.parseInt(startTimeArray[2]);

        int now_y = nowDate.get(Calendar.YEAR);
        int now_m = nowDate.get(Calendar.MONTH)+1;
        int now_d = nowDate.get(Calendar.DATE);

        if (start_y == now_y) {
            if (start_m == now_m) {
                if (start_d == now_d) {
                    return 0;//时间相同
                } else if (now_d > start_d) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (now_m > start_m) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return -100;//时间间隔不能超过一年
        }
    }

    /**
     *字符串的日期格式的计算
     */
    public static int daysBetween(String smdate,String bdate)  {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(smdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time1 = cal.getTimeInMillis();
        try {
            cal.setTime(sdf.parse(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }


}
