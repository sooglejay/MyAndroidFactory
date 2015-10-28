package com.jsb.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.NetWorkResultBean;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.ProgressDialogUtil;
import com.jsb.widget.TitleBar;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 买保险-加班狗-确认订单
 */
public class OrderCofirmJiaBanDogInsureActivity extends BaseActivity {
    private boolean tvPayFlag = false;
    private TitleBar titleBar;
    private TextView tv_pay;
    private EditText et_insure_user_name;
    private EditText et_company_address;
    private EditText et_home_address;

    private ProgressDialogUtil progressDialogUtil;


    private  Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm_jiaban_dog_insure);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                OrderCofirmJiaBanDogInsureActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("确认订单", R.drawable.arrow_left, -1, "", "");


        context = this;
        progressDialogUtil = new ProgressDialogUtil(this);

        et_insure_user_name = (EditText) findViewById(R.id.et_insure_user_name);
        et_company_address = (EditText) findViewById(R.id.et_company_address);
        et_home_address = (EditText) findViewById(R.id.et_home_address);


        et_insure_user_name.addTextChangedListener(textWatcher);
        et_company_address.addTextChangedListener(textWatcher);
        et_home_address.addTextChangedListener(textWatcher);


        tv_pay = (TextView) findViewById(R.id.tv_pay);
        tv_pay.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tv_pay.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvPayFlag) {
                    //如果输入框都有输入

                    progressDialogUtil.show("正在保存订单...");
                    UserRetrofitUtil.saveOvertimeInsuranceOrder(context,
                            PreferenceUtil.load(context, PreferenceConstant.phone, ""),
                            1,
                            et_company_address.getText().toString(),
                            et_home_address.getText().toString(),
                            new NetCallback<NetWorkResultBean<String>>(context) {
                                @Override
                                public void onFailure(RetrofitError error) {
                                    progressDialogUtil.hide();
                                    Toast.makeText(context,"无法连接网络！请检查网络设置",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
                                    progressDialogUtil.hide();
                                    startActivity(new Intent(context, PayJiaBanDogInsureActivity.class));
                                    OrderCofirmJiaBanDogInsureActivity.this.finish();
                                }
                            });

                  } else {
                    //如果输入框没有输入 信息
                    Toast.makeText(context, "请先完善以上信息！", Toast.LENGTH_SHORT).show();
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
            if (TextUtils.isEmpty(et_insure_user_name.getText())||TextUtils.isEmpty(et_company_address.getText())||TextUtils.isEmpty(et_home_address.getText())) {
                tvPayFlag = false;
                tv_pay.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                tv_pay.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                tvPayFlag = true;
                tv_pay.setBackgroundResource(R.drawable.btn_select_base);
                tv_pay.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
