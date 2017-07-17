package com.example.geniusplaza.usertouserchat.Tutor.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by geniusplaza on 7/17/17.
 */

public class Pod {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("scanner")
    @Expose
    private String scanner;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("numsubpods")
    @Expose
    private Integer numsubpods;
    @SerializedName("subpods")
    @Expose
    private List<Subpod> subpods = null;
    @SerializedName("primary")
    @Expose
    private Boolean primary;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScanner() {
        return scanner;
    }

    public void setScanner(String scanner) {
        this.scanner = scanner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getNumsubpods() {
        return numsubpods;
    }

    public void setNumsubpods(Integer numsubpods) {
        this.numsubpods = numsubpods;
    }

    public List<Subpod> getSubpods() {
        return subpods;
    }

    public void setSubpods(List<Subpod> subpods) {
        this.subpods = subpods;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
}