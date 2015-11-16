package com.jsb.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.model.InviteInfo;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.Userstable;
import com.jsb.util.ProgressDialogUtil;

import java.util.Iterator;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by JammyQtheLab on 2015/11/14.
 */
public class MemberConsiderInviteAdapter extends BaseAdapter {
    private final static int ACCEPT = 1;//接受
    private final static int OBJECT = 0;//拒绝
    private int inviteResult;//团员审核结果
    private List<InviteInfo> mDatas;
    private Activity activity;
    private ViewHolder holder;

    public MemberConsiderInviteAdapter(List<InviteInfo> mDatas, Activity activity) {
        this.mDatas = mDatas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public InviteInfo getItem(int position) {
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
                    if (object instanceof InviteInfo) {
                        final InviteInfo bean = (InviteInfo) object;
                        switch (v.getId()) {
                            case R.id.item:
                                int inviterid = bean.getInviterid() != null ? bean.getInviterid() : 0;
                                String invitername = bean.getInvitername() != null ? bean.getInvitername() : "";
                                showConfirmOrCancelDialog(activity, inviterid, invitername).show();
                                break;
                            case R.id.layout_clear:
                                Iterator keys = mDatas.iterator();
                                while (keys.hasNext()) {
                                    InviteInfo b = (InviteInfo) keys.next();
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
        InviteInfo bean = getItem(position);
        holder.tv_message.setText(bean.getInvitername());
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
    private Dialog showConfirmOrCancelDialog(final Context mContext, final int inviteinfoid, final String inviteName) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_double_choice, null);
        final Dialog dialog = new Dialog(mContext, R.style.mystyle);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_cancel:
                        inviteResult = OBJECT;
                        dialog.dismiss();
                        break;
                    case R.id.tv_confirm:
                        inviteResult = ACCEPT;
                        dialog.dismiss();
                        break;
                    default:
                        inviteResult = OBJECT;
                        dialog.dismiss();
                        break;
                }
                final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(activity);
                progressDialogUtil.show("正在发送请求...");
                UserRetrofitUtil.dealInviting(activity, inviteinfoid, inviteResult, new NetCallback<NetWorkResultBean<String>>(activity) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        progressDialogUtil.hide();
                        if (!TextUtils.isEmpty(message)) {
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
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
        tv_content.setText(inviteName + " 邀请您加入她/他的团队\n是否接受？");
        tv_cancel.setOnClickListener(listener);
        tv_confirm.setOnClickListener(listener);
        dialog.setContentView(convertView);
        dialog.getWindow().setWindowAnimations(R.style.dialog_right_control_style);
        return dialog;
    }


}
