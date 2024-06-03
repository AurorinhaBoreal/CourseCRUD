package com.db.crud.course.dto.mapper;

import org.mapstruct.Mapper;

import com.db.crud.course.dto.request.CourseRequest;
import com.db.crud.course.dto.response.CourseResponse;
import com.db.crud.course.model.Course;

@Mapper(componentModel = "spring")
public class CourseMapper {
    
    static CourseResponse courseToDto(Course course) {
        return CourseResponse.builder()
            .courseId(course.getCourseId())
            .teacherId(course.getTeacherId())
            .name(course.getName())
            .semesters(course.getSemesters())
            .students(course.getStudents())
            .build();
    }

    static Course dtoToCourse(CourseRequest courseDTO) {
        return Course.builder()
            .courseId(courseDTO.courseId())
            .teacherId(courseDTO.teacherId())
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
