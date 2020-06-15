package org.orekyuu.nozomi.infrastructure.doma.converter;

import org.orekyuu.nozomi.domain.project.ProjectId;
import org.seasar.doma.ExternalDomain;

@ExternalDomain
public class ProjectIdConverter extends AbstractConverter<ProjectId, String> {
    public ProjectIdConverter() {
        super(ProjectId::value, ProjectId::new);
    }
}
