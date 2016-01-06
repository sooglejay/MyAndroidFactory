package com.jiandanbaoxian.ui.stopinsurance;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.CardListAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.FinancialAccount;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.BrowserActivity;
import com.jiandanbaoxian.util.FilterUtil;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;
import com.jiandanbaoxian.widget.swipemenulistview.SwipeMenu;
import com.jiandanbaoxian.widget.swipemenulistview.SwipeMenuCreator;
import com.jiandanbaoxian.widget.swipemenulistview.SwipeMenuItem;
import com.jiandanbaoxian.widget.swipemenulistview.SwipeMenuListView;

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
    private SwipeMenuListView mListView;
    private CardListAdapter cardListAdapter;
    private List<FinancialAccount> mDatas = new ArrayList<>();


    //footerView
    private View headerView;
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
        headerView = View.inflate(this, R.layout.header_pull_money_activity, null);
        footerView = View.inflate(this, R.layout.footer_pull_money_activity, null);
        tvAddNewCard = (TextView) footerView.findViewById(R.id.tv_add_new_card);
        tvConfirmPullMoney = (TextView) footerView.findViewById(R.id.tv_confirm_pull_money);
        etInputNumber = (EditText) footerView.findViewById(R.id.et_input_number);
        etInputNumber.setFilters(new InputFilter[]{new FilterUtil(5, 2)});

        tvConfirmPullMoney.setEnabled(false);
        tvConfirmPullMoney.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        mListView.addHeaderView(headerView);
        mListView.addFooterView(footerView);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_money);
        activity = this;
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        withdrawlPwd = getIntent().getExtras().getString("password","");//提现用，提现属于特殊操作需要输入密码

        setUp();
        setLisenter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWithdrawlAccount();//获取用户的银行卡信息
    }

    private void setUp() {
        mListView = (SwipeMenuListView) findViewById(R.id.card_listview);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("取钱", R.drawable.arrow_left, -1, "", "取钱说明");

        cardListAdapter = new CardListAdapter(this, mDatas);
        initListViewMenu();
        mListView.setAdapter(cardListAdapter);
        findViews();

    }

    public void initListViewMenu() {
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(R.drawable.btn_select_dark_gray);
                // set item width
                openItem.setWidth((int) UIUtils.dp2px(activity, 90));
                // set item title
                openItem.setTitle("编辑");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);


                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(R.drawable.btn_select_red);
                // set item width
                deleteItem.setTitle("删除");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);

                deleteItem.setWidth((int) UIUtils.dp2px(activity, 90));
                // set a icon
//                deleteItem.setIcon(R.drawable.wallet_base_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //只有一个Item，所以其实，它是进入了 case 0这个语句了
                        //open
                        //open(item);
                        Toast.makeText(activity, "没有接口！", Toast.LENGTH_SHORT).show();

                        break;
                    case 1:
                        // delete
                        //	delete(deleteItem);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                        dialog.setTitle("小安提醒你").setIcon(android.R.drawable.ic_dialog_info).setMessage("你确定要删除这条银行卡信息么？").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(activity, "没有接口！", Toast.LENGTH_SHORT).show();

                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();//取消弹出框
                            }
                        }).create().show();
                        break;
                }
                return false;
            }
        });

        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
                //测试用
                // Toast.makeText(getApplicationContext(),position+":Start",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
                //测试用
                // Toast.makeText(getApplicationContext(),position+":End",Toast.LENGTH_LONG).show();
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                //测试用
                // Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_LONG).show();
                return true;
            }
        });

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
                            String message = stringNetWorkResultBean.getMessage().toString();
                            if (message.equals("余额不足！")) {
                                Toast.makeText(activity, "提现失败！余额不足！", Toast.LENGTH_SHORT).show();
                            } else if(message.equals("提现密码不正确！"))
                            {
                                Toast.makeText(activity, "提现失败！提现密码不正确！", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                PullMoneyDetailActivity.startActivity(activity, amountStr, union, account);
                                activity.finish();

                            }
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
