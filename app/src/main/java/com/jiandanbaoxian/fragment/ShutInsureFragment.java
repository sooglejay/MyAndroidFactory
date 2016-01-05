package com.jiandanbaoxian.fragment;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshLinearLayout;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.SpinnerDropDownAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.ExtraConstants;
import com.jiandanbaoxian.constant.IntConstant;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.event.BusEvent;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.PauseData;
import com.jiandanbaoxian.ui.BrowserActivity;
import com.jiandanbaoxian.ui.LoginActivity;
import com.jiandanbaoxian.ui.MyModifyPasswordActivity;
import com.jiandanbaoxian.ui.stopinsurance.InComeDetailActivity;
import com.jiandanbaoxian.ui.stopinsurance.PullMoneyActivity;
import com.jiandanbaoxian.ui.stopinsurance.TimePickerActivity;
import com.jiandanbaoxian.util.DiditUtil;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.util.ShareUtilsTest;
import com.jiandanbaoxian.util.SpannableStringUtil;
import com.jiandanbaoxian.widget.TitleBar;
import com.jiandanbaoxian.widget.customswitch.SwitchButton;
import com.jiandanbaoxian.widget.decoview.decoviewlib.DecoView;
import com.jiandanbaoxian.widget.decoview.decoviewlib.charts.SeriesItem;
import com.jiandanbaoxian.widget.decoview.decoviewlib.events.DecoEvent;
import com.umeng.analytics.MobclickAgent;

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
    private List<String> mCarNumbersStringList = new ArrayList<>();
    private List<String> mWeekNumbersStringList = new ArrayList<>();
    private SpinnerDropDownAdapter mCarNumbersListAdapter;
    private SpinnerDropDownAdapter mWeekNumbersListAdapter;

    private List<PauseData> mPauseDataList = new ArrayList<>();


//spinner  :if has not invoked setCheck(position) ,then,its itemSelectedListener will never be invoked,even if has been set itemSelectedlistener


    private boolean isWeekSpinnerControlledByCode = false;//是否是代码设置Spinner

    private boolean isWeekSwitchButtonSelected = false;//增加两个标志位：如果滑动按钮选中，此时再去选择周、日期，就会请求网络。如果没有选中，则，选择周，或者日期，就不会请求网络
    private boolean isDatePickerSwitchButtonSelected = false;


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

    private SpannableString unitSpanString;


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


    PullToRefreshLinearLayout mPullRefreshLinearLayout;
    LinearLayout mTopLayout;


    private LinearLayout layoutCarNumberSpinner;
    private Spinner carNumberSpinner;
    private RelativeLayout layoutHistory;
    private DecoView dynamicArcView;
    private TextView tvDescribe;
    private TextView tvFollowDecoView;
    private TextView tvPausePrice;
    private TextView tvUsefulPauseFee;
    private TextView tvPullMoney;
    private LinearLayout layoutRule;
    private TextView tvXxtb;
    private SwitchButton weekSwitchTabView;
    private LinearLayout layoutWeekNumberSpinner;
    private Spinner weekNumberSpinner;
    private TextView tvYytb;
    private SwitchButton dateSwitchTabView;
    private LinearLayout layoutDatePicker;
    private LinearLayout layoutTotalDays;
    private TextView tvDateInterval;
    private TextView tvStartText;
    private TextView tvStartDate;
    private TextView tv00Start;
    private TextView tvEndText;
    private TextView tvEndDate;
    private TextView tv00End;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-16 13:07:31 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews(View viewFromOuter) {
        //自定义View，作为界面的顶部View，封装具体操作
        titleBar = (TitleBar) viewFromOuter.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.shutInsure, -1, -1, "", "分享");


        mPullRefreshLinearLayout = (PullToRefreshLinearLayout) viewFromOuter.findViewById(R.id.top_layout);
        mPullRefreshLinearLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<LinearLayout>() {

            @Override
            public void onRefresh(PullToRefreshBase<LinearLayout> refreshView) {
//                Random random = new Random(47);
//                maxNumber = random.nextInt(10000) + 1;
//                realNumber = maxNumber;
                createAnimation();
                getPauseInfoAndThenRefreshData(context, outerUserId);
            }
        });
        mTopLayout = mPullRefreshLinearLayout.getRefreshableView();


        layoutCarNumberSpinner = (LinearLayout) viewFromOuter.findViewById(R.id.layout_car_number_spinner);
        carNumberSpinner = (Spinner) viewFromOuter.findViewById(R.id.car_number_spinner);
        layoutHistory = (RelativeLayout) viewFromOuter.findViewById(R.id.layout_history);
        dynamicArcView = (DecoView) viewFromOuter.findViewById(R.id.dynamicArcView);
        tvDescribe = (TextView) viewFromOuter.findViewById(R.id.tv_describe);
        tvFollowDecoView = (TextView) viewFromOuter.findViewById(R.id.tv_follow_decoView);
        tvPausePrice = (TextView) viewFromOuter.findViewById(R.id.tv_pausePrice);
        tvUsefulPauseFee = (TextView) viewFromOuter.findViewById(R.id.tv_usefulPauseFee);
        tvPullMoney = (TextView) viewFromOuter.findViewById(R.id.tv_pull_money);
        layoutRule = (LinearLayout) viewFromOuter.findViewById(R.id.layout_rule);
        tvXxtb = (TextView) viewFromOuter.findViewById(R.id.tv_xxtb);
        weekSwitchTabView = (SwitchButton) viewFromOuter.findViewById(R.id.week_switch_tab_view);
        layoutWeekNumberSpinner = (LinearLayout) viewFromOuter.findViewById(R.id.layout_week_number_spinner);
        weekNumberSpinner = (Spinner) viewFromOuter.findViewById(R.id.week_number_spinner);
        tvYytb = (TextView) viewFromOuter.findViewById(R.id.tv_yytb);
        dateSwitchTabView = (SwitchButton) viewFromOuter.findViewById(R.id.date_switch_tab_view);
        layoutDatePicker = (LinearLayout) viewFromOuter.findViewById(R.id.layout_date_picker);
        layoutTotalDays = (LinearLayout) viewFromOuter.findViewById(R.id.layout_total_days);
        tvDateInterval = (TextView) viewFromOuter.findViewById(R.id.tv_date_interval);
        tvStartText = (TextView) viewFromOuter.findViewById(R.id.tv_start_text);
        tvStartDate = (TextView) viewFromOuter.findViewById(R.id.tv_start_date);
        tv00Start = (TextView) viewFromOuter.findViewById(R.id.tv_00_start);
        tvEndText = (TextView) viewFromOuter.findViewById(R.id.tv_end_text);
        tvEndDate = (TextView) viewFromOuter.findViewById(R.id.tv_end_date);
        tv00End = (TextView) viewFromOuter.findViewById(R.id.tv_00_end);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        context = this.getActivity();
        findViews(view);
        setUp(view);
        setUpLisenter();
    }

    private void setUp(View view) {
        //作为Dialog的生成器
        dialogFragmentController = new DialogFragmentCreater();//涉及到权限操作时，需要临时输入密码并验证
        dialogFragmentController.setDialogContext(getActivity(), getFragmentManager());
        //人民币单位的字符
        unitSpanString = SpannableStringUtil.getSpannableString(getActivity(), "¥", 30);//单位
        //用户的密码字符串
        outerPwdString = PreferenceUtil.load(context, PreferenceConstant.pwd, "");//提现等操作密码
        //用户的id
        outerUserId = PreferenceUtil.load(context, PreferenceConstant.userid, -1);

        //包装耗时操作
        progressDialogUtil = new ProgressDialogUtil(context);
        tvUsefulPauseFee.setText(unitSpanString);
        tvUsefulPauseFee.append("0");

        tvPausePrice.setText(unitSpanString);
        tvPausePrice.append("0");

        //停保规则
        layoutRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserActivity.startActivity(getActivity(), StringConstant.PauseRule, "停保规则");
            }
        });

        //时间控件  先显示为当前时间
        tvStartDate.setText(todayString_yyyy_m_d + "");
        tvEndDate.setText(todayString_yyyy_m_d + "");
        tvDateInterval.setText("共0天");
        //时间控件
        layoutDatePicker = (LinearLayout) view.findViewById(R.id.layout_date_picker);
        refreshCarSpinnerLayout(false);

        //车牌号Spinner
        mCarNumbersListAdapter = new SpinnerDropDownAdapter(getActivity(), mCarNumbersStringList);
        carNumberSpinner.setAdapter(mCarNumbersListAdapter);

        //限行停保的 Spinner
        mWeekNumbersStringList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.weekArray)));
        mWeekNumbersListAdapter = new SpinnerDropDownAdapter(getActivity(), mWeekNumbersStringList);
        weekNumberSpinner.setAdapter(mWeekNumbersListAdapter);

        //默认先设置为false,并且其父Layout设置为不可点击
        weekSwitchTabView.setChecked(false);
        refreshLimitPauseUI(false);

        // 滑动按钮-选择预约停保的时间
        dateSwitchTabView.setChecked(false);//默认是关闭
    }

    public boolean createAnimation() {
        return super.createAnimation();
    }

    private void setUpLisenter() {

        //titleBar 的点击事件
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
            }

            @Override
            public void onRightButtonClick(View v) {
                new ShareUtilsTest(getActivity(),"http://img0.imgtn.bdimg.com/it/u=4096430706,2666285308&fm=21&gp=0.jpg");
            }
        });

        //预约停保  时间控件 点击事件
        layoutDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过userid来判断用户是否登录，-1就是未登录状态
                if (outerUserId < 0) {
                    loginDialog();
                } else {
                    long orderStartDateFromServer = 0, orderEndDateFromServer = 0;
                    if (outerPauseBean != null) {
                        if (outerPauseBean.getStartdate() != null && outerPauseBean.getEnddate() != null) {
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
                    loginDialog();
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
                                ((TextView) tv_title).setText("提示");
                            }
                            if (tv_content instanceof TextView) {
                                ((TextView) tv_content).setText("您需要登录操作才能操作哦！\n是否现在就去登录？");
                            }

                        }
                    });
                    dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
                } else {
                    //弹出输入密码对话框，输入密码正确才能提现
                    dialogFragmentController.setOnPasswordDialogClickListener(new DialogFragmentCreater.OnPasswordDialogClickListener() {
                        @Override
                        public void getPassword(String psw) {
                            progressDialogUtil.show("正在验证密码...");
                            String phoneStr = PreferenceUtil.load(context, PreferenceConstant.phone, "");
                            UserRetrofitUtil.verifyPwd(context, phoneStr, psw, new NetCallback<NetWorkResultBean<String>>(context) {
                                @Override
                                public void onFailure(RetrofitError error, String message) {
                                    progressDialogUtil.hide();
                                    if (!TextUtils.isEmpty(message)) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
        layoutCarNumberSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carNumberSpinner.performClick();
            }
        });

        myCarSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //如果用户点击切换了车牌号，则刷新主界面
                String carNumberStr = mCarNumbersStringList.get(position);
                for (PauseData bean : mPauseDataList) {
                    if (carNumberStr.equals(bean.getLicenseplate())) {
                        //刷新车牌号为选中的字符串的停保记录
                        outerPauseBean = bean;
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
                    return;
                }
                outerWeekthPosition = position;
                //如果是真实用户的操作，才会进入下面的逻辑
                //保存限行停保的信息
                if (isWeekSwitchButtonSelected) {
                    //如果 选择周的滑动按钮是打开的，表示可以选择周，此时，选择的周会提交网络
                    if (outerUserId > 0) {
                        saveLimitPauseInfo(IntConstant.cancelPauseType_LimitPause, outerUserId);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isWeekSpinnerControlledByCode = false;
            }
        };


        //车牌号码Spinner 的Item选中事件
        carNumberSpinner.setOnItemSelectedListener(myCarSpinnerItemSelectedListener);
        //周Spinner 的下拉Item 选中事件
        weekNumberSpinner.setOnItemSelectedListener(myWeekSpinnerItemSelectedListener);


        //限行停保 - 周Spinner 的父Layout 的点击事件，并触发Spinner 的下拉事件
        layoutWeekNumberSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekNumberSpinner.performClick();
            }
        });

        //限行停保-周Switch 滑动监听器
        weekSwitchTabView.setOnCheckedChangeListener(new MySwitchOnCheckedChangeListener(weekSwitchTabView));
        //预约停保 Switch 滑动监听器
        dateSwitchTabView.setOnCheckedChangeListener(new MySwitchOnCheckedChangeListener(dateSwitchTabView));
        //更新预约停保的UI
        refreshReservePauseUI(false);
        //先画初始化的动画
        createTracksWithNoValue();
        //检查如果登录就刷新UI
        checkIsLoginAndRefreshUI();

        layoutHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orderid = -1;
                if (outerPauseBean != null) {
                    orderid = outerPauseBean.getOrderid();
                }
                Intent intent = new Intent(context, InComeDetailActivity.class);
                intent.putExtra(ExtraConstants.EXTRA_orderid, orderid);
                context.startActivity(intent);

            }
        });
        realNumber = 4567.00f;
        maxNumber = 4567.00f;
    }

    private void loginDialog() {
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
                    ((TextView) tv_title).setText("提示");
                }
                if (tv_content instanceof TextView) {
                    ((TextView) tv_content).setText("您需要登录操作才能操作哦！\n是否现在就去登录？");
                }

            }
        });
        dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
    }

    /**
     * @param isChecked true 就是打开，false 就是关闭滑动开关
     */
    private void checkIsLoginOrHasPassword(final boolean isChecked, final SwitchButton switchView) {
        //通过userid来判断用户是否登录，-1就是未登录状态,弹出登录对话框
        if (outerUserId < 0) {
            loginDialog();
        }
        //如果用户已经登录
        else if (TextUtils.isEmpty(outerPwdString)) {
            //但是没有设置过密码
            setPassword();
        } else {
            dialogFragmentController.setOnPasswordDialogClickListener(new DialogFragmentCreater.OnPasswordDialogClickListener() {
                @Override
                public void getPassword(String psw) {
                    progressDialogUtil.show("正在验证密码...");

                    String phoneStr = PreferenceUtil.load(context, PreferenceConstant.phone, "");

                    UserRetrofitUtil.verifyPwd(context, phoneStr, psw, new NetCallback<NetWorkResultBean<String>>(context) {
                        @Override
                        public void onFailure(RetrofitError error, String message) {
                            progressDialogUtil.hide();
                            if (!TextUtils.isEmpty(message)) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                                    tvXxtb.setAlpha(isChecked ? 1 : 0.5f);
                                    layoutWeekNumberSpinner.setAlpha(isChecked ? 1 : 0.5f);
                                    layoutWeekNumberSpinner.setClickable(isChecked);
                                    layoutWeekNumberSpinner.setEnabled(isChecked);
                                    if (isChecked) {//如果是打开操作，就保存信息
                                        saveLimitPauseInfo(IntConstant.cancelPauseType_LimitPause, outerUserId);
                                    } else {//如果是关闭操作，就取消上一次保存的信息
                                        cancelPause(outerUserId, IntConstant.cancelPauseType_LimitPause);
                                    }
                                } else {
                                    tvYytb.setAlpha(isChecked ? 1 : 0.5f);
                                    layoutDatePicker.setAlpha(isChecked ? 1 : 0.5f);
                                    layoutDatePicker.setClickable(isChecked);
                                    layoutDatePicker.setEnabled(isChecked);
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

    private void setPassword() {
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
                    ((TextView) tv_title).setText("提示");
                }
                if (tv_content instanceof TextView) {
                    ((TextView) tv_content).setText("您还没有设置过密码\n是否现在就去设置？");
                }
            }
        });
        dialogFragmentController.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);
    }

    private void cancelPause(int userid, int type) {
        if (outerPauseBean != null) {
            switch (type) {
                case IntConstant.cancelPauseType_LimitPause:
                    if (outerPauseBean.getLimitPaused()) {
                        UserRetrofitUtil.cancelPause(context, userid, outerPauseBean.getOrderid(), type, new NetCallback<NetWorkResultBean<String>>(context) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {
                                if (!TextUtils.isEmpty(message)) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                setSwitchChecked(false, weekSwitchTabView);
                            }
                        });
                    }
                    break;
                case IntConstant.cancelPauseType_ReservePause:
                    if (outerPauseBean.getReservePaused()) {
                        UserRetrofitUtil.cancelPause(context, userid, outerPauseBean.getOrderid(), type, new NetCallback<NetWorkResultBean<String>>(context) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {
                                if (!TextUtils.isEmpty(message)) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                setSwitchChecked(false, dateSwitchTabView);
                            }
                        });
                    }
                    break;
            }
        }
    }

    public void setSwitchChecked(boolean isChecked, SwitchButton switchView) {
        isSwitchTouchedOrTriggeredByCode = true;
        switchView.setChecked(isChecked);
        isSwitchTouchedOrTriggeredByCode = false;
    }

    /**
     * 保存预约停保
     */
    private void saveReservePauseInfo(int type, final int userid) {
        if (outerPauseBean != null) {
            if (TextUtils.isEmpty(timeStringForPostToServer)) {
                return;
            }
            if (outerPauseBean.getReservePaused()) {
                UserRetrofitUtil.cancelPause(context, userid, outerPauseBean.getOrderid(), type, new NetCallback<NetWorkResultBean<String>>(context) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        if (!TextUtils.isEmpty(message)) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                    public void onFailure(RetrofitError error, String message) {
                        if (!TextUtils.isEmpty(message)) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                        outerPauseBean.setLimitPaused(false);
                        if (weekNumberSpinner != null) {
                            outerWeekthPosition = weekNumberSpinner.getSelectedItemPosition();
                            UserRetrofitUtil.saveLimitPauseInfo(context, userid, outerPauseBean.getOrderid(), outerPauseBean.getPausePrice(), outerWeekthPosition + 1, new NetCallback<NetWorkResultBean<String>>(context) {
                                @Override
                                public void onFailure(RetrofitError error, String message) {
                                    if (!TextUtils.isEmpty(message)) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                if (weekNumberSpinner != null) {
                    outerWeekthPosition = weekNumberSpinner.getSelectedItemPosition();
                    UserRetrofitUtil.saveLimitPauseInfo(context, userid, outerPauseBean.getOrderid(), outerPauseBean.getPausePrice(), outerWeekthPosition + 1, new NetCallback<NetWorkResultBean<String>>(context) {
                        @Override
                        public void onFailure(RetrofitError error, String message) {
                            if (!TextUtils.isEmpty(message)) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    /**
     * 退出登录时刷新ui
     */
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
            if (tvUsefulPauseFee != null) {

                tvUsefulPauseFee.setText(unitSpanString);
                tvUsefulPauseFee.append("0");//¥
            }

            //没有登录时的每日停保费用
            if (tvPausePrice != null) {
                tvPausePrice.setText(unitSpanString);
                tvPausePrice.append("0");//¥
            }

            //没有登录时的累计节省保费
            if (tvFollowDecoView != null) {
                tvFollowDecoView.setText(unitSpanString);
                tvFollowDecoView.append("0");//¥
            }

            //没有登录时的预约停保开始时间
            if (tvStartDate != null) {
                tvStartDate.setText(todayString_yyyy_m_d);

            }
            //没有登录时的预约停保结束时间
            if (tvEndDate != null) {
                tvEndDate.setText(todayString_yyyy_m_d);
            }
            //没有登录时的预约停保时间间隔
            if (tvDateInterval != null) {
                tvDateInterval.setText("共0天");
            }
        }
    }

    private void checkIsLoginAndRefreshUI() {
        context = ShutInsureFragment.this.getActivity();
        outerPwdString = PreferenceUtil.load(context, PreferenceConstant.pwd, "");//提现等操作密码
        //用户的id
        outerUserId = PreferenceUtil.load(context, PreferenceConstant.userid, -1);
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
        if (layoutCarNumberSpinner != null) {
            //车牌号码
            layoutCarNumberSpinner.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
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

        float inset = getDimension(6);
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
        addFitListener(series1Item, tvFollowDecoView);
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
                        view.setText(unitSpanString);
                        view.append(String.format(mProgress, (1.0f - (currentPosition / seriesItem.getMaxValue())) * 1000f).replace('.', ','));
                    } else {
                        view.setText(unitSpanString);
                        view.append(String.format(mProgress, currentPosition).replace('.', ','));
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
                //保存预约停保的信息
                startTimeStr = event.getStart_time();
                endTimeStr = event.getEnd_time();
                timeIntvalStr = event.getInterval_time();
                timeStringForPostToServer = event.getTimeStringFroServer();
                refreshReservePauseInsurance();
                Log.e("Retrofit", "123");
                if (isDatePickerSwitchButtonSelected) {
                    Log.e("Retrofit", "456");
                    //如果滑动按钮的状态是打开的，才会请求网络
                    saveReservePauseInfo(IntConstant.cancelPauseType_ReservePause, outerUserId);
                }
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

    /**
     * 预约停保的ui 刷新
     */
    public void refreshReservePauseInsurance() {
        tvStartDate.setText(startTimeStr);
        tvEndDate.setText(endTimeStr);
        tvDateInterval.setText(timeIntvalStr);
        //若果没有值，就不显示
        if (TextUtils.isEmpty(startTimeStr)) {
            tvStartDate.setText(todayString_yyyy_m_d + "");
            tvEndDate.setText(todayString_yyyy_m_d + "");
            tvDateInterval.setText("共0天");
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
            public void onFailure(RetrofitError error, String message) {
                if (!TextUtils.isEmpty(message)) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
                mPullRefreshLinearLayout.onRefreshComplete();
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
                mPullRefreshLinearLayout.onRefreshComplete();

            }
        });
    }

    /**
     * 刷新首页的停保对象
     *
     * @param bean
     */
    public void refreshData(PauseData bean) {
        if (bean != null && bean.getReservedays() != null) {
            String reservedays = bean.getReservedays();
            if (reservedays.contains(",")) {
                String dateArray[] = reservedays.split(",");
                startTimeStr = todayString_yyyy_m_d;//使用今天的日期重新给其赋值
                if (dateArray.length > 0) {
                    startTimeStr = dateArray[0];
                    if (dateArray.length > 1) {
                        endTimeStr = dateArray[dateArray.length - 1];
                        try {
                            int e_s = (int) ((dateFormat_yyyy_mm_dd.parse(endTimeStr).getTime() - dateFormat_yyyy_mm_dd.parse(startTimeStr).getTime()) / IntConstant.milliSecondInADay);
                            timeIntvalStr = "共" + e_s + "天";
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        timeStringForPostToServer = reservedays;
                    }
                }
            } else {
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

                int selectedPosition = bean.getLimitday() - 1;
                if (selectedPosition < 1) {
                    weekNumberSpinner.setSelection(0);
                    isWeekSpinnerControlledByCode = false;
                } else {//如果只有一个，调用setSelection方法时，不会进入itemSelectedListener方法中
                    weekNumberSpinner.setSelection(selectedPosition);
                }
            } else {
                isWeekSpinnerControlledByCode = true;
                weekNumberSpinner.setSelection(0);
                isWeekSpinnerControlledByCode = false;
            }
            if (bean.getPausePrice() != null) {
                tvPausePrice.setText(unitSpanString.toString() + bean.getPausePrice() + "");
            }
            if (bean.getUsefulPauseFee() != null) {
                tvUsefulPauseFee.setText(unitSpanString.toString() + bean.getUsefulPauseFee() + "");
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
            if (bean.getTotalPauseFee() != null) { //开始动画
                realNumber = bean.getTotalPauseFee();
                maxNumber = (float) DiditUtil.getMaxInteger(realNumber);
                createAnimation();
            }
            //更新车牌号的layout
            if (mCarNumbersStringList != null && mCarNumbersStringList.size() > 0) {
                refreshCarSpinnerLayout(true);
            }
        }
    }


    //新需求：去掉颜色和透明度的变化效果

    /**
     * @param isLimitedPause 为true说明之前限行停保过；false代表没有，则ui不可点击，有半透明效果
     */
    private void refreshLimitPauseUI(boolean isLimitedPause) {
//        tvXxtb.setAlpha(isLimitedPause ? 1 : 0.5f);
//        layoutWeekNumberSpinner.setAlpha(isLimitedPause ? 1 : 0.5f);
//        layoutWeekNumberSpinner.setClickable(isLimitedPause ? true : false);
//        layoutWeekNumberSpinner.setEnabled(isLimitedPause ? true : false);
    }

    /**
     * @param isReservePause 为true说明之前预约停保过；false代表没有，则ui不可点击，有半透明效果
     */
    private void refreshReservePauseUI(boolean isReservePause) {
//        tvYytb.setAlpha(isReservePause ? 1 : 0.5f);
//        layoutDatePicker.setAlpha(isReservePause ? 1 : 0.5f);
//        layoutDatePicker.setClickable(isReservePause ? true : false);
//        layoutDatePicker.setEnabled(isReservePause ? true : false);
    }

    /**
     * 选择 预约和限行停保的Switch
     */
    private class MySwitchOnCheckedChangeListener implements Switch.OnCheckedChangeListener {
        SwitchButton aSwitch;

        public MySwitchOnCheckedChangeListener(SwitchButton aSwitch) {
            this.aSwitch = aSwitch;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            //获取滑动按钮的状态
            if (aSwitch == weekSwitchTabView) {
                isWeekSwitchButtonSelected = isChecked;
            } else if (aSwitch == dateSwitchTabView) {
                isDatePickerSwitchButtonSelected = isChecked;
            }
            //如果是代码调用了setCheck()就直接跳出，不进行下面的逻辑
            if (isSwitchTouchedOrTriggeredByCode) {
                return;
            }
            //先反向设置一下Switch
            setSwitchChecked(!isChecked, aSwitch);
            //检查操作的合法性：登录与否，是否设置过密码
            checkIsLoginOrHasPassword(isChecked, aSwitch);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() == null) {
            return;
        }
        mInitialized = true;
        final View replay = getView().findViewById(R.id.imageReplay);
        final View swipe = getView().findViewById(R.id.imageSwipe);
        if (replay != null) {
            replay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animation = AnimationUtils.loadAnimation(getActivity(),
                            R.anim.rotate_hide);

                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            swipe.setVisibility(View.INVISIBLE);
                            replay.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            setDemoFinished(false);
                            createAnimation();
                            replay.setEnabled(true);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    replay.startAnimation(animation);
                }
            });
        }
        createAnimation();
    }

}
