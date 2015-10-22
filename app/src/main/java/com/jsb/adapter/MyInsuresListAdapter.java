package com.jsb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.ui.MyInsuresListCarInsureDetailActivity;
import com.jsb.ui.MyInsuresListDrivingInsureDetailActivity;
import com.jsb.ui.MyInsuresListJiaBanDogInsureDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MyInsuresListAdapter extends BaseAdapter {
    public MyInsuresListAdapter(Context mContext, List<String> datas) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    private Context mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<String> mDatas = new ArrayList<String>();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        if(convertView==null)
        {
            convertView = View.inflate(mContext, R.layout.item_my_insure_list,null);
            holder.tv_insure_name = (TextView)convertView.findViewById(R.id.tv_insure_name);
            holder.tv_buy_insure_time = (TextView)convertView.findViewById(R.id.tv_buy_insure_time);
            holder.tv_insure_price = (TextView)convertView.findViewById(R.id.tv_insure_price);
            holder.tv_buy_insure_agent = (TextView)convertView.findViewById(R.id.tv_buy_insure_agent);
            holder.item = (LinearLayout)convertView.findViewById(R.id.item);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_insure_name.setText(mDatas.get(position) + "");
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position)
                {
                    case 0:
                        mContext.startActivity(new Intent(mContext, MyInsuresListCarInsureDetailActivity.class));
                        break;
                    case 1:
                        mContext.startActivity(new Intent(mContext, MyInsuresListDrivingInsureDetailActivity.class));
                        break;
                    case 2:
                        mContext.startActivity(new Intent(mContext, MyInsuresListJiaBanDogInsureDetailActivity.class));
                        break;
                }

            }
        });

        return convertView;
    }
    private class ViewHolder
    {
        TextView tv_insure_name;
        TextView tv_buy_insure_time;
        TextView tv_insure_price;
        TextView tv_buy_insure_agent;
        LinearLayout item;
    }
}
