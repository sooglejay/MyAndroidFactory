package com.jsb.ui.stopinsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.CardListAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.FinancialAccount;
import com.jsb.model.NetWorkResultBean;
import com.jsb.ui.BaseActivity;
import com.jsb.ui.BrowserActivity;
import com.jsb.util.FilterUtil;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.ProgressDialogUtil;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 首页-取钱
 */
public class PullMoneyActivity extends BaseActivity {
    private int userid;
    private boolean isPullMoneyAllowed = false;
    private Activity activity;

    //提现相关
    private int type;
    private String amountStr;
    private String realname;
    private String withdrawlPwd;
    private String account;
    private String union;
    private int accountType = 0;


    private TitleBar titleBar;
    private ListView mListView;
    private CardListAdapter cardListAdapter;
    private List<FinancialAccount> mDatas = new ArrayList<>();


    //footerView
    private View footerView;
    private TextView tvAddNewCard;
    private EditText etInputNumber;
    private TextView tvConfirmPullMoney;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-12 18:45:51 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        footerView = View.inflate(this, R.layout.footer_pull_money_activity, null);
        tvAddNewCard = (TextView) footerView.findViewById(R.id.tv_add_new_card);
        tvConfirmPullMoney = (TextView) footerView.findViewById(R.id.tv_confirm_pull_money);
        etInputNumber = (EditText) footerView.findViewById(R.id.et_input_number);
        etInputNumber.setFilters(new InputFilter[]{new FilterUtil(5, 2)});

        tvConfirmPullMoney.setEnabled(false);
        tvConfirmPullMoney.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        mListView.addFooterView(footerView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_money);
        activity = this;
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        withdrawlPwd = PreferenceUtil.load(this, PreferenceConstant.pwd, "");//提现用，提现属于特殊操作需要输入密码
        setUp();
        setLisenter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWithdrawlAccount();//获取用户的银行卡信息
    }

    private void setUp() {
        mListView = (ListView) findViewById(R.id.card_listview);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("取钱", R.drawable.arrow_left, -1, "", "取钱说明");

        cardListAdapter = new CardListAdapter(this, mDatas);
        mListView.setAdapter(cardListAdapter);
        findViews();

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
        tvAddNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PullMoneyActivity.this, AddCardActivity.class));
            }
        });

        //确定提现
        tvConfirmPullMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPullMoneyAllowed) {
                    if (TextUtils.isEmpty(withdrawlPwd)) {
                        Toast.makeText(activity, "请重新登录！", Toast.LENGTH_SHORT).show();
                        activity.finish();
                    }

                    if (TextUtils.isEmpty(account)) {
                        Toast.makeText(activity, "收款帐号不能为空！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(union)) {
                        Toast.makeText(activity, "银行名字不能为空！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(activity);
                    progressDialogUtil.show("正在提现...");
                    UserRetrofitUtil.saveWithdrawlInfo(activity, userid, 1, amountStr, realname, withdrawlPwd, account, union, accountType, new NetCallback<NetWorkResultBean<String>>(activity) {
                        @Override
                        public void onFailure(RetrofitError error, String message) {
                            progressDialogUtil.hide();
                            if (!TextUtils.isEmpty(message)) {
                                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                            progressDialogUtil.hide();
                            Toast.makeText(activity, "提现成功！", Toast.LENGTH_SHORT).show();
                            PullMoneyDetailActivity.startActivity(activity, amountStr, union, account);
                            activity.finish();
                        }
                    });
                } else {
                    Toast.makeText(activity, "请输入提现金额！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        etInputNumber.addTextChangedListener(textWatcher);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == -1 || position > mDatas.size()) {
                    return;
                }
                FinancialAccount bean = mDatas.get(position);
                for (FinancialAccount financialAccount : mDatas) {
                    financialAccount.setSuperFlag(false);
                }
                bean.setSuperFlag(true);
                cardListAdapter.notifyDataSetChanged();

                account = bean.getAccount_num();//收款帐号
                realname = bean.getAccount_name();//收款人名字
                union = bean.getBank_name();//收款的方式；银行；银行名字
                accountType = 0;//银联0
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            amountStr = etInputNumber.getText().toString();
            if (TextUtils.isEmpty(etInputNumber.getText())) {
                tvConfirmPullMoney.setEnabled(false);
                isPullMoneyAllowed = false;
                tvConfirmPullMoney.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                tvConfirmPullMoney.setEnabled(true);
                isPullMoneyAllowed = true;
                tvConfirmPullMoney.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private void getWithdrawlAccount() {
        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(this);
        progressDialogUtil.show("正在获取账户信息...");
        UserRetrofitUtil.getWithdrawlAccount(this, userid, new NetCallback<NetWorkResultBean<List<FinancialAccount>>>(this) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                progressDialogUtil.hide();
                if (!TextUtils.isEmpty(message)) {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(NetWorkResultBean<List<FinancialAccount>> financialAccountNetWorkResultBean, Response response) {

                if (financialAccountNetWorkResultBean != null && financialAccountNetWorkResultBean.getData() != null) {
                    mDatas.clear();
                    mDatas.addAll(financialAccountNetWorkResultBean.getData());
                    cardListAdapter.notifyDataSetChanged();
                }
                progressDialogUtil.hide();

            }
        });
    }

}
