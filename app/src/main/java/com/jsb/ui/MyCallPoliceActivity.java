package com.jsb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.MyCallPoliceListAdapter;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.constant.PreferenceConstant;
import com.jsb.fragment.DialogFragmentCreater;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.Overtimeordertable;
import com.jsb.model.ReportData;
import com.jsb.model.ReportableInsurance;
import com.jsb.model.Vehicleordertable;
import com.jsb.util.PreferenceUtil;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 我的-我的报案
 */
public class MyCallPoliceActivity extends BaseActivity {
    public static final int REQUEST_CODE_CALL = 1000;
    private ListView mInsureList;
    private TitleBar titleBar;
    private MyCallPoliceListAdapter myCallPoliceListAdapter;
    private List<Object> mListDatas = new ArrayList<>();
    private DialogFragmentCreater dialogFragmentCreater;

    private int userid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_call_police);
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyCallPoliceActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("我的报案", R.drawable.arrow_left, -1, "", "");

        dialogFragmentCreater = new DialogFragmentCreater();
        dialogFragmentCreater.setDialogContext(this, getSupportFragmentManager());

        myCallPoliceListAdapter = new MyCallPoliceListAdapter(this, mListDatas, dialogFragmentCreater);
        mInsureList = (ListView) findViewById(R.id.list_view);
        mInsureList.setAdapter(myCallPoliceListAdapter);

        getReportableInsurance(userid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CALL) {
            //重新生成一个 DialogFragment
            DialogFragmentCreater dialogFragmentCreater = new DialogFragmentCreater();
            dialogFragmentCreater.setDialogContext(this, getSupportFragmentManager());
            myCallPoliceListAdapter.setResultDialg(dialogFragmentCreater);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void getReportableInsurance(int userid) {
        UserRetrofitUtil.getReportableInsurance(this, userid, new NetCallback<NetWorkResultBean<ReportData>>(this) {
            @Override
            public void onFailure(RetrofitError error,String message) {
                if(!TextUtils.isEmpty(message)) {
                    Toast.makeText(MyCallPoliceActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void success(NetWorkResultBean<ReportData> reportDataNetWorkResultBean, Response response) {
                ReportData data = reportDataNetWorkResultBean.getData();
                if (data.getReportableInsurance() != null) {
                    ReportableInsurance reportableInsurance = data.getReportableInsurance();
                    if (reportableInsurance.getOvertimeReportableData() != null) {
                        mListDatas.addAll(data.getReportableInsurance().getOvertimeReportableData());
                    }
                    if (reportableInsurance.getDriverReportableData() != null) {
                        mListDatas.addAll(data.getReportableInsurance().getDriverReportableData());
                    }
                    if (reportableInsurance.getVehicleReportableData() != null) {
                        mListDatas.addAll(data.getReportableInsurance().getVehicleReportableData());
                    }
                    myCallPoliceListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
