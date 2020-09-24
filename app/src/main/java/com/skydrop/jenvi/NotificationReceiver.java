package com.skydrop.jenvi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class NotificationReceiver extends BroadcastReceiver {

    public static final String NEXT_ACTION = "next";
    public static final String PREV_ACTION = "prev";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toastMessage");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
