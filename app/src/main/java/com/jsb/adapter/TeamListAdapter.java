package com.jsb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.wallet.base.datamodel.AccountManager;
import com.jsb.R;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;

import com.jsb.constant.IntConstant;
import com.jsb.constant.PreferenceConstant;
import com.jsb.fragment.DialogFragmentCreater;
import com.jsb.model.FourService;
import com.jsb.model.FreedomData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.Userstable;
import com.jsb.ui.me.myteam.CertificationActivity;
import com.jsb.ui.me.myteam.MyTeamForFreeActivity;
import com.jsb.ui.me.myteam.MyTeamForLeaderActivity;
import com.jsb.ui.me.myteam.MyTeamForMemberActivity;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.ProgressDialogUtil;
import com.jsb.widget.decoview.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/9/16.
 */
public class TeamListAdapter extends BaseAdapter {

    public TeamListAdapter(final Activity mContext, List<FreedomData> datas) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;

    }

    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<FreedomData> mDatas = new ArrayList<>();


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public FreedomData getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.item_team_list, null);
            holder.tvLeaderTz = (TextView) convertView.findViewById(R.id.tv_leader_tz);
            holder.tvLeaderTyCount = (TextView) convertView.findViewById(R.id.tv_leader_ty_count);
            holder.tvTotalBaofei = (TextView) convertView.findViewById(R.id.tv_total_baofei);
            holder.layoutIWantToJoin = (LinearLayout) convertView.findViewById(R.id.layout_i_want_to_join);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            holder.listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object obj = v.getTag();
                    if (obj instanceof FreedomData) {
                        final FreedomData data = (FreedomData) obj;
                        switch (v.getId()) {
                            case R.id.layout_i_want_to_join:
                                final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(mContext);
                                progressDialogUtil.show("正在获取用户身份信息...");
                                final int userid = PreferenceUtil.load(mContext, PreferenceConstant.userid, -1);
                                if (userid != -1) {

                                    UserRetrofitUtil.getSelfInfo(mContext, userid, new NetCallback<NetWorkResultBean<Userstable>>(mContext) {
                                        @Override
                                        public void onFailure(RetrofitError error, String message) {
                                            progressDialogUtil.hide();
                                            if (TextUtils.isEmpty(message)) {
                                                Toast.makeText(mContext, "无法连接网络", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void success(NetWorkResultBean<Userstable> userstableNetWorkResultBean, Response response) {
                                            progressDialogUtil.hide();

                                            Userstable userBean = userstableNetWorkResultBean.getData();
                                            //进入我的 团队之前先获取用户的信息
                                            if (userBean != null) {
                                                if (userBean.getType() != null) {
                                                    int userType = userBean.getType();
                                                    switch (userType) {
                                                        case IntConstant.USER_TYPE_FREE:
                                                            switch (userBean.getAudit()) {
                                                                //   0未认证
                                                                //   1等待审核
                                                                //   2 通过审核
                                                                //   3未通过
                                                                case 0:
                                                                    Toast.makeText(mContext, "请先提交实名认证！", Toast.LENGTH_SHORT).show();
                                                                    mContext.startActivity(new Intent(mContext, CertificationActivity.class));
                                                                    break;
                                                                case 1:
                                                                    Toast.makeText(mContext, "加团失败！您提交了实名认证，请等待审核！", Toast.LENGTH_SHORT).show();
                                                                    break;
                                                                case 3:
                                                                    Toast.makeText(mContext, "加团失败！您的实名认证未通过审核！请重新提交实名认证！", Toast.LENGTH_SHORT).show();
                                                                    mContext.startActivity(new Intent(mContext, CertificationActivity.class));
                                                                    break;
                                                                case 2:

                                                                    //判断是否已经提交过加团请求
                                                                    UserRetrofitUtil.jugeJoinRequest(mContext, userid, new NetCallback<NetWorkResultBean<Object>>(mContext) {
                                                                        @Override
                                                                        public void onFailure(RetrofitError error, String message) {

                                                                        }

                                                                        @Override
                                                                        public void success(NetWorkResultBean<Object> integer, Response response) {
                                                                            if (integer.getData() instanceof Integer)//未提交任何加团请求
                                                                            {
                                                                                UserRetrofitUtil.submitJoinRequest(mContext, userid, data.getTeamid(), new NetCallback<NetWorkResultBean<String>>(mContext) {
                                                                                    @Override
                                                                                    public void onFailure(RetrofitError error, String message) {
                                                                                        Toast.makeText(mContext, "提交失败！", Toast.LENGTH_SHORT).show();
                                                                                    }

                                                                                    @Override
                                                                                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                                                                        Toast.makeText(mContext, "人团申请已经提交！请等待团长审核！", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });
                                                                            } else {
                                                                                Toast.makeText(mContext, integer.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                                            }

                                                                        }
                                                                    });
                                                            }
                                                            return;
                                                        case IntConstant.USER_TYPE_MEMBER:
                                                            return;
                                                        case IntConstant.USER_TYPE_LEADER:
                                                            return;
                                                        default:
                                                            return;
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                                break;
                            case R.id.item:
                                Toast.makeText(mContext, "点击一条记录！", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
            };
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FreedomData bean = getItem(position);
        if (!TextUtils.isEmpty(bean.getLeaderName())) {
            holder.tvLeaderTz.setText(bean.getLeaderName());
        }
        if (bean.getAmount() != null) {
            holder.tvLeaderTyCount.setText(bean.getAmount() + "人");
        }
        if (bean.getTotalFee() != null) {
            holder.tvTotalBaofei.setText(bean.getTotalFee() + "元");
        }
        holder.item.setTag(bean);
        holder.layoutIWantToJoin.setTag(bean);
        holder.item.setOnClickListener(holder.listener);
        holder.layoutIWantToJoin.setOnClickListener(holder.listener);
        return convertView;
    }


    private class ViewHolder {
        private TextView tvLeaderTz;
        private TextView tvLeaderTyCount;
        private TextView tvTotalBaofei;
        private LinearLayout layoutIWantToJoin;
        private LinearLayout item;

        private View.OnClickListener listener;

    }
}
