package com.jiandanbaoxian.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.model.Driverordertable;
import com.jiandanbaoxian.model.Overtimeordertable;
import com.jiandanbaoxian.model.Vehicleordertable;
import com.jiandanbaoxian.ui.me.myinsurance.MyInsuresListCarInsureDetailActivity;
import com.jiandanbaoxian.ui.me.myinsurance.MyInsuresListDrivingInsureDetailActivity;
import com.jiandanbaoxian.ui.me.myinsurance.MyInsuresListJiaBanDogInsureDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MyInsuresListAdapter extends BaseAdapter {

    private SimpleDateFormat dateFormat_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");

    private List<Object> mDatas = new ArrayList<>();

    private List<Vehicleordertable> vehicleordertables = new ArrayList<>();
    private List<Driverordertable> driverordertables = new ArrayList<>();
    private List<Overtimeordertable> overtimeordertables = new ArrayList<>();

    public MyInsuresListAdapter(Activity mContext, List<Object> datas, List<Vehicleordertable> vehicleordertables, List<Driverordertable> driverordertables, List<Overtimeordertable> overtimeordertables) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;
        this.vehicleordertables = vehicleordertables;
        this.overtimeordertables = overtimeordertables;
        this.driverordertables = driverordertables;
    }

    @Override
    public void notifyDataSetChanged() {

        mDatas.clear();

        int size_vehicleordertables = vehicleordertables.size();
        int size_driverordertables = driverordertables.size();
        int size_overtimeordertables = overtimeordertables.size();

        //顺序为:车险-驾驶险-加班险

        //车险-驾驶险-加班险
        int i = 0;
        for (; i < size_vehicleordertables; i++) {
            if (size_vehicleordertables < 1)
                break;
            mDatas.add(vehicleordertables.get(i));//车险直到加载完为止
            if (i < size_driverordertables) {
                mDatas.add(driverordertables.get(i));//然后依次加载驾驶险
            }
            if (i < size_overtimeordertables) {
                mDatas.add(overtimeordertables.get(i));//然后依次加载加班险
            }
        }

        //驾驶险-加班险
        int j = i;
        for (; j < size_driverordertables; j++) {
            if (size_driverordertables < 1)
                break;
            mDatas.add(driverordertables.get(j));//驾驶险直到加载完为止
            if (j < size_overtimeordertables) {
                mDatas.add(overtimeordertables.get(j));//然后依次加载加班险
            }
        }

        int k = j;
        for (; k < size_overtimeordertables; k++) {
            mDatas.add(overtimeordertables.get(k));//加班险直到加载完为止
        }
        super.notifyDataSetChanged();
    }


    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.item_my_insure_list, null);
            holder.tv_insure_name = (TextView) convertView.findViewById(R.id.tv_insure_name);
            holder.tv_buy_insure_time = (TextView) convertView.findViewById(R.id.tv_buy_insure_time);
            holder.tv_insure_price = (TextView) convertView.findViewById(R.id.tv_insure_price);
            holder.tv_buy_insure_agent = (TextView) convertView.findViewById(R.id.tv_buy_insure_agent);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Object obj = mDatas.get(position);
        Log.e("jwjw", "position:" + position + "    size:" + mDatas.size() + "    obj.toString() " + obj.toString());
        if (obj instanceof Vehicleordertable) {
            final Vehicleordertable bean = (Vehicleordertable) obj;
            if (bean.getInsuranceDetail() != null && bean.getInsuranceDetail().getInsurancename() != null) {
                holder.tv_insure_name.setText(bean.getInsuranceDetail().getInsurancename() + "");
            } else {
                holder.tv_insure_name.setText("null");
            }
            if (bean.getInsuranceDetail() != null && bean.getInsuranceDetail().getFee() != null) {
                holder.tv_insure_price.setText(bean.getInsuranceDetail().getFee() + "");
            } else {
                holder.tv_insure_price.setText("null");
            }
            if (bean.getInsurancecompanyprices() != null && bean.getInsurancecompanyprices().getCompany() != null && bean.getInsurancecompanyprices().getCompany().getCompanyname() != null) {
                holder.tv_buy_insure_agent.setText(bean.getInsurancecompanyprices().getCompany().getCompanyname() + "");
            } else {
                holder.tv_buy_insure_agent.setText("null");
            }
            if (bean.getInsurancecompanyprices() != null && bean.getInsurancecompanyprices().getDate() != null) {
                holder.tv_buy_insure_time.setText(dateFormat_yyyy_mm_dd.format(bean.getInsurancecompanyprices().getDate()) + "");
            } else {
                holder.tv_buy_insure_time.setText("null");
            }
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyInsuresListCarInsureDetailActivity.startActivity(mContext, bean);
                }
            });

        } else if (obj instanceof Driverordertable) {
            final Driverordertable bean = (Driverordertable) obj;
            holder.tv_insure_name.setText("驾驶险");
            if (bean.getMoney() != null) {
                holder.tv_insure_price.setText(bean.getMoney() + "");
            } else {
                holder.tv_insure_price.setText("null");
            }
            if (bean.getCompanyInfo() != null && bean.getCompanyInfo().getCompanyname() != null) {
                holder.tv_buy_insure_agent.setText(bean.getCompanyInfo().getCompanyname() + "");
            } else {
                holder.tv_buy_insure_agent.setText("null");
            }
            if (bean.getBuydate() != null) {
                holder.tv_buy_insure_time.setText(dateFormat_yyyy_mm_dd.format(bean.getBuydate()) + "");
            } else {
                holder.tv_buy_insure_time.setText("null");
            }
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyInsuresListDrivingInsureDetailActivity.startActivity(mContext, bean);
                }
            });

        } else if (obj instanceof Overtimeordertable) {
            final Overtimeordertable bean = (Overtimeordertable) obj;
            holder.tv_insure_name.setText("加班险");
            holder.tv_insure_price.setText(bean.getMoney() + "");
            holder.tv_buy_insure_agent.setText("万保易");
            if (bean.getStartdate() != null) {
                holder.tv_buy_insure_time.setText(dateFormat_yyyy_mm_dd.format(bean.getStartdate()) + "");
            } else {
                holder.tv_buy_insure_time.setText("null");
            }
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyInsuresListJiaBanDogInsureDetailActivity.startActivity(mContext, bean);
                }
            });
        } else {
            Log.e("jwjw", "没有任何类型 :" + obj.toString());
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_insure_name;
        TextView tv_buy_insure_time;
        TextView tv_insure_price;
        TextView tv_buy_insure_agent;
        LinearLayout item;
    }
}
