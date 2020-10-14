package com.skydrop.jenvi.singleton;

import com.skydrop.jenvi.models.SongModel;

import java.util.ArrayList;

public class SongsList_singleton {
    private ArrayList<SongModel> songslist;
    private static SongsList_singleton Instance = new SongsList_singleton();

    public static SongsList_singleton getInstance(){
        return Instance;
    }

    private SongsList_singleton() {
        songslist = new ArrayList<>();
    }

    public SongModel getSingslist(int postion) {
        return songslist.get(postion);
    }


    public void setSongslist(SongModel songitem) {
        songslist.add(songitem);
    }

    public int getsize(){
        return songslist.size();
    }

}
