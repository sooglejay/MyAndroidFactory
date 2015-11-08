package com.jsb.widget;

import android.app.Activity;
import android.app.SearchManager;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jsb.R;
import com.jsb.util.UIUtils;
import com.jsb.widget.wheel.WheelView;

import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/25.
 */
public class PopWindowUtils {

    private PopupWindow pop;

    public PopWindowUtils(Activity mContext) {
        this.mContext = mContext;
    }

    private Activity mContext;

    public PopupWindow showMeFragmentPopWindows(View v, View.OnClickListener onClickListener) {

        View view = mContext.getLayoutInflater().inflate(R.layout.pop_up_window_me_fragment, null);
        LinearLayout layout_modify_password = (LinearLayout) view.findViewById(R.id.layout_modify_password);
        LinearLayout layout_login_out = (LinearLayout) view.findViewById(R.id.layout_login_out);
        LinearLayout layout_clear_buffer = (LinearLayout) view.findViewById(R.id.layout_clear_buffer);


        layout_modify_password.setOnClickListener(onClickListener);
        layout_login_out.setOnClickListener(onClickListener);
        layout_clear_buffer.setOnClickListener(onClickListener);


        pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.transparent));
        pop.setWidth((int) UIUtils.dp2px(mContext, 250));
        pop.setHeight((int) UIUtils.dp2px(mContext, 250));
        pop.setBackgroundDrawable(dw);
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.popupwindow_animation);
        pop.showAsDropDown(v, 0, (int) UIUtils.dp2px(mContext, -12));//必须放在setBackgroundDrawable和setOutsideTouchable之后
        UIUtils.setWindowAlpla(mContext, 1f);
        //取消不透明的效果
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(onPopWindowDismissListener!=null)
                {
                    onPopWindowDismissListener.onDismissListener();
                }
            }
        });

        return pop;
    }


    public void dismiss() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
    }

    public boolean isShowing() {
        if (pop != null) {
            return pop.isShowing();
        }
        return false;
    }

    public interface OnPopWindowDismissListener
    {
        public void onDismissListener();
    }
    public OnPopWindowDismissListener onPopWindowDismissListener;

    public void setOnPopWindowDismissListener(OnPopWindowDismissListener onPopWindowDismissListener) {
        this.onPopWindowDismissListener = onPopWindowDismissListener;
    }
}
