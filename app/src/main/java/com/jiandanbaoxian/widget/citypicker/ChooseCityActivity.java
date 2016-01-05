package com.jiandanbaoxian.widget.citypicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.ExtraConstants;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.util.CityUtil;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.widget.TitleBar;
import com.jiandanbaoxian.widget.citypicker.view.CharacterParser;
import com.jiandanbaoxian.widget.citypicker.view.CitySortModel;
import com.jiandanbaoxian.widget.citypicker.view.PinyinComparator;
import com.jiandanbaoxian.widget.citypicker.view.SideBar;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChooseCityActivity extends AppCompatActivity {


    private boolean isEdit = false;
    private TitleBar title_bar;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private CharacterParser characterParser;
    private List<CitySortModel> SourceDateList;
    private CityUtil cityUtil;

    private boolean isChooseProvince = true;//是否是选择省份

    private Activity context;

    private String provinceStr;//省份的字符串
    private String cityStr;//城市的字符串

    private String cityNameStrFromIntent;//传递进来的城市字符串，直接给自动定位那里显示即可

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        cityUtil = new CityUtil(this);
        context = this;
        cityNameStrFromIntent = getIntent().getExtras().getString(ExtraConstants.EXTRA_CITY_NAME, "北京");
        isEdit = getIntent().getExtras().getBoolean("is_edit", false);
        initViews();
        setUpListener();
    }

    private void setUpListener() {

        title_bar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                context.finish();
            }

            @Override
            public void onRightButtonClick(View v) {

            }
        });


        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position + 1);
                }

            }
        });


        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //说明是自动定位
                    //返回上一个activity ，带着这个信号
                    context.setResult(RESULT_CANCELED);
                    context.finish();
                } else if (position > 0) {
                    if (isChooseProvince) {
                        isChooseProvince = false;
                        provinceStr = ((CitySortModel) adapter.getItem(position - 1)).getName();
                        SourceDateList.clear();
                        SourceDateList.addAll(filledData(cityUtil.getCityList(provinceStr)));
                        Collections.sort(SourceDateList, new PinyinComparator());
                        adapter.notifyDataSetChanged();
                    } else {
                        cityStr = ((CitySortModel) adapter.getItem(position - 1)).getName();
                        //返回上一个 activity ，带着 选中的值
                        if (isEdit) {
                            int userid = PreferenceUtil.load(context, PreferenceConstant.userid, -1);
                            if (!cityStr.contains(provinceStr)) {
                                cityStr = provinceStr + cityStr;
                            }
                            UserRetrofitUtil.modifySelfInfo(context, userid, -1, -1, "-1", cityStr, "-1", "-1", "-1",null, new NetCallback<NetWorkResultBean<Userstable>>(context) {
                                @Override
                                public void onFailure(RetrofitError error, String message) {
                                    Toast.makeText(context, "服务器无响应，请检查网络！", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void success(NetWorkResultBean<Userstable> userstableNetWorkResultBean, Response response) {
                                    Toast.makeText(context, "修改城市成功！", Toast.LENGTH_SHORT).show();
                                    Intent intent = getIntent();
                                    intent.putExtra(ExtraConstants.EXTRA_CITY_NAME, cityStr);
                                    context.setResult(RESULT_OK, intent);
                                    context.finish();
                                }
                            });
                        } else {
                            Intent intent = getIntent();
                            intent.putExtra(ExtraConstants.EXTRA_CITY_NAME, cityStr);
                            intent.putExtra(ExtraConstants.EXTRA_PROVINCE_NAME, provinceStr);
                            context.setResult(RESULT_OK, intent);
                            context.finish();
                        }

                    }

                }
            }
        });

    }

    private void initViews() {
        characterParser = CharacterParser.getInstance();
        title_bar = (TitleBar) findViewById(R.id.title_bar);
        title_bar.initTitleBarInfo("选择城市", R.drawable.arrow_left, -1, "", "");
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        sideBar.setTextView(dialog);
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        SourceDateList = filledData(cityUtil.getProvinceList());
        Collections.sort(SourceDateList, new PinyinComparator());
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.addHeaderView(initHeadView());
        sortListView.setAdapter(adapter);
    }


    private View initHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.header_item_select_city, null);
        TextView tv_catagory = (TextView) headView.findViewById(R.id.tv_catagory);
        TextView tv_city_name = (TextView) headView.findViewById(R.id.tv_city_name);
        tv_catagory.setText("自动定位");
        tv_city_name.setText(cityNameStrFromIntent);
        tv_city_name.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_location), null, null, null);
        tv_city_name.setCompoundDrawablePadding(24);
        return headView;
    }

    private List<CitySortModel> filledData(List<String> data) {
        List<CitySortModel> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            CitySortModel sortModel = new CitySortModel();
            sortModel.setName(data.get(i));
            String pinyin = characterParser.getSelling(data.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {

                //对重庆多音字做特殊处理
                if (pinyin.startsWith("zhongqing")) {
                    sortString = "C";
                    sortModel.setSortLetters("C");
                } else {
                    sortModel.setSortLetters(sortString.toUpperCase());
                }

                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }

            mSortList.add(sortModel);
        }
        Collections.sort(indexString);
        sideBar.setIndexText(indexString);
        return mSortList;

    }

}
