package com.jiandanbaoxian.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;

import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.IntConstant;
import com.jiandanbaoxian.event.BusEvent;
import com.jiandanbaoxian.model.CommPriceData;
import com.jiandanbaoxian.model.HistoryPriceData;
import com.jiandanbaoxian.model.HuanPriceData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Vehicleordertable;

import com.jiandanbaoxian.ui.me.historyprice.MyHistoryPriceItemActivity;
import com.jiandanbaoxian.util.JsonUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.util.TimeUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.greenrobot.event.EventBus;
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
    private ProgressDialogUtil progressDialogUtil;

    public MyHistorySaleAdapter(Activity mContext, List<Vehicleordertable> datas, View footerView, View listView, TitleBar mTitleBar, ProgressDialogUtil progressDialogUtil) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;
        this.footerView = footerView;
        this.listView = listView;
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.mTitleBar = mTitleBar;
        this.progressDialogUtil = progressDialogUtil;
    }

    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<Vehicleordertable> mDatas = new ArrayList<>();

    private int deleteNum = 0;//删除的记录个数

    /**
     * @param isDelete 删除 或者 取消
     */
    public void deleteItem(boolean isDelete) {
        if (isDelete) {
            progressDialogUtil.show("正在处理...");
            deleteNum = 0;
            for (final Vehicleordertable bean : mDatas) {
                deleteNum = deleteNum + 1;
                if (bean.getSuper_status() == VISIBLE_SELECTED && bean.getId() != null) {
                    UserRetrofitUtil.deleteHistoryPrice(mContext, bean.getId(), new NetCallback<NetWorkResultBean<Object>>(mContext) {
                        @Override
                        public void onFailure(RetrofitError error, String message) {
                            progressDialogUtil.hide();
                            Toast.makeText(mContext, "请检查网络设置", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void success(NetWorkResultBean<Object> stringNetWorkResultBean, Response response) {

                            progressDialogUtil.hide();

                            if (stringNetWorkResultBean != null) {
                                int httpStatus = stringNetWorkResultBean.getStatus();
                                switch (httpStatus) {
                                    case HttpURLConnection.HTTP_OK:

                                        bean.setDeleted(IntConstant.DELETE);
                                        if (bean.getInsuranceDetail() != null) {
                                            Toast.makeText(mContext, bean.getInsuranceDetail().getInsurancename() + "删除成功！", Toast.LENGTH_SHORT).show();
                                        }
                                        if (deleteNum == mDatas.size()) {
                                            EventBus.getDefault().post(new BusEvent(BusEvent.MSG_RefreshDataInHistoryPrice));
                                        }

                                        break;
                                    default:
                                        Toast.makeText(mContext, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                }
            }
            progressDialogUtil.hide();
        } else {
            for (Vehicleordertable bean : mDatas) {
                bean.setSuper_status(GONE_UNSELECTED);
            }
        }
        layoutParams.setMargins(0, (int) UIUtils.dp2px(mContext, 56), 0, 0);
        listView.setLayoutParams(layoutParams);
        footerView.setVisibility(View.GONE);
        mTitleBar.setRightTv("", -1);
        this.notifyDataSetChanged();
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
            //0 表示 已经被删除，1表示未删除
            if (bean.getDeleted() != null && bean.getDeleted() == IntConstant.DELETE) {
                keys.remove();
                Log.e("jwjw", "删除成功！");
            } else {
                Log.e("jwjw", "删除shibai！" + bean.toString());
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
        Integer insurancetype = bean.getInsurancetype();
        if (insurancetype != null) {
            switch (insurancetype) {
                case 0:
                    holder.tv_insure_name.setText("商业险");
                    break;
                case 1:
                    holder.tv_insure_name.setText("交强险");
                    break;
                default:
                    holder.tv_insure_name.setText("车险");
                    break;
            }
        } else {
            holder.tv_insure_name.setText("车险(服务端字段是NULL)");
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

        if (bean.getCompanyInfo() != null) {
            holder.tv_buy_insure_agent.setText(bean.getCompanyInfo().getCompanyname());
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
                bean.setSuper_status(GONE_UNSELECTED);
                holder.iv_choose.setImageResource(R.drawable.icon_choose);
                holder.iv_choose.setVisibility(View.GONE);
                break;
        }
//        qwqw
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vehicleordertable tagBean = (Vehicleordertable) v.getTag();
                switch (tagBean.getSuper_status()) {
                    case GONE_UNSELECTED:
                        if (tagBean.getOrdernum() != null) {
                            UserRetrofitUtil.getHistoryPriceDetail(mContext, Integer.valueOf(tagBean.getOrdernum()), new NetCallback<NetWorkResultBean<Object>>(mContext) {
                                @Override
                                public void onFailure(RetrofitError error, String message) {

                                }

                                @Override
                                public void success(NetWorkResultBean<Object> objectNetWorkResultBean, Response response) {
                                    if (objectNetWorkResultBean != null) {
                                        int status = objectNetWorkResultBean.getStatus();
                                        switch (status) {
                                            case 200://请求成功！
                                                HistoryPriceData bean = JsonUtil.getSerializedObject(objectNetWorkResultBean.getData(), HistoryPriceData.class);
                                                if (bean != null && bean.getVehicleorderDetail() != null) {
                                                    Vehicleordertable vehicleordertable = bean.getVehicleorderDetail();





                                                    if (vehicleordertable.getPayed() != null) {
                                                        int payed = vehicleordertable.getPayed();
                                                        if (vehicleordertable.getInsurancetype() != null) {
                                                            int insurancetype = vehicleordertable.getInsurancetype();
                                                            CommPriceData commPriceData;
                                                            HuanPriceData huanPriceData=new HuanPriceData();
                                                            String  idcard_number;
                                                            String  city_no;
                                                            String  country_no;
                                                            String  province_no;
                                                            long  compulsorystartdate;
                                                            long  commercestartdate;
                                                            try {
                                                                idcard_number = vehicleordertable.getIdcardnum();
                                                                if (insurancetype==1){
                                                                    compulsorystartdate = vehicleordertable.getStartdate();
                                                                    commercestartdate = 0 ;
//                                                                    huanPriceData.setCompulsoryAmount(vehicleordertable.get);
                                                                }else {
                                                                    commercestartdate = vehicleordertable.getStartdate();
                                                                    compulsorystartdate = 0 ;
                                                                }
                                                            }catch (Exception e){

                                                            }
                                                            switch (payed) {
                                                                case 0://未支付


                                                                    break;
                                                                case 1://已支付
                                                                    break;
                                                            }
                                                        }
                                                    }

                                                    Toast.makeText(mContext, "success:" + objectNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();


                                                }
                                                break;
                                            default:
                                                Toast.makeText(mContext, objectNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();

                                                break;

                                        }
                                    }

                                }
                            });
                        } else {

                        }
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
                    mTitleBar.setRightTv("全选", -1);
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
