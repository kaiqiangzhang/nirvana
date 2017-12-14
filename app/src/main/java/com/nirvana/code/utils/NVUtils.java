package com.nirvana.code.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * Created by kriszhang on 2017/1/31.
 */

public class NVUtils {
    public static int dip2pix(Context context, int dips) {
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        return (dips * densityDpi) / 160;
    }

    public static int pix2dip(Context context, int pixs) {
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        return (pixs * 160) / densityDpi;
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }
}
