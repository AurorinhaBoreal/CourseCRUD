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
        return new TeacherRequest(111L, "Pedrro", "Lost", LocalDate.of(1995, 02, 24), "11954672398", "09730461040");
    }

    public static Teacher TeacherEntityValid() {
        Teacher teacher = TeacherMapper.dtoToTeacher(TeacherDTOValidFixture());

        return teacher;
    }

    public static CourseRequest CourseDTOValidFixture() {
        return new CourseRequest(111L, 1L, "Técnico em Desenvolvimento de Sistemas", 6);
    }

    public static CourseRequest CourseDTOUpdateFixture() {
        return new CourseRequest(113L, 1L, "Técnólogo em Desenvolvimento de Sistemas", 8);
    }
    public static CourseRequest CourseDTOInvalidFixture() {
        return new CourseRequest(112L, null, null, null);
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
