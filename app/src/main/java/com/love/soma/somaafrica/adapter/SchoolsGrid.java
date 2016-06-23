package com.love.soma.somaafrica.adapter;

/**
 * Created by Neli'e on 07/02/2016.
 */
public class SchoolsGrid {

    private String school;
    private int photoId;

    public SchoolsGrid() {
    }

    public SchoolsGrid(String school, int photoId) {
        this.school = school;
        this.photoId = photoId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String name) {
        this.school = name;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }


}
