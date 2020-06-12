package org.orekyuu.nozomi.presentation.controller;

import org.orekyuu.nozomi.domain.project.ProjectRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/projects")
public class ProjectsController {

    final ProjectRepository projectRepository;

    public ProjectsController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    List<ProjectEntity> index() {
        return projectRepository.findAll().stream()
                .map(ProjectEntity::new)
                .collect(Collectors.toList());
    }
}
