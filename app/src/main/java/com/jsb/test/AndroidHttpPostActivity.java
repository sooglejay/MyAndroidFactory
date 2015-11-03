package com.jsb.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.api.callback.NetCallback;
import com.jsb.api.user.UserRetrofitUtil;
import com.jsb.model.AccountData;
import com.jsb.model.ConsultantData;
import com.jsb.model.FreedomData;
import com.jsb.model.HistoryPriceData;
import com.jsb.model.InviteInfo;
import com.jsb.model.MyInsuranceData;
import com.jsb.model.MyWalletData;
import com.jsb.model.NetWorkResultBean;
import com.jsb.model.RangeData;
import com.jsb.model.ReportData;
import com.jsb.model.SelfRecord;
import com.jsb.model.TeamData;
import com.jsb.model.Userstable;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class AndroidHttpPostActivity extends Activity {

    Button bt_test;
    TextView result;

    String string;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_activity_test_http);
        result = (TextView) findViewById(R.id.result);
        bt_test = (Button) findViewById(R.id.bt_test);
        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyOvertimeInfo();
            }
        });
    }


    private void getOtherConsultant() {
        UserRetrofitUtil.getOtherConsultant(this, 1,1,10, new NetCallback<NetWorkResultBean<ConsultantData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {
            }

            @Override
            public void success(NetWorkResultBean<ConsultantData> stringNetWorkResultBean, Response response) {
            }
        });
    }

    private void getMyConsultant() {
        UserRetrofitUtil.getMyConsultant(this, 1,new NetCallback<NetWorkResultBean<ConsultantData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {
            }

            @Override
            public void success(NetWorkResultBean<ConsultantData> stringNetWorkResultBean, Response response) {
            }
        });
    }


    private void searchMember() {
        UserRetrofitUtil.searchMember(this, 1,"13678054215",new NetCallback<NetWorkResultBean<SelfRecord>>(this) {
            @Override
            public void onFailure(RetrofitError error) {
            }

            @Override
            public void success(NetWorkResultBean<SelfRecord> stringNetWorkResultBean, Response response) {
            }
        });
    }

    private void getMyTeamInfo() {
        UserRetrofitUtil.getMyTeamInfo(this, 1, new NetCallback<NetWorkResultBean<TeamData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {
            }

            @Override
            public void success(NetWorkResultBean<TeamData> stringNetWorkResultBean, Response response) {
            }
        });
    }

    private void auditJoinRequest() {
        UserRetrofitUtil.auditJoinRequest(this, 1, 2,0, new NetCallback<NetWorkResultBean<String>>(this) {
            @Override
            public void onFailure(RetrofitError error) {
            }
            @Override
            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {
            }
        });
    }


    private void getJoinRequest() {
        UserRetrofitUtil.getJoinRequest(this, 1,  new NetCallback<NetWorkResultBean<String>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

            }
        });
    }

    private void createTeam() {
        UserRetrofitUtil.createTeam(this, 1, "测试-团队名称", "2", new NetCallback<NetWorkResultBean<String>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

            }
        });
    }

    private void dealInviting() {
        UserRetrofitUtil.dealInviting(this, 1, 1, new NetCallback<NetWorkResultBean<String>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

            }
        });
    }

    private void verifyTeamName() {
        UserRetrofitUtil.verifyTeamName(this, "2", new NetCallback<NetWorkResultBean<String>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

            }
        });
    }

    private void addNewMember() {
        UserRetrofitUtil.addNewMember(this, 1, "2", new NetCallback<NetWorkResultBean<String>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

            }
        });
    }


    private void getAvailable() {
        UserRetrofitUtil.getAvailable(this, 1, 1, 10, new NetCallback<NetWorkResultBean<List<Userstable>>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<List<Userstable>> listNetWorkResultBean, Response response) {

            }
        });
    }


    private void getChoicers() {
        UserRetrofitUtil.getChoicers(this, 1, 1, 10, new NetCallback<NetWorkResultBean<List<Userstable>>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<List<Userstable>> listNetWorkResultBean, Response response) {

            }
        });
    }


    private void getInviteInfo() {
        UserRetrofitUtil.getInviteInfo(this, 1, new NetCallback<NetWorkResultBean<List<InviteInfo>>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<List<InviteInfo>> stringNetWorkResultBean, Response response) {

            }
        });
    }


    private void getTeamRangeInfo() {
        UserRetrofitUtil.getTeamRangeInfo(this, 1, new NetCallback<NetWorkResultBean<RangeData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<RangeData> stringNetWorkResultBean, Response response) {

            }
        });
    }


    private void fillInfoJoinTeam() {
        UserRetrofitUtil.fillInfoJoinTeam(this, 1, "测试员", "测试", "123", "测试", -1, -1, "测试", "-1", new NetCallback<NetWorkResultBean<String>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

            }
        });
    }

    private void submitJoinRequest() {
        UserRetrofitUtil.submitJoinRequest(this, 1, 1, new NetCallback<NetWorkResultBean<String>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<String> reportDataNetWorkResultBean, Response response) {

            }
        });
    }

    private void reportOvertime() {
        UserRetrofitUtil.reportOvertime(this, 1, 1, "蒋维测试", new NetCallback<NetWorkResultBean<String>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<String> reportDataNetWorkResultBean, Response response) {

            }
        });
    }


    private void searchTeam() {
        UserRetrofitUtil.searchTeam(this, "战斗团队", new NetCallback<NetWorkResultBean<List<TeamData>>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<List<TeamData>> listNetWorkResultBean, Response response) {

            }
        });
    }

    private void getReportableInsurance() {
        UserRetrofitUtil.getReportableInsurance(this, 1, new NetCallback<NetWorkResultBean<ReportData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<ReportData> reportDataNetWorkResultBean, Response response) {

            }
        });
    }

    private void getMywalletInfo() {
        UserRetrofitUtil.getMywalletInfo(this, 1, new NetCallback<NetWorkResultBean<MyWalletData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<MyWalletData> myWalletDataNetWorkResultBean, Response response) {

            }
        });
    }

    private void getVehicleOrderByPage() {
        UserRetrofitUtil.getVehicleOrderByPage(this, 1, 1, 10, new NetCallback<NetWorkResultBean<MyInsuranceData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<MyInsuranceData> myInsuranceDataNetWorkResultBean, Response response) {

            }
        });
    }

    private void deleteHistoryPrice() {
        UserRetrofitUtil.deleteHistoryPrice(this, 1, new NetCallback<NetWorkResultBean<String>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

            }
        });
    }

    private void getPriceHistoryList() {
        UserRetrofitUtil.getPriceHistoryList(this, 1, 1, 10, new NetCallback<NetWorkResultBean<HistoryPriceData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<HistoryPriceData> myInsuranceDataNetWorkResultBean, Response response) {

            }
        });
    }

    private void getHistoryPriceDetail() {
        UserRetrofitUtil.getHistoryPriceDetail(this, 1, new NetCallback<NetWorkResultBean<HistoryPriceData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<HistoryPriceData> myInsuranceDataNetWorkResultBean, Response response) {

            }
        });
    }

    private void getDriverOrderByPage() {
        UserRetrofitUtil.getDriverOrderByPage(this, 1, 1, 10, new NetCallback<NetWorkResultBean<MyInsuranceData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<MyInsuranceData> myInsuranceDataNetWorkResultBean, Response response) {

            }
        });
    }

    private void getOvertimeOrderByPage() {
        UserRetrofitUtil.getOvertimeOrderByPage(this, 1, 1, 10, new NetCallback<NetWorkResultBean<MyInsuranceData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<MyInsuranceData> myInsuranceDataNetWorkResultBean, Response response) {

            }
        });
    }

    private void saveWithdrawlInfo() {

        UserRetrofitUtil.saveWithdrawlInfo(this, 1, 1, 10, "jiangwei", "123456", "123@qq.com", "微信", 2, new NetCallback<NetWorkResultBean<String>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<String> stringNetWorkResultBean, Response response) {

            }
        });
    }

    private void getFourTeamInfo() {
        UserRetrofitUtil.getFourTeamInfo(this, new NetCallback<NetWorkResultBean<List<FreedomData>>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<List<FreedomData>> listNetWorkResultBean, Response response) {

            }
        });
    }

    private void getMyOvertimeInfo() {
        UserRetrofitUtil.getMyOvertimeInfo(this, 1, 1, 1000, new NetCallback<NetWorkResultBean<MyWalletData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<MyWalletData> myWalletDataNetWorkResultBean, Response response) {

            }
        });
    }


    private void getLastAccountInfo() {
        UserRetrofitUtil.getLastAccountInfo(this, 1, new NetCallback<NetWorkResultBean<AccountData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<AccountData> accountDataNetWorkResultBean, Response response) {

            }
        });
    }


    private void getMyinsuranceListInfo() {
        UserRetrofitUtil.getMyinsuranceListInfo(this, 1, new NetCallback<NetWorkResultBean<MyInsuranceData>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<MyInsuranceData> myInsuranceDataNetWorkResultBean, Response response) {

            }
        });
    }

    private void getSelfInfo() {
        UserRetrofitUtil.getSelfInfo(this, 1, new NetCallback<NetWorkResultBean<Userstable>>(this) {
            @Override
            public void onFailure(RetrofitError error) {

            }

            @Override
            public void success(NetWorkResultBean<Userstable> listNetWorkResultBean, Response response) {

            }
        });
    }
}