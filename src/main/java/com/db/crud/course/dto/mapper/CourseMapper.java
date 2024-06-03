package com.db.crud.course.dto.mapper;

import org.mapstruct.Mapper;

import com.db.crud.course.dto.request.CourseRequest;
import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.dto.response.CourseResponse;
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.model.Course;
import com.db.crud.course.model.Student;

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
}
