package com.jorch.proyecto.firechat.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by JORCH on 18/03/2017.
 */

public class User {
    private String nickname;
    private String email;
    private String password;
    private String date;
    private Boolean status;
    private HashMap<String,Boolean> chats;

    public User() {
    }

    public User(String nickname, String email, String password, String date, Boolean status) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.date = date;
        this.status = status;
    }

    public User(String nickname, String email, String password, String date, HashMap<String, Boolean> chats,Boolean status) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.date = date;
        this.chats = chats;
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, Boolean> getChats() {
        return chats;
    }

    public void setChats(HashMap<String, Boolean> chats) {
        this.chats = chats;
    }
}
