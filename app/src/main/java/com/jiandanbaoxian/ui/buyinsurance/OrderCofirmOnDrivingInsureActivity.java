package com.jiandanbaoxian.ui.buyinsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.model.Overtimeordertable;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.util.TimeUtil;
import com.jiandanbaoxian.widget.TitleBar;

/**
 * 买保险-驾驶险-确认订单信息
 */
public class OrderCofirmOnDrivingInsureActivity extends BaseActivity {
    public final static String ExtrasKeyName = "ExtrasKeyName";

    private boolean tvPayFlag = false;
    private TitleBar titleBar;
    private TextView tv_pay;
    private TextView tv_start_date_to_end_date;
    private EditText et_insure_user_name;//用户姓名
    private EditText et_car_number;//车牌号码
    private EditText et_insure_user_id_number;////身份证号码

    private Activity activity;

    private Overtimeordertable overtimeordertable;

    public static void startActivity(Activity context, Overtimeordertable bean) {
        Intent intent = new Intent(context, OrderCofirmOnDrivingInsureActivity.class);
        intent.putExtra(ExtrasKeyName, bean);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm_on_driving_insure);
        activity = this;
        // 传递过来的model
        overtimeordertable = getIntent().getParcelableExtra(ExtrasKeyName);


        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                OrderCofirmOnDrivingInsureActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("确认订单", R.drawable.arrow_left, -1, "", "");

        et_insure_user_name = (EditText) findViewById(R.id.et_insure_user_name);
        et_insure_user_name.addTextChangedListener(textWatcher);
        tv_start_date_to_end_date = (TextView) findViewById(R.id.tv_start_date_to_end_date);
        if (overtimeordertable != null && overtimeordertable.getStartdate() != null)
            tv_start_date_to_end_date.setText(TimeUtil.getTimeString(overtimeordertable.getStartdate()) + "起，");
        if (overtimeordertable != null && overtimeordertable.getEnddate() != null) {
            tv_start_date_to_end_date.append(TimeUtil.getTimeString(overtimeordertable.getEnddate()) + "止");
        }
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        tv_pay.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tv_pay.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvPayFlag) {
                    PayJiaBanDogInsureActivity.startActivity(activity, overtimeordertable);
                } else {
                    Toast.makeText(OrderCofirmOnDrivingInsureActivity.this, "请先完善以上信息！", Toast.LENGTH_SHORT).show();
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
            if (TextUtils.isEmpty(et_insure_user_name.getText())) {
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
