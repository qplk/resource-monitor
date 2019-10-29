package com.monitor.app.entity;


import lombok.Data;

import javax.persistence.*;

@Table
@Entity
@Data
public class User {

    public User(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

}
