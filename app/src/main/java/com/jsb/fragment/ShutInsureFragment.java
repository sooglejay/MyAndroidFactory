package com.jsb.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.SpinnerDropDownAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.IntConstant;
import com.jsb.constant.PreferenceConstant;
import com.jsb.constant.StringConstant;
import com.jsb.event.BusEvent;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.PauseData;
import com.jsb.ui.LoginActivity;
import com.jsb.ui.MyModifyPasswordActivity;
import com.jsb.util.DiditUtil;
import com.jsb.util.ProgressDialogUtil;
import com.jsb.util.ShareUtilsTest;
import com.jsb.util.SpannableStringUtil;
import com.jsb.widget.decoview.decoviewlib.DecoView;
import com.jsb.widget.decoview.decoviewlib.charts.SeriesItem;
import com.jsb.widget.decoview.decoviewlib.events.DecoEvent;
import com.jsb.ui.BrowserActivity;
import com.jsb.ui.stopinsurance.PullMoneyActivity;
import com.jsb.ui.stopinsurance.TimePickerActivity;
import com.jsb.util.PreferenceUtil;
import com.jsb.widget.TitleBar;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 停保险-主框架-tab1
 */
public class ShutInsureFragment extends DecoViewBaseFragment {
    public static final int ACTION_CHOOSE_TIME = 1000;

    private TitleBar titleBar;
    private TextView tvPullMoney;

    private Spinner week_number_spinner;
    private Spinner car_number_spinner;

    private List<String> mCarNumbersStringList = new ArrayList<>();
    private List<String> mWeekNumbersStringList = new ArrayList<>();
    private SpinnerDropDownAdapter mCarNumbersListAdapter;
    private SpinnerDropDownAdapter mWeekNumbersListAdapter;

    private List<PauseData> mPauseDataList = new ArrayList<>();
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_date_interval;
    private Switch weekSwitchTabView;
    private Switch dateSwitchTabView;
    private LinearLayout datePickerLayout;

//spinner  :if has not invoked setCheck(position) ,then,its itemSelectedListener will never be invoked,even if has been set itemSelectedlistener


    private boolean isWeekSpinnerControlledByCode = false;//是否是代码设置Spinner

    private float maxNumber = 0;//动画的 数字最大值
    private float realNumber = 0;//动画的 数字最大值


    private String startTimeStr = "";
    private String endTimeStr = "";
    private String timeIntvalStr = "";
    private String timeStringForPostToServer = "";//时间控件，服务端要求的时间控件 时间字符串 格式


    private SimpleDateFormat dateFormat_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
    private String todayString_yyyy_m_d = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    //圆形动画
    private String mProgress;//  进度条
    private int mBigCircleSeriesIndex;//外围大环序列号
    private TextView tv_follow_decoView;//TextView 显示进度

    private SpannableString unitSpanString;

    private TextView tv_yytb;//
    private TextView tv_xxtb;//

    private LinearLayout layout_week_number_spinner;//
    private LinearLayout layout_car_number_spinner;//

    private TextView tv_pausePrice;//
    private TextView tv_usefulPauseFee;//

    private PauseData outerPauseBean;//这个是作为首页的公用 停保对象，作为用户正在使用的停保对象
    private int outerWeekthPosition = 0;//这个是作为首页的公用 限行停保 周几字符串，请求网络的时候使用这个外部对象
    //保存限行停保的记录
    private int outerUserId;//作为公用的 用户id 变量


    private AdapterView.OnItemSelectedListener myCarSpinnerItemSelectedListener, myWeekSpinnerItemSelectedListener;


    private Context context;//作为公用的 上下文引用，必须在Activity的UI初始化前对其初始化

    private ProgressDialogUtil progressDialogUtil;//阻塞用户操作

    private boolean isSwitchTouchedOrTriggeredByCode = false;//Switch 的 状态位，标识用户操作还是代码操作


    private String outerPwdString;//提现，开启/关闭滑动按钮等重要操作需要 输入密码

    private DialogFragmentCreater dialogFragmentController;//注意清除掉 mPasswordString 才能公用

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
        setUpLisenter();
    }

    private void setUpLisenter() {

        //titleBar 的点击事件
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {

            }

            @Override
            public void onRightButtonClick(View v) {
                ShareUtilsTest shareUtilsTest = new ShareUtilsTest();
                shareUtilsTest.addCustomPlatforms(ShutInsureFragment.this.getActivity());
            }
        });


        //预约停保  时间控件 点击事件
        datePickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过userid来判断用户是否登录，-1就是未登录状态
                if (outerUserId < 0) {
                    dialogFragmentController.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                LoginActivity.startLoginActivity(context);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("您需要登录操作才能操作哦！\n是否现在就去登录？");
                            }
                            tv_content.setVisibility(View.GONE);

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {

                    long orderStartDateFromServer=0,orderEndDateFromServer=0;
                    if(outerPauseBean!=null)
                    {
                        if(outerPauseBean.getStartdate()!=null&&outerPauseBean.getEnddate()!=null)
                        {
                            orderStartDateFromServer = outerPauseBean.getStartdate();
                            orderEndDateFromServer = outerPauseBean.getEnddate();
                        }
                    }
                    Intent intent = new Intent(getActivity(), TimePickerActivity.class);
                    intent.putExtra(StringConstant.orderStartTimeFromServerString, startTimeStr);
                    intent.putExtra(StringConstant.orderEndTimeFromServerString, endTimeStr);

                    intent.putExtra(StringConstant.orderStartTimeFromServerLong, orderStartDateFromServer);
                    intent.putExtra(StringConstant.orderEndTimeFromServerLong, orderEndDateFromServer);
                    getActivity().startActivityForResult(intent, ACTION_CHOOSE_TIME);
                }
            }
        });


        //用户点击 提现
        tvPullMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判断用户是否已经登录；通过userid来判断用户是否登录，-1就是未登录状态
                if (outerUserId < 0) {
                    //如果没有登录，就弹出是否登录的提醒对话框
                    dialogFragmentController.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                LoginActivity.startLoginActivity(context);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("您需要登录操作才能提现哦！\n是否现在就去登录？");
                            }
                            tv_content.setVisibility(View.GONE);
                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                }
                //如果已经登录，再判断是否设置过密码
                else if (TextUtils.isEmpty(outerPwdString)) {
                    //如果没有设置过密码，即密码为空，就弹出设置密码的提醒对话框
                    dialogFragmentController.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                        @Override
                        public void viewClick(String tag) {
                            if (tag.equals(StringConstant.tv_confirm)) {
                                MyModifyPasswordActivity.startModifyPasswordActivity(getActivity(), StringConstant.setPassword);
                            }
                        }

                        @Override
                        public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                            if (tv_title instanceof TextView) {
                                ((TextView) tv_title).setText("您还没有设置密码，是否现在去设置");
                            }
                            tv_content.setVisibility(View.GONE);

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                }

                else {
                    //弹出输入密码对话框，输入密码正确才能提现
                    dialogFragmentController.setOnPasswordDialogClickListener(new DialogFragmentCreater.OnPasswordDialogClickListener() {
                        @Override
                        public void getPassword(View v1, View v2, View v3, View v4, View v5, View v6) {
                            progressDialogUtil.show("正在验证密码...");
                            String p1 = ((EditText) v1).getText().toString();
                            String p2 = ((EditText) v2).getText().toString();
                            String p3 = ((EditText) v3).getText().toString();
                            String p4 = ((EditText) v4).getText().toString();
                            String p5 = ((EditText) v5).getText().toString();
                            String p6 = ((EditText) v6).getText().toString();
                            String passwordStr = p1 + p2 + p3 + p4 + p5 + p6;

                            String phoneStr = PreferenceUtil.load(context, PreferenceConstant.phone, "");

                            UserRetrofitUtil.verifyPwd(context, phoneStr, passwordStr, new NetCallback<NetWorkResultBean<String>>(context) {
                                @Override
                                public void onFailure(RetrofitError error,String message) {
                                    progressDialogUtil.hide();
                                    if(!TextUtils.isEmpty(message)) {
                                        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                    progressDialogUtil.hide();
                                    if (stringNetWorkResultBean.getMessage().equals(StringConstant.failure)) {
                                        Toast.makeText(context, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "验证成功！", Toast.LENGTH_SHORT).show();
                                        dialogFragmentController.dismiss();
                                        context.startActivity(new Intent(context, PullMoneyActivity.class));
                                    }

                                }
                            });
                        }

                        @Override
                        public void onDialogDismiss(EditText view) {
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }
                    });
                    dialogFragmentController.showDialog(context, DialogFragmentCreater.DialogShowInputPasswordDialog);


                }
            }
        });




        //车牌号Layout ，包裹Spinner，并且设置Layout 的点击事件，触发Spinner 的下拉操作
        layout_car_number_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car_number_spinner.performClick();
            }
        });

        myCarSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //如果用户点击切换了车牌号，则刷新主界面
                String carNumberStr = mCarNumbersStringList.get(position);
                Log.e("jwjw", "执行到了车牌号选中");
                for (PauseData bean : mPauseDataList) {
                    if (carNumberStr.equals(bean.getLicenseplate())) {
                        //刷新车牌号为选中的字符串的停保记录
                        outerPauseBean = bean;
                        Log.d("Retrofit", "执行 到 车牌号 这个坑爹的代码行了，注意啊啊啊啊啊啊");
                        refreshData(outerPauseBean);
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        myWeekSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //传出 position，在其他地方网络请求时，使用这个position，用于传递给服务端，周几

                if (isWeekSpinnerControlledByCode) {
                    isWeekSpinnerControlledByCode = false;
                    Log.d("Retrofit", "行号 316 上一行代码是 isWeekSpinnerControlledByCode = false;");
                    return;
                }
                outerWeekthPosition = position;
                Log.d("Retrofit", "myWeekSpinnerItemSelectedListener 行号 320 上一行代码是 }");
                //如果是真实用户的操作，才会进入下面的逻辑
                //保存限行停保的信息
                if(outerUserId>0)
                {
                    saveLimitPauseInfo(IntConstant.cancelPauseType_LimitPause, outerUserId);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isWeekSpinnerControlledByCode = false;
                Log.d("Retrofit","周监听器 什么都没有选择 ");
            }
        };


        //车牌号码Spinner 的Item选中事件
        car_number_spinner.setOnItemSelectedListener(myCarSpinnerItemSelectedListener);
        //周Spinner 的下拉Item 选中事件
        week_number_spinner.setOnItemSelectedListener(myWeekSpinnerItemSelectedListener);


        //限行停保 - 周Spinner 的父Layout 的点击事件，并触发Spinner 的下拉事件
        layout_week_number_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week_number_spinner.performClick();
            }
        });


        //限行停保-周Switch 滑动监听器
        weekSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //如果是代码调用了setCheck()就直接跳出，不进行下面的逻辑
                if (isSwitchTouchedOrTriggeredByCode) {
                    Log.d("Retrofit", "行号371:上一行代码是：  isSwitchTouchedOrTriggeredByCode = false;");
                    return;
                }
                Log.d("Retrofit", "行号371:上一行代码是：}");
                //先反向设置一下Switch
                setSwitchChecked(!isChecked, weekSwitchTabView);
                //检查操作的合法性：登录与否，是否设置过密码
                checkIsLoginOrHasPassword(isChecked, weekSwitchTabView);
            }
        });


        //预约停保 Switch 滑动监听器
        dateSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //如果是代码调用了setCheck()就直接跳出，不进行下面的逻辑
                if (isSwitchTouchedOrTriggeredByCode) {
                    Log.d("Retrofit", "行号371:上一行代码是：  isSwitchTouchedOrTriggeredByCode = false;");
                    return;
                }
                Log.d("Retrofit", "行号374 上一行代码是：}");
                //先反向设置一下Switch
                setSwitchChecked(!isChecked, dateSwitchTabView);
                //检查操作的合法性：登录与否，是否设置过密码
                checkIsLoginOrHasPassword(isChecked, dateSwitchTabView);
            }
        });


        //更新预约停保的UI
        refreshReservePauseUI(false);

        //先画初始化的动画
        createTracksWithNoValue();

        //检查如果登录就刷新UI
        checkIsLoginAndRefreshUI();
    }

    /**
     * @param isChecked true 就是打开，false 就是关闭滑动开关
     */
    private void checkIsLoginOrHasPassword(final boolean isChecked, final Switch switchView) {
        //通过userid来判断用户是否登录，-1就是未登录状态,弹出登录对话框
        if (outerUserId < 0) {
            //登录对话框
            dialogFragmentController.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                @Override
                public void viewClick(String tag) {
                    if (tag.equals(StringConstant.tv_confirm)) {
                        LoginActivity.startLoginActivity(context);
                    }
                }

                @Override
                public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                    if (tv_title instanceof TextView) {
                        ((TextView) tv_title).setText("您需要登录操作才能操作哦！\n是否现在就去登录？");
                    }
                    tv_content.setVisibility(View.GONE);

                }
            });
            dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
        }
        //如果用户已经登录
        else if (TextUtils.isEmpty(outerPwdString)) {
            //但是没有设置过密码
            dialogFragmentController.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                @Override
                public void viewClick(String tag) {
                    if (tag.equals(StringConstant.tv_confirm)) {
                        MyModifyPasswordActivity.startModifyPasswordActivity(getActivity(), StringConstant.setPassword);
                    }
                }

                @Override
                public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                    if (tv_title instanceof TextView) {
                        ((TextView) tv_title).setText("您还没有设置密码，是否现在去设置");
                    }
                    tv_content.setVisibility(View.GONE);


                }
            });
            dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
        } else {
            dialogFragmentController.setOnPasswordDialogClickListener(new DialogFragmentCreater.OnPasswordDialogClickListener() {
                @Override
                public void getPassword(View v1, View v2, View v3, View v4, View v5, View v6) {
                    progressDialogUtil.show("正在验证密码...");

                    String p1 = ((EditText) v1).getText().toString();
                    String p2 = ((EditText) v2).getText().toString();
                    String p3 = ((EditText) v3).getText().toString();
                    String p4 = ((EditText) v4).getText().toString();
                    String p5 = ((EditText) v5).getText().toString();
                    String p6 = ((EditText) v6).getText().toString();
                    String passwordStr = p1 + p2 + p3 + p4 + p5 + p6;

                    String phoneStr = PreferenceUtil.load(context, PreferenceConstant.phone, "");

                    UserRetrofitUtil.verifyPwd(context, phoneStr, passwordStr, new NetCallback<NetWorkResultBean<String>>(context) {
                        @Override
                        public void onFailure(RetrofitError error,String message) {
                            progressDialogUtil.hide();
                            if(!TextUtils.isEmpty(message)) {
                                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                            progressDialogUtil.hide();
                            if (stringNetWorkResultBean.getMessage().equals(StringConstant.failure)) {
                                Toast.makeText(context, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "验证成功！", Toast.LENGTH_SHORT).show();
                                dialogFragmentController.dismiss();
                                //如果密码验证成功才真正去操作Switch
                                setSwitchChecked(isChecked, switchView);
                                if (switchView == weekSwitchTabView) {
                                    tv_xxtb.setAlpha(isChecked ? 1 : 0.5f);
                                    layout_week_number_spinner.setAlpha(isChecked ? 1 : 0.5f);
                                    layout_week_number_spinner.setClickable(isChecked);
                                    layout_week_number_spinner.setEnabled(isChecked);
                                    if (isChecked) {//如果是打开操作，就保存信息
                                        saveLimitPauseInfo(IntConstant.cancelPauseType_LimitPause, outerUserId);
                                    } else {//如果是关闭操作，就取消上一次保存的信息
                                        cancelPause(outerUserId, IntConstant.cancelPauseType_LimitPause);
                                    }
                                } else {
                                    tv_yytb.setAlpha(isChecked ? 1 : 0.5f);
                                    datePickerLayout.setAlpha(isChecked ? 1 : 0.5f);
                                    datePickerLayout.setClickable(isChecked);
                                    datePickerLayout.setEnabled(isChecked);
                                    if (isChecked) {//如果是打开操作，就保存信息
                                        saveReservePauseInfo(IntConstant.cancelPauseType_ReservePause, outerUserId);
                                    } else {//如果是关闭操作，就取消上一次保存的信息
                                        cancelPause(outerUserId, IntConstant.cancelPauseType_ReservePause);
                                    }
                                }
                            }

                        }
                    });
                }

                @Override
                public void onDialogDismiss(EditText view) {
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            });
            dialogFragmentController.showDialog(context, DialogFragmentCreater.DialogShowInputPasswordDialog);
        }
    }

    private void cancelPause(int userid, int type) {
        if (outerPauseBean != null) {
            switch (type) {
                case IntConstant.cancelPauseType_LimitPause:
                    if (outerPauseBean.getLimitPaused()) {
                        UserRetrofitUtil.cancelPause(context, userid, outerPauseBean.getOrderid(), type, new NetCallback<NetWorkResultBean<String>>(context) {
                            @Override
                            public void onFailure(RetrofitError error,String message) {
                                if(!TextUtils.isEmpty(message)) {
                                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                setSwitchChecked(false,weekSwitchTabView);
                            }
                        });
                    }
                    break;
                case IntConstant.cancelPauseType_ReservePause:
                    if (outerPauseBean.getReservePaused()) {
                        UserRetrofitUtil.cancelPause(context, userid, outerPauseBean.getOrderid(), type, new NetCallback<NetWorkResultBean<String>>(context) {
                            @Override
                            public void onFailure(RetrofitError error,String message) {
                                if(!TextUtils.isEmpty(message)) {
                                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                setSwitchChecked(false,dateSwitchTabView);
                            }
                        });
                    }
                    break;
            }
        }
    }

    public void setSwitchChecked(boolean isChecked, Switch switchView) {
        isSwitchTouchedOrTriggeredByCode = true;
        switchView.setChecked(isChecked);
        isSwitchTouchedOrTriggeredByCode = false;
    }

    private void setUp(View view, Bundle savedInstanceState) {
        context = ShutInsureFragment.this.getActivity();

        //作为Dialog的生成器
        dialogFragmentController = new DialogFragmentCreater();//涉及到权限操作时，需要临时输入密码并验证
        dialogFragmentController.setDialogContext(getActivity(), getFragmentManager());
        //人民币单位的字符
        unitSpanString = SpannableStringUtil.getSpannableString(getActivity(), "¥", 40);//单位
        //用户的密码字符串
        outerPwdString = PreferenceUtil.load(context, PreferenceConstant.pwd, "");//提现等操作密码
        //用户的id
        outerUserId = PreferenceUtil.load(context, PreferenceConstant.userid, -1);

        //包装耗时操作
        progressDialogUtil = new ProgressDialogUtil(context);

        //几个View的引用，由于 滑动开关的开闭操作设计到这些View的UI效果，使用透明度来表现
        tv_yytb = (TextView) view.findViewById(R.id.tv_yytb);
        tv_xxtb = (TextView) view.findViewById(R.id.tv_xxtb);

        //UI界面上的文本显示，每日停保费用和可用保费
        tv_usefulPauseFee = (TextView) view.findViewById(R.id.tv_usefulPauseFee);
        tv_pausePrice = (TextView) view.findViewById(R.id.tv_pausePrice);
        //圆形动画 文本跟随器，跟踪画圆圈的进度
        tv_follow_decoView = (TextView) view.findViewById(R.id.tv_follow_decoView);
        //自定义View，作为界面的顶部View，封装具体操作
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.shutInsure, -1,R.drawable.icon_share,"","");
        //提现
        tvPullMoney = (TextView) view.findViewById(R.id.tv_pull_money);
        //停保规则
        view.findViewById(R.id.layout_rule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserActivity.startActivity(getActivity(), true);
            }
        });


        //预约停保 的文本时间显示，表达用户选择的时间
        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date);
        tv_date_interval = (TextView) view.findViewById(R.id.tv_date_interval);

        //时间控件  先显示为当前时间
        tv_start_date.setText(todayString_yyyy_m_d + "");
        tv_end_date.setText(todayString_yyyy_m_d + "");
        tv_date_interval.setText("共0天");
        //时间控件
        datePickerLayout = (LinearLayout) view.findViewById(R.id.layout_date_picker);
        //车牌号Spinner的父Layout
        layout_car_number_spinner = (LinearLayout) view.findViewById(R.id.layout_car_number_spinner);
        refreshCarSpinnerLayout(false);

        //限行停保的 Spinner 的父Layout
        layout_week_number_spinner = (LinearLayout) view.findViewById(R.id.layout_week_number_spinner);

        //车牌号Spinner
        car_number_spinner = (Spinner) view.findViewById(R.id.car_number_spinner);
        mCarNumbersListAdapter = new SpinnerDropDownAdapter(getActivity(), mCarNumbersStringList);
        car_number_spinner.setAdapter(mCarNumbersListAdapter);


        //限行停保的 Spinner
        week_number_spinner = (Spinner) view.findViewById(R.id.week_number_spinner);
        mWeekNumbersStringList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.weekArray)));
        mWeekNumbersListAdapter = new SpinnerDropDownAdapter(getActivity(), mWeekNumbersStringList);
        week_number_spinner.setAdapter(mWeekNumbersListAdapter);


        //限行停保 滑动按钮
        weekSwitchTabView = (Switch) view.findViewById(R.id.week_switch_tab_view);

        //默认先设置为false,并且其父Layout设置为不可点击
        weekSwitchTabView.setChecked(false);
        refreshLimitPauseUI(false);


        // 滑动按钮-选择预约停保的时间
        dateSwitchTabView = (Switch) view.findViewById(R.id.date_switch_tab_view);
        dateSwitchTabView.setChecked(false);//默认是关闭

    }


    /**
     * 保存预约停保
     */
    private void saveReservePauseInfo(int type, final int userid) {
        if (outerPauseBean != null) {
            if(TextUtils.isEmpty(timeStringForPostToServer))
            {
                return;
            }
            if (outerPauseBean.getReservePaused()) {
                UserRetrofitUtil.cancelPause(context, userid, outerPauseBean.getOrderid(), type, new NetCallback<NetWorkResultBean<String>>(context) {
                    @Override
                    public void onFailure(RetrofitError error,String message) {
                        if(!TextUtils.isEmpty(message)) {
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                        outerPauseBean.setReservePaused(false);
                        UserRetrofitUtil.saveReservePauseInfo(context, userid, outerPauseBean.getOrderid(), outerPauseBean.getPausePrice(), timeStringForPostToServer, new NetCallback<NetWorkResultBean<String>>(context) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {
                                if (!TextUtils.isEmpty(message)) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                outerPauseBean.setReservePaused(true);
                                setSwitchChecked(true, dateSwitchTabView);

                            }
                        });

                    }
                });
            } else if (!outerPauseBean.getReservePaused()) {
                UserRetrofitUtil.saveReservePauseInfo(context, userid, outerPauseBean.getOrderid(), outerPauseBean.getPausePrice(), timeStringForPostToServer, new NetCallback<NetWorkResultBean<String>>(context) {
                    @Override
                    public void onFailure(RetrofitError error,String message) {
                        if(!TextUtils.isEmpty(message)) {
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                        outerPauseBean.setReservePaused(true);
                        setSwitchChecked(true, dateSwitchTabView);

                    }
                });
            }
        }
    }


    /**
     * 保存限行停保
     */
    private void saveLimitPauseInfo(int type, final int userid) {
        if (outerPauseBean != null) {
            if (outerPauseBean.getLimitPaused()) {
                UserRetrofitUtil.cancelPause(context, userid, outerPauseBean.getOrderid(), type, new NetCallback<NetWorkResultBean<String>>(context) {
                    @Override
                    public void onFailure(RetrofitError error,String message) {
                        if(!TextUtils.isEmpty(message)) {
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                        outerPauseBean.setLimitPaused(false);
                        if (week_number_spinner != null) {
                            outerWeekthPosition = week_number_spinner.getSelectedItemPosition();
                            UserRetrofitUtil.saveLimitPauseInfo(context, userid, outerPauseBean.getOrderid(), outerPauseBean.getPausePrice(), outerWeekthPosition + 1, new NetCallback<NetWorkResultBean<String>>(context) {
                                @Override
                                public void onFailure(RetrofitError error,String message) {
                                    if(!TextUtils.isEmpty(message)) {
                                        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                    outerPauseBean.setLimitPaused(true);
                                    setSwitchChecked(true, weekSwitchTabView);
                                }
                            });
                        }
                    }
                });
            } else if (!outerPauseBean.getLimitPaused()) {
                if (week_number_spinner != null) {
                    outerWeekthPosition = week_number_spinner.getSelectedItemPosition();
                    UserRetrofitUtil.saveLimitPauseInfo(context, userid, outerPauseBean.getOrderid(), outerPauseBean.getPausePrice(), outerWeekthPosition + 1, new NetCallback<NetWorkResultBean<String>>(context) {
                        @Override
                        public void onFailure(RetrofitError error,String message) {
                            if(!TextUtils.isEmpty(message)) {
                                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                            outerPauseBean.setLimitPaused(true);
                            setSwitchChecked(true, weekSwitchTabView);
                        }
                    });
                }
            }
        }
    }


    private void refreshUIAfterSignOut() {
        context = ShutInsureFragment.this.getActivity();
        outerPwdString = PreferenceUtil.load(context, PreferenceConstant.pwd, "");//提现等操作密码
        //用户的id
        outerUserId = PreferenceUtil.load(context, PreferenceConstant.userid, -1);

        if (outerUserId < 0) {
            outerPauseBean = null;
            mCarNumbersStringList.clear();
            if (dateSwitchTabView != null && weekSwitchTabView != null) {
                isSwitchTouchedOrTriggeredByCode = true;
                weekSwitchTabView.setChecked(false);
                dateSwitchTabView.setChecked(false);
                isSwitchTouchedOrTriggeredByCode = false;
            }
            //没有登录的时候，累计节省保费的动画
            createTracksWithNoValue();
            refreshCarSpinnerLayout(false);

            refreshReservePauseUI(false);
            refreshLimitPauseUI(false);

            //没有登录时的可用停保余额
            if (tv_usefulPauseFee != null) {
                tv_usefulPauseFee.setText("¥0");
            }

            //没有登录时的每日停保费用
            if (tv_pausePrice != null) {
                tv_pausePrice.setText("¥0");
            }

            //没有登录时的累计节省保费
            if (tv_follow_decoView != null) {
                tv_follow_decoView.setText("¥0");
            }

            //没有登录时的预约停保开始时间
            if (tv_start_date != null) {
                tv_start_date.setText(todayString_yyyy_m_d);

            }
            //没有登录时的预约停保结束时间
            if (tv_end_date != null) {
                tv_end_date.setText(todayString_yyyy_m_d);
            }
            //没有登录时的预约停保时间间隔
            if (tv_date_interval != null) {
                tv_date_interval.setText("共0天");
            }
        }
    }


    private void checkIsLoginAndRefreshUI() {
        context = ShutInsureFragment.this.getActivity();
        outerPwdString = PreferenceUtil.load(context, PreferenceConstant.pwd, "");//提现等操作密码
        //用户的id
        outerUserId = PreferenceUtil.load(context, PreferenceConstant.userid, -1);
        Log.e("Retrofit", "userid=" + outerUserId);
        if (outerUserId > 0) {
            //获取停保信息
            getPauseInfoAndThenRefreshData(context, outerUserId);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    private void refreshCarSpinnerLayout(boolean isVisible) {
        if (layout_car_number_spinner != null) {
            //车牌号码
            layout_car_number_spinner.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    /**
     * 当没有值或者没有登录的时候的动画
     */
    private void createTracksWithNoValue() {
        setDemoFinished(false);
        final View view = getView();
        final DecoView decoView = getDecoView();
        if (view == null || decoView == null) {
            return;
        }
        decoView.executeReset();
        decoView.deleteAll();

        final float mSeriesMax = 100f;

        SeriesItem seriesBack1Item = new SeriesItem.Builder(COLOR_BACK)
                .setRange(0, mSeriesMax, mSeriesMax)
                .setLineWidth(getDimension(6))
                .build();
        decoView.addSeries(seriesBack1Item);
        decoView.addEvent(new DecoEvent.Builder(mSeriesMax)
                .setListener(null)
                .build());


        //for  test
        realNumber = 4567.98f;
        maxNumber = 10000f;
        createAnimation();

    }

    @Override
    protected void createTracks() {
        setDemoFinished(false);
        final View view = getView();
        final DecoView decoView = getDecoView();
        if (view == null || decoView == null) {
            return;
        }

//        view.setBackgroundColor(Color.argb(255, 196, 196, 128));

        decoView.executeReset();
        decoView.deleteAll();

        final float mSeriesMax = 100f;

        SeriesItem seriesBack1Item = new SeriesItem.Builder(COLOR_BACK)
                .setRange(0, mSeriesMax, mSeriesMax)
                .setLineWidth(getDimension(6))
                .build();
        decoView.addSeries(seriesBack1Item);

        float inset = getDimension(10);
        SeriesItem smallCircle = new SeriesItem.Builder(COLOR_SMALL_CIRCLE)
                .setRange(0, mSeriesMax, mSeriesMax)
                .setInset(new PointF(inset, inset))
                .setLineWidth(getDimension(2))
                .setCapRounded(true)
                .setShowPointWhenEmpty(true)
                .build();
        decoView.addSeries(smallCircle);

        SeriesItem series1Item = new SeriesItem.Builder(COLOR_BIG_CIRCLE)
                .setRange(0, maxNumber, 0)
                .setInitialVisibility(false)
                .setLineWidth(getDimension(6))
                .setCapRounded(true)
//                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, COLOR_EDGE, 0.3f))
                .setShowPointWhenEmpty(true)
                .build();
        mBigCircleSeriesIndex = decoView.addSeries(series1Item);
        addFitListener(series1Item, tv_follow_decoView);
    }

    /**
     * 圆形滚动动画
     *
     * @param arcView
     * @param series
     * @param moveTo
     * @param delay
     * @param imageView
     * @param imageId
     * @param format
     * @param color
     * @param restart
     */
    private void addAnimation(final DecoView arcView,
                              int series, float moveTo, int delay,
                              final ImageView imageView, final int imageId,
                              final String format, final int color, final boolean restart) {

        DecoEvent.ExecuteEventListener listener = new DecoEvent.ExecuteEventListener() {
            @Override
            public void onEventStart(DecoEvent event) {
                mProgress = format;
            }

            @Override
            public void onEventEnd(DecoEvent event) {
                if (restart) {
                    setupEvents();
                }
            }
        };

        arcView.addEvent(new DecoEvent.Builder(moveTo)
                .setIndex(series)
                .setDelay(delay)
                .setDuration(5000)
                .setListener(listener)
                .setColor(color)
                .build());
    }


    @Override
    protected void setupEvents() {
        final DecoView arcView = getDecoView();
        final View view = getView();
        if (view == null || arcView == null || arcView.isEmpty()) {
            return;
        }
        addAnimation(arcView, mBigCircleSeriesIndex, realNumber, 500, null, -1, "%.2f", COLOR_BIG_CIRCLE, false);
    }

    /**
     * 圆形控件动画的 跟随监听器
     *
     * @param seriesItem
     * @param view
     */
    private void addFitListener(@NonNull final SeriesItem seriesItem,
                                @NonNull final TextView view) {
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {

            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (mProgress != null) {
                    if (mProgress.contains("%%")) {
                        view.setText(unitSpanString + String.format(mProgress, (1.0f - (currentPosition / seriesItem.getMaxValue())) * 1000f).replace('.', ','));
                    } else {
                        view.setText(unitSpanString + String.format(mProgress, currentPosition).replace('.', ','));
                    }
                }
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });
    }

    /**
     * EventBus 广播
     * @param event
     */
    public void onEventMainThread(BusEvent event) {
        switch (event.getMsg()) {
            case BusEvent.MSG_INT_TIME:
                //保存预约停保的信息
                startTimeStr = event.getStart_time();
                endTimeStr = event.getEnd_time();
                timeIntvalStr = event.getInterval_time();
                timeStringForPostToServer = event.getTimeStringFroServer();


                refreshReservePauseInsurance();
                saveReservePauseInfo(IntConstant.cancelPauseType_ReservePause, outerUserId);
                break;
            case BusEvent.MSG_Login_Success:
                checkIsLoginAndRefreshUI();
                break;
            case BusEvent.MSG_SignOut_Success:

                refreshUIAfterSignOut();
                break;
            default:
                break;
        }
    }

    public void refreshReservePauseInsurance() {
        tv_start_date.setText(startTimeStr);
        tv_end_date.setText(endTimeStr);
        tv_date_interval.setText(timeIntvalStr);
        //若果没有值，就不显示
        if (TextUtils.isEmpty(startTimeStr)) {
            tv_start_date.setText(todayString_yyyy_m_d + "");
            tv_end_date.setText(todayString_yyyy_m_d + "");
            tv_date_interval.setText("共0天");
        }
    }

    /**
     * 获取服务端 userid 的值
     *
     * @param context
     * @param userid
     */
    private void getPauseInfoAndThenRefreshData(final Context context, int userid) {
        UserRetrofitUtil.getPauseInfo(context, userid, new NetCallback<NetWorkResultBean<List<PauseData>>>(context) {
            @Override
            public void onFailure(RetrofitError error,String message) {
                if(!TextUtils.isEmpty(message)) {
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(NetWorkResultBean<List<PauseData>> objectNetWorkResultBean, Response response) {
                mPauseDataList.clear();
                mPauseDataList.addAll(objectNetWorkResultBean.getData());
                mCarNumbersStringList.clear();
                for (PauseData bean : mPauseDataList) {
                    mCarNumbersStringList.add(bean.getLicenseplate());
                }
                mCarNumbersListAdapter.notifyDataSetChanged();




                if (mPauseDataList != null && mPauseDataList.size() > 0) {
                    outerPauseBean = mPauseDataList.get(0);
                    Log.d("Retrofit", "网络请求执行refreshData（）必须的");
                    refreshData(outerPauseBean);
                }
            }
        });
    }

    /**
     * 刷新首页的停保对象
     *
     * @param bean
     */
    public void refreshData(PauseData bean) {
        if(bean!=null&&bean.getReservedays()!=null) {
            String reservedays = bean.getReservedays();
            if(reservedays.contains(",")) {
                String dateArray[] = reservedays.split(",");
                startTimeStr = todayString_yyyy_m_d;//使用今天的日期重新给其赋值
                if (dateArray.length > 0) {
                    startTimeStr = dateArray[0];
                    if (dateArray.length > 1) {
                        Log.e("Retrofit", "测试Split：reservedays:" + reservedays + "  dateArray[0]:" + dateArray[0] + " dateArray[1]:" + dateArray[1]);
                        endTimeStr = dateArray[dateArray.length - 1];
                        Log.e("Retrofit", "endTimeStr:" + endTimeStr);
                        try {
                            int e_s = (int) ((dateFormat_yyyy_mm_dd.parse(endTimeStr).getTime() - dateFormat_yyyy_mm_dd.parse(startTimeStr).getTime()) / IntConstant.milliSecondInADay);
                            timeIntvalStr = "共" + e_s + "天";
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        timeStringForPostToServer = reservedays;
                    }
                }
            }else {
                endTimeStr = startTimeStr;
                timeIntvalStr = "共0天";
                timeStringForPostToServer = reservedays;
            }
        }
        //刷新预约停保的时间文本显示
        refreshReservePauseInsurance();
        if (bean != null) {
            if (bean.getLimitday() != null) {
                isWeekSpinnerControlledByCode = true;
                Log.d("Retrofit", " 行号 972  上一行代码 isWeekSpinnerControlledByCode =true");

                int selectedPosition = bean.getLimitday() - 1;
                if(selectedPosition <1  )
                {
                    week_number_spinner.setSelection(0);
                    isWeekSpinnerControlledByCode = false;
                }else {//如果只有一个，调用setSelection方法时，不会进入itemSelectedListener方法中
                    week_number_spinner.setSelection(selectedPosition);
                }
            } else {
                isWeekSpinnerControlledByCode = true;
                Log.d("Retrofit", " 啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊行号 976  上一行代码  isWeekSpinnerControlledByCode =true");
                week_number_spinner.setSelection(0);
                isWeekSpinnerControlledByCode = false;

            }
            if (bean.getPausePrice() != null) {
                tv_pausePrice.setText("¥" + bean.getPausePrice() + "");
            }

            if (bean.getUsefulPauseFee() != null) {
                tv_usefulPauseFee.setText("¥" + bean.getUsefulPauseFee() + "");
            }

            if (bean.getReservePaused() != null) {
                refreshReservePauseUI(bean.getReservePaused());
                //如果 之前是打开的，则再一次进入页面时也要打开
                setSwitchChecked(bean.getReservePaused(), dateSwitchTabView);
            }
            if (bean.getLimitPaused() != null) {
                //刷新两种停保的ui 透明度和点击使能
                refreshLimitPauseUI(bean.getLimitPaused());
                setSwitchChecked(bean.getLimitPaused(), weekSwitchTabView);
            }

//            if (bean.getTotalPauseFee() != null) { //开始动画
//                realNumber = bean.getTotalPauseFee();
//                maxNumber = (float) DiditUtil.getMaxInteger(realNumber);
//                createAnimation();
//            }


            //更新车牌号的layout
            if (mCarNumbersStringList != null && mCarNumbersStringList.size() > 0) {
                refreshCarSpinnerLayout(true);
            }
        }


    }

    /**
     * @param isLimitedPause 为true说明之前限行停保过；false代表没有，则ui不可点击，有半透明效果
     */

    private void refreshLimitPauseUI(boolean isLimitedPause) {
        tv_xxtb.setAlpha(isLimitedPause ? 1 : 0.5f);
        layout_week_number_spinner.setAlpha(isLimitedPause ? 1 : 0.5f);
        layout_week_number_spinner.setClickable(isLimitedPause ? true : false);
        layout_week_number_spinner.setEnabled(isLimitedPause ? true : false);
    }

    /**
     * @param isReservePause 为true说明之前预约停保过；false代表没有，则ui不可点击，有半透明效果
     */
    private void refreshReservePauseUI(boolean isReservePause) {
        tv_yytb.setAlpha(isReservePause ? 1 : 0.5f);
        datePickerLayout.setAlpha(isReservePause ? 1 : 0.5f);
        datePickerLayout.setClickable(isReservePause ? true : false);
        datePickerLayout.setEnabled(isReservePause ? true : false);

    }

}
