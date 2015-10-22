package com.jsb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.model.aaa_HistorySaleBean;
import com.jsb.ui.MyInsuresListCarInsureDetailActivity;
import com.jsb.ui.MyInsuresListDrivingInsureDetailActivity;
import com.jsb.ui.MyInsuresListJiaBanDogInsureDetailActivity;
import com.jsb.util.UIUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MyHistorySaleAdapter extends BaseAdapter {
    public static final int GONE_UNSELECTED = 0;
    public static final int VISIBLE_UNSELECTED = 1;
    public static final int VISIBLE_SELECTED = 2;


    private View footerView;
    private View listView;
    private RelativeLayout.LayoutParams layoutParams;


    public MyHistorySaleAdapter(Context mContext, List<aaa_HistorySaleBean> datas,View footerView,View listView) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;
        this.footerView = footerView;
        this.listView = listView;
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);



    }

    private Context mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<aaa_HistorySaleBean> mDatas = new ArrayList<aaa_HistorySaleBean>();


    /**
     *
     * @param isDelete 删除 或者 取消
     */
    public void deleteItem(boolean isDelete)
    {
        if(isDelete)
        {
            Iterator keys = mDatas.iterator();
            while (keys.hasNext())
            {
                aaa_HistorySaleBean bean = (aaa_HistorySaleBean)keys.next();
                if(bean.getStatus()==VISIBLE_SELECTED)
                {
                    keys.remove();
                }
            }
            for (aaa_HistorySaleBean bean :mDatas)
            {
                bean.setStatus(GONE_UNSELECTED);
            }
        }else{
            for (aaa_HistorySaleBean bean : mDatas)
            {
               bean.setStatus(GONE_UNSELECTED);
            }
        }


        layoutParams.setMargins(0,0,0,0);
        listView.setLayoutParams(layoutParams);
        footerView.setVisibility(View.GONE);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public aaa_HistorySaleBean getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_my_history_sale_adapter, null);
            holder.tv_insure_name = (TextView) convertView.findViewById(R.id.tv_insure_name);
            holder.tv_buy_insure_time = (TextView) convertView.findViewById(R.id.tv_buy_insure_time);
            holder.tv_insure_price = (TextView) convertView.findViewById(R.id.tv_insure_price);
            holder.tv_buy_insure_agent = (TextView) convertView.findViewById(R.id.tv_buy_insure_agent);
            holder.iv_choose = (ImageView) convertView.findViewById(R.id.iv_choose);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        aaa_HistorySaleBean bean = getItem(position);
        holder.tv_insure_name.setText(bean.getInsureNameStr() + "");


        switch (bean.getStatus()) {
            case GONE_UNSELECTED:
                holder.iv_choose.setImageResource(R.drawable.icon_choose);
                holder.iv_choose.setVisibility(View.GONE);
                break;
            case VISIBLE_UNSELECTED:
                holder.iv_choose.setImageResource(R.drawable.icon_choose);
                holder.iv_choose.setVisibility(View.VISIBLE);
                break;
            case VISIBLE_SELECTED:
                holder.iv_choose.setImageResource(R.drawable.icon_choose_selected);
                holder.iv_choose.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aaa_HistorySaleBean tagBean = (aaa_HistorySaleBean) v.getTag();
                switch (tagBean.getStatus()) {
                    case GONE_UNSELECTED:
                        Toast.makeText(mContext, "点击事件", Toast.LENGTH_SHORT).show();
                        break;
                    case VISIBLE_UNSELECTED:
                        tagBean.setStatus(VISIBLE_SELECTED);
                        break;
                    case VISIBLE_SELECTED:
                        tagBean.setStatus(VISIBLE_UNSELECTED);
                        break;
                }
                notifyDataSetChanged();

            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                aaa_HistorySaleBean tagBean = (aaa_HistorySaleBean) v.getTag();
                if (tagBean.getStatus() > 0)//如果长按时，已经有item显示了 checkBox，则隐藏
                {

                    footerView.setVisibility(View.GONE);
                    layoutParams.setMargins(0,0,0,0);
                    listView.setLayoutParams(layoutParams);
                    for (aaa_HistorySaleBean bean : mDatas) {
                        bean.setStatus(GONE_UNSELECTED);
                    }
                } else {//否则，就设置为 ：显示+未选中 状态
                    footerView.setVisibility(View.VISIBLE);
                    layoutParams.setMargins(0,0,0, (int)UIUtils.dp2px(mContext,48));
                    listView.setLayoutParams(layoutParams);
                    for (aaa_HistorySaleBean bean : mDatas) {
                        bean.setStatus(VISIBLE_UNSELECTED);
                    }
                }
                notifyDataSetChanged();
                return false;
            }
        });


        holder.item.setTag(bean);

        return convertView;
    }

    private class ViewHolder {
        TextView tv_insure_name;
        TextView tv_buy_insure_time;
        TextView tv_insure_price;
        TextView tv_buy_insure_agent;
        ImageView iv_choose;
        LinearLayout item;
    }

}
