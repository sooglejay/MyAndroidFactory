package com.jiandanbaoxian.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.event.BusEvent;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.OvertimeData;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;

import javax.net.ssl.HttpsURLConnection;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    private TitleBar titleBar;
    public static final int REFRESH = 1000;

    private boolean loginTvFlag = false;
    private TextView tv_login;
    private TextView tv_obtain_verify_code;
    private EditText et_phone_number;
    private EditText et_verify_code;
    private CountDownTimer mCountTimer;


    private String phoneString = "";
    private String verifyCodeStringService = "";
    private String verifyCodeStringUser = "";


    private ProgressDialogUtil mProgressUtil;
    protected SharedPreferences sp;
    protected GestureDetector mGestureDetector;
    private Activity activity;

    public static void startLoginActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        setUp();
        setLisenter();


        sp = getSharedPreferences("config", MODE_PRIVATE);


        //1.创建一个手势识别器 new 对象，并给这个手势识别器设置监听器
        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            //当手指在屏幕上滑动的时候 调用的方法.
            @Override
            //e1代表的是手指刚开始滑动的事件，e2代表手指滑动完了的事件
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getRawX() - e2.getRawX() > 200) {
//                    UIUtils.showNext();//向右滑动，显示下一个界面
                    return true;
                }

                if (e2.getRawX() - e1.getRawX() > 200) {
                    UIUtils.showPre(activity, MainActivity.class);//向左滑动，显示上一个界面
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

    }

    private void setUp() {
        mProgressUtil = new ProgressDialogUtil(this, true);


        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("快捷登录", R.drawable.arrow_left, -1, "", "");


        tv_obtain_verify_code = (TextView) findViewById(R.id.tv_obtain_verify_code);
        tv_login = (TextView) findViewById(R.id.tv_login);
        loginTvFlag = false;
        tv_login.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tv_login.setClickable(true);
        tv_login.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));


        et_verify_code = (EditText) findViewById(R.id.et_verification_number);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);


        new FreshWordsThread().start();

        et_verify_code.addTextChangedListener(textWatcher);
        et_phone_number.addTextChangedListener(textWatcher);


        mCountTimer = new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                //让验证码输入框获取焦点
                et_verify_code.requestFocus();
                tv_obtain_verify_code.setText("" + millisUntilFinished / 1000 + "秒后重试");
                tv_obtain_verify_code.setEnabled(false);
                tv_obtain_verify_code.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            }

            public void onFinish() {
                tv_obtain_verify_code.setText("重新获取验证码");
                tv_obtain_verify_code.setTextColor(getResources().getColor(R.color.white_color));
                tv_obtain_verify_code.setEnabled(true);
            }

        };


    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                LoginActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(verifyCodeStringService)) {
                    Toast.makeText(LoginActivity.this, "请输入手机号获取验证码！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!verifyCodeStringService.equals(verifyCodeStringUser)) {
                    Toast.makeText(LoginActivity.this, "验证码不正确！请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressUtil.show("正在登录...");
                    UserRetrofitUtil.login(LoginActivity.this, phoneString, verifyCodeStringUser, new NetCallback<NetWorkResultBean<Object>>(activity) {
                        @Override
                        public void onFailure(RetrofitError error, String message) {
                            mProgressUtil.hide();
                            Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();

                            mCountTimer.onFinish();
                            mCountTimer.cancel();


                        }

                        @Override
                        public void success(NetWorkResultBean<Object> commDataNetWorkResultBean, Response response) {
                            mProgressUtil.hide();

                            if (commDataNetWorkResultBean != null) {
                                int status = commDataNetWorkResultBean.getStatus();
                                switch (status) {
                                    case HttpsURLConnection.HTTP_OK:
                                        CommData bean = null;

                                        Object obj = commDataNetWorkResultBean.getData();
                                        if (obj instanceof String) {
                                            Toast.makeText(activity, commDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        try {
                                            String json = com.jiandanbaoxian.util.JsonUtil.toJson(obj);
                                            bean = com.jiandanbaoxian.util.JsonUtil.fromJson(json, CommData.class);
                                        } catch (Exception e) {
                                            Log.e("qw", "出错啦！！！!");
                                        }
                                        if (bean == null) {
                                            Toast.makeText(activity, "系统异常！", Toast.LENGTH_LONG).show();
                                            return;
                                        }


                                        //保存用户信息
                                        PreferenceUtil.save(LoginActivity.this, PreferenceConstant.userid, bean.getUserid());
                                        PreferenceUtil.save(LoginActivity.this, PreferenceConstant.name, bean.getUserInfo().getName());
                                        PreferenceUtil.save(LoginActivity.this, PreferenceConstant.phone, bean.getUserInfo().getPhone());
                                        PreferenceUtil.save(LoginActivity.this, PreferenceConstant.pwd, bean.getUserInfo().getPwd());
                                        EventBus.getDefault().post(new BusEvent(BusEvent.MSG_Login_Success));
                                        LoginActivity.this.finish();
                                        break;
                                    default:
                                        Toast.makeText(activity, commDataNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }


                        }
                    });

                }
            }
        });

        tv_obtain_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkPhoneNumberValid(et_phone_number.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号码!", Toast.LENGTH_SHORT).show();
                } else {
                    mCountTimer.start();
                    phoneString = et_phone_number.getText().toString();
                    UserRetrofitUtil.obtainVerifyCode(LoginActivity.this, phoneString, 0, new NetCallback<NetWorkResultBean<Object>>(LoginActivity.this) {
                        @Override
                        public void onFailure(RetrofitError error, String message) {
                            Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();
                            mCountTimer.onFinish();
                            mCountTimer.cancel();
                        }

                        @Override
                        public void success(NetWorkResultBean<Object> submitPhoneNetWorkResultBean, Response response) {
                            if (submitPhoneNetWorkResultBean != null) {
                                int status = submitPhoneNetWorkResultBean.getStatus();
                                switch (status) {
                                    case HttpsURLConnection.HTTP_OK:
                                        CommData bean = null;
                                        Object obj = submitPhoneNetWorkResultBean.getData();
                                        if (obj instanceof String) {
                                            Toast.makeText(activity, submitPhoneNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        try {
                                            String json = com.jiandanbaoxian.util.JsonUtil.toJson(obj);
                                            bean = com.jiandanbaoxian.util.JsonUtil.fromJson(json, CommData.class);
                                        } catch (Exception e) {
                                            Log.e("qw", "出错啦！！！!");
                                        }
                                        if (bean == null) {
                                            Toast.makeText(activity, "系统异常！", Toast.LENGTH_LONG).show();
                                            return;
                                        }


                                        Toast.makeText(LoginActivity.this, "获取验证码成功！短信已经下发至您的手机上", Toast.LENGTH_LONG).show();
                                        verifyCodeStringService = bean.getVerifyCode();
                                        break;
                                    default:
                                        Toast.makeText(activity, submitPhoneNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        }
                    });
                }
            }
        });
    }


    /**
     * 简单判断手机号码合法性
     *
     * @param phoneNumber
     * @return
     */
    private boolean checkPhoneNumberValid(String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() == 11)
            return true;
        return false;
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            phoneString = et_phone_number.getText().toString();
            verifyCodeStringUser = et_verify_code.getText().toString();

            if (TextUtils.isEmpty(phoneString) || TextUtils.isEmpty(verifyCodeStringUser)) {
                loginTvFlag = false;
                tv_login.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                tv_login.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                loginTvFlag = true;
                tv_login.setBackgroundResource(R.drawable.btn_select_base);
                tv_login.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void finish() {
        //隐藏键盘
        if (et_phone_number != null) {
            UIUtils.setHideSoftInput(this, et_phone_number);
        }
        super.finish();
    }


    /**
     * 主线程更新ui
     */
    private class FreshWordsThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            refreshUiHandler.sendEmptyMessage(REFRESH);
        }
    }


    //主线程中的handler
    private RefreshUiHandler refreshUiHandler = new RefreshUiHandler();

    private class RefreshUiHandler extends Handler {
        /**
         * 接受子线程传递的消息机制
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case REFRESH: {
                    //手机号码输入框获取焦点
                    UIUtils.showSoftInput(LoginActivity.this, et_phone_number);
                    et_phone_number.requestFocus();
                    break;
                }

            }
        }

    }

    //2.让手势识别器生效，重写Activity的触摸事件，并且将Activity的触摸事件传入到手势识别器中
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
