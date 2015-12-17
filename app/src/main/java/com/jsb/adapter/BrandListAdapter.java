package com.jsb.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.model.Brand;
import com.jsb.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/12/16.
 */
public class BrandListAdapter extends BaseAdapter {
    private List<Brand> brandList = new ArrayList<>();

    public BrandListAdapter(List<Brand> brandList, Activity activity) {
        this.brandList = brandList;
        this.activity = activity;
    }

    private Activity activity;

    @Override
    public int getCount() {
        return brandList.size();
    }

    @Override
    public Brand getItem(int i) {
        return brandList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            view = View.inflate(activity, R.layout.item_brand_list, null);
            holder.brand_name = (TextView) view.findViewById(R.id.tv_brand_name);
            holder.imageView = (ImageView) view.findViewById(R.id.iv_selected);
            holder.item = (LinearLayout) view.findViewById(R.id.item);
            holder.listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Brand object = (Brand) view.getTag();
                    for (Brand brand : brandList) {
                        brand.setIsSelected(false);
                    }
                    object.setIsSelected(true);
                    notifyDataSetChanged();
                }
            };
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Brand brand = getItem(i);
        holder.brand_name.setText(brand.getBrand_name());

        holder.imageView.setVisibility(brand.isSelected() ? View.VISIBLE : View.GONE);

        holder.item.setTag(brand);
        holder.item.setOnClickListener(holder.listener);

        return view;
    }

    private static class ViewHolder {
        private TextView brand_name;
        private ImageView imageView;
        private LinearLayout item;
        private View.OnClickListener listener;

    }
}
