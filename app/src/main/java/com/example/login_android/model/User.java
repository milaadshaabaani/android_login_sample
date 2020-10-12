package com.example.login_android.model;

public class User {
//    private int id;
//    private String email;
    private String access_token;

//    public void setId(int _id) {
//        this.id = _id;
//    }
//
//    public int getId() {
//        return this.id;
//    }
//
//    public void setEmail(String _email) {
//        this.email = _email;
//    }
//
//    public String getEmail() {
//        return this.email;
//    }

    public void setToken(String _token) {
        this.access_token = _token;
    }

    public String getToken() {
        return this.access_token;
    }

}
