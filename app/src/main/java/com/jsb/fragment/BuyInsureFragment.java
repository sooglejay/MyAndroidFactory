package com.jsb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsb.constant.StringConstant;
import com.jsb.R;
import com.jsb.model.Banner;
import com.jsb.ui.BrowserActivity;
import com.jsb.widget.BannerView;
import com.jsb.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class BuyInsureFragment extends BaseFragment {
    private BannerView bannerView;
    private List<Banner> bannerDatas = new ArrayList<>();

    private TitleBar titleBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setUp(view, savedInstanceState);
    }

    private void setUp(View view, Bundle savedInstanceState) {
        titleBar = (TitleBar)view.findViewById(R.id.title_bar);
        titleBar.initTitleBarInfo(StringConstant.buyInsure,-1,-1,"","");



        //广告
        Banner b1 = new Banner();
        Banner b2 = new Banner();
        Banner b3 = new Banner();
        Banner b4 = new Banner();
        b1.setUrl("http://a.hiphotos.baidu.com/image/h%3D300/sign=e5e212bd972397ddc9799e046983b216/0823dd54564e925851d3adb49a82d158cdbf4e80.jpg");
        b2.setUrl("http://g.hiphotos.baidu.com/image/h%3D360/sign=914c2a32bd99a90124355d302d940a58/2934349b033b5bb54796d6d833d3d539b600bc57.jpg");
        b3.setUrl("http://h.hiphotos.baidu.com/image/h%3D360/sign=f355a93303e9390149028b384bed54f9/a9d3fd1f4134970abfc2013c97cad1c8a7865d6b.jpg");
        b4.setUrl("http://h.hiphotos.baidu.com/image/h%3D360/sign=02e4abf39058d109dbe3afb4e159ccd0/b7fd5266d016092474a86a96d60735fae7cd34aa.jpg");

        b1.setFile_location("http://img3.imgtn.bdimg.com/it/u=4195379407,1561282992&fm=21&gp=0.jpg");
        b2.setFile_location("http://img3.imgtn.bdimg.com/it/u=2863599132,929299683&fm=21&gp=0.jpg");
        b3.setFile_location("http://img4.imgtn.bdimg.com/it/u=1704061436,275613074&fm=21&gp=0.jpg");
        b4.setFile_location("http://img5.imgtn.bdimg.com/it/u=4074420674,1381824193&fm=21&gp=0.jpg");

        bannerDatas.add(b1);
        bannerDatas.add(b2);
        bannerDatas.add(b3);
        bannerDatas.add(b4);
        //init banner view
        bannerView = (BannerView) view.findViewById(R.id.banner_view);
        bannerView.initBannerAdapter(getActivity(), bannerDatas, new BannerView.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                BrowserActivity.startActivity(getActivity(), bannerDatas.get(position).getUrl());
            }
        });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

}
