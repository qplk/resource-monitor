package com.monitor.app.entity;

import lombok.Data;

@Data
public class User {

    private String firstName;

    private String secondName;

    public User(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

}
