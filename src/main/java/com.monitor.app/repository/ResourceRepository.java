package com.monitor.app.repository;

import com.monitor.app.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Resource findResourceByProjectName(String projectName);
}
