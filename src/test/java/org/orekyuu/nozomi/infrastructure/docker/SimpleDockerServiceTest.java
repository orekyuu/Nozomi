package org.orekyuu.nozomi.infrastructure.docker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SimpleDockerServiceTest {
    @Autowired
    SimpleDockerService dockerService;

    @Test
    void testPingCommand() {
        dockerService.ping();
    }
}