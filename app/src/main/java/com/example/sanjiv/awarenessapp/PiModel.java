package com.example.sanjiv.awarenessapp;

public class PiModel {
    String apName, apPassword, userid, key;
    int newdata;

    public PiModel() {

    }

    public PiModel(String naam, String password, int newData, String userId, String key) {
        this.apName = naam;
        this.apPassword = password;
        this.newdata = newData;
        this.userid = userId;
        this.key = key;
    }

    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    public String getApPassword() {
        return apPassword;
    }

    public void setApPassword(String apPassword) {
        this.apPassword = apPassword;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getNewdata() {
        return newdata;
    }

    public void setNewdata(int newdata) {
        this.newdata = newdata;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
