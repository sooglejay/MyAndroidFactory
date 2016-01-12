package com.jiandanbaoxian.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.event.BusEvent;
import com.jiandanbaoxian.model.ConsultantData;
import com.jiandanbaoxian.model.FourService;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.ui.LoginActivity;
import com.jiandanbaoxian.ui.ModifyUserInfoActivity;
import com.jiandanbaoxian.util.ImageUtils;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;
import com.jiandanbaoxian.util.UIUtils;
import com.jiandanbaoxian.widget.TitleBar;
import com.jiandanbaoxian.widget.jazzyviewpager.JazzyViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
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
    private List<ImageView> dotViewList = new ArrayList<>();
    private LinearLayout layout_dot_list;
    private Context context;
    private JazzyViewPager viewPager;
    private TitleBar titleBar;
    private FrameLayout layout_viewpager;
    View layout_circle_dot;
    View layout_server_call;

    private ConsultantData consultantData;


    private Userstable myConsult;//用于显示的 我的顾问
    private int userid = -1;
    private int myConsultId = -1;
    private Activity activity;


    //分页获取其他顾问（维修顾问）
    private int pageNum = 0;
    private int pageSize = 20;


    private DialogFragmentCreater dialogFragmentCreater;//打电话时需要确认才能打


    View gif_call;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        userid = PreferenceUtil.load(activity, PreferenceConstant.userid, -1);
        findViews(view);
        setUpListener();
        dialogFragmentCreater = new DialogFragmentCreater();
        dialogFragmentCreater.setDialogContext(this.getActivity(), this.getActivity().getSupportFragmentManager());
        loadConsults();
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

    private void findViews(View view) {
        viewPager = (JazzyViewPager) view.findViewById(R.id.pager);
        layout_viewpager = (FrameLayout) view.findViewById(R.id.layout_viewpager);
        layout_dot_list = (LinearLayout) view.findViewById(R.id.layout_dot_list);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_company_address = (TextView) view.findViewById(R.id.tv_company_address);
        tv_company_name = (TextView) view.findViewById(R.id.tv_company_name);
        tv_phone_number = (TextView) view.findViewById(R.id.tv_phone_number);
        layout_circle_dot = view.findViewById(R.id.layout_circle_dot);
        layout_server_call = view.findViewById(R.id.layout_server_call);
        gif_call = view.findViewById(R.id.gif_call);

        iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
//        iv_dot_0 = (ImageView) view.findViewById(R.id.dot_0);
//        iv_dot_0.setImageResource(R.drawable.dot_0);
//        iv_dot_1 = (ImageView) view.findViewById(R.id.dot_1);
//        iv_dot_2 = (ImageView) view.findViewById(R.id.dot_2);
//        iv_dot_3 = (ImageView) view.findViewById(R.id.dot_3);
        titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo("服务顾问", -1, -1, "", "");


    }

    private void setUpListener() {

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMyConsult();

            }
        });

        //点击我的顾问的电话号码，打电话
        layout_server_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myConsult.getId() == userid) {
                    startActivity(new Intent(activity, ModifyUserInfoActivity.class));
                } else {
                    callMyConsult();
                }

            }
        });
    }


    private void setUp() {

        ImageLoader.getInstance().displayImage(StringConstant.Avatar_original.replace("XXX", myConsultId + ""), iv_avatar, ImageUtils.getOptions());
        viewPager.setOffscreenPageLimit(20);
        viewPager.setCurrentItem(1, true);
        viewPager.setPageMargin(-140);


        viewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.Tablet);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (dotViewList.size() < 1) {
                    return;
                }
                for (int i = 0; i < dotViewList.size(); i++) {
                    dotViewList.get(i).setImageResource(R.drawable.dot_4);
                }

                switch (position % 4) {
                    case 0:
                        dotViewList.get(position).setImageResource(R.drawable.dot_0);
                        break;
                    case 1:
                        dotViewList.get(position).setImageResource(R.drawable.dot_1);
                        break;
                    case 2:
                        dotViewList.get(position).setImageResource(R.drawable.dot_2);
                        break;
                    case 3:
                        dotViewList.get(position).setImageResource(R.drawable.dot_0);
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


        getOtherConsultant(true);


    }


    /**
     * 给我的顾问打电话
     */
    private void callMyConsult() {
        if (myConsult != null) { //弹出确认对话框
            dialogFragmentCreater.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                @Override
                public void viewClick(String tag) {
                    if (tag.equals(StringConstant.tv_confirm)) {
                        UIUtils.takePhoneCall(activity, myConsult.getPhone(), 1000);
                    }
                }

                @Override
                public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                    if (tv_title instanceof TextView) {
                        ((TextView) tv_title).setText("提示");
                    }
                    if (tv_content instanceof TextView) {
                        ((TextView) tv_content).setText("您确定要打电话么？\n " + myConsult.getPhone());
                    }
                }
            });
            dialogFragmentCreater.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);

        }
    }

    private void loadConsults() {
        UserRetrofitUtil.getMyConsultant(activity, userid, new NetCallback<NetWorkResultBean<ConsultantData>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                gif_call.setVisibility(View.GONE);
            }

            @Override
            public void success(NetWorkResultBean<ConsultantData> consultantDataNetWorkResultBean, Response response) {
                gif_call.setVisibility(View.VISIBLE);

                consultantData = consultantDataNetWorkResultBean.getData();
                List<Userstable> myConsultant = new ArrayList<Userstable>();

                myConsultant.addAll(consultantData.getMyConsultant());//
                if (myConsultant.size() > 0) {
                    myConsult = myConsultant.get(0);
                    myConsultId = myConsult.getId();
                    tv_name.setText(TextUtils.isEmpty(myConsult.getName()) ? "" : myConsult.getName());
                    tv_company_address.setText(TextUtils.isEmpty(myConsult.getContactaddress()) ? "" : myConsult.getContactaddress());

                    if (myConsult.getCompany() != null) {
                        tv_company_name.setText(TextUtils.isEmpty(myConsult.getCompany().getCompanyname()) ? "" : myConsult.getCompany().getCompanyname());
                    } else {
                        tv_company_name.setText("null");
                    }
                    tv_phone_number.setText(TextUtils.isEmpty(myConsult.getPhone()) ? "" : myConsult.getPhone());

                }
                setUp();


            }
        });
    }

    /***
     * 获取其他保险顾问，然后刷新ViewPager
     */
    private void getOtherConsultant(final boolean isDialog) {
        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(activity);
        if(isDialog) {
            progressDialogUtil.show("正在获取数据...");
        }
        UserRetrofitUtil.getOtherConsultant(activity, userid, 20, 1, new NetCallback<NetWorkResultBean<ConsultantData>>(activity) {
            @Override
            public void onFailure(RetrofitError error, String message) {
                    progressDialogUtil.hide();
            }

            @Override
            public void success(NetWorkResultBean<ConsultantData> consultantDataNetWorkResultBean, Response response) {
                viewPager.setAdapter(null);
                List<FourService> fourServiceList = consultantDataNetWorkResultBean.getData().getMaintainConsultant();
                List<ConsultFragmentPerPage> fragmentPerPages = new ArrayList<ConsultFragmentPerPage>();
//                //做冗余操作
//                if (fragmentPerPages != null && fragmentPerPages.size() > position) {
//                    fragmentPerPages.get(position).updateBackground(activity,position);
//                }
                fragmentPerPages.clear();
                layout_dot_list.removeAllViews();
                dotViewList.clear();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) UIUtils.dp2px(activity, 8), (int) UIUtils.dp2px(activity, 8), 1.0f);
                int uiMargin = (int) UIUtils.dp2px(activity, 4);
                layoutParams.setMargins(uiMargin, uiMargin, uiMargin, uiMargin);
                for (int i = 0; i < fourServiceList.size(); i++) {
                    ConsultFragmentPerPage fragmentPerPage = new ConsultFragmentPerPage();
                    fragmentPerPage.setPosition(i);
                    fragmentPerPage.setUserstable(fourServiceList.get(i));
                    fragmentPerPages.add(fragmentPerPage);
                    ImageView dot = new ImageView(activity);
                    dot.setTag("dot_" + i);
                    if (i == 0) {
                        dot.setImageResource(R.drawable.dot_0);
                    } else {
                        dot.setImageResource(R.drawable.dot_4);
                    }
                    dot.setLayoutParams(layoutParams);
                    dotViewList.add(dot);
                    layout_dot_list.addView(dot);

                }
                if (fourServiceList.size() < 1) {
                    layout_circle_dot.setVisibility(View.GONE);
                } else {
                    layout_circle_dot.setVisibility(View.VISIBLE);
                }
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(activity, getChildFragmentManager(), viewPager, fragmentPerPages);
                viewPager.setAdapter(viewPagerAdapter);
                progressDialogUtil.hide();

            }
        });
    }

    /**
     * 首页viewpager的adapter
     */
    private final class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<ConsultFragmentPerPage> pages = new ArrayList<>();

        public ViewPagerAdapter(Context context, FragmentManager fm, JazzyViewPager mJazzy, List<ConsultFragmentPerPage> pages) {
            super(fm);
            this.mJazzy = mJazzy;
            this.pages = pages;
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
        public ConsultFragmentPerPage getItem(int position) {
            return pages.get(position);
        }

        @Override
        public int getCount() {
            return pages.size();
        }

    }
    @Override
    public void onDestroyView() {
        if(viewPager!=null) {
            viewPager.destroyDrawingCache();
            viewPager.removeAllViews();
            viewPager = null;
        }
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            getOtherConsultant(false);
        }
    }
}
