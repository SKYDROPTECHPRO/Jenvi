package com.skydrop.jenvi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SongPlaying extends AppCompatActivity {

    private TextView songtitleview;
    private ImageView playpauseicon;
    private ImageView albumartview;
    CurrentPlayingsongSingleton currentsong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playeractivity);
        currentsong = CurrentPlayingsongSingleton.getInstance();
        setmappings();
        setdata();
    }

    private void setdata() {
        currentsong.setSongplayingalbumart(albumartview);
        currentsong.setSongplayingplaypause(playpauseicon);
        currentsong.setSongplayingtitle(songtitleview);
        currentsong.loadsong();
    }

    private void setmappings() {
        songtitleview = findViewById(R.id.playingsong_title);
        playpauseicon = findViewById(R.id.playingsong_playpauseicon);
        albumartview = findViewById(R.id.playingsong_albumart);
    }
}