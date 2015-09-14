package com.jsb.widget;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.util.UIUtils;

public class SwitchTabView extends FrameLayout {

    private TextView switchBtn;
    private TextView leftTv;
    private TextView rightTv;
    private TextView showLeftTv;
    private TextView showRightTv;

    private OnTabSelectedListener onTabSelectedListener;

    private String leftText = "";
    private String rightText = "";


    /**
     * 当前选择
     */
    private int currentPos = 0;

    /**
     * 左边tab
     */
    private static final int POS_LEFT = 0;
    /**
     * 右边tab
     */
    private static final int POS_RIGHT = 1;

    /**
     * 是否正在滑动
     */
    private boolean isScrolling = false;

    public SwitchTabView(Context context) {
        super(context);
        initView(context, null);

    }
    private int switchWidth = 50;

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    public SwitchTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SwitchTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(final Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SwitchTabView);
        leftText = array.getString(R.styleable.SwitchTabView_left_tab_text);
        rightText = array.getString(R.styleable.SwitchTabView_right_tab_text);
        array.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.switch_tab_view, null, false);
        this.addView(view);
        leftTv = (TextView) view.findViewById(R.id.left_tv);
        rightTv = (TextView) view.findViewById(R.id.right_tv);
        switchBtn = (TextView) view.findViewById(R.id.switch_btn);

        showLeftTv = (TextView) view.findViewById(R.id.show_left_tv);
        showRightTv = (TextView) view.findViewById(R.id.show_right_tv);

        showLeftTv.setText(leftText);
//        switchBtn.setText(leftText);
        showRightTv.setText(rightText);

        //点击了左边文字
        leftTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isScrolling) {
                    setSwitchEnabled(false);
                    isScrolling = true;
                    switchBtn.animate().translationXBy(UIUtils.dp2px(context, -switchWidth)).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            handleScrollToLeft();
                            setSwitchEnabled(true);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
                }
            }
        });

        //点击了右边文字
        rightTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isScrolling) {
                    setSwitchEnabled(false);
                    isScrolling = true;

                    switchBtn.animate().translationX(UIUtils.dp2px(context, switchWidth)).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            handleScrollToRight();
                            setSwitchEnabled(true);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
                }

            }
        });


        switchBtn.setOnTouchListener(new OnTouchListener() {
            float lastX = 0;
            float maxX = UIUtils.dp2px(context, switchWidth);

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (isScrolling) {
                            return true;
                        }
                        UIUtils.handleHomeViewPagerScrollable(context, false);
                        isScrolling = true;

                        lastX = event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx = event.getRawX() - lastX;
                        float currentX = switchBtn.getX();
                        float newX = currentX + dx;
                        if (newX < 0) {
                            newX = 0;
                        }
                        if (newX > maxX) {
                            newX = maxX;
                        }
                        switchBtn.setX(newX);
                        lastX = event.getRawX();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        UIUtils.handleHomeViewPagerScrollable(context, true);
                        float x = switchBtn.getX();

                        //回到最左方
                        if (x < maxX / 2) {
                            switchBtn.animate().translationXBy(-x).setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    handleScrollToLeft();
                                    setSwitchEnabled(true);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            }).start();

                        }
                        //回到最右方
                        else {
                            switchBtn.animate().translationXBy(maxX - x).setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    handleScrollToRight();
                                    setSwitchEnabled(true);

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            }).start();

                        }
                        break;
                }

                return true;
            }
        });

//        //滑动开关
//        scrollView.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                    return false;
//                }
//                else if(event.getAction() == MotionEvent.ACTION_UP) {
//                    int scrollViewLocation[] = new int[2];
//                    scrollView.getLocationInWindow(scrollViewLocation);
//                    int mostLeft = scrollViewLocation[0];
//                    int mostRight = (int) (mostLeft + UIUtils.dp2px(context, switchWidth));
//
//
//                    int switchLocation[] = new int[2];
//                    switchBtn.getLocationInWindow(switchLocation);
//                    int currentX =switchLocation[0];
//
//                    //滚动到最左边
//                    if(currentX < mostLeft + (mostRight - mostLeft)/2) {
//                        scrollView.fullScroll(FOCUS_RIGHT);
//                        handleScrollToLeft();
//
//
//                    }
//                    //滚动到最右边
//                    else {
//                        scrollView.fullScroll(FOCUS_LEFT);
//
//                        handleScrollToRight();
//                    }
//                    return true;
//
//                }
//                return false;
//            }
//        });
    }

    /**
     * 设置开关是否可点击
     * @param enabled
     */
    private void setSwitchEnabled(boolean enabled) {
        switchBtn.setEnabled(enabled);
        leftTv.setEnabled(enabled);
        rightTv.setEnabled(enabled);
        setEnabled(enabled);
    }

    /**
     * 处理移动到右边
     */
    private void handleScrollToRight() {
        if (currentPos != POS_RIGHT) {
            if (onTabSelectedListener != null) {
                onTabSelectedListener.onRightTabSelected();
            }
//            switchBtn.setText(rightText);
        }
        currentPos = POS_RIGHT;
        isScrolling = false;
    }

    /**
     * 处理移动到左边
     */
    private void handleScrollToLeft() {
        //若是从右滑到左
        if (currentPos != POS_LEFT) {
            if (onTabSelectedListener != null) {
                onTabSelectedListener.onLeftTabSelected();
            }
//            switchBtn.setText(leftText);
        }
        currentPos = POS_LEFT;
        isScrolling = false;
    }

    public interface OnTabSelectedListener {
        public void onLeftTabSelected();

        public void onRightTabSelected();
    }

}
