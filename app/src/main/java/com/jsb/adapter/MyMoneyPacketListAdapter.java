package com.jsb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsb.Bean.aaa_MyMoneyPocketBean;
import com.jsb.R;
import com.jsb.constant.StringConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MyMoneyPacketListAdapter extends BaseAdapter {

    public MyMoneyPacketListAdapter(final Activity mContext, List<aaa_MyMoneyPocketBean> datas ) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;

    }

    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<aaa_MyMoneyPocketBean> mDatas = new ArrayList<aaa_MyMoneyPocketBean>();

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public aaa_MyMoneyPocketBean getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.item_my_money_pocket_list, null);
            holder.tv_money_amount = (TextView) convertView.findViewById(R.id.tv_money_amount);
            holder.tv_money_kind = (TextView) convertView.findViewById(R.id.tv_money_kind);
            holder.tv_pull_money = (TextView) convertView.findViewById(R.id.tv_pull_money);

            holder.item = (LinearLayout) convertView.findViewById(R.id.item);

            holder.onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            };
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final aaa_MyMoneyPocketBean bean = getItem(position);
        holder.tv_money_kind.setText(bean.getMoneyKind());
        holder.tv_money_amount.setText(StringConstant.RMB+bean.getMoneyAmount()+"");
        holder.item.setTag(bean);
        holder.item.setOnClickListener(holder.onClickListener);
        return convertView;
    }

    private class ViewHolder {
        TextView tv_money_amount;
        TextView tv_money_kind;
        TextView tv_pull_money;
        LinearLayout item;
        View.OnClickListener onClickListener;
    }
}
