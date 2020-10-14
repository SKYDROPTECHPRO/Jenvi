package com.skydrop.jenvi.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.skydrop.jenvi.R;
import com.skydrop.jenvi.singleton.SongsList_singleton;
import com.skydrop.jenvi.singleton.song_singleton;

import static com.skydrop.jenvi.Applications.App.PlAYERACTIVITY_FLAG;

public class Playeractivity extends AppCompatActivity {
    SongsList_singleton singleton = SongsList_singleton.getInstance();
    song_singleton song = song_singleton.instance;
    ImageButton back;
    ImageButton play;
    ImageButton prev;
    ImageButton next;
    TextView songName;
    SeekBar seekBar;
    TextView played_duration;
    TextView total_duration;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playeractivity);
        song.flag = PlAYERACTIVITY_FLAG;
        handler = new Handler();
        setMappings();
        song.play = play;
        song.SongName = songName;
        songName.setText(singleton.getSingslist(song.position).getTitle());
        song.seek_Bar(seekBar,played_duration,total_duration,handler);
        play.setOnClickListener(listener);
        back.setOnClickListener(listener);
        next.setOnClickListener(listener);
        prev.setOnClickListener(listener);
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view == play){
                song.player();
                return;
            }
            else if(view == back){
                onBackPressed();
                return;
            }
            else if(view == next){
                song.next(getApplicationContext());
            }
            else if(view == prev)
            {
                song.prev(getApplicationContext());
            }
            song.seek_Bar(seekBar,played_duration,total_duration,handler);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setMappings() {
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        back = findViewById(R.id.backbutton);
        play = findViewById(R.id.play);
        songName = findViewById(R.id.name);
        seekBar = findViewById(R.id.seekBar);
        played_duration = findViewById(R.id.playduration);
        total_duration = findViewById(R.id.totalduration);
    }

}

