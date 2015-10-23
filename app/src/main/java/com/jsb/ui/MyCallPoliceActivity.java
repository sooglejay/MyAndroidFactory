package com.jsb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jsb.R;
import com.jsb.adapter.MyCallPoliceListAdapter;
import com.jsb.fragment.DialogFragmentCreater;
import com.jsb.Bean.aaa_MyCallPoliceBean;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的-我的报案
 */
public class MyCallPoliceActivity extends BaseActivity {

    public static final int REQUEST_CODE_CALL = 1000;
    private ListView mInsureList;
    private TitleBar titleBar;
    private MyCallPoliceListAdapter myCallPoliceListAdapter;
    private List<aaa_MyCallPoliceBean> mListDatas = new ArrayList<>();

    private DialogFragmentCreater dialogFragmentCreater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_call_police);
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


        dialogFragmentCreater = new DialogFragmentCreater(this,getSupportFragmentManager());

        aaa_MyCallPoliceBean bean1 = new aaa_MyCallPoliceBean();
        aaa_MyCallPoliceBean bean2 = new aaa_MyCallPoliceBean();
        aaa_MyCallPoliceBean bean3 = new aaa_MyCallPoliceBean();
        bean1.setInsureNameStr("车险");
        bean2.setInsureNameStr("驾驶险");
        bean3.setInsureNameStr("加班狗");
        bean1.setStatus(0);
        bean2.setStatus(0);
        bean3.setStatus(0);
        mListDatas.add(bean1);
        mListDatas.add(bean2);
        mListDatas.add(bean3);


        myCallPoliceListAdapter = new MyCallPoliceListAdapter(this, mListDatas,dialogFragmentCreater);
        mInsureList = (ListView)findViewById(R.id.list_view);
        mInsureList.setAdapter(myCallPoliceListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_CALL)
        {

            //重新生成一个 DialogFragment
          DialogFragmentCreater dialogFragmentCreater = new DialogFragmentCreater(this,getSupportFragmentManager());
            myCallPoliceListAdapter.setResultDialg(dialogFragmentCreater);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
