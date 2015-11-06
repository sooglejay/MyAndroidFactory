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
import com.jsb.model.Driverordertable;
import com.jsb.model.Overtimeordertable;
import com.jsb.model.Vehicleordertable;
import com.jsb.ui.MyCallPoliceActivity;
import com.jsb.util.TimeUtil;
import com.jsb.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MyCallPoliceListAdapter extends BaseAdapter {
    private DialogFragmentCreater mCallPoliceFragment;

    private Object outerTagBean;

    public MyCallPoliceListAdapter(final Activity mContext, List<Object> datas, DialogFragmentCreater dialogFragmentCreater) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;
        this.mCallPoliceFragment = dialogFragmentCreater;
    }

    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<Object> mDatas = new ArrayList<>();

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
            convertView = View.inflate(mContext, R.layout.item_my_call_police_list, null);
            holder.tv_insure_name = (TextView) convertView.findViewById(R.id.tv_insure_name);
            holder.tv_buy_insure_time = (TextView) convertView.findViewById(R.id.tv_buy_insure_time);
            holder.tv_call_police = (TextView) convertView.findViewById(R.id.tv_call_police);
            holder.tv_buy_insure_agent = (TextView) convertView.findViewById(R.id.tv_buy_insure_agent);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            holder.onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    outerTagBean = v.getTag();
                    if (outerTagBean instanceof Vehicleordertable) {
                        final Vehicleordertable bean = (Vehicleordertable) outerTagBean;
                        mCallPoliceFragment.showDialog(mContext, DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                        mCallPoliceFragment.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                            @Override
                            public void viewClick(String tag) {
                                if (tag.equals("tv_confirm")) {
                                    if (bean.getCompanyInfo() != null) {
                                        String phoneNumber = bean.getCompanyInfo().getCompanyphone();
                                        UIUtils.takePhoneCall(mContext, phoneNumber, MyCallPoliceActivity.REQUEST_CODE_CALL);
                                    }
                                }
                            }

                            @Override
                            public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                                if (tv_content instanceof TextView) {
                                    ((TextView) tv_content).setText(StringConstant.CALL_POLICE_PHONE_NUMBER);
                                }
                                if (tv_title instanceof TextView) {
                                    if (bean.getInsuranceDetail() != null) {
                                        ((TextView) tv_title).setText(bean.getInsuranceDetail().getInsurancename());
                                    }
                                    ((TextView) tv_title).append("报案电话");
                                }
                                if (tv_confirm instanceof TextView) {
                                    ((TextView) tv_confirm).setText("拨打");
                                }
                            }
                        });
                    } else if (outerTagBean instanceof Driverordertable) {
                        final Driverordertable bean = (Driverordertable) outerTagBean;
                        mCallPoliceFragment.showDialog(mContext, DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                        mCallPoliceFragment.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                            @Override
                            public void viewClick(String tag) {
                                if (tag.equals("tv_confirm")) {
                                    if (bean.getCompanyInfo() != null) {
                                        String phoneNumber = bean.getCompanyInfo().getCompanyphone();
                                        UIUtils.takePhoneCall(mContext, phoneNumber, MyCallPoliceActivity.REQUEST_CODE_CALL);
                                    }
                                }
                            }

                            @Override
                            public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                                if (tv_content instanceof TextView) {
                                    ((TextView) tv_content).setText(StringConstant.CALL_POLICE_PHONE_NUMBER);
                                }
                                if (tv_title instanceof TextView) {
                                    ((TextView) tv_title).setText("驾驶险报案电话");
                                }
                                if (tv_confirm instanceof TextView) {
                                    ((TextView) tv_confirm).setText("拨打");
                                }
                            }
                        });
                    } else if (outerTagBean instanceof Overtimeordertable) {
                        //还没有做 加班险报案
                    }
                }
            };
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Object obj = getItem(position);
        if (obj instanceof Vehicleordertable) {
            Vehicleordertable bean = (Vehicleordertable) obj;
            holder.tv_insure_name.setText( "是哪个字段？");
            if (bean.getCompanyInfo() != null) {
                holder.tv_buy_insure_agent.setText(bean.getCompanyInfo().getCompanyname() + "");
            } else {
                holder.tv_buy_insure_agent.setText("null");
            }
            if (bean.getStartdate() != null) {
                holder.tv_buy_insure_time.setText(TimeUtil.getTimeString(bean.getStartdate()));
            } else {
                holder.tv_buy_insure_time.setText("null");
            }
            holder.item.setTag(bean);
            holder.item.setOnClickListener(holder.onClickListener);
        } else if (obj instanceof Overtimeordertable) {
            Overtimeordertable bean = (Overtimeordertable) obj;
            holder.tv_insure_name.setText("加班险");

            holder.tv_buy_insure_agent.setText("万保易");

            if (bean.getStartdate() != null) {
                holder.tv_buy_insure_time.setText(TimeUtil.getTimeString(bean.getStartdate()));
            }else {
                holder.tv_buy_insure_time.setText("null");
            }
            holder.item.setTag(bean);
            holder.item.setOnClickListener(holder.onClickListener);
        } else if (obj instanceof Driverordertable) {
            Driverordertable bean = (Driverordertable) obj;
            holder.tv_insure_name.setText("驾驶险");
            if (bean.getCompanyInfo() != null) {
                holder.tv_buy_insure_agent.setText(bean.getCompanyInfo().getCompanyname());
            } else {
                holder.tv_buy_insure_agent.setText("null");
            }

            if (bean.getStartdate() != null) {
                holder.tv_buy_insure_time.setText(TimeUtil.getTimeString(bean.getStartdate()));
            } else {
                holder.tv_buy_insure_time.setText("null");
            }
            holder.item.setTag(bean);
            holder.item.setOnClickListener(holder.onClickListener);
        }
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
                    String text = "";
                    if (outerTagBean instanceof Vehicleordertable) {
                        Vehicleordertable bean = (Vehicleordertable) outerTagBean;
                        if (bean.getInsuranceDetail() != null) {
                            text = ((Vehicleordertable) outerTagBean).getInsuranceDetail().getInsurancename();
                        }
                        text += StringConstant.TEXT_SHOW_AFTER_CALL_POLICE_SUCCESS;
                    } else if (outerTagBean instanceof Driverordertable) {
                        text = "驾驶险" + StringConstant.TEXT_SHOW_AFTER_CALL_POLICE_SUCCESS;

                    } else if (outerTagBean instanceof Overtimeordertable) {
                        text = "加班险" + StringConstant.TEXT_SHOW_AFTER_CALL_POLICE_SUCCESS;
                    }
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
