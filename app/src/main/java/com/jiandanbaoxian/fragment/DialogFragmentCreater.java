package com.jiandanbaoxian.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.RegionListAdapter;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.RegionBean;
import com.jiandanbaoxian.ui.MyModifyPasswordActivity;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;
import com.jungly.gridpasswordview.GridPasswordView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/18.
 */
public class DialogFragmentCreater extends DialogFragment {
    public static final int DialogShowInputPasswordDialog = 1000;//权限控制，当特殊操作时，要求输入密码
    public static final int DialogShowConfirmOrCancelDialog = 1001;// 我要报案 -点击item弹出对话框
    public static final int DialogShowSingleChoiceDialog = 1002;// 成功报案后，需要弹出对话框 显示 一些文字
    public static final int DialogShowRegionChoiceDialog = 1003;// 车险首页，选择行驶区域
    public static final int DialogShowLicenseDialog = 1004;//尝试在用户 购买车险 支付之前 使用Dialog 展示用户协议
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

    public void setOnDialogBackClickLisenter(OnDialogBackClickLisenter onDialogBackClickLisenter) {
        this.onDialogBackClickLisenter = onDialogBackClickLisenter;
    }

    private OnDialogBackClickLisenter onDialogBackClickLisenter;

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

    public interface OnDialogBackClickLisenter {
        public void onClickBack(String tvText, List<RegionBean> regionBeans, View view);


        //ListView 的方法
        public void onItemClickListener(int position, long id);

    }

    //专为密码  设置的回调
    public interface OnPasswordDialogClickListener {
        public void getPassword(String psw);

        public void onDialogDismiss(EditText view);
    }

    //专为显示Web View License  设置的回调
    public interface OnLicenseDialogClickListener {
        public void onClick(View view, boolean isAgree);
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
                case DialogShowRegionChoiceDialog:
                    return showRegionChoiceDialog(mContext);
                case DialogShowLicenseDialog:
                    return showLicenseChoiceDialog(mContext);
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


    private OnLicenseDialogClickListener onLicenseDialogClickListener;

    public OnLicenseDialogClickListener getOnLicenseDialogClickListener() {
        return onLicenseDialogClickListener;
    }

    public void setOnLicenseDialogClickListener(OnLicenseDialogClickListener onLicenseDialogClickListener) {
        this.onLicenseDialogClickListener = onLicenseDialogClickListener;
    }


    String webViewURL = "";
    boolean isAgree = false;

    public void setWebViewURL(String webViewURL) {
        this.webViewURL = webViewURL;
    }


    /**
     * Dialog展示 网页服务协议
     * @param mContext
     * @return
     */
    private Dialog showLicenseChoiceDialog(final Context mContext) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_license_choice, null);
        final Dialog dialog = new Dialog(mContext, R.style.mystyle);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.layout_rule:
                        if (isAgree) {
                            isAgree = false;
                            ImageView imageView = (ImageView) v.findViewById(R.id.iv_choose);
//                            TextView textView = (TextView)v.findViewById(R.id.tv_rule);
                            imageView.setImageResource(R.drawable.icon_choose);
//                            textView.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
//                            textView.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
                        } else {
                            isAgree = true;
                            ImageView imageView = (ImageView) v.findViewById(R.id.iv_choose);
//                            TextView textView = (TextView)v.findViewById(R.id.tv_rule);
                            imageView.setImageResource(R.drawable.icon_choose_selected);
//                            textView.setBackgroundResource(R.drawable.btn_select_base_shape_0);
//                            textView.setTextColor(getResources().getColor(R.color.white_color));
                        }
                        break;
                }
                if (onLicenseDialogClickListener != null) {
                    onLicenseDialogClickListener.onClick(v, isAgree);
                }
            }
        };

        TitleBar titleBar;
        SwipeRefreshLayout swipeLayout;
        final WebView webView;
        ProgressBar progressBar;
        LinearLayout layoutRule;
        LinearLayout layoutConfirm;


        titleBar = (TitleBar) convertView.findViewById(R.id.title_bar);
        swipeLayout = (SwipeRefreshLayout) convertView.findViewById(R.id.swipe_layout);
        webView = (WebView) convertView.findViewById(R.id.webView);
        progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
        layoutRule = (LinearLayout) convertView.findViewById(R.id.layout_rule);
        layoutConfirm = (LinearLayout) convertView.findViewById(R.id.layout_confirm);


        WebChromeClient client = new AppChromeWebClient(titleBar, progressBar, swipeLayout);
        webView.setWebChromeClient(client);
        webView.setWebViewClient(new AppWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(webViewURL);
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });

        titleBar.initTitleBarInfo("", R.drawable.arrow_left, -1, "", "");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();   //后退
                    return;    //已处理
                }

                if (onLicenseDialogClickListener != null) {
                    onLicenseDialogClickListener.onClick(v, isAgree);
                    dismiss();
                }
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
        UIUtils.initSwipeRefreshLayout(swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });


        layoutConfirm.setOnClickListener(listener);
        layoutRule.setOnClickListener(listener);

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


    //购买车险 时 选择城市
    private List<RegionBean> regionBeans = new ArrayList<>();
    private String titleText = "";
    RegionListAdapter adapter;
    TextView tvTitle;
    ListView listView;
    LinearLayout layoutBack;

    //you should setData first,and then show dialog
    public void setData(List<RegionBean> regionBeans, String titleText) {
        this.regionBeans = regionBeans;
        this.titleText = titleText;
    }

    public void updateData(List<RegionBean> regionBeans, String text) {
        this.titleText = text;

        if (tvTitle != null) {
            tvTitle.setText(this.titleText);
        }
        adapter = new RegionListAdapter(regionBeans, mContext);
        listView.setAdapter(adapter);


    }

    private Dialog showRegionChoiceDialog(final Context mContext) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_region_choice, null);
        final Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.layout_back:
                        if (onDialogBackClickLisenter != null) {
                            onDialogBackClickLisenter.onClickBack(titleText, regionBeans, v);
                        }
                        break;
                }
            }
        };

        tvTitle = (TextView) convertView.findViewById(R.id.tv_region_name);
        listView = (ListView) convertView.findViewById(R.id.list_view);
        layoutBack = (LinearLayout) convertView.findViewById(R.id.layout_back);
        adapter = new RegionListAdapter(regionBeans, mContext);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onDialogBackClickLisenter != null) {
                    onDialogBackClickLisenter.onItemClickListener(position, id);
                }

            }
        });

        tvTitle.setText(titleText);
        layoutBack.setOnClickListener(listener);

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


    //WebView 相关
    private static final class AppChromeWebClient extends WebChromeClient {
        private ProgressBar bar;
        private SwipeRefreshLayout swipeRefreshLayout;
        private TitleBar titleBar;

        public AppChromeWebClient(TitleBar titleBar, ProgressBar bar, SwipeRefreshLayout swipeRefreshLayout) {
            this.bar = bar;
            this.swipeRefreshLayout = swipeRefreshLayout;
            this.titleBar = titleBar;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress < 100) {

            } else {
                if (bar.getVisibility() == View.VISIBLE) {
                    bar.setVisibility(View.GONE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            titleBar.updateTitle(title);
        }
    }

    private static final class AppWebClient extends WebViewClient {

    }

}
