package com.jsb.test;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.third_party.hookedonplay.decoviewlib.DecoView;
import com.jsb.third_party.hookedonplay.decoviewlib.charts.ChartSeries;
import com.jsb.third_party.hookedonplay.decoviewlib.charts.DecoDrawEffect;
import com.jsb.third_party.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.jsb.third_party.hookedonplay.decoviewlib.charts.SeriesItem;
import com.jsb.third_party.hookedonplay.decoviewlib.events.DecoEvent;

/**
 * activity 基类
 */
public class TestDecoViewActivity extends Activity {
    private int mBigCircleIndex;
    private int mSmallCircleIndex;
    private String mProgress;
    private DecoView bigDecoView ;
    private DecoView smallDecoView ;
    private TextView textPause;

    final protected int COLOR = Color.parseColor("#86b38f");
    final protected int COLOR_BLUE = Color.parseColor("#AA1D76D2");
    final protected int COLOR_PINK = Color.parseColor("#AAFF4081");
    final protected int COLOR_YELLOW = Color.parseColor("#AAFFC107");
    final protected int COLOR_GREEN = Color.parseColor("#AA07CC07");
    final protected int COLOR_BACK = Color.parseColor("#22888888");
    final protected int COLOR_NEUTRAL = Color.parseColor("#FF999999");
    protected boolean mUpdateListeners = true;


    //第二个测试，测试两个目的，看未初始化的变量如何被Java处理，第二是测试的主要目的
    private int mBackIndex;
    private int mSeries1Index;
    private int mSeries2Index;
    private int mStyleIndex;
    final protected int COLOR_EDGE = Color.parseColor("#22000000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaaaaaaaaaa_activity_deco_view_test);
        bigDecoView = (DecoView) findViewById(R.id.dynamicArcView);
        smallDecoView = (DecoView) findViewById(R.id.dynamicArcView2);
        textPause = (TextView) findViewById(R.id.textViewPause);


        bigDecoView.executeReset();
        smallDecoView.executeReset();

        bigDecoView.deleteAll();
        smallDecoView.deleteAll();




        //第一个测试






        final float seriesMax = 10f;
        SeriesItem big_cirle_huan = new SeriesItem.Builder(COLOR)
                .setRange(0, seriesMax, 0)
                .setLineWidth(getDimension(6))//大圆环的环直径长度
                .setInterpolator(new LinearInterpolator())

                .build();
        SeriesItem small_cirle_huan = new SeriesItem.Builder(COLOR)
                .setRange(0, seriesMax, 0)
                .setLineWidth(getDimension(2))//小圆环的直径
                .setInterpolator(new LinearInterpolator())
                .build();


        mBigCircleIndex = bigDecoView.addSeries(big_cirle_huan);
        mSmallCircleIndex = smallDecoView.addSeries(small_cirle_huan);


        // 设置动画监听器
        big_cirle_huan.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
//                bigDecoView.getChartSeries(mBingIndex).setPosition(percentComplete < 1.0f ? percentComplete * seriesMax : 0f);
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {
            }
        });


        //回调开始和结束的接口
        final DecoEvent.ExecuteEventListener eventListener = new DecoEvent.ExecuteEventListener() {
            @Override
            public void onEventStart(DecoEvent event) {
                textPause.setText("PAUSE");
            }

            @Override
            public void onEventEnd(DecoEvent event) {
                textPause.setText("");
//                bigDecoView.getChartSeries(mBingIndex).reset();
            }
        };

//        bigDecoView.addEvent(new DecoEvent.Builder(seriesMax)
//                .setIndex(mBigCircleIndex)
//                .setDelay(100)
//                .setDuration(3100)
//                .setListener(eventListener)
//                .build());
//        smallDecoView.addEvent(new DecoEvent.Builder(seriesMax)
//                .setIndex(mSmallCircleIndex)
//                .setDuration(3000)
//                .setListener(eventListener)
//                .build());








        ///第二个测试




















        // 本测试解决了
        // 画圆圈的问题，直接画，没有画的过程动画
        final  float[] mTrackBackWidth = {30f, 60f, 30f, 40f, 30f};
        final  boolean[] mPie = {false, false, false, false, true};
        final  float[] mTrackWidth = {30f, 30f, 30f, 30f, 30f};
         final int[] mTotalAngle = {360, 360, 320, 260, 360};
         final  int[] mRotateAngle = {0, 180, 180, 0, 270};

        bigDecoView.deleteAll();
        bigDecoView.configureAngles(mTotalAngle[mStyleIndex], mRotateAngle[mStyleIndex]);

        SeriesItem arcBackTrack = new SeriesItem.Builder(Color.argb(255, 228, 228, 228))
                .setRange(0, seriesMax, seriesMax)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(mTrackBackWidth[mStyleIndex]))
                .setChartStyle(mPie[mStyleIndex] ? SeriesItem.ChartStyle.STYLE_PIE : SeriesItem.ChartStyle.STYLE_DONUT)
                .build();
        mBackIndex = bigDecoView.addSeries(arcBackTrack);
//        bigDecoView.addEvent(new DecoEvent.Builder(seriesMax)
//                .setIndex(mSmallCircleIndex)
//                .setListener(eventListener)
//                .build());
//








        //测试3  设置文字跟随器   不成功
        final  boolean[] mClockwise = {true, true, true, false, true};
        final  boolean[] mRounded = {true, true, true, true, true};
        float inset = 0;
        if (mTrackBackWidth[mStyleIndex] != mTrackWidth[mStyleIndex]) {
            inset = getDimension((mTrackBackWidth[mStyleIndex] - mTrackWidth[mStyleIndex]) / 2);
        }
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 255, 165, 0))
                .setRange(0, seriesMax, 0)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(mTrackWidth[mStyleIndex]))
                .setInset(new PointF(-inset, -inset))
                .setSpinClockwise(mClockwise[mStyleIndex])
                .setCapRounded(mRounded[mStyleIndex])
                .setChartStyle(mPie[mStyleIndex] ? SeriesItem.ChartStyle.STYLE_PIE : SeriesItem.ChartStyle.STYLE_DONUT)
                .build();

        mSeries1Index = bigDecoView.addSeries(seriesItem1);
        textPause.setText("");
        addProgressListener(seriesItem1, textPause, "%.0f Km");
//        bigDecoView.addEvent(new DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
//                .setIndex(mSeries1Index)
////                .setLinkedViews(linkedViews)
//                .setDuration(2000)
//                .setDelay(1100)
//                .build());
//
//        bigDecoView.addEvent(new DecoEvent.Builder(10).setIndex(mSeries1Index).setDelay(3900).build());
//        bigDecoView.addEvent(new DecoEvent.Builder(22).setIndex(mSeries1Index).setDelay(7000).build());
//







        //测试4 同测试3
//        final TextView textPercent = (TextView) findViewById(R.id.textPercentage);
//        final TextView textToGo = (TextView) findViewById(R.id.textRemaining);
//        final View layout = findViewById(R.id.layoutActivities);
//        final View[] linkedViews = {textPercent, textToGo, layout};
//        final int fadeDuration = 2000;
//
//
//        SeriesItem seriesItem2 = new SeriesItem.Builder(Color.argb(255, 255, 51, 51))
//                .setRange(0, seriesMax, 0)
//                .setInitialVisibility(false)
//                .setCapRounded(true)
//                .setLineWidth(getDimension(mTrackWidth[mStyleIndex]))
//                .setInset(new PointF(inset, inset))
//                .setCapRounded(mRounded[mStyleIndex])
//                .build();
//
//        mSeries2Index = bigDecoView.addSeries(seriesItem2);
//
//        final TextView textActivity2 = (TextView)findViewById(R.id.textActivity2);
//        textActivity2.setText("");
//        addProgressListener(seriesItem2, textActivity2, "%.0f Km");
//
//
//
//        bigDecoView.addEvent(new DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_OUT)
//                .setIndex(mSeries2Index)
//                .setLinkedViews(linkedViews)
//                .setDuration(2000)
//                .setDelay(1100)
//                .build());
//
//        bigDecoView.addEvent(new DecoEvent.Builder(10).setIndex(mSeries2Index).setDelay(3900).build());
//        bigDecoView.addEvent(new DecoEvent.Builder(22).setIndex(mSeries2Index).setDelay(7000).build());
//



//
//        textPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ChartSeries series = bigDecoView.getChartSeries(mSmallCircleIndex);
//                if (series.isPaused()) {
//                    series.resume();
//                    textPause.setText("Rqwqwqwqwqwqwq");
//                } else {
//
//                    if (series.pause()) {
//                        textPause.setText("000000");
////                        bigDecoView.getChartSeries(mBingIndex).reset();
//                    }
//                }
//            }
//        });



        SeriesItem series1Item = new SeriesItem.Builder(Color.parseColor("#ff00ff"))
                .setRange(0, 10f, 0)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(46))
                .setCapRounded(true)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, COLOR_EDGE, 0.3f))
                .setShowPointWhenEmpty(true)
                .build();

        mSeries1Index = bigDecoView.addSeries(series1Item);

        addFitListener(series1Item, textPause);


        addAnimation(bigDecoView, mSeries1Index, 79.7f, 2000, null, -1, "Cycle %.0f Km", COLOR_GREEN, false);
        addAnimation(bigDecoView, mSeries1Index, 16.4f, 9000,null, -1, "Run %.1f Km", COLOR_YELLOW, false);
        addAnimation(bigDecoView, mSeries1Index, 58f, 16000, null, -1, "Gym %.0f min", COLOR_PINK, false);
        addAnimation(bigDecoView, mSeries1Index, 3.38f, 23000, null,-1, "Swim %.2f Km", COLOR_BLUE, true);


    }

    protected float getDimension(float base) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, base, getResources().getDisplayMetrics());
    }


    private void addFitListener(@NonNull final SeriesItem seriesItem, @NonNull final TextView view) {
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {

            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (mProgress != null) {
                    if (mProgress.contains("%%")) {
                        view.setText(String.format(mProgress, (1.0f - (currentPosition / seriesItem.getMaxValue())) * 100f));
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
     * Add a listener to update the progress on a TextView
     *
     * @param seriesItem ArcItem to listen for update changes
     * @param view       View to update
     * @param format     String.format to display the progress
     *                   <p/>
     *                   If the string format includes a percentage character we assume that we should set
     *                   a percentage into the string, otherwise the current position is added into the string
     *                   For example if the arc has a min of 0 and a max of 50 and the current position is 20
     *                   Format -> "%.0f%% Complete" -> "40% Complete"
     *                   Format -> "%.1f Km" -> "20.0 Km"
     *                   Format -> "%.0f/40 Levels Complete" -> "20/40 Levels Complete"
     */
    protected void addProgressListener(@NonNull final SeriesItem seriesItem, @NonNull final TextView view, @NonNull final String format) {
        if (format.length() <= 0) {
            throw new IllegalArgumentException("String formatter can not be empty");
        }

        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (true) {
                    if (format.contains("%%")) {
                        // We found a percentage so we insert a percentage
                        float percentFilled = ((currentPosition - seriesItem.getMinValue()) / (seriesItem.getMaxValue() - seriesItem.getMinValue()));
                        view.setText(String.format(format, percentFilled * 100f));
                    } else {
                        view.setText(String.format(format, currentPosition));
                    }
                }
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });
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


}
