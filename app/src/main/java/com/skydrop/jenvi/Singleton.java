package com.skydrop.jenvi;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.skydrop.jenvi.App.CHANNEL_2_ID;
import static com.skydrop.jenvi.NotificationReceiver.PLAY_ACTION;

public class Singleton {
    public static Singleton Instance = new Singleton();
    private Singleton(){}
    public boolean isplaying = true;
    public Context applicationcontext;
    public Bitmap artwork;

    public void sendOnChannel2() {

        Intent nextaction = new Intent(Singleton.Instance.applicationcontext,NotificationReceiver.class);
        nextaction.putExtra("toastMessage","Next presses");
        PendingIntent nexting = PendingIntent.getBroadcast(Singleton.Instance.applicationcontext,0,nextaction,PendingIntent.FLAG_UPDATE_CURRENT);


        Intent playaction = new Intent(Singleton.Instance.applicationcontext,NotificationReceiver.class);
        playaction.putExtra("toastMessage",PLAY_ACTION);
        PendingIntent playIntent = PendingIntent.getBroadcast(Singleton.Instance.applicationcontext,0,playaction,PendingIntent.FLAG_UPDATE_CURRENT);



        Notification notification;
        notification = new NotificationCompat.Builder(Singleton.Instance.applicationcontext, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle("title")
                .setContentText("message")
                .setLargeIcon(artwork)
                .addAction(R.drawable.ic_dislike, "Dislike",null)
                .addAction(R.drawable.ic_previous, "Previous", null)
                .addAction(Singleton.Instance.isplaying?R.drawable.ic_pause: R.drawable.ic_play, "Pause", playIntent)
                .addAction(R.drawable.ic_next, "Next", nexting)
                .addAction(R.drawable.ic_like, "Like", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1,2,3)

                )
//                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
//                        .setShowActionsInCompactView(1, 2, 3)
//                        .setMediaSession(mediaSession.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setNumber(0)
                .setOngoing(Singleton.Instance.isplaying)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Singleton.Instance.applicationcontext);
        notificationManager.notify(2, notification);
    }
}
