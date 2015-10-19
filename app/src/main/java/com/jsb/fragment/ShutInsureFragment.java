package com.jsb.fragment;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.jsb.constant.StringConstant;
import com.jsb.event.BusEvent;
import com.jsb.third_party.hookedonplay.decoviewlib.DecoView;
import com.jsb.third_party.hookedonplay.decoviewlib.charts.SeriesItem;
import com.jsb.third_party.hookedonplay.decoviewlib.events.DecoEvent;
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
public class ShutInsureFragment extends DecoViewBaseFragment {
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

    
    //圆形动画
    private String mProgress;//  进度条
    private int mBigCircleSeriesIndex;//外围大环序列号
    private int mSmallCircleSeriesIndex;//内 部小圆环序列号
    private TextView tv_follow_decoView;//TextView 显示进度



    private RightControlFragment rightControlFragment;//注意清除掉 mPasswordString 才能公用

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
                rightControlFragment.showDialog(ShutInsureFragment.this.getActivity(), ShutInsureFragment.this.getFragmentManager(), RightControlFragment.Dialog_RightControl);
                if (isChecked) {

                } else {

                }
            }

        });




        // 滑动按钮-选择预约停保的时间
        dateSwitchTabView = (Switch) view.findViewById(R.id.date_switch_tab_view);
        dateSwitchTabView.setChecked(false);//默认是关闭
        dateSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rightControlFragment.showDialog(ShutInsureFragment.this.getActivity(), ShutInsureFragment.this.getFragmentManager(), RightControlFragment.Dialog_RightControl);
                if (isChecked) {
                    Toast.makeText(getActivity(), "开", Toast.LENGTH_SHORT).show();
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

        SeriesItem series1Item = new SeriesItem.Builder(COLOR_BIG_CIRCLE)
                .setRange(0, 1000f, 0)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(6))
                .setCapRounded(true)
//                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, COLOR_EDGE, 0.3f))
                .setShowPointWhenEmpty(true)
                .build();
        mBigCircleSeriesIndex = decoView.addSeries(series1Item);



        float inset =  getDimension(8);
        SeriesItem smallCircle = new SeriesItem.Builder(COLOR_SMALL_CIRCLE)
                .setRange(0, 1000f, 0)
                .setInitialVisibility(false)
                .setInset(new PointF(inset, inset))
                .setLineWidth(getDimension(2))
                .setCapRounded(true)
                .setShowPointWhenEmpty(true)
                .build();
        mBigCircleSeriesIndex = decoView.addSeries(series1Item);
        mSmallCircleSeriesIndex = decoView.addSeries(smallCircle);

        addFitListener(series1Item, tv_follow_decoView);
    }


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
        addAnimation(arcView, mBigCircleSeriesIndex,869.2f, 500, null, -1, "$ %.2f", COLOR_BIG_CIRCLE, false);
        addAnimation(arcView, mSmallCircleSeriesIndex, 1000f, 100, null, -1, "$ %.2f", COLOR_SMALL_CIRCLE, false);

      }

    /**
     * 圆形控件动画的 跟随监听器
     *@param seriesItem
     * @param view
     */
    private void addFitListener(@NonNull final SeriesItem seriesItem, @NonNull final TextView view) {
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {

            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (mProgress != null) {
                    if (mProgress.contains("%%")) {
                        view.setText(String.format(mProgress, (1.0f - (currentPosition / seriesItem.getMaxValue())) * 1000f));
                    } else {
                        view.setText(String.format(mProgress, currentPosition));
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
