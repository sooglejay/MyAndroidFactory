package com.jsb.ui.stopinsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.ui.BaseActivity;
import com.jsb.widget.TitleBar;

/**
 * Created by JammyQtheLab on 2015/11/12.
 */
public class PullMoneyDetailActivity extends BaseActivity{

    private static final String ExtraKeyMoneyStr = "ExtraKeyMoneyStr";
    private static final String ExtraKeyBankName = "ExtraKeyBankName";
    private static final String ExtraKeyAccountNum = "ExtraKeyAccountNum";
    private Activity activity;
    private TitleBar titleBar;
    private TextView tvConfirm;
    private TextView tv_pull_money_amount;
    private TextView tv_bank_name;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-12 20:16:35 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar)findViewById( R.id.title_bar );
        tvConfirm = (TextView)findViewById( R.id.tv_confirm );
        tv_pull_money_amount = (TextView) findViewById(R.id.tv_pull_money_amount);
        tv_bank_name = (TextView) findViewById(R.id.tv_bank_name);
    }


    private String moneyStr;
    private String bankNameStr;
    private String accountNumStr;

    public static void startActivity(Activity activity, String money, String bankNameStr, String accountNumStr) {
        Intent intent = new Intent(activity, PullMoneyDetailActivity.class);
        intent.putExtra(ExtraKeyMoneyStr, money);
        intent.putExtra(ExtraKeyBankName, bankNameStr);
        intent.putExtra(ExtraKeyAccountNum, accountNumStr);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_money_detail);
        activity = this;
        moneyStr = getIntent().getExtras().getString(ExtraKeyMoneyStr, "0");
        bankNameStr = getIntent().getExtras().getString(ExtraKeyBankName, "0");
        accountNumStr = getIntent().getExtras().getString(ExtraKeyAccountNum, "0");
        findViews();
        setUpViews();
    }


    private void setUpViews() {

        titleBar.initTitleBarInfo("提现详情",R.drawable.arrow_left,-1,"","");
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                activity.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });


        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        tv_pull_money_amount.setText("提现金额:" + moneyStr);
        tv_bank_name.setText(bankNameStr + "  尾号为  " + accountNumStr);
    }
}
