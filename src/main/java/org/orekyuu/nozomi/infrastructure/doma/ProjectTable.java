package org.orekyuu.nozomi.infrastructure.doma;

import org.orekyuu.nozomi.domain.project.ProjectId;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity(immutable = true)
@Table(name = "projects")
public record ProjectTable(@Id ProjectId projectId, String name) {
}
