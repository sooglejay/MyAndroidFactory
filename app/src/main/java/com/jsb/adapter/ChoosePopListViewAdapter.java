package com.jsb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jsb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class ChoosePopListViewAdapter extends BaseAdapter {
    public ChoosePopListViewAdapter(Context mContext, List<String> datas) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        if(convertView==null)
        {
            convertView = View.inflate(mContext, R.layout.item_choose_popwindow,null);
            holder.tvName = (TextView)convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvName.setText(mDatas.get(position)+"");

        return convertView;
    }
    private class ViewHolder
    {
        TextView tvName;
    }
}
