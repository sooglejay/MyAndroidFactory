package com.jsb.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.constant.StringConstant;
import com.jsb.model.MyInsuranceData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.Vehicleordertable;
import com.jsb.model.Vehicletable;
import com.jsb.ui.MyInsuresListCarInsureDetailActivity;
import com.jsb.util.PreferenceUtil;
import com.jsb.util.TimeUtil;
import com.jsb.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的保险-车险-tab1
 */
public class CarInsureDetailContentFragmentTab1 extends BaseFragment {

    private final static int ACTION_CALL = 1001;
    private LinearLayout layout_server_call;
    private Activity context;
    private CheckBox cb_agree_license;
    private TextView tv_i_want_to_rebuy_insurance;
    private boolean isAgreeWithLicence = true;

    private int userid = -1;
    private int pageNum,pageSize;//分页加载

    private List<Vehicleordertable> mDatas = new ArrayList<>();
    private Vehicleordertable vehicleordertable;


    private TextView tv_insurance_agent_name;//
    private TextView tv_insurant_name;
    private TextView tv_insurance_number;
    private TextView tv_insurance_duration;
    private TextView tv_car_fdjh;
    private TextView tv_car_cjhm;
    private TextView tv_car_cphm;

    private GifImageView gifImageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_car_insure_tab1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }


    private void setUp(View view, Bundle savedInstanceState) {
        context =CarInsureDetailContentFragmentTab1.this.getActivity();
        userid = PreferenceUtil.load(context, PreferenceConstant.userid, -1);

        gifImageView = (GifImageView)view.findViewById(R.id.gif_view);
        UIUtils.setGifView(this.getActivity(), gifImageView,R.drawable.anim_flag_iceland);

        tv_car_cphm = (TextView)view.findViewById(R.id.tv_car_cphm);
        tv_car_cjhm = (TextView)view.findViewById(R.id.tv_car_cjhm);
        tv_car_fdjh = (TextView)view.findViewById(R.id.tv_car_fdjh);
        tv_insurance_duration = (TextView)view.findViewById(R.id.tv_insurance_duration);
        tv_insurance_number = (TextView)view.findViewById(R.id.tv_insurance_number);
        tv_insurant_name = (TextView)view.findViewById(R.id.tv_insurant_name);
        tv_insurance_agent_name = (TextView)view.findViewById(R.id.tv_insurance_agent_name);


        layout_server_call = (LinearLayout)view.findViewById(R.id.layout_server_call);
        layout_server_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.takePhoneCall(context, StringConstant.call_police_number, ACTION_CALL);
            }
        });

        cb_agree_license = (CheckBox)view.findViewById(R.id.cb_agree_license);
        cb_agree_license.setChecked(true);


        tv_i_want_to_rebuy_insurance = (TextView)view.findViewById(R.id.tv_i_want_to_rebuy_insurance);
        tv_i_want_to_rebuy_insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAgreeWithLicence) {
                    Toast.makeText(context, "程序员正在火速敲击键盘...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "请您勾选同意保障条款！", Toast.LENGTH_SHORT).show();
                }
            }
        });


        cb_agree_license.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isAgreeWithLicence = true;
                    cb_agree_license.setButtonDrawable(R.drawable.icon_choose_selected);
                    tv_i_want_to_rebuy_insurance.setBackgroundResource(R.drawable.btn_select_base_shape_0);
                    tv_i_want_to_rebuy_insurance.setTextColor(getResources().getColor(R.color.white_color));
                } else {
                    cb_agree_license.setButtonDrawable(R.drawable.icon_choose);
                    isAgreeWithLicence = false;
                    tv_i_want_to_rebuy_insurance.setBackgroundColor(getResources().getColor(R.color.bg_gray_color_level_0));
                    tv_i_want_to_rebuy_insurance.setTextColor(getResources().getColor(R.color.tv_gray_color_level_3));
                }
            }
        });
        vehicleordertable = this.getActivity().getIntent().getParcelableExtra(MyInsuresListCarInsureDetailActivity.ExtrasKeyName);
        if(vehicleordertable!=null)
        {
            Vehicletable vehicletable = vehicleordertable.getVehicle();
            if(vehicletable!=null) {
                tv_car_cphm.setText(TextUtils.isEmpty(vehicletable.getLicenseplate()) ? "" : vehicletable.getLicenseplate());
                tv_car_cjhm.setText(TextUtils.isEmpty(vehicletable.getFramenumber()) ? "" : vehicletable.getFramenumber());
                tv_car_fdjh.setText(TextUtils.isEmpty(vehicletable.getEnginenumber()) ? "" : vehicletable.getEnginenumber());
                tv_car_fdjh.setText(TextUtils.isEmpty(vehicletable.getOwnerName()) ? "" : vehicletable.getOwnerName());
                tv_insurance_number.setText(vehicletable.getId() == null ? "" : vehicletable.getId() + "");
            }
            if(vehicleordertable.getInsurancecompanyprices()!=null&&vehicleordertable.getInsurancecompanyprices().getCompany()!=null)
            {
                tv_insurance_agent_name.setText(vehicleordertable.getInsurancecompanyprices().getCompany().getCompanyname()+"");
            }else {
                tv_insurance_agent_name.setText("null");
            }
            tv_insurance_duration.setText(TimeUtil.getTimeString(vehicleordertable.getStartdate())+"起，"+TimeUtil.getTimeString(vehicleordertable.getEnddate())+"止");

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void getVehicleOrderByPage(int userid)
    {
        UserRetrofitUtil.getVehicleOrderByPage(context, userid, pageSize, pageNum, new NetCallback<NetWorkResultBean<MyInsuranceData>>(context) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<MyInsuranceData> myInsuranceDataNetWorkResultBean, Response response) {
                MyInsuranceData myInsuranceData = myInsuranceDataNetWorkResultBean.getData();

                List<Vehicleordertable>vehicleordertables = myInsuranceData.getVehicleorderRecords();
                if(vehicleordertables!=null&&vehicleordertables.size()>0)
                {
                    mDatas.addAll(vehicleordertables);
                }



            }
        });
    }

}
