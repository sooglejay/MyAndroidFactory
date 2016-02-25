package com.jiandanbaoxian.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.VehicleTypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/12/16.
 */
public class VehicleTypeInfoAdapter extends BaseAdapter {
    private List<VehicleTypeInfo> vehicleTypeInfos = new ArrayList<>();

    public VehicleTypeInfoAdapter(List<VehicleTypeInfo> brandList, Activity activity) {
        this.vehicleTypeInfos = brandList;
        this.activity = activity;
    }

    private Activity activity;

    @Override
    public int getCount() {
        return vehicleTypeInfos.size();
    }

    @Override
    public VehicleTypeInfo getItem(int i) {
        return vehicleTypeInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            view = View.inflate(activity, R.layout.item_car_model_list, null);
            holder.tv_car_des = (TextView) view.findViewById(R.id.tv_car_des);
            holder.imageView = (ImageView) view.findViewById(R.id.iv_selected);
            holder.item = (LinearLayout) view.findViewById(R.id.item);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        VehicleTypeInfo brand = getItem(i);
        holder.tv_car_des.setText(brand.getModel_name()+" "
                + brand.getRisk_name() + " "
                + brand.getCar_sort()  + " "
                + brand.getCar_style() + " "
                + StringConstant.RMB   + " "
                +brand.getCar_price());
        holder.imageView.setBackgroundResource(brand.isSelected() ? R.drawable.icon_choose_selected : R.drawable.icon_choose);

        return view;
    }

    private static class ViewHolder {
        private TextView tv_car_des;
        private ImageView imageView;
        private LinearLayout item;
        private View.OnClickListener listener;

    }


}
