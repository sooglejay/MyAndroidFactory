package com.jsb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.constant.StringConstant;
import com.jsb.fragment.DialogFragmentCreater;
import com.jsb.Bean.aaa_MyCallPoliceBean;
import com.jsb.ui.MyCallPoliceActivity;
import com.jsb.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MyCallPoliceListAdapter extends BaseAdapter {
    public static final int STATUS_Default = 0;
    public static final int STATUS_CALLED = 1;


    private DialogFragmentCreater mCallPoliceFragment;

    private int textColorResId_called = -1;
    private int textColorResId_uncalled = -1;//dialog 按钮文字的颜色


    private aaa_MyCallPoliceBean outerTagBean = new aaa_MyCallPoliceBean();

    public MyCallPoliceListAdapter(final Activity mContext, List<aaa_MyCallPoliceBean> datas, DialogFragmentCreater dialogFragmentCreater) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;
        this.mCallPoliceFragment = dialogFragmentCreater;

        textColorResId_called = mContext.getResources().getColor(R.color.gray_color);
        textColorResId_uncalled = mContext.getResources().getColor(R.color.light_red);

    }

    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<aaa_MyCallPoliceBean> mDatas = new ArrayList<aaa_MyCallPoliceBean>();

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public aaa_MyCallPoliceBean getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.item_my_call_police_list, null);
            holder.tv_insure_name = (TextView) convertView.findViewById(R.id.tv_insure_name);
            holder.tv_buy_insure_time = (TextView) convertView.findViewById(R.id.tv_buy_insure_time);
            holder.tv_call_police = (TextView) convertView.findViewById(R.id.tv_call_police);
            holder.tv_buy_insure_agent = (TextView) convertView.findViewById(R.id.tv_buy_insure_agent);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);

            holder.onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    outerTagBean = (aaa_MyCallPoliceBean) v.getTag();
                    mCallPoliceFragment.showDialog(mContext, DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                    mCallPoliceFragment.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals("tv_confirm")) {
                                UIUtils.takePhoneCall(mContext, StringConstant.CALL_POLICE_PHONE_NUMBER, MyCallPoliceActivity.REQUEST_CODE_CALL);
                                outerTagBean.setStatus(STATUS_CALLED);
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_content instanceof TextView) {
                                ((TextView) tv_content).setText(StringConstant.CALL_POLICE_PHONE_NUMBER);
                            }
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("太平洋保险报案电话");
                            }
                            if (tv_confirm instanceof TextView) {
                                ((TextView) tv_confirm).setText("拨打");
                            }


                        }
                    });
                }
            };
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final aaa_MyCallPoliceBean bean = getItem(position);
        holder.tv_insure_name.setText(bean.getInsureNameStr() + "");
        switch (bean.getStatus()) {
            case STATUS_Default:
                holder.tv_call_police.setTextColor(textColorResId_uncalled);
                break;
            case STATUS_CALLED:
                holder.tv_call_police.setTextColor(textColorResId_called);
                break;
            default:
                break;
        }
        holder.item.setTag(bean);
        holder.item.setOnClickListener(holder.onClickListener);
        return convertView;
    }

    /**
     * 成功报案后的弹出对话框
     */
    public void setResultDialg(DialogFragmentCreater mCallPoliceFragment) {
        mCallPoliceFragment.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
            @Override
            public void viewClick(String tag) {

            }

            @Override
            public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                if (tv_content instanceof TextView) {
                    String text = outerTagBean.getInsureNameStr() + StringConstant.TEXT_SHOW_AFTER_CALL_POLICE_SUCCESS;
                    ((TextView) tv_content).setText(text);
                }
            }

        });
        mCallPoliceFragment.showDialog(mContext, DialogFragmentCreater.DialogShowSingleChoiceDialog);

    }

    private class ViewHolder {
        TextView tv_insure_name;
        TextView tv_buy_insure_time;
        TextView tv_call_police;
        TextView tv_buy_insure_agent;
        LinearLayout item;
        View.OnClickListener onClickListener;
    }
}