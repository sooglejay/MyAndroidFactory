package com.jsb.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.adapter.MyHistorySaleAdapter;
import com.jsb.model.aaa_HistorySaleBean;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的-我的历史记录
 */
public class MyHistorySaleActivity extends BaseActivity {

    private TitleBar titleBar;
    private ListView list_view;
    private List<aaa_HistorySaleBean>mDatas = new ArrayList<>();
    private MyHistorySaleAdapter mAdapter;


    private TextView tv_delete;
    private TextView tv_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history_sale);
        setUp();
        setLisenter();
    }

    private void setLisenter() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                MyHistorySaleActivity.this.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyHistorySaleActivity.this,"金属大师",Toast.LENGTH_SHORT).show();
                mAdapter.deleteItem(true);
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteItem(false);
            }
        });
    }

    private void setUp() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);

        list_view = (ListView) findViewById(R.id.list_view);
        View view =findViewById(R.id.layout_operate);
        view.setVisibility(View.GONE);
        tv_delete = (TextView)findViewById(R.id.tv_delete);
        tv_cancel = (TextView)findViewById(R.id.tv_cancel);

        titleBar.initTitleBarInfo("历史报价", R.drawable.arrow_left, -1, "", "");

        int i=0;
        while (i<14)
        {
            aaa_HistorySaleBean bean = new aaa_HistorySaleBean();
            bean.setStatus(0);//默认不选中
            bean.setInsureNameStr("保险名称为："+i);
            mDatas.add(bean);
            i++;
        }
        mAdapter = new MyHistorySaleAdapter(this,mDatas, view,list_view);
        list_view.setAdapter(mAdapter);
    }

}
