package org.orekyuu.nozomi.presentation.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.orekyuu.nozomi.domain.project.Project;

public class ProjectEntity {
    String id;
    String name;

    @JsonCreator
    public ProjectEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProjectEntity(Project project) {
        this(project.id().value(), project.name());
    }
}
