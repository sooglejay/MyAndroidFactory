package com.jiandanbaoxian.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.fragment.DialogFragmentCreater;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.TeamData;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.util.ProgressDialogUtil;

import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by JammyQtheLab on 2015/11/14.
 */
public class LeaderConsiderRequestAdapter extends BaseAdapter {
    private final static int PASS = 1;//审核通过
    private final static int FAILURE = 0;//未通过
    private int auditResult;//团长审核结果
    private List<Userstable> mDatas;
    private Activity activity;
    private ViewHolder holder;
    private TeamData teamData;
    private DialogFragmentCreater dialogFragmentController;
    private PopupWindow popupWindow;
    private int teamId = 0;

    public LeaderConsiderRequestAdapter(List<Userstable> mDatas, Activity activity, TeamData teamData, PopupWindow popupWindow) {
        this.mDatas = mDatas;
        this.activity = activity;
        this.teamData = teamData;
        teamId = teamData != null && teamData.getTeamid() != null ? teamData.getTeamid() : 0;
        this.popupWindow = popupWindow;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Userstable getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_leader_consider_request, null);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            holder.tv_message = (TextView) convertView.findViewById(R.id.tv_message);
            holder.layout_clear = (LinearLayout) convertView.findViewById(R.id.layout_clear);
            holder.listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object object = v.getTag();
                    if (object instanceof Userstable) {
                        final Userstable bean = (Userstable) object;
                        switch (v.getId()) {
                            case R.id.item: {
                                final int requestUserId = bean.getId();
                                Log.e("jwjw", teamData.toString() + "\n teamId;" + teamId);
                                if (teamId != 0) {

                                    showConfirmOrCancelDialog(activity, bean.getName(), requestUserId, teamId, bean.getId()).show();
                                }
                            }
                            break;
                            case R.id.layout_clear:
                                Iterator keys = mDatas.iterator();
                                while (keys.hasNext()) {
                                    Userstable b = (Userstable) keys.next();
                                    if (b.getId() == bean.getId()) {
                                        keys.remove();
                                        onClickInterface.onClick(v);
                                        notifyDataSetChanged();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }
            };
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Userstable bean = getItem(position);
        holder.tv_message.setText("" + bean.getName() + "   想加入团队");
        holder.layout_clear.setTag(bean);
        holder.item.setTag(bean);
        holder.layout_clear.setOnClickListener(holder.listener);
        holder.item.setOnClickListener(holder.listener);
        return convertView;
    }

    private class ViewHolder {
        private LinearLayout layout_clear;
        private LinearLayout item;
        private TextView tv_message;
        private View.OnClickListener listener;
    }

    public void setOnClickInterface(OnClickInterface onClickInterface) {
        this.onClickInterface = onClickInterface;
    }

    private OnClickInterface onClickInterface;

    public interface OnClickInterface {
        public void onClick(View v);
    }


    /**
     * 我要报案=点击item 弹出对话框
     *
     * @param mContext
     * @return
     */
    private Dialog showConfirmOrCancelDialog(final Context mContext, final String name, final int memberId, final int teamId, final int userid) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_double_choice, null);
        final Dialog dialog = new Dialog(mContext, R.style.mystyle);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_cancel:
                        auditResult = FAILURE;
                        dialog.dismiss();
                        break;
                    case R.id.tv_confirm:
                        auditResult = PASS;
                        dialog.dismiss();
                        break;
                    default:
                        auditResult = FAILURE;
                        dialog.dismiss();
                        break;
                }
                final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(activity);
                progressDialogUtil.show("正在处理...");
                UserRetrofitUtil.auditJoinRequest(activity, memberId, teamId, auditResult, new NetCallback<NetWorkResultBean<Object>>(activity) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        progressDialogUtil.hide();
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void success(NetWorkResultBean<Object> stringNetWorkResultBean, Response response) {
                        progressDialogUtil.hide();

                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        if (stringNetWorkResultBean != null) {
                            int httpStatus = stringNetWorkResultBean.getStatus();
                            String message = stringNetWorkResultBean.getMessage().toString();
                            switch (httpStatus) {
                                case HttpURLConnection.HTTP_OK:
                                    if (!TextUtils.isEmpty(message)) {
                                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                default:
                                    if (!TextUtils.isEmpty(message)) {
                                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                            }
                        }
                    }
                });

                UserRetrofitUtil.auditUser(activity, userid, auditResult, new NetCallback<NetWorkResultBean<Object>>(activity) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {

                    }

                    @Override
                    public void success(NetWorkResultBean<Object> objectNetWorkResultBean, Response response) {

                    }
                });
            }
        };
        TextView tv_cancel = (TextView) convertView.findViewById(R.id.tv_cancel);
        TextView tv_confirm = (TextView) convertView.findViewById(R.id.tv_confirm);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) convertView.findViewById(R.id.tv_content);
        tv_title.setText("提示");
        tv_content.setText("是否同意" + name + " 加团请求！");
        tv_cancel.setOnClickListener(listener);
        tv_confirm.setOnClickListener(listener);
        dialog.setContentView(convertView);
        dialog.getWindow().setWindowAnimations(R.style.dialog_right_control_style);
        return dialog;
    }


}
