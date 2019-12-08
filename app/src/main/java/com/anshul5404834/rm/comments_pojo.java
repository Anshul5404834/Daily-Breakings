package com.anshul5404834.rm;

public class comments_pojo {
    String comments;
    String username;

    public comments_pojo() {
    }

    public comments_pojo(String comments, String username) {
        this.comments = comments;
        this.username = username;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
