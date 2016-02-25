package com.jiandanbaoxian.ui.buyinsurance.car_insurance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.jiandanbaoxian.adapter.SpinnerDropDownAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.VehicleTypeInfo;
import com.jiandanbaoxian.widget.customswitch.SwitchButton;

import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.widget.TitleBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sooglejay on 16/2/24.
 */
public class CarInsurancePricePlanActivity extends BaseActivity {

    private List<String> mWeekNumbersStringList = new ArrayList<>();
    private SpinnerDropDownAdapter spinnerDropDownAdapter;

    private Activity activity;

    String userid = "";
    String engineNumber = "";
    String seatingcapacity = "";
    String newValue = "";
    String model_code = "";
    String ownerName = "";
    String commercestartdate = "";
    String compulsorystartdate = "";


    private String licenseplate = "鲁Y82599";
    private String frameNumber = "LSGJT62U87S009773";

    private String regiterationDateString = "";
    private String issueDateString = "";


    String provence = "四川省";
    String provnce_no = "370602";
    String city_no = "";
    String city_number = "";
    String country_no = "";
    String country_name = "";
    String county_no = "";
    String transfer = "1";
    String transferDate = "0";
    String idcardNum = "";
    String phone = "13678054216";
    String compulsoryAmt = "10000";
    String insuranceItems = "[{\"amt\":0,\"bullet_glass\":-1,\"c_ly15\":-1,\"franchise_flag\":-1,\"insrnc_cde\":\"030101\",\"insrnc_name\":\"\",\"number\":-1,\"premium\":0,\"remark\":\"-1\"},\n" +
            "{\"amt\":0,\"bullet_glass\":-1,\"c_ly15\":-1,\"franchise_flag\":-1,\"insrnc_cde\":\"030103\",\"insrnc_name\":\"\",\"number\":-1,\"premium\":0,\"remark\":\"-1\"}]\n" +
            "";
    String type = "0";

    private boolean flag_SDEWCabSeatInsurance = true;
    private boolean flag_SDEWMotorVehicleLossInsurance = true;
    private boolean flag_SDEWPassengerSeatInsurance = true;
    private boolean flag_SDEWRobInsurance = true;
    private boolean flag_SDEWThirdResponsibilityInsurance = true;
    private SimpleDateFormat dateFormat_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");//日期格式化


    public static void startActivity(Activity activity, String engineNumber,
                                     String model_code, String framenumber,
                                     String ownerName,
                                     String province_no, String province_name, String city_no, String city_name, String country_no, String country_name,
                                     String transfer, String transferDate, String registerationDateString, String issueDateString) {
        Intent intent = new Intent(activity, CarInsurancePricePlanActivity.class);
        intent.putExtra("engineNumber", engineNumber);
        intent.putExtra("frameNumber", framenumber);
        intent.putExtra("userName", ownerName);
        intent.putExtra("province_no", province_no);
        intent.putExtra("province_name", province_name);
        intent.putExtra("city_no", city_no);
        intent.putExtra("city_name", city_name);
        intent.putExtra("country_no", country_no);
        intent.putExtra("country_name", country_name);
        intent.putExtra("model_code", model_code);
        intent.putExtra("transfer", transfer);
        intent.putExtra("transferDate", transferDate);
        intent.putExtra("registerationDateString", registerationDateString);
        intent.putExtra("issueDateString", issueDateString);


        activity.startActivity(intent);
    }

    private TitleBar titleBar;
    private LinearLayout layoutCommercialDatePicker;
    private TextView tvCommercialStartDate;
    private LinearLayout layoutCompulsoryDatePicker;
    private TextView tvCompulsoryStartDate;
    private LinearLayout layoutMotorVehicleLossInsurance;
    private ImageView ivSDEWMotorVehicleLossInsurance;
    private TextView tvSDEWMotorVehicleLossInsurance;
    private SwitchButton switchTabViewMotorVehicleLossInsurance;
    private Spinner spinnerThirdResponsibility;
    private LinearLayout layoutThirdResponsibility;
    private ImageView ivSDEWThirdResponsibilityInsurance;
    private TextView tvSDEWThirdResponsibilityInsurance;
    private SwitchButton switchTabViewThirdResponsibility;
    private Spinner spinnerDriverChair;
    private LinearLayout layoutCabSeatInsurance;
    private ImageView ivSDEWCabSeatInsurance;
    private TextView tvSDEWCabSeatInsurance;
    private SwitchButton switchTabViewCabChair;
    private Spinner spinnerPassageChair;
    private LinearLayout layoutPassengerSeatInsurance;
    private ImageView ivSDEWPassengerSeatInsurance;
    private TextView tvSDEWPassengerSeatInsurance;
    private SwitchButton switchTabViewPassengeChair;
    private Spinner spinner;
    private LinearLayout layoutRobInsurance;
    private ImageView ivSDEWRobInsurance;
    private TextView tvSDEWRobInsurance;
    private SwitchButton switchTabViewRob;
    private TextView tvQuotaPrice;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-02-25 21::09 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        layoutCommercialDatePicker = (LinearLayout) findViewById(R.id.layout_commercial_date_picker);
        tvCommercialStartDate = (TextView) findViewById(R.id.tv_commercial_start_date);
        layoutCompulsoryDatePicker = (LinearLayout) findViewById(R.id.layout_compulsory_date_picker);
        tvCompulsoryStartDate = (TextView) findViewById(R.id.tv_compulsory_start_date);
        layoutMotorVehicleLossInsurance = (LinearLayout) findViewById(R.id.layout_motor_vehicle_loss_insurance);
        ivSDEWMotorVehicleLossInsurance = (ImageView) findViewById(R.id.iv_SDEW_motor_vehicle_loss_insurance);
        tvSDEWMotorVehicleLossInsurance = (TextView) findViewById(R.id.tv_SDEW_motor_vehicle_loss_insurance);
        switchTabViewMotorVehicleLossInsurance = (SwitchButton) findViewById(R.id.switch_tab_view_motor_vehicle_loss_insurance);
        spinnerThirdResponsibility = (Spinner) findViewById(R.id.spinner_third_responsibility);
        layoutThirdResponsibility = (LinearLayout) findViewById(R.id.layout_third_responsibility);
        ivSDEWThirdResponsibilityInsurance = (ImageView) findViewById(R.id.iv_SDEW_third_responsibility_insurance);
        tvSDEWThirdResponsibilityInsurance = (TextView) findViewById(R.id.tv_SDEW_third_responsibility_insurance);
        switchTabViewThirdResponsibility = (SwitchButton) findViewById(R.id.switch_tab_view_third_responsibility);
        spinnerDriverChair = (Spinner) findViewById(R.id.spinner_driver_chair);
        layoutCabSeatInsurance = (LinearLayout) findViewById(R.id.layout_cab_seat_insurance);
        ivSDEWCabSeatInsurance = (ImageView) findViewById(R.id.iv_SDEW_cab_seat_insurance);
        tvSDEWCabSeatInsurance = (TextView) findViewById(R.id.tv_SDEW_cab_seat_insurance);
        switchTabViewCabChair = (SwitchButton) findViewById(R.id.switch_tab_view_cab_chair);
        spinnerPassageChair = (Spinner) findViewById(R.id.spinner_passage_chair);
        layoutPassengerSeatInsurance = (LinearLayout) findViewById(R.id.layout_passenger_seat_insurance);
        ivSDEWPassengerSeatInsurance = (ImageView) findViewById(R.id.iv_SDEW_passenger_seat_insurance);
        tvSDEWPassengerSeatInsurance = (TextView) findViewById(R.id.tv_SDEW_passenger_seat_insurance);
        switchTabViewPassengeChair = (SwitchButton) findViewById(R.id.switch_tab_view_passenge_chair);
        spinner = (Spinner) findViewById(R.id.spinner_rob);
        layoutRobInsurance = (LinearLayout) findViewById(R.id.layout_rob_insurance);
        ivSDEWRobInsurance = (ImageView) findViewById(R.id.iv_SDEW_rob_insurance);
        tvSDEWRobInsurance = (TextView) findViewById(R.id.tv_SDEW_rob_insurance);
        switchTabViewRob = (SwitchButton) findViewById(R.id.switch_tab_view_rob);
        tvQuotaPrice = (TextView) findViewById(R.id.tv_quota_price);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insure_car_plan);
        activity = this;

        engineNumber = getIntent().getStringExtra("engineNumber");
        frameNumber = getIntent().getStringExtra("frameNumber");
        ownerName = getIntent().getStringExtra("userName");
        provnce_no = getIntent().getStringExtra("province_no");
        provence = getIntent().getStringExtra("province_name");
        city_no = getIntent().getStringExtra("city_no");
        city_no = getIntent().getStringExtra("city_name");
        country_no = getIntent().getStringExtra("country_no");
        country_name = getIntent().getStringExtra("country_name");
        model_code = getIntent().getStringExtra("model_code");
        transfer = getIntent().getStringExtra("transfer");
        transferDate = getIntent().getStringExtra("transferDate");
        regiterationDateString = getIntent().getStringExtra("regiterationDateString");
        issueDateString = getIntent().getStringExtra("issueDateString");


        findViews();
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                activity.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });


        layoutCommercialDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvCommercialStartDate.setText(year + "-" + (++monthOfYear) + "-" + dayOfMonth);
                        String mm = "" + monthOfYear;
                        String dd = "" + dayOfMonth;
                        if (monthOfYear < 10 && mm.length() == 1) {
                            mm = "0" + mm;
                        }
                        if (dayOfMonth < 10 && dd.length() == 1) {
                            dd = "0" + dd;
                        }
                        long commercialDateLong = 0;
                        try {
                            commercialDateLong = dateFormat_yyyy_MM_dd.parse(year + "-" + mm + "-" + dd).getTime();
                        } catch (ParseException e) {
                            commercialDateLong = 0L;
                            e.printStackTrace();
                            Toast.makeText(activity, "Error in convert time !", Toast.LENGTH_LONG).show();
                        } finally {
                            Toast.makeText(activity, commercialDateLong + "", Toast.LENGTH_LONG).show();
                            commercestartdate = commercialDateLong + "";
                        }

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        layoutCompulsoryDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvCompulsoryStartDate.setText(year + "-" + (++monthOfYear) + "-" + dayOfMonth);
                        String mm = "" + monthOfYear;
                        String dd = "" + dayOfMonth;
                        if (monthOfYear < 10 && mm.length() == 1) {
                            mm = "0" + mm;
                        }
                        if (dayOfMonth < 10 && dd.length() == 1) {
                            dd = "0" + dd;
                        }
                        long compulsoryDateLong = 0;
                        try {
                            compulsoryDateLong = dateFormat_yyyy_MM_dd.parse(year + "-" + mm + "-" + dd).getTime();
                        } catch (ParseException e) {
                            compulsoryDateLong = 0L;
                            e.printStackTrace();
                            Toast.makeText(activity, "Error in convert time !", Toast.LENGTH_LONG).show();
                        } finally {
                            Toast.makeText(activity, compulsoryDateLong + "", Toast.LENGTH_LONG).show();
                            compulsorystartdate = compulsoryDateLong + "";
                        }

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        tvQuotaPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(transfer)) {
                    transfer = "0";
                }
                if (TextUtils.isEmpty(transferDate)) {
                    transferDate = "0";
                }
                UserRetrofitUtil.quotaPrice(activity, userid, licenseplate, engineNumber, frameNumber, seatingcapacity, newValue,
                        model_code, regiterationDateString, ownerName, commercestartdate, compulsorystartdate, issueDateString, provence, provnce_no,
                        city_no, county_no, transfer, transferDate, idcardNum, phone, compulsoryAmt, insuranceItems, type,
                        new NetCallback<NetWorkResultBean<List<VehicleTypeInfo>>>(activity) {
                            @Override
                            public void onFailure(RetrofitError error, String message) {
                                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void success(NetWorkResultBean<List<VehicleTypeInfo>> listNetWorkResultBean, Response response) {
                                Toast.makeText(activity, listNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });


        layoutCabSeatInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_SDEWCabSeatInsurance) {
                    flag_SDEWCabSeatInsurance = false;
                    ivSDEWCabSeatInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWCabSeatInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                } else {
                    flag_SDEWCabSeatInsurance = true;
                    ivSDEWCabSeatInsurance.setImageResource(R.drawable.icon_choose_selected);
                    tvSDEWCabSeatInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                }
            }
        });

        layoutMotorVehicleLossInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_SDEWMotorVehicleLossInsurance) {
                    flag_SDEWMotorVehicleLossInsurance = false;
                    ivSDEWMotorVehicleLossInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWMotorVehicleLossInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                } else {
                    flag_SDEWMotorVehicleLossInsurance = true;
                    ivSDEWMotorVehicleLossInsurance.setImageResource(R.drawable.icon_choose_selected);
                    tvSDEWMotorVehicleLossInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                }
            }
        });

        layoutPassengerSeatInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_SDEWPassengerSeatInsurance) {
                    flag_SDEWPassengerSeatInsurance = false;
                    ivSDEWPassengerSeatInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWPassengerSeatInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                } else {
                    flag_SDEWPassengerSeatInsurance = true;
                    ivSDEWPassengerSeatInsurance.setImageResource(R.drawable.icon_choose_selected);
                    tvSDEWPassengerSeatInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                }
            }
        });


        layoutRobInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_SDEWRobInsurance) {
                    flag_SDEWRobInsurance = false;
                    ivSDEWRobInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWRobInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                } else {
                    flag_SDEWRobInsurance = true;
                    ivSDEWRobInsurance.setImageResource(R.drawable.icon_choose_selected);
                    tvSDEWRobInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                }
            }
        });


        layoutThirdResponsibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_SDEWThirdResponsibilityInsurance) {
                    flag_SDEWThirdResponsibilityInsurance = false;
                    ivSDEWThirdResponsibilityInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWThirdResponsibilityInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                } else {
                    flag_SDEWThirdResponsibilityInsurance = true;
                    ivSDEWThirdResponsibilityInsurance.setImageResource(R.drawable.icon_choose_selected);
                    tvSDEWThirdResponsibilityInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                }
            }
        });


        //switch button 开关
        switchTabViewMotorVehicleLossInsurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    ivSDEWMotorVehicleLossInsurance.setImageResource(R.drawable.icon_choose_selected);
                    tvSDEWMotorVehicleLossInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                }
                else {
                    ivSDEWMotorVehicleLossInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWMotorVehicleLossInsurance.setTextColor(Color.parseColor("#f0f0f0"));

                }

            }
        });
        switchTabViewThirdResponsibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    ivSDEWThirdResponsibilityInsurance.setImageResource(R.drawable.icon_choose_selected);
                    tvSDEWThirdResponsibilityInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                }
                else {
                    ivSDEWThirdResponsibilityInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWThirdResponsibilityInsurance.setTextColor(Color.parseColor("#f0f0f0"));

                }
            }
        });


        switchTabViewCabChair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    ivSDEWCabSeatInsurance.setImageResource(R.drawable.icon_choose_selected);
                    tvSDEWCabSeatInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                }
                else {
                    ivSDEWCabSeatInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWCabSeatInsurance.setTextColor(Color.parseColor("#f0f0f0"));

                }
            }
        });
        switchTabViewPassengeChair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    ivSDEWPassengerSeatInsurance.setImageResource(R.drawable.icon_choose_selected);
                    tvSDEWPassengerSeatInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                }
                else {
                    ivSDEWPassengerSeatInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWPassengerSeatInsurance.setTextColor(Color.parseColor("#f0f0f0"));

                }
            }
        });
        switchTabViewRob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    ivSDEWRobInsurance.setImageResource(R.drawable.icon_choose_selected);
                    tvSDEWRobInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                }
                else {
                    ivSDEWRobInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWRobInsurance.setTextColor(Color.parseColor("#f0f0f0"));

                }
            }
        });


    }

    private void setUp() {
        titleBar.initTitleBarInfo("投保方案", R.drawable.arrow_left, -1, "", "");


        //限行停保的 Spinner
        mWeekNumbersStringList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.insurancePrice)));
        spinnerDropDownAdapter = new SpinnerDropDownAdapter(activity, mWeekNumbersStringList);

        spinner.setAdapter(spinnerDropDownAdapter);
        spinnerDriverChair.setAdapter(spinnerDropDownAdapter);
        spinnerPassageChair.setAdapter(spinnerDropDownAdapter);
        spinnerThirdResponsibility.setAdapter(spinnerDropDownAdapter);


    }

}
