package com.jiandanbaoxian.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.model.Userstable;
import com.jiandanbaoxian.util.ImageUtils;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.widget.RoundImageView;
import com.jiandanbaoxian.widget.imagepicker.MultiImageSelectorActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/11/11.
 */
public class ModifyUserInfoActivity extends BaseActivity {

    public static final int MODIFY_PHONE_NUMBER = 100;//修改手机号码
    public static final int MODIFY_EMPLOYEE_NUMBER = 101;//修改员工工号

    private FrameLayout layoutAvatar;
    private ImageView iv_avatar_background;
    private RoundImageView ivAvatar;
    private LinearLayout layoutModifyMobile;
    private TextView tvMobileNumber;
    private TextView tv_user_name;
    private LinearLayout layoutModifyCity;
    private TextView tvCityName;
    private LinearLayout layoutModifyCompanyName;
    private TextView tvCompanyName;
    private LinearLayout layoutModifyCompanyAddress;
    private TextView tvCompanyAddress;
    private TextView tv_service_describe;
    private TextView tv_employ_number;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-11 17:02:44 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        layoutAvatar = (FrameLayout) findViewById(R.id.layout_avatar);
        iv_avatar_background = (ImageView) findViewById(R.id.iv_avatar_background);
        ivAvatar = (RoundImageView) findViewById(R.id.iv_avatar);
        layoutModifyMobile = (LinearLayout) findViewById(R.id.layout_modify_mobile);
        tvMobileNumber = (TextView) findViewById(R.id.tv_mobile_number);
        tv_employ_number = (TextView) findViewById(R.id.tv_employ_number);
        layoutModifyCity = (LinearLayout) findViewById(R.id.layout_modify_city);
        tvCityName = (TextView) findViewById(R.id.tv_city_name);
        layoutModifyCompanyName = (LinearLayout) findViewById(R.id.layout_modify_company_name);
        tvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        layoutModifyCompanyAddress = (LinearLayout) findViewById(R.id.layout_modify_company_address);
        tvCompanyAddress = (TextView) findViewById(R.id.tv_company_address);
        tv_service_describe = (TextView) findViewById(R.id.tv_service_describe);
        //返回
        findViewById(R.id.layout_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        if (userstable != null) {
            tvMobileNumber.setText(!TextUtils.isEmpty(userstable.getPhone()) ? userstable.getPhone() : "");
            tv_user_name.setText(!TextUtils.isEmpty(userstable.getName()) ? "Hi," + userstable.getName() : "");
            tv_service_describe.setText(!TextUtils.isEmpty(userstable.getService()) ? userstable.getService() : "");
            if (userstable.getCompany() != null) {
                tvCompanyName.setText(userstable.getCompany().getCompanyname());
            } else {
                tvCompanyName.setText("未设置");
            }
            tvCompanyAddress.setText("服务端没有对应字段");
        }
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtils.startPickPhoto(activity, imageList, 1, false);
            }
        });
    }


    private ArrayList<String> imageList = new ArrayList<>();
    private String resultPath;//图片最终位置

    private int userid = -1;
    private Activity activity;
    private static final String ExtraKey = "ExtraKey";
    private Userstable userstable;

    public static void startActivity(Activity context, Userstable userstable) {
        Intent intent = new Intent(context, ModifyUserInfoActivity.class);
        intent.putExtra(ExtraKey, userstable);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_info);
        activity = this;
        userstable = getIntent().getExtras().getParcelable(ExtraKey);
        userid = PreferenceUtil.load(this, PreferenceConstant.userid, -1);
        findViews();
        setUpListeners();
    }

    private void setUpListeners() {

        findViewById(R.id.layout_modify_mobile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ModifyUserPhoneNumberActivity.class);
                activity.startActivityForResult(intent, MODIFY_PHONE_NUMBER);
            }
        });

        findViewById(R.id.layout_employee_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ModifyEmployeeNumberActivity.class);
                activity.startActivityForResult(intent, MODIFY_EMPLOYEE_NUMBER);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //若是从图库选择图
            case ImageUtils.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    // 获取返回的图片列表
                    List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    imageList.clear();
                    imageList.addAll(paths);
                    if (imageList.size() > 0) {
                        resultPath = ImageUtils.getImageFolderPath(this) + File.separator + System.currentTimeMillis() + ".jpg";
                        ImageUtils.cropImage(this, Uri.fromFile(new File(imageList.get(0))), resultPath, 1, 1);
                    }
                }
                break;

            //裁剪图片
            case ImageUtils.REQUEST_CODE_CROP_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    //添加图片到list并且显示出来
                    //上传图片
                    if (!TextUtils.isEmpty(resultPath)) {
                        ImageLoader.getInstance().displayImage("file://" + resultPath, ivAvatar, ImageUtils.getOptions());
                        ImageLoader.getInstance().displayImage("file://" + resultPath, iv_avatar_background, ImageUtils.getOptions());
                    }
                }
                break;
            case MODIFY_PHONE_NUMBER: //修改用户名
                if (resultCode == Activity.RESULT_OK) {
                    String newPhoneStr = data.getExtras().getString("MODIFY_PHONE_NUMBER");
                    tvMobileNumber.setText(newPhoneStr);
                }

            case MODIFY_EMPLOYEE_NUMBER: //修改员工号
                if (resultCode == Activity.RESULT_OK) {
                    String newEmploy = data.getExtras().getString("MODIFY_EMPLOYEE_NUMBER");
                    tv_employ_number.setText(newEmploy);
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
