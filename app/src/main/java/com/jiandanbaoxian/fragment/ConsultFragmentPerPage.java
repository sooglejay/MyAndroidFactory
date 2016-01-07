package com.jiandanbaoxian.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.model.FourService;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.util.UIUtils;

/**
 * Created by JammyQtheLab on 2015/11/12.
 */
public class ConsultFragmentPerPage extends BaseFragment {

    private int pagePosition;
    private FourService userstable;
    private DialogFragmentCreater dialogFragmentCreater;//打电话时需要确认才能打
    private String phoneNumStr = "";
    private Activity activity ;


    public void setPosition(int postition) {
        pagePosition = postition;
    }
    public void updateBackground(int pagePosition)
    {
        if (layoutBackground!=null)
        {
            switch (pagePosition%4) {
                case 0:
                    layoutBackground.setBackgroundColor(Color.parseColor("#aa89bd"));
                    break;
                case 1:
                    layoutBackground.setBackgroundColor(Color.parseColor("#da8f8f"));
                    break;
                case 2:
                    layoutBackground.setBackgroundColor(Color.parseColor("#5fb1d0"));
                    break;
                case 3:
                    layoutBackground.setBackgroundColor(Color.parseColor("#aa89bd"));
                    break;
                default:
                    layoutBackground.setBackgroundColor(Color.parseColor("#aa89bd"));
                    break;

            }
        }
    }


    public void setUserstable(FourService userstable) {
        this.userstable = userstable;
    }
    private LinearLayout layoutBackground;
    private LinearLayout layout_server_call;
    private TextView tvUserName;
    private TextView tvShopName;
    private TextView tvShopAddress;
    private TextView tvPhoneNumber;
    private TextView tvServiceDescribe;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-01-05 21:47:44 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews(View view) {
        layoutBackground = (LinearLayout)view.findViewById(R.id.layout_background);
        layout_server_call = (LinearLayout)view.findViewById(R.id.layout_server_call);
        tvUserName = (TextView)view.findViewById(R.id.tv_user_name);
        tvShopName = (TextView)view.findViewById(R.id.tv_shop_name);
        tvShopAddress = (TextView)view.findViewById(R.id.tv_shop_address);
        tvPhoneNumber = (TextView)view.findViewById(R.id.tv_phone_number);
        tvServiceDescribe = (TextView)view.findViewById(R.id.tv_service_describe);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return   inflater.inflate(R.layout.fragment_consult_per_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        dialogFragmentCreater = new DialogFragmentCreater();
        dialogFragmentCreater.setDialogContext(this.getActivity(), this.getActivity().getSupportFragmentManager());

        findViews(view);
        setUpViews();
        setUpListener();
        switch (pagePosition % 4) {
            case 0:
                layoutBackground.setBackgroundColor(Color.parseColor("#aa89bd"));
                break;
            case 1:
                layoutBackground.setBackgroundColor(Color.parseColor("#da8f8f"));
                break;
            case 2:
                layoutBackground.setBackgroundColor(Color.parseColor("#5fb1d0"));
                break;
            case 3:
                layoutBackground.setBackgroundColor(Color.parseColor("#aa89bd"));
                break;
            default:
                layoutBackground.setBackgroundColor(Color.parseColor("#aa89bd"));
                break;

        }
    }

    private void setUpListener() {

        layout_server_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragmentCreater.setOnDialogClickLisenter(new DialogFragmentCreater.OnDialogClickLisenter() {
                    @Override
                    public void viewClick(String tag) {
                        if (tag.equals(StringConstant.tv_confirm)) {
                            UIUtils.takePhoneCall(activity, phoneNumStr, 1000);
                        }
                    }

                    @Override
                    public void controlView(View tv_confirm, View tv_cancel, View tv_title, View tv_content) {
                        if (tv_title instanceof TextView) {
                            ((TextView) tv_title).setText("提示");
                        }
                        if (tv_content instanceof TextView) {
                            ((TextView) tv_content).setText("您确定要打电话么？\n " + phoneNumStr);
                        }
                    }
                });
                dialogFragmentCreater.showDialog(getActivity(), DialogFragmentCreater.DialogShowConfirmOrCancelDialog);

            }
        });
    }


    private void setUpViews() {

        if(userstable!=null)
        {
            try {
                phoneNumStr = userstable.getPhone();
                tvPhoneNumber.setText(phoneNumStr);
                tvServiceDescribe.setText(userstable.getService());
                tvShopAddress.setText(userstable.getAddress());
                tvUserName.setText(userstable.getManagername());
                tvShopName.setText(userstable.getName());
            }catch (NullPointerException npe)
            {

            }
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&layoutBackground!=null)
        {
            switch (pagePosition%4) {
                case 0:
                    layoutBackground.setBackgroundColor(Color.parseColor("#aa89bd"));
                    break;
                case 1:
                    layoutBackground.setBackgroundColor(Color.parseColor("#da8f8f"));
                    break;
                case 2:
                    layoutBackground.setBackgroundColor(Color.parseColor("#5fb1d0"));
                    break;
                case 3:
                    layoutBackground.setBackgroundColor(Color.parseColor("#aa89bd"));
                    break;
                default:
                    layoutBackground.setBackgroundColor(Color.parseColor("#aa89bd"));
                    break;

            }
        }
    }


}
