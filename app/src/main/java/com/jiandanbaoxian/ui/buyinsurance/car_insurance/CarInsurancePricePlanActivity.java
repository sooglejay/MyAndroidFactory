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

import com.google.gson.Gson;
import com.jiandanbaoxian.adapter.SpinnerDropDownAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.InsuranceItemData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.VehicleTypeInfo;
import com.jiandanbaoxian.ui.LoginActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.widget.customswitch.SwitchButton;

import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.widget.TitleBar;

import net.sf.json.JSONArray;

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
    String seatingcapacity = "0";
    float newValue = 0;
    String model_code = "";
    String ownerName = "";
    String commercestartdate = "0";
    String compulsorystartdate = "0";


    private String licenseplate = "鲁Y82599";
    private String frameNumber = "LSGJT62U87S009773";

    private String registrationDateString = "";
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
    String insuranceItems =
            "[" +
                    "{\"amt\":0,\"bullet_glass\":-1,\"c_ly15\":-1,\"franchise_flag\":1,\"insrnc_cde\":\"030101\",\"insrnc_name\":\"hello\",\"number\":-1,\"premium\":0,\"remark\":\"-1\"},\n" +
                    "{\"amt\":306006009,\"bullet_glass\":-1,\"c_ly15\":-1,\"franchise_flag\":1,\"insrnc_cde\":\"030102\",\"insrnc_name\":\"hello\",\"number\":-1,\"premium\":0,\"remark\":\"-1\"},\n" +
                    "{\"amt\":10000,\"bullet_glass\":-1,\"c_ly15\":-1,\"franchise_flag\":1,\"insrnc_cde\":\"030104\",\"insrnc_name\":\"hello\",\"number\":-1,\"premium\":0,\"remark\":\"-1\"},\n" +
                    "{\"amt\":10000,\"bullet_glass\":-1,\"c_ly15\":-1,\"franchise_flag\":1,\"insrnc_cde\":\"030105\",\"insrnc_name\":\"hello\",\"number\":-1,\"premium\":0,\"remark\":\"-1\"},\n" +
                    "{\"amt\":0,\"bullet_glass\":-1,\"c_ly15\":-1,\"franchise_flag\":-1,\"insrnc_cde\":\"030107\",\"insrnc_name\":\"hello\",\"number\":-1,\"premium\":0,\"remark\":\"-1\"},\n" +
                    "{\"amt\":0,\"bullet_glass\":-1,\"c_ly15\":-1,\"franchise_flag\":-1,\"insrnc_cde\":\"030116\",\"insrnc_name\":\"hello\",\"number\":-1,\"premium\":0,\"remark\":\"-1\"}]\n" +
                    "";
    String type = "0";


    private List<InsuranceItemData> insuranceItemDatas = new ArrayList<>();

    //基本险
    InsuranceItemData motor_vehicle_loss_insurance = new InsuranceItemData();
    InsuranceItemData third_responsibility_insurance = new InsuranceItemData();
    InsuranceItemData cab_seat_insurance = new InsuranceItemData();
    InsuranceItemData passenger_seat_insurance = new InsuranceItemData();
    InsuranceItemData rob_insurance = new InsuranceItemData();


    InsuranceItemData shade_lining_insurance = new InsuranceItemData();
    InsuranceItemData risk_of_spontaneous_combustion_insurance = new InsuranceItemData();
    InsuranceItemData risk_of_new_equipment_insurance = new InsuranceItemData();
    InsuranceItemData risk_of_scratches_insurance = new InsuranceItemData();
    InsuranceItemData risk_of_engine_wading_insurance = new InsuranceItemData();


    //基本险
    private boolean flag_switch_SDEWCabSeatInsurance = true;
    private boolean flag_switch_SDEWMotorVehicleLossInsurance = true;
    private boolean flag_switch_SDEWPassengerSeatInsurance = true;
    private boolean flag_switch_SDEWRobInsurance = true;
    private boolean flag_switch_SDEWThirdResponsibilityInsurance = true;


    //基本险
    private boolean flag_layout_SDEWCabSeatInsurance = true;
    private boolean flag_layout_SDEWMotorVehicleLossInsurance = true;
    private boolean flag_layout_SDEWPassengerSeatInsurance = true;
    private boolean flag_layout_SDEWRobInsurance = true;
    private boolean flag_layout_SDEWThirdResponsibilityInsurance = true;


    //附加险
    private boolean flag_switch_SDEWShadeLiningInsurance = true;//玻璃险
    private boolean flag_switch_SDEWRiskOfSpontaneousCombustionInsurance = true;//自燃险
    private boolean flag_switch_SDEWRiskOfNewEquipmentInsurance = true;//新增设备险
    private boolean flag_switch_SDEWRiskOfScratchesInsurance = true;//刮痕险
    private boolean flag_switch_SDEWRiskOfEngineWadingInsurance = true;//发动机涉水险


    //附加险
    private boolean flag_layout_SDEWShadeLiningInsurance = true;
    private boolean flag_layout_SDEWRiskOfSpontaneousCombustionInsurance = true;
    private boolean flag_layout_SDEWRiskOfNewEquipmentInsurance = true;
    private boolean flag_layout_SDEWRiskOfScratchesInsurance = true;
    private boolean flag_layout_SDEWRiskOfEngineWadingInsurance = true;


    private SimpleDateFormat dateFormat_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");//日期格式化


    public static void startActivity(Activity activity, String engineNumber,
                                     String model_code,
                                     float newValue,
                                     String framenumber,
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
        intent.putExtra("newValue", newValue);
        intent.putExtra("transfer", transfer);
        intent.putExtra("transferDate", transferDate);
        intent.putExtra("registrationDateString", registerationDateString);
        intent.putExtra("issueDateString", issueDateString);


        activity.startActivity(intent);
    }

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
    private Spinner spinnerRob;
    private LinearLayout layoutRobInsurance;
    private ImageView ivSDEWRobInsurance;
    private TextView tvSDEWRobInsurance;
    private SwitchButton switchTabViewRob;
    private Spinner spinnerShadeLining;
    private LinearLayout layoutShadeLiningInsurance;
    private ImageView ivSDEWShadeLiningInsurance;
    private TextView tvSDEWShadeLiningInsurance;
    private SwitchButton switchTabViewShadeLining;
    private Spinner spinnerRiskOfSpontaneousCombustion;
    private LinearLayout layoutRiskOfSpontaneousCombustionInsurance;
    private ImageView ivSDEWRiskOfSpontaneousCombustionInsurance;
    private TextView tvSDEWRiskOfSpontaneousCombustionInsurance;
    private SwitchButton switchTabViewRiskOfSpontaneousCombustion;
    private Spinner spinnerNewEquipmentRisk;
    private LinearLayout layoutRiskOfNewEquipmentInsurance;
    private ImageView ivSDEWRiskOfNewEquipmentInsurance;
    private TextView tvSDEWRiskOfNewEquipmentInsurance;
    private SwitchButton switchTabViewRiskOfNewEquipment;
    private Spinner spinnerScratchesRisk;
    private LinearLayout layoutRiskOfScratchesInsurance;
    private ImageView ivSDEWRiskOfScratchesInsurance;
    private TextView tvSDEWRiskOfScratchesInsurance;
    private SwitchButton switchTabViewRiskOfScratches;
    private Spinner spinnerEngineWadingInsuranceRisk;
    private LinearLayout layoutRiskOfEngineWadingInsurance;
    private ImageView ivSDEWRiskOfEngineWadingInsurance;
    private TextView tvSDEWRiskOfEngineWadingInsurance;
    private SwitchButton switchTabViewRiskOfEngineWading;
    private TextView tvQuotaPrice;
    private TitleBar titleBar;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-02-27 19:26:39 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
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
        spinnerRob = (Spinner) findViewById(R.id.spinner_rob);
        layoutRobInsurance = (LinearLayout) findViewById(R.id.layout_rob_insurance);
        ivSDEWRobInsurance = (ImageView) findViewById(R.id.iv_SDEW_rob_insurance);
        tvSDEWRobInsurance = (TextView) findViewById(R.id.tv_SDEW_rob_insurance);
        switchTabViewRob = (SwitchButton) findViewById(R.id.switch_tab_view_rob);
        spinnerShadeLining = (Spinner) findViewById(R.id.spinner_shade_lining);
        layoutShadeLiningInsurance = (LinearLayout) findViewById(R.id.layout_shade_lining_insurance);
        ivSDEWShadeLiningInsurance = (ImageView) findViewById(R.id.iv_SDEW_shade_lining_insurance);
        tvSDEWShadeLiningInsurance = (TextView) findViewById(R.id.tv_SDEW_shade_lining_insurance);
        switchTabViewShadeLining = (SwitchButton) findViewById(R.id.switch_tab_view_shade_lining);
        spinnerRiskOfSpontaneousCombustion = (Spinner) findViewById(R.id.spinner_risk_of_spontaneous_combustion);
        layoutRiskOfSpontaneousCombustionInsurance = (LinearLayout) findViewById(R.id.layout_risk_of_spontaneous_combustion_insurance);
        ivSDEWRiskOfSpontaneousCombustionInsurance = (ImageView) findViewById(R.id.iv_SDEW_risk_of_spontaneous_combustion_insurance);
        tvSDEWRiskOfSpontaneousCombustionInsurance = (TextView) findViewById(R.id.tv_SDEW_risk_of_spontaneous_combustion_insurance);
        switchTabViewRiskOfSpontaneousCombustion = (SwitchButton) findViewById(R.id.switch_tab_view_risk_of_spontaneous_combustion);
        spinnerNewEquipmentRisk = (Spinner) findViewById(R.id.spinner_new_equipment_risk);
        layoutRiskOfNewEquipmentInsurance = (LinearLayout) findViewById(R.id.layout_risk_of_new_equipment_insurance);
        ivSDEWRiskOfNewEquipmentInsurance = (ImageView) findViewById(R.id.iv_SDEW_risk_of_new_equipment_insurance);
        tvSDEWRiskOfNewEquipmentInsurance = (TextView) findViewById(R.id.tv_SDEW_risk_of_new_equipment_insurance);
        switchTabViewRiskOfNewEquipment = (SwitchButton) findViewById(R.id.switch_tab_view_risk_of_new_equipment);
        spinnerScratchesRisk = (Spinner) findViewById(R.id.spinner_scratches_risk);
        layoutRiskOfScratchesInsurance = (LinearLayout) findViewById(R.id.layout_risk_of_scratches_insurance);
        ivSDEWRiskOfScratchesInsurance = (ImageView) findViewById(R.id.iv_SDEW_risk_of_scratches_insurance);
        tvSDEWRiskOfScratchesInsurance = (TextView) findViewById(R.id.tv_SDEW_risk_of_scratches_insurance);
        switchTabViewRiskOfScratches = (SwitchButton) findViewById(R.id.switch_tab_view_risk_of_scratches);
        spinnerEngineWadingInsuranceRisk = (Spinner) findViewById(R.id.spinner_engine_wading_insurance_risk);
        layoutRiskOfEngineWadingInsurance = (LinearLayout) findViewById(R.id.layout_risk_of_engine_wading_insurance);
        ivSDEWRiskOfEngineWadingInsurance = (ImageView) findViewById(R.id.iv_SDEW_risk_of_engine_wading_insurance);
        tvSDEWRiskOfEngineWadingInsurance = (TextView) findViewById(R.id.tv_SDEW_risk_of_engine_wading_insurance);
        switchTabViewRiskOfEngineWading = (SwitchButton) findViewById(R.id.switch_tab_view_risk_of_engine_wading);
        tvQuotaPrice = (TextView) findViewById(R.id.tv_quota_price);
        titleBar = (TitleBar) findViewById(R.id.title_bar);


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
        newValue = getIntent().getFloatExtra("newValue", 0);
        transfer = getIntent().getStringExtra("transfer");
        transferDate = getIntent().getStringExtra("transferDate");
        registrationDateString = getIntent().getStringExtra("registrationDateString");
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
                int uid = PreferenceUtil.load(activity, PreferenceConstant.userid, -1);

                if (uid < 0) {
                    Toast.makeText(activity, "请登录后再购买！", Toast.LENGTH_SHORT).show();
                    LoginActivity.startLoginActivity(activity);
                    return;
                }
                userid = uid + "";

                if (TextUtils.isEmpty(transfer)) {
                    transfer = "0";
                }
                if (TextUtils.isEmpty(transferDate)) {
                    transferDate = "0";
                }


                insuranceItemDatas.clear();
                insuranceItemDatas.add(motor_vehicle_loss_insurance);
                insuranceItemDatas.add(third_responsibility_insurance);
                insuranceItemDatas.add(cab_seat_insurance);
//                insuranceItemDatas.add(passenger_seat_insurance);
//                insuranceItemDatas.add(rob_insurance);
//                insuranceItemDatas.add(risk_of_spontaneous_combustion_insurance);
//                insuranceItemDatas.add(shade_lining_insurance);
//                insuranceItemDatas.add(risk_of_new_equipment_insurance);
//                insuranceItemDatas.add(risk_of_scratches_insurance);
//                insuranceItemDatas.add(risk_of_engine_wading_insurance);


                Gson gson = new Gson();
                insuranceItems = gson.toJson(insuranceItemDatas);


                UserRetrofitUtil.quotaPrice(activity, userid, licenseplate, engineNumber, frameNumber, seatingcapacity, newValue + "",
                        model_code, registrationDateString, ownerName, commercestartdate, compulsorystartdate, issueDateString, provence, provnce_no,
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
                if (flag_switch_SDEWCabSeatInsurance) {
                    if (flag_layout_SDEWCabSeatInsurance) {
                        cab_seat_insurance.setFranchise_flag(1);//不计免赔

                        flag_layout_SDEWCabSeatInsurance = false;
                        ivSDEWCabSeatInsurance.setImageResource(R.drawable.icon_choose);
                        tvSDEWCabSeatInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                    } else {
                        cab_seat_insurance.setFranchise_flag(-1);//不计免赔

                        flag_layout_SDEWCabSeatInsurance = true;
                        ivSDEWCabSeatInsurance.setImageResource(R.drawable.icon_choose_selected);
                        tvSDEWCabSeatInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                    }
                }
            }
        });

        layoutMotorVehicleLossInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_switch_SDEWMotorVehicleLossInsurance) {
                    if (flag_layout_SDEWMotorVehicleLossInsurance) {
                        motor_vehicle_loss_insurance.setFranchise_flag(1);//不计免赔

                        flag_layout_SDEWMotorVehicleLossInsurance = false;
                        ivSDEWMotorVehicleLossInsurance.setImageResource(R.drawable.icon_choose);
                        tvSDEWMotorVehicleLossInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                    } else {
                        motor_vehicle_loss_insurance.setFranchise_flag(-1);

                        flag_layout_SDEWMotorVehicleLossInsurance = true;
                        ivSDEWMotorVehicleLossInsurance.setImageResource(R.drawable.icon_choose_selected);
                        tvSDEWMotorVehicleLossInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                    }
                }
            }
        });

        layoutPassengerSeatInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_switch_SDEWPassengerSeatInsurance) {
                    if (flag_layout_SDEWPassengerSeatInsurance) {
                        passenger_seat_insurance.setFranchise_flag(1);//不计免赔

                        flag_layout_SDEWPassengerSeatInsurance = false;
                        ivSDEWPassengerSeatInsurance.setImageResource(R.drawable.icon_choose);
                        tvSDEWPassengerSeatInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                    } else {
                        passenger_seat_insurance.setFranchise_flag(-1);//不计免赔

                        flag_layout_SDEWPassengerSeatInsurance = true;
                        ivSDEWPassengerSeatInsurance.setImageResource(R.drawable.icon_choose_selected);
                        tvSDEWPassengerSeatInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                    }
                }
            }
        });


        layoutRobInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_switch_SDEWRobInsurance) {
                    if (flag_layout_SDEWRobInsurance) {
                        rob_insurance.setFranchise_flag(1);//不计免赔

                        flag_layout_SDEWRobInsurance = false;
                        ivSDEWRobInsurance.setImageResource(R.drawable.icon_choose);
                        tvSDEWRobInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                    } else {
                        rob_insurance.setFranchise_flag(-1);//不计免赔

                        flag_layout_SDEWRobInsurance = true;
                        ivSDEWRobInsurance.setImageResource(R.drawable.icon_choose_selected);
                        tvSDEWRobInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                    }
                }
            }
        });


        layoutThirdResponsibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_switch_SDEWThirdResponsibilityInsurance) {
                    if (flag_layout_SDEWThirdResponsibilityInsurance) {
                        third_responsibility_insurance.setFranchise_flag(1);//不计免赔

                        flag_layout_SDEWThirdResponsibilityInsurance = false;
                        ivSDEWThirdResponsibilityInsurance.setImageResource(R.drawable.icon_choose);
                        tvSDEWThirdResponsibilityInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                    } else {
                        third_responsibility_insurance.setFranchise_flag(-1);//不计免赔

                        flag_layout_SDEWThirdResponsibilityInsurance = true;
                        ivSDEWThirdResponsibilityInsurance.setImageResource(R.drawable.icon_choose_selected);
                        tvSDEWThirdResponsibilityInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                    }
                }
            }
        });


        //附加险
        layoutShadeLiningInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_switch_SDEWShadeLiningInsurance) {
                    if (flag_layout_SDEWShadeLiningInsurance) {
                        shade_lining_insurance.setFranchise_flag(1);//不计免赔
                        flag_layout_SDEWShadeLiningInsurance = false;
                        ivSDEWShadeLiningInsurance.setImageResource(R.drawable.icon_choose);
                        tvSDEWShadeLiningInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                    } else {
                        shade_lining_insurance.setFranchise_flag(-1);//不计免赔
                        flag_layout_SDEWShadeLiningInsurance = true;
                        ivSDEWShadeLiningInsurance.setImageResource(R.drawable.icon_choose_selected);
                        tvSDEWShadeLiningInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                    }
                }
            }
        });

        //自燃险
        layoutRiskOfSpontaneousCombustionInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_switch_SDEWRiskOfSpontaneousCombustionInsurance) {
                    if (flag_layout_SDEWRiskOfSpontaneousCombustionInsurance) {
                        risk_of_spontaneous_combustion_insurance.setFranchise_flag(1);//不计免赔
                        flag_layout_SDEWRiskOfSpontaneousCombustionInsurance = false;
                        ivSDEWRiskOfSpontaneousCombustionInsurance.setImageResource(R.drawable.icon_choose);
                        tvSDEWRiskOfSpontaneousCombustionInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                    } else {
                        risk_of_spontaneous_combustion_insurance.setFranchise_flag(-1);
                        flag_layout_SDEWRiskOfSpontaneousCombustionInsurance = true;
                        ivSDEWRiskOfSpontaneousCombustionInsurance.setImageResource(R.drawable.icon_choose_selected);
                        tvSDEWRiskOfSpontaneousCombustionInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                    }
                }
            }
        });

        layoutRiskOfScratchesInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_switch_SDEWRiskOfScratchesInsurance) {
                    if (flag_layout_SDEWRiskOfScratchesInsurance) {
                        risk_of_scratches_insurance.setFranchise_flag(1);//不计免赔
                        flag_layout_SDEWRiskOfScratchesInsurance = false;
                        ivSDEWRiskOfScratchesInsurance.setImageResource(R.drawable.icon_choose);
                        tvSDEWRiskOfScratchesInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                    } else {
                        risk_of_scratches_insurance.setFranchise_flag(-1);//不计免赔
                        flag_layout_SDEWRiskOfScratchesInsurance = true;
                        ivSDEWRiskOfScratchesInsurance.setImageResource(R.drawable.icon_choose_selected);
                        tvSDEWRiskOfScratchesInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                    }
                }
            }
        });


        layoutRiskOfNewEquipmentInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_switch_SDEWRiskOfNewEquipmentInsurance) {
                    if (flag_layout_SDEWRiskOfNewEquipmentInsurance) {
                        risk_of_new_equipment_insurance.setFranchise_flag(1);//不计免赔
                        flag_layout_SDEWRiskOfNewEquipmentInsurance = false;
                        ivSDEWRiskOfNewEquipmentInsurance.setImageResource(R.drawable.icon_choose);
                        tvSDEWRiskOfNewEquipmentInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                    } else {
                        risk_of_new_equipment_insurance.setFranchise_flag(-1);//不计免赔
                        flag_layout_SDEWRiskOfNewEquipmentInsurance = true;
                        ivSDEWRiskOfNewEquipmentInsurance.setImageResource(R.drawable.icon_choose_selected);
                        tvSDEWRiskOfNewEquipmentInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                    }
                }
            }
        });


        layoutRiskOfEngineWadingInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_switch_SDEWRiskOfEngineWadingInsurance) {
                    if (flag_layout_SDEWRiskOfEngineWadingInsurance) {
                        risk_of_engine_wading_insurance.setFranchise_flag(1);//不计免赔
                        flag_layout_SDEWRiskOfEngineWadingInsurance = false;
                        ivSDEWRiskOfEngineWadingInsurance.setImageResource(R.drawable.icon_choose);
                        tvSDEWRiskOfEngineWadingInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                    } else {
                        risk_of_engine_wading_insurance.setFranchise_flag(-1);//不计免赔
                        flag_layout_SDEWRiskOfEngineWadingInsurance = true;
                        ivSDEWRiskOfEngineWadingInsurance.setImageResource(R.drawable.icon_choose_selected);
                        tvSDEWRiskOfEngineWadingInsurance.setTextColor(Color.parseColor("#0b0b0b"));
                    }
                }
            }
        });


        //车损险
        //switch button 开关
        switchTabViewMotorVehicleLossInsurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flag_switch_SDEWMotorVehicleLossInsurance = isChecked;
                if (!isChecked) {
                    motor_vehicle_loss_insurance.setFranchise_flag(-1);
                    //switch打开时，前面的图标可以点击；如果关闭，前面的图标不能点击，且变灰
                    flag_layout_SDEWMotorVehicleLossInsurance = false;
                    ivSDEWMotorVehicleLossInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWMotorVehicleLossInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                }

            }
        });

        //第三方责任险
        switchTabViewThirdResponsibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flag_switch_SDEWThirdResponsibilityInsurance = isChecked;

                if (!isChecked) {
                    third_responsibility_insurance.setFranchise_flag(-1);
                    //switch打开时，前面的图标可以点击；如果关闭，前面的图标不能点击，且变灰
                    flag_layout_SDEWThirdResponsibilityInsurance = false;
                    ivSDEWThirdResponsibilityInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWThirdResponsibilityInsurance.setTextColor(Color.parseColor("#f0f0f0"));

                }
            }
        });


        //司机座位险
        switchTabViewCabChair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flag_switch_SDEWCabSeatInsurance = isChecked;
                if (!isChecked) {
                    cab_seat_insurance.setFranchise_flag(-1);
                    //switch打开时，前面的图标可以点击；如果关闭，前面的图标不能点击，且变灰
                    flag_layout_SDEWCabSeatInsurance = false;
                    ivSDEWCabSeatInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWCabSeatInsurance.setTextColor(Color.parseColor("#f0f0f0"));

                }
            }
        });

        //乘客座位险
        switchTabViewPassengeChair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flag_switch_SDEWPassengerSeatInsurance = isChecked;
                if (!isChecked) {
                    passenger_seat_insurance.setFranchise_flag(-1);
                    //switch打开时，前面的图标可以点击；如果关闭，前面的图标不能点击，且变灰
                    flag_layout_SDEWPassengerSeatInsurance = false;
                    ivSDEWPassengerSeatInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWPassengerSeatInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                }
            }
        });
        //强盗险
        switchTabViewRob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flag_switch_SDEWRobInsurance = isChecked;
                if (!isChecked) {
                    rob_insurance.setFranchise_flag(-1);
                    //switch打开时，前面的图标可以点击；如果关闭，前面的图标不能点击，且变灰
                    flag_layout_SDEWRobInsurance = false;
                    ivSDEWRobInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWRobInsurance.setTextColor(Color.parseColor("#f0f0f0"));

                }
            }
        });


        //玻璃险
        switchTabViewShadeLining.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flag_switch_SDEWShadeLiningInsurance = isChecked;
                if(!isChecked){
                    shade_lining_insurance.setFranchise_flag(-1);
                    //switch打开时，前面的图标可以点击；如果关闭，前面的图标不能点击，且变灰
                    flag_layout_SDEWShadeLiningInsurance = false;
                    ivSDEWShadeLiningInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWShadeLiningInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                }
            }
        });

        //自燃险
        switchTabViewRiskOfSpontaneousCombustion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flag_switch_SDEWRiskOfSpontaneousCombustionInsurance = isChecked;
                if(!isChecked){
                    risk_of_spontaneous_combustion_insurance.setFranchise_flag(-1);
                    flag_layout_SDEWRiskOfSpontaneousCombustionInsurance=false;
                    ivSDEWRiskOfSpontaneousCombustionInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWRiskOfSpontaneousCombustionInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                }
            }
        });

        //新增设备险
        switchTabViewRiskOfNewEquipment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flag_switch_SDEWRiskOfNewEquipmentInsurance = isChecked;
                if (!isChecked) {
                    risk_of_new_equipment_insurance.setFranchise_flag(-1);
                    flag_layout_SDEWRiskOfNewEquipmentInsurance = false;
                    ivSDEWRiskOfNewEquipmentInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWRiskOfNewEquipmentInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                }
            }
        });


        //划痕险
        switchTabViewRiskOfScratches.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flag_switch_SDEWRiskOfScratchesInsurance = isChecked;
                if (!isChecked) {
                    risk_of_scratches_insurance.setFranchise_flag(-1);
                    flag_layout_SDEWRiskOfScratchesInsurance = false;
                    ivSDEWRiskOfScratchesInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWRiskOfScratchesInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                }
            }
        });

        //发动机涉水险
        switchTabViewRiskOfEngineWading.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flag_switch_SDEWRiskOfEngineWadingInsurance = isChecked;
                if (!isChecked) {
                    risk_of_engine_wading_insurance.setFranchise_flag(-1);
                    flag_layout_SDEWRiskOfEngineWadingInsurance = false;
                    ivSDEWRiskOfEngineWadingInsurance.setImageResource(R.drawable.icon_choose);
                    tvSDEWRiskOfEngineWadingInsurance.setTextColor(Color.parseColor("#f0f0f0"));
                }
            }
        });


    }

    private void setUp() {
        titleBar.initTitleBarInfo("投保方案", R.drawable.arrow_left, -1, "", "");


        //限行停保的 Spinner
        mWeekNumbersStringList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.insurancePrice)));
        spinnerDropDownAdapter = new SpinnerDropDownAdapter(activity, mWeekNumbersStringList);


        spinnerDriverChair.setAdapter(spinnerDropDownAdapter);
        spinnerPassageChair.setAdapter(spinnerDropDownAdapter);
        spinnerThirdResponsibility.setAdapter(spinnerDropDownAdapter);
        spinnerRob.setAdapter(spinnerDropDownAdapter);
        spinnerShadeLining.setAdapter(spinnerDropDownAdapter);
        spinnerRiskOfSpontaneousCombustion.setAdapter(spinnerDropDownAdapter);
        spinnerNewEquipmentRisk.setAdapter(spinnerDropDownAdapter);
        spinnerScratchesRisk.setAdapter(spinnerDropDownAdapter);
        spinnerEngineWadingInsuranceRisk.setAdapter(spinnerDropDownAdapter);


        switchTabViewMotorVehicleLossInsurance.setChecked(true);
        switchTabViewCabChair.setChecked(true);
        switchTabViewPassengeChair.setChecked(true);
        switchTabViewRob.setChecked(true);
        switchTabViewThirdResponsibility.setChecked(true);


        switchTabViewShadeLining.setChecked(true);
        switchTabViewRiskOfSpontaneousCombustion.setChecked(true);
        switchTabViewRiskOfNewEquipment.setChecked(true);
        switchTabViewRiskOfScratches.setChecked(true);
        switchTabViewRiskOfEngineWading.setChecked(true);


        //不能单独保车辆损失险
        motor_vehicle_loss_insurance.setInsrnc_name("车辆损失险");//保险名称
        motor_vehicle_loss_insurance.setPremium(-1f);//保费，用户买保险要掏腰包，但是这里不知道，我们的目的就是为了获取它
        motor_vehicle_loss_insurance.setRemark("");//备注，一般为空字符串
        motor_vehicle_loss_insurance.setInsrnc_cde("030101");//保险代码
        motor_vehicle_loss_insurance.setC_ly15(-1);//车辆损失绝对免赔额，需要问清楚到底是干嘛的
        motor_vehicle_loss_insurance.setFranchise_flag(1);//是否不计免赔
        motor_vehicle_loss_insurance.setNumber(-1);//保障天数
        motor_vehicle_loss_insurance.setBullet_glass(-1);//是否有防弹玻璃
        motor_vehicle_loss_insurance.setAmt(newValue + "");//保额，出险赔多少，需要我去写个String Array 供用户选择。但是车辆损失险 的 保额就是车辆的价格。所以不用选择的


        //第三方责任险
        third_responsibility_insurance.setInsrnc_name("第三方责任险");//保险名称
        third_responsibility_insurance.setPremium(-1f);//保费，用户买保险要掏腰包，但是这里不知道，我们的目的就是为了获取它
        third_responsibility_insurance.setRemark("");//备注，一般为空字符串
        third_responsibility_insurance.setInsrnc_cde("030102");//保险代码
        third_responsibility_insurance.setC_ly15(-1);//车辆损失绝对免赔额，需要问清楚到底是干嘛的
        third_responsibility_insurance.setNumber(-1);//保障天数
        third_responsibility_insurance.setFranchise_flag(1);//是否不计免赔
        third_responsibility_insurance.setBullet_glass(-1);//是否有防弹玻璃
        third_responsibility_insurance.setAmt(306006021 + "");//默认值   保额，出险赔多少，需要我去写个String Array 供用户选择。但是车辆损失险 的 保额就是车辆的价格。所以不用选择的


        //盗抢险
        rob_insurance.setInsrnc_name("盗抢险");//保险名称
        rob_insurance.setPremium(-1f);//保费，用户买保险要掏腰包，但是这里不知道，我们的目的就是为了获取它
        rob_insurance.setRemark("");//备注，一般为空字符串
        rob_insurance.setInsrnc_cde("030103");//保险代码
        rob_insurance.setNumber(-1);//保障天数
        rob_insurance.setC_ly15(-1);//车辆损失绝对免赔额，需要问清楚到底是干嘛的
        rob_insurance.setFranchise_flag(1);//是否不计免赔
        rob_insurance.setBullet_glass(-1);//是否有防弹玻璃
        rob_insurance.setAmt(newValue + "");//折旧后   保额，出险赔多少，需要我去写个String Array 供用户选择。但是车辆损失险 的 保额就是车辆的价格。所以不用选择的


        //司机座位险
        cab_seat_insurance.setInsrnc_name("司机座位险");//保险名称
        cab_seat_insurance.setPremium(-1f);//保费，用户买保险要掏腰包，但是这里不知道，我们的目的就是为了获取它
        cab_seat_insurance.setRemark("");//备注，一般为空字符串
        cab_seat_insurance.setInsrnc_cde("030104");//保险代码
        cab_seat_insurance.setC_ly15(-1);//车辆损失绝对免赔额，需要问清楚到底是干嘛的
        cab_seat_insurance.setFranchise_flag(1);//是否不计免赔
        cab_seat_insurance.setNumber(-1);//保障天数
        cab_seat_insurance.setBullet_glass(-1);//是否有防弹玻璃
        cab_seat_insurance.setAmt(306006021 + "");//保额，出险赔多少，需要我去写个String Array 供用户选择。但是车辆损失险 的 保额就是车辆的价格。所以不用选择的


        //乘客座位险
        passenger_seat_insurance.setInsrnc_name("乘客座位险");//保险名称
        passenger_seat_insurance.setPremium(-1f);//保费，用户买保险要掏腰包，但是这里不知道，我们的目的就是为了获取它
        passenger_seat_insurance.setRemark("");//备注，一般为空字符串
        passenger_seat_insurance.setInsrnc_cde("030105");//保险代码
        passenger_seat_insurance.setC_ly15(-1);//车辆损失绝对免赔额，需要问清楚到底是干嘛的
        passenger_seat_insurance.setNumber(-1);//保障天数
        passenger_seat_insurance.setFranchise_flag(1);//是否不计免赔
        passenger_seat_insurance.setBullet_glass(-1);//是否有防弹玻璃
        passenger_seat_insurance.setAmt(306006021 + "");//保额，出险赔多少，需要我去写个String Array 供用户选择。但是车辆损失险 的 保额就是车辆的价格。所以不用选择的


        //附加险


        //玻璃险
        shade_lining_insurance.setInsrnc_name("玻璃险");//保险名称
        shade_lining_insurance.setPremium(-1f);//保费，用户买保险要掏腰包，但是这里不知道，我们的目的就是为了获取它
        shade_lining_insurance.setRemark("");//备注，一般为空字符串
        shade_lining_insurance.setInsrnc_cde("030107");//保险代码
        shade_lining_insurance.setC_ly15(-1);//车辆损失绝对免赔额，需要问清楚到底是干嘛的
        shade_lining_insurance.setFranchise_flag(1);//是否不计免赔
        shade_lining_insurance.setBullet_glass(-1);//是否有防弹玻璃
        shade_lining_insurance.setNumber(-1);//保障天数
        shade_lining_insurance.setAmt(0 + "");//保额，出险赔多少，需要我去写个String Array 供用户选择。但是车辆损失险 的 保额就是车辆的价格。所以不用选择的


        //自燃险
        risk_of_spontaneous_combustion_insurance.setInsrnc_name("自燃险");//保险名称
        risk_of_spontaneous_combustion_insurance.setPremium(-1f);//保费，用户买保险要掏腰包，但是这里不知道，我们的目的就是为了获取它
        risk_of_spontaneous_combustion_insurance.setRemark("");//备注，一般为空字符串
        risk_of_spontaneous_combustion_insurance.setInsrnc_cde("030108");//保险代码
        risk_of_spontaneous_combustion_insurance.setC_ly15(-1);//车辆损失绝对免赔额，需要问清楚到底是干嘛的
        risk_of_spontaneous_combustion_insurance.setFranchise_flag(1);//是否不计免赔
        risk_of_spontaneous_combustion_insurance.setBullet_glass(-1);//是否有防弹玻璃
        risk_of_spontaneous_combustion_insurance.setNumber(-1);//保障天数
        risk_of_spontaneous_combustion_insurance.setAmt(newValue + "");//保额，出险赔多少，需要我去写个String Array 供用户选择。但是车辆损失险 的 保额就是车辆的价格。所以不用选择的


        //新增设备险
        risk_of_new_equipment_insurance.setInsrnc_name("新增设备险");//保险名称
        risk_of_new_equipment_insurance.setPremium(-1f);//保费，用户买保险要掏腰包，但是这里不知道，我们的目的就是为了获取它
        risk_of_new_equipment_insurance.setRemark("");//备注，一般为空字符串
        risk_of_new_equipment_insurance.setInsrnc_cde("030109");//保险代码
        risk_of_new_equipment_insurance.setC_ly15(-1);//车辆损失绝对免赔额，需要问清楚到底是干嘛的
        risk_of_new_equipment_insurance.setFranchise_flag(1);//是否不计免赔
        risk_of_new_equipment_insurance.setBullet_glass(-1);//是否有防弹玻璃
        risk_of_new_equipment_insurance.setNumber(-1);//保障天数
        risk_of_new_equipment_insurance.setAmt(0 + "");//保额，出险赔多少，需要我去写个String Array 供用户选择。但是车辆损失险 的 保额就是车辆的价格。所以不用选择的


        //划痕险
        risk_of_scratches_insurance.setInsrnc_name("划痕险");//保险名称
        risk_of_scratches_insurance.setPremium(-1f);//保费，用户买保险要掏腰包，但是这里不知道，我们的目的就是为了获取它
        risk_of_scratches_insurance.setRemark("");//备注，一般为空字符串
        risk_of_scratches_insurance.setInsrnc_cde("030110");//保险代码
        risk_of_scratches_insurance.setC_ly15(-1);//车辆损失绝对免赔额，需要问清楚到底是干嘛的
        risk_of_scratches_insurance.setFranchise_flag(1);//是否不计免赔
        risk_of_scratches_insurance.setBullet_glass(-1);//是否有防弹玻璃
        risk_of_scratches_insurance.setNumber(-1);//保障天数
        risk_of_scratches_insurance.setAmt(365003 + "");//保额，出险赔多少，需要我去写个String Array 供用户选择。但是车辆损失险 的 保额就是车辆的价格。所以不用选择的


        //发动机涉水险
        risk_of_engine_wading_insurance.setInsrnc_name("发动机涉水险");//保险名称
        risk_of_engine_wading_insurance.setPremium(-1f);//保费，用户买保险要掏腰包，但是这里不知道，我们的目的就是为了获取它
        risk_of_engine_wading_insurance.setRemark("");//备注，一般为空字符串
        risk_of_engine_wading_insurance.setInsrnc_cde("030111");//保险代码
        risk_of_engine_wading_insurance.setC_ly15(-1);//车辆损失绝对免赔额，需要问清楚到底是干嘛的
        risk_of_engine_wading_insurance.setFranchise_flag(1);//是否不计免赔
        risk_of_engine_wading_insurance.setBullet_glass(-1);//是否有防弹玻璃
        risk_of_engine_wading_insurance.setNumber(-1);//保障天数
        risk_of_engine_wading_insurance.setAmt(0 + "");//保额，出险赔多少，需要我去写个String Array 供用户选择。但是车辆损失险 的 保额就是车辆的价格。所以不用选择的


//
//        //停驶损失险
//         risk_of_engine_wading_insurance.setInsrnc_name("停驶损失险");//保险名称
//         risk_of_engine_wading_insurance.setPremium(-1f);//保费，用户买保险要掏腰包，但是这里不知道，我们的目的就是为了获取它
//         risk_of_engine_wading_insurance.setRemark("");//备注，一般为空字符串
//         risk_of_engine_wading_insurance.setInsrnc_cde("030112");//保险代码
//         risk_of_engine_wading_insurance.setC_ly15(-1);//车辆损失绝对免赔额，需要问清楚到底是干嘛的
//         risk_of_engine_wading_insurance.setFranchise_flag(1);//是否不计免赔
//         risk_of_engine_wading_insurance.setBullet_glass(-1);//是否有防弹玻璃
//         risk_of_engine_wading_insurance.setAmt(0+"");//保额，出险赔多少，需要我去写个String Array 供用户选择。但是车辆损失险 的 保额就是车辆的价格。所以不用选择的
//


    }

}
