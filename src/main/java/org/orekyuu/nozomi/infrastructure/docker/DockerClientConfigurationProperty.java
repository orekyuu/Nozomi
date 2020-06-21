package org.orekyuu.nozomi.infrastructure.docker;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.function.Function;

@ConfigurationProperties(prefix = DockerClientConfigurationProperty.DOCKER_CLIENT_PREFIX)
@ConstructorBinding
public class DockerClientConfigurationProperty {
    public static final String DOCKER_CLIENT_PREFIX = "docker.client";

    final String host;
    final String registryUrl;
    final String registryUsername;
    final String registryPassword;

    public DockerClientConfigurationProperty(String host, String registryUrl, String registryUsername, String registryPassword) {
        this.host = host;
        this.registryUrl = registryUrl;
        this.registryUsername = registryUsername;
        this.registryPassword = registryPassword;
    }

    public DockerClientConfig build() {
        DefaultDockerClientConfig.Builder builder = DefaultDockerClientConfig.createDefaultConfigBuilder();
        setIfNotNull(host, builder::withDockerHost);
        setIfNotNull(registryUrl, builder::withRegistryUrl);
        setIfNotNull(registryUsername, builder::withRegistryUsername);
        setIfNotNull(registryPassword, builder::withRegistryPassword);
        setIfNotNull(registryPassword, builder::withRegistryPassword);
        return builder.build();
    }

    private <T> void setIfNotNull(T value, Function<T, DefaultDockerClientConfig.Builder> func) {
        if (value != null) {
            func.apply(value);
        }
    }
}
