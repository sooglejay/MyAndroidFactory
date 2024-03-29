/**
 * ****************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * *****************************************************************************
 */
package com.handmark.pulltorefresh.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout;

public class PullToRefreshLinearLayout extends PullToRefreshBase<LinearLayout> {

    public PullToRefreshLinearLayout(Context context) {
        super(context);
    }

    public PullToRefreshLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshLinearLayout(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshLinearLayout(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected LinearLayout createRefreshableView(Context context, AttributeSet attrs) {
        LinearLayout linearLayout;
        if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
            linearLayout = new InternalLinearLayoutSDK9(context, attrs);
        } else {
            linearLayout = new LinearLayout(context, attrs);
        }

        linearLayout.setId(R.id.linearlayout);
        return linearLayout;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return mRefreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        View LinearLayoutChild = mRefreshableView.getChildAt(0);
        if (null != LinearLayoutChild) {
            return mRefreshableView.getScrollY() >= (LinearLayoutChild.getHeight() - getHeight());
        }
        return false;
    }

    @TargetApi(9)
    final class InternalLinearLayoutSDK9 extends LinearLayout {

        public InternalLinearLayoutSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                    scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(PullToRefreshLinearLayout.this, deltaX, scrollX, deltaY, scrollY,
                    getScrollRange(), isTouchEvent);

            return returnValue;
        }

        /**
         * Taken from the AOSP LinearLayout source
         */
        private int getScrollRange() {
            int scrollRange = 0;
            if (getChildCount() > 0) {
                View child = getChildAt(0);
                scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
            }
            return scrollRange;
        }
    }
}
