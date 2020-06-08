package org.orekyuu.nozomi.presentation.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.orekyuu.nozomi.domain.project.Project;
import org.orekyuu.nozomi.domain.project.ProjectId;
import org.orekyuu.nozomi.domain.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectsControllerTest {

    @Autowired
    WebTestClient client;
    @MockBean
    ProjectRepository projectRepository;

    @Test
    void index() {
        var data = List.of(new Project(new ProjectId("id"), "test name"));
        doReturn(data).when(projectRepository).findAll();

        client.get().uri("/api/projects").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("[0].id").isEqualTo("id")
                .jsonPath("[0].name").isEqualTo("test name");
    }
}