package com.jiandanbaoxian.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jiandanbaoxian.R;

/**
 * Created by JammyQtheLab on 2015/11/12.
 */
public class ConsultFragmentPerPage extends BaseFragment {

    private LinearLayout layout_background;
    private int pagePosition;

    public void setPosition(int postition) {
        pagePosition = postition;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consult_per_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        layout_background = (LinearLayout) view.findViewById(R.id.layout_background);
        switch (pagePosition) {
            case 0:
                layout_background.setBackgroundColor(Color.parseColor("#aa89bd"));
                break;
            case 1:
                layout_background.setBackgroundColor(Color.parseColor("#da8f8f"));
                break;
            case 2:
                layout_background.setBackgroundColor(Color.parseColor("#5fb1d0"));
                break;
            case 3:
                layout_background.setBackgroundColor(Color.parseColor("#aa89bd"));
                break;
            default:
                break;
        }
    }
}
