package com.jsb.ui.stopinsurance;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.constant.StringConstant;
import com.jsb.ui.BaseActivity;
import com.jsb.util.SpannableStringUtil;
import com.jsb.widget.TitleBar;
import com.jsb.widget.timepicker.CalenderAdapter;
import com.jsb.widget.timepicker.FatherBean;
import com.jsb.widget.timepicker.IncomeCalenderAdapter;
import com.jsb.widget.timepicker.SonBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by JammyQtheLab on 2015/11/24.
 */
public class InComeDetailActivity extends BaseActivity {
    private List<Object> mDatas = new ArrayList<>();
    private IncomeCalenderAdapter mAdapter;
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);//全局日历对象

    private SimpleDateFormat dateFormat_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");//日期格式化
    private String todayString_yyyy_m_d = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    private Activity activity;
    private SpannableString unitSpanString;


    private TitleBar titleBar;
    private TextView tvAccumulatedAmount;
    private TextView tvAccumulatedText;
    private ImageView ivAccumulatedAmount;
    private TextView tvDailyAmount;
    private TextView tvDailyText;
    private ImageView ivDailyAmount;
    private TextView tvAccumulatedDays;
    private TextView tvAccumulatedDaysText;
    private ImageView ivAccumulatedDays;
    private ListView listItem;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-24 03:09:12 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        tvAccumulatedAmount = (TextView) findViewById(R.id.tv_accumulated_amount);
        tvAccumulatedText = (TextView) findViewById(R.id.tv_accumulated_text);
        ivAccumulatedAmount = (ImageView) findViewById(R.id.iv_accumulated_amount);
        tvDailyAmount = (TextView) findViewById(R.id.tv_daily_amount);
        tvDailyText = (TextView) findViewById(R.id.tv_daily_text);
        ivDailyAmount = (ImageView) findViewById(R.id.iv_daily_amount);
        tvAccumulatedDays = (TextView) findViewById(R.id.tv_accumulated_days);
        tvAccumulatedDaysText = (TextView) findViewById(R.id.tv_accumulated_days_text);
        ivAccumulatedDays = (ImageView) findViewById(R.id.iv_accumulated_days);
        listItem = (ListView) findViewById(R.id.list_item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);
        activity = this;
        //人民币单位的字符
        unitSpanString = SpannableStringUtil.getSpannableString(activity, "¥", 30);//单位
        findViews();
        setUpView();
        setUpLisenter();
    }

    private void setUpLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                activity.finish();
            }

            @Override
            public void onRightButtonClick(View v) {
            }
        });
    }

    private void setUpView() {

        titleBar.initTitleBarInfo("收益详情", R.drawable.arrow_left, -1, "", "");
        setmAdapterDatas(mDatas);
        mAdapter = new IncomeCalenderAdapter(this, mDatas);
        listItem.setAdapter(mAdapter);

        tvDailyAmount.setText(unitSpanString);
        tvDailyAmount.append("867.08");

        tvAccumulatedAmount.setText(unitSpanString);
        tvAccumulatedAmount.append("867.08");

        tvDailyAmount.setText(unitSpanString);
        tvDailyAmount.append("17.08");

        tvAccumulatedDays.setText(unitSpanString);
        tvAccumulatedDays.append("91");

    }

    /**
     * 设置日历需要的数据集
     *
     * @param listData
     */
    public void setmAdapterDatas(List<Object> listData) {
        int thisMonthNumber = calendar.get(Calendar.MONTH) + 1;
        int thisYearNumber = calendar.get(Calendar.YEAR);
        for (int i = 0; i < 2; i++) {
            if (thisMonthNumber + i <= 12) {//在一年以内
                listData.add(thisYearNumber + "年" + (thisMonthNumber + i) + "月");
                FatherBean calenderListBean = new FatherBean();
                List<SonBean> daysList = new ArrayList<>();//当月的天对象

                calendar.set(Calendar.DATE, 1);//cal设置当前day为当前月第一天
                int daysInTheMonth = calendar.getActualMaximum(Calendar.DATE);
                int firstDay_NumberOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                for (int j = 0; j < firstDay_NumberOfWeek; j++) {
                    SonBean dayBean = new SonBean();
                    dayBean.setDateStr("");
                    daysList.add(dayBean);

                }
                for (int k = 1; k <= daysInTheMonth; k++) {
                    String dayS = k + "";
                    if (k < 10) {
                        dayS = "0" + k;
                    }
                    SonBean dayBean = new SonBean();
                    dayBean.setDateStr(thisYearNumber + "-" + (thisMonthNumber + i) + "-" + dayS);
                    //设置 时间状态
                    dayBean.setStatus(k % 4 == 0 ? IncomeCalenderAdapter.CHOOSE : IncomeCalenderAdapter.OTHERS);

                    dayBean.setDayNumberString(k + "");
                    daysList.add(dayBean);
                }
                calenderListBean.setDaysList(daysList);
                listData.add(calenderListBean);
                calendar.add(Calendar.MONTH, 1);
            } else {

            }
        }

    }


    /**
     * 生成 status ，标识是开始0 、结束1  还是 在它们之间2
     *
     * @param startDateString
     * @param endDateString
     * @param dateString
     * @return
     */
    public int genStatus(String startDateString, String endDateString, String dateString) {
        try {
            if (dateString.equals(startDateString)) {
                return CalenderAdapter.START;

            } else if (dateString.equals(endDateString)) {
                return CalenderAdapter.END;
            } else if (dateFormat_yyyy_MM_dd.parse(dateString).getTime() > dateFormat_yyyy_MM_dd.parse(startDateString).getTime()
                    && dateFormat_yyyy_MM_dd.parse(dateString).getTime() < dateFormat_yyyy_MM_dd.parse(endDateString).getTime()) {
                return CalenderAdapter.BETWEEN;
            } else return CalenderAdapter.OTHERS;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return CalenderAdapter.OTHERS;
    }
}
