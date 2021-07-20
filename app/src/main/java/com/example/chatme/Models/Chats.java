package com.example.chatme.Models;

public class Chats {
    private String uid;
    private String message;
    private Long timestamp;

    public Chats(){}

    public Chats(String uid, String message) {
        this.uid = uid;
        this.message = message;
    }

    public Chats(String uid, String message, Long timestamp) {
        this.uid = uid;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
