package com.itbg.calllogresolver;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceUtils {
    public static boolean isMyServiceRunning(Context ct, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) ct.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
