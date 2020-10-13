package com.skydrop.jenvi.models;

import android.graphics.Bitmap;

public class SongModel {
    private String path;
    private String title;
    private String artist;
    private String album;
    private String duration;
    private int albumart;
    private Bitmap Albumartbitmap;
    public SongModel(String path, String title, String artist, String album, String duration, int albumart,Bitmap albumartbitmap) {
        this.path = path;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.albumart = albumart;
        this.Albumartbitmap = albumartbitmap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getAlbumart() {
        return albumart;
    }

    public void setAlbumart(int albumart) {
        this.albumart = albumart;
    }

    public Bitmap getAlbumartbitmap() {
        return Albumartbitmap;
    }

    public void setAlbumartbitmap(Bitmap albumartbitmap) {
        Albumartbitmap = albumartbitmap;
    }
}
