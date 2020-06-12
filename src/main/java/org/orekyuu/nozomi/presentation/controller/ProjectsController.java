package org.orekyuu.nozomi.presentation.controller;

import org.orekyuu.nozomi.application.service.ProjectService;
import org.orekyuu.nozomi.domain.project.ProjectId;
import org.orekyuu.nozomi.domain.project.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/projects")
public class ProjectsController {

    final ProjectRepository projectRepository;
    final ProjectService projectService;

    public ProjectsController(ProjectRepository projectRepository, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    @GetMapping
    List<ProjectEntity> index() {
        return projectRepository.findAll().stream()
                .map(ProjectEntity::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    void create(@Valid @ModelAttribute ProjectRequest request) {
        projectService.create(request.projectId(), request.name);
    }

    @PutMapping("{id}")
    void update(@PathVariable String id, @Valid @ModelAttribute ProjectRequest request) {
        projectService.rename(new ProjectId(id), request.name);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable String id) {
        projectService.delete(new ProjectId(id));
    }
}
