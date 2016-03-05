package com.jiandanbaoxian.ui.stopinsurance;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.ExtraConstants;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.Limitpausetable;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.PauseData;
import com.jiandanbaoxian.model.PauseHistory;
import com.jiandanbaoxian.model.Reservepausetable;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.SpannableStringUtil;
import com.jiandanbaoxian.widget.TitleBar;
import com.jiandanbaoxian.widget.timepicker.CalenderAdapter;
import com.jiandanbaoxian.widget.timepicker.FatherBean;
import com.jiandanbaoxian.adapter.IncomeCalenderAdapter;
import com.jiandanbaoxian.widget.timepicker.SonBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by JammyQtheLab on 2015/11/24.
 */
public class InComeDetailActivity extends BaseActivity {
    private List<Object> mDatas = new ArrayList<>();
    private List<String> mDatasTimeStr = new ArrayList<>();
    private IncomeCalenderAdapter mAdapter;
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);//全局日历对象

    private SimpleDateFormat dateFormat_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");//日期格式化
    private String todayString_yyyy_m_d = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    private int userid = -1;
    private int orderid = -1;

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


    List<Reservepausetable> reservehistory = new ArrayList<>();
    private List<Limitpausetable> limithistory = new ArrayList<>();

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
        userid = PreferenceUtil.load(activity, PreferenceConstant.userid, -1);
        orderid = getIntent().getIntExtra(ExtraConstants.EXTRA_orderid, -1);
        findViews();
        setUpView();
        setUpLisenter();
        getPauseHistory();
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


        mAdapter.setOnDateClickCallBack(new IncomeCalenderAdapter.OnDateClickCallBack() {
            @Override
            public void callBack(View view, String dateString) {
                for (Reservepausetable bean : reservehistory) {
                    if (bean.getReservedays().contains(dateString)) {
                        tvDailyAmount.append(bean.getDayprice() + "");
                        tvAccumulatedAmount.append(bean.getTotalfee() + "");
                        tvAccumulatedDays.append(bean.getDay() + "");
                    }
                }


            }
        });
    }

    private void setUpView() {

        titleBar.initTitleBarInfo("收益详情", R.drawable.arrow_left, -1, "", "");
        setmAdapterDatas(mDatas);
        mAdapter = new IncomeCalenderAdapter(this, mDatas);
        listItem.setAdapter(mAdapter);

        tvDailyAmount.setText(unitSpanString);
        tvDailyAmount.append("");

        tvAccumulatedAmount.setText(unitSpanString);
        tvAccumulatedAmount.append("");


        tvAccumulatedDays.setText(unitSpanString);
        tvAccumulatedDays.append("");

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
                    dayBean.setStatus(IncomeCalenderAdapter.OTHERS);

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

    private void getPauseHistory() {
        if (orderid == -1) {
            tvDailyAmount.append("0");
            tvAccumulatedAmount.append("0");
            tvAccumulatedDays.append("0");
            Toast.makeText(activity, "亲，你还没有购买过保险哦！历史记录为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //只写了预约停保，接口很难实现 效果图的 效果
        UserRetrofitUtil.getPauseHistory(activity, userid, orderid, new NetCallback<NetWorkResultBean<Object>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void success(NetWorkResultBean<Object> pauseHistoryNetWorkResultBean, Response response) {
                if (pauseHistoryNetWorkResultBean != null) {
                    int status = pauseHistoryNetWorkResultBean.getStatus();
                    switch (status) {
                        case HttpsURLConnection.HTTP_OK:
                            if (pauseHistoryNetWorkResultBean.getData() != null) {
                                PauseHistory bean = (PauseHistory) pauseHistoryNetWorkResultBean.getData();

                                reservehistory.clear();
                                limithistory.clear();
                                reservehistory.addAll(bean.getReservehistory());
                                limithistory.addAll(bean.getLimithistory());
                                mDatasTimeStr.clear();
                                String array[];

                                //根据接口，拼凑出 预约过的日期字符串
                                for (Reservepausetable reserve : reservehistory) {
                                    array = reserve.getReservedays().split(",");
                                    for (String str : array) {
                                        mDatasTimeStr.add(str);
                                    }

                                    tvDailyAmount.append(reserve.getDayprice() + "");
                                    tvAccumulatedAmount.append(reserve.getTotalfee() + "");
                                    tvAccumulatedDays.append(reserve.getDay() + "");
                                }
                                //更新Adapter，我不确定能够正确的更新
                                mAdapter.setmReserverDaysStr(mDatasTimeStr);
                            }
                            break;
                        default:
                            Toast.makeText(activity, pauseHistoryNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }
        });
    }
}
