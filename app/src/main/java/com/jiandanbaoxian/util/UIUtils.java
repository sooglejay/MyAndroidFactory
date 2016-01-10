package com.jiandanbaoxian.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.jiandanbaoxian.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class UIUtils {
    /**
     * px转换为dp
     *
     * @param context
     * @param dp
     * @return
     */
    public static float dp2px(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * px转换为sp
     *
     * @param context
     * @param sp
     * @return
     */
    public static float sp2px(Context context, int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


    /**
     * 初始化下拉刷新样式
     *
     * @param swipeLayout
     */
    public static void initSwipeRefreshLayout(SwipeRefreshLayout swipeLayout) {
        if (swipeLayout == null) return;
        swipeLayout.setColorSchemeResources(R.color.base_color);
//        swipeLayout.setProgressBackgroundColorSchemeColor(R.color.more_dark_green_color);
    }

    /**
     * 设置窗口的透明度
     *
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
            mInputManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);

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
     *
     * @param context
     * @param phoneNumberString
     */
    public static void takePhoneCall(Activity context, String phoneNumberString, int requestCode) {
        if (!TextUtils.isEmpty(phoneNumberString)) {
            Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumberString));
            context.startActivityForResult(phoneIntent, requestCode);
        }
    }

    public static void setGifView(Activity context, GifImageView gifImageView, int imageId) {
        try {
            GifDrawable gifDrawable = new GifDrawable(context.getResources(), imageId);
            gifImageView.setImageDrawable(gifDrawable);
        } catch (Resources.NotFoundException e) {
            Log.e("jwjw", "出错了:NotFoundException");
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("jwjw", "出错了:IOException");

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void showNext(Activity currentAc,Activity nextAc) {
        Intent intent = new Intent(currentAc, nextAc.getClass());
        currentAc.startActivity(intent);
        currentAc.finish();
        //调用此方法让动画效果生效
        currentAc.overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
    }

    public static void showPre(Activity currentAc,Class preAc) {
        Intent intent = new Intent(currentAc, preAc);
        currentAc.startActivity(intent);
        currentAc.finish();
        currentAc.overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
    }

    //名字只能输入中文
   private static InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (isCharAllowed(c)) // put your condition here
                    sb.append(c);
                else
                    keepOriginal = false;
            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }

        private boolean isCharAllowed(char c) {
//            return Character.isLetterOrDigit(c) || Character.isSpaceChar(c);
            return  isChinese(c);
        }
    };


    /**
     * 判定输入汉字
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
    public static void  filterEditText(EditText editText)
    {
        editText.setFilters(new InputFilter[]{filter});
    }

    //文件存储根目录
    private static String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }
    public  static  void generaterQRCode(final Context context, final String content, final ImageView imageView, final int width, final int height)
    {
        final String filePath = getFileRoot(context) + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";

        //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
         new AsyncTask<String, Boolean, Boolean>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected Boolean doInBackground(String... params) {

                return QRCodeUtil.createQRImage(content, width,height,
                        BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher),
                        filePath);
            }

            @Override
            protected void onPostExecute(final Boolean isSuccessful) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));


            }
        }.execute("");
    }
}
