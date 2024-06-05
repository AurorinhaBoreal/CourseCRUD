package com.db.crud.course.dto.response;

import lombok.Builder;

import java.util.List;

import com.db.crud.course.model.Student;

@Builder
public record CourseStudentResponse(

    Long courseId,
    String name,
    Integer semesters,
    List<Student> students
) {}
