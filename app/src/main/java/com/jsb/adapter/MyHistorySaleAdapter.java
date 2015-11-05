package com.jsb.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;

import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.Vechicleinsurancedetail;
import com.jsb.model.Vehicleordertable;

import com.jsb.ui.HistoryPriceDetailActivity;
import com.jsb.util.TimeUtil;
import com.jsb.util.UIUtils;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MyHistorySaleAdapter extends BaseAdapter {
    public static final int GONE_UNSELECTED = 0;//默认
    public static final int VISIBLE_UNSELECTED = 1;
    public static final int VISIBLE_SELECTED = 2;


    private View footerView;
    private View listView;
    private RelativeLayout.LayoutParams layoutParams;

    private TitleBar mTitleBar;

    public MyHistorySaleAdapter(Activity mContext, List<Vehicleordertable> datas, View footerView, View listView,TitleBar mTitleBar) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;
        this.footerView = footerView;
        this.listView = listView;
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.mTitleBar =mTitleBar;
    }

    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<Vehicleordertable> mDatas = new ArrayList<>();


    /**
     * @param isDelete 删除 或者 取消
     */
    public void deleteItem(boolean isDelete) {
        if (isDelete) {
            final Iterator keys = mDatas.iterator();
            while (keys.hasNext()) {
                final Vehicleordertable bean = (Vehicleordertable) keys.next();
                if (bean.getSuper_status() == VISIBLE_SELECTED && bean.getId() != null) {
                    UserRetrofitUtil.deleteHistoryPrice(mContext, bean.getId(), new NetCallback<NetWorkResultBean<String>>(mContext) {
                        @Override
                        public void onFailure(RetrofitError error) {
                            if (bean.getInsuranceDetail() != null) {
                                Toast.makeText(mContext, bean.getInsuranceDetail().getInsurancename() + "删除失败！", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                            keys.remove();
                            if (bean.getInsuranceDetail() != null) {
                                Toast.makeText(mContext, bean.getInsuranceDetail().getInsurancename() + "删除成功！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            for (Vehicleordertable bean : mDatas) {
                bean.setSuper_status(GONE_UNSELECTED);
            }
        } else {
            for (Vehicleordertable bean : mDatas) {
                bean.setSuper_status(GONE_UNSELECTED);
            }
        }


        layoutParams.setMargins(0, (int) UIUtils.dp2px(mContext, 56), 0, 0);
        listView.setLayoutParams(layoutParams);
        footerView.setVisibility(View.GONE);
        mTitleBar.setRightTv("",-1);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public void notifyDataSetChanged() {
        final Iterator keys = mDatas.iterator();
        while (keys.hasNext()) {
            final Vehicleordertable bean = (Vehicleordertable) keys.next();
            if (bean.getDeleted() != null && bean.getDeleted() == 1) {
                keys.remove();
            }
        }
        super.notifyDataSetChanged();
    }

    @Override
    public Vehicleordertable getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.item_my_history_sale_adapter, null);
            holder.tv_insure_name = (TextView) convertView.findViewById(R.id.tv_insure_name);
            holder.tv_buy_insure_time = (TextView) convertView.findViewById(R.id.tv_buy_insure_time);
            holder.tv_insure_price = (TextView) convertView.findViewById(R.id.tv_insure_price);
            holder.tv_buy_insure_agent = (TextView) convertView.findViewById(R.id.tv_buy_insure_agent);
            holder.iv_choose = (ImageView) convertView.findViewById(R.id.iv_choose);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Vehicleordertable bean = getItem(position);

        Vechicleinsurancedetail vechicleinsurancedetail = bean.getInsuranceDetail();
        if (vechicleinsurancedetail != null) {
            holder.tv_insure_name.setText(bean.getInsuranceDetail().getInsurancename());
        } else {
            holder.tv_insure_name.setText("null");
        }

        if (bean.getOrderdate() != null) {
            holder.tv_buy_insure_time.setText(TimeUtil.getTimeString(bean.getOrderdate()) + "");
        } else {
            holder.tv_buy_insure_time.setText("null");
        }
        if (bean.getMoney() != null) {
            holder.tv_insure_price.setText(bean.getMoney() + "");
        } else {
            holder.tv_insure_price.setText("null");
        }

        if (bean.getInsurancecompanyprices() != null && bean.getInsurancecompanyprices().getCompany() != null) {
            holder.tv_buy_insure_agent.setText(bean.getInsurancecompanyprices().getCompany().getCompanyname());
        } else {
            holder.tv_buy_insure_agent.setText("null");
        }

        switch (bean.getSuper_status()) {
            case GONE_UNSELECTED:
                holder.iv_choose.setImageResource(R.drawable.icon_choose);
                holder.iv_choose.setVisibility(View.GONE);
                break;
            case VISIBLE_UNSELECTED:
                holder.iv_choose.setImageResource(R.drawable.icon_choose);
                holder.iv_choose.setVisibility(View.VISIBLE);
                break;
            case VISIBLE_SELECTED:
                holder.iv_choose.setImageResource(R.drawable.icon_choose_selected);
                holder.iv_choose.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vehicleordertable tagBean = (Vehicleordertable) v.getTag();
                switch (tagBean.getSuper_status()) {
                    case GONE_UNSELECTED:
                        HistoryPriceDetailActivity.startActivity(mContext,null);
                        break;
                    case VISIBLE_UNSELECTED:
                        tagBean.setSuper_status(VISIBLE_SELECTED);
                        break;
                    case VISIBLE_SELECTED:
                        tagBean.setSuper_status(VISIBLE_UNSELECTED);
                        break;
                }
                notifyDataSetChanged();
            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Vehicleordertable tagBean = (Vehicleordertable) v.getTag();
                if (tagBean.getSuper_status() > 0)//如果长按时，已经有item显示了 checkBox，则隐藏
                {
                    footerView.setVisibility(View.GONE);
                    mTitleBar.setRightTv("", -1);
                    layoutParams.setMargins(0, (int) UIUtils.dp2px(mContext, 56), 0, 0);
                    listView.setLayoutParams(layoutParams);
                    for (Vehicleordertable bean : mDatas) {
                        bean.setSuper_status(GONE_UNSELECTED);
                    }
                } else {//否则，就设置为 ：显示+未选中 状态
                    footerView.setVisibility(View.VISIBLE);
                    mTitleBar.setRightTv("全选",-1);
                    layoutParams.setMargins(0, (int) UIUtils.dp2px(mContext, 56), 0, (int) UIUtils.dp2px(mContext, 48));
                    listView.setLayoutParams(layoutParams);
                    for (Vehicleordertable bean : mDatas) {
                        bean.setSuper_status(VISIBLE_UNSELECTED);
                    }
                }
                notifyDataSetChanged();
                return false;
            }
        });
        holder.item.setTag(bean);
        return convertView;
    }

    private class ViewHolder {
        TextView tv_insure_name;
        TextView tv_buy_insure_time;
        TextView tv_insure_price;
        TextView tv_buy_insure_agent;
        ImageView iv_choose;
        LinearLayout item;
    }

}
