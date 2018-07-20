package com.sagereal.streettest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SysIntentReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            if (SettingPrefMain.getIsloop(context)) {
                Intent it = new Intent();
                it.setClass(context, MainActivity.class);
                //it.setFlags(268435456);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
                return;
            }
        } else if (!action.equals("android.intent.action.ACTION_SHUTDOWN")) {
        }
    }
}
