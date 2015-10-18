package com.jsb.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jsb.R;

/**
 * Created by Administrator on 2015/10/18.
 */
public class RightControlFragment extends DialogFragment {
    public static final int Dialog_RightControl = 10000;//权限控制，当特殊操作时，要求输入密码
    public final static String dialog_fragment_key = "fragment_id";
    public final static String dialog_fragment_tag = "dialog";
    private Context mContext;


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    /**
     * 调用Dialog的地方
     *
     * @param manager
     * @param fragment_id
     */
    public void showDialog(Context mContext,FragmentManager manager, int fragment_id) {
        this.mContext = mContext;
        try {
            Bundle args = new Bundle();
            args.putInt(dialog_fragment_key, fragment_id);
            this.setArguments(args);
            this.show(manager, dialog_fragment_tag);
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
                case Dialog_RightControl:
                    return showRightControlDialog(mContext);
                default:
                    break;
            }
        }
        return super.onCreateDialog(savedInstanceState);
    }

    /**
     * 自定义Dialog
     * @param mContext
     * @return
     */
    private Dialog showRightControlDialog(Context mContext) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_right_control, null);
        Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
        dialog.setContentView(convertView);
        dialog.getWindow().setWindowAnimations(R.style.dialog_right_control_style);
        return dialog;
    }

}
