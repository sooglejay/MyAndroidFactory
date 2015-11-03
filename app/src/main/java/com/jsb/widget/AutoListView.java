package com.jsb.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.util.TimeUtil;


public class AutoListView extends ListView implements OnScrollListener {

    public static final int REFRESH = 0;
    public static final int LOADMORE = 1;
    public static final int StopLoading = -1;
    public static final int onFailureLoad = -100;//如果上拉加载更多失败的处理，已在setResultSize中处理
    public static final int onFailureRefresh = -101;//如果下拉刷新更多失败的处理 ，还未在setResultSize中处理！
    public static final int PerPageSize = 20;//

    private static final int SPACE = 64;


    private static final int NONE = 0;
    private static final int PULL = 1;
    private static final int RELEASE = 2;
    private static final int REFRESHING = 3;
    private int state;

    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;

    private LayoutInflater inflater;
    private View footer;

    private TextView loadFull;
    private TextView more;
    private ProgressBar footerProgress;
//    private AnimationDrawable progressAnim;

    private int firstVisibleItem;
    private int scrollState;

    private boolean isRecorded;
    private boolean isLoading;
    private boolean loadEnable = true;
    private boolean isLoadFull;
    private int pageSize = PerPageSize;

    private OnRefreshListener onRefreshListener;
    private OnLoadListener onLoadListener;

    private View mKeepScreenHeaderView;

    private int mExtraHeaderCount = 0;

    private OnAutoListViewScrollListener onAutoListViewScrollListener;


    public AutoListView(Context context) {
        super(context);
        initView(context);
    }

    public AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AutoListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    // 下拉刷新监听
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    // 加载更多监听
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.loadEnable = true;
        this.onLoadListener = onLoadListener;
    }

    public boolean isLoadEnable() {
        return loadEnable;
    }

    //不支持动态调整
    public void setLoadEnable(boolean loadEnable) {
        this.loadEnable = loadEnable;
        if (!loadEnable) {
            this.removeFooterView(footer);
        } else if (loadEnable && this.getFooterViewsCount() == 0) {
            this.addFooterView(footer);
        }

    }

    public void setOnAutoListViewScrollListener(OnAutoListViewScrollListener onAutoListViewScrollListener) {
        this.onAutoListViewScrollListener = onAutoListViewScrollListener;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    // 初始化组件
    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.listview_footer, null);
        footerProgress = (ProgressBar) footer.findViewById(R.id.progress);
        loadFull = (TextView) footer.findViewById(R.id.loadFull);
        more = (TextView) footer.findViewById(R.id.more);
//        progressAnim = (AnimationDrawable) footerProgress.getBackground();


        footer.setClickable(false);

        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    public void addOtherHeader(View view) {
        this.addHeaderView(view);
    }

    public void addKeepScreenHeader(View view) {
        mKeepScreenHeaderView = view;
    }


    public void doRefresh() {

    }

    public void onRefresh() {

    }

    public void onLoad() {
        if (onLoadListener != null) {
            onLoadListener.onLoad();
        }
    }

    public void onRefreshComplete(String updateTime) {
        state = NONE;
    }

    public void onRefreshComplete() {
        String currentTime = TimeUtil.getCurrentTime();
        onRefreshComplete(currentTime);
    }

    public void onLoadComplete() {
        isLoading = false;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        if (isLoading) {
            //显示正在加载
            footerProgress.setVisibility(View.VISIBLE);
            loadFull.setVisibility(View.GONE);
            footer.setVisibility(View.VISIBLE);

//            more.setVisibility(View.VISIBLE);
        } else {
            //加载完成
            footer.setVisibility(View.VISIBLE);
            loadFull.setVisibility(View.VISIBLE);//显示“加载完成”
            loadFull.setText("");
//            more.setVisibility(View.GONE);//隐藏 “加载中”
            footerProgress.setVisibility(View.GONE);//隐藏 加载中的progress
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (onAutoListViewScrollListener != null) {
            onAutoListViewScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

        this.firstVisibleItem = firstVisibleItem;
        if (mKeepScreenHeaderView != null) {
            if (firstVisibleItem >= mExtraHeaderCount) {
                mKeepScreenHeaderView.setVisibility(View.VISIBLE);
            } else {

                mKeepScreenHeaderView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        if (onAutoListViewScrollListener != null) {
            onAutoListViewScrollListener.onScrollStateChanged(view, scrollState);
        }

        ifNeedLoad(view, scrollState);
    }

    // 根据listview滑动的状态判断是否需要加载更多
    private void ifNeedLoad(AbsListView view, int scrollState) {
        if (!loadEnable) {
            return;
        }
        try {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && !isLoading && view.getLastVisiblePosition() == view.getPositionForView(footer)) {
                onLoad();
                isLoading = true;
            }
        } catch (Exception e) {
        }
    }

    /**
     * 设置加载完成View是否可见
     */
    public void setFullViewVisibility(boolean isVisible) {

        if (isVisible) {
            loadFull.setVisibility(View.VISIBLE);
            footerProgress.setVisibility(View.VISIBLE);
            more.setVisibility(View.VISIBLE);

        } else {
            loadFull.setVisibility(View.GONE);
            footerProgress.setVisibility(View.GONE);
            more.setVisibility(View.GONE);

        }
    }

    //保留方法
    public void setResultSize(int resultSize) {
//        isLoading = false;
//        setLoading(isLoading);
    }


    private void measureView(View child) {
        ViewGroup.LayoutParams layoutParam = child.getLayoutParams();
        if (layoutParam == null) {
            layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, layoutParam.width);
        int lpHeight = layoutParam.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /*
     * 定义下拉刷新接口
     */
    public interface OnRefreshListener {
        public void onRefresh();
    }

    /*
     * 定义加载更多接口
     */
    public interface OnLoadListener {
        public void onLoad();
    }


    public interface OnAutoListViewScrollListener {
        public void onScrollStateChanged(AbsListView view, int scrollState);

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }
}
