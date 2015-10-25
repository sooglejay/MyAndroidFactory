package com.jsb.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.constant.StringConstant;
import com.jsb.R;
import com.jsb.ui.MyCallPoliceActivity;
import com.jsb.ui.MyHistorySaleActivity;
import com.jsb.ui.MyInsureActivity;
import com.jsb.ui.MyModifyPasswordActivity;
import com.jsb.ui.MyMoneyPocketActivity;
import com.jsb.ui.ShareActivity;
import com.jsb.widget.PopWindowUtils;
import com.jsb.widget.TitleBar;

import retrofit.RetrofitError;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }


    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.me, -1, R.drawable.icon_bar_share, "", "");

        //我的 fragment 顶部右边的图标
        mPopWindow = new PopWindowUtils(this.getActivity());
        dialogFragmentCreater = new DialogFragmentCreater(this.getActivity(), this.getActivity().getSupportFragmentManager());


        //我的保险
        view.findViewById(R.id.layout_my_insure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyInsureActivity.class));
            }
        });

        //我的历史报价
        view.findViewById(R.id.layout_my_history_sale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyHistorySaleActivity.class));
            }
        });

        //我的报案
        view.findViewById(R.id.layout_my_call_plice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyCallPoliceActivity.class));
            }
        });

        //我的钱包
        view.findViewById(R.id.layout_my_money_packet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyMoneyPocketActivity.class));
            }
        });

        //分享给朋友
        view.findViewById(R.id.layout_my_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ShareActivity.class));
            }
        });

        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {

            }

            @Override
            public void onRightButtonClick(View v) {
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
                                startActivity(new Intent(MeFragment.this.getActivity(), MyModifyPasswordActivity.class));
                                break;
                            case R.id.layout_login_out:

                                AlertDialog.Builder builder = new AlertDialog.Builder(MeFragment.this.getActivity());
                                builder.setTitle("您确定要退出登录么?")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialogFragmentCreater.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                                                    @Override
                                                    public void viewClick(String tag) {

                                                    }

                                                    @Override
                                                    public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                                                        if (tv_content instanceof TextView) {
                                                            ((TextView) tv_content).setText("亲，您去忙吧！需要小保\n的时候，我随时在您左右！");
                                                        }
                                                    }


                                                });
                                                dialogFragmentCreater.showDialog(MeFragment.this.getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);

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


    @Override
    public void onStop() {
        super.onStop();
        mPopWindow.dismiss();
    }

}
