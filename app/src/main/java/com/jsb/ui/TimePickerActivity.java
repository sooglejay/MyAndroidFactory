package com.jsb.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.constant.PreferenceConstant;
import com.jsb.event.BusEvent;
import com.jsb.util.PreferenceUtil;
import com.jsb.widget.TimePicker.MyCalendar;
import com.jsb.widget.TitleBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 首页-选择预约停保-时间选择器
 */
public class TimePickerActivity extends BaseActivity implements MyCalendar.OnDaySelectListener {
    private TitleBar mTitleBar;


    LinearLayout dateLayout;
    MyCalendar myCalendar;
    Date date;
    String nowdayString;
    long milliSecondsInADay = 1000 * 24L * 60L * 60L;//一天的毫秒数
    SimpleDateFormat simpleDateFormat, sd_year, sd_day;

    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");//日期格式化

    private String inday, outday, startDayFromSharedXml, endDayFromSharedXml;


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
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.initTitleBarInfo("选择开始结束时间", R.drawable.arrow_left, -1, "", "");

        initCalenderView();
    }

    private void initCalenderView() {
         //本地保存
        startDayFromSharedXml = PreferenceUtil.load(this, PreferenceConstant.TimePickerDateStart, "");
        endDayFromSharedXml = PreferenceUtil.load(this, PreferenceConstant.TimePickerDateEnd, "");


        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nowdayString = simpleDateFormat.format(new Date());
        sd_year = new SimpleDateFormat("yyyy");
        sd_day = new SimpleDateFormat("dd");
        dateLayout = (LinearLayout) findViewById(R.id.layout_calender);

        List<String> listDate = getDateList();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < listDate.size(); i++) {
            myCalendar = new MyCalendar(this);
            myCalendar.setLayoutParams(params);
            Date date = null;
            try {
                date = simpleDateFormat.parse(listDate.get(i));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (!"".equals(startDayFromSharedXml)) {
                myCalendar.setInDay(startDayFromSharedXml);
            }
            if (!"".equals(endDayFromSharedXml)) {
                myCalendar.setOutDay(endDayFromSharedXml);
            }
            myCalendar.setTheDay(date);
            myCalendar.setOnDaySelectListener(this);
            dateLayout.addView(myCalendar);
        }
    }

    @Override
    public void onDaySelectListener(View viewSelected, String dateSelected) {
        //若日历日期小于当前日期，或日历日期-当前日期超过三个月，则不能点击
        try {
            if (simpleDateFormat.parse(dateSelected).getTime() < simpleDateFormat.parse(nowdayString).getTime()) {
                Toast.makeText(TimePickerActivity.this, "日期无效", Toast.LENGTH_SHORT).show();
                return;
            }
//            long dayxc = (simpleDateFormat.parse(dateSelected).getTime() - simpleDateFormat.parse(nowdayString).getTime()) / milliSecondsInADay;
//            if (dayxc > 90) {
//                Toast.makeText(TimePickerActivity.this, "日期无效", Toast.LENGTH_SHORT).show();
//                return;
//            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //若以前已经选择了日期，则在进入日历后会显示以选择的日期，该部分作用则是重新点击日历时，清空以前选择的数据（包括背景图案）
        if (!"".equals(startDayFromSharedXml)) {
            myCalendar.inView.setBackgroundColor(Color.WHITE);
            ((TextView) myCalendar.inView.findViewById(R.id.tv_up)).setTextColor(getResources().getColor(R.color.tv_black_color_level_1));
            ((TextView) myCalendar.inView.findViewById(R.id.tv_bottom)).setText("");
        }
        if (!"".equals(endDayFromSharedXml)) {
            myCalendar.outView.setBackgroundColor(Color.WHITE);
            ((TextView) myCalendar.outView.findViewById(R.id.tv_up)).setTextColor(getResources().getColor(R.color.tv_black_color_level_1));
            ((TextView) myCalendar.outView.findViewById(R.id.tv_bottom)).setText("");
        }

        String dayStringInDateSelected = dateSelected.split("-")[2];
        if (Integer.parseInt(dayStringInDateSelected) < 10) {
            dayStringInDateSelected = dateSelected.split("-")[2].replace("0", "");
        }
        TextView tv_up = (TextView) viewSelected.findViewById(R.id.tv_up);
        TextView tv_bottom = (TextView) viewSelected.findViewById(R.id.tv_bottom);

        //设置选中的时间的背景色
        viewSelected.setBackgroundColor(getResources().getColor(R.color.base_color));
        tv_up.setTextColor(Color.WHITE);

        //第一次点击gridView
        if (null == inday || inday.equals("")) {
            tv_up.setText(dayStringInDateSelected);
            tv_bottom.setText("开始");
            inday = dateSelected;
        }

        //非第一次点击
        else {

            if (inday.equals(dateSelected)) {
                tv_up.setText(dayStringInDateSelected);
                tv_up.setTextColor(getResources().getColor(R.color.tv_black_color_level_1));//如果同一个View连续点击两次，就取消选中状态
                viewSelected.setBackgroundColor(Color.WHITE);
                tv_bottom.setText("");
                inday = "";
            } else {
                try {
                    if (simpleDateFormat.parse(dateSelected).getTime() < simpleDateFormat.parse(inday).getTime()) {
                        viewSelected.setBackgroundColor(Color.WHITE);
                        tv_up.setTextColor(getResources().getColor(R.color.tv_black_color_level_1));
                        Toast.makeText(TimePickerActivity.this, "离开日期不能小于入住日期", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tv_up.setText(dayStringInDateSelected);
                tv_bottom.setText("结束");
                outday = dateSelected;
                PreferenceUtil.save(this, PreferenceConstant.TimePickerDateStart, inday);
                PreferenceUtil.save(this, PreferenceConstant.TimePickerDateEnd, outday);

                long dayxc = 0;
                try {
                     dayxc = (dateFormat2.parse(outday).getTime() - dateFormat2.parse(inday).getTime()) / milliSecondsInADay;
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                BusEvent intEvent = new BusEvent(BusEvent.MSG_INT_TIME);
                intEvent.setStart_time(inday);
                intEvent.setEnd_time(outday);
                intEvent.setInterval_time(dayxc + "天");
                EventBus.getDefault().post(intEvent);
                finish();
            }
        }
    }

    //根据当前日期，向后数三个月（若当前day不是1号，为满足至少90天，则需要向后数4个月）
    @SuppressLint("SimpleDateFormat")
    public List<String> getDateList() {
        List<String> list = new ArrayList<String>();
        Date date = new Date();
        int nowMon = date.getMonth() + 1;
        String yyyy = sd_year.format(date);
        String dd = sd_day.format(date);
        if (nowMon == 9) {
            list.add(simpleDateFormat.format(date));
            list.add(yyyy + "-10-" + dd);
            list.add(yyyy + "-11-" + dd);
            if (!dd.equals("01")) {
                list.add(yyyy + "-12-" + dd);
            }
        } else if (nowMon == 10) {
            list.add(yyyy + "-10-" + dd);
            list.add(yyyy + "-11-" + dd);
            list.add(yyyy + "-12-" + dd);
            if (!dd.equals("01")) {
                list.add((Integer.parseInt(yyyy) + 1) + "-01-" + dd);
            }
        } else if (nowMon == 11) {
            list.add(yyyy + "-11-" + dd);
            list.add(yyyy + "-12-" + dd);
            list.add((Integer.parseInt(yyyy) + 1) + "-01-" + dd);
            if (!dd.equals("01")) {
                list.add((Integer.parseInt(yyyy) + 1) + "-02-" + dd);
            }
        } else if (nowMon == 12) {
            list.add(yyyy + "-12-" + dd);
            list.add((Integer.parseInt(yyyy) + 1) + "-01-" + dd);
            list.add((Integer.parseInt(yyyy) + 1) + "-02-" + dd);
            if (!dd.equals("01")) {
                list.add((Integer.parseInt(yyyy) + 1) + "-03-" + dd);
            }
        } else {
            list.add(yyyy + "-" + getMon(nowMon) + "-" + dd);
            list.add(yyyy + "-" + getMon((nowMon + 1)) + "-" + dd);
            list.add(yyyy + "-" + getMon((nowMon + 2)) + "-" + dd);
            if (!dd.equals("01")) {
                list.add(yyyy + "-" + getMon((nowMon + 3)) + "-" + dd);
            }
        }
        return list;
    }

    public String getMon(int mon) {
        String month = "";
        if (mon < 10) {
            month = "0" + mon;
        } else {
            month = "" + mon;
        }
        return month;
    }

}
