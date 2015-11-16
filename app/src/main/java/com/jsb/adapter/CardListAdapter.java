package com.jsb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.model.FinancialAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class CardListAdapter extends BaseAdapter {
    public CardListAdapter(Context mContext, List<FinancialAccount> datas) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    private Context mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<FinancialAccount> mDatas = new ArrayList<>();

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public FinancialAccount getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.item_card_list, null);
            holder.tvCardName = (TextView) convertView.findViewById(R.id.tv_card_name);
            holder.iv_choose = (ImageView) convertView.findViewById(R.id.iv_choose);
            holder.tv_card_end_number = (TextView) convertView.findViewById(R.id.tv_card_end_number);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FinancialAccount bean = getItem(position);
        if (bean.getBank_name() != null) {
            holder.tvCardName.setText(bean.getBank_name() + "");
        } else {
            holder.tvCardName.setText("");
        }
        if (bean.getAccount_num() != null) {
            String numStr = bean.getAccount_num();
            String endNumStr = bean.getAccount_num().substring(numStr.length() - getEndNum(numStr.length()), numStr.length());
            holder.tv_card_end_number.setText("尾号" + endNumStr);
        }

        holder.iv_choose.setVisibility(bean.getSuperFlag() ? View.VISIBLE : View.GONE);
        return convertView;
    }

    private class ViewHolder {
        TextView tvCardName;
        TextView tv_card_end_number;
        ImageView iv_choose;
    }

    private static final int getEndNum(int length) {
        if (length > 5) {
            return 4;
        } else if (length >= 2) {
            return 1;
        } else {
            return 0;
        }

    }
}
