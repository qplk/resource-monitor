package com.monitor.app.service;

import com.monitor.app.entity.Resource;
import com.monitor.app.entity.User;
import com.monitor.app.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.monitor.app.utils.UserUtils.concatFirstAndSecondNames;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    private static final String PROJECT_NOT_FOUND_MESSAGE = "No projects were found";

    @Autowired
    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public void takeResource(User user) {

        //todo: make project configurable
        Resource project = resourceRepository.findAll().stream().findFirst().orElse(null);
        if (project == null) {
            log.error(PROJECT_NOT_FOUND_MESSAGE);
            throw new ObjectNotFoundException(PROJECT_NOT_FOUND_MESSAGE, "project");
        }
        Queue<String> usersQueue = project.getUsersQueue();
        log.debug("Found project: {}", project);

        //todo: добавить проверку на добавление в очередь, не подряд

        if (StringUtils.isEmpty(project.getCurrentUser())) {

            if (usersQueue.isEmpty()) {
                updateCurrentUser(project, user);
                return;
            }

            if (usersQueue.peek().equals(concatFirstAndSecondNames(user))) {
                moveUserFromQueueToResource(project, user, usersQueue);
                return;

            } else {
                addUserToQueue(project, user, usersQueue);
                return;
            }
        }
        addUserToQueue(project, user, usersQueue);
    }

    @Override
    public void releaseResource(User user) {
        Resource project = resourceRepository.findAll().stream().findFirst().orElse(null);
        if (project == null) {
            log.error(PROJECT_NOT_FOUND_MESSAGE);
            throw new ObjectNotFoundException(PROJECT_NOT_FOUND_MESSAGE, "project");
        }
        LinkedList<String> usersQueue = project.getUsersQueue();

        if (project.getCurrentUser().equals(concatFirstAndSecondNames(user))) {
            project.setCurrentUser(usersQueue.poll());
            resourceRepository.save(project);
            return;
        }

        if (usersQueue.contains(concatFirstAndSecondNames(user))) {
            usersQueue.remove(concatFirstAndSecondNames(user));
            resourceRepository.save(project);
        }
    }

    @Override
    public List<String> getQueue() {
        Resource project = resourceRepository.findAll().stream().findFirst().orElse(null);
        if (project == null) {
            log.error(PROJECT_NOT_FOUND_MESSAGE);
            throw new ObjectNotFoundException(PROJECT_NOT_FOUND_MESSAGE, "project");
        }
        return project.getUsersQueue();
    }

    @Override
    public String getCurrentUser() {
        Resource project = resourceRepository.findAll().stream().findFirst().orElse(null);
        if (project == null) {
            log.error(PROJECT_NOT_FOUND_MESSAGE);
            throw new ObjectNotFoundException(PROJECT_NOT_FOUND_MESSAGE, "project");
        }
        return project.getCurrentUser();
    }

    private void updateCurrentUser(Resource resource, User user) {
        log.debug("Updating project={} that has current user={} to user={}", resource.getProjectName(), resource.getCurrentUser(), concatFirstAndSecondNames(user));

        resource.setCurrentUser(concatFirstAndSecondNames(user));
        Resource updatedResource = resourceRepository.save(resource);

        log.debug("New current user={}", updatedResource.getCurrentUser());
    }

    private void moveUserFromQueueToResource(Resource resource, User user, Queue<String> queue) {
        queue.poll();
        resource.setCurrentUser(concatFirstAndSecondNames(user));
        resourceRepository.save(resource);
    }

    private void addUserToQueue(Resource resource, User user, Queue<String> queue) {
        queue.add(concatFirstAndSecondNames(user));
        resourceRepository.save(resource);
    }
}
