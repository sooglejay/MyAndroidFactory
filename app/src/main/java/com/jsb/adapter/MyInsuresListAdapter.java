package com.jsb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsb.Bean.aaa_MyInsuranceBean;
import com.jsb.R;
import com.jsb.ui.MyInsuresListCarInsureDetailActivity;
import com.jsb.ui.MyInsuresListDrivingInsureDetailActivity;
import com.jsb.ui.MyInsuresListJiaBanDogInsureDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MyInsuresListAdapter extends BaseAdapter {

    private SimpleDateFormat dateFormat_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");

    public MyInsuresListAdapter(Context mContext, List<aaa_MyInsuranceBean> datas) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    private Context mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<aaa_MyInsuranceBean> mDatas = new ArrayList<>();
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public aaa_MyInsuranceBean getItem(int position) {
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
        holder.tv_insure_name.setText(mDatas.get(position).getInsuranceName() + "");
        holder.tv_insure_price.setText(mDatas.get(position).getInsuranceMoney() + "");
        holder.tv_buy_insure_agent.setText(mDatas.get(position).getInsuranceCompanyName() + "");
        holder.tv_buy_insure_time.setText(dateFormat_yyyy_mm_dd.format(mDatas.get(position).getInsuranceBuyDate()) + "");


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
