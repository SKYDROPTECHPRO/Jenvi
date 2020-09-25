package com.skydrop.jenvi;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Applicationclass  extends Application {
    public static final String CHANNEL_ID_1 = "channel1";
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PREV = "PREV";
    public static final String ACTION_PLAY = "PLAY";

    @Override
    public void onCreate() {
        super.onCreate();
        createnotification();
    }

    private void createnotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID_1,
                    "Music",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("Music display");


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
