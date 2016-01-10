package com.jiandanbaoxian.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.constant.IntConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.umeng.socialize.bean.MultiStatus;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMEvernoteHandler;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.controller.media.EvernoteShareContent;
import com.umeng.socialize.facebook.controller.UMFacebookHandler;
import com.umeng.socialize.facebook.media.FaceBookShareContent;
import com.umeng.socialize.flickr.controller.UMFlickrHandler;
import com.umeng.socialize.flickr.media.FlickrShareContent;
import com.umeng.socialize.instagram.controller.UMInstagramHandler;
import com.umeng.socialize.instagram.media.InstagramShareContent;
import com.umeng.socialize.kakao.controller.UMKakaoHandler;
import com.umeng.socialize.kakao.media.KakaoShareContent;
import com.umeng.socialize.laiwang.controller.UMLWHandler;
import com.umeng.socialize.laiwang.media.LWDynamicShareContent;
import com.umeng.socialize.laiwang.media.LWShareContent;
import com.umeng.socialize.line.controller.UMLineHandler;
import com.umeng.socialize.line.media.LineShareContent;
import com.umeng.socialize.linkedin.controller.UMLinkedInHandler;
import com.umeng.socialize.linkedin.media.LinkedInShareContent;
import com.umeng.socialize.media.GooglePlusShareContent;
import com.umeng.socialize.media.MailShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.RenrenShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.TwitterShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.pinterest.controller.UMPinterestHandler;
import com.umeng.socialize.pinterest.media.PinterestShareContent;
import com.umeng.socialize.pocket.controller.UMPocketHandler;
import com.umeng.socialize.pocket.media.PocketShareContent;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.tumblr.controller.UMTumblrHandler;
import com.umeng.socialize.tumblr.media.TumblrShareContent;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.umeng.socialize.whatsapp.controller.UMWhatsAppHandler;
import com.umeng.socialize.whatsapp.media.WhatsAppShareContent;
import com.umeng.socialize.yixin.controller.UMYXHandler;
import com.umeng.socialize.ynote.controller.UMYNoteHandler;
import com.umeng.socialize.ynote.media.YNoteShareContent;

/**
 * @功能描述 : 分享页面Fragment,包括分享、图文分享、快速分享等
 * @原 作 者 :
 * @版 本 号 : [版本号, Aug 8, 2013]
 * @修 改 人 : mrsimple
 * @修改内容 :
 */

/**
 *
 */
public class ShareUtils {

    /***
     AppID：wxfd96806031dd55eb
     AppSecret：2fdc0ad3055464f642c46b7ac37858a0重置
     */
    public static final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private final static String AppID_WX="wxfd96806031dd55eb";
    private final static String AppSecret_WX="2fdc0ad3055464f642c46b7ac37858a0";

    private final static String AppID_QQ="1104959839";
    private final static String AppSecret_QQ="Z5bXtqD4ZYy3dxwK";

    private SHARE_MEDIA mPlatform = SHARE_MEDIA.SINA;
    private Activity activity;

    private String imageFileStr;

    Bitmap imageBitmap;

    public ShareUtils(final Activity activity, String imageFileStr) {
        this.activity = activity;
        this.imageFileStr = imageFileStr;
        if(TextUtils.isEmpty(imageFileStr))
        {
            imageBitmap = BitmapFactory.decodeResource(activity.getResources(),R.drawable.test);
            // 配置需要分享的相关平台
            configPlatforms();
            // 设置分享的内容
            setShareContent();
            initViewListener();
        }
        else {
            new AsyncTask<String, Bitmap, Bitmap>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                }
                @Override
                protected Bitmap doInBackground(String... params) {
                    Bitmap tempBitmap = ImageUtils.getBitmapFromLocalPath(params[0], 1);
                    Bitmap bitmap = ImageUtils.getResizedBitmap(tempBitmap,500,500);
                    return bitmap;
                }

                @Override
                protected void onPostExecute(final Bitmap bitmap) {
                    imageBitmap = bitmap;
                    if(imageBitmap==null)
                    {
                        imageBitmap = BitmapFactory.decodeResource(activity.getResources(),R.drawable.test);
                    }
                    // 配置需要分享的相关平台
                    configPlatforms();
                    // 设置分享的内容
                    setShareContent();
                    initViewListener();

                }
            }.execute(imageFileStr);
        }

    }


    /**
     * 配置分享平台参数</br>
     */
    private void configPlatforms() {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 添加腾讯微博SSO授权
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        // 添加人人网SSO授权
        RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(activity,
                "201874", "28401c0964f04a72a14c812d6132fcef",
                "3bf66e42db1e4fa9829b955cc300b737");
        mController.getConfig().setSsoHandler(renrenSsoHandler);

        // 添加QQ、QZone平台
        addQQQZonePlatform();

        // 添加微信、微信朋友圈平台
        addWXPlatform();
    }

    /**
     * @功能描述 : 初始化视图控件，比如Button
     */
    private void initViewListener() {
        mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT,
                SHARE_MEDIA.DOUBAN,
                SHARE_MEDIA.RENREN);
        mController.openShare(activity, false);

//        // 分享(先选择平台)
//        rootView.findViewById(R.id.open_share).setOnClickListener(this);
//        // 呼出分享编辑页
//        rootView.findViewById(R.id.post_share).setOnClickListener(this);
//        // 底层分享
//        rootView.findViewById(R.id.direct_share).setOnClickListener(this);
//        // 一键分享到所有已经授权的平台
//        rootView.findViewById(R.id.share_multi).setOnClickListener(this);
//        // 添加支持的所有平台
//        rootView.findViewById(R.id.share_all_platform).setOnClickListener(this);
    }

//    /**
//     * 点击事件处理。</br> 在不考虑是否授权的情况下区别openShare、postShare、directShare三个方法 <li>
//     * openShare : 分享面板 -> 分享编辑页 -> 分享 <li>postShare : 分享编辑页 -> 分享 <li>
//     * directShare : 分享
//     *
//     * @param v
//     */
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.open_share) {
//            mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
//                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT,
//                    SHARE_MEDIA.DOUBAN,
//                    SHARE_MEDIA.RENREN);
//            mController.openShare(activity, false);
//        } else if (id == R.id.post_share) {
//            postShare();
//        } else if (id == R.id.direct_share) {
//            directShare();
//        } else if (id == R.id.share_multi) {
//            shareMult();
//        } else if (id == R.id.share_all_platform) {
//            addCustomPlatforms();
//        }
//    }


//    /**
//     * 调用postShare分享。跳转至分享编辑页，然后再分享。</br> [注意]<li>
//     * 对于新浪，豆瓣，人人，腾讯微博跳转到分享编辑页，其他平台直接跳转到对应的客户端
//     */
//    private void postShare() {
//        CustomShareBoard shareBoard = new CustomShareBoard(activity);
//        shareBoard.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//    }

    /**
     * 直接分享，底层分享接口。如果分享的平台是新浪、腾讯微博、豆瓣、人人，则直接分享，无任何界面弹出； 其它平台分别启动客户端分享</br>
     */
    private void directShare() {
        mController.directShare(activity, mPlatform, new SocializeListeners.SnsPostListener() {

            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                String showText = "分享成功";
                if (eCode != StatusCode.ST_CODE_SUCCESSED) {
                    showText = "分享失败 [" + eCode + "]";
                }
                Toast.makeText(activity, showText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 一键分享到多个已授权平台。</br>
     */
    private void shareMult() {
        SHARE_MEDIA[] platforms = new SHARE_MEDIA[]{
                SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.RENREN
        };
        mController.postShareMulti(activity, new SocializeListeners.MulStatusListener() {

            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(MultiStatus multiStatus, int st, SocializeEntity entity) {
                String showText = "分享结果：" + multiStatus.toString();
                Toast.makeText(activity, showText, Toast.LENGTH_SHORT).show();
            }
        }, platforms);
    }

    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    private void setShareContent() {

        // 配置SSO
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity,
                "100424468", "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能。http://www.wanbaoe.com/");

        // APP ID：201874, API
        // * KEY：28401c0964f04a72a14c812d6132fcef, Secret
        // * Key：3bf66e42db1e4fa9829b955cc300b737.
        RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(activity,
                "201874", "28401c0964f04a72a14c812d6132fcef",
                "3bf66e42db1e4fa9829b955cc300b737");
        mController.getConfig().setSsoHandler(renrenSsoHandler);

        UMImage localImage = new UMImage(activity,imageBitmap);
        UMImage urlImage = new UMImage(activity, imageBitmap);
        // UMImage resImage = new UMImage(activity,imageBitmap);

        // 视频分享
        UMVideo video = new UMVideo(
                "http://v.youku.com/v_show/id_XNTc0ODM4OTM2.html");
        // vedio.setThumb("http://www.umeng.com/images/pic/home/social/img-1.png");
        video.setTitle("友盟社会化组件视频");
        video.setThumb(urlImage);

        UMusic uMusic = new UMusic(
                "http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
        uMusic.setAuthor("umeng");
        uMusic.setTitle("天籁之音");
        // uMusic.setThumb(urlImage);
        uMusic.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");

        // UMEmoji emoji = new UMEmoji(activity,
        // "http://www.pc6.com/uploadimages/2010214917283624.gif");
        // UMEmoji emoji = new UMEmoji(activity,
        // "/storage/sdcard0/emoji.gif");

        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent
                .setShareContent("这里设置分享内容-微信。http://www.wanbaoe.com/");
        weixinContent.setTitle("简单保险分享测试-微信");
        weixinContent.setTargetUrl("http://www.wanbaoe.com/");
        weixinContent.setShareMedia(urlImage);
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia
                .setShareContent("这里设置分享内容-朋友圈。http://www.wanbaoe.com/");
        circleMedia.setTitle("简单保险分享测试-朋友圈");
        circleMedia.setShareMedia(urlImage);
        // circleMedia.setShareMedia(uMusic);
        // circleMedia.setShareMedia(video);
        circleMedia.setTargetUrl("http://www.wanbaoe.com/");
        mController.setShareMedia(circleMedia);

        // 设置renren分享内容
        RenrenShareContent renrenShareContent = new RenrenShareContent();
        renrenShareContent.setShareContent("人人分享内容");
        UMImage image = new UMImage(activity,
                imageBitmap);
        image.setTitle("thumb title");
        image.setThumb("http://www.umeng.com/images/pic/social/integrated_3.png");
        renrenShareContent.setShareImage(image);
        renrenShareContent.setAppWebSite("http://www.wanbaoe.com/");
        mController.setShareMedia(renrenShareContent);

        UMImage qzoneImage = new UMImage(activity, imageBitmap);
        qzoneImage
                .setTargetUrl("http://www.umeng.com/images/pic/social/integrated_3.png");

        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent("share icon_share");
        qzone.setTargetUrl("http://www.umeng.com");
        qzone.setTitle(activity.getString(R.string.app_name));
        qzone.setShareMedia(urlImage);
        // qzone.setShareMedia(uMusic);
        mController.setShareMedia(qzone);

        video.setThumb(new UMImage(activity,imageBitmap));

        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent("这里设置分享内容 -- QQ");
        qqShareContent.setTitle("hello, title");
        qqShareContent.setShareMedia(image);
        qqShareContent.setTargetUrl("http://www.wanbaoe.com/");
        mController.setShareMedia(qqShareContent);

        // 视频分享
        UMVideo umVideo = new UMVideo(
                "http://v.youku.com/v_show/id_XNTc0ODM4OTM2.html");
        umVideo.setThumb("http://www.umeng.com/images/pic/home/social/img-1.png");
        umVideo.setTitle("主题");

        TencentWbShareContent tencent = new TencentWbShareContent();
        tencent.setShareContent("这里设置分享内容");
        // 设置tencent分享内容
        mController.setShareMedia(tencent);

        // 设置邮件分享内容， 如果需要分享图片则只支持本地图片
        MailShareContent mail = new MailShareContent(localImage);
        mail.setTitle("主题");
        mail.setShareContent("这里设置分享内容");
        // 设置tencent分享内容
        mController.setShareMedia(mail);

        // 设置短信分享内容
        SmsShareContent sms = new SmsShareContent();
        sms.setShareContent("这里设置分享内容");
        // sms.setShareImage(urlImage);
        mController.setShareMedia(sms);

        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent
                .setShareContent("这里设置分享内容");
        sinaContent.setShareImage(new UMImage(activity,imageBitmap));
        mController.setShareMedia(sinaContent);

        TwitterShareContent twitterShareContent = new TwitterShareContent();
        twitterShareContent
                .setShareContent("这里设置分享内容");
        twitterShareContent.setShareMedia(new UMImage(activity,imageBitmap));
        mController.setShareMedia(twitterShareContent);

        GooglePlusShareContent googlePlusShareContent = new GooglePlusShareContent();
        googlePlusShareContent
                .setShareContent("这里设置分享内容");
        googlePlusShareContent.setShareMedia(localImage);
        mController.setShareMedia(googlePlusShareContent);

        // 来往分享内容
        LWShareContent lwShareContent = new LWShareContent();
        // lwShareContent.setShareImage(urlImage);
        // lwShareContent.setShareMedia(uMusic);
        lwShareContent.setShareMedia(umVideo);
        lwShareContent.setTitle("主题");
        lwShareContent.setMessageFrom("信息");
        lwShareContent
                .setShareContent("这里设置分享内容");
        mController.setShareMedia(lwShareContent);

        // 来往动态分享内容
        LWDynamicShareContent lwDynamicShareContent = new LWDynamicShareContent();
        // lwDynamicShareContent.setShareImage(urlImage);
        // lwDynamicShareContent.setShareMedia(uMusic);
        lwDynamicShareContent.setShareMedia(umVideo);
        lwDynamicShareContent.setTitle("主题");
        lwDynamicShareContent.setMessageFrom("来自万保易");
        lwDynamicShareContent
                .setShareContent("这里设置分享内容");
        lwDynamicShareContent.setTargetUrl("http://www.wanbaoe.com/");
        mController.setShareMedia(lwDynamicShareContent);

    }

    /**
     * 添加所有的平台</br>
     */
    private void addCustomPlatforms() {
        // 添加微信平台
        addWXPlatform();
        // 添加QQ平台
        addQQQZonePlatform();
        // 添加印象笔记平台
        addEverNote();
        // 添加facebook平台
        addFacebook();
        // 添加Instagram平台
        addInstagram();
        // 添加来往、来往动态平台
        addLaiWang();
        // 添加LinkedIn平台
        addLinkedIn();
        // 添加Pinterest平台
        addPinterest();
        // 添加Pocket平台
        addPocket();
        // 添加有道云平台
        addYNote();
        // 添加易信平台
        addYXPlatform();
        // 添加短信平台
        addSMS();
        // 添加email平台
        addEmail();

        addWhatsApp();
        addLine();
        addTumblr();
        addkakao();
        addFlickr();

        mController.registerListener(new SocializeListeners.SnsPostListener() {

            @Override
            public void onStart() {
                Toast.makeText(activity, "share start...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                Toast.makeText(activity, "code : " + eCode, Toast.LENGTH_SHORT).show();
            }
        });

        mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT,
                SHARE_MEDIA.DOUBAN,
                SHARE_MEDIA.RENREN, SHARE_MEDIA.EMAIL, SHARE_MEDIA.EVERNOTE, SHARE_MEDIA.FACEBOOK,
                SHARE_MEDIA.GOOGLEPLUS, SHARE_MEDIA.INSTAGRAM, SHARE_MEDIA.LAIWANG,
                SHARE_MEDIA.LAIWANG_DYNAMIC, SHARE_MEDIA.LINKEDIN, SHARE_MEDIA.PINTEREST,
                SHARE_MEDIA.POCKET, SHARE_MEDIA.SMS, SHARE_MEDIA.TWITTER, SHARE_MEDIA.YIXIN,
                SHARE_MEDIA.YIXIN_CIRCLE, SHARE_MEDIA.YNOTE, SHARE_MEDIA.WHATSAPP,
                SHARE_MEDIA.LINE, SHARE_MEDIA.TUMBLR, SHARE_MEDIA.FLICKR, SHARE_MEDIA.KAKAO);
        mController.openShare(activity, false);
    }

    /**
     * 添加短信平台</br>
     */
    private void addSMS() {
        // 添加短信
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();
    }

    /**
     * 添加Email平台</br>
     */
    private void addEmail() {
        // 添加email
        EmailHandler emailHandler = new EmailHandler();
        emailHandler.addToSocialSDK();
    }

    /**
     * Pocket分享。pockect只支持分享网络链接</br>
     */
    private void addPocket() {
        UMPocketHandler pocketHandler = new UMPocketHandler(activity);
        pocketHandler.addToSocialSDK();
        PocketShareContent pocketShareContent = new PocketShareContent();
        pocketShareContent.setShareContent("http://www.wanbaoe.com/");
        mController.setShareMedia(pocketShareContent);
    }

    /**
     * LinkedIn分享。LinkedIn只支持图片，文本，图文分享</br>
     */
    private void addLinkedIn() {
        UMLinkedInHandler linkedInHandler = new UMLinkedInHandler(activity);
        linkedInHandler.addToSocialSDK();
        LinkedInShareContent linkedInShareContent = new LinkedInShareContent();
        linkedInShareContent
                .setShareContent("这里设置分享内容-LinkedIn。http://www.wanbaoe.com/");
        mController.setShareMedia(linkedInShareContent);
    }

    /**
     * 有道云笔记分享。有道云笔记只支持图片，文本，图文分享</br>
     */
    private void addYNote() {
        UMYNoteHandler yNoteHandler = new UMYNoteHandler(activity);
        yNoteHandler.addToSocialSDK();
        YNoteShareContent yNoteShareContent = new YNoteShareContent();
        yNoteShareContent
                .setShareContent("这里设置分享内容-云有道笔记。http://www.wanbaoe.com/");
        yNoteShareContent.setTitle("主题");
        yNoteShareContent.setShareImage(new UMImage(activity, imageBitmap));
        mController.setShareMedia(yNoteShareContent);
    }

    /**
     * 添加印象笔记平台
     */
    private void addEverNote() {
        UMEvernoteHandler evernoteHandler = new UMEvernoteHandler(activity);
        evernoteHandler.addToSocialSDK();

        // 设置evernote的分享内容
        EvernoteShareContent shareContent = new EvernoteShareContent(
                "这里设置分享内容-EverNote。http://www.wanbaoe.com/");
        shareContent.setShareMedia(new UMImage(activity, imageBitmap));
        mController.setShareMedia(shareContent);
    }

    /**
     * 添加Pinterest平台
     */
    private void addPinterest() {
        /**
         * app id需到pinterest开发网站( https://developers.pinterest.com/ )自行申请.
         */
        UMPinterestHandler pinterestHandler = new UMPinterestHandler(
                activity, "1439206");
        pinterestHandler.addToSocialSDK();

        // 设置Pinterest的分享内容
        PinterestShareContent shareContent = new PinterestShareContent(
                "这里设置分享内容-Pinterest。http://www.wanbaoe.com/");
        shareContent.setShareMedia(new UMImage(activity,imageBitmap));
        mController.setShareMedia(shareContent);
    }

    /**
     * 添加来往和来往动态平台</br>
     */
    private void addLaiWang() {

        String appToken = "laiwangd497e70d4";
        String secretID = "d497e70d4c3e4efeab1381476bac4c5e";
        // laiwangd497e70d4:来往appToken,d497e70d4c3e4efeab1381476bac4c5e:来往secretID
        // 添加来往的支持
        UMLWHandler umlwHandler = new UMLWHandler(activity, appToken, secretID);
        umlwHandler.addToSocialSDK();

        // 添加来往动态的支持
        UMLWHandler lwDynamicHandler = new UMLWHandler(activity,
                appToken, secretID);
        lwDynamicHandler.setToCircle(true);
        lwDynamicHandler.addToSocialSDK();
    }

    /**
     * @功能描述 : 添加微信平台分享
     * @return
     */
    private void addWXPlatform() {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
//        String appId = "wx967daebe835fbeac";
//        String appSecret = "5bb696d9ccd75a38c8a0bfe0675559b3";
//
        String appId = AppID_WX;
        String appSecret = AppSecret_WX;
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(activity, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     * @return
     */
    private void addQQQZonePlatform() {
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity,
                AppID_QQ, AppSecret_QQ);
        qqSsoHandler.setTargetUrl("http://www.wanbaoe.com/");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity,AppID_QQ, AppSecret_QQ);
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * @Title: addYXPlatform
     * @Description:
     * @throws
     */
    private void addYXPlatform() {

        // 添加易信平台
        UMYXHandler yixinHandler = new UMYXHandler(activity,
                "yxc0614e80c9304c11b0391514d09f13bf");
        // 关闭分享时的等待Dialog
        yixinHandler.enableLoadingDialog(false);
        // 设置target Url, 必须以http或者https开头
        yixinHandler.setTargetUrl("http://www.wanbaoe.com/");
        yixinHandler.addToSocialSDK();

        // 易信朋友圈平台
        UMYXHandler yxCircleHandler = new UMYXHandler(activity,
                "yxc0614e80c9304c11b0391514d09f13bf");
        yxCircleHandler.setToCircle(true);
        yxCircleHandler.addToSocialSDK();

    }

    /**
     * @Title: addFacebook
     * @Description:
     * @throws
     */
    private void addFacebook() {

        UMFacebookHandler mFacebookHandler = new UMFacebookHandler(activity);
        mFacebookHandler.addToSocialSDK();

        UMImage localImage = new UMImage(activity,imageBitmap);

        UMVideo umVedio = new UMVideo(
                "http://v.youku.com/v_show/id_XNTc0ODM4OTM2.html");
        umVedio.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
        umVedio.setTitle("友盟社会化组件视频");

        FaceBookShareContent fbContent = new FaceBookShareContent();
//         fbContent.setShareImage(new UMImage(activity,
//         "http://www.umeng.com/images/pic/social/integrated_3.png"));
//        fbContent.setShareImage(localImage);
//         fbContent.setShareContent("This is my facebook social share sdk.");
//        fbContent.setShareVideo(umVedio);
        fbContent.setTitle("FB title");
        fbContent.setCaption("Caption - Fb");
        fbContent.setShareContent("主题支持FB最新版啦~");
        fbContent.setTargetUrl("http://www.wanbaoe.com/");
        mController.setShareMedia(fbContent);

    }

    /**
     * </br> Instagram只支持图片分享, 只支持纯图片分享.</br>
     */
    private void addInstagram() {
        // 构建Instagram的Handler
        UMInstagramHandler instagramHandler = new UMInstagramHandler(
                activity);
        instagramHandler.addToSocialSDK();

        UMImage localImage = new UMImage(activity, imageBitmap);

        // // 添加分享到Instagram的内容
        InstagramShareContent instagramShareContent = new InstagramShareContent(
                localImage);
        mController.setShareMedia(instagramShareContent);
    }

    private void addWhatsApp() {
        UMWhatsAppHandler whatsAppHandler = new UMWhatsAppHandler(activity);
        whatsAppHandler.addToSocialSDK();
        WhatsAppShareContent whatsAppShareContent = new WhatsAppShareContent();
        // whatsAppShareContent.setShareContent("share icon_share");
        whatsAppShareContent.setShareImage(new UMImage(activity,imageBitmap));
        mController.setShareMedia(whatsAppShareContent);
        // mController.openShare(activity, false);
    }

    private void addLine() {
        UMLineHandler lineHandler = new UMLineHandler(activity);
        lineHandler.addToSocialSDK();
        LineShareContent lineShareContent = new LineShareContent();
        // lineShareContent.setShareContent("share icon_share");
        lineShareContent.setShareImage(new UMImage(activity,imageBitmap));
        mController.setShareMedia(lineShareContent);
        // mController.openShare(activity, false);
    }

    private void addTumblr() {
        UMTumblrHandler tumblrHandler = new UMTumblrHandler(activity);
        tumblrHandler.addToSocialSDK();
        TumblrShareContent tumblrShareContent = new TumblrShareContent();
        tumblrShareContent.setTitle("主题");
        tumblrShareContent.setShareContent("设置分析内容");
        tumblrShareContent.setShareImage(new UMImage(activity,imageBitmap));
        mController.setShareMedia(tumblrShareContent);
        // mController.openShare(activity, false);
    }

    private void addkakao() {
        UMKakaoHandler kakaoHandler = new UMKakaoHandler(activity);
        kakaoHandler.addToSocialSDK();

        KakaoShareContent kakaoShareContent = new KakaoShareContent();
        // kakaoShareContent.setShareContent("share icon_share");
        kakaoShareContent.setShareImage(new UMImage(activity,imageBitmap));
        mController.setShareMedia(kakaoShareContent);
        // mController.openShare(activity, false);
    }

    private void addFlickr() {
        UMFlickrHandler flickrHandler = new UMFlickrHandler(activity);
        flickrHandler.addToSocialSDK();
        FlickrShareContent flickrShareContent = new FlickrShareContent();
        flickrShareContent.setShareImage(new UMImage(activity,imageBitmap));
        // flickrShareContent.setShareContent("share icon_share");
        mController.setShareMedia(flickrShareContent);
        // mController.openShare(activity, false);
    }

    // /**
    // * 授权后直接分享
    // */
    // private void shareAfterOauth() {
    //
    // // 授权
    // mController.doOauthVerify(activity, SHARE_MEDIA.TENCENT,
    // new UMAuthListener() {
    //
    // @Override
    // public void onStart(SHARE_MEDIA platform) {
    //
    // }
    //
    // @Override
    // public void onError(SocializeException e,
    // SHARE_MEDIA platform) {
    // Toast.makeText(activity, "授权错误",
    // Toast.LENGTH_SHORT).show();
    // }
    //
    // @Override
    // public void onComplete(Bundle value, SHARE_MEDIA platform) {
    // directShare();
    // }
    //
    // @Override
    // public void onCancel(SHARE_MEDIA platform) {
    // Toast.makeText(activity, "授权取消",
    // Toast.LENGTH_SHORT).show();
    // }
    // });
    // }
    //
    // private void checkToken() {
    // SHARE_MEDIA[] platforms = new SHARE_MEDIA[] { SHARE_MEDIA.SINA,
    // SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.DOUBAN,
    // SHARE_MEDIA.RENREN, SHARE_MEDIA.TENCENT };
    // mController.checkTokenExpired(activity, platforms,
    // new UMDataListener() {
    //
    // @Override
    // public void onStart() {
    //
    // }
    //
    // @Override
    // public void onComplete(int status, Map<String, Object> info) {
    // if (info != null) {
    // Set<String> keys = info.keySet();
    // StringBuilder builder = new StringBuilder();
    // for (String key : keys) {
    // builder.append(key).append("=")
    // .append(info.get(key)).append(",");
    // }
    // if (builder.length() > 0) {
    // builder.deleteCharAt(builder.length() - 1);
    // }
    // Toast.makeText(activity, builder.toString(),
    // Toast.LENGTH_SHORT).show();
    // }
    // }
    // });
    // }
    //
    // /**
    // * 打开新浪和腾讯微薄的SSO授权
    // */
    // private void openSSO() {
    // mController.getConfig().setSsoHandler(new SinaSsoHandler());
    // mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
    // mController.getConfig().setSsoHandler(
    // new RenrenSsoHandler(activity, "201874",
    // "28401c0964f04a72a14c812d6132fcef",
    // "3bf66e42db1e4fa9829b955cc300b737"));
    // }
    //
    // /**
    // * 关闭sina微博SSO，QQ zone SSO，腾讯微博SSO，
    // */
    // private void closeSSO() {
    // mController.getConfig().removeSsoHandler(SHARE_MEDIA.SINA);
    // mController.getConfig().removeSsoHandler(SHARE_MEDIA.TENCENT);
    // mController.getConfig().removeSsoHandler(SHARE_MEDIA.RENREN);
    // }

    // /**
    // * @功能描述 :
    // */
    // public void removePlatform() {
    // mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
    // SHARE_MEDIA.DOUBAN);
    // mController.openShare(activity, false);
    // }

    // /**
    // * 根据开发者设置的顺序对平台排序
    // */
    // private void sortPlatform() {
    // mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN_CIRCLE,
    // SHARE_MEDIA.RENREN, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ,
    // SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.GOOGLEPLUS,
    // SHARE_MEDIA.EMAIL, SHARE_MEDIA.TENCENT, SHARE_MEDIA.GENERIC);
    // mController.openShare(activity, false);
    // }

    // /**
    // * @功能描述 : 分享(先选择平台)
    // */
    // private void openShare() {
    // mController.openShare(activity, false);
    // }

    // /**
    // * @功能描述 : 图文分享（呼出编辑页）
    // */
    // private void directShare() {
    //
    // // mController.setShareMedia(new TencentWbShareMedia(new
    // // UMImage(mContext,
    // // BitmapFactory.decodeResource(getResources(),
    // // R.drawable.actionbar_compat_logo))));
    //
    // // 视频分享
    // UMVideo umVedio = new UMVideo(
    // "http://v.youku.com/v_show/id_XNTc0ODM4OTM2.html");
    // umVedio.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
    // umVedio.setTitle("友盟社会化组件视频");
    // TencentWbShareContent tencentWbShareContent = new TencentWbShareContent(
    // umVedio);
    // tencentWbShareContent
    // .setShareContent("腾讯微博分享内容 Direct --这里设置分享内容");
    // // 设置tencent分享内容
    // mController.setShareMedia(tencentWbShareContent);
    //
    // mController.directShare(activity, SHARE_MEDIA.TENCENT,
    // new SnsPostListener() {
    //
    // @Override
    // public void onStart() {
    //
    // }
    //
    // @Override
    // public void onComplete(SHARE_MEDIA platform, int eCode,
    // SocializeEntity entity) {
    // Toast.makeText(activity, "分享完成",
    // Toast.LENGTH_SHORT).show();
    // }
    // });
    // }

    // /**
    // * @功能描述 : 快速分享接口（呼出编辑页）
    // */
    // private void quickShare() {
    // // 快速分享接口
    // mController.shareTo(activity, mShareContent, null);
    // }
    // /**
    // * @功能描述 : 图文分享（功能底层接口）
    // */
    // private void textAndPicShare() {
    //
    // mController.postShare(mContext, mTestMedia, new SnsPostListener() {
    //
    // @Override
    // public void onComplete(SHARE_MEDIA arg0, int arg1,
    // SocializeEntity arg2) {
    // Toast.makeText(mContext, "分享完成", Toast.LENGTH_SHORT).show();
    // }
    //
    // @Override
    // public void onStart() {
    // Toast.makeText(mContext, "开始分享", Toast.LENGTH_SHORT).show();
    // }
    // });
    // }
    // /**
    // * @功能描述 : 授权（功能底层接口）
    // */
    // private void doOauth() {
    // if (OauthHelper.isAuthenticated(activity, mTestMedia)) {
    // Toast.makeText(mContext, "新浪平台已经授权.", Toast.LENGTH_SHORT).show();
    // } else {
    // mController.doOauthVerify(mContext, mTestMedia,
    // new UMAuthListener() {
    // @Override
    // public void onError(SocializeException e,
    // SHARE_MEDIA platform) {
    // }
    //
    // @Override
    // public void onComplete(Bundle value,
    // SHARE_MEDIA platform) {
    // mController.directShare(activity,
    // SHARE_MEDIA.QQ, null);
    // // if (value != null
    // // && !TextUtils.isEmpty(value
    // // .getString("uid"))) {
    // // Toast.makeText(mContext, "授权成功.",
    // // Toast.LENGTH_SHORT).show();
    // // } else {
    // // Toast.makeText(mContext, "授权失败",
    // // Toast.LENGTH_SHORT).show();
    // // }
    // }
    //
    // @Override
    // public void onCancel(SHARE_MEDIA arg0) {
    // }
    //
    // @Override
    // public void onStart(SHARE_MEDIA arg0) {
    // }
    //
    // });
    // }
    // }
    // /**
    // * @功能描述 : 解除（功能底层接口）
    // */
    // private void deleteOauth() {
    // mController.deleteOauth(mContext, mTestMedia,
    // new SocializeClientListener() {
    // @Override
    // public void onStart() {
    // Log.d(TAG,
    // "sina="
    // + OauthHelper.isAuthenticated(
    // activity, mTestMedia));
    //
    // }
    //
    // @Override
    // public void onComplete(int status, SocializeEntity entity) {
    // Log.d(TAG,
    // status
    // + "      sina="
    // + OauthHelper.isAuthenticated(
    // activity, mTestMedia));
    // }
    // });
    // }


}
