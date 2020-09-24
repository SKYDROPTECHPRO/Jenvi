package com.skydrop.jenvi;

public class SongModel {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    String album;
    String title;
    String duration;
    String artist;
    String path;

    @Override
    public String toString() {
        return "SongModel{" +
                "album='" + album + '\'' +
                ", title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                ", artist='" + artist + '\'' +
                ", path='" + path + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
/* public SongModel(String album, String title, String duration, String artist, String path) {
        this.album = album;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.path = path;
    }*/

    public SongModel() {
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
