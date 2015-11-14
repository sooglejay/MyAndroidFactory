package com.jsb.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

/**
 * Created by JammyQtheLab on 2015/10/25.
 */
public class SpannableStringUtil {



    /**
     * 获取SpannableString
     * @param string
     * @param sizeUnion
     * @return
     */
    public static SpannableString getSpannableString(Context mContext,String string,int sizeUnion)
    {

        SpannableString spanString = new SpannableString(string);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(sizeUnion);
        spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }

}
