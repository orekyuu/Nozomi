package org.orekyuu.nozomi.presentation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProjectsControllerTest {

    @Autowired
    MockMvc client;
    @SpyBean
    ProjectRepository projectRepository;
    @Autowired
    ObjectMapper mapper;

    String requestBody(String id, String name) throws JsonProcessingException {
        HashMap<String, Object> json = new HashMap<>();
        if (id != null) {
            json.put("id", id);
        }
        if (name != null) {
            json.put("name", name);
        }
        return mapper.writeValueAsString(json);
    }

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
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody("test_id", "hoge"))
        )
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        Assertions.assertThat(projectRepository.find(new ProjectId("test_id")))
                .isNotEmpty();
    }

    @Test
    void createValidationError() throws Exception {
        client.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody("test_id", ""))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("must not be blank")));

        Assertions.assertThat(projectRepository.findAll())
                .isEmpty();
    }

    @Test
    void updateSuccess() throws Exception {
        projectRepository.create(new Project(new ProjectId("test"), "New Project"));

        client.perform(put("/api/projects/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody(null, "Renamed Project"))
        )
                .andExpect(status().isOk());

        Assertions.assertThat(projectRepository.find(new ProjectId("test")))
                .isNotEmpty()
                .hasValueSatisfying(project -> {
                    Assertions.assertThat(project.name()).isEqualTo("Renamed Project");
                });
    }

    @Test
    void updateValidationError() throws Exception {
        projectRepository.create(new Project(new ProjectId("test"), "New Project"));

        client.perform(put("/api/projects/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody(null, ""))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("must not be blank")));

        Assertions.assertThat(projectRepository.find(new ProjectId("test")))
                .isNotEmpty()
                .hasValueSatisfying(project -> {
                    Assertions.assertThat(project.name()).isEqualTo("New Project");
                });
    }

    @Test
    void updateNotFound() throws Exception {
        client.perform(put("/api/projects/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody(null, "test"))
        )
                .andExpect(status().isNotFound());

        Assertions.assertThat(projectRepository.findAll())
                .isEmpty();
    }

    @Test
    void deleteSuccess() throws Exception {
        projectRepository.create(new Project(new ProjectId("test"), "hoge"));

        client.perform(delete("/api/projects/test"))
                .andExpect(status().isOk());

        Assertions.assertThat(projectRepository.findAll())
                .isEmpty();
    }
}