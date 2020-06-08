package org.orekyuu.nozomi.infrastructure.datasource;

import org.orekyuu.nozomi.domain.project.Project;
import org.orekyuu.nozomi.domain.project.ProjectId;
import org.orekyuu.nozomi.domain.project.ProjectRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryProjectRepository extends AbstractInMemoryRepository<ProjectId, Project> implements ProjectRepository {

    @Override
    public List<Project> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<Project> find(ProjectId id) {
        return super.find(id);
    }

    @Override
    public void create(Project project) {
        super.insert(project.id(), project);
    }

    @Override
    public void remove(ProjectId id) {
        super.remove(id);
    }

    @Override
    public void update(Project project) {
        super.update(project.id(), project);
    }
}
