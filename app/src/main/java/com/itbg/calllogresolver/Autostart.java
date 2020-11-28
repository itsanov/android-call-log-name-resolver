package com.itbg.calllogresolver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class Autostart extends BroadcastReceiver
{
    public void onReceive(Context context, Intent arg1)
    {
        //Toast.makeText(context, "Receiver Started", Toast.LENGTH_LONG).show();;
        context.startService(new Intent(context,CallLogService.class));
    }
}

