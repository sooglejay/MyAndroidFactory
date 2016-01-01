package com.jiandanbaoxian.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollableViewPager extends android.support.v4.view.ViewPager {

    private boolean isScroll = true;

    public boolean isScroll() {
        return isScroll;
    }

    public void setScrollable(boolean isScroll) {
        this.isScroll = isScroll;
    }

    public ScrollableViewPager(Context context) {
        super(context);
    }

    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isScroll) {
            return false;
        }
        try {
            return super.onInterceptTouchEvent(ev);

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isScroll) {
            return false;
        }
        try {
            return super.onTouchEvent(ev);

        } catch (Exception e) {
            return false;
        }
    }
}
