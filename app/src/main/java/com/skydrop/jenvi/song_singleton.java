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
    TextView SongName;
    TextView txt;
    ImageButton play;
    public static song_singleton instance = new song_singleton();
    void play(final Context context) {
            position = MainActivity.position;
            Toast.makeText(context,"songindex"+position,Toast.LENGTH_SHORT).show();
            model = singleton.getSingslist(position);
            txt.setText(singleton.getSingslist(position).getTitle());
            if(mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(model.getPath()));
                mediaPlayer.start();
//                SongName.setText(singleton.getSingslist(position).getTitle());

            }
//            if (!mediaPlayer.isPlaying()){
//                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(model.getPath()));
//                mediaPlayer.seekTo(pausepos);
//                mediaPlayer.start();
////                SongName.setText(singleton.getSingslist(position).getTitle());
//            }
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(model.getPath()));
                mediaPlayer.start();
//                SongName.setText(singleton.getSingslist(position).getTitle());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(singleton.getSingslist(++position).getPath()));
                        mediaPlayer.start();
                    }
                });
            }
//             if(mediaPlayer.getCurrentPosition() == mediaPlayer.getDuration()) {

//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//
//                        mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(singleton.getSingslist(++position).getPath()));
//                        mediaPlayer.start();
//                    }
//                });
//              }

    }

    void player(final Context context) {
        Toast.makeText(context,"songindex"+position,Toast.LENGTH_SHORT).show();
        model = singleton.getSingslist(position);
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(model.getPath()));
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer.seekTo(pausepos);
                    mediaPlayer.start();
                    SongName.setText(singleton.getSingslist(position).getTitle());

                }
                else if(mediaPlayer.isPlaying()){
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    mediaPlayer.pause();
                    pausepos = mediaPlayer.getCurrentPosition();
                }
//                 if(mediaPlayer.getCurrentPosition() == mediaPlayer.getDuration()) {

//                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mediaPlayer) {
//
//                            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), Uri.parse(singleton.getSingslist(++position).getPath()));
//                            mediaPlayer.start();
//                        }
//                    });
//                }
        txt.setText(singleton.getSingslist(position).getTitle());

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

    void next(Context applicationContext){
        System.out.print("play value"+play);
        play.setImageResource(R.drawable.ic_baseline_pause_24);
        mediaPlayer.stop();
        if(position>=singleton.songslist.size()-1) {
            position = -1;
        }
        ++position;
       play(applicationContext,position);
        txt.setText(singleton.getSingslist(position).getTitle());
        System.out.println("txt value"+txt);
        MainActivity.position = position;
    }

    public void prev(Context applicationContext) {
        play.setImageResource(R.drawable.ic_baseline_pause_24);
        mediaPlayer.stop();
        if(position>1) {
            position--;
        }
           play(applicationContext,position);
        txt.setText(singleton.getSingslist(position).getTitle());
        System.out.println("txt value"+txt);
        MainActivity.position = position;
    }
    void play(Context applicationContext,int pos){
        mediaPlayer = MediaPlayer.create(applicationContext, Uri.parse(singleton.getSingslist(pos).getPath()));
        SongName.setText(singleton.getSingslist(position).getTitle());
        mediaPlayer.start();
    }
}



