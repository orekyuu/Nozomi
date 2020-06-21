package org.orekyuu.nozomi.infrastructure.docker;

import com.github.dockerjava.core.DockerClientConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableConfigurationProperties(DockerClientConfigurationProperty.class)
public class DockerClientConfiguration {

    public static final String DOCKER_TASK_EXECUTOR = "org.orekyuu.nozomi.infrastructure.docker.DockerClientConfiguration.dockerTaskExecutor";

    @Bean
    DockerClientConfig dockerClientConfig(DockerClientConfigurationProperty property) {
        return property.build();
    }

    @Bean(DOCKER_TASK_EXECUTOR)
    TaskExecutor dockerTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(10);
        return executor;
    }
}
