package com.skydrop.jenvi;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playeractivity);
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
    }

}

