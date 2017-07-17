package com.example.geniusplaza.usertouserchat.Tutor.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by geniusplaza on 7/17/17.
 */

public class Query {
    @SerializedName("queryresult")
    @Expose
    private Queryresult queryresult;

    public Queryresult getQueryresult() {
        return queryresult;
    }

    public void setQueryresult(Queryresult queryresult) {
        this.queryresult = queryresult;
    }
}