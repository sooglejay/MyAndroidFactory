package com.jiandanbaoxian.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;


public class ShareUtils {
    public static final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    public static final String QQ_APPID = "1104959839";
    public static final String QQ_APPKEY = "Z5bXtqD4ZYy3dxwK";
    public static final String WeChat_APPID = "wxfd96806031dd55eb";
    public static final String WeChat_APPSECRET = "2fdc0ad3055464f642c46b7ac37858a0";

    /**
     * 分享到QQ空间
     *
     * @param context
     * @param shareContent
     * @param url
     */
    public static void shareQZone(Context context, String shareContent, String url, String shareImage) {
        handlerQZone(context, shareContent, url, shareImage);
        postShare(context, SHARE_MEDIA.QZONE);
    }

    /**
     * 分享到朋友圈
     *
     * @param context
     * @param shareContent
     * @param url
     */
    public static void shareWeChat(Context context, String shareContent, String url, String shareImage) {
        handlerWeChat(context, shareContent, url, shareImage);
        postShare(context, SHARE_MEDIA.WEIXIN_CIRCLE);
    }

    /**
     * 自定义分享面板
     *
     * @param context
     */
    public static void postShare(final Context context, SHARE_MEDIA share_media) {
        mController.getConfig().closeToast();
        mController.getConfig().cleanListeners();
        mController.postShare(context, share_media,
                new SocializeListeners.SnsPostListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                        if (eCode == 200) {
/*                            Toast.makeText(context, context.getResources().getString(R.string.share_success), Toast.LENGTH_SHORT)
                                    .show();*/
                        } else {
                            String eMsg = "";
                            if (eCode == -101) {
                                eMsg = "没有授权";
                            }
//                            Toast.makeText(context, "分享失败[" + eCode + "] " + eMsg, Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 默认分享面板
     *
     * @param context
     */
    public static void openShare(final Context context, String shareContent, String url, String shareImage) {
        handlerQZone(context, shareContent, url, shareImage);
        handlerWeChat(context, shareContent, url, shareImage);
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        handlerSina(context, shareContent, url, shareImage);
        SocializeListeners.SnsPostListener mSnsPostListener = new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int stCode,
                                   SocializeEntity entity) {
                if (stCode == 200) {
/*                    Toast.makeText((Activity) context, context.getResources().getString(R.string.share_success), Toast.LENGTH_SHORT)
                            .show();*/
                } else {
//                    Toast.makeText((Activity) context,"分享失败 : error code : " + stCode, Toast.LENGTH_SHORT).show();
                    Toast.makeText((Activity) context, "分享失败", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        };
        mController.getConfig().cleanListeners();
        mController.registerListener(mSnsPostListener);
        mController.getConfig().closeToast();
        mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT, SHARE_MEDIA.QQ);
        mController.openShare((Activity) context, false);
    }

    /**
     * 设置QQ空间的分享内容
     */
    public static void handlerQZone(Context context, String shareContent, String url, String shareImage) {
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, QQ_APPID, QQ_APPKEY);
        qZoneSsoHandler.addToSocialSDK();
        QZoneShareContent qzone = new QZoneShareContent();
        //设置分享文字
        qzone.setShareContent(shareContent);
        //设置点击消息的跳转URL
        qzone.setTargetUrl(url);
        //设置分享内容的标题
        qzone.setTitle(context.getResources().getString(R.string.app_name));
        //设置分享图片
        if (shareImage != null) {
            qzone.setShareImage(new UMImage((Activity) context, shareImage));
        } else {
            qzone.setShareImage(new UMImage((Activity) context, R.drawable.ic_launcher));
        }
        mController.setShareMedia(qzone);
    }

    /**
     * 设置Q微信的分享内容
     */
    public static void handlerWeChat(Context context, String shareContent, String url, String shareImage) {
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler((Activity) context, WeChat_APPID, WeChat_APPSECRET);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        //设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(shareContent);
        //设置朋友圈title
        circleMedia.setTitle(shareContent);
        circleMedia.setShareImage(new UMImage((Activity) context, R.drawable.ic_launcher));
        if (shareImage != null) {
            circleMedia.setShareImage(new UMImage((Activity) context, shareImage));
        } else {
            circleMedia.setShareImage(new UMImage((Activity) context, R.drawable.ic_launcher));
        }
        circleMedia.setTargetUrl(url);
        mController.setShareMedia(circleMedia);
    }

    /**
     * 设置新浪微博的分享内容
     */
    public static void handlerSina(Context context, String shareContent, String url, String shareImage) {
        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent.setShareContent(shareContent + url);
        if (shareImage != null) {
            sinaContent.setShareImage(new UMImage((Activity) context, shareImage));
        } else {
            sinaContent.setShareImage(new UMImage((Activity) context, R.drawable.ic_launcher));
        }
        mController.setShareMedia(sinaContent);
    }
}
