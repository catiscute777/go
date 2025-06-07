package com.example.go.forbuyer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * The {@code bootreciver}
 * class is a broadcast receiver that starts the {@code FirebasedatabseService}
 *  when the device is rebooted.
 * */
public class bootreciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent serviceIntent = new Intent(context, FirebasedatabseService.class);
            context.startService(serviceIntent);
        }

    }
}