package com.jiandanbaoxian.ui.buyinsurance.car_insurance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jiandanbaoxian.R;
import com.jiandanbaoxian.adapter.SpinnerDropDownAdapter;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.ExtraConstants;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.fragment.DialogFragmentCreater;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.RegionBean;
import com.jiandanbaoxian.test.API_Test;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.LoginActivity;
import com.jiandanbaoxian.util.JsonUtil;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.widget.TitleBar;
import com.jiandanbaoxian.widget.decoview.nineoldandroids.animation.ObjectAnimator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * 车险主界面
 */
public class CarInsuranceBaseInfoActivity extends BaseActivity {
    java.text.NumberFormat nf = java.text.NumberFormat.getInstance();//double 不使用科学计数法

    private final static int MODIFY_CITY = 103;
    private String driverAreaNameString = "";

    private DialogFragmentCreater dialogFragmentCreater;
    private List<RegionBean> provinceRegionList = new ArrayList<>();
    private List<RegionBean> cityRegionList = new ArrayList<>();
    private List<RegionBean> countryRegionList = new ArrayList<>();

    private int flag_choose_region_counter = 0;//0-province 1-city  2-country


    private String licenseplate = "";
    private String engineNumber = "";
    private String frameNumber = "";
    private String userName = "";
    private String idCardNumber = "";
    private String model_name = "";
    private int userId = -1;
    private int transfer = 0;
    private long transferDateLong = 0;
    private long registrationDateLong = 0;
    private long issueDateLong = 0;

    private String province_no = "";
    private String province_name = "";

    private String city_no = "";
    private String city_name = "";

    private String country_no = "";
    private String country_name = "";

    private ProgressDialogUtil progressDialogUtil;
    private Activity activity;
    private boolean isValidToNextActivity = false;
    private SimpleDateFormat dateFormat_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");//日期格式化
    private TitleBar titleBar;
    private ImageView ivTakeCarPicture;
    private EditText etLicensePlateNumber;
    private EditText etOwnerName;
    private TextView tvDriverArea;
    private EditText etVehicleType;
    private EditText etVehicleFrameNumber;
    private EditText etEngineNumber;
    private LinearLayout layoutDatePicker;
    private LinearLayout layoutIssueDate;
    private View line;
    private TextView tvRegistrationDate;
    private TextView tvIssueDate;
    private EditText etIdNumber;
    private TextView tvAssignedYes;
    private TextView tvAssignedNo;
    private TextView tvConfirm;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-02-25 16:00:51 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        ivTakeCarPicture = (ImageView) findViewById(R.id.iv_take_car_picture);
        etLicensePlateNumber = (EditText) findViewById(R.id.et_license_plate_number);
        etOwnerName = (EditText) findViewById(R.id.et_owner_name);
        tvDriverArea = (TextView) findViewById(R.id.tv_driver_area);
        etVehicleType = (EditText) findViewById(R.id.et_vehicle_type);
        etVehicleFrameNumber = (EditText) findViewById(R.id.et_vehicle_frame_number);
        etEngineNumber = (EditText) findViewById(R.id.et_engine_number);
        layoutDatePicker = (LinearLayout) findViewById(R.id.layout_date_picker);
        layoutIssueDate = (LinearLayout) findViewById(R.id.layout_issue_date);
        line = findViewById(R.id.line);
        tvRegistrationDate = (TextView) findViewById(R.id.tv_registration_date);
        tvIssueDate = (TextView) findViewById(R.id.tv_issue_date);
        etIdNumber = (EditText) findViewById(R.id.et_id_number);
        tvAssignedYes = (TextView) findViewById(R.id.tv_assigned_yes);
        tvAssignedNo = (TextView) findViewById(R.id.tv_assigned_no);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insure_car);
        progressDialogUtil = new ProgressDialogUtil(this, true);
        activity = this;
        //作为Dialog的生成器
        dialogFragmentCreater = new DialogFragmentCreater();//涉及到权限操作时，需要临时输入密码并验证
        dialogFragmentCreater.setDialogContext(activity, getSupportFragmentManager());
        nf.setGroupingUsed(false);

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


        tvRegistrationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (registrationDateLong != 0) {
                    calendar.setTime(new Date(registrationDateLong));
                }
                new DatePickerDialog(CarInsuranceBaseInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvRegistrationDate.setText(year + "-" + (++monthOfYear) + "-" + dayOfMonth);
                        String mm = "" + monthOfYear;
                        String dd = "" + dayOfMonth;
                        if (monthOfYear < 10 && mm.length() == 1) {
                            mm = "0" + mm;
                        }
                        if (dayOfMonth < 10 && dd.length() == 1) {
                            dd = "0" + dd;
                        }
                        long jqxTimeLong = 0;
                        try {
                            jqxTimeLong = dateFormat_yyyy_MM_dd.parse(year + "-" + mm + "-" + dd).getTime();
                        } catch (ParseException e) {
                            jqxTimeLong = 0L;
                            e.printStackTrace();
                            Toast.makeText(activity, "Error in convert time !", Toast.LENGTH_LONG).show();
                        } finally {
                            registrationDateLong = jqxTimeLong;
                        }

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        tvIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                if (issueDateLong != 0) {
                    calendar.setTime(new Date(issueDateLong));
                }
                new DatePickerDialog(CarInsuranceBaseInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvIssueDate.setText(year + "-" + (++monthOfYear) + "-" + dayOfMonth);
                        String mm = "" + monthOfYear;
                        String dd = "" + dayOfMonth;
                        if (monthOfYear < 10 && mm.length() == 1) {
                            mm = "0" + mm;
                        }
                        if (dayOfMonth < 10 && dd.length() == 1) {
                            dd = "0" + dd;
                        }
                        long jqxTimeLong = 0;
                        try {
                            jqxTimeLong = dateFormat_yyyy_MM_dd.parse(year + "-" + mm + "-" + dd).getTime();
                        } catch (ParseException e) {
                            jqxTimeLong = 0L;
                            e.printStackTrace();
                            Toast.makeText(activity, "Error in convert time !", Toast.LENGTH_LONG).show();
                        } finally {
                            issueDateLong = jqxTimeLong;
                        }

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = PreferenceUtil.load(activity, PreferenceConstant.userid, -1);
                if (userId < 0) {
                    Toast.makeText(activity, "请登录后再购买！", Toast.LENGTH_SHORT).show();
                    LoginActivity.startLoginActivity(activity);
                    return;
                }
                if (issueDateLong != registrationDateLong) {
                    transfer = 1;
                    transferDateLong = issueDateLong;
                } else {
                    transfer = 0;
                    transferDateLong = 0;
                }
                engineNumber = etEngineNumber.getText().toString();
                frameNumber = etVehicleFrameNumber.getText().toString();
                userName = etOwnerName.getText().toString();
                idCardNumber = etIdNumber.getText().toString();
                model_name = etVehicleType.getText().toString();
                licenseplate = etLicensePlateNumber.getText().toString();
                Log.e("qq", "province_no:" + province_no + "");
                Log.e("qq", "province_name:" + province_name + "");
                Log.e("qq", "city_no:" + city_no + "");
                Log.e("qq", "county_no:" + country_no + "");
                if (isValidToNextActivity) {
                    CarInsurancePickCarTypeActivity.startActivity(activity, licenseplate, engineNumber, frameNumber, userName
                            , province_no, province_name, city_no, city_name, country_no, country_name, transfer + "", transferDateLong + "", registrationDateLong + "", issueDateLong + "", idCardNumber,model_name);
                } else {
                    Toast.makeText(activity, "请完善以上信息！", Toast.LENGTH_LONG).show();
                }
            }
        });


        tvAssignedNo.setTextColor(Color.parseColor("#ffffff"));
        tvAssignedNo.setBackgroundResource(R.drawable.btn_select_base);
        tvAssignedYes.setTextColor(Color.parseColor("#3d3d3d"));
        tvAssignedYes.setBackgroundResource(R.drawable.btn_select_white);


        tvAssignedNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutIssueDate.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                transfer = 0;
                transferDateLong = 0;
                tvAssignedNo.setTextColor(Color.parseColor("#ffffff"));
                tvAssignedNo.setBackgroundResource(R.drawable.btn_select_base);
                tvAssignedYes.setTextColor(Color.parseColor("#3d3d3d"));
                tvAssignedYes.setBackgroundResource(R.drawable.btn_select_white);
            }
        });


        tvAssignedYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer = 1;
                transferDateLong = issueDateLong;
                layoutIssueDate.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                tvAssignedYes.setTextColor(Color.parseColor("#ffffff"));
                tvAssignedYes.setBackgroundResource(R.drawable.btn_select_base);
                tvAssignedNo.setTextColor(Color.parseColor("#3d3d3d"));
                tvAssignedNo.setBackgroundResource(R.drawable.btn_select_white);
            }
        });


        tvDriverArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialogUtil.show("正在获取省份列表...");
                UserRetrofitUtil.getProvenceNo(activity, new NetCallback<NetWorkResultBean<Object>>(activity) {
                    @Override
                    public void onFailure(RetrofitError error, String message) {
                        progressDialogUtil.hide();
                        Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void success(NetWorkResultBean<Object> listNetWorkResultBean, Response response) {
                        progressDialogUtil.hide();
                        if (listNetWorkResultBean != null) {
                            int status = listNetWorkResultBean.getStatus();
                            switch (status) {
                                case HttpsURLConnection.HTTP_OK:
                                    if (listNetWorkResultBean != null && listNetWorkResultBean.getData() != null) {

                                        List<RegionBean> regionBeans = null;
                                        Object obj = listNetWorkResultBean.getData();
                                        if (obj instanceof String) {
                                            Toast.makeText(activity, listNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        try {
                                            String json = com.jiandanbaoxian.util.JsonUtil.toJson(obj);
                                            regionBeans = com.jiandanbaoxian.util.JsonUtil.fromJson(json, new TypeToken<List<RegionBean>>() {
                                            }.getType());
                                        } catch (Exception e) {
                                            Log.e("qw", "出错啦！！！!");
                                        }
                                        if (regionBeans == null) {
                                            Toast.makeText(activity, "系统异常！", Toast.LENGTH_LONG).show();
                                            return;
                                        }

                                        provinceRegionList.clear();
                                        provinceRegionList.addAll(regionBeans);
                                    }
                                    flag_choose_region_counter = 1;
                                    dialogFragmentCreater.setData(provinceRegionList, "");
                                    dialogFragmentCreater.showDialog(activity, DialogFragmentCreater.DialogShowRegionChoiceDialog);
                                    break;
                                default:
                                    Toast.makeText(activity, listNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }


                    }
                });
            }
        });


        dialogFragmentCreater.setOnDialogBackClickLisenter(new DialogFragmentCreater.OnDialogBackClickLisenter() {
            @Override
            public void onClickBack(String tvText, List<RegionBean> regionBeans, View view) {
                flag_choose_region_counter--;
                switch (flag_choose_region_counter) {
                    case 0:
                        dialogFragmentCreater.dismiss();
                        break;
                    case 1:
                        dialogFragmentCreater.updateData(provinceRegionList, "选择省份");
                        break;
                    case 2:
                        dialogFragmentCreater.updateData(cityRegionList, province_name);
                        break;
                }

            }

            @Override
            public void onItemClickListener(int position, long id) {
                switch (flag_choose_region_counter) {
                    case 1: {//chosen province and to choose city
                        if (provinceRegionList != null && provinceRegionList.size() > position) {
                            province_no = provinceRegionList.get(position).getRegionNum();
                            progressDialogUtil.show("正在获取市级行政代码...");
                            province_name = provinceRegionList.get(position).getRegionName();
                            UserRetrofitUtil.getCityNo(activity, province_no, new NetCallback<NetWorkResultBean<Object>>(activity) {
                                @Override
                                public void onFailure(RetrofitError error, String message) {
                                    progressDialogUtil.hide();
                                    Toast.makeText(activity, "请检查网络设置", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void success(NetWorkResultBean<Object> listNetWorkResultBean, Response response) {


                                    if (listNetWorkResultBean != null) {
                                        int status = listNetWorkResultBean.getStatus();
                                        switch (status) {
                                            case HttpsURLConnection.HTTP_OK:
                                                if (listNetWorkResultBean != null && listNetWorkResultBean.getData() != null) {



                                                    List<RegionBean> regionBeans = null;
                                                    Object obj = listNetWorkResultBean.getData();
                                                    if (obj instanceof String) {
                                                        Toast.makeText(activity, listNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                        return;
                                                    }
                                                    try {
                                                        String json = com.jiandanbaoxian.util.JsonUtil.toJson(obj);
                                                        regionBeans = com.jiandanbaoxian.util.JsonUtil.fromJson(json, new TypeToken<List<RegionBean>>() {
                                                        }.getType());
                                                    } catch (Exception e) {
                                                        Log.e("qw", "出错啦！！！!");
                                                    }
                                                    if (regionBeans == null) {
                                                        Toast.makeText(activity, "系统异常！", Toast.LENGTH_LONG).show();
                                                        return;
                                                    }

                                                    cityRegionList.clear();
                                                    cityRegionList.addAll(regionBeans);

                                                    dialogFragmentCreater.updateData(cityRegionList, province_name);
                                                }
                                                progressDialogUtil.hide();
                                                flag_choose_region_counter++;
                                                break;
                                            default:
                                                Toast.makeText(activity, listNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                break;
                                        }
                                    }


                                }
                            });
                        }
                    }

                    break;
                    case 2: {//chosen city and to choose country
                        if (cityRegionList != null && cityRegionList.size() > position) {
                            city_no = cityRegionList.get(position).getRegionNum();
                            city_name = cityRegionList.get(position).getRegionName();
                            progressDialogUtil.show("正在获取县级行政代码...");
                            UserRetrofitUtil.getCountyNo(activity, city_no, new NetCallback<NetWorkResultBean<Object>>(activity) {
                                @Override
                                public void onFailure(RetrofitError error, String message) {
                                    progressDialogUtil.hide();
                                }

                                @Override
                                public void success(NetWorkResultBean<Object> listNetWorkResultBean, Response response) {


                                    if (listNetWorkResultBean != null) {
                                        int status = listNetWorkResultBean.getStatus();
                                        switch (status) {
                                            case HttpsURLConnection.HTTP_OK:
                                                if (listNetWorkResultBean != null && listNetWorkResultBean.getData() != null) {

                                                    List<RegionBean> regionBeans = null;
                                                    Object obj = listNetWorkResultBean.getData();
                                                    if (obj instanceof String) {
                                                        Toast.makeText(activity, listNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                        return;
                                                    }
                                                    try {
                                                        String json = com.jiandanbaoxian.util.JsonUtil.toJson(obj);
                                                        regionBeans = com.jiandanbaoxian.util.JsonUtil.fromJson(json, new TypeToken<List<RegionBean>>() {
                                                        }.getType());
                                                    } catch (Exception e) {
                                                        Log.e("qw", "出错啦！！！!");
                                                    }
                                                    if (regionBeans == null) {
                                                        Toast.makeText(activity, "系统异常！", Toast.LENGTH_LONG).show();
                                                        return;
                                                    }
                                                    countryRegionList.clear();
                                                    countryRegionList.addAll(regionBeans);

                                                    dialogFragmentCreater.updateData(countryRegionList, city_name);
                                                }
                                                progressDialogUtil.hide();
                                                flag_choose_region_counter++;
                                                break;
                                            default:
                                                Toast.makeText(activity, listNetWorkResultBean.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                break;
                                        }
                                    }

                                }
                            });
                        }

                    }
                    break;
                    case 3: {//最后一次的选择 县城区域编码
                        if (countryRegionList != null && countryRegionList.size() > position) {
                            country_no = countryRegionList.get(position).getRegionNum();
                            country_name = countryRegionList.get(position).getRegionName();
                            dialogFragmentCreater.dismiss();
                            flag_choose_region_counter = 0;
                            tvDriverArea.setText("" + province_name + "-" + city_name + "-" + country_name);
                        }

                    }

                }
            }
        });


    }


    private void setUp() {

        titleBar.initTitleBarInfo("填写车辆信息", R.drawable.arrow_left, -1, "", "");

        etVehicleType.addTextChangedListener(textWatcher);
        etVehicleFrameNumber.addTextChangedListener(textWatcher);
        etLicensePlateNumber.addTextChangedListener(textWatcher);
        etIdNumber.addTextChangedListener(textWatcher);
        etEngineNumber.addTextChangedListener(textWatcher);
        etOwnerName.addTextChangedListener(textWatcher);


        tvConfirm.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
        tvConfirm.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));


    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (
                    TextUtils.isEmpty(etOwnerName.getText()) ||
                            TextUtils.isEmpty(etEngineNumber.getText()) ||
                            TextUtils.isEmpty(etIdNumber.getText()) ||
                            TextUtils.isEmpty(etLicensePlateNumber.getText()) ||
                            TextUtils.isEmpty(etVehicleFrameNumber.getText())||
                            TextUtils.isEmpty(etVehicleType.getText())
                    ) {
                isValidToNextActivity = false;
                tvConfirm.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                tvConfirm.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
            } else {
                isValidToNextActivity = true;
                tvConfirm.setBackgroundResource(R.drawable.btn_select_base);
                tvConfirm.setTextColor(getResources().getColor(R.color.white_color));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case MODIFY_CITY:
                if (resultCode == Activity.RESULT_OK) {
                    driverAreaNameString = data.getExtras().getString(ExtraConstants.EXTRA_CITY_NAME);
                    if (tvDriverArea != null) {
                        tvDriverArea.setText(driverAreaNameString);
                    }
                }
                break;

            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String engineNumber = etEngineNumber.getText().toString() + "";
        String vehicleFrameNumber = etVehicleFrameNumber.getText().toString() + "";
        String ownerName = etOwnerName.getText().toString() + "";
        String idNumber = etIdNumber.getText().toString() + "";
        String licensePlateNumber = etLicensePlateNumber.getText().toString() + "";
        PreferenceUtil.save(this, PreferenceConstant.engineNumber, engineNumber);
        PreferenceUtil.save(this, PreferenceConstant.frameNumber, vehicleFrameNumber);
        PreferenceUtil.save(this, PreferenceConstant.etOwnerName, ownerName);
        PreferenceUtil.save(this, PreferenceConstant.etIdNumber, idNumber);
        PreferenceUtil.save(this, PreferenceConstant.etLicensePlateNumber, licensePlateNumber);
        PreferenceUtil.save(this, PreferenceConstant.registrationDateLong, registrationDateLong);
        PreferenceUtil.save(this, PreferenceConstant.issueDateLong, issueDateLong);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String engineNumberString = PreferenceUtil.load(this, PreferenceConstant.engineNumber, "");
        String IdNumber = PreferenceUtil.load(this, PreferenceConstant.etIdNumber, "");
        String ownerName = PreferenceUtil.load(this, PreferenceConstant.etOwnerName, "");
        String licensePlateNumber = PreferenceUtil.load(this, PreferenceConstant.etLicensePlateNumber, "");
        String frameNumber = PreferenceUtil.load(this, PreferenceConstant.frameNumber, "");
        registrationDateLong = PreferenceUtil.load(this, PreferenceConstant.registrationDateLong, 0L);
        issueDateLong = PreferenceUtil.load(this, PreferenceConstant.issueDateLong, 0L);

        etIdNumber.setText(IdNumber);
        etOwnerName.setText(ownerName);
        etVehicleFrameNumber.setText(frameNumber);
        etLicensePlateNumber.setText(licensePlateNumber);
        etEngineNumber.setText(engineNumberString + "");


        etIdNumber.setSelection(IdNumber.length());
        etOwnerName.setSelection(ownerName.length());
        etVehicleFrameNumber.setSelection(frameNumber.length());
        etLicensePlateNumber.setSelection(licensePlateNumber.length());
        etEngineNumber.setSelection(engineNumberString.length());


        if (registrationDateLong > 0) {
            tvRegistrationDate.setText(dateFormat_yyyy_MM_dd.format(new Date(registrationDateLong)) + "");
        }
        if (issueDateLong > 0) {
            tvIssueDate.setText(dateFormat_yyyy_MM_dd.format(new Date(issueDateLong)) + "");
        }

    }
}
