package org.orekyuu.nozomi.infrastructure.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.orekyuu.nozomi.domain.project.ProjectRepository;
import org.orekyuu.nozomi.infrastructure.datasource.InMemoryProjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfigurer {
    @Bean
    ProjectRepository projectRepository() {
        return new InMemoryProjectRepository();
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }
}
