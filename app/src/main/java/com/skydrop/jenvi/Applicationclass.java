package com.skydrop.jenvi;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Applicationclass  extends Application {
    public static final String CHANNEL_ID_1 = "channel1";
    public static final String CHANNEL_ID_2 = "channel2";
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
                    "Channel(1)",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("Channel1 Description");

            NotificationChannel notificationChannel2 = new NotificationChannel(
                    CHANNEL_ID_2,
                    "Channel(2)",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel2.setDescription("Channel2 Description");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.createNotificationChannel(notificationChannel2);
        }
    }
}
