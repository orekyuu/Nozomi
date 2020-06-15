package org.orekyuu.nozomi.infrastructure.doma;

import org.assertj.db.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orekyuu.nozomi.domain.project.ProjectId;
import org.orekyuu.nozomi.utils.database.Tables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class ProjectTableDaoTest {

    @Autowired
    ProjectTableDao dao;
    @Autowired
    Tables tables;

    ProjectTable record = new ProjectTable(new ProjectId("test"), "test_name");

    @Test
    void testInsert() {
        dao.insert(record);

        Assertions.assertThat(tables.projects()).hasNumberOfRows(1);
    }

    @Test
    void testUpdate() {
        dao.insert(record);
        dao.update(new ProjectTable(record.projectId(), "renamed"));

        Assertions.assertThat(tables.projects()).row()
                .column("name")
                .containsValues("renamed");
    }

    @Test
    void testDeleteByIds() {
        dao.insert(record);
        dao.deleteByIds(List.of(record.projectId()));

        Assertions.assertThat(tables.projects()).hasNumberOfRows(0);
    }
}