package com.example.weatherapp.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RebootAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // TODO: 알람 복구 로직 구현
        }
    }
}

