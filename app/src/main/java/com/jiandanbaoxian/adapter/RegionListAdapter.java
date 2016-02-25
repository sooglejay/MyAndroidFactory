package com.jiandanbaoxian.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.model.Brand;
import com.jiandanbaoxian.model.RegionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/12/16.
 */
public class RegionListAdapter extends BaseAdapter {
    private List<RegionBean> regionBeanArrayList = new ArrayList<>();

    public RegionListAdapter(List<RegionBean> regionBeanArrayList, Context activity) {
        this.regionBeanArrayList = regionBeanArrayList;
        this.activity = activity;
    }

    private Context activity;

    @Override
    public int getCount() {
        return regionBeanArrayList.size();
    }

    @Override
    public RegionBean getItem(int i) {
        return regionBeanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            view = View.inflate(activity, R.layout.item_region_list, null);
            holder.brand_name = (TextView) view.findViewById(R.id.tv_region_name);
            holder.item = (LinearLayout) view.findViewById(R.id.item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        RegionBean brand = getItem(i);
        holder.brand_name.setText(brand.getRegionName());
        return view;
    }

    private static class ViewHolder {
        private TextView brand_name;
        private LinearLayout item;


    }
}
