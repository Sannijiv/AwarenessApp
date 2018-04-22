package com.example.sanjiv.awarenessapp;

public class UserModel {

    private String userRole;

    public UserModel(){

    }

    public UserModel(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
