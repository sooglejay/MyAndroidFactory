package com.jsb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.fragment.DialogFragmentCreater;
import com.jsb.model.FourService;
import com.jsb.model.FreedomData;
import com.jsb.ui.me.myteam.CertificationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class TeamListAdapter extends BaseAdapter {

    public TeamListAdapter(final Activity mContext, List<FreedomData> datas) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;

    }

    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<FreedomData> mDatas = new ArrayList<>();


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public FreedomData getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.item_team_list, null);
            holder.tvLeaderTz = (TextView) convertView.findViewById(R.id.tv_leader_tz);
            holder.tvLeaderTyCount = (TextView) convertView.findViewById(R.id.tv_leader_ty_count);
            holder.tvTotalBaofei = (TextView) convertView.findViewById(R.id.tv_total_baofei);
            holder.layoutIWantToJoin = (LinearLayout) convertView.findViewById(R.id.layout_i_want_to_join);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            holder.listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object obj = v.getTag();
                    if (obj instanceof FreedomData) {
                        FreedomData data = (FreedomData) obj;
                        switch (v.getId()) {
                            case R.id.layout_i_want_to_join:
                                Toast.makeText(mContext, "我要加入！", Toast.LENGTH_SHORT).show();
                                mContext.startActivity(new Intent(mContext, CertificationActivity.class));
                                break;
                            case R.id.item:
                                Toast.makeText(mContext, "点击一条记录！", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
            };
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FreedomData bean = getItem(position);
        if (!TextUtils.isEmpty(bean.getLeaderName())) {
            holder.tvLeaderTz.setText(bean.getLeaderName());
        }
        if (bean.getAmount() != null) {
            holder.tvLeaderTyCount.setText(bean.getAmount() + "人");
        }
        if (bean.getTotalFee() != null) {
            holder.tvTotalBaofei.setText(bean.getTotalFee() + "元");
        }
        holder.item.setTag(bean);
        holder.layoutIWantToJoin.setTag(bean);
        holder.item.setOnClickListener(holder.listener);
        holder.layoutIWantToJoin.setOnClickListener(holder.listener);
        return convertView;
    }


    private class ViewHolder {
        private TextView tvLeaderTz;
        private TextView tvLeaderTyCount;
        private TextView tvTotalBaofei;
        private LinearLayout layoutIWantToJoin;
        private LinearLayout item;

        private View.OnClickListener listener;

    }
}
