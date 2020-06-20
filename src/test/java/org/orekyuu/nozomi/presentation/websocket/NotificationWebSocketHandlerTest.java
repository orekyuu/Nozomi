package org.orekyuu.nozomi.presentation.websocket;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.orekyuu.nozomi.domain.project.ProjectRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(NotificationWebSocketHandlerTest.MockingConfiguration.class)
class NotificationWebSocketHandlerTest {

    @LocalServerPort
    int port;
    WebSocketClient client;
    RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        client = new StandardWebSocketClient();
        restTemplate = new RestTemplate();
    }

    private String uri() {
        return "ws://localhost:" + port + "/ws/notifications";
    }

    private String apiUri() {
        return "http://localhost:" + port + "/api/projects";
    }

    private void postCreateProject(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var map = new LinkedMultiValueMap<String, String>();
        map.add("id", id);
        map.add("name", "test_project");

        var request = new HttpEntity<>(map, headers);
        restTemplate.postForEntity(apiUri(), request, String.class);
    }

    @Test
    void testOnNewProject() {
        CompletableFuture<String> future = WebSocketTestUtil.connectAndWaitFirstMessage(client, uri(),
                () -> postCreateProject("test"));

        Assertions.assertThat(future)
                .succeedsWithin(Duration.ofSeconds(5))
                .isEqualTo("{\"type\":\"PROJECT_CREATED\",\"data\":{\"id\":\"test\"}}");
    }

    @TestConfiguration
    static class MockingConfiguration {
        @Bean
        @Primary
        ProjectRepository projectRepository() {
            return Mockito.mock(ProjectRepository.class);
        }
    }
}