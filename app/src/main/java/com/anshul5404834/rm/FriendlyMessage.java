package com.anshul5404834.rm;

public class FriendlyMessage {

    private String text;
    private String name;
    private String photoUrl;
    private String time;
    private String uid;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String text, String name, String photoUrl, String time, String uid) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.time = time;
        this.uid = uid;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}