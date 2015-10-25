package com.jsb.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

/**
 * Created by JammyQtheLab on 2015/10/25.
 */
public class SpannableStringUtil {

    /**
     * 获取SpannableString
     * @param string
     * @param color
     * @param size
     * @return
     */
    public static SpannableString getSpannableString(Context mContext,String string,int color,int size)
    {
        SpannableString spanString = new SpannableString(string);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan((int)UIUtils.dp2px(mContext,size),true);
        spanString.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }

    /**
     * 获取SpannableString
     * @param string
     * @param size
     * @return
     */
    public static SpannableString getSpannableString(Context mContext,String string,int size)
    {
        SpannableString spanString = new SpannableString(string);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan((int)UIUtils.dp2px(mContext,size),true);
        spanString.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }


}
