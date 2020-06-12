package org.orekyuu.nozomi.presentation.controller;


import org.orekyuu.nozomi.domain.project.ProjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProjectRequest {
    @Size(min = 1, max = 128)
    @Pattern(regexp = "[a-zA-Z0-1\\-_]*")
    String id;
    @NotBlank
    @Size(max = 128)
    String name;

    public ProjectId projectId() {
        return new ProjectId(id);
    }
}
