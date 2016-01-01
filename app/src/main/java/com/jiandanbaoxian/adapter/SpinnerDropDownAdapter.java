package com.jiandanbaoxian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jiandanbaoxian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/18.
 */
public class SpinnerDropDownAdapter extends BaseAdapter implements SpinnerAdapter {
    Context context;
    private ViewHolder holder;

    public SpinnerDropDownAdapter(Context ctx, List<String> mDatasStringList) {
        context = ctx;
        this.mDatasStringList = mDatasStringList;
    }

    private List<String> mDatasStringList = new ArrayList<>();

    @Override
    public int getCount() {
        return mDatasStringList.size();
    }

    @Override
    public String getItem(int pos) {
        // TODO Auto-generated method stub
        return mDatasStringList.get(pos);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        holder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_text_view, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(mDatasStringList.get(position) + "");
        return convertView;

    }

    //如果不实现这个方法，那么会怎么样呢？
//    @Override
//    public View getDropDownView(int position, View convertView,
//                                ViewGroup parent) {
//        TextView text = new TextView(context);
//        text.setTextColor(Color.BLACK);
//        text.setText(value[position]);
//        return text;
//    }
    private static class ViewHolder {
        TextView tvName;
    }
}
