package com.jiandanbaoxian.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;

/**
 * Created by JammyQtheLab on 2015/12/14.
 */
public class ModifyEmployeeNumberActivity extends BaseActivity {
    public static final int REFRESH = 1000;

    private TitleBar titleBar;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_number);
        findViews();
        setUpViews();
        setUpListeners();
        new FreshWordsThread().start();
    }

    private void setUpListeners() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }

            @Override
            public void onRightButtonClick(View v) {
                Intent intent = getIntent();
                String newPhoneStr = editText.getText().toString();
                intent.putExtra("MODIFY_EMPLOYEE_NUMBER", newPhoneStr);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    private void setUpViews() {
        titleBar.initTitleBarInfo("修改员工工号", R.drawable.arrow_left, -1, "", "完成");

    }

    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        editText = (EditText) findViewById(R.id.et_phone_number);
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
                    UIUtils.showSoftInput(ModifyEmployeeNumberActivity.this, editText);
                    editText.requestFocus();
                    break;
                }

            }
        }

    }

    @Override
    public void finish() {
        //隐藏键盘
        if (editText != null) {
            UIUtils.setHideSoftInput(this, editText);
        }
        super.finish();
    }


}
