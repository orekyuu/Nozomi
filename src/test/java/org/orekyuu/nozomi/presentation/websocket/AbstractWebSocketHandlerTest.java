package org.orekyuu.nozomi.presentation.websocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AbstractWebSocketHandlerTest {

    @LocalServerPort
    int port;
    WebSocketClient client;

    @BeforeEach
    void setup() {
        client = new StandardWebSocketClient();
    }

    @Test
    void testWebSocketConnection() {
        CompletableFuture<String> future = WebSocketTestUtil.connectAndWaitFirstMessage(client, "ws://localhost:" + port + "/test");

        assertThat(future).succeedsWithin(Duration.ofSeconds(30)).isEqualTo("count=1");
    }

    @TestConfiguration
    @EnableWebSocket
    static class WebSocketConfiguration implements WebSocketConfigurer {

        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(new AbstractWebSocketHandler() {
                @Override
                public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                    super.afterConnectionEstablished(session);
                    session.sendMessage(new TextMessage("count=" + sessions.sessionCount()));
                }
            }, "/test");
        }
    }
}