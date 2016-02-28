package com.jiandanbaoxian.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.model.InsuranceItemData;
import com.jiandanbaoxian.model.InsuranceItemData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/12/16.
 */
public class HuaanInsuranceItemAdapter extends BaseAdapter {


    java.text.NumberFormat nf = java.text.NumberFormat.getInstance();//double 不使用科学计数法
    private List<InsuranceItemData> InsuranceItemDataList = new ArrayList<>();

    public HuaanInsuranceItemAdapter(List<InsuranceItemData> InsuranceItemDataList, Activity activity) {
        this.InsuranceItemDataList = InsuranceItemDataList;
        this.activity = activity;
        nf.setGroupingUsed(false);

    }

    private Activity activity;

    @Override
    public int getCount() {
        return InsuranceItemDataList.size();
    }

    @Override
    public InsuranceItemData getItem(int i) {
        return InsuranceItemDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            view = View.inflate(activity, R.layout.item_huaan_insurance_item, null);
            holder.tvInsuranceName = (TextView) view.findViewById(R.id.tv_insurance_name);
            holder.tvInsuranceAmt = (TextView) view.findViewById(R.id.tv_insurance_amt);
            holder.tvInsurancePremium = (TextView) view.findViewById(R.id.tv_insurance_premium);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        InsuranceItemData InsuranceItemData = getItem(i);
        holder.tvInsuranceName.setText(InsuranceItemData.getInsrnc_name());
        holder.tvInsuranceAmt.setText(nf.format(InsuranceItemData.getAmt()) + "");
        holder.tvInsurancePremium.setText(InsuranceItemData.getPremium() + "");
        return view;
    }
    private static class ViewHolder {
        private TextView tvInsuranceName;
        private TextView tvInsuranceAmt;
        private TextView tvInsurancePremium;
    }
}
