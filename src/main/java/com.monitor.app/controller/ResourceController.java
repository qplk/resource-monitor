package com.monitor.app.controller;

import com.monitor.app.entity.User;
import com.monitor.app.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    //todo: add projectName in path variable (it should be determined on project icon in UI)
    @PostMapping(path = "/takeResource")
    public void takeResource(@RequestBody User user) {
        log.debug("Try to take resource");

        if (Objects.nonNull(user)) {
            resourceService.takeResource(user);
        }
    }

    //todo: add projectName in path variable (it should be determined on project icon in UI)
    @PostMapping(path = "/releaseResource")
    public void releaseResource(@RequestBody User user) {
        log.debug("Try to take resource");

        if (Objects.nonNull(user)) {
            resourceService.releaseResource(user);
        }
    }

    @GetMapping(path = "/getQueue")
    public List<String> getQueue() {
        return resourceService.getQueue();
    }

    @GetMapping(path = "/getCurrentUser")
    public String getCurrentUser() {
        return resourceService.getCurrentUser();
    }
}
