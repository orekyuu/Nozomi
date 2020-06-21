package org.orekyuu.nozomi.infrastructure.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.orekyuu.nozomi.domain.docker.DockerService;
import org.springframework.stereotype.Component;

@Component
public class SimpleDockerService implements DockerService {

    final DockerClientConfig config;

    public SimpleDockerService(DockerClientConfig config) {
        this.config = config;
    }

    private DockerClient client() {
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();
        return DockerClientImpl.getInstance(config, httpClient);
    }

    @Override
    public void ping() {
        client().pingCmd().exec();
    }
}
