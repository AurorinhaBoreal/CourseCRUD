package com.db.crud.course.dto.response;

import lombok.Builder;

import com.db.crud.course.model.Student;
import com.db.crud.course.model.Teacher;

import java.util.List;

@Builder
public record CourseResponse(

    Long courseId,
    String name,
    Integer semesters,
    Teacher teacherId,
    List<Student> students
) {}
