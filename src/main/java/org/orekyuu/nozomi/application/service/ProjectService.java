package org.orekyuu.nozomi.application.service;

import org.orekyuu.nozomi.domain.basic.event.EventType;
import org.orekyuu.nozomi.domain.project.Project;
import org.orekyuu.nozomi.domain.project.ProjectEvent;
import org.orekyuu.nozomi.domain.project.ProjectId;
import org.orekyuu.nozomi.domain.project.ProjectRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    final ApplicationEventPublisher publisher;
    final ProjectRepository repository;

    public ProjectService(ApplicationEventPublisher publisher, ProjectRepository repository) {
        this.publisher = publisher;
        this.repository = repository;
    }

    public void create(ProjectId id, String name) {
        repository.create(new Project(id, name));

        publisher.publishEvent(new ProjectEvent(EventType.PROJECT_CREATED, id));
    }

    public void rename(ProjectId id, String newName) {
        repository.update(new Project(id, newName));
    }

    public void delete(ProjectId id) {
        repository.remove(id);
    }
}
