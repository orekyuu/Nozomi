package org.orekyuu.nozomi.presentation.websocket;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orekyuu.nozomi.domain.basic.event.EventType;
import org.orekyuu.nozomi.domain.project.ProjectEvent;
import org.orekyuu.nozomi.domain.project.ProjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationWebSocketHandlerTest {

    @LocalServerPort
    int port;
    WebSocketClient client;

    @Autowired
    ApplicationEventPublisher publisher;

    @BeforeEach
    void setup() {
        client = new StandardWebSocketClient();
    }

    private String uri() {
        return "ws://localhost:" + port + "/ws/notifications";
    }

    @Test
    void testOnNewProject() {
        CompletableFuture<String> future = WebSocketTestUtil.connectAndWaitFirstMessage(client, uri(),
                () -> publisher.publishEvent(new ProjectEvent(EventType.PROJECT_CREATED, new ProjectId("test"))));

        Assertions.assertThat(future)
                .succeedsWithin(Duration.ofSeconds(30))
                .isEqualTo("{\"type\":\"PROJECT_CREATED\",\"data\":{\"id\":\"test\"}}");
    }
}