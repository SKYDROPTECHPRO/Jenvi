package com.skydrop.jenvi.singleton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.skydrop.jenvi.R;
import com.skydrop.jenvi.models.SongModel;

import static com.skydrop.jenvi.Applications.App.MAINACTIVITY_FLAG;
import static com.skydrop.jenvi.Applications.App.PlAYERACTIVITY_FLAG;

public class song_singleton extends AppCompatActivity {

    //region Final Variables
    private final SongsList_singleton singleton = SongsList_singleton.getInstance();
    //endregion

    //region Private Variables
    private MediaPlayer mediaPlayer;
    private int flag;
    private SongModel model;
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
        model = singleton.getSingslist(position);

        //region New Song Creating
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        mediaPlayer = MediaPlayer.create(context, Uri.parse(model.getPath()));

        try{
            mediaPlayer.start();
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

        if (flag == PlAYERACTIVITY_FLAG) {
            Player_SongName.setText(model.getTitle());
            Player_Play.setImageResource(R.drawable.ic_baseline_pause_24);
            Player_albumart.setImageBitmap(model.getAlbumart());
            seek_Bar();
        }
        else if (flag == MAINACTIVITY_FLAG) {
            Main_SongName.setText(model.getTitle());
            Main_albumart.setImageBitmap(model.getAlbumart());
            Main_Play.setImageResource(R.drawable.ic_baseline_pause_24);
        }
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
                    mediaPlayer.start();
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

    public void next(Context applicationContext) {
        if (position >= singleton.getsize() - 1) {
            position = -1;
        }
        play(applicationContext, ++position);
    }

    public void prev(Context applicationContext) {
        if (position <= 0) {
            position = singleton.getsize();
        }
        play(applicationContext, --position);
    }

    public void playsong(){
        mediaPlayer.start();
    }

    public void pausesong(){
        mediaPlayer.pause();
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
