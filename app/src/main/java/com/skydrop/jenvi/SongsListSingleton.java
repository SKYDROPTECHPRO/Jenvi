package com.skydrop.jenvi;

import java.util.ArrayList;

public class SongsListSingleton {
    private static SongsListSingleton Instance = new SongsListSingleton();
    private SongsListSingleton() {
        songslist = new ArrayList<>();
    }

    public static SongsListSingleton getInstance() {return Instance;}

    private ArrayList<SongModel> songslist;

    public SongModel get(int position) {
        return songslist.get(position);
    }

    public void add(SongModel songslist) {
        this.songslist.add(songslist);
    }

    public int size(){
        return songslist.size();
    }
}
