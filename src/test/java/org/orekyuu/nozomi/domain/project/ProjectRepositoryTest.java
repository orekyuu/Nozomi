package org.orekyuu.nozomi.domain.project;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    Project project1 = new Project(new ProjectId("pj1"), "pj1");
    Project project2 = new Project(new ProjectId("pj2"), "pj2");

    @BeforeEach
    void setup() {
        projectRepository.create(project1);
        projectRepository.create(project2);
    }

    @Test
    void testFindAll() {
        Assertions.assertThat(projectRepository.findAll()).hasSize(2);
    }

    @Test
    void testFind() {
        Assertions.assertThat(projectRepository.find(project1.id()))
                .get().extracting(Project::name)
                .isEqualTo(project1.name());
    }

    @Test
    void testUpdate() {
        projectRepository.update(new Project(project1.id(), "renamed"));
        Assertions.assertThat(projectRepository.find(project1.id()))
                .get().extracting(Project::name)
                .isEqualTo("renamed");
    }

    @Test
    void testUpdateRecordNotFound() {
        Assertions.assertThatThrownBy(() -> {
            projectRepository.update(new Project(new ProjectId("dummy"), "test"));
        }).isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void testInsert() {
        projectRepository.create(new Project(new ProjectId("test"), "test"));
        Assertions.assertThat(projectRepository.findAll()).hasSize(3);
    }

    @Test
    void testInsertFailed() {
        Assertions.assertThatThrownBy(() -> {
            projectRepository.create(project1);
        }).isExactlyInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void testDelete() {
        projectRepository.remove(project1.id());
        Assertions.assertThat(projectRepository.findAll())
                .hasSize(1)
                .doesNotContain(project1)
                .contains(project2);
    }

    @Test
    void testDeleteFailed() {
        Assertions.assertThatThrownBy(() -> {
            projectRepository.remove(new ProjectId("dummy"));
        }).isExactlyInstanceOf(EmptyResultDataAccessException.class);

        Assertions.assertThat(projectRepository.findAll()).hasSize(2);
    }
}