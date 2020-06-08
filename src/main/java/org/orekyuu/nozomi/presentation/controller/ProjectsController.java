package org.orekyuu.nozomi.presentation.controller;

import org.orekyuu.nozomi.domain.project.ProjectRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/projects")
public class ProjectsController {

    final ProjectRepository projectRepository;

    public ProjectsController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    Mono<List<ProjectEntity>> index() {
        return Flux.fromIterable(projectRepository.findAll())
                .map(ProjectEntity::new)
                .collectList();
    }
}
