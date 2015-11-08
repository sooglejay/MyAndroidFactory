package com.jsb.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.CommData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.ProgressDialogUtil;
import com.jsb.widget.TitleBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private TextView tv_sign_date;
    private TextView tv_enquiry;
    private TextView tv_sign_pick_date;
    private TextView tv_jqx_pick_date;
    private TextView tv_commercial_pick_date;

    private int userid = -1;
    private SimpleDateFormat dateFormat_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");//日期格式化


    private Long commercialTimeLong = 0L;
    private Long jqxTimeLong = 0L;
    private Long signTimeLong = 0L;
    private int flag_commercial_selected = 0;//用户是否选择 商业险的时间
    private int flag_jqx_selected = 0;//用户是否选择交强险时间


    private ProgressDialogUtil progressDialogUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insure_car);
        progressDialogUtil = new ProgressDialogUtil(this);
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
                    tv_enquiry.setEnabled(false);
                    tv_enquiry.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));

                    progressDialogUtil.show("正在生成订单...");
                    userid = PreferenceUtil.load(InsureCarActivity.this, PreferenceConstant.userid, -1);
                    UserRetrofitUtil.saveVehicleInfo(InsureCarActivity.this,
                            userid,
                            et_car_number.getText().toString(),
                            et_car_fadongji_number.getText().toString(),
                            et_car_jia_number.getText().toString(),
                            0,
                            signTimeLong,
                            et_user_name.getText().toString(),
                            commercialTimeLong,
                            jqxTimeLong, new NetCallback<NetWorkResultBean<CommData>>(InsureCarActivity.this) {
                                @Override
                                public void onFailure(RetrofitError error,String message) {
                                  if(!TextUtils.isEmpty(message)) {
                                      Toast.makeText(InsureCarActivity.this, message, Toast.LENGTH_SHORT).show();
                                  }else{
                                      Toast.makeText(InsureCarActivity.this, "生成订单失败，请检查输入的内容是否合法！", Toast.LENGTH_SHORT).show();
                                  }
                                    progressDialogUtil.hide();
                                    tv_enquiry.setEnabled(true);
                                    tv_enquiry.setTextColor(getResources().getColor(R.color.white_color));

                                }

                                @Override
                                public void success(NetWorkResultBean<CommData> commDataNetWorkResultBean, Response response) {
                                    progressDialogUtil.hide();
                                    InsureCarActivity.this.finish();
                                }
                            }


                    );
                }
            }
        });

        tv_sign_pick_date = (TextView) findViewById(R.id.tv_sign_pick_date);
        tv_sign_pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(InsureCarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_sign_pick_date.setText(year + "-" + (++monthOfYear) + "-" + dayOfMonth);

                        String mm = "" + monthOfYear;
                        String dd = "" + dayOfMonth;
                        if (monthOfYear < 10 && mm.length() == 1) {
                            mm = "0" + mm;
                        }
                        if (dayOfMonth < 10 && dd.length() == 1) {
                            dd = "0" + dd;
                        }
                        try {
                            signTimeLong = dateFormat_yyyy_MM_dd.parse(year + "-" + mm + "-" + dd).getTime();
                        } catch (ParseException e) {
                            signTimeLong = 0L;
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
                Calendar calendar = Calendar.getInstance();
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
                            commercialTimeLong = 0L;
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


        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_car_number = (EditText) findViewById(R.id.et_car_number);
        et_car_jia_number = (EditText) findViewById(R.id.et_car_jia_number);
        et_car_fadongji_number = (EditText) findViewById(R.id.et_car_fadongji_number);
        tv_sign_date = (TextView) findViewById(R.id.tv_sign_date);


        et_user_name.addTextChangedListener(textWatcher);
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
