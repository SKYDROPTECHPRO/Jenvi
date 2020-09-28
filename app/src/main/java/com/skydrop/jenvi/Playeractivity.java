package com.skydrop.jenvi;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Playeractivity extends AppCompatActivity {
    SongsList_singleton singleton = SongsList_singleton.getInstance();
    song_singleton song = song_singleton.instance;
    ImageButton back;
    ImageButton play;
    ImageButton pause;
    TextView songName;
    SeekBar seekBar;
    TextView played_duration;
    TextView total_duration;
    Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playeractivity);
        handler = new Handler();
        setMappings();
        songName.setText(singleton.getSingslist(MainActivity.position).getTitle());
        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                song.play(getApplicationContext());
            }
        });
        pause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                song.pause();
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
       song.seek_Bar(seekBar,played_duration,total_duration,handler);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setMappings() {
        back = (ImageButton)findViewById(R.id.backbutton);
        play = (ImageButton)findViewById(R.id.play);
        pause = (ImageButton)findViewById(R.id.pause);
        songName = (TextView)findViewById(R.id.name);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        played_duration = (TextView)findViewById(R.id.playduration);
        total_duration = (TextView)findViewById(R.id.totalduration);
    }

}

