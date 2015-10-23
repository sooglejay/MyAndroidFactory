package com.jsb.util;

import android.graphics.Bitmap;

import com.jsb.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;


public class ImageUtils {

    /**
     * 取得图片展示选项
     *
     * @param imgs
     * @return
     */
    public static DisplayImageOptions getOptions(int... imgs) {
        int loadingImg = R.color.very_light_gray_color;
        int emptyImg = R.color.very_light_gray_color;
        int failImg = R.color.very_light_gray_color;
        if (imgs.length == 3) {
            loadingImg = imgs[0];
            emptyImg = imgs[1];
            failImg = imgs[2];
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadingImg)
                .showImageForEmptyUri(emptyImg)
                .showImageOnFail(failImg)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer()).build();
        return options;
    }
}
