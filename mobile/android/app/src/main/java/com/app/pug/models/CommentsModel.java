package com.app.pug.models;

/**
 * Created by MATIVO-PC on 3/9/2015, 12:43 PM
 * Project:  PUG
 * Package Name: com.app.pug.models
 */
public class CommentsModel {
    private String name, message;
    private int imageRes;

    public CommentsModel(String name, String message, int imageRes) {
        setName(name);
        setMessage(message);
        setImageRes(imageRes);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}
