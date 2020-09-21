package com.skydrop.jenvi;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class CurrentPlayingsongSingleton {
    private static CurrentPlayingsongSingleton Instance=new CurrentPlayingsongSingleton();
    Context context;
    private CurrentPlayingsongSingleton() {}

    public static CurrentPlayingsongSingleton getInstance() {
        return Instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private SongModel currentsong;
    private boolean isplaying = false;
    private MediaPlayer player;

    private String title;
    private String duration;
    private int albumart;


    public void setCurrentsong(SongModel currentsong,Context cont) {
        this.currentsong = currentsong;
        loadmedia(cont);
    }


    private void loadmedia(Context cont) {
        System.out.println("load midea:"+currentsong.getPath());
        if(player != null){
            player.stop();

        }
        assert player != null;
        player = MediaPlayer.create(cont,Uri.parse(currentsong.getPath()));
        if(player==null){
            System.out.println("olauer is null");
        }
        else{
            player.start();
        }
        this.title = currentsong.getTitle();
        this.albumart=currentsong.getAlbumart();
        this.duration = currentsong.getDuration();
    }

    public void pause(){
        isplaying = false;
        player.pause();
    }

    public void play(){
        isplaying = true;
        player.start();
    }

    public boolean isIsplaying() {
        return isplaying;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public int getAlbumart() {
        return albumart;
    }
}
