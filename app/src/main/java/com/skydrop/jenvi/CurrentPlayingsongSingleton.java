package com.skydrop.jenvi;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class CurrentPlayingsongSingleton {
    private static CurrentPlayingsongSingleton Instance=new CurrentPlayingsongSingleton();
    Context context;
    private CurrentPlayingsongSingleton() {
    }

    public static CurrentPlayingsongSingleton getInstance() {
        return Instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private SongModel currentsong;
    private int position;
    private boolean isplaying = false;
    private MediaPlayer player;

    private String title;
    private String duration;
    private int albumart;
    private SongsListSingleton songslist = SongsListSingleton.getInstance();


    public void setCurrentsong(int pos) {
        position = pos;
        this.currentsong = songslist.get(pos);
        loadmedia(context);
    }


    private void loadmedia(Context cont) {
        System.out.println("load midea:"+currentsong.getPath());
        try {
            if(player != null){
                player.stop();
            }
            player = MediaPlayer.create(cont,Uri.parse(currentsong.getPath()));
            this.title = currentsong.getTitle();
            this.albumart=currentsong.getAlbumart();
            this.duration = currentsong.getDuration();
            play();
        }
        catch (Exception e){
            nextsong();
        }
    }

    public void pause(){
        isplaying = false;
        player.pause();
    }

    public void play(){
        isplaying = true;
        player.start();
    }

    public void nextsong(){
        position++;
        position = position % songslist.size();
        setCurrentsong(position);
    }

    public void prevsong(){
        position--;
        if(position<0){
            position = songslist.size()-1;
        }
        setCurrentsong(position);
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
