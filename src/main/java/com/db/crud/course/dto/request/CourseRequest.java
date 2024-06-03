package com.db.crud.course.dto.request;

import com.db.crud.course.model.Teacher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseRequest(

    @NotBlank(message = "Must inform a courseId.")
    Long courseId,

    @NotBlank(message = "Must assign a teacher to the course.")
    Teacher teacherId,

    @NotBlank(message = "Must inform the course name.")
    String name,

    @NotNull(message = "Must inform course duration")
    Integer semesters

) {}
