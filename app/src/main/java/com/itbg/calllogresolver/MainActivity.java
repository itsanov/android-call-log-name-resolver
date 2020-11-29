package com.itbg.calllogresolver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity  extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!ServiceUtils.isMyServiceRunning(this, CallLogService.class))
            startService(new Intent(this,CallLogService.class));
    }
}
