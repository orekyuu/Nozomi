package org.orekyuu.nozomi.infrastructure.doma;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orekyuu.nozomi.domain.project.ProjectId;
import org.seasar.doma.jdbc.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class ProjectTableDaoTest {

    @Autowired
    ProjectTableDao dao;

    ProjectTable record = new ProjectTable(new ProjectId("test"), "test_name");

    @Test
    void testInsert() {
        Result<ProjectTable> result = dao.insert(record);
        Assertions.assertThat(result.getEntity()).isNotNull();
    }

    @Test
    void testUpdate() {
        dao.insert(record);
        dao.update(new ProjectTable(record.projectId(), "renamed"));
    }

    @Test
    void testDeleteByIds() {
        dao.insert(record);
        dao.deleteByIds(List.of(record.projectId()));
    }
}