package com.monitor.app.service;

import com.monitor.app.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResourceService {

    void takeResource(User user);

    void releaseResource(User user);

    List<String> getQueue();

    String getCurrentUser();
}
