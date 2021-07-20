package com.example.chatme.Models;

public class Users {

    private String username;
    private String name;
    private String email;
    private String password;
    private String photoUrl;
    private String userId;
    private String lastMessage;
    private int profilePic;



    public Users(){}

    public Users(String username, String name, String email, String password){
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public Users(String username, String name, String email, String password, String photoUrl, String userId, String lastMessage) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photoUrl = photoUrl;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public Users(String name, int profilePic){
        this.name = name;
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhotoUrl() { return photoUrl; }

    public String getLastMessage() { return lastMessage; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }
}
