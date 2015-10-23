package com.jsb.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.jsb.R;
import java.util.List;


public class UIUtils {
    /**
     * px转换为dp
     * @param context
     * @param dp
     * @return
     */
    public static float dp2px(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * px转换为sp
     * @param context
     * @param sp
     * @return
     */
    public static float sp2px(Context context, int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


    /**
     * 初始化下拉刷新样式
     * @param swipeLayout
     */
    public static void initSwipeRefreshLayout(SwipeRefreshLayout swipeLayout) {
        if (swipeLayout == null) return;
        swipeLayout.setColorSchemeResources(R.color.green_color);
//        swipeLayout.setProgressBackgroundColorSchemeColor(R.color.more_dark_green_color);
    }

    /**
     * 设置窗口的透明度
     * @param context
     * @param alpla
     */
    public static void setWindowAlpla(Activity context, float alpla) {
        WindowManager.LayoutParams params = context.getWindow().getAttributes();
        params.alpha = alpla;
        context.getWindow().setAttributes(params);
    }


    /**
     * Hide the soft input
     *
     * @param view
     */
    public static void setHideSoftInput(Context context, View view) {
        InputMethodManager mInputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mInputManager != null) {
            if (view != null) {
                mInputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

            } else {
                if (context instanceof Activity) {
                    Activity activity = (Activity) (context);
                    mInputManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
                }
            }

        }
    }

    /**
     * Show the soft input
     */
    public static void showSoftInput(Context context) {
        InputMethodManager mInputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mInputManager != null) {
            mInputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

        }
    }

    /**
     * Show the soft input
     */
    public static void showSoftInput(Context context, View view) {
        InputMethodManager mInputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mInputManager != null) {
            mInputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);

        }
    }

    public static boolean isAppForground(Context mContext) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 拨打电话
     * @param context
     * @param phoneNumberString
     */
    public static void takePhoneCall(Activity context,String phoneNumberString,int requestCode)
    {
        if(!TextUtils.isEmpty(phoneNumberString))
        {
            Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumberString));
            context.startActivityForResult(phoneIntent, requestCode);
        }
    }
}
