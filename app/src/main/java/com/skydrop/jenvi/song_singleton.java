package com.skydrop.jenvi;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
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

    void play(final Context context, final ImageButton play) {
        Toast.makeText(context,"songindex"+position,Toast.LENGTH_SHORT).show();
        model = singleton.getSingslist(position);
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(model.getPath()));
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer.seekTo(pausepos);
                    mediaPlayer.start();
                }
                else if(mediaPlayer.isPlaying()){
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    mediaPlayer.pause();
                    pausepos = mediaPlayer.getCurrentPosition();
                }
    }
//    void pause(){
//        mediaPlayer.pause();
//        pausepos = mediaPlayer.getCurrentPosition();
//    }
//    void stop(){
//        mediaPlayer.stop();
//        mediaPlayer = null;
//    }
    void seek_Bar(final SeekBar seekBar, final TextView played_duration, TextView total_duration, final android.os.Handler handler){
        int duration = Integer.parseInt(singleton.getSingslist(position).getDuration());
        total_duration.setText(formattedTime(duration));
        seekBar.setMax(Integer.parseInt(singleton.getSingslist(position).getDuration()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaPlayer != null && b) {
                    mediaPlayer.seekTo(i * 1000);
                    drag(seekBar.getProgress());
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
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
    private void drag(int progress) {
           mediaPlayer.seekTo(progress);
           mediaPlayer.start();
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

    void next(Context applicationContext, TextView songName){
        mediaPlayer.stop();
        if(position<singleton.songslist.size()-1) {
            mediaPlayer = MediaPlayer.create(applicationContext, Uri.parse(singleton.getSingslist(++position).getPath()));
            songName.setText(singleton.getSingslist(position).getTitle());
            mediaPlayer.start();
        }
    }



    public void prev(Context applicationContext, TextView songName) {
        mediaPlayer.stop();
        if(position>1) {
            position--;
            mediaPlayer = MediaPlayer.create(applicationContext, Uri.parse(singleton.getSingslist(position).getPath()));
            songName.setText(singleton.getSingslist(position).getTitle());
            mediaPlayer.start();
        }
    }
}

