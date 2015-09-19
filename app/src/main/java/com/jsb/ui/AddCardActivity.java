package com.jsb.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;

import com.jsb.R;
import com.jsb.adapter.CardListAdapter;
import com.jsb.widget.TitleBar;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 */
public class AddCardActivity extends BaseActivity {

    private TitleBar titleBar;
    private EditText etUserName;
    private EditText etUserCardNumber;
    private Button btNextStep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
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
        etUserName = (EditText)findViewById(R.id.et_user_name);
        etUserCardNumber = (EditText)findViewById(R.id.et_card_number);

        etUserName.addTextChangedListener(textWatcher);
        etUserCardNumber.addTextChangedListener(textWatcher);

        btNextStep = (Button)findViewById(R.id.btn_next_step);
    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!TextUtils.isEmpty(etUserCardNumber.getText().toString())&&!TextUtils.isEmpty(etUserCardNumber.getText().toString()))
            {
                btNextStep.setEnabled(true);
            }else {
                btNextStep.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
