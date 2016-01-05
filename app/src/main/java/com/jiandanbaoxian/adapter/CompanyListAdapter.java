package com.jiandanbaoxian.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.model.InsuranceCompanyInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/12/16.
 */
public class CompanyListAdapter extends BaseAdapter {
    private List<InsuranceCompanyInfo> companyList = new ArrayList<>();

    public CompanyListAdapter(List<InsuranceCompanyInfo> companyList, Activity activity) {
        this.companyList = companyList;
        this.activity = activity;
    }

    private Activity activity;

    @Override
    public int getCount() {
        return companyList.size();
    }

    @Override
    public InsuranceCompanyInfo getItem(int i) {
        return companyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            view = View.inflate(activity, R.layout.item_company_list, null);
            holder.company_name = (TextView) view.findViewById(R.id.tv_company_name);
            holder.imageView = (ImageView) view.findViewById(R.id.iv_selected);
            holder.item = (LinearLayout) view.findViewById(R.id.item);
            holder.listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InsuranceCompanyInfo object = (InsuranceCompanyInfo) view.getTag();
                    for (InsuranceCompanyInfo companyInfo : companyList) {
                        companyInfo.setIsSelected(false);
                    }
                    object.setIsSelected(true);
                    notifyDataSetChanged();
                }
            };
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        InsuranceCompanyInfo companyInfo = getItem(i);
        holder.company_name.setText(companyInfo.getCompanyname());

        holder.imageView.setVisibility(companyInfo.isSelected() ? View.VISIBLE : View.GONE);

        holder.item.setTag(companyInfo);
        holder.item.setOnClickListener(holder.listener);

        return view;
    }

    private static class ViewHolder {
        private TextView company_name;
        private ImageView imageView;
        private LinearLayout item;
        private View.OnClickListener listener;

    }
}
