package com.jsb.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.model.Userstable;

import java.util.List;

/**
 * Created by JammyQtheLab on 2015/11/12.
 */
public class CreateTeamAdapter extends BaseAdapter {

    private static final int Data = 0;
    private static final int Header = 1;
    private static final int ViewTypeCount = 2;

    private List<Object> mDatas;
    private Activity activity;
    private ViewHolder holder;

    public CreateTeamAdapter(List<Object> mDatas, Activity activity) {
        this.mDatas = mDatas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public int getViewTypeCount() {
        return ViewTypeCount;
    }

    @Override
    public int getItemViewType(int position) {
        return (mDatas.get(position) instanceof String) ? Header
                : Data;
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
    public View getView(int position, View convertView, ViewGroup parent) {


        final int type = getItemViewType(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            final LayoutInflater inflater = LayoutInflater.from(activity);
            final int layoutID = type == Header ? R.layout.item_my_team_for_member_header : R.layout.item_create_team_data;
            convertView = inflater.inflate(layoutID, parent, false);
            if (type == Header) {
                holder.tv_type_header = (TextView) convertView.findViewById(R.id.tv_type_header);
                holder.line_top = convertView.findViewById(R.id.line_top);
                holder.line_bottom = convertView.findViewById(R.id.line_bottom);
            } else {
                holder.item = (LinearLayout) convertView.findViewById(R.id.item);
                holder.tvUserName = (TextView) convertView.findViewById(R.id.tv_user_name);
                holder.ivSelected = (ImageView) convertView.findViewById(R.id.iv_selected);
                final ViewHolder finalHolder = holder;
                holder.listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object obj = v.getTag();
                        if (obj instanceof Userstable) {
                            Userstable bean = (Userstable) obj;
                            switch (v.getId()) {
                                case R.id.item:
                                    if (bean.getSuperFlag() == null || !bean.getSuperFlag()) {
                                        bean.setSuperFlag(true);
                                        finalHolder.ivSelected.setImageResource(R.drawable.icon_choose_selected);
                                    } else {
                                        bean.setSuperFlag(false);
                                        finalHolder.ivSelected.setImageResource(R.drawable.icon_choose);
                                    }
                                   CreateTeamAdapter.this.notifyDataSetChanged();
                                    break;
                            }
                        }
                    }
                };
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (type == Header) {//如果是标题栏
            holder.tv_type_header.setText(mDatas.get(position).toString());
            if (position < 1) {
                holder.line_top.setVisibility(View.VISIBLE);
                holder.line_bottom.setVisibility(View.VISIBLE);
            } else {
                holder.line_bottom.setVisibility(View.GONE);
                holder.line_top.setVisibility(View.GONE);
            }

        } else {//如果是数据
            Object obj = mDatas.get(position);
            if (obj instanceof Userstable) {
                Userstable bean = (Userstable) obj;
                holder.tvUserName.setText(TextUtils.isEmpty(bean.getName()) ? "" : bean.getName());
                holder.item.setTag(bean);
                holder.item.setOnClickListener(holder.listener);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView tvUserName;
        private ImageView ivSelected;
        private LinearLayout item;
        private View.OnClickListener listener;


        //header
        private TextView tv_type_header;
        private View line_top;
        private View line_bottom;
    }
}
