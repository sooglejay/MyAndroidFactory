package com.jiandanbaoxian.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.LeaderConsiderRequestAdapter;
import com.jiandanbaoxian.adapter.MemberConsiderInviteAdapter;
import com.jiandanbaoxian.model.InviteInfo;
import com.jiandanbaoxian.model.TeamData;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.util.UIUtils;

import java.util.List;

/**
 * Created by JammyQtheLab on 2015/10/25.
 */
public class PopWindowUtils {

    private PopupWindow pop;

    public PopWindowUtils(Activity mContext) {
        this.mContext = mContext;
    }

    private Activity mContext;

    public PopupWindow showMeFragmentPopWindows(View v, View.OnClickListener onClickListener) {

        View view = mContext.getLayoutInflater().inflate(R.layout.pop_up_window_me_fragment, null);
        LinearLayout layout_modify_password = (LinearLayout) view.findViewById(R.id.layout_modify_password);
        LinearLayout layout_login_out = (LinearLayout) view.findViewById(R.id.layout_login_out);
        LinearLayout layout_clear_buffer = (LinearLayout) view.findViewById(R.id.layout_clear_buffer);


        layout_modify_password.setOnClickListener(onClickListener);
        layout_login_out.setOnClickListener(onClickListener);
        layout_clear_buffer.setOnClickListener(onClickListener);


        pop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.transparent));
        pop.setBackgroundDrawable(dw);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.popupwindow_animation);
        if (!mContext.isFinishing()) {
            pop.showAsDropDown(v, 0, 0);//必须放在setBackgroundDrawable和setOutsideTouchable之后
        }
        UIUtils.setWindowAlpla(mContext, 1f);
        //取消不透明的效果
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onPopWindowDismissListener != null) {
                    onPopWindowDismissListener.onDismissListener();
                }
            }
        });

        return pop;
    }

    public PopupWindow showPopWindowInMyTeamForMember(View v, View.OnClickListener onClickListener) {

        View view = mContext.getLayoutInflater().inflate(R.layout.pop_up_window_my_team_for_member, null);
        LinearLayout layout_create_team = (LinearLayout) view.findViewById(R.id.layout_create_team);
        LinearLayout layout_modify_info = (LinearLayout) view.findViewById(R.id.layout_modify_info);
        LinearLayout layout_check_rule = (LinearLayout) view.findViewById(R.id.layout_check_rule);


        layout_create_team.setOnClickListener(onClickListener);
        layout_modify_info.setOnClickListener(onClickListener);
        layout_check_rule.setOnClickListener(onClickListener);


        pop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.transparent));
        pop.setBackgroundDrawable(dw);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.popupwindow_animation);
        if (!mContext.isFinishing()) {
            pop.showAsDropDown(v, 0, 0);//必须放在setBackgroundDrawable和setOutsideTouchable之后
        }
        UIUtils.setWindowAlpla(mContext, 1f);
        //取消不透明的效果
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onPopWindowDismissListener != null) {
                    onPopWindowDismissListener.onDismissListener();
                }
            }
        });

        return pop;
    }

    public PopupWindow showPopWindowInMyTeamForLeader(View v, View.OnClickListener onClickListener) {

        View view = mContext.getLayoutInflater().inflate(R.layout.pop_up_window_my_team_for_leader, null);
        LinearLayout layout_add_new_member = (LinearLayout) view.findViewById(R.id.layout_add_new_member);
        LinearLayout layout_modify_info = (LinearLayout) view.findViewById(R.id.layout_modify_info);
        LinearLayout layout_check_rule = (LinearLayout) view.findViewById(R.id.layout_check_rule);


        layout_add_new_member.setOnClickListener(onClickListener);
        layout_modify_info.setOnClickListener(onClickListener);
        layout_check_rule.setOnClickListener(onClickListener);


        pop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.transparent));
        pop.setBackgroundDrawable(dw);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.popupwindow_animation);
        if (!mContext.isFinishing()) {
            pop.showAsDropDown(v, 0, 0);//必须放在setBackgroundDrawable和setOutsideTouchable之后
        }
        UIUtils.setWindowAlpla(mContext, 1f);
        //取消不透明的效果
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onPopWindowDismissListener != null) {
                    onPopWindowDismissListener.onDismissListener();
                }
            }
        });

        return pop;
    }


    //团长查看入团请求
    public PopupWindow showPopWindowInMyTeamForLeaderConsiderRequest(View v, Activity activity, List<Userstable> userstableList, TeamData teamData) {
        View view = mContext.getLayoutInflater().inflate(R.layout.pop_up_window_my_team_for_leader_consider_request, null);
        ListView listView = (ListView) view.findViewById(R.id.list_view);

        pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LeaderConsiderRequestAdapter adapter = new LeaderConsiderRequestAdapter(userstableList, activity, teamData, pop);
        listView.setAdapter(adapter);
        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.transparent));
        pop.setBackgroundDrawable(dw);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.popupwindow_animation);

        if (!activity.isFinishing()) {
            pop.showAsDropDown(v, 0, 0);//必须放在setBackgroundDrawable和setOutsideTouchable之后
        }

        UIUtils.setWindowAlpla(mContext, 1f);
        //取消不透明的效果
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onPopWindowDismissListener != null) {
                    onPopWindowDismissListener.onDismissListener();
                }
            }
        });
        adapter.setOnClickInterface(new LeaderConsiderRequestAdapter.OnClickInterface() {
            @Override
            public void onClick(View v) {
                if (pop != null) {
                    pop.dismiss();
                }
            }
        });
        return pop;
    }

    //团员查看入团邀请
    public PopupWindow showPopWindowInMyTeamForMemberConsiderRequest(View v, Activity activity, List<InviteInfo> inviteInfos) {
        //下面的xml文件和上面团长审核可以共用
        View view = mContext.getLayoutInflater().inflate(R.layout.pop_up_window_my_team_for_leader_consider_request, null);
        ListView listView = (ListView) view.findViewById(R.id.list_view);

        pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        MemberConsiderInviteAdapter adapter = new MemberConsiderInviteAdapter(inviteInfos, activity);
        listView.setAdapter(adapter);
        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.transparent));
        pop.setBackgroundDrawable(dw);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.popupwindow_animation);
        if (!activity.isFinishing()) {
            pop.showAsDropDown(v, 0, 0);//必须放在setBackgroundDrawable和setOutsideTouchable之后
        }
        UIUtils.setWindowAlpla(mContext, 1f);
        //取消不透明的效果
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onPopWindowDismissListener != null) {
                    onPopWindowDismissListener.onDismissListener();
                }
            }
        });
        adapter.setOnClickInterface(new MemberConsiderInviteAdapter.OnClickInterface() {
            @Override
            public void onClick(View v) {
                if (pop != null) {
                    pop.dismiss();
                }
            }
        });
        return pop;
    }


    public void dismiss() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
    }

    public boolean isShowing() {
        if (pop != null) {
            return pop.isShowing();
        }
        return false;
    }

    public interface OnPopWindowDismissListener {
        public void onDismissListener();
    }

    public OnPopWindowDismissListener onPopWindowDismissListener;

    public void setOnPopWindowDismissListener(OnPopWindowDismissListener onPopWindowDismissListener) {
        this.onPopWindowDismissListener = onPopWindowDismissListener;
    }
}
