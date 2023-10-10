package com.example.polls.controller;

import com.example.polls.model.Robots;

import java.util.List;

public class UserOrAdmin {
    private   Long id ;
    private String username    ;

    List<Robots> robots ;



    private   Boolean   admin;

    public List<Robots> getRobots() {
        return robots;
    }

    public void setRobots(List<Robots> robots) {
        this.robots = robots;
    }

    private String   email    ;

    public UserOrAdmin() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
