package org.orekyuu.nozomi.infrastructure.doma;

import org.orekyuu.nozomi.domain.project.ProjectId;
import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

@Dao
@ConfigAutowireable
public interface ProjectTableDao {

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