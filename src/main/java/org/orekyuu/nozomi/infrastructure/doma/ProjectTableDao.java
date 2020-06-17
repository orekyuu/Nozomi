package org.orekyuu.nozomi.infrastructure.doma;

import org.orekyuu.nozomi.domain.project.ProjectId;
import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.util.stream.Collector;

@Dao
@ConfigAutowireable
public interface ProjectTableDao {

    @Select(strategy = SelectType.COLLECT)
    @Sql("""
            select * from projects""")
    <T> T findAll(Collector<ProjectTable, ?, T> collector);

    @Select(strategy = SelectType.COLLECT)
    @Sql("""
            select * from projects where project_id in /*ids*/('id')""")
    <T> T findByIds(Iterable<ProjectId> ids, Collector<ProjectTable, ?, T> collector);

    @Insert
    Result<ProjectTable> insert(ProjectTable projectTable);

    @Update
    Result<ProjectTable> update(ProjectTable projectTable);

    @Delete
    @Sql("""
            delete from projects
            where project_id in /*ids*/('id')""")
    int deleteByIds(Iterable<ProjectId> ids);
}