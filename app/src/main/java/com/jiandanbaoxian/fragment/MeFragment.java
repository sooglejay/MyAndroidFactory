package com.jiandanbaoxian.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.IntConstant;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.event.BusEvent;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.ui.ApplyEcoActivity;
import com.jiandanbaoxian.ui.LoginActivity;
import com.jiandanbaoxian.ui.me.mycallpolice.MyCallPoliceActivity;
import com.jiandanbaoxian.ui.me.historyprice.MyHistoryPriceListActivity;
import com.jiandanbaoxian.ui.me.myinsurance.MyInsureActivity;
import com.jiandanbaoxian.ui.MyModifyPasswordActivity;
import com.jiandanbaoxian.ui.me.mymoneypocket.MyMoneyPocketActivity;
import com.jiandanbaoxian.ui.me.myteam.MyTeamForFreeActivity;
import com.jiandanbaoxian.ui.me.myteam.MyTeamForLeaderActivity;
import com.jiandanbaoxian.ui.me.myteam.MyTeamForMemberActivity;
import com.jiandanbaoxian.ui.me.share.ShareActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.PopWindowUtils;
import com.jiandanbaoxian.widget.TitleBar;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的-主框架-tab3
 */
public class MeFragment extends BaseFragment {

    private TitleBar titleBar;
    private View layout_my_insure;
    private View layout_my_history_sale;
    private View layout_my_call_plice;
    private View layout_my_money_packet;
    private View layout_my_share;
    private View layout_my_team;

    private TextView my_money_packet;
    private TextView my_share;
    private PopWindowUtils mPopWindow;
    private DialogFragmentCreater dialogFragmentCreater;


    private Activity context;


    private Userstable userBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_4, container, false);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //统计页面
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }


    private void setUp(View view, Bundle savedInstanceState) {
        context = this.getActivity();

        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.me, -1, R.drawable.icon_more, "", "");

        //我的 fragment 顶部右边的图标
        mPopWindow = new PopWindowUtils(this.getActivity());
        dialogFragmentCreater = new DialogFragmentCreater();
        dialogFragmentCreater.setDialogContext(this.getActivity(), this.getActivity().getSupportFragmentManager());


        //我的保险
        view.findViewById(R.id.layout_my_insure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIsLogin()) {
                    //如果没有登录，就弹出是否登录的提醒对话框
                    dialogFragmentCreater.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                LoginActivity.startLoginActivity(context);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("提示");
                            }
                            if (tv_content instanceof TextView) {
                                ((TextView) tv_content).setText("您需要登录操作才能操作哦！\n是否现在就去登录？");
                            }
                        }
                    });
                    dialogFragmentCreater.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {
                    getActivity().startActivity(new Intent(getActivity(), MyInsureActivity.class));
                }
            }
        });

        //我的团队
        view.findViewById(R.id.layout_my_team).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIsLogin()) {
                    //如果没有登录，就弹出是否登录的提醒对话框
                    dialogFragmentCreater.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                LoginActivity.startLoginActivity(context);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("提示");
                            }
                            if (tv_content instanceof TextView) {
                                ((TextView) tv_content).setText("您需要登录操作才能操作哦！\n是否现在就去登录？");
                            }

                        }
                    });
                    dialogFragmentCreater.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {
                    final int userid = PreferenceUtil.load(context, PreferenceConstant.userid, -1);
                    if (userid != -1) {

                        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(context);
                        progressDialogUtil.show("正在获取用户身份信息...");

                        UserRetrofitUtil.getSelfInfo(context, userid, new NetCallback<NetWorkResultBean<Userstable>>(context) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {
                                progressDialogUtil.hide();
                                if (TextUtils.isEmpty(message)) {
                                    Toast.makeText(context, "无法连接网络：" + error.toString(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void success(NetWorkResultBean<Userstable> userstableNetWorkResultBean, Response response) {
                                progressDialogUtil.hide();
                                userBean = userstableNetWorkResultBean.getData();
                                //进入我的 团队之前先获取用户的信息
                                if (userBean != null) {
                                    if (userBean.getType() != null) {
                                        int userType = userBean.getType();
                                        switch (userType) {
                                            case IntConstant.USER_TYPE_FREE:
                                                MyTeamForFreeActivity.startActivity(context, userBean);
                                                return;
                                            case IntConstant.USER_TYPE_MEMBER:
                                                MyTeamForMemberActivity.startActivity(context, userBean);
                                                return;
                                            case IntConstant.USER_TYPE_LEADER:
                                                MyTeamForLeaderActivity.startActivity(context, userBean);
                                                return;
                                            default:
                                                return;
                                        }
                                    }
                                }
                            }
                        });
                    }

                }
            }
        });

        //我的历史报价
        view.findViewById(R.id.layout_my_history_sale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIsLogin()) {
                    //如果没有登录，就弹出是否登录的提醒对话框
                    dialogFragmentCreater.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                LoginActivity.startLoginActivity(context);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("提示");
                            }
                            if (tv_content instanceof TextView) {
                                ((TextView) tv_content).setText("您需要登录操作才能操作哦！\n是否现在就去登录？");
                            }

                        }
                    });
                    dialogFragmentCreater.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {
                    getActivity().startActivity(new Intent(getActivity(), MyHistoryPriceListActivity.class));
                }
            }
        });

        //我的报案
        view.findViewById(R.id.layout_my_call_plice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIsLogin()) {
                    //如果没有登录，就弹出是否登录的提醒对话框
                    dialogFragmentCreater.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                LoginActivity.startLoginActivity(context);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("提示");
                            }
                            if (tv_content instanceof TextView) {
                                ((TextView) tv_content).setText("您需要登录操作才能操作哦！\n是否现在就去登录？");
                            }

                        }
                    });
                    dialogFragmentCreater.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {
                    getActivity().startActivity(new Intent(getActivity(), MyCallPoliceActivity.class));
                }
            }
        });

        //我的钱包
        view.findViewById(R.id.layout_my_money_packet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIsLogin()) {
                    //如果没有登录，就弹出是否登录的提醒对话框
                    dialogFragmentCreater.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                LoginActivity.startLoginActivity(context);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("提示");
                            }
                            if (tv_content instanceof TextView) {
                                ((TextView) tv_content).setText("您需要登录操作才能操作哦！\n是否现在就去登录？");
                            }

                        }
                    });
                    dialogFragmentCreater.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {
                    getActivity().startActivity(new Intent(getActivity(), MyMoneyPocketActivity.class));
                }
            }
        });

        //分享给朋友
        view.findViewById(R.id.layout_my_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ShareActivity.class));
            }
        });

        //申请合作
        view.findViewById(R.id.layout_apply_eco).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ApplyEcoActivity.class));
            }
        });

        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {

            }

            @Override
            public void onRightButtonClick(View v) {
                if (!checkIsLogin()) {
                    //如果没有登录，就弹出是否登录的提醒对话框
                    dialogFragmentCreater.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                LoginActivity.startLoginActivity(context);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("提示");
                            }
                            if (tv_content instanceof TextView) {
                                ((TextView) tv_content).setText("您需要登录操作才能操作哦！\n是否现在就去登录？");
                            }

                        }
                    });
                    dialogFragmentCreater.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {


                    titleBar.setRightContainerClickAble(false);
                    if (mPopWindow.isShowing()) {
                        mPopWindow.dismiss();
                        return;
                    }
                    mPopWindow.showMeFragmentPopWindows(v, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.layout_modify_password:
                                    MyModifyPasswordActivity.startModifyPasswordActivity(MeFragment.this.getActivity());
                                    break;
                                case R.id.layout_login_out:

                                    AlertDialog.Builder builder = new AlertDialog.Builder(MeFragment.this.getActivity());
                                    builder.setTitle("您确定要退出登录么?")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    PreferenceUtil.save(context, PreferenceConstant.userid, -1);
                                                    PreferenceUtil.save(context, PreferenceConstant.pwd, "");
                                                    PreferenceUtil.save(context, PreferenceConstant.phone, "");
                                                    PreferenceUtil.save(context, PreferenceConstant.name, "");
                                                    EventBus.getDefault().post(new BusEvent(BusEvent.MSG_SignOut_Success));

                                                    dialogFragmentCreater.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                                                        @Override
                                                        public void viewClick(String tag) {
                                                            if (tag.equals(StringConstant.tv_confirm)) {
                                                            }
                                                        }

                                                        @Override
                                                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                                                            if (tv_content instanceof TextView) {
                                                                ((TextView) tv_content).setText("亲，您去忙吧！需要小保\n的时候，我随时在您左右！");
                                                            }


                                                        }
                                                    });
                                                    dialogFragmentCreater.showDialog(MeFragment.this.getActivity(), DialogFragmentCreater.DialogShowSingleChoiceDialog);

                                                }
                                            }).setNegativeButton("取消", null).create().show();


                                    break;
                                case R.id.layout_clear_buffer:
                                    Toast.makeText(MeFragment.this.getActivity(), "清除缓存", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            mPopWindow.dismiss();
                        }
                    });
                }
            }
        });

        mPopWindow.setOnPopWindowDismissListener(new PopWindowUtils.OnPopWindowDismissListener() {
            @Override
            public void onDismissListener() {
                titleBar.setRightContainerClickAble(true);
            }
        });

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    private boolean checkIsLogin() {
        int userid = PreferenceUtil.load(getActivity(), PreferenceConstant.userid, -1);
        if (userid != -1) {
            return true;
        } else return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        mPopWindow.dismiss();
    }

}
