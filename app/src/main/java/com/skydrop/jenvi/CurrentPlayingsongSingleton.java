package com.skydrop.jenvi;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrentPlayingsongSingleton {
    private static CurrentPlayingsongSingleton Instance=new CurrentPlayingsongSingleton();
    private CurrentPlayingsongSingleton() {}

    public static CurrentPlayingsongSingleton getInstance() {
        return Instance;
    }

    private ImageView mainactivityalbumart;
    private TextView mainactivitytitle;
    private ImageView mainactivityplaypause;
    private ImageView songplayingalbumart;
    private ImageView songplayingplaypause;
    private TextView songplayingtitle;

    private SongModel currentsong;
    private boolean isplaying = false;
    private MediaPlayer player;

    public void setMainactivityalbumart(ImageView mainactivityalbumart) {
        this.mainactivityalbumart = mainactivityalbumart;
    }

    public void setMainactivitytitle(TextView mainactivitytitle) {
        this.mainactivitytitle = mainactivitytitle;
    }

    public void setMainactivityplaypause(ImageView mainactivityplaypause) {
        this.mainactivityplaypause = mainactivityplaypause;
        mainactivityplaypause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentsong != null){
                    if(isplaying){
                        pause();
                    }
                    else {
                        play();
                    }
                }
            }
        });
    }

    public void setSongplayingalbumart(ImageView songplayingalbumart) {
        this.songplayingalbumart = songplayingalbumart;
    }

    public void setSongplayingplaypause(ImageView songplayingplaypause) {
        this.songplayingplaypause = songplayingplaypause;
    }

    public void setSongplayingtitle(TextView songplayingtitle) {
        this.songplayingtitle = songplayingtitle;
    }


    public void setCurrentsong(SongModel currentsong) {
        this.currentsong = currentsong;
        loadsong();
    }

    public void loadsong() {
        mainactivityalbumart.setImageResource(currentsong.getAlbumart());
        mainactivitytitle.setText(currentsong.getTitle());
        songplayingalbumart.setImageResource(currentsong.getAlbumart());
        songplayingtitle.setText(currentsong.getTitle());
        if(!isplaying){
            loadmedia();
            play();
        }
    }

    private void loadmedia() {

    }

    private void pause(){
        isplaying = false;
        setplaypauseicons();
    }

    private void play(){
        isplaying = true;
        setplaypauseicons();
    }

    private void setplaypauseicons(){
        if(isplaying){
            mainactivityplaypause.setImageResource(R.drawable.pause);
            songplayingplaypause.setImageResource(R.drawable.pause);
        }
        else{
            mainactivityplaypause.setImageResource(R.drawable.play);
            songplayingplaypause.setImageResource(R.drawable.play);
        }
    }
}
