package org.orekyuu.nozomi.presentation.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.orekyuu.nozomi.domain.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(NotificationWebSocketHandlerTest.MockingConfiguration.class)
class NotificationWebSocketHandlerTest {

    @LocalServerPort
    int port;
    WebSocketClient client;
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper mapper;

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
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            String body = mapper.writeValueAsString(Map.of("id", id, "name", "test_project"));
            var request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(apiUri(), request, String.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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