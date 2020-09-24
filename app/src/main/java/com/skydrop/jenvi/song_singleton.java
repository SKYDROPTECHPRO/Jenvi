package com.skydrop.jenvi;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

public class song_singleton {
    SongsList_singleton singleton = SongsList_singleton.getInstance();
    SongModel model;
    MediaPlayer mediaPlayer;
    int pausepos;

    public static song_singleton instance = new song_singleton();
    void play(Context context) {
        int position = MainActivity.position;
        Toast.makeText(context,"songindex"+position,Toast.LENGTH_SHORT).show();
        model = singleton.getSingslist(position);
        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(model.getPath()));
            mediaPlayer.start();
        }
        if (!mediaPlayer.isPlaying()){
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(model.getPath()));
            mediaPlayer.seekTo(pausepos);
            mediaPlayer.start();
        }else if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(model.getPath()));
            mediaPlayer.start();
        }
    }
    void pause(){
        mediaPlayer.pause();
        pausepos = mediaPlayer.getCurrentPosition();
    }
    void stop(){
        mediaPlayer.stop();
        mediaPlayer = null;
    }

}

