package com.jsb.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jsb.R;
import com.jsb.constant.PreferenceConstant;
import com.jsb.util.CityUtil;
import com.jsb.util.PreferenceUtil;
import com.jsb.widget.PopWindowUtils;
import com.jsb.widget.TitleBar;
import com.jsb.widget.citypicker.ChooseCityActivity;
import com.jsb.widget.wheel.WheelView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JammyQtheLab on 2015/11/7.
 */
public class CertificationActivity extends BaseActivity {

    private Activity context;
    /**
     * 选择省份 的滚动轮
     */
    private WheelView mProvinceWheelView;
    private TitleBar titleBar;

    private int userid = -1;

    private LinearLayout layout_choose_city;
    private List<String> provinceList = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();
    private CityUtil cityUtil;
    private PopWindowUtils popWindowUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        cityUtil = new CityUtil(this);
        popWindowUtils = new PopWindowUtils(this);
        context = this;
        provinceList = cityUtil.getProvinceList();
        cityList = cityUtil.getCityList(provinceList.get(0));
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                CertificationActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("实名认证", R.drawable.arrow_left, -1, "", "");
        layout_choose_city = (LinearLayout) findViewById(R.id.layout_choose_city);
        layout_choose_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CertificationActivity.this, ChooseCityActivity.class));
            }
        });
    }

}
