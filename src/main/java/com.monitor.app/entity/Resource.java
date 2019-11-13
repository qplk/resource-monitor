package com.monitor.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;

@Data
@Entity
@AllArgsConstructor
public class Resource {

    public Resource(String projectName) {
        this.projectName = projectName;
    }

    public Resource(String projectName, String currentUser, LinkedList<String> queue) {
        this.projectName = projectName;
        this.currentUser = currentUser;
        this.usersQueue = queue;
    }

    public Resource() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String projectName;

    private String currentUser;

    private LinkedList<String> usersQueue;
}
