package com.jsb.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.widget.DatePicker.cons.DPMode;
import com.jsb.widget.DatePicker.views.DatePicker;
import com.jsb.widget.TitleBar;

public class TimePickerActivity extends Activity {

    private TitleBar mTitleBar;
    private TextView mTvStartTime;
    private TextView mTvEndTime,mTvEndTimeDes;

    private boolean timePickedflag = false;
    private String  timeFirstStr = "";
    private String  timeEndStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
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
        mTvStartTime = (TextView) findViewById(R.id.tv_pic_start_time);

        mTvEndTime = (TextView) findViewById(R.id.tv_end_time);
        mTvEndTimeDes = (TextView) findViewById(R.id.tv_end_time_des);
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.initTitleBarInfo("选择预约停保时间", R.drawable.back_arrow, -1, "", "");
        //时间控件的配置
        DatePicker picker = (DatePicker) findViewById(R.id.date_picker);
        picker.setDate(2015, 7);
        picker.setMode(DPMode.SINGLE);
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {

                if(!timePickedflag)
                {
                    timeFirstStr = date;
                    timePickedflag = true;
                    mTvStartTime.setText(date);
                    mTvEndTimeDes.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(TimePickerActivity.this,"开始时间："+timeFirstStr+"\ndate:"+date,Toast.LENGTH_LONG).show();
                    switch (validTime(timeFirstStr,date))
                    {
                        case 0:
                            Toast.makeText(TimePickerActivity.this,"0:结束>开始",Toast.LENGTH_SHORT).show();
                            mTvEndTime.setText(date);
                            TimePickerActivity.this.finish();
                            break;
                        case -1:
                            Toast.makeText(TimePickerActivity.this,"-1:结束《开始",Toast.LENGTH_SHORT).show();
                            break;
                        case -2:
                            Toast.makeText(TimePickerActivity.this,"-2:结束《开始",Toast.LENGTH_SHORT).show();
                            break;
                        case -3:
                            Toast.makeText(TimePickerActivity.this,"-3:结束《开始",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
    }

    /**
     * @param startTimeStr
     * @param endTimeStr
     * @return 0 表示合法；-1表示结束时间比开始时间还要早；-2表示结束时间等于开始时间;-3表示结束时间过大
     */
    public int validTime(String startTimeStr, String endTimeStr) {
        String startTimeArray[] = startTimeStr.split("-");
        String endTimeArray[] = endTimeStr.split("-");
        Integer year_s = Integer.valueOf(startTimeArray[0]);
        Integer year_e = Integer.valueOf(endTimeArray[0]);

        Integer m_s = Integer.valueOf(startTimeArray[1]);
        Integer m_e = Integer.valueOf(endTimeArray[1]);

        Integer d_s = Integer.valueOf(startTimeArray[2]);
        Integer d_e = Integer.valueOf(endTimeArray[2]);

        if (year_e == year_s) {
            if (m_e == m_s) {
                if (d_e == d_s) {
                    return -2;
                } else if (d_e < d_s) {
                    return -1;
                } else {
                    return 0;
                }
            } else if (m_e < m_s) {
                return -1;
            } else {
                return 0;
            }
        } else if (year_e > year_s) {
            return -3;
        } else return -1;
    }
}
