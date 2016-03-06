package com.jiandanbaoxian.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.InsuranceItemData;
import com.jiandanbaoxian.model.InsuranceItemData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/12/16.
 */
public class HuaanInsuranceItemAdapter extends BaseAdapter {

    private LinkedHashMap<String, String> hashThridResponsibilityInsurance = new LinkedHashMap<>();

    java.text.NumberFormat nf = java.text.NumberFormat.getInstance();//double 不使用科学计数法
    private List<InsuranceItemData> InsuranceItemDataList = new ArrayList<>();

    public HuaanInsuranceItemAdapter(List<InsuranceItemData> InsuranceItemDataList, Activity activity) {
        this.InsuranceItemDataList = InsuranceItemDataList;
        this.activity = activity;
        nf.setGroupingUsed(false);

        hashThridResponsibilityInsurance.put("306006004", "5万");
        hashThridResponsibilityInsurance.put("306006005", "10万");
        hashThridResponsibilityInsurance.put("306006018", "15万");
        hashThridResponsibilityInsurance.put("306006006", "20万");
        hashThridResponsibilityInsurance.put("306006007", "30万");
        hashThridResponsibilityInsurance.put("306006009", "50万");
        hashThridResponsibilityInsurance.put("306006014", "100万");
        hashThridResponsibilityInsurance.put("306006019", "150万");
        hashThridResponsibilityInsurance.put("306006020", "200万");
        hashThridResponsibilityInsurance.put("306006021", "250万");
        hashThridResponsibilityInsurance.put("306006022", "300万");
        hashThridResponsibilityInsurance.put("306006023", "350万");
        hashThridResponsibilityInsurance.put("306006024", "400万");
        hashThridResponsibilityInsurance.put("306006025", "450万");
        hashThridResponsibilityInsurance.put("306006026", "500万");


        hashThridResponsibilityInsurance.put("365001", "2千");
        hashThridResponsibilityInsurance.put("365002", "5千");
        hashThridResponsibilityInsurance.put("365003", "1万");
        hashThridResponsibilityInsurance.put("365004", "2万");

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
            holder.header = (LinearLayout) view.findViewById(R.id.header);
            holder.tvInsuranceAmt = (TextView) view.findViewById(R.id.tv_insurance_amt);
            holder.tvInsurancePremium = (TextView) view.findViewById(R.id.tv_insurance_premium);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (i > 0) {
            holder.header.setVisibility(View.GONE);
        } else {
            holder.header.setVisibility(View.VISIBLE);
        }
        InsuranceItemData InsuranceItemData = getItem(i);
        holder.tvInsuranceName.setText(InsuranceItemData.getInsrnc_name());

        StringBuilder amt;
        try {
            amt = new StringBuilder(nf.format(InsuranceItemData.getAmt()) + "");
            if (hashThridResponsibilityInsurance.containsKey(amt.toString())) {
                amt = new StringBuilder(hashThridResponsibilityInsurance.get(amt.toString()));
            }
        } catch (Exception e) {
            amt = new StringBuilder(InsuranceItemData.getAmt() + "");
        }
        holder.tvInsuranceAmt.setText(amt.toString());
        holder.tvInsurancePremium.setText(StringConstant.RMB + "" + InsuranceItemData.getPremium() + "");
        return view;
    }

    private static class ViewHolder {
        private TextView tvInsuranceName;
        private TextView tvInsuranceAmt;
        private TextView tvInsurancePremium;
        private LinearLayout header;
    }
}
