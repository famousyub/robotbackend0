package com.example.polls.controller;

import com.example.polls.model.Excution;
import com.example.polls.model.Planifications;
import com.example.polls.model.Robots;
import com.example.polls.model.User;

public class RobotDetails {

    private Robots robots ;

    private Excution exception ;

    private Planifications planifications ;

    private UserOrAdmin userOrAdmin ;

    public RobotDetails() {
    }

    public Robots getRobots() {
        return robots;
    }

    public void setRobots(Robots robots) {
        this.robots = robots;
    }


    public Excution getException() {
        return exception;
    }

    public void setException(Excution exception) {
        this.exception = exception;
    }

    public Planifications getPlanifications() {
        return planifications;
    }

    public void setPlanifications(Planifications planifications) {
        this.planifications = planifications;
    }

    public UserOrAdmin getUserOrAdmin() {
        return userOrAdmin;
    }

    public void setUserOrAdmin(UserOrAdmin userOrAdmin) {
        this.userOrAdmin = userOrAdmin;
    }
}
