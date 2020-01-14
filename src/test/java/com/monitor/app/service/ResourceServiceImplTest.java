package com.monitor.app.service;

import com.monitor.app.entity.Resource;
import com.monitor.app.entity.User;
import com.monitor.app.repository.ResourceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.LinkedList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceServiceImplTest {

    @Autowired
    ResourceService resourceService;

    @Autowired
    ResourceRepository resourceRepository;

    @Before
    public void cleanup() {
        resourceRepository.deleteAll();
        resourceRepository.save(new Resource("project", "", new LinkedList<>()));
    }

    @Test
    public void takeFreeResourceTest() {
        resourceService.takeResource(new User("First", "User"));

        Resource project = resourceRepository.findResourceByProjectName("project");

        Assert.assertEquals("First User", project.getCurrentUser());
        Assert.assertEquals(Collections.emptyList(), project.getUsersQueue());
    }

    @Test
    public void takeBusyResourceTest() {
        resourceService.takeResource(new User("First", "User"));
        resourceService.takeResource(new User("Second", "User"));

        Resource project = resourceRepository.findResourceByProjectName("project");
        LinkedList<String> usersQueue = project.getUsersQueue();

        Assert.assertEquals("First User", project.getCurrentUser());
        Assert.assertEquals(1, usersQueue.size());
        Assert.assertTrue(usersQueue.contains("Second User"));
        Assert.assertFalse(usersQueue.contains("First User"));
    }

    @Test
    public void releaseResourceTest() {
        resourceService.takeResource(new User("First", "User"));
        resourceService.takeResource(new User("Second", "User"));
        resourceService.takeResource(new User("Third", "User"));
        resourceService.takeResource(new User("Fourth", "User"));
        resourceService.releaseResource(new User("First", "User"));

        Resource project = resourceRepository.findResourceByProjectName("project");
        LinkedList<String> usersQueue = project.getUsersQueue();

        Assert.assertEquals("Second User", project.getCurrentUser());
        Assert.assertEquals(2, usersQueue.size());
        Assert.assertFalse(usersQueue.contains("Second User"));
        Assert.assertTrue(usersQueue.contains("Third User"));
        Assert.assertTrue(usersQueue.contains("Fourth User"));

        resourceService.releaseResource(new User("Fourth", "User"));
        project = resourceRepository.findResourceByProjectName("project");
        usersQueue = project.getUsersQueue();

        Assert.assertEquals(1, usersQueue.size());
        Assert.assertTrue(usersQueue.contains("Third User"));
    }

    @Test
    public void takeResourceTwoTimesInRaw(){
        resourceService.takeResource(new User("First", "User"));
        resourceService.takeResource(new User("Second", "User"));
        resourceService.takeResource(new User("Third", "User"));

        resourceService.takeResource(new User("Third", "User"));
        resourceService.takeResource(new User("First", "User"));

        Resource project = resourceRepository.findResourceByProjectName("project");
        LinkedList<String> usersQueue = project.getUsersQueue();

        Assert.assertEquals(2, usersQueue.size());
        Assert.assertTrue(usersQueue.contains("Second User"));
        Assert.assertTrue(usersQueue.contains("Third User"));
    }
}
