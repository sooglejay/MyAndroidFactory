package com.jsb.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jsb.Bean.aaa_MyInsuranceBean;
import com.jsb.R;
import com.jsb.adapter.MyInsuresListAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.Driverordertable;
import com.jsb.model.MyInsuranceData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.Overtimeordertable;
import com.jsb.model.Vehicleordertable;
import com.jsb.util.PreferenceUtil;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的-我的保险
 */
public class MyInsureActivity extends BaseActivity {

    private TitleBar titleBar;
    private ListView mInsureList;
    private MyInsuresListAdapter myInsuresListAdapter;
    private List<aaa_MyInsuranceBean> mInsureListDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_insure);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyInsureActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("我的保险", R.drawable.arrow_left, -1, "", "");


        myInsuresListAdapter = new MyInsuresListAdapter(this, mInsureListDatas);
        mInsureList = (ListView) findViewById(R.id.list_view);
        mInsureList.setAdapter(myInsuresListAdapter);


        int userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        getMyinsuranceListInfo(userid);
    }

    /**
     * 获取我的保险的列表  （有三个代表）
     *
     * @param userid
     */
    private void getMyinsuranceListInfo(int userid) {
        UserRetrofitUtil.getMyinsuranceListInfo(this, 1, new NetCallback<NetWorkResultBean<MyInsuranceData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<MyInsuranceData> myInsuranceDataNetWorkResultBean, Response response) {
                mInsureListDatas.clear();

                List<Driverordertable> driverordertableList = myInsuranceDataNetWorkResultBean.getData().getDriverorderRecords();
                List<Overtimeordertable> overtimeordertableList = myInsuranceDataNetWorkResultBean.getData().getOvertimeorderRecords();
                List<Vehicleordertable> vehicleordertableList = myInsuranceDataNetWorkResultBean.getData().getVehicleorderRecords();


                if (vehicleordertableList != null && vehicleordertableList.size() > 0) {
                    aaa_MyInsuranceBean b1 = new aaa_MyInsuranceBean();
                    if (vehicleordertableList.get(0).getInsurancecompanyprices() != null) {
                        b1.setInsuranceCompanyName(vehicleordertableList.get(0).getInsurancecompanyprices().getCompany().getCompanyname());
                    } else {
                        b1.setInsuranceCompanyName("万保易");
                    }
                    b1.setInsuranceMoney(vehicleordertableList.get(0).getMoney());

                    if (vehicleordertableList.get(0).getInsuranceDetail() != null) {
                        b1.setInsuranceName(vehicleordertableList.get(0).getInsuranceDetail().getInsurancename());
                    } else {
                        b1.setInsuranceName("车险");
                    }

                    b1.setInsuranceBuyDate(vehicleordertableList.get(0).getOrderdate());
                    mInsureListDatas.add(b1);
                }


                if (overtimeordertableList.size() > 0) {
                    aaa_MyInsuranceBean b1 = new aaa_MyInsuranceBean();
                    b1.setInsuranceCompanyName("万保易");
                    b1.setInsuranceMoney(overtimeordertableList.get(0).getMoney());
                    b1.setInsuranceName("加班险");
                    b1.setInsuranceBuyDate(overtimeordertableList.get(0).getStartdate());
                    mInsureListDatas.add(b1);
                }


                if (driverordertableList.size() > 0) {
                    aaa_MyInsuranceBean b1 = new aaa_MyInsuranceBean();
                    b1.setInsuranceCompanyName(driverordertableList.get(0).getCompanyInfo().getCompanyname());
                    b1.setInsuranceMoney(driverordertableList.get(0).getMoney());
                    b1.setInsuranceName("驾驶险");
                    b1.setInsuranceBuyDate(driverordertableList.get(0).getBuydate());
                    mInsureListDatas.add(b1);
                }

                myInsuresListAdapter.notifyDataSetChanged();

            }
        });
    }
}
