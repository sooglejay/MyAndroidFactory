package com.jsb.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.jsb.R;
import com.jsb.widget.imagepicker.MultiImageSelectorActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ImageUtils {
    /**
     * 选择图片
     */
    public static final int REQUEST_CODE_PICK_IMAGE = 1991;
    /**
     * 拍照
     */
    public static final int REQUEST_CODE_CAPTURE_IMAGE = 1992;
    /**
     * 裁剪图片
     */
    public static final int REQUEST_CODE_CROP_IMAGE = 1993;
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

    /**
     * 取得图片文件夹位置
     *
     * @param context
     * @return
     */
    public static String getImageFolderPath(Context context) {
        String packPath = "";

        try {
            packPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();

        } catch (Exception e) {
            packPath = context.getFilesDir() + File.separator + "Pictures";

        } finally {
            return packPath;
        }
    }

    /**
     * 裁剪图片
     *
     * @param activity
     * @param imageUri
     * @return
     */
    public static void cropImage(Activity activity, Uri imageUri, String resultPath, int aspectX, int aspectY) {
        Intent intent = getCropImageIntent(imageUri, resultPath, aspectX, aspectY);

        activity.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    public static void cropImage(Fragment fragment, Uri imageUri, String resultPath, int aspectX, int aspectY) {
        Intent intent = getCropImageIntent(imageUri, resultPath, aspectX, aspectY);
        fragment.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }


    @NonNull
    private static Intent getCropImageIntent(Uri imageUri, String resultPath, int aspectX, int aspectY) {
        //创建图片文件
        File resultFile = new File(resultPath);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        if(aspectX != 0) {
            intent.putExtra("aspectX", aspectX);
        }
        if(aspectY != 0) {
            intent.putExtra("aspectY", aspectY);
        }
        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 800);
//        intent.putExtra("outputY", 800);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(resultFile));
        return intent;
    }

    public static Bitmap compressImage(Context context, String path) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1024f;//这里设置高度为800f
        float ww = 1024f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        float be = 1f;//be=1表示不缩放
        if (w >= h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be =  (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) {
            be = 1f;
        }
        newOpts.inSampleSize = (int) be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(path, newOpts);
        if(bitmap.getHeight() > hh || bitmap.getWidth() > ww) {
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();
            float scaleSize = 1f;
            if(width >= height) {
                scaleSize = ww/width;
            }
            else if(height > width) {
                scaleSize = ww/height;
            }
            Matrix matrix = new Matrix();
            matrix.postScale(scaleSize, scaleSize);
            Bitmap result = Bitmap.createBitmap(bitmap, 0, 0,  width,
                    height, matrix, true);
            bitmap.recycle();
            return result;
        }

        return bitmap;//压缩好比例大小后再进行质量压缩
    }



    /**
     * 选择照片
     */
    public static void startPickPhoto(Activity context, ArrayList<String> selectedImages, int max_count, boolean isModeMulti) {
        Intent intent = getPickPhotoIntent(context, selectedImages, max_count, isModeMulti);
        context.startActivityForResult(intent, ImageUtils.REQUEST_CODE_PICK_IMAGE);
    }

    /**
     * 选择照片
     */
    public static void startPickPhoto(Activity context, Fragment fragment, ArrayList<String> selectedImages, int max_count, boolean isModeMulti) {
        Intent intent = getPickPhotoIntent(context, selectedImages, max_count, isModeMulti);
        fragment.startActivityForResult(intent, ImageUtils.REQUEST_CODE_PICK_IMAGE);
    }

    @NonNull
    private static Intent getPickPhotoIntent(Activity context, ArrayList<String> selectedImages, int max_count, boolean isModeMulti) {
        Intent intent = new Intent(context, MultiImageSelectorActivity.class);

        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);

        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, max_count);

        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, isModeMulti ? MultiImageSelectorActivity.MODE_MULTI : MultiImageSelectorActivity.MODE_SINGLE);

        // 默认选择
        if (selectedImages != null && selectedImages.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, selectedImages);
        }
        return intent;
    }

}
