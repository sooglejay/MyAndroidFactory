package com.jiandanbaoxian.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.CommData;
import com.jiandanbaoxian.model.InsuranceItemData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.RegionBean;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.SpannableStringUtil;


import net.sf.json.JSONArray;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RetrofitError;
import retrofit.client.Response;

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
    private SpannableString unitSpanString;


    private String carNumberString;
    private String carFaDongJiNumber;
    private String carJiaNumber;
    private String userNameString;

    private long commercialTimeLong;
    private long jqxTimeLong;
    private long fazhengTimeLong;
    private long signInTimeLong;

    private Activity activity;


    /**
     * 传递 参数
     *
     * @param carNumberString
     * @param carFaDongJiNumber
     * @param carJiaNumber
     * @param userNameString
     * @param commercialTimeLong
     * @param jqxTimeLong
     * @param fazhengTimeLong
     * @param signInTimeLong
     */
    public void setExtras(String carNumberString, String carFaDongJiNumber, String carJiaNumber
            , String userNameString, long commercialTimeLong, long jqxTimeLong, long fazhengTimeLong, long signInTimeLong) {
        this.carNumberString = carNumberString;
        this.carFaDongJiNumber = carFaDongJiNumber;
        this.carJiaNumber = carJiaNumber;
        this.userNameString = userNameString;

        this.commercialTimeLong = commercialTimeLong;
        this.jqxTimeLong = jqxTimeLong;
        this.fazhengTimeLong = fazhengTimeLong;
        this.signInTimeLong = signInTimeLong;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_price_detail_tab1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //人民币单位的字符
        unitSpanString = SpannableStringUtil.getSpannableString(getActivity(), "¥", 30);//单位


        activity = getActivity();
        setUp(view, savedInstanceState);
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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
