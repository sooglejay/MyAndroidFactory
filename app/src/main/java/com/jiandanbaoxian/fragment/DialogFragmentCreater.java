package com.jiandanbaoxian.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.ui.MyModifyPasswordActivity;
import com.jungly.gridpasswordview.GridPasswordView;

/**
 * Created by Administrator on 2015/10/18.
 */
public class DialogFragmentCreater extends DialogFragment {
    public static final int DialogShowInputPasswordDialog = 1000;//权限控制，当特殊操作时，要求输入密码
    public static final int DialogShowConfirmOrCancelDialog = 1001;// 我要报案 -点击item弹出对话框
    public static final int DialogShowSingleChoiceDialog = 1002;// 成功报案后，需要弹出对话框 显示 一些文字
    public final static String dialog_fragment_key = "fragment_id";
    public final static String dialog_fragment_tag = "dialog";
    private Context mContext;

    private FragmentManager fragmentManager;

    public void setDialogContext(Context mContext, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.fragmentManager = fragmentManager;
    }


    private Dialog outerDialog;
    private OnDialogClickLisenter onDialogClickLisenter;

    public void setOnPasswordDialogClickListener(OnPasswordDialogClickListener onPasswordDialogClickListener) {
        this.onPasswordDialogClickListener = onPasswordDialogClickListener;
    }

    private OnPasswordDialogClickListener onPasswordDialogClickListener;//只为密码 设置的回调接口

    public void setOnDialogClickLisenter(OnDialogClickLisenter onDialogClickLisenter) {
        this.onDialogClickLisenter = onDialogClickLisenter;
    }

    public interface OnDialogClickLisenter {
        public void viewClick(String tag);

        //回调控制方法
        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content);
    }

    //专为密码  设置的回调
    public interface OnPasswordDialogClickListener {
        public void getPassword(String psw);

        public void onDialogDismiss(EditText view);
    }

    private TextView tv_forget_psw;//找回密码
    private GridPasswordView inputView;//密码输入框

    @Override
    public void onDismiss(android.content.DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    /**
     * 调用Dialog的地方
     * * @param fragment_id
     */
    public void showDialog(Context mContext, int fragment_id) {
        this.mContext = mContext;
        try {
            Bundle args = new Bundle();
            args.putInt(dialog_fragment_key, fragment_id);
            this.setArguments(args);
            this.show(fragmentManager, dialog_fragment_tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void dismiss() {
        // TODO Auto-generated method stub
        super.dismiss();
    }

    @Override
    public Dialog getDialog() {
        // TODO Auto-generated method stub
        return super.getDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            int fragment_id = getArguments().getInt(dialog_fragment_key);
            switch (fragment_id) {

                case DialogShowInputPasswordDialog:
                    return showInputPasswordDialog(mContext);
                case DialogShowConfirmOrCancelDialog:
                    return showConfirmOrCancelDialog(mContext);
                case DialogShowSingleChoiceDialog:
                    return showSingleChoiceDialog(mContext);
                default:
                    break;
            }
        }
        return super.onCreateDialog(savedInstanceState);
    }

    /**
     * 自定义Dialog
     *
     * @param mContext
     * @return
     */
    private Dialog showInputPasswordDialog(final Context mContext) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_right_control, null);
        tv_forget_psw = (TextView) convertView.findViewById(R.id.tv_forget_psw);
        tv_forget_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MyModifyPasswordActivity.class));
            }
        });
        inputView = (GridPasswordView) convertView.findViewById(R.id.pswView);
        inputView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onChanged(String psw) {

            }

            @Override
            public void onMaxLength(String psw) {
                if (onPasswordDialogClickListener != null) {
                    onPasswordDialogClickListener.getPassword(psw);
                }
            }
        });

        final Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
//        dialog.setCanceledOnTouchOutside(false);//要求触碰到外面能够消失
        dialog.setContentView(convertView);

        dialog.getWindow().setWindowAnimations(R.style.dialog_right_control_style);
        //当dialog 显示的时候，弹出键盘
        dialog.setOnShowListener(new android.content.DialogInterface.OnShowListener() {
            @Override
            public void onShow(final android.content.DialogInterface dialog) {

                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            Thread.sleep(params[0]);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void value) {
                        super.onPostExecute(value);
                        if (outerDialog.isShowing())
                            if (inputView != null) {
                                inputView.requestFocus();
                                InputMethodManager imm = (InputMethodManager) inputView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                            }
                    }
                }.execute(600);
            }
        });

        outerDialog = dialog;
        return dialog;
    }

    /**
     * 我要报案=点击item 弹出对话框
     *
     * @param mContext
     * @return
     */
    private Dialog showConfirmOrCancelDialog(final Context mContext) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_double_choice, null);
        final Dialog dialog = new Dialog(mContext, R.style.mystyle);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_cancel:
                        if (onDialogClickLisenter != null)
                            onDialogClickLisenter.viewClick(StringConstant.tv_cancel);
                        dismiss();
                        break;
                    case R.id.tv_confirm:
                        if (onDialogClickLisenter != null)
                            onDialogClickLisenter.viewClick(StringConstant.tv_confirm);
                        dismiss();
                        break;
                    default:
                        break;
                }
            }
        };
        TextView tv_cancel = (TextView) convertView.findViewById(R.id.tv_cancel);
        TextView tv_confirm = (TextView) convertView.findViewById(R.id.tv_confirm);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) convertView.findViewById(R.id.tv_content);

        if (onDialogClickLisenter != null) {
            onDialogClickLisenter.controlView(tv_confirm, tv_cancel, tv_title, tv_content);
        }
        tv_cancel.setOnClickListener(listener);
        tv_confirm.setOnClickListener(listener);
        dialog.setContentView(convertView);
        dialog.getWindow().setWindowAnimations(R.style.dialog_right_control_style);
        return dialog;
    }


    private Dialog showSingleChoiceDialog(final Context mContext) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_single_choice, null);
        final Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_confirm:
                        onDialogClickLisenter.viewClick("tv_confirm");
                        dismiss();
                        break;
                }
            }
        };
        TextView tv_confirm = (TextView) convertView.findViewById(R.id.tv_confirm);
        TextView tv_explain = (TextView) convertView.findViewById(R.id.tv_explain);

        tv_explain.setText(StringConstant.TEXT_SHOW_AFTER_CALL_POLICE_SUCCESS);
        if (onDialogClickLisenter != null) {
            onDialogClickLisenter.controlView(tv_confirm, null, null, tv_explain);
        }
        tv_confirm.setOnClickListener(listener);

        dialog.setContentView(convertView);
        dialog.getWindow().setWindowAnimations(R.style.dialog_right_control_style);
        return dialog;
    }

    private Dialog signOutDialog() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.close_app:

                        if (onDialogClickLisenter != null)
                            onDialogClickLisenter.viewClick(StringConstant.tv_confirm);
                        dismiss();
                        break;
                    case R.id.stay_here:
                        if (onDialogClickLisenter != null)
                            onDialogClickLisenter.viewClick(StringConstant.tv_cancel);
                        dismiss();
                        break;
                    default:
                        break;
                }
            }
        };
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.view_sign_out_dialog, null);
        TextView signOutButton = (TextView) convertView.findViewById(R.id.stay_here);
        TextView closeAppButton = (TextView) convertView.findViewById(R.id.close_app);

        signOutButton.setOnClickListener(listener);
        closeAppButton.setOnClickListener(listener);
        Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
        dialog.setContentView(convertView);
        dialog.getWindow().setWindowAnimations(R.style.dialog_right_control_style);
        return dialog;
    }

}
