package com.jsb.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.jsb.util.SpannableStringUtil;
import com.jsb.widget.DecoView.decoviewlib.DecoView;
import com.jsb.widget.DecoView.decoviewlib.charts.SeriesItem;
import com.jsb.widget.DecoView.decoviewlib.events.DecoEvent;
import com.jsb.ui.BrowserActivity;
import com.jsb.ui.PullMoneyActivity;
import com.jsb.ui.TimePickerActivity;
import com.jsb.util.PreferenceUtil;
import com.jsb.widget.TitleBar;


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
    private TextView layout_rule;
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


    private float maxNumber = 0;//动画的 数字最大值
    private float realNumber = 0;//动画的 数字最大值


    private String startTimeStr;
    private String endTimeStr;
    private String timeIntvalStr;
    private String timeStringFroServer;//时间控件，服务端要求的时间控件 时间字符串 格式


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

    private boolean weekSpinnerTriggeredByCode = true;//第一次进入周Spinner不进行其他处理

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
        titleBar.initTitleBarInfo(StringConstant.shutInsure, -1, -1, StringConstant.empty, StringConstant.share);
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {

            }

            @Override
            public void onRightButtonClick(View v) {
                Toast.makeText(getActivity(), "hello,share", Toast.LENGTH_SHORT).show();
            }
        });


        //提现
        tvPullMoney = (TextView) view.findViewById(R.id.tv_pull_money);
        tvPullMoney.setOnClickListener(new View.OnClickListener() {
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
                                ((TextView) tv_title).setText("您需要登录操作才能提现哦！\n是否现在就去登录？");
                            }

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else if (TextUtils.isEmpty(outerPwdString)) {
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

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {
                    context.startActivity(new Intent(context, PullMoneyActivity.class));
                }
            }
        });


        //停保规则
        layout_rule = (TextView) view.findViewById(R.id.layout_rule);
        layout_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserActivity.startActivity(getActivity(), true);
            }
        });


        //预约停保  的textView，显示开始和结束的时间文本和时间间隔
        startTimeStr = PreferenceUtil.load(this.getActivity(), PreferenceConstant.TimePickerDateStart, "");
        endTimeStr = PreferenceUtil.load(this.getActivity(), PreferenceConstant.TimePickerDateEnd, "");
        timeIntvalStr = PreferenceUtil.load(this.getActivity(), PreferenceConstant.TimePickerDateInterval, "");

        //服务端要求的 上传预约日期  的时间格式
        timeStringFroServer = PreferenceUtil.load(this.getActivity(), PreferenceConstant.timeStringFroServer, "");


        //预约停保 的文本时间显示，表达用户选择的时间
        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date);
        tv_date_interval = (TextView) view.findViewById(R.id.tv_date_interval);

        tv_start_date.setText(startTimeStr);
        tv_end_date.setText(endTimeStr);
        tv_date_interval.setText(timeIntvalStr);


        //若果没有值 ，用户首次登陆时，显示的内容
        if (TextUtils.isEmpty(startTimeStr)) {
            tv_start_date.setText(todayString_yyyy_m_d + "");
            tv_end_date.setText(todayString_yyyy_m_d + "");
            tv_date_interval.setText("共0天");
        }


        //点击预约停保的时间选择控件，进入自定义的时间控件
        datePickerLayout = (LinearLayout) view.findViewById(R.id.layout_date_picker);
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

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {
                    Intent intent = new Intent(getActivity(), TimePickerActivity.class);
                    long startTime = 0;
                    long endTime = 0;
                    if (outerPauseBean != null) {
                        if (outerPauseBean.getStartdate() != null) {
                            startTime = outerPauseBean.getStartdate();
                        }
                        if (outerPauseBean.getEnddate() != null) {
                            endTime = outerPauseBean.getEnddate();
                        }
                    }
                    intent.putExtra(StringConstant.orderStartTimeFromServer, startTime);
                    intent.putExtra(StringConstant.orderEndTimeFromServer, endTime);
                    getActivity().startActivityForResult(intent, ACTION_CHOOSE_TIME);
                }
            }
        });


        //车牌号Spinner 点击事件由父View 触发
        layout_car_number_spinner = (LinearLayout) view.findViewById(R.id.layout_car_number_spinner);
        layout_car_number_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car_number_spinner.performClick();
            }
        });

        //车牌号Spinner
        car_number_spinner = (Spinner) view.findViewById(R.id.car_number_spinner);
        mCarNumbersListAdapter = new SpinnerDropDownAdapter(getActivity(), mCarNumbersStringList);
        car_number_spinner.setAdapter(mCarNumbersListAdapter);
        car_number_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //如果用户点击切换了车牌号，则刷新主界面
                    String carNumberStr = mCarNumbersStringList.get(position);
                    for (PauseData bean : mPauseDataList) {
                        if (carNumberStr.equals(bean.getLicenseplate())) {
                            refreshData(bean);
                            break;
                        }
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //限行停保的 Spinner 的父View ，选择周几， 点击事件由父View 触发
        layout_week_number_spinner = (LinearLayout) view.findViewById(R.id.layout_week_number_spinner);
        layout_week_number_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week_number_spinner.performClick();
            }
        });
        //限行停保的 Spinner
        week_number_spinner = (Spinner) view.findViewById(R.id.week_number_spinner);
        mWeekNumbersStringList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.weekArray)));
        mWeekNumbersListAdapter = new SpinnerDropDownAdapter(getActivity(), mWeekNumbersStringList);
        week_number_spinner.setAdapter(mWeekNumbersListAdapter);
        week_number_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //传出 position，在其他地方网络请求时，使用这个position，用于确定是周几
                outerWeekthPosition = position;

                //如果是程序员代码指定了item，如调用方法setSelection（），那么不处理这一动作，直接跳出，只是获取position
                if (weekSpinnerTriggeredByCode) {
                    return;
                }

                //如果是真实用户的操作，才会进入下面的逻辑
                //先取消
                cancelPause(IntConstant.cancelPauseType_LimitPause);
                //再保存
                saveLimitPauseInfo();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //限行停保 滑动按钮
        weekSwitchTabView = (Switch) view.findViewById(R.id.week_switch_tab_view);
        weekSwitchTabView.setChecked(false);
        tv_xxtb.setAlpha(0.5f);
        layout_week_number_spinner.setAlpha(0.5f);
        layout_week_number_spinner.setClickable(false);
        weekSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //如果不是人为按下的就不进入监听器
                if (isSwitchTouchedOrTriggeredByCode) return;

                //通过userid来判断用户是否登录，-1就是未登录状态,弹出登录对话框
                if (outerUserId < 0) {
                    if (isChecked) {
                        //实际上，不打开
                        isSwitchTouchedOrTriggeredByCode = true;
                        weekSwitchTabView.setChecked(false);
                        isSwitchTouchedOrTriggeredByCode = false;
                    } else {
                        //实际上，不打开
                        isSwitchTouchedOrTriggeredByCode = true;
                        weekSwitchTabView.setChecked(true);
                        isSwitchTouchedOrTriggeredByCode = false;
                    }
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

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                }
                //如果没有设置过密码
               else if (TextUtils.isEmpty(outerPwdString)) {

                    if (isChecked) {
                        //实际上，不打开
                        isSwitchTouchedOrTriggeredByCode = true;
                        weekSwitchTabView.setChecked(false);
                        isSwitchTouchedOrTriggeredByCode = false;
                    } else {
                        //实际上，不关闭
                        isSwitchTouchedOrTriggeredByCode = true;
                        weekSwitchTabView.setChecked(true);
                        isSwitchTouchedOrTriggeredByCode = false;
                    }

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

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                }

                else {

                    //否则进入
                    if (isChecked) {

                        isSwitchTouchedOrTriggeredByCode = true;
                        weekSwitchTabView.setChecked(false);
                        isSwitchTouchedOrTriggeredByCode = false;

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
                                    public void onFailure(RetrofitError error) {
                                        progressDialogUtil.hide();
                                    }

                                    @Override
                                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                        progressDialogUtil.hide();
                                        if (stringNetWorkResultBean.getMessage().equals(StringConstant.failure)) {
                                            Toast.makeText(context, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "验证成功！", Toast.LENGTH_SHORT).show();
                                            dialogFragmentController.dismiss();
                                            tv_xxtb.setAlpha(1);//如果滑动开关 选中 要调整 UI 的透明度 和可点击
                                            layout_week_number_spinner.setAlpha(1);
                                            layout_week_number_spinner.setClickable(true);
                                            layout_week_number_spinner.setEnabled(true);

                                            //如果密码验证成功才真正去打开
                                            isSwitchTouchedOrTriggeredByCode = true;
                                            weekSwitchTabView.setChecked(true);
                                            isSwitchTouchedOrTriggeredByCode = false;

                                            saveLimitPauseInfo();

                                        }

                                    }
                                });
                            }
                        });
                        dialogFragmentController.showDialog(context, DialogFragmentCreater.DialogShowInputPasswordDialog);

                    } else {

                        isSwitchTouchedOrTriggeredByCode = true;
                        weekSwitchTabView.setChecked(true);
                        isSwitchTouchedOrTriggeredByCode = false;


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
                                    public void onFailure(RetrofitError error) {
                                        progressDialogUtil.hide();
                                    }

                                    @Override
                                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                        progressDialogUtil.hide();
                                        if (stringNetWorkResultBean.getMessage().equals(StringConstant.failure)) {
                                            Toast.makeText(context, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "验证成功！", Toast.LENGTH_SHORT).show();
                                            dialogFragmentController.dismiss();

                                            tv_xxtb.setAlpha(0.5f);
                                            layout_week_number_spinner.setAlpha(0.5f);
                                            layout_week_number_spinner.setClickable(false);
                                            layout_week_number_spinner.setEnabled(false);

                                            //如果密码验证成功才真正去关闭
                                            isSwitchTouchedOrTriggeredByCode = true;
                                            weekSwitchTabView.setChecked(false);
                                            isSwitchTouchedOrTriggeredByCode = false;


                                            //取消限行停保的记录
                                            cancelPause(IntConstant.cancelPauseType_LimitPause);

                                        }

                                    }
                                });
                            }
                        });
                        dialogFragmentController.showDialog(context, DialogFragmentCreater.DialogShowInputPasswordDialog);
                    }
                }
            }

        });

        // 滑动按钮-选择预约停保的时间
        dateSwitchTabView = (Switch) view.findViewById(R.id.date_switch_tab_view);
        dateSwitchTabView.setChecked(false);//默认是关闭
        tv_yytb.setAlpha(0.5f);
        datePickerLayout.setAlpha(0.5f);
        datePickerLayout.setClickable(false);
        dateSwitchTabView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //如果不是人为按下的就不进入监听器
                if (isSwitchTouchedOrTriggeredByCode) return;

                //通过userid来判断用户是否登录，-1就是未登录状态,弹出登录对话框
                if (outerUserId < 0) {
                    if (isChecked) {
                        //实际上，不打开
                        isSwitchTouchedOrTriggeredByCode = true;
                        dateSwitchTabView.setChecked(false);
                        isSwitchTouchedOrTriggeredByCode = false;
                    }else {
                        //实际上，不打开
                        isSwitchTouchedOrTriggeredByCode = true;
                        dateSwitchTabView.setChecked(true);
                        isSwitchTouchedOrTriggeredByCode = false;
                    }
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

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                }
                //如果没有密码，则弹出让用户去设置密码
                else if (TextUtils.isEmpty(outerPwdString)) {
                    if (isChecked) {
                        //实际上，不打开
                        isSwitchTouchedOrTriggeredByCode = true;
                        dateSwitchTabView.setChecked(false);
                        isSwitchTouchedOrTriggeredByCode = false;

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

                            }
                        });
                        dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                    } else {
                        //实际上，不关闭
                        isSwitchTouchedOrTriggeredByCode = true;
                        dateSwitchTabView.setChecked(true);
                        isSwitchTouchedOrTriggeredByCode = false;
                    }
                } else {
                    //否则进入密码验证
                    if (isChecked) {

                        //先关闭，等待后面的网络请求
                        isSwitchTouchedOrTriggeredByCode = true;
                        dateSwitchTabView.setChecked(false);
                        isSwitchTouchedOrTriggeredByCode = false;

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
                                    public void onFailure(RetrofitError error) {
                                        progressDialogUtil.hide();
                                    }

                                    @Override
                                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                        progressDialogUtil.hide();
                                        if (stringNetWorkResultBean.getMessage().equals(StringConstant.failure)) {
                                            Toast.makeText(context, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                                        } else {
                                            //如果密码验证成功才真正去打开
                                            isSwitchTouchedOrTriggeredByCode = true;
                                            dateSwitchTabView.setChecked(true);
                                            isSwitchTouchedOrTriggeredByCode = false;

                                            Toast.makeText(context, "验证成功！", Toast.LENGTH_SHORT).show();
                                            dialogFragmentController.dismiss();
                                            tv_yytb.setAlpha(1);
                                            datePickerLayout.setAlpha(1);
                                            datePickerLayout.setClickable(true);
                                            datePickerLayout.setEnabled(true);

                                            //保存预约停保
                                            saveReservePauseInfo();
                                        }

                                    }
                                });
                            }
                        });
                        dialogFragmentController.showDialog(context, DialogFragmentCreater.DialogShowInputPasswordDialog);

                    } else {


                        //先打开，等待后面的网络请求
                        isSwitchTouchedOrTriggeredByCode = true;
                        dateSwitchTabView.setChecked(true);
                        isSwitchTouchedOrTriggeredByCode = false;

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
                                    public void onFailure(RetrofitError error) {
                                        progressDialogUtil.hide();
                                    }

                                    @Override
                                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                        progressDialogUtil.hide();
                                        if (stringNetWorkResultBean.getMessage().equals(StringConstant.failure)) {
                                            Toast.makeText(context, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                                        } else {
                                            //如果密码验证成功才真正去关闭
                                            isSwitchTouchedOrTriggeredByCode = true;
                                            dateSwitchTabView.setChecked(false);
                                            isSwitchTouchedOrTriggeredByCode = false;

                                            Toast.makeText(context, "验证成功！", Toast.LENGTH_SHORT).show();
                                            dialogFragmentController.dismiss();

                                            tv_yytb.setAlpha(0.5f);
                                            datePickerLayout.setAlpha(0.5f);
                                            datePickerLayout.setClickable(false);
                                            datePickerLayout.setEnabled(false);

                                            //预约停保的取消
                                            cancelPause(IntConstant.cancelPauseType_ReservePause);
                                        }

                                    }
                                });
                            }
                        });
                        dialogFragmentController.showDialog(context, DialogFragmentCreater.DialogShowInputPasswordDialog);
                    }
                }
            }
        });

        if(outerUserId>0) {
            //获取停保信息
            getPauseInfo(context, outerUserId);
        }else {
            //没有登录的时候，累计节省保费的动画
            createTracksWithNoValue();
            //车牌号码
            layout_car_number_spinner.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * 保存预约停保
     */
    private void saveReservePauseInfo() {
        //暂时使用 userid = 1的记录，release的时候，使用outerUserid
        if (outerPauseBean != null) {
            UserRetrofitUtil.saveReservePauseInfo(context, 1, outerPauseBean.getOrderid(), outerPauseBean.getPausePrice(), timeStringFroServer, new NetCallback<NetWorkResultBean<String>>(context) {
                @Override
                public void onFailure(RetrofitError error) {

                }

                @Override
                public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

                }
            });
        }
    }


    /**
     * 保存限行停保
     */
    private void saveLimitPauseInfo() {
        //暂时使用 userid = 1的记录
        if (outerPauseBean != null) {
            UserRetrofitUtil.saveLimitPauseInfo(context, 1, outerPauseBean.getOrderid(), outerPauseBean.getPausePrice(), outerWeekthPosition + 1, new NetCallback<NetWorkResultBean<String>>(context) {
                @Override
                public void onFailure(RetrofitError error) {
                }

                @Override
                public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

                }
            });
        }
    }

    /**
     * 发送时机	用户选择取消暂停调用
     * @param type 0限行取消   1预约取消
     */
    public void cancelPause(int type) {
        if (outerPauseBean != null) {
            UserRetrofitUtil.cancelPause(context, 1, outerPauseBean.getOrderid(), type, new NetCallback<NetWorkResultBean<String>>(context) {
                @Override
                public void onFailure(RetrofitError error) {

                }

                @Override
                public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

                }
            });
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            context = ShutInsureFragment.this.getActivity();
            outerPwdString = PreferenceUtil.load(context, PreferenceConstant.pwd, "");//提现等操作密码
            //用户的id
            outerUserId = PreferenceUtil.load(context, PreferenceConstant.userid, -1);
            if (outerUserId < 0) {
                outerPauseBean = null;
                mCarNumbersStringList.clear();
                if(dateSwitchTabView!=null&&weekSwitchTabView!=null) {
                    isSwitchTouchedOrTriggeredByCode = true;
                    weekSwitchTabView.setChecked(false);
                    dateSwitchTabView.setChecked(false);
                    isSwitchTouchedOrTriggeredByCode = false;
                }
                //车牌号
                if(layout_car_number_spinner!=null)
                {
                    layout_car_number_spinner.setVisibility(View.INVISIBLE);
                }

                //没有登录时的可用停保余额
                if(tv_usefulPauseFee!=null)
                {
                    tv_usefulPauseFee.setText("¥0");
                }

                //没有登录时的每日停保费用
                if(tv_pausePrice!=null)
                {
                    tv_pausePrice.setText("¥0");
                }

                //没有登录时的累计节省保费
                if(tv_follow_decoView!=null)
                {
                    createTracksWithNoValue();
                    tv_follow_decoView.setText("¥0");
                }
            }
        }
    }

    /**
     * 当没有值或者没有登录的时候的动画
     */
    private void createTracksWithNoValue()
    {
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
    private void addFitListener(@NonNull final SeriesItem seriesItem, @NonNull final TextView view) {
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
     *
     * @param event
     */
    public void onEventMainThread(BusEvent event) {
        switch (event.getMsg()) {
            case BusEvent.MSG_INT_TIME:
                startTimeStr = event.getStart_time();
                endTimeStr = event.getEnd_time();
                timeIntvalStr = event.getInterval_time();

                timeStringFroServer = event.getTimeStringFroServer();

                tv_start_date.setText(startTimeStr);
                tv_end_date.setText(endTimeStr);
                tv_date_interval.setText(timeIntvalStr);

                //若果没有值，就不显示
                if (TextUtils.isEmpty(startTimeStr)) {
                    tv_start_date.setText(todayString_yyyy_m_d + "");
                    tv_end_date.setText(todayString_yyyy_m_d + "");
                    tv_date_interval.setText("共0天");
                }
                PreferenceUtil.save(this.getActivity(), PreferenceConstant.TimePickerDateStart, startTimeStr);
                PreferenceUtil.save(this.getActivity(), PreferenceConstant.TimePickerDateEnd, endTimeStr);
                PreferenceUtil.save(this.getActivity(), PreferenceConstant.TimePickerDateInterval, timeIntvalStr);
                PreferenceUtil.save(this.getActivity(), PreferenceConstant.timeStringFroServer, timeStringFroServer);

                //保存预约停保的信息
                saveReservePauseInfo();

                break;
            default:
                break;
        }
    }


    private void getPauseInfo(final Context context, int userid) {
        UserRetrofitUtil.getPauseInfo(context, 1, new NetCallback<NetWorkResultBean<List<PauseData>>>(context) {
            @Override
            public void onFailure(RetrofitError error) {
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
                    refreshData(outerPauseBean);
                }
            }
        });
    }


    public void refreshData(PauseData bean) {
        if (bean.getLimitday() != null) {
            weekSpinnerTriggeredByCode = true;
            week_number_spinner.setSelection(bean.getLimitday() - 1);
            weekSpinnerTriggeredByCode = false;

        } else {
            weekSpinnerTriggeredByCode = true;
            week_number_spinner.setSelection(0);
            weekSpinnerTriggeredByCode = false;
        }
        if (bean.getPausePrice() != null) {
            tv_pausePrice.setText("¥" + bean.getPausePrice() + "");
        }

        if (bean.getUsefulPauseFee() != null) {
            tv_usefulPauseFee.setText("¥" + bean.getUsefulPauseFee() + "");
        }

        //开始动画
        realNumber = bean.getTotalPauseFee();
        maxNumber = (float) DiditUtil.getMaxInteger(realNumber);
        createAnimation();
    }
}
