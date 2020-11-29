package com.itbg.calllogresolver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Autostart extends BroadcastReceiver
{
    public void onReceive(Context context, Intent arg1)
    {
        if(!ServiceUtils.isMyServiceRunning(context, CallLogService.class))
            context.startService(new Intent(context, CallLogService.class));
    }
}

