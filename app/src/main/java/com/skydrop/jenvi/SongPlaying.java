package com.skydrop.jenvi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
        setmappings();
        currentsong = CurrentPlayingsongSingleton.getInstance();
        setdata();
    }

    private void setdata() {
        songtitleview.setText(currentsong.getTitle());
        albumartview.setImageResource(currentsong.getAlbumart());
        if(currentsong.isIsplaying()){
            playpauseicon.setImageResource(R.drawable.pause);
        }
        playpauseicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentsong.isIsplaying()){
                    currentsong.pause();
                    System.out.println("pasue presed in songplaying");
                    playpauseicon.setImageResource(R.drawable.play);
                }
                else{
                    currentsong.play();
                    playpauseicon.setImageResource(R.drawable.pause);
                }
            }
        });
    }

    private void setmappings() {
        songtitleview = findViewById(R.id.playingsong_title);
        playpauseicon = findViewById(R.id.playingsong_playpauseicon);
        albumartview = findViewById(R.id.playingsong_albumart);
    }
}