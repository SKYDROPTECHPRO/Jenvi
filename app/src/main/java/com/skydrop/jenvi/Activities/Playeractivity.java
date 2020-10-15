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

import static com.skydrop.jenvi.Applications.App.MAINACTIVITY_FLAG;
import static com.skydrop.jenvi.Applications.App.PlAYERACTIVITY_FLAG;

public class Playeractivity extends AppCompatActivity {
    //region Final Variables
    private final SongsList_singleton singleton = SongsList_singleton.getInstance();
    private final song_singleton song = song_singleton.getInstance();

    private final OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == play) {
                song.player();
                return;
            } else if (view == back) {
                onBackPressed();
                return;
            } else if (view == next) {
                song.next(getApplicationContext());
            } else if (view == prev) {
                song.prev(getApplicationContext());
            }
            song.seek_Bar(seekBar, played_duration, total_duration, handler);
        }
    };
    //endregion

    //region Private Variables
    private ImageButton back;
    private ImageButton play;
    private ImageButton prev;
    private ImageButton next;

    private TextView songName;
    private TextView played_duration;
    private TextView total_duration;

    private SeekBar seekBar;

    private Handler handler;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playeractivity);
        song.setFlag(PlAYERACTIVITY_FLAG);
        setMappings();

        song.play = play;
        song.SongName = songName;

        handler = new Handler();

        song.seek_Bar(seekBar, played_duration, total_duration, handler);
        songName.setText(singleton.getSingslist(song.position).getTitle());

        //region Listeners
        play.setOnClickListener(listener);
        back.setOnClickListener(listener);
        next.setOnClickListener(listener);
        prev.setOnClickListener(listener);
        //endregion
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        song.setFlag(MAINACTIVITY_FLAG);
    }

    private void setMappings() {
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        play = findViewById(R.id.play);
        back = findViewById(R.id.backbutton);

        songName = findViewById(R.id.name);

        played_duration = findViewById(R.id.playduration);
        total_duration = findViewById(R.id.totalduration);

        seekBar = findViewById(R.id.seekBar);
    }
}

