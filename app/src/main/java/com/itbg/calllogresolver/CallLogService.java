package com.itbg.calllogresolver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.CallLog;
import android.widget.Toast;

public class CallLogService extends Service {

    CallLogChangeObserverClass callLogChangeEvents = null;

    public void onDestroy() {
        //Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {

        callLogChangeEvents = new CallLogChangeObserverClass(new Handler(),this);

        //Registering content observer
        getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, true,
                callLogChangeEvents);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
