package org.orekyuu.nozomi.presentation.controller;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.orekyuu.nozomi.domain.project.Project;
import org.orekyuu.nozomi.domain.project.ProjectId;
import org.orekyuu.nozomi.domain.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectsControllerTest {

    @Autowired
    MockMvc client;
    @SpyBean
    ProjectRepository projectRepository;

    @Test
    void index() throws Exception {
        var data = List.of(new Project(new ProjectId("id"), "test name"));
        doReturn(data).when(projectRepository).findAll();

        client.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value("id"))
                .andExpect(jsonPath("[0].name").value("test name"));
    }

    @Test
    void createSuccess() throws Exception {
        client.perform(post("/api/projects")
                .param("id", "test_id")
                .param("name", "hoge")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        Assertions.assertThat(projectRepository.find(new ProjectId("test_id")))
                .isNotEmpty();
    }

    @Test
    void createValidationError() throws Exception {
        client.perform(post("/api/projects")
                .param("id", "test_id")
                .param("name", "")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("must not be blank")));

        Assertions.assertThat(projectRepository.findAll())
                .isEmpty();
    }
}