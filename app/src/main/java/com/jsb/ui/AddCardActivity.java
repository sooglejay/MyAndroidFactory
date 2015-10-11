package com.jsb.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.widget.TitleBar;

/**
 * 主页面-取钱-添加新卡
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
        btNextStep.setEnabled(false);
        btNextStep.setClickable(true);
        btNextStep.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        btNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btNextStep.isEnabled()) {
                    Toast.makeText(AddCardActivity.this, "123！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AddCardActivity.this,"456！",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(TextUtils.isEmpty(etUserCardNumber.getText())||TextUtils.isEmpty(etUserCardNumber.getText()))
            {
                btNextStep.setEnabled(false);
                btNextStep.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            }else {
                btNextStep.setEnabled(true);
                btNextStep.setTextColor(getResources().getColor(R.color.white_color));

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
