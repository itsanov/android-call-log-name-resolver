package com.itbg.calllogresolver;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;

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

        //return START_STICKY;
        return super.onStartCommand(intent, flags, startid);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
