package com.jiandanbaoxian.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.fragment.DialogFragmentCreater;
import com.jiandanbaoxian.model.Driverordertable;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Overtimeordertable;
import com.jiandanbaoxian.model.Vehicleordertable;
import com.jiandanbaoxian.ui.me.mycallpolice.MyCallPoliceActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.util.TimeUtil;
import com.jiandanbaoxian.util.UIUtils;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

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

    public void checkLocation(double lat, double lng) {
        if (outerTagBean instanceof Overtimeordertable) {
            float o_lat = 0, o_lng = 0;
            if (((Overtimeordertable) outerTagBean).getLat() != null && ((Overtimeordertable) outerTagBean).getLng() != null) {
                o_lat = ((Overtimeordertable) outerTagBean).getLat();
                o_lng = ((Overtimeordertable) outerTagBean).getLng();
            }
            if (o_lat == lat && o_lng == lng) {
                int id = 0;
                int userid = PreferenceUtil.load(mContext, PreferenceConstant.userid, -1);
                if (((Overtimeordertable) outerTagBean).getId() != null) {
                    id = ((Overtimeordertable) outerTagBean).getId();
                }
                final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(mContext);
                progressDialogUtil.show("正在提交报案信息...");
                UserRetrofitUtil.reportOvertime(mContext, userid, id, new NetCallback<NetWorkResultBean<Object>>(mContext) {
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
                                    Toast.makeText(mContext, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(mContext, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        }

    }

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
                        callBack.onClick((Overtimeordertable) outerTagBean);
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
            holder.tv_insure_name.setText("是哪个字段？");
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
            } else {
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

    private JiaBanGouBaoAnCallBack callBack;

    public void setCallBack(JiaBanGouBaoAnCallBack callBack) {
        this.callBack = callBack;
    }

    public interface JiaBanGouBaoAnCallBack {
        public void onClick(Overtimeordertable object);

    }
}
