package org.orekyuu.nozomi.presentation.controller;

import org.junit.jupiter.api.Test;
import org.orekyuu.nozomi.domain.project.Project;
import org.orekyuu.nozomi.domain.project.ProjectId;
import org.orekyuu.nozomi.domain.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectsControllerTest {

    @Autowired
    MockMvc client;
    @MockBean
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
}