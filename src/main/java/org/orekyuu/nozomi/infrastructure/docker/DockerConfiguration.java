package org.orekyuu.nozomi.infrastructure.docker;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import org.orekyuu.nozomi.domain.deploy.docker.DockerService;
import org.springframework.context.annotation.Bean;

public class DockerConfiguration {

    @Bean
    DockerClientConfig dockerClientConfig() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder().build();
    }

    @Bean
    DockerService dockerService(DockerClientConfig dockerClientConfig) {
        return new SimpleDockerService(dockerClientConfig);
    }
}
