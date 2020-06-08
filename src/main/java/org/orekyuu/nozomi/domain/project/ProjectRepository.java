package org.orekyuu.nozomi.domain.project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Project> findAll();

    Optional<Project> find(ProjectId id);

    void create(Project project);

    void remove(ProjectId id);

    void update(Project project);
}
