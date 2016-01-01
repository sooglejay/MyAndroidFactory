package com.jiandanbaoxian.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.util.PreferenceUtil;

/**
 * 历史报价-点击item-具体的报价页面-tab1
 */
public class HistoryPriceDetailFragmentTab1 extends BaseFragment {

    private final static int ACTION_CALL = 1001;
    private LinearLayout layout_server_call;
    private Activity context;
    private CheckBox cb_agree_license;
    private TextView tv_i_want_to_rebuy_insurance;
    private boolean isAgreeWithLicence = true;

    private int userid = -1;

    private CheckBox cb_csx;//车损
    private CheckBox cb_szx;//三者
    private CheckBox cb_zwx;//座位
    private CheckBox cb_bjmpx;//不计免赔
    private CheckBox cb_jqx;//交强
    private CheckBox cb_ccx;//车船


    private TextView tv_csx_price;
    private TextView tv_szx_price;
    private TextView tv_zwx_price;
    private TextView tv_jmpx_price;
    private TextView tv_jqx_price;
    private TextView tv_cqx_price;
    private TextView tv_total_price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_price_detail_tab1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
        setUpListener();
    }

    private void setUp(View view, Bundle savedInstanceState) {
        context = HistoryPriceDetailFragmentTab1.this.getActivity();
        userid = PreferenceUtil.load(context, PreferenceConstant.userid, -1);

        tv_csx_price = (TextView) view.findViewById(R.id.tv_csx_price);
        tv_szx_price = (TextView) view.findViewById(R.id.tv_szx_price);
        tv_zwx_price = (TextView) view.findViewById(R.id.tv_zwx_price);
        tv_jmpx_price = (TextView) view.findViewById(R.id.tv_bjmpx_price);
        tv_jqx_price = (TextView) view.findViewById(R.id.tv_jqx_price);
        tv_cqx_price = (TextView) view.findViewById(R.id.tv_cqx_price);
        tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);

        cb_csx = (CheckBox) view.findViewById(R.id.cb_csx);
        cb_zwx = (CheckBox) view.findViewById(R.id.cb_zwx);
        cb_szx = (CheckBox) view.findViewById(R.id.cb_szx);
        cb_jqx = (CheckBox) view.findViewById(R.id.cb_jqx);
        cb_bjmpx = (CheckBox) view.findViewById(R.id.cb_bjmpx);
        cb_ccx = (CheckBox) view.findViewById(R.id.cb_ccx);

        cb_csx.setChecked(true);
        cb_zwx.setChecked(true);
        cb_szx.setChecked(true);
        cb_jqx.setChecked(true);
        cb_bjmpx.setChecked(true);
        cb_ccx.setChecked(true);

        cb_agree_license = (CheckBox) view.findViewById(R.id.cb_agree_license);
        cb_agree_license.setChecked(true);

        tv_i_want_to_rebuy_insurance = (TextView) view.findViewById(R.id.tv_i_want_to_rebuy_insurance);


    }


    private void setUpListener() {
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


        cb_bjmpx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_bjmpx.setButtonDrawable(R.drawable.icon_choose_selected);
                } else {
                    cb_bjmpx.setButtonDrawable(R.drawable.icon_choose);
                }
            }
        });

        cb_ccx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_ccx.setButtonDrawable(R.drawable.icon_choose_selected);
                } else {
                    cb_ccx.setButtonDrawable(R.drawable.icon_choose);
                }
            }
        });

        cb_csx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_csx.setButtonDrawable(R.drawable.icon_choose_selected);
                } else {
                    cb_csx.setButtonDrawable(R.drawable.icon_choose);
                }
            }
        });

        cb_jqx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_jqx.setButtonDrawable(R.drawable.icon_choose_selected);
                } else {
                    cb_jqx.setButtonDrawable(R.drawable.icon_choose);
                }
            }
        });

        cb_szx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_szx.setButtonDrawable(R.drawable.icon_choose_selected);
                } else {
                    cb_szx.setButtonDrawable(R.drawable.icon_choose);
                }
            }
        });

        cb_zwx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_zwx.setButtonDrawable(R.drawable.icon_choose_selected);
                } else {
                    cb_zwx.setButtonDrawable(R.drawable.icon_choose);
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
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
