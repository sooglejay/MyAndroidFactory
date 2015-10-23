package com.jsb.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.model.Banner;
import com.jsb.util.ImageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

import de.greenrobot.event.EventBus;

public class BannerView extends FrameLayout {

    private static final int FLAG_SHOW_NEXT = 1;
    /**
     * 加载下一张图片需要时间
     */
    private int SHOW_NEXT_TIME = 5000;

    private ViewPager viewPager;
    private BannerAdapter bannerAdapter;
    private List<Banner> data = new ArrayList<Banner>();


    private float height;
    private Handler showNextHandler;

    private OnBannerClickListener listener;

    /**
     * 是否用户手动滑动
     */
    private boolean isCustomScrolling = false;

    public BannerView(Context context) {
        super(context);

        initView(context);
    }


    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_banner, null, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int delta) {
                isCustomScrolling = true;
                if (delta == 0) {
                    isCustomScrolling = false;
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
//                if(i == ViewPager.SCROLL_STATE_IDLE){
//                    EventBus.getDefault().post(new TouchBannerEvent(true));
//                }
//                else {
//                    EventBus.getDefault().post(new TouchBannerEvent(false));
//
//                }
            }
        });



        //计算图片高度，保持3:1比例
        height = getResources().getDisplayMetrics().widthPixels / 3;
        viewPager.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height));

        //一次只加载当前
        viewPager.setOffscreenPageLimit(0);

//        initBannerAdapter(context, data, listener);

        this.addView(view);

    }

    /**
     * 设置展示切换事件
     *
     * @param time
     */
    public void setDisplayTime(int time) {
        SHOW_NEXT_TIME = time;
    }


    /**
     * 初始化bannerAdapter
     *
     * @param context
     * @param data
     * @param listener
     */
    public void initBannerAdapter(Context context, List<Banner> data, OnBannerClickListener listener) {
        this.listener = null;

        bannerAdapter = new BannerAdapter(context, data, height, listener);
        viewPager.setAdapter(bannerAdapter);


        if (showNextHandler != null) {
            showNextHandler.removeCallbacksAndMessages(null);
        }

        //滑动到下一张图片
        showNextHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == FLAG_SHOW_NEXT) {
                    int nextPos = viewPager.getCurrentItem() + 1;
                    if (nextPos >= bannerAdapter.getCount()) {
                        nextPos = 0;
                    }
                    if (!isCustomScrolling) {
                        viewPager.setCurrentItem(nextPos);
                    }
                    showNextHandler.sendEmptyMessageDelayed(FLAG_SHOW_NEXT, SHOW_NEXT_TIME);
                }
            }
        };

        showNextHandler.sendEmptyMessageDelayed(FLAG_SHOW_NEXT, SHOW_NEXT_TIME);


    }

    private static class BannerAdapter extends PagerAdapter {

        private static final int MAX_VALUE = 100;
        private List<Banner> data = new ArrayList<>();
        private Context context;
        private float imgHeight;
        private OnBannerClickListener listener;
        private List<String> trueImgs = new ArrayList<>();

        public BannerAdapter(Context context, List<Banner> data, float imgHeight, final OnBannerClickListener listener) {
            //初始化imageViews
            this.data.clear();
            this.data.addAll(data);
            this.context = context;
            this.imgHeight = imgHeight;
            this.listener = listener;
        }

        @Override
        public int getCount() {
            return MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            if (data.size() > 0) {
                final ImageView iv = new ImageView(context);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) imgHeight));
                final Banner banner = data.get(position % data.size());
                String currentUrl = banner.getFile_location();

                ImageLoader.getInstance().displayImage(currentUrl, iv, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(final String imageUri, View view, final Bitmap loadedImage) {
                        if (trueImgs.contains(imageUri)) {
                            iv.setImageBitmap(loadedImage);
                        } else {
                            new AsyncTask<Bitmap, Void, Long>() {

                                @Override
                                protected Long doInBackground(Bitmap... params) {

                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Long value) {
                                    super.onPostExecute(value);
                                      iv.setImageBitmap(loadedImage);
                                      trueImgs.add(imageUri);

                                }
                            }.execute(loadedImage);
                        }

                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });

                ImageLoader.getInstance().displayImage(currentUrl, iv, ImageUtils.getOptions(R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher));
                iv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onBannerClick(position % data.size());
                        }
                    }
                });

                container.addView(iv);
                return iv;
            }
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public interface OnBannerClickListener {
        public void onBannerClick(int position);
    }
}
