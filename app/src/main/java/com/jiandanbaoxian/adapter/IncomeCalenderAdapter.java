package com.jiandanbaoxian.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.widget.timepicker.FatherBean;
import com.jiandanbaoxian.widget.timepicker.MyGridView;
import com.jiandanbaoxian.widget.timepicker.SonBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/20.
 */
public class IncomeCalenderAdapter extends BaseAdapter {

    public final static int CHOOSE = 2;
    public final static int OTHERS = 3;


    private static final int Data = 0;
    private static final int Header = 1;
    private static final int ViewTypeCount = 2;

    private Activity mContext;
    private List<Object> mDatas = new ArrayList<>();
    private List<SonBean> dayListBean = new ArrayList<>();//我把子Adapter的 数据源拿到了外面来了

    public void setmReserverDaysStr(List<String> mReserverDaysStr) {
        this.mReserverDaysStr = mReserverDaysStr;
        for (String str : mReserverDaysStr) {
            for (SonBean bean : dayListBean) {
                if (str.equals(bean.getDateStr())) {
                    bean.setStatus(CHOOSE);//由于之前所有的设置为OTHERs，所以现在只需要设置CHOOSE就行了
                }
            }
        }
        notifyDataSetChanged();//由于我在Adapter里面写了Adapter,所以，我不确定能够刷新子Adapter
    }

    private List<String> mReserverDaysStr = new ArrayList<>();
    private LayoutInflater inflater;
    private int tvWhiteColorResId, tvBlackColorResId;//日历 数字本身的颜色值
    private SimpleDateFormat dateFormat_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");//日期格式化
    private String todayString_yyyy_m_d = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    public IncomeCalenderAdapter(Activity mContext, List<Object> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(mContext);
        tvWhiteColorResId = mContext.getResources().getColor(R.color.white_color);
        tvBlackColorResId = mContext.getResources().getColor(R.color.black_color);
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return ViewTypeCount;
    }

    @Override
    public int getItemViewType(int position) {
        return (mDatas.get(position) instanceof String) ? Header
                : Data;
    }


    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != Header;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final int type = getItemViewType(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            final int layoutID = type == Header ? R.layout.aaa_item_calender_list_view_header : R.layout.aaa_item_calender_list_view_data;
            convertView = inflater.inflate(layoutID, parent, false);
            if (type == Header) {
                holder.tvHeaderString = (TextView) convertView.findViewById(R.id.tv_type_header);
            } else {
                holder.myGridView = (MyGridView) convertView.findViewById(R.id.gv_calendar);
                holder.innerGridViewAdapter = new InnerGridViewAdapter();
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Object obj = getItem(position);
        if (type == Header) {//如果是标题栏
            String yyyymm = (String) obj;//比如 ：yyyymm 是 2015年3月
            holder.tvHeaderString.setText(yyyymm + "");
        } else if (obj instanceof FatherBean) {//如果是数据
            FatherBean bean = (FatherBean) obj;
            holder.innerGridViewAdapter.setYmDatas(bean);
            holder.myGridView.setAdapter(holder.innerGridViewAdapter);
        }
        return convertView;
    }

    private static class ViewHolder {
        private TextView tvHeaderString;
        private MyGridView myGridView;
        private InnerGridViewAdapter innerGridViewAdapter;
    }

    private class InnerGridViewAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return dayListBean.size();
        }

        @Override
        public Object getItem(int position) {
            return dayListBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            IncomeCalenderAdapter.this.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final GrideViewHolder innerGridViewHolder;
            if (convertView == null) {
                innerGridViewHolder = new GrideViewHolder();
                convertView = inflater.inflate(R.layout.item_calendar_gridview, parent, false);
                innerGridViewHolder.tv_bottom = (TextView) convertView.findViewById(R.id.tv_bottom);
                innerGridViewHolder.tv_up = (TextView) convertView.findViewById(R.id.tv_up);
                innerGridViewHolder.item = (LinearLayout) convertView.findViewById(R.id.item);
                innerGridViewHolder.onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SonBean bean = (SonBean) v.getTag();
                        if (TextUtils.isEmpty(bean.getDateStr())) {
                            return;//说明是该位置没有日期

                        }
                        //要手动去通知!
                        notifyDataSetChanged();
                    }
                };
                convertView.setTag(innerGridViewHolder);
            } else {
                innerGridViewHolder = (GrideViewHolder) convertView.getTag();
            }


            String dayNumberString = dayListBean.get(position).getDayNumberString();
            int status = dayListBean.get(position).getStatus();
            innerGridViewHolder.tv_up.setText(dayNumberString + "");
            switch (status) {
                case CHOOSE:
                    innerGridViewHolder.tv_bottom.setText("");
                    innerGridViewHolder.tv_bottom.setTextColor(tvBlackColorResId);
                    innerGridViewHolder.tv_up.setTextColor(tvWhiteColorResId);
                    innerGridViewHolder.item.setBackgroundResource(R.drawable.shape_circle_base_color);
                    break;
                case OTHERS:
                    innerGridViewHolder.tv_bottom.setText("");
                    innerGridViewHolder.tv_bottom.setTextColor(tvBlackColorResId);
                    innerGridViewHolder.tv_up.setTextColor(tvBlackColorResId);
                    innerGridViewHolder.item.setBackgroundResource(R.drawable.btn_select_for_white_to_circle_base_color);
                    break;
                default:
                    break;
            }


//
//            try {
//                if(dateFormat_yyyy_MM_dd.parse(dayListBean.get(position).getDateStr()).getTime() < orderStartTimeFromServer)
//                {
//                    innerGridViewHolder.tv_up.setTextColor(mContext.getResources().getColor(R.color.light_gray_color));
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }


            //如果是周末
            if ((position + 1) % 7 == 0 || (position) % 7 == 0) {
                innerGridViewHolder.tv_up.setTextColor(mContext.getResources().getColor(R.color.red_color));
            }


            //如果是今天
            String dateString = dayListBean.get(position).getDateStr();
            if (dateString.equals(todayString_yyyy_m_d)) {
                innerGridViewHolder.tv_up.setTextColor(mContext.getResources().getColor(R.color.orange_color));
                innerGridViewHolder.tv_up.setText("今天");
            }

            innerGridViewHolder.item.setTag(dayListBean.get(position));
            innerGridViewHolder.item.setOnClickListener(innerGridViewHolder.onClickListener);

            return convertView;
        }

        public void setYmDatas(FatherBean ymDatas) {
            dayListBean.clear();
            dayListBean.addAll(ymDatas.getDaysList());
        }
    }

    static class GrideViewHolder {
        private TextView tv_up, tv_bottom;
        private LinearLayout item;
        private View.OnClickListener onClickListener;
    }

}
