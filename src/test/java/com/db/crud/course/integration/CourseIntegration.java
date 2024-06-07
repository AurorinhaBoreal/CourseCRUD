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

import com.db.crud.course.dto.request.CourseRequest;
import com.db.crud.course.fixture.CourseFixture;
import com.db.crud.course.fixture.SqlProvider;
import com.db.crud.course.model.Course;
import com.db.crud.course.model.Student;
import com.db.crud.course.repository.CourseRepository;
import com.db.crud.course.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class CourseIntegration {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    private CourseRequest courseDTORequest;
    String json;

    @Test
    @DisplayName("Happy Test: Should get Course Pageable")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertTeacher),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertCourse),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void shouldGetCoursePageableIntegrationC() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.get("/course"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].name").value("Análise e Desenvolvimento de Sistemas"))
        .andExpect(jsonPath("$.content[0].semesters").value(6));
    }

    @Test
    @DisplayName("Happy Test: Should get Students Pageable")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertStudent),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertTeacher),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertCourse),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void shouldGetStudentPageableIntegrationIntegrationC() throws Exception {
    
        mockMvc.perform(MockMvcRequestBuilders.get("/course/students"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].name").value("Análise e Desenvolvimento de Sistemas"))
        .andExpect(jsonPath("$.content[0].semesters").value(6));
    
    }

    @Test
    @DisplayName("Happy Test: Should Enroll Student in Course")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertStudent),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertTeacher),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertCourse),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void shouldEnrollStudentIntegrationC() throws Exception {
    
        Course course = courseRepository.findAll().get(0);
        Student student = studentRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/course/enroll/"+course.getCourseId()+"/"+student.getEnrollmentId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(course.getName()))
        .andExpect(jsonPath("$.semesters").value(course.getSemesters()))
        .andExpect(jsonPath("$.students[0].firstName").value(student.getFirstName()))
        .andExpect(jsonPath("$.students[0].parentName").value(student.getParentName()))
        .andExpect(jsonPath("$.students[0].parentNumber").value(student.getParentNumber()));
    }

    @Test
    @DisplayName("Happy Test: Should Disenroll Student from Course")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertStudent),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertTeacher),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertCourse),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertCourseStudent),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void shouldDisenrollStudentIntegrationC() throws Exception {
    
        Course course = courseRepository.findAll().get(0);
        Student student = studentRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/course/enroll/"+course.getCourseId()+"/"+student.getEnrollmentId()))
        .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.delete("/course/disenroll/"+course.getCourseId()+"/"+student.getEnrollmentId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.students[0]").doesNotExist());
    }

    @Test
    @DisplayName("Happy Test: Should Create Course")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertTeacher),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void shouldCreateCourseIntegrationC() throws Exception {
    
        courseDTORequest = CourseFixture.CourseDTOValidFixture();
        json = mapper.writeValueAsString(courseDTORequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/course/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Happy Test: Should Update Course")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertTeacher),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertCourse),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void shouldUpdateCourseIntegrationC() throws Exception {
    
        courseDTORequest = CourseFixture.CourseDTOValidFixture();
        json = mapper.writeValueAsString(courseDTORequest);
        Course course = courseRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.put("/course/update/" + course.getCourseId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(courseDTORequest.name()))
        .andExpect(jsonPath("$.semesters").value(courseDTORequest.semesters()));
    }

    @Test
    @DisplayName("Happy Test: Should Delete Course")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertTeacher),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertCourse),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void shouldDeleteCourseIntegrationC() throws Exception {
    
        Course course = courseRepository.findAll().get(0);

        mockMvc.perform(MockMvcRequestBuilders.delete("/course/delete/"+course.getCourseId()+"/"+course.getSemesters()))
            .andExpect(status().isNoContent());
    }
}
