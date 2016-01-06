package com.jiandanbaoxian.ui.stopinsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 主页面-取钱-添加新卡
 */
public class AddCardActivity extends BaseActivity {
    public static final int REFRESH = 1000;

    private TitleBar titleBar;
    private EditText etUserName;
    private EditText etUserCardNumber;
    private Button btNextStep;

    private Activity activity;

    private int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        activity = this;
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                AddCardActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("添加新卡", R.drawable.arrow_left, -1, "", "");
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etUserCardNumber = (EditText) findViewById(R.id.et_card_number);

        etUserName.addTextChangedListener(textWatcher);
        etUserCardNumber.addTextChangedListener(textWatcher);

        btNextStep = (Button) findViewById(R.id.btn_next_step);
        btNextStep.setEnabled(false);
        btNextStep.setClickable(true);
        btNextStep.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        btNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btNextStep.isEnabled()) {

                } else {

                    String name = etUserName.getText().toString();
                    String card = etUserCardNumber.getText().toString();

                    Intent intent = new Intent(activity, AddCardDetailActivity.class);
                    intent.putExtra("card", card);
                    intent.putExtra("name", name);
                    startActivity(intent);
                    finish();
                }
            }
        });

        new FreshWordsThread().start();


    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(etUserCardNumber.getText()) || TextUtils.isEmpty(etUserCardNumber.getText())) {
                btNextStep.setEnabled(false);
                btNextStep.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                btNextStep.setEnabled(true);
                btNextStep.setTextColor(getResources().getColor(R.color.white_color));

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    @Override
    public void finish() {
        //隐藏键盘
        if (etUserName != null) {
            UIUtils.setHideSoftInput(this, etUserName);
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
                    UIUtils.showSoftInput(AddCardActivity.this, etUserName);
                    etUserName.requestFocus();
                    break;
                }

            }
        }
    }
}
