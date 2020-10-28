package com.nirvana.code;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by kriszhang on 16/7/6.
 */
public class NivanaApplication extends Application {
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    public static NivanaApplication mAPP;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wx1ad4e33fd06e3224", "f3cc055488ccef494143b4f71b266dcf");
        PlatformConfig.setQQZone("1105908768", "v4zcQAwcjOYFZEON");
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博

        PlatformConfig.setSinaWeibo("3752428046", "7685545ee029aee1501a9c9609f31e67","http://sns.whalecloud.com/sina2/callback");
//        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";//6.3+不需要
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");

    }

//    @Override
//    public void uncaughtException(Thread thread, Throwable ex) {
//        // TODO Auto-generated method stub
//        PrintStream printStream = null;
//        try {
//            FileUtils fileUtils = new FileUtils(this);
//            File dir = fileUtils.getDir("error");
//            File file = new File(dir, "error.txt");
//
//            FileOutputStream outputStream = new FileOutputStream(file, true);
//            printStream = new PrintStream(outputStream, false);
//            printStream.append(new Date().toString());
//            printStream.append("\n");
//            ex.printStackTrace(printStream);
//            printStream.flush();
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            if (printStream != null) {
//                printStream.close();
//                printStream = null;
//            }
//        }
//
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Config.DEBUG = true;
        mAPP = this;
        UMShareAPI.get(this);
    }
}
