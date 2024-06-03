package com.db.crud.course.dto.response;

import com.db.crud.course.model.Teacher;

import lombok.Builder;

import com.db.crud.course.model.Student;
import java.util.List;

@Builder
public record CourseResponse(

    Long courseId,
    Teacher teacherId,
    String name,
    Integer semesters,
    List<Student> students
) {}
