package com.jsb.widget.citypicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jsb.R;
import com.jsb.util.CityUtil;
import com.jsb.widget.citypicker.view.CharacterParser;
import com.jsb.widget.citypicker.view.CitySortModel;
import com.jsb.widget.citypicker.view.PinyinComparator;
import com.jsb.widget.citypicker.view.SideBar;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChooseCityActivity extends AppCompatActivity {

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private CharacterParser characterParser;
    private List<CitySortModel> SourceDateList;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("选择城市");

        initViews();
    }

    private void initViews() {
        characterParser = CharacterParser.getInstance();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position + 1);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position>0) {
                    Toast.makeText(getApplication(),
                            ((CitySortModel) adapter.getItem(position - 1)).getName(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        CityUtil cityUtil = new CityUtil(this);
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
        tv_city_name.setText("北京");
        tv_city_name.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_location), null, null, null);
        tv_city_name.setCompoundDrawablePadding(24);
        return headView;
    }

    private List<CitySortModel> filledData(List<String>data) {
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
