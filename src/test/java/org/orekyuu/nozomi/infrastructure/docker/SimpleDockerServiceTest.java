package org.orekyuu.nozomi.infrastructure.docker;

import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class SimpleDockerServiceTest {
    @Autowired
    SimpleDockerService dockerService;

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    void testPingCommand() {
        dockerService.ping();
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.MINUTES)
    void testDockerTask() {
        DockerTask task = new DockerTask("debian", "stretch-slim", List.of(), List.of(
                "/bin/sh", "-c", "i=1; while [ $i -le 5 ]; do sleep 1; echo test; i=$(expr $i + 1); done"
        ));
        Observable<String> container = dockerService.runContainer(task);
        List<String> strings = container.toList().blockingGet();
        assertThat(strings).hasSize(5).allMatch(it -> it.equals("test\n"));
    }
}