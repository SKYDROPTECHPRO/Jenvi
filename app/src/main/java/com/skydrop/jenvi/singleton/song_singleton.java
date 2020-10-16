package com.skydrop.jenvi.singleton;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.skydrop.jenvi.Activities.Playeractivity;
import com.skydrop.jenvi.Notifications.NotificationReceiver;
import com.skydrop.jenvi.R;
import com.skydrop.jenvi.models.SongModel;

import static com.skydrop.jenvi.Applications.App.CHANNEL_1_ID;
import static com.skydrop.jenvi.Applications.App.MAINACTIVITY_FLAG;
import static com.skydrop.jenvi.Applications.App.PlAYERACTIVITY_FLAG;
import static com.skydrop.jenvi.Notifications.NotificationReceiver.ACTION_NAME;
import static com.skydrop.jenvi.Notifications.NotificationReceiver.NEXT_ACTION;
import static com.skydrop.jenvi.Notifications.NotificationReceiver.PLAY_ACTION;
import static com.skydrop.jenvi.Notifications.NotificationReceiver.PREV_ACTION;

public class song_singleton extends AppCompatActivity {

    //region Final Variables
    private final SongsList_singleton singleton = SongsList_singleton.getInstance();
    //endregion

    //region Private Variables
    private MediaPlayer mediaPlayer;
    private int flag;
    private SongModel model;
    private Context app_context;
    //endregion

    //region Public Variables
    public int position;
    public TextView Player_SongName;
    public ImageButton Player_Play;
    public ImageView Player_albumart;
    public TextView played_duration;
    public TextView total_duration;
    public SeekBar seekBar;
    public Handler handler;

    public TextView Main_SongName;
    public ImageView Main_albumart;
    public ImageButton Main_Play;
    //endregion

    //region Static Variables
    @SuppressLint("StaticFieldLeak")
    private static final song_singleton instance = new song_singleton();
    //endregion

    public void play(final Context context, int position) {
        this.position = position;
        this.app_context = context;
        model = singleton.getSingslist(position);

        //region New Song Creating
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        mediaPlayer = MediaPlayer.create(app_context, Uri.parse(model.getPath()));

        try{
            playsong();
        }
        catch (Exception e){
            next(context);
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next(context);
            }
        });
        //endregion

        showdata();
    }


    //TODO: Custom Notification
    public void shownotification(){

        Intent plyayintent = new Intent(app_context,NotificationReceiver.class);
        plyayintent.putExtra(ACTION_NAME,PLAY_ACTION);

        Intent previntent = new Intent(app_context,NotificationReceiver.class);
        previntent.putExtra(ACTION_NAME,PREV_ACTION);
//        PendingIntent prevaction = PendingIntent.getBroadcast(app_context,0,previntent,PendingIntent.FLAG_UPDATE_CURRENT);


//        PendingIntent playaction = PendingIntent.getBroadcast(app_context,0,plyayintent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextintent = new Intent(app_context, NotificationReceiver.class);
        nextintent.putExtra(ACTION_NAME,NEXT_ACTION);
//        PendingIntent nextaction = PendingIntent.getBroadcast(app_context,0,nextintent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent openactivity = new Intent(app_context, Playeractivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(app_context, 0, openactivity, 0);


        int resourse = mediaPlayer.isPlaying()?R.drawable.ic_baseline_pause_24: R.drawable.ic_baseline_play_arrow_24;

        Notification notification = new NotificationCompat.Builder(app_context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle(model.getTitle())
                .setContentText(model.getAlbum())
                .setLargeIcon(model.getAlbumart())
                .addAction(R.drawable.ic_previous, "Previous", PendingIntent.getBroadcast(app_context,0,previntent,PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(resourse, "Pause", PendingIntent.getBroadcast(app_context,0,plyayintent,PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_next, "Next", PendingIntent.getBroadcast(app_context,0,nextintent,PendingIntent.FLAG_UPDATE_CURRENT))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .setContentIntent(pendingIntent)
                .setOngoing(mediaPlayer.isPlaying())
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(app_context);
        notificationManager.notify(2, notification);
    }

    public void seek_Bar() {
        int duration = mediaPlayer.getDuration();

        total_duration.setText(formattedTime(duration));
        seekBar.setMax(duration);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer != null && b) {
                    mediaPlayer.seekTo(i * 1000);
                    mediaPlayer.seekTo(seekBar.getProgress());
                    Player_Play.setImageResource(R.drawable.ic_baseline_pause_24);
                    playsong();
                }
            }

            //region Unused Overrides
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            //endregion
        });

        song_singleton.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPos = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPos);
                    played_duration.setText(formattedTime(currentPos));
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void next(Context context) {
        if (position >= singleton.getsize() - 1) {
            position = -1;
        }
        play(context,++position);
    }

    public void prev(Context context) {
        if (position <= 0) {
            position = singleton.getsize();
        }
        play(context,--position);
    }

    public void playsong(){
        mediaPlayer.start();
        shownotification();
    }

    public void pausesong(){
        mediaPlayer.pause();
        shownotification();
    }

    public void showdata(){
        if(model!=null) {
            if (flag == PlAYERACTIVITY_FLAG) {
                Player_SongName.setText(model.getTitle());
                Player_Play.setImageResource(R.drawable.ic_baseline_pause_24);
                Player_albumart.setImageBitmap(model.getAlbumart());
                seek_Bar();
            } else if (flag == MAINACTIVITY_FLAG) {
                Main_SongName.setText(model.getTitle());
                Main_albumart.setImageBitmap(model.getAlbumart());
                Main_Play.setImageResource(R.drawable.ic_baseline_pause_24);
            }
        }
    }

    private String formattedTime(int currentPos) {
        String total1;
        String total2;
        currentPos /= 1000;
        String seconds = String.valueOf(currentPos % 60);
        String minutes = String.valueOf(currentPos / 60);
        total1 = minutes + ":" + "0" + seconds;
        total2 = minutes + ":" + seconds;
        if (seconds.length() == 1)
            return total1;
        else
            return total2;
    }

    //region Getters and Setters
    public static song_singleton getInstance() {
        return instance;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public boolean getIsPlaying(){return mediaPlayer.isPlaying();}
    //endregion
}
