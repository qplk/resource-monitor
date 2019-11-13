package com.monitor.app;

import com.monitor.app.entity.Resource;
import com.monitor.app.repository.ResourceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.LinkedList;

@SpringBootApplication
public class MonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }


    @Bean
    CommandLineRunner init(ResourceRepository resourceRepository) {
        return args -> {
            Resource project = new Resource("project", "", new LinkedList<>());
            resourceRepository.save(project);
            resourceRepository.findAll().forEach(System.out::println);
        };
    }
}
