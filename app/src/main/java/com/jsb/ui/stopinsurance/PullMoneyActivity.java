package com.jsb.ui.stopinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.CardListAdapter;
import com.jsb.ui.BaseActivity;
import com.jsb.ui.BrowserActivity;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页-取钱
 */
public class PullMoneyActivity extends BaseActivity {

    private TitleBar titleBar;
    private ListView mListView;
    private CardListAdapter cardListAdapter;
    private List<String> mDatas = new ArrayList<>();
    private TextView tv_add_new_card;
    private TextView tv_confirm_pull_money;
    private EditText et_input_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_money);
        setUp();
        setLisenter();
    }

    private void setUp() {
        mListView = (ListView) findViewById(R.id.card_listview);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("取钱", R.drawable.arrow_left, -1, "", "取钱说明");

        mDatas.add("建设银行");
        mDatas.add("招商银行");

        cardListAdapter = new CardListAdapter(this, mDatas);
        mListView.setAdapter(cardListAdapter);

        tv_add_new_card = (TextView) findViewById(R.id.tv_add_new_card);
        tv_confirm_pull_money = (TextView) findViewById(R.id.tv_confirm_pull_money);
        tv_confirm_pull_money.setEnabled(false);
        tv_confirm_pull_money.setClickable(true);
        tv_confirm_pull_money.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));


        et_input_number = (EditText) findViewById(R.id.et_input_number);


    }

    private void setLisenter() {


        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                PullMoneyActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {
                BrowserActivity.startActivity(PullMoneyActivity.this, true);
            }
        });


        //添加新的银行卡
        tv_add_new_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(PullMoneyActivity.this, AddCardActivity.class));
            }
        });

        //确定提现
        tv_confirm_pull_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isEnabled()) {
                    startActivity(new Intent(PullMoneyActivity.this, AddCardActivity.class));
                }else {
                    Toast.makeText(PullMoneyActivity.this, "请输入提现金额！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        et_input_number.addTextChangedListener(textWatcher);
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(et_input_number.getText())) {
                tv_confirm_pull_money.setEnabled(false);
                tv_confirm_pull_money.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                tv_confirm_pull_money.setEnabled(true);
                tv_confirm_pull_money.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
