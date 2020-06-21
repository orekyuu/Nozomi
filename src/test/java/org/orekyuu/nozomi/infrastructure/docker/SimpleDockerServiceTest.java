package org.orekyuu.nozomi.infrastructure.docker;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

class SimpleDockerServiceTest {
    SimpleDockerService dockerService;

    @BeforeEach
    void setup() {
        var conf = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        dockerService = new SimpleDockerService(conf);
    }

    @Test
    void testContainers() throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println(dockerService.pullDeploymentImage().get());
    }
}