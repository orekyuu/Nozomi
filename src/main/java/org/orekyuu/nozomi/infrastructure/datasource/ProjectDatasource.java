package org.orekyuu.nozomi.infrastructure.datasource;

import org.orekyuu.nozomi.domain.project.Project;
import org.orekyuu.nozomi.domain.project.ProjectId;
import org.orekyuu.nozomi.domain.project.ProjectRepository;
import org.orekyuu.nozomi.infrastructure.doma.ProjectTable;
import org.orekyuu.nozomi.infrastructure.doma.ProjectTableDao;
import org.seasar.doma.jdbc.Result;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectDatasource implements ProjectRepository {
    private final ProjectTableDao dao;

    public ProjectDatasource(ProjectTableDao dao) {
        this.dao = dao;
    }

    private Project mapping(ProjectTable table) {
        return new Project(table.projectId(), table.name());
    }

    @Override
    public List<Project> findAll() {
        return dao.findAll(Collector2.mappingToList(this::mapping));
    }

    @Override
    public Optional<Project> find(ProjectId id) {
        return dao.findByIds(List.of(id), Collector2.mappingToOptional(this::mapping));
    }

    @Override
    public void create(Project project) {
        dao.insert(ProjectTable.fromProject(project));
    }

    @Override
    public void remove(ProjectId id) {
        int count = dao.deleteByIds(List.of(id));
        if (count != 1) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    @Override
    public void update(Project project) {
        Result<ProjectTable> result = dao.update(ProjectTable.fromProject(project));
        if (result.getCount() != 1) {
            throw new EmptyResultDataAccessException(1);
        }
    }
}
