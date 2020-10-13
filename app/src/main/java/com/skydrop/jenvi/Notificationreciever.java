package com.skydrop.jenvi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.skydrop.jenvi.Singletons.CurrentPlayingsongSingleton;

import static com.skydrop.jenvi.Applicationclass.*;

public class Notificationreciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Reciver class");
        String action = intent.getStringExtra(ACTION);
        assert action != null;
        System.out.println("Action: recived"+action);
        switch (action) {
            case ACTION_NEXT:
                CurrentPlayingsongSingleton.getInstance().nextsong();
                System.out.println("Next notification");
                break;
            case ACTION_PREV:
                CurrentPlayingsongSingleton.getInstance().prevsong();
                break;
            case ACTION_PLAY:
                CurrentPlayingsongSingleton.getInstance().playorpause();
                break;
        }
    }
}
