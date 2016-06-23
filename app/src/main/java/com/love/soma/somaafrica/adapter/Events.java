package com.love.soma.somaafrica.adapter;

/**
 * Created by Neli'e on 07/02/2016.
 */
public class Events {

    private String title, date;
   // private int photoId;

    public Events() {
    }

    public Events(String title, String date) {
        this.title = title;
        this.date = date;
        //this.photoId = photoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

}
