package com.jiandanbaoxian.ui.buyinsurance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.me.historyprice.MyHistoryPriceItemActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * 买保险-车险
 */
public class InsureCarActivity extends BaseActivity {

    private boolean tvEnquiryFlag = false;
    private TitleBar titleBar;
    private EditText et_user_name;
    private EditText et_car_number;
    private EditText et_car_jia_number;
    private EditText et_car_fadongji_number;

    private EditText etUserIdCardNumber;
    private EditText etStartInsureDate;
    private EditText et_car_cpcxdm_number;
    private TextView tv_sign_in_pick_date;
    private TextView tv_enquiry;
    private TextView tv_fazheng_date;
    private TextView tv_jqx_pick_date;
    private TextView tv_commercial_pick_date;

    private SimpleDateFormat dateFormat_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");//日期格式化


    private Long commercialTimeLong = 0L;//商业险的日期
    private Long jqxTimeLong = 0L;//交强险的日期
    private Long fazhengTimeLong = 0L;//发证日期
    private Long signInTimeLong = 0L;//登记日期


    private ProgressDialogUtil progressDialogUtil;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insure_car);
        progressDialogUtil = new ProgressDialogUtil(this, true);
        activity = this;
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                InsureCarActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });


        //点击去报价，
        tv_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvEnquiryFlag) {
                    Toast.makeText(InsureCarActivity.this, "请先完善以上信息！", Toast.LENGTH_SHORT).show();
                } else {

                    String carNumberString = et_car_number.getText().toString();
                    String carFaDongJiNumber = et_car_fadongji_number.getText().toString();
                    String carJiaNumber = et_car_jia_number.getText().toString();
                    String userNameString = et_user_name.getText().toString();

                    MyHistoryPriceItemActivity.startActivity(activity,
                            carNumberString,
                            carFaDongJiNumber,
                            carJiaNumber,
                            userNameString,
                            commercialTimeLong,
                            jqxTimeLong,
                            fazhengTimeLong,
                            signInTimeLong);
                }
            }
        });

        tv_fazheng_date = (TextView) findViewById(R.id.tv_fazheng_date);
        tv_fazheng_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(InsureCarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_fazheng_date.setText(year + "-" + (++monthOfYear) + "-" + dayOfMonth);

                        String mm = "" + monthOfYear;
                        String dd = "" + dayOfMonth;
                        if (monthOfYear < 10 && mm.length() == 1) {
                            mm = "0" + mm;
                        }
                        if (dayOfMonth < 10 && dd.length() == 1) {
                            dd = "0" + dd;
                        }
                        try {
                            fazhengTimeLong = dateFormat_yyyy_MM_dd.parse(year + "-" + mm + "-" + dd).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        tv_commercial_pick_date = (TextView) findViewById(R.id.tv_commercial_pick_date);
        tv_commercial_pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(InsureCarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_commercial_pick_date.setText(year + "-" + (++monthOfYear) + "-" + dayOfMonth);

                        String mm = "" + monthOfYear;
                        String dd = "" + dayOfMonth;
                        if (monthOfYear < 10 && mm.length() == 1) {
                            mm = "0" + mm;
                        }
                        if (dayOfMonth < 10 && dd.length() == 1) {
                            dd = "0" + dd;
                        }
                        try {
                            commercialTimeLong = dateFormat_yyyy_MM_dd.parse(year + "-" + mm + "-" + dd).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        tv_sign_in_pick_date = (TextView) findViewById(R.id.tv_sign_in_pick_date);
        tv_sign_in_pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(InsureCarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_sign_in_pick_date.setText(year + "-" + (++monthOfYear) + "-" + dayOfMonth);

                        String mm = "" + monthOfYear;
                        String dd = "" + dayOfMonth;
                        if (monthOfYear < 10 && mm.length() == 1) {
                            mm = "0" + mm;
                        }
                        if (dayOfMonth < 10 && dd.length() == 1) {
                            dd = "0" + dd;
                        }
                        try {
                            signInTimeLong = dateFormat_yyyy_MM_dd.parse(year + "-" + mm + "-" + dd).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tv_jqx_pick_date = (TextView) findViewById(R.id.tv_jqx_pick_date);
        tv_jqx_pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(InsureCarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_jqx_pick_date.setText(year + "-" + (++monthOfYear) + "-" + dayOfMonth);
                        String mm = "" + monthOfYear;
                        String dd = "" + dayOfMonth;
                        if (monthOfYear < 10 && mm.length() == 1) {
                            mm = "0" + mm;
                        }
                        if (dayOfMonth < 10 && dd.length() == 1) {
                            dd = "0" + dd;
                        }
                        try {
                            jqxTimeLong = dateFormat_yyyy_MM_dd.parse(year + "-" + mm + "-" + dd).getTime();
                        } catch (ParseException e) {
                            jqxTimeLong = 0L;
                            e.printStackTrace();
                        }

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setUp() {
        tv_enquiry = (TextView) findViewById(R.id.tv_enquiry);

        tv_enquiry.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tv_enquiry.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("车险", R.drawable.arrow_left, -1, "", "");


        et_car_cpcxdm_number = (EditText) findViewById(R.id.et_car_cpcxdm_number);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_car_number = (EditText) findViewById(R.id.et_car_number);
        et_car_jia_number = (EditText) findViewById(R.id.et_car_jia_number);
        et_car_fadongji_number = (EditText) findViewById(R.id.et_car_fadongji_number);

        et_user_name.addTextChangedListener(textWatcher);
        et_car_number.addTextChangedListener(textWatcher);
        et_car_jia_number.addTextChangedListener(textWatcher);
        et_car_fadongji_number.addTextChangedListener(textWatcher);

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(et_user_name.getText())) {
                tvEnquiryFlag = false;
                tv_enquiry.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                tv_enquiry.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                tvEnquiryFlag = true;
                tv_enquiry.setBackgroundResource(R.drawable.btn_select_base);
                tv_enquiry.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
