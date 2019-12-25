package com.monitor.app.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)//JUnit annotation. It means tests will run against spring application context.
@SpringBootTest
public class ApplicationClassTest {

    //Empty Test
    //Fails if only Spring Application can not load context
    @Test
    public void contextLoads() {

    }
}

