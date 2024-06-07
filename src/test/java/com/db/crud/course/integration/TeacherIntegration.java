package com.db.crud.course.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.db.crud.course.dto.request.TeacherRequest;
import com.db.crud.course.fixture.SqlProvider;
import com.db.crud.course.model.Teacher;
import com.db.crud.course.fixture.TeacherFixture;
import com.db.crud.course.repository.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class TeacherIntegration {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    TeacherRepository teacherRepository;

    private TeacherRequest teacherDTORequest;
    String json;

    @Test
    @DisplayName("Happy Test: Should get Teacher Pageable")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertTeacher),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void getTeacherPageable() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.get("/teacher"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].firstName").value("Julio"))
        .andExpect(jsonPath("$.content[0].lastName").value("Costa"));
    }

    @Test
    @DisplayName("Happy Test: Should Create Teacher")
    void createTeacher() throws Exception {
    
        teacherDTORequest = TeacherFixture.TeacherDTOValidFixture();

        json = mapper.writeValueAsString(teacherDTORequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/teacher/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.teacherId").value(teacherDTORequest.teacherId()))
        .andExpect(jsonPath("$.firstName").value(teacherDTORequest.firstName()))
        .andExpect(jsonPath("$.cpf").value(teacherDTORequest.cpf()))
        .andExpect(jsonPath("$.phoneNumber").value(teacherDTORequest.phoneNumber()));
    }

    @Test
    @DisplayName("Happy Test: Should Update Teacher")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertTeacher),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void updateTeacher() throws Exception {
    
        teacherDTORequest = TeacherFixture.TeacherDTOValidFixture();

        json = mapper.writeValueAsString(teacherDTORequest);

        Teacher teacher = teacherRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.put("/teacher/update/" + teacher.getTeacherId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(teacherDTORequest.firstName()))
        .andExpect(jsonPath("$.lastName").value(teacherDTORequest.lastName()))
        .andExpect(jsonPath("$.phoneNumber").value(teacherDTORequest.phoneNumber()));
    }

    @Test
    @DisplayName("Happy Test: Should Delete Teacher")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertTeacher),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void deleteTeacher() throws Exception {
    
        Teacher teacher = teacherRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.delete("/teacher/delete/"+teacher.getTeacherId()+"/"+teacher.getCpf()))
            .andExpect(status().isNoContent());
    }
}
