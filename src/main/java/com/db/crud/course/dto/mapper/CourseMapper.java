package com.db.crud.course.dto.mapper;

import org.mapstruct.Mapper;

import com.db.crud.course.dto.request.CourseRequest;
import com.db.crud.course.dto.response.CourseResponse;
import com.db.crud.course.model.Course;
import com.db.crud.course.model.Teacher;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    
    static CourseResponse courseToDto(Course course) {
        return CourseResponse.builder()
            .courseId(course.getCourseId())
            .teacherId(course.getTeacherId())
            .name(course.getName())
            .semesters(course.getSemesters())
            .students(course.getStudents())
            .build();
    }

    static Course dtoToCourse(CourseRequest courseDTO, Teacher teacher) {
        return Course.builder()
            .courseId(courseDTO.courseId())
            .teacherId(teacher)
            .name(courseDTO.name())
            .semesters(courseDTO.semesters())
            .build();
    }

    static Course updateEntity(Course originalCourse, CourseRequest updateCourse) {

        originalCourse.setName(updateCourse.name());
        originalCourse.setSemesters(updateCourse.semesters());
        
        return originalCourse;
    }
}
