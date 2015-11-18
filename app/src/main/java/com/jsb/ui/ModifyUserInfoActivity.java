package com.jsb.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsb.R;
import com.jsb.constant.ExtraConstants;
import com.jsb.constant.PreferenceConstant;
import com.jsb.model.Userstable;
import com.jsb.util.ImageUtils;
import com.jsb.util.PreferenceUtil;
import com.jsb.widget.RoundImageView;
import com.jsb.widget.imagepicker.MultiImageSelectorActivity;
import com.jsb.widget.imagepicker.bean.Image;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.update.UmengUpdateAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JammyQtheLab on 2015/11/11.
 */
public class ModifyUserInfoActivity extends BaseActivity {
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

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2015-11-11 17:02:44 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        layoutAvatar = (FrameLayout)findViewById( R.id.layout_avatar );
        iv_avatar_background = (ImageView)findViewById( R.id.iv_avatar_background );
        ivAvatar = (RoundImageView)findViewById( R.id.iv_avatar );
        layoutModifyMobile = (LinearLayout)findViewById( R.id.layout_modify_mobile );
        tvMobileNumber = (TextView)findViewById( R.id.tv_mobile_number );
        layoutModifyCity = (LinearLayout)findViewById( R.id.layout_modify_city );
        tvCityName = (TextView)findViewById( R.id.tv_city_name );
        layoutModifyCompanyName = (LinearLayout)findViewById( R.id.layout_modify_company_name );
        tvCompanyName = (TextView)findViewById( R.id.tv_company_name );
        tv_user_name = (TextView)findViewById( R.id.tv_user_name );
        layoutModifyCompanyAddress = (LinearLayout)findViewById( R.id.layout_modify_company_address );
        tvCompanyAddress = (TextView)findViewById( R.id.tv_company_address );
        tv_service_describe = (TextView)findViewById( R.id.tv_service_describe );
        //返回
        findViewById(R.id.layout_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        if(userstable!=null)
        {
            tvMobileNumber.setText(!TextUtils.isEmpty(userstable.getPhone())?userstable.getPhone():"");
            tv_user_name.setText(!TextUtils.isEmpty(userstable.getName())?"Hi,"+userstable.getName():"");
            tv_service_describe.setText(!TextUtils.isEmpty(userstable.getService())?userstable.getService():"");
            if(userstable.getCompany()!=null)
            {
                tvCompanyName.setText(userstable.getCompany().getCompanyname());
            }else {
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
    private String resultPath ;//图片最终位置

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
                        ImageLoader.getInstance().displayImage("file://"+resultPath,ivAvatar,ImageUtils.getOptions());
                        ImageLoader.getInstance().displayImage("file://"+resultPath,iv_avatar_background,ImageUtils.getOptions());
                    }
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
