package com.jsb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsb.R;

import java.util.List;

/**
 * Created by kcg on 15-7-9.
 */
public class DetailCarInsureTab3ServiceStationAdapter extends BaseAdapter {


    private static final int Data = 0;
    private static final int Header = 1;
    private static final int ViewTypeCount = 2;

    private List<Object> mData = null;
    private Context mContext = null;

    public DetailCarInsureTab3ServiceStationAdapter(Context mContext, List<Object> mData) {
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
            final int layoutID = type == Header ? R.layout.item_detail_car_insure_tab_1_service_station_header : R.layout.item_detail_car_insure_tab_1_service_station_data;
            convertView = inflater.inflate(layoutID, parent, false);
            if (type == Header) {
                holder.tv_type_header = (TextView) convertView.findViewById(R.id.tv_type_header);
            } else {
                holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
                holder.tv_shop_address = (TextView) convertView.findViewById(R.id.tv_shop_address);
                holder.item = (LinearLayout)convertView.findViewById(R.id.item);
            }
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (type == Header) {//如果是标题栏
            holder.tv_type_header.setText(mData.get(position).toString());
        }else  {//如果是数据
            holder.tv_shop_name.setText("长城汽车建国店");
            holder.tv_shop_address.setText("一环路南四段168号/110120119");
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView tv_type_header;
        TextView tv_shop_name;
        TextView tv_shop_address;
        LinearLayout item;
    }
}
