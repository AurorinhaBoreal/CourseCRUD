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

import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.fixture.SqlProvider;
import com.db.crud.course.model.Student;
import com.db.crud.course.fixture.StudentFixture;
import com.db.crud.course.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class StudentIntegration {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    StudentRepository studentRepository;

    private StudentRequest studentDTORequest;
    String json;

    @Test
    @DisplayName("Happy Test: Should get Studend Pageable")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertStudent),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void shouldGetStudentPageableIntegrationS() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.get("/student"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[1].firstName").value("Fernando"))
        .andExpect(jsonPath("$.content[1].lastName").value("Senna"));
    }

    @Test
    @DisplayName("Happy Test: Should Create Student")
    void shouldCreateStudentIntegrationS() throws Exception {
    
        studentDTORequest = StudentFixture.StudentDTOValidFixture();

        json = mapper.writeValueAsString(studentDTORequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/student/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.enrollmentId").value(studentDTORequest.enrollmentId()))
        .andExpect(jsonPath("$.firstName").value(studentDTORequest.firstName()))
        .andExpect(jsonPath("$.cpf").value(studentDTORequest.cpf()))
        .andExpect(jsonPath("$.parentName").value(studentDTORequest.parentName()))
        .andExpect(jsonPath("$.parentNumber").value(studentDTORequest.parentNumber()));
    }

    @Test
    @DisplayName("Happy Test: Should Update Student")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertStudent),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void shouldUpdateStudentIntegrationS() throws Exception {
    
        studentDTORequest = StudentFixture.StudentDTOValidFixture();

        json = mapper.writeValueAsString(studentDTORequest);

        Student student = studentRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.put("/student/update/" + student.getEnrollmentId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(studentDTORequest.firstName()))
        .andExpect(jsonPath("$.parentName").value(studentDTORequest.parentName()))
        .andExpect(jsonPath("$.parentNumber").value(studentDTORequest.parentNumber()));
    }

    @Test
    @DisplayName("Happy Test: Should Delete Student")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertStudent),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void shouldDeleteStudentIntegrationS() throws Exception {
    
        Student student = studentRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/delete/"+student.getEnrollmentId()+"/"+student.getCpf()))
            .andExpect(status().isNoContent());
    }
}
