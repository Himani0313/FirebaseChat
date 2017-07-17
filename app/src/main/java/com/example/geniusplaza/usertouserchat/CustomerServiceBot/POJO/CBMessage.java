package com.example.geniusplaza.usertouserchat.CustomerServiceBot.POJO;

/**
 * Created by geniusplaza on 7/17/17.
 */

import java.io.Serializable;

public class CBMessage implements Serializable {
    String id, message;


    public CBMessage() {
    }

    public CBMessage(String id, String message, String createdAt) {
        this.id = id;
        this.message = message;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}