package com.skydrop.jenvi.singleton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.skydrop.jenvi.R;
import com.skydrop.jenvi.models.SongModel;

import static com.skydrop.jenvi.Applications.App.MAINACTIVITY_FLAG;
import static com.skydrop.jenvi.Applications.App.PlAYERACTIVITY_FLAG;

public class song_singleton extends AppCompatActivity {
    private SongsList_singleton singleton = SongsList_singleton.getInstance();
    private MediaPlayer mediaPlayer;
    public int position;
    public TextView SongName;
    public TextView txt;
    public ImageButton play;
    private int flag;
    private static song_singleton instance = new song_singleton();
    public void play(final Context context, int position) {
            this.position = position;
        SongModel model = singleton.getSingslist(position);
            if (flag == PlAYERACTIVITY_FLAG){
                SongName.setText(singleton.getSingslist(position).getTitle());
                play.setImageResource(R.drawable.ic_baseline_pause_24);
            }
            else if(flag == MAINACTIVITY_FLAG){
                txt.setText(singleton.getSingslist(position).getTitle());
            }
            if(mediaPlayer!= null){
                mediaPlayer.stop();
            }
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(model.getPath()));
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next(context);
            }
        });
    }

    public void player() {
                if(mediaPlayer.isPlaying()){
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    mediaPlayer.pause();
                }
                else {
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer.start();
                }
    }

public void seek_Bar(final SeekBar seekBar, final TextView played_duration, TextView total_duration, final android.os.Handler handler){

        int duration = mediaPlayer.getDuration();
        total_duration.setText(formattedTime(duration));
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaPlayer != null && b) {
                    mediaPlayer.seekTo(i * 1000);
                    drag(seekBar.getProgress());
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer.start();
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
             if (mediaPlayer != null) {
                 int currentPos = mediaPlayer.getCurrentPosition();
                 seekBar.setProgress(currentPos);
                 played_duration.setText(formattedTime(currentPos));
             }
             handler.postDelayed(this, 1000);
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

    public void next(Context applicationContext){
        if(position>=singleton.getsize()-1) {
            position = -1;
        }
       play(applicationContext,++position);
    }

    public void prev(Context applicationContext) {
        if(position>1) {
            position--;
        }
        play(applicationContext,position);
    }

    public static song_singleton getInstance() {
        return instance;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}



