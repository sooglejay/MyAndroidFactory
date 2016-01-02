package com.jiandanbaoxian.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.ConsultantData;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.util.ImageUtils;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;
import com.jiandanbaoxian.widget.jazzyviewpager.JazzyViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ServerConsultorFragment extends BaseFragment {
    private TextView tv_name;
    private TextView tv_company_address;
    private TextView tv_company_name;
    private TextView tv_phone_number;
    private ImageView iv_avatar;
    private ImageView iv_dot_0;
    private ImageView iv_dot_1;
    private ImageView iv_dot_2;
    private ImageView iv_dot_3;
    private Context context;
    private JazzyViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter = null;
    private TitleBar titleBar;
    private FrameLayout layout_viewpager;
    View layout_circle_dot;

    private List<ConsultFragmentPerPage> fragmentPerPages = new ArrayList<>();
    private ConsultantData consultantData;


    private Userstable myConsult;//用于显示的 我的顾问
    private int userid = -1;
    private Activity activity;


    //分页获取其他顾问（维修顾问）
    private int pageNum = 0;
    private int pageSize = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        userid = PreferenceUtil.load(activity, PreferenceConstant.userid, -1);
        setUp(view, savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //统计页面
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

    private void setUp(View view, Bundle savedInstanceState) {
        viewPager = (JazzyViewPager) view.findViewById(R.id.pager);
        layout_viewpager = (FrameLayout) view.findViewById(R.id.layout_viewpager);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_company_address = (TextView) view.findViewById(R.id.tv_company_address);
        tv_company_name = (TextView) view.findViewById(R.id.tv_company_name);
        tv_phone_number = (TextView) view.findViewById(R.id.tv_phone_number);
        layout_circle_dot = view.findViewById(R.id.layout_circle_dot);


        iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
        ImageLoader.getInstance().displayImage(StringConstant.Avatar_original.replace("XXX", userid + ""), iv_avatar, ImageUtils.getOptions());

        iv_dot_0 = (ImageView) view.findViewById(R.id.dot_0);
        iv_dot_0.setImageResource(R.drawable.dot_0);
        iv_dot_1 = (ImageView) view.findViewById(R.id.dot_1);
        iv_dot_2 = (ImageView) view.findViewById(R.id.dot_2);
        iv_dot_3 = (ImageView) view.findViewById(R.id.dot_3);
        viewPager.setOffscreenPageLimit(20);
        viewPager.setCurrentItem(1, true);
        viewPager.setPageMargin(-140);

        viewPagerAdapter = new ViewPagerAdapter(this.getActivity(), this.getActivity().getSupportFragmentManager(), viewPager);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.Tablet);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                pageNum = position;
//                if (fragmentPerPages.size() == pageNum)
//                {
//                    loadConsults();
//                }
                switch (position % 4) {
                    case 0:
                        iv_dot_0.setImageResource(R.drawable.dot_0);
                        iv_dot_1.setImageResource(R.drawable.dot_4);
                        iv_dot_2.setImageResource(R.drawable.dot_4);
                        iv_dot_3.setImageResource(R.drawable.dot_4);
                        break;
                    case 1:
                        iv_dot_0.setImageResource(R.drawable.dot_4);
                        iv_dot_1.setImageResource(R.drawable.dot_1);
                        iv_dot_2.setImageResource(R.drawable.dot_4);
                        iv_dot_3.setImageResource(R.drawable.dot_4);
                        break;
                    case 2:
                        iv_dot_0.setImageResource(R.drawable.dot_4);
                        iv_dot_1.setImageResource(R.drawable.dot_4);
                        iv_dot_2.setImageResource(R.drawable.dot_2);
                        iv_dot_3.setImageResource(R.drawable.dot_4);
                        break;
                    case 3:
                        iv_dot_0.setImageResource(R.drawable.dot_4);
                        iv_dot_1.setImageResource(R.drawable.dot_4);
                        iv_dot_2.setImageResource(R.drawable.dot_4);
                        iv_dot_3.setImageResource(R.drawable.dot_0);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        layout_viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });

        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("服务顾问", -1, -1, "", "");


        //点击我的顾问的电话号码，打电话
        view.findViewById(R.id.layout_server_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myConsult != null)
                    UIUtils.takePhoneCall(activity, myConsult.getPhone(), 100);
            }
        });

        getOtherConsultant();
        loadConsults();

    }

    private void loadConsults() {
        UserRetrofitUtil.getMyConsultant(activity, userid, new NetCallback<NetWorkResultBean<ConsultantData>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {

            }

            @Override
            public void success(NetWorkResultBean<ConsultantData> consultantDataNetWorkResultBean, Response response) {
                consultantData = consultantDataNetWorkResultBean.getData();
                List<Userstable> myConsultant = new ArrayList<Userstable>();

                myConsultant.addAll(consultantData.getMyConsultant());//
                if (myConsultant.size() > 0) {
                    myConsult = myConsultant.get(0);
                    tv_name.setText(TextUtils.isEmpty(myConsult.getName()) ? "" : myConsult.getName());
                    tv_company_address.setText(TextUtils.isEmpty(myConsult.getContactaddress()) ? "" : myConsult.getContactaddress());

                    if (myConsult.getCompany() != null) {
                        tv_company_name.setText(TextUtils.isEmpty(myConsult.getCompany().getCompanyname()) ? "" : myConsult.getCompany().getCompanyname());
                    } else {
                        tv_company_name.setText("null");
                    }
                    tv_phone_number.setText(TextUtils.isEmpty(myConsult.getPhone()) ? "" : myConsult.getPhone());

                }
            }
        });
    }

    private void getOtherConsultant() {
        UserRetrofitUtil.getOtherConsultant(activity, userid, 20, 1, new NetCallback<NetWorkResultBean<ConsultantData>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {

            }

            @Override
            public void success(NetWorkResultBean<ConsultantData> consultantDataNetWorkResultBean, Response response) {
                List<Userstable> otherConsultant = new ArrayList<Userstable>();
                for (int i = 0; i < otherConsultant.size(); i++) {
                    ConsultFragmentPerPage fragmentPerPage = new ConsultFragmentPerPage();
                    fragmentPerPage.setPosition(i);
                    fragmentPerPage.setUserstable(otherConsultant.get(i));
                    fragmentPerPages.add(fragmentPerPage);
                }
                if (otherConsultant.size() < 1) {
                    layout_circle_dot.setVisibility(View.GONE);
                } else {
                    layout_circle_dot.setVisibility(View.VISIBLE);
                }
                viewPagerAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 首页viewpager的adapter
     */
    private final class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(Context context, FragmentManager fm, JazzyViewPager mJazzy) {
            super(fm);
            this.mJazzy = mJazzy;
        }

        private JazzyViewPager mJazzy;

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Object obj = super.instantiateItem(container, position);
            mJazzy.setObjectForPosition(obj, position);
            return obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {
            container.removeView(mJazzy.findViewFromObject(position));
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentPerPages.get(position);
        }

        @Override
        public int getCount() {
            return fragmentPerPages.size();
        }
    }


}
