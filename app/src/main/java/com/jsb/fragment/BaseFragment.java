package com.jsb.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.greenrobot.event.EventBus;

/**
 * fragment 基类
 */
public class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        // setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onEvent(Object object){
    }
}
