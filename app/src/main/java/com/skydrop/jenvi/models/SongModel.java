package com.skydrop.jenvi.models;

import android.graphics.Bitmap;

public class SongModel {
    private String id;
    private String album;
    private String title;
    private String duration;
    private String artist;
    private String path;
    private Bitmap Albumart;


    //region Getters
    public String getId() {
        return id;
    }

    public String getAlbum() {
        return album;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public String getPath() {
        return path;
    }

    public Bitmap getAlbumart() { return Albumart; }

    //endregion


    //region Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setAlbum(String album) { this.album = album; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setPath(String path) {
        this.path = path;
    }
    //endregion


    // TODO: Remove after albumart
    public void setAlbumart(Bitmap albumart) {
        Albumart = albumart;
    }
}
