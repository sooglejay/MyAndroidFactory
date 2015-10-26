package com.jsb.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.SpinnerDropDownAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.constant.StringConstant;
import com.jsb.event.BusEvent;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.PauseData;
import com.jsb.ui.MyModifyPasswordActivity;
import com.jsb.util.DiditUtil;
import com.jsb.util.SpannableStringUtil;
import com.jsb.widget.DecoView.decoviewlib.DecoView;
import com.jsb.widget.DecoView.decoviewlib.charts.SeriesItem;
import com.jsb.widget.DecoView.decoviewlib.events.DecoEvent;
import com.jsb.ui.BrowserActivity;
import com.jsb.ui.PullMoneyActivity;
import com.jsb.ui.TimePickerActivity;
import com.jsb.util.PreferenceUtil;
import com.jsb.widget.TitleBar;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 停保险-主框架-tab1
 */
public class ShutInsureFragment extends DecoViewBaseFragment {
    public static final int ACTION_CHOOSE_TIME = 1000;

    private TitleBar titleBar;
    private TextView tvPullMoney;
    private TextView layout_rule;
    private Spinner week_number_spinner;
    private Spinner car_number_spinner;

    private List<String> mCarNumbersStringList = new ArrayList<>();
    private List<String> mWeekNumbersStringList = new ArrayList<>();
    private SpinnerDropDownAdapter mCarNumbersListAdapter;
    private SpinnerDropDownAdapter mWeekNumbersListAdapter;

    private List<PauseData> mPauseDataList = new ArrayList<>();
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_date_interval;
    private Switch weekSwitchTabView;
    private Switch dateSwitchTabView;
    private LinearLayout datePickerLayout;


    private float maxNumber = 0;//动画的 数字最大值
    private float realNumber = 0;//动画的 数字最大值


    private String startTimeStr;
    private String endTimeStr;
    private String timeIntvalStr;


    private String todayString_yyyy_m_d = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    //圆形动画
    private String mProgress;//  进度条
    private int mBigCircleSeriesIndex;//外围大环序列号
    private TextView tv_follow_decoView;//TextView 显示进度

    private SpannableString unitSpanString;


    private TextView tv_yytb;//
    private TextView tv_xxtb;//



    private LinearLayout layout_week_number_spinner;//
    private LinearLayout layout_car_number_spinner;//




    private TextView tv_pausePrice;//
    private TextView tv_usefulPauseFee;//



    private String pwdString;//提现，开启/关闭滑动按钮等重要操作需要 输入密码

    private DialogFragmentCreater dialogFragmentController;//注意清除掉 mPasswordString 才能公用

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        dialogFragmentController = new DialogFragmentCreater(getActivity(), getFragmentManager());//涉及到权限操作时，需要临时输入密码并验证
        unitSpanString = SpannableStringUtil.getSpannableString(getActivity(), "¥", 40);//单位
        pwdString = PreferenceUtil.load(ShutInsureFragment.this.getActivity(), PreferenceConstant.pwd, "");//提现等操作密码

        tv_yytb = (TextView) view.findViewById(R.id.tv_yytb);
        tv_xxtb = (TextView) view.findViewById(R.id.tv_xxtb);

        tv_usefulPauseFee = (TextView) view.findViewById(R.id.tv_usefulPauseFee);
        tv_pausePrice = (TextView) view.findViewById(R.id.tv_pausePrice);



        //圆形动画 文本跟随器
        tv_follow_decoView = (TextView) view.findViewById(R.id.tv_follow_decoView);

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

                if (TextUtils.isEmpty(pwdString)) {
                    dialogFragmentController.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                MyModifyPasswordActivity.startModifyPasswordActivity(getActivity(), StringConstant.setPassword);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("您还没有设置密码，是否现在去设置");
                            }

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {
                    getActivity().startActivity(new Intent(getActivity(), PullMoneyActivity.class));
                }
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


        //预约停保  的textView，显示开始和结束的时间文本和时间间隔
        startTimeStr = PreferenceUtil.load(this.getActivity(), PreferenceConstant.TimePickerDateStart, "");
        endTimeStr = PreferenceUtil.load(this.getActivity(), PreferenceConstant.TimePickerDateEnd, "");
        timeIntvalStr = PreferenceUtil.load(this.getActivity(), PreferenceConstant.TimePickerDateInterval, "");

        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date);
        tv_date_interval = (TextView) view.findViewById(R.id.tv_date_interval);

        tv_start_date.setText(startTimeStr);
        tv_end_date.setText(endTimeStr);
        tv_date_interval.setText(timeIntvalStr);


        //若果没有值
        if (TextUtils.isEmpty(startTimeStr)) {
            tv_start_date.setText(todayString_yyyy_m_d + "");
            tv_end_date.setText(todayString_yyyy_m_d + "");
            tv_date_interval.setText("共0天");
        }


        //选择预约停保时间
        datePickerLayout = (LinearLayout) view.findViewById(R.id.layout_date_picker);
        datePickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivityForResult(new Intent(getActivity(), TimePickerActivity.class), ACTION_CHOOSE_TIME);
            }
        });


        //车牌号Spinner 点击事件由父View 触发
        layout_car_number_spinner = (LinearLayout) view.findViewById(R.id.layout_car_number_spinner);
        layout_car_number_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car_number_spinner.performClick();
            }
        });

        //车牌号Spinner
        car_number_spinner = (Spinner) view.findViewById(R.id.car_number_spinner);
        mCarNumbersListAdapter = new SpinnerDropDownAdapter(getActivity(), mCarNumbersStringList);
        car_number_spinner.setAdapter(mCarNumbersListAdapter);


        //周Spinner 点击事件由父View 触发
        layout_week_number_spinner = (LinearLayout) view.findViewById(R.id.layout_week_number_spinner);
        layout_week_number_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week_number_spinner.performClick();
            }
        });
        //周Spinner
        week_number_spinner = (Spinner) view.findViewById(R.id.week_number_spinner);
        mWeekNumbersStringList = new ArrayList<>( Arrays.asList(getResources().getStringArray(R.array.weekArray)));
        mWeekNumbersListAdapter = new SpinnerDropDownAdapter(getActivity(), mWeekNumbersStringList);
        week_number_spinner.setAdapter(mWeekNumbersListAdapter);
        week_number_spinner.setSelection(6);

        //限行停保 滑动按钮
        weekSwitchTabView = (Switch) view.findViewById(R.id.week_switch_tab_view);
        weekSwitchTabView.setChecked(false);
        tv_xxtb.setAlpha(0.5f);
        layout_week_number_spinner.setAlpha(0.5f);
        layout_week_number_spinner.setClickable(false);
        weekSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (TextUtils.isEmpty(pwdString)) {
                    dialogFragmentController.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                MyModifyPasswordActivity.startModifyPasswordActivity(getActivity(), StringConstant.setPassword);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("您还没有设置密码，是否现在去设置");
                            }

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {
                    dialogFragmentController.showDialog(ShutInsureFragment.this.getActivity(), DialogFragmentCreater.DialogShowInputPasswordDialog);
                    if (isChecked) {
                        tv_xxtb.setAlpha(1);//如果滑动开关 选中 要调整 UI 的透明度 和可点击
                        layout_week_number_spinner.setAlpha(1);
                        layout_week_number_spinner.setClickable(true);
                        layout_week_number_spinner.setEnabled(true);
                    } else {
                        tv_xxtb.setAlpha(0.5f);
                        layout_week_number_spinner.setAlpha(0.5f);
                        layout_week_number_spinner.setClickable(false);
                        layout_week_number_spinner.setEnabled(false);
                    }
                }
            }

        });

        // 滑动按钮-选择预约停保的时间
        dateSwitchTabView = (Switch) view.findViewById(R.id.date_switch_tab_view);
        dateSwitchTabView.setChecked(false);//默认是关闭
        tv_yytb.setAlpha(0.5f);
        datePickerLayout.setAlpha(0.5f);
        datePickerLayout.setClickable(false);
        dateSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (false) {
                    dialogFragmentController.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                MyModifyPasswordActivity.startModifyPasswordActivity(getActivity(), StringConstant.setPassword);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("您还没有设置密码，是否现在去设置");
                            }

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {
                    dialogFragmentController.showDialog(ShutInsureFragment.this.getActivity(), DialogFragmentCreater.DialogShowInputPasswordDialog);

                    if (isChecked) {
                        tv_yytb.setAlpha(1);
                        datePickerLayout.setAlpha(1);
                        datePickerLayout.setClickable(true);
                        datePickerLayout.setEnabled(true);
                    } else {
                        tv_yytb.setAlpha(0.5f);
                        datePickerLayout.setAlpha(0.5f);
                        datePickerLayout.setClickable(false);
                        datePickerLayout.setEnabled(false);
                    }
                }
            }

        });


        //获取停保信息
        getPauseInfo(getActivity(), PreferenceUtil.load(getActivity(), PreferenceConstant.userid, -1));

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected void createTracks() {
        setDemoFinished(false);
        final View view = getView();
        final DecoView decoView = getDecoView();
        if (view == null || decoView == null) {
            return;
        }

//        view.setBackgroundColor(Color.argb(255, 196, 196, 128));

        decoView.executeReset();
        decoView.deleteAll();

        final float mSeriesMax = 100f;

        SeriesItem seriesBack1Item = new SeriesItem.Builder(COLOR_BACK)
                .setRange(0, mSeriesMax, mSeriesMax)
                .setLineWidth(getDimension(6))
                .build();
        decoView.addSeries(seriesBack1Item);

        float inset = getDimension(10);
        SeriesItem smallCircle = new SeriesItem.Builder(COLOR_SMALL_CIRCLE)
                .setRange(0, mSeriesMax, mSeriesMax)
                .setInset(new PointF(inset, inset))
                .setLineWidth(getDimension(2))
                .setCapRounded(true)
                .setShowPointWhenEmpty(true)
                .build();
        decoView.addSeries(smallCircle);

        SeriesItem series1Item = new SeriesItem.Builder(COLOR_BIG_CIRCLE)
                .setRange(0, maxNumber, 0)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(6))
                .setCapRounded(true)
//                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, COLOR_EDGE, 0.3f))
                .setShowPointWhenEmpty(true)
                .build();
        mBigCircleSeriesIndex = decoView.addSeries(series1Item);
        addFitListener(series1Item, tv_follow_decoView);
    }

    /**
     * 圆形滚动动画
     *
     * @param arcView
     * @param series
     * @param moveTo
     * @param delay
     * @param imageView
     * @param imageId
     * @param format
     * @param color
     * @param restart
     */
    private void addAnimation(final DecoView arcView,
                              int series, float moveTo, int delay,
                              final ImageView imageView, final int imageId,
                              final String format, final int color, final boolean restart) {

        DecoEvent.ExecuteEventListener listener = new DecoEvent.ExecuteEventListener() {
            @Override
            public void onEventStart(DecoEvent event) {
                mProgress = format;
            }

            @Override
            public void onEventEnd(DecoEvent event) {
                if (restart) {
                    setupEvents();
                }
            }
        };

        arcView.addEvent(new DecoEvent.Builder(moveTo)
                .setIndex(series)
                .setDelay(delay)
                .setDuration(5000)
                .setListener(listener)
                .setColor(color)
                .build());
    }


    @Override
    protected void setupEvents() {
        final DecoView arcView = getDecoView();
        final View view = getView();
        if (view == null || arcView == null || arcView.isEmpty()) {
            return;
        }
        addAnimation(arcView, mBigCircleSeriesIndex, realNumber, 500, null, -1, "%.2f", COLOR_BIG_CIRCLE, false);
    }

    /**
     * 圆形控件动画的 跟随监听器
     *
     * @param seriesItem
     * @param view
     */
    private void addFitListener(@NonNull final SeriesItem seriesItem, @NonNull final TextView view) {
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {

            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (mProgress != null) {
                    if (mProgress.contains("%%")) {
                        view.setText(unitSpanString + String.format(mProgress, (1.0f - (currentPosition / seriesItem.getMaxValue())) * 1000f).replace('.', ','));
                    } else {
                        view.setText(unitSpanString + String.format(mProgress, currentPosition).replace('.', ','));
                    }
                }
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });
    }

    /**
     * EventBus 广播
     *
     * @param event
     */
    public void onEventMainThread(BusEvent event) {
        switch (event.getMsg()) {
            case BusEvent.MSG_INT_TIME:
                startTimeStr = event.getStart_time();
                endTimeStr = event.getEnd_time();
                timeIntvalStr = event.getInterval_time();

                tv_start_date.setText(startTimeStr);
                tv_end_date.setText(endTimeStr);
                tv_date_interval.setText(timeIntvalStr);

                //若果没有值，就不显示
                if (TextUtils.isEmpty(startTimeStr)) {
                    tv_start_date.setText(todayString_yyyy_m_d + "");
                    tv_end_date.setText(todayString_yyyy_m_d + "");
                    tv_date_interval.setText("共0天");
                }
                PreferenceUtil.save(this.getActivity(), PreferenceConstant.TimePickerDateStart, startTimeStr);
                PreferenceUtil.save(this.getActivity(), PreferenceConstant.TimePickerDateEnd, endTimeStr);
                PreferenceUtil.save(this.getActivity(), PreferenceConstant.TimePickerDateInterval, timeIntvalStr);
                break;
            default:
                break;
        }
    }


    private void getPauseInfo(final Context context, int userid) {
        UserRetrofitUtil.getPauseInfo(context, 1, new NetCallback<NetWorkResultBean<List<PauseData>>>(context) {
            @Override
            public void onFailure(RetrofitError error) {
            }

            @Override
            public void success(NetWorkResultBean<List<PauseData>> objectNetWorkResultBean, Response response) {
                mPauseDataList.clear();
                mPauseDataList.addAll(objectNetWorkResultBean.getData());
                mCarNumbersStringList.clear();
                for (PauseData bean : mPauseDataList) {
                    mCarNumbersStringList.add(bean.getLicenseplate());
                }
                mCarNumbersListAdapter.notifyDataSetChanged();

                if (mPauseDataList!=null&&mPauseDataList.size() > 0) {
                    PauseData bean = mPauseDataList.get(0);
                    refreshData(bean);
                }
            }
        });
    }


    public void refreshData(PauseData bean)
    {
        if(bean.getLimitday()!=null)
        {
            week_number_spinner.setSelection(bean.getLimitday());
        }else {
            week_number_spinner.setSelection(0);
        }
        if(bean.getPausePrice()!=null)
        {
            tv_pausePrice.setText("¥"+bean.getPausePrice()+"");
        }

        if(bean.getUsefulPauseFee()!=null)
        {
            tv_usefulPauseFee.setText("¥" + bean.getUsefulPauseFee() + "");
        }

        //开始动画
        realNumber = bean.getTotalPauseFee();
        maxNumber  = (float)DiditUtil.getMaxInteger(realNumber);
        createAnimation();
    }
}
