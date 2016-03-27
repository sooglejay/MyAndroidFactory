package com.jiandanbaoxian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.RangeRecord;

import java.util.List;

/**
 * Created by kcg on 15-7-9.
 */
public class MyTeamForMemberAdapter extends BaseAdapter {


    private static final int Data = 0;
    private static final int Header = 1;
    private static final int ViewTypeCount = 2;

    private List<Object> mData = null;
    private Context mContext = null;

    public MyTeamForMemberAdapter(Context mContext, List<Object> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
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
        return (mData.get(position) instanceof String) ? Header
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
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            final int layoutID = type == Header ? R.layout.item_my_team_for_member_header : R.layout.item_my_team_for_member_data;
            convertView = inflater.inflate(layoutID, parent, false);
            if (type == Header) {
                holder.tv_type_header = (TextView) convertView.findViewById(R.id.tv_type_header);
            } else {
                holder.item = (LinearLayout) convertView.findViewById(R.id.item);
                holder.tvRangNumber = (TextView) convertView.findViewById(R.id.tv_rang_number);
                holder.tvUserName = (TextView) convertView.findViewById(R.id.tv_user_name);
                holder.line = convertView.findViewById(R.id.line);
                holder.tvMoney = (TextView) convertView.findViewById(R.id.tv_money);
                holder.listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object obj = v.getTag();
                        if (obj instanceof RangeRecord) {
                            RangeRecord bean = (RangeRecord) obj;
//                            Toast.makeText(mContext, bean != null ? bean.toString() : "", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
            }
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (type == Header) {//如果是标题栏
            holder.tv_type_header.setText(mData.get(position).toString());

        } else {//如果是数据
            Object obj = mData.get(position);
            if (obj instanceof RangeRecord) {
                RangeRecord bean = (RangeRecord) obj;
                holder.tvMoney.setText(bean.getMoney() + "");
                holder.tvUserName.setText(bean.getName());
                holder.tvRangNumber.setText(position + "");

                if (mData.size() > 0 && position == mData.size() - 1||position<(mData.size()-1)&&mData.get(position+1) instanceof String) {
                    holder.line.setVisibility(View.GONE);
                } else {
                    holder.line.setVisibility(View.VISIBLE);
                }
                holder.item.setTag(bean);
                holder.item.setOnClickListener(holder.listener);
            }
        }
        return convertView;
    }

    private static class ViewHolder {

        //data
        private LinearLayout item;
        private TextView tvRangNumber;
        private TextView tvUserName;
        private TextView tvMoney;
        private View.OnClickListener listener;
        private View line;


        //header
        private TextView tv_type_header;


    }
}
