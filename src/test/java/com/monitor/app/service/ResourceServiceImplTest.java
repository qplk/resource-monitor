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

import java.util.LinkedList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceServiceImplTest {

    @Autowired
    ResourceService resourceService;

    @Autowired
    ResourceRepository resourceRepository;

    @Before
    public void createTestData() {
        Resource project = new Resource("project", "", new LinkedList<>());
        resourceRepository.save(project);
    }

    @Test
    public void takeFreeResourceTest() {
        resourceService.takeResource(new User("First", "User"));
        Assert.assertEquals("First User", resourceService.getCurrentUser());
    }
}
