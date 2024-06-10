package com.db.crud.course.fixture;

import java.time.LocalDate;

import com.db.crud.course.dto.mapper.CourseMapper;
import com.db.crud.course.dto.mapper.TeacherMapper;
import com.db.crud.course.dto.request.CourseRequest;
import com.db.crud.course.dto.request.TeacherRequest;
import com.db.crud.course.model.Course;
import com.db.crud.course.model.Teacher;

public class CourseFixture {
    
    public static TeacherRequest TeacherDTOValidFixture() {
        return TeacherRequest.builder()
                .teacherId(1001L)
                .firstName("Pedrro")
                .lastName("Lost")
                .birthDate(LocalDate.of(1995, 2, 24))
                .cpf("11954672398")
                .phoneNumber("09730461040")
                .build();
    }

    public static Teacher TeacherEntityValid() {
        Teacher teacher = TeacherMapper.dtoToTeacher(TeacherDTOValidFixture());

        return teacher;
    }

    public static CourseRequest CourseDTOValidFixture() {
        return CourseRequest.builder()
            .courseId(111L)
            .teacherId(1001L)
            .name("Técnico em Desenvolvimento de Sistemas")
            .semesters(6)
            .build();
    }
    
    public static CourseRequest CourseDTOUpdateFixture() {
        return CourseRequest.builder()
                .courseId(113L)
                .teacherId(1L)
                .name("Técnólogo em Desenvolvimento de Sistemas")
                .semesters(8)
                .build();
    }
    
    public static CourseRequest CourseDTOInvalidFixture() {
        return CourseRequest.builder()
                .courseId(112L)
                .teacherId(null)
                .name(null)
                .semesters(null)
                .build();
    }

    public static Course CourseEntityValid() {
        Course course = CourseMapper.dtoToCourse(CourseDTOValidFixture(), TeacherEntityValid());
        course.setId(1L);
        return course;
    }
    
    public static Course CourseEntityInvalid() {
        Course course = CourseMapper.dtoToCourse(CourseDTOInvalidFixture(), null);

        return course;
    }

    public static Course CourseEntityUpdate() {
        Course course = CourseMapper.dtoToCourse(CourseDTOUpdateFixture(), TeacherEntityValid());

        return course;
    }
}
