package com.love.soma.somaafrica.adapter;

/**
 * Created by Neli'e on 07/02/2016.
 */
public class Schools {

    private String title, genre;
    private int photoId;

    public Schools() {
    }

    public Schools(String title, String genre, int photoId) {
        this.title = title;
        this.genre = genre;
        this.photoId = photoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
