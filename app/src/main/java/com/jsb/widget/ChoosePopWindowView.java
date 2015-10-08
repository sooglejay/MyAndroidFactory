package com.jsb.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.adapter.ChoosePopListViewAdapter;
import com.jsb.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/7.
 */
public class ChoosePopWindowView {

    public final static int CHOOSE_POP_WEEK = 1;
    public final static int CHOOSE_POP_CAR = 2;
    private  int mPopWindowWidthDp = -1;
    private PopupWindow popupWindow;
    private Context mContext;
    private ListView mListView;
    private List<String> dataList = new ArrayList<>();
    private ChoosePopListViewAdapter listAdapter;
    private TextView tvShowText;


    public ChoosePopWindowView(Context mContext, List<String> dataList,int flag) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.listAdapter = new ChoosePopListViewAdapter(mContext, dataList);
        switch (flag)
        {
           case CHOOSE_POP_WEEK:
                mPopWindowWidthDp = 58;
                break;
            case CHOOSE_POP_CAR:
                mPopWindowWidthDp = 106;
                break;
            default:
                mPopWindowWidthDp = 58;
                break;
        }
        initView(mContext);
    }

    private void initView(Context context) {
        View viewLayout  = LayoutInflater.from(context).inflate(R.layout.view_choose_popwindow, null);
        mListView = (ListView)viewLayout.findViewById(R.id.list_view);
        mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvShowText.setText(dataList.get(position) + "");
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(viewLayout,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_4_light_black));
    }

    public  void show(View view,TextView tvShowText)
    {
        popupWindow.showAsDropDown(view,(int)UIUtils.dp2px(mContext,12), -(int)UIUtils.dp2px(mContext,12));
        this.tvShowText = tvShowText;
    }
}
