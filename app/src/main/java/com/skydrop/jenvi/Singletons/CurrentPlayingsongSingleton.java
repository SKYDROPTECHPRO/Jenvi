package com.skydrop.jenvi.Singletons;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.skydrop.jenvi.Notificationreciever;
import com.skydrop.jenvi.R;
import com.skydrop.jenvi.models.SongModel;

import static com.skydrop.jenvi.Applicationclass.*;

public class CurrentPlayingsongSingleton {
    @SuppressLint("StaticFieldLeak")
    private static CurrentPlayingsongSingleton Instance=new CurrentPlayingsongSingleton();
    Context context;
    private CurrentPlayingsongSingleton() {
    }

    public static CurrentPlayingsongSingleton getInstance() {
        return Instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private SongModel currentsong;
    private int position;
    private boolean isplaying = false;
    private MediaPlayer player;

    private String title;
    private String duration;
    private int albumart;
    private SongsListSingleton songslist = SongsListSingleton.getInstance();


    public void setCurrentsong(int pos) {
        position = pos;
        this.currentsong = songslist.get(pos);
        loadmedia(context);
    }


    private void loadmedia(Context cont) {
        System.out.println("load midea:"+currentsong.getPath());
        try {
            if(player != null){
                player.stop();
                isplaying = false;
                player = null;
            }
            player = MediaPlayer.create(cont,Uri.parse(currentsong.getPath()));
            this.title = currentsong.getTitle();
            this.albumart=currentsong.getAlbumart();
            this.duration = currentsong.getDuration();
            System.out.println("song"+title);
            playorpause();
        }
        catch (Exception e){
            nextsong();
        }
    }


    public void playorpause(){
        if(isplaying){
            isplaying = false;
            player.pause();
        }
        else {
            isplaying = true;
            player.start();
        }
        shonotification();
    }


    public void nextsong(){
        setCurrentsong((position+1) % songslist.size());
    }

    public void prevsong(){
        position--;
        if(position<0){
            position = songslist.size()-1;
        }
        setCurrentsong(position);
    }

    public boolean isIsplaying() {
        return isplaying;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public int getAlbumart() {
        return albumart;
    }

    public Context getContext() {
        return context;
    }

    private void shonotification(){
        Intent Nextintent = new Intent(CurrentPlayingsongSingleton.getInstance().getContext(), Notificationreciever.class);
        Nextintent.putExtra(ACTION,ACTION_NEXT);
        PendingIntent NextAction = PendingIntent.getBroadcast(CurrentPlayingsongSingleton.getInstance().getContext(),0,Nextintent,PendingIntent.FLAG_UPDATE_CURRENT);


        Intent Previntent = new Intent(CurrentPlayingsongSingleton.getInstance().getContext(),Notificationreciever.class);
        Nextintent.putExtra(ACTION,ACTION_PREV);
        PendingIntent PrevAction = PendingIntent.getBroadcast(CurrentPlayingsongSingleton.getInstance().getContext(),0,Previntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent Playintent = new Intent(CurrentPlayingsongSingleton.getInstance().getContext(),Notificationreciever.class);
        Nextintent.putExtra(ACTION,ACTION_PLAY);
        PendingIntent PlayAction = PendingIntent.getBroadcast(CurrentPlayingsongSingleton.getInstance().getContext(),0,Playintent,PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(CurrentPlayingsongSingleton.getInstance().getContext(), CHANNEL_ID_1)
                .setSmallIcon(R.drawable.music)
                .setContentTitle(title)
                .setContentText(currentsong.getArtist())
                .setLargeIcon(currentsong.getAlbumartbitmap())
                .addAction(R.drawable.prev, "Previous", PrevAction)
                .addAction(isplaying?R.drawable.pause: R.drawable.play, "Pause", PlayAction)
                .addAction(R.drawable.next, "Next", NextAction)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0,1,2)
                )
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setNumber(0)
                .setOngoing(isplaying)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(2, notification);
    }
}
