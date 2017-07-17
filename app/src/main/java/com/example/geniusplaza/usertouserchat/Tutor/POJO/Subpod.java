package com.example.geniusplaza.usertouserchat.Tutor.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by geniusplaza on 7/17/17.
 */

public class Subpod {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("plaintext")
    @Expose
    private String plaintext;
    @SerializedName("imagesource")
    @Expose
    private String imagesource;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getImagesource() {
        return imagesource;
    }

    public void setImagesource(String imagesource) {
        this.imagesource = imagesource;
    }
}