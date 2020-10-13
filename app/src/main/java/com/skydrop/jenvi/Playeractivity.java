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
    ImageButton prev;
    ImageButton next;
    TextView songName;
    SeekBar seekBar;
    TextView played_duration;
    TextView total_duration;
    Handler handler;

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("title in onstart:"+MainActivity.position);
        songName.setText(singleton.getSingslist(MainActivity.position).getTitle());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playeractivity);
        handler = new Handler();
        setMappings();
        song.play = play;
        song.SongName = songName;

        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                song.player(getApplicationContext());
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        song.seek_Bar(seekBar,played_duration,total_duration,handler);
        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                song.next(getApplicationContext());
                song.seek_Bar(seekBar,played_duration,total_duration,handler);
            }
        });
        prev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                song.prev(getApplicationContext());
                song.seek_Bar(seekBar,played_duration,total_duration,handler);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setMappings() {
        next = (ImageButton)findViewById(R.id.next);
        prev = (ImageButton)findViewById(R.id.prev);
        back = (ImageButton)findViewById(R.id.backbutton);
        play = (ImageButton)findViewById(R.id.play);
        songName = (TextView)findViewById(R.id.name);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        played_duration = (TextView)findViewById(R.id.playduration);
        total_duration = (TextView)findViewById(R.id.totalduration);
    }

}

