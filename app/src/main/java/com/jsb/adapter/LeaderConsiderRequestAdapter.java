package com.jsb.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

import com.jsb.R;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.StringConstant;
import com.jsb.fragment.DialogFragmentCreater;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.TeamData;
import com.jsb.model.Userstable;
import com.jsb.util.ProgressDialogUtil;

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

                                    showConfirmOrCancelDialog(activity, bean.getName(), requestUserId, teamId).show();
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
    private Dialog showConfirmOrCancelDialog(final Context mContext, final String name, final int memberId, final int teamId) {
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
                progressDialogUtil.show("正在发送请求...");
                UserRetrofitUtil.auditJoinRequest(activity, memberId, teamId, auditResult, new NetCallback<NetWorkResultBean<String>>(activity) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        progressDialogUtil.hide();
                        if (!TextUtils.isEmpty(message)) {
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        progressDialogUtil.hide();
                        Toast.makeText(activity, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
