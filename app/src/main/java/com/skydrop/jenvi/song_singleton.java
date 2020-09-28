package com.skydrop.jenvi;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class song_singleton extends AppCompatActivity {
    SongsList_singleton singleton = SongsList_singleton.getInstance();
    SongModel model;
    MediaPlayer mediaPlayer;
    int pausepos;
    int position;
    public static song_singleton instance = new song_singleton();
    void play(Context context) {
        position = MainActivity.position;
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
        }
        else if(mediaPlayer.isPlaying()){
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
    void seek_Bar(final SeekBar seekBar, final TextView played_duration, TextView total_duration, final android.os.Handler handler){
        int duration = Integer.parseInt(singleton.getSingslist(position).getDuration());
        //total_duration.setText(Integer.toString(duration));
        total_duration.setText(formattedTime(duration));
        seekBar.setMax(Integer.parseInt(singleton.getSingslist(position).getDuration()));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if(mediaPlayer != null && b) {
                    mediaPlayer.seekTo(i * 1000);
                   // mediaPlayer.start();
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer != null) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                    mediaPlayer.start();
                    pausepos = mediaPlayer.getCurrentPosition();
                }
            }
        });
     song_singleton.this.runOnUiThread(new Runnable() {
         @Override
         public void run() {
             if(mediaPlayer != null){
                 int currentPos = mediaPlayer.getCurrentPosition() ;
                 seekBar.setProgress(currentPos);
                 played_duration.setText(formattedTime(currentPos));
             }
             handler.postDelayed(this,1000);
         }
     });

    }
    private String formattedTime(int currentPos) {
        String total1;
        String total2;
        currentPos/=1000;
        String seconds = String.valueOf(currentPos % 60);
        String minutes = String.valueOf(currentPos / 60);
        total1 = minutes + ":" + "0" + seconds;
        total2 = minutes + ":" + seconds;
        if(seconds.length() == 1)
            return total1;
        else
            return total2;
    }

}

