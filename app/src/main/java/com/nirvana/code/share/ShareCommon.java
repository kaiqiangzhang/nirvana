package com.nirvana.code.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.nirvana.code.NivanaApplication;
import com.nirvana.code.core.utils.log.NVLog;
import com.nirvana.code.model.Defaultcontent;
import com.nirvana.code.model.StyleUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.socialize.utils.SocializeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kriszhang on 2017/5/21.
 */

public class ShareCommon {
    public ArrayList<SnsPlatform> platforms = new ArrayList<SnsPlatform>();
    public ArrayList<String> styles = new ArrayList<String>();
    private UMImage imageurl,imagelocal;
    private UMWeb umWeb;
    private UMVideo video;
    private UMusic music;
    private UMEmoji emoji;
    private File file;
    private Activity mContext;
    private UMShareListener mShareListener;
    private ShareAction mShareAction;

    public ShareCommon(Activity context){
        this.mContext = context;
        mShareListener =new CustomShareListener(context);
    }

    public void initPlatforms(){
        platforms.clear();
        platforms.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WEIXIN_FAVORITE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.SINA.toSnsPlatform());
        platforms.add(SHARE_MEDIA.QQ.toSnsPlatform());
        platforms.add(SHARE_MEDIA.QZONE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.ALIPAY.toSnsPlatform());
        platforms.add(SHARE_MEDIA.DINGTALK.toSnsPlatform());
        platforms.add(SHARE_MEDIA.RENREN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.DOUBAN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.SMS.toSnsPlatform());
        platforms.add(SHARE_MEDIA.EMAIL.toSnsPlatform());
        platforms.add(SHARE_MEDIA.YNOTE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.EVERNOTE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.LAIWANG.toSnsPlatform());
        platforms.add(SHARE_MEDIA.LAIWANG_DYNAMIC.toSnsPlatform());
        platforms.add(SHARE_MEDIA.LINKEDIN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.YIXIN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.YIXIN_CIRCLE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.TENCENT.toSnsPlatform());
        platforms.add(SHARE_MEDIA.FACEBOOK.toSnsPlatform());
        platforms.add(SHARE_MEDIA.FACEBOOK_MESSAGER.toSnsPlatform());
        platforms.add(SHARE_MEDIA.TWITTER.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WHATSAPP.toSnsPlatform());
        platforms.add(SHARE_MEDIA.GOOGLEPLUS.toSnsPlatform());
        platforms.add(SHARE_MEDIA.LINE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.INSTAGRAM.toSnsPlatform());
        platforms.add(SHARE_MEDIA.KAKAO.toSnsPlatform());
        platforms.add(SHARE_MEDIA.PINTEREST.toSnsPlatform());
        platforms.add(SHARE_MEDIA.POCKET.toSnsPlatform());
        platforms.add(SHARE_MEDIA.TUMBLR.toSnsPlatform());
        platforms.add(SHARE_MEDIA.FLICKR.toSnsPlatform());
        platforms.add(SHARE_MEDIA.FOURSQUARE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.MORE.toSnsPlatform());

    }

    public void initStyles(SHARE_MEDIA share_media){
        styles.clear();
        if (share_media == SHARE_MEDIA.QQ){
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.QZONE){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.SINA){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.TEXTANDIMAGE);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.WEIXIN){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
            styles.add(StyleUtil.EMOJI);
        }
        else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }
        else if (share_media == SHARE_MEDIA.WEIXIN_FAVORITE){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);

        }  else if (share_media == SHARE_MEDIA.TENCENT){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        } else if (share_media == SHARE_MEDIA.DOUBAN){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.TEXTANDIMAGE);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.RENREN){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.TEXTANDIMAGE);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.ALIPAY){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
        }else if (share_media == SHARE_MEDIA.FACEBOOK){
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.FACEBOOK_MESSAGER){
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.TWITTER){
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.EMAIL){
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.SMS){
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.YIXIN){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.YIXIN_CIRCLE){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.LAIWANG){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.LAIWANG_DYNAMIC){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.INSTAGRAM){
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
        }else if (share_media == SHARE_MEDIA.PINTEREST){
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.TUMBLR){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.LINE){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.WHATSAPP){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.KAKAO){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.GOOGLEPLUS){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.EVERNOTE){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.YNOTE){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.FLICKR){

            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);

        }else if (share_media == SHARE_MEDIA.LINKEDIN){
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }else if (share_media == SHARE_MEDIA.POCKET) {
            styles.add(StyleUtil.WEB00);
        }else if (share_media == SHARE_MEDIA.FOURSQUARE) {
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
        }else if (share_media == SHARE_MEDIA.MORE) {
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.TEXTANDIMAGE);
        }else if (share_media == SHARE_MEDIA.DINGTALK) {
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }

    }

    public void initMedia(final String title,final String contentUrl,final String firstPicUrl){
        String  picurl=firstPicUrl;
        Log.e("Splash","firstPicUrl1="+firstPicUrl);
        if (TextUtils.isEmpty(picurl)){
            picurl="http://www.haowuyun.com/pics/ic_launcher.png";
        }
        imageurl = new UMImage(mContext,picurl);
        imageurl.setThumb(new UMImage(mContext, picurl));
        imagelocal = new UMImage(mContext,picurl);
        imagelocal.setThumb(new UMImage(mContext, picurl));
        imagelocal.setDescription(title);
        UMImage image = new UMImage(mContext, picurl);
        image.compressStyle=UMImage.CompressStyle.SCALE;
        image.compressFormat= Bitmap.CompressFormat.PNG;
        umWeb =new UMWeb(contentUrl);
        umWeb.setTitle(title);
        umWeb.setDescription("人生趣事，情感生活，励志故事，职场正能量，IT/技术资讯。LoveInLog，志在提高人们闲暇时间的利用率，传递人生向上的力量。更好的人生，睿智的人生尽在LoveInLog。");
        umWeb.setThumb(image);



        music = new UMusic(Defaultcontent.musicurl);
        video = new UMVideo(Defaultcontent.videourl);
        music.setTitle("This is music title");
        music.setThumb(new UMImage(mContext, picurl));
        music.setDescription("my description");
        //init video
        video.setThumb(new UMImage(mContext,picurl));
        video.setTitle("This is video title");
        video.setDescription("my description");
        emoji = new UMEmoji(mContext,"http://img5.imgtn.bdimg.com/it/u=2749190246,3857616763&fm=21&gp=0.jpg");
        emoji.setThumb(imagelocal);
        file = new File(NivanaApplication.mAPP.getFilesDir()+"test.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (SocializeUtils.File2byte(file).length<=0){
            String content = "LoveInLog分享";
            byte[] contentInBytes = content.getBytes();
            try {
                FileOutputStream fop = new FileOutputStream(file);
                fop.write(contentInBytes);
                fop.flush();
                fop.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void shareBigPic(final String firstPicUrl){
        initMedia("","",firstPicUrl);
        ShareBoardConfig config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        new ShareAction(mContext).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                SHARE_MEDIA.ALIPAY,
                SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL,

                SHARE_MEDIA.TENCENT,


                SHARE_MEDIA.MORE)
                .withText("#慢节奏# ☞LoveInLog分享")
//                        .withTitle("LoveInLog分享")
//                        .withTargetUrl(webView.getOriginalUrl())
//                        .setShareboardclickCallback(new ShareBoardlistener() {
//                            @Override
//                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
//                                if (share_media==SHARE_MEDIA.QZONE || share_media==SHARE_MEDIA.WEIXIN_CIRCLE){
//                                    mShareAction.withMedia(imagelocal);
//                                    if (share_media==SHARE_MEDIA.WEIXIN_CIRCLE){
//                                        mShareAction.withTitle(webView.getTitle());
//                                    }
//
//                                }
//                                mShareAction.setPlatform(share_media);
//                                mShareAction.share();
//                            }
//                        })
                .setCallback(mShareListener).open(config);
    }

    public void sharePicText(final String title,final String originalUrl,final String firstPicUrl){
         initMedia(title,originalUrl,firstPicUrl);
        ShareBoardConfig config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        mShareAction = new ShareAction(mContext);
        mShareAction.setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                SHARE_MEDIA.ALIPAY,
                SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL,

                SHARE_MEDIA.TENCENT,


                SHARE_MEDIA.MORE);
        mShareAction.withText(title);
//        mShareAction.withTitle("LoveInLog分享");
//        mShareAction.withTargetUrl(originalUrl);
        mShareAction.setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                if (share_media == SHARE_MEDIA.QQ || share_media == SHARE_MEDIA.QZONE || share_media == SHARE_MEDIA.WEIXIN_CIRCLE || share_media == SHARE_MEDIA.SINA) {
//                    mShareAction.withMedia(imagelocal);
                    mShareAction.withMedia(umWeb);
//                    if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
//                        mShareAction.withTitle(title);
//                    }

                }
                mShareAction.setPlatform(share_media);
                mShareAction.share();
            }
        });
        mShareAction.setCallback(mShareListener);
        mShareAction.open(config);
    }

    /**
     * 当屏幕发生改变时调用此方法,以防止Window泄漏
     */
    public void closeShare(){
        if (mShareAction !=null){
            mShareAction.close();
        }
    }
}
