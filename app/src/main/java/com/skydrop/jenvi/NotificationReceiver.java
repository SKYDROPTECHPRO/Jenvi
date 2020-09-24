package com.skydrop.jenvi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class NotificationReceiver extends BroadcastReceiver {

    public static final String NEXT_ACTION = "next";
    public static final String PREV_ACTION = "prev";
    public static final String PLAY_ACTION = "play";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toastMessage");
        System.out.println("msg:"+message);
        assert message != null;
        if(message.equals(PLAY_ACTION)){
            Singleton.Instance.isplaying = !Singleton.Instance.isplaying;
            Singleton.Instance.sendOnChannel2();
        }
    }


}
