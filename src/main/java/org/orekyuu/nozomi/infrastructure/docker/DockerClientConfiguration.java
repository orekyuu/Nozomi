package org.orekyuu.nozomi.infrastructure.docker;

import com.github.dockerjava.core.DockerClientConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DockerClientConfigurationProperty.class)
public class DockerClientConfiguration {

    @Bean
    DockerClientConfig dockerClientConfig(DockerClientConfigurationProperty property) {
        return property.build();
    }
}
