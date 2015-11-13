package com.jsb.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.model.FreedomData;
import com.jsb.model.SelfRecord;

import java.util.List;

/**
 * Created by JammyQtheLab on 2015/11/12.
 */
public class LeaderSearchMemberAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<SelfRecord> mDatas;
    private Activity activity;

    public LeaderSearchMemberAdapter(List<SelfRecord> mDatas, Activity activity) {
        this.mDatas = mDatas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public SelfRecord getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_leader_search_member_list, null);
            holder.tv_month_amount = (TextView) convertView.findViewById(R.id.tv_month_amount);
            holder.tv_year_amount = (TextView) convertView.findViewById(R.id.tv_year_amount);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            holder.line = (View) convertView.findViewById(R.id.line);
            holder.layout_header = (LinearLayout) convertView.findViewById(R.id.layout_header);
            holder.listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object object = v.getTag();
                    if (object instanceof SelfRecord) {
                        SelfRecord bean = (SelfRecord) object;
                        Toast.makeText(activity, bean.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            };
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SelfRecord bean = getItem(position);
        if (position == 0) {
            holder.layout_header.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.VISIBLE);
        } else {
            holder.layout_header.setVisibility(View.GONE);
            holder.line.setVisibility(View.GONE);
        }
        if (bean.getMoneyofmonth() != null) {
            holder.tv_month_amount.setText(bean.getMoneyofmonth() + "");
        } else {
            holder.tv_month_amount.setText("无");
        }
        if (bean.getMoneyofyear() != null) {
            holder.tv_year_amount.setText(bean.getMoneyofyear() + "");
        } else {
            holder.tv_year_amount.setText("无");
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_year_amount;
        private TextView tv_month_amount;
        private LinearLayout layout_header;
        private LinearLayout item;
        private View line;

        private View.OnClickListener listener;

    }
}
