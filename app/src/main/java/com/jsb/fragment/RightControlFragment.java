package com.jsb.fragment;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.third_party.nineoldandroids.animation.AnimatorSet;
import com.jsb.util.UIUtils;

import java.util.concurrent.Delayed;

/**
 * Created by Administrator on 2015/10/18.
 */
public class RightControlFragment extends DialogFragment {
    public static final int Dialog_RightControl = 10000;//权限控制，当特殊操作时，要求输入密码
    public final static String dialog_fragment_key = "fragment_id";
    public final static String dialog_fragment_tag = "dialog";
    private Context mContext;

    private String mPasswordString = "";
    private EditText et_1;
    private EditText et_2;
    private EditText et_3;
    private EditText et_4;
    private EditText et_5;
    private EditText et_6;
    private EditText[] mEditTexts;


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
    public void showDialog(Context mContext, FragmentManager manager, int fragment_id) {
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
     *
     * @param mContext
     * @return
     */
    private Dialog showRightControlDialog(final Context mContext) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_right_control, null);
        mPasswordString = "";

        et_1 = (EditText) convertView.findViewById(R.id.et_1);
        et_2 = (EditText) convertView.findViewById(R.id.et_2);
        et_3 = (EditText) convertView.findViewById(R.id.et_3);
        et_4 = (EditText) convertView.findViewById(R.id.et_4);
        et_5 = (EditText) convertView.findViewById(R.id.et_5);
        et_6 = (EditText) convertView.findViewById(R.id.et_6);

        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    int i = 5;
                    while (i >=0) {
                        mEditTexts[i].setText("");
                        mEditTexts[i].clearFocus();
                        i--;
                    }
                    mPasswordString="";
                    mEditTexts[0].requestFocus();

                    return true;

                }
                return false;
            }
        };

        mEditTexts = new EditText[]{et_1, et_2, et_3, et_4, et_5, et_6};



        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPasswordString = mPasswordString + s;
                if (mPasswordString.length() < 6) {
                    int index = mPasswordString.length();
                    if (index >= 5)
                    {
                        index =5;
                    }
                    mEditTexts[index].requestFocus();
                } else
                {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        et_1.addTextChangedListener(textWatcher);
        et_2.addTextChangedListener(textWatcher);
        et_3.addTextChangedListener(textWatcher);
        et_4.addTextChangedListener(textWatcher);
        et_5.addTextChangedListener(textWatcher);
        et_6.addTextChangedListener(textWatcher);

        et_1.setOnKeyListener(onKeyListener);
        et_2.setOnKeyListener(onKeyListener);
        et_3.setOnKeyListener(onKeyListener);
        et_4.setOnKeyListener(onKeyListener);
        et_5.setOnKeyListener(onKeyListener);
        et_6.setOnKeyListener(onKeyListener);


        Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
        dialog.setContentView(convertView);
        dialog.getWindow().setWindowAnimations(R.style.dialog_right_control_style);
       //当dialog 显示的时候，弹出键盘
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

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
                        if(et_1!=null)
                        {
                            et_1.requestFocus();
                            InputMethodManager imm = (InputMethodManager) et_1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                        }

                    }
                }.execute(800);
            }
        });
        return dialog;
    }


}
