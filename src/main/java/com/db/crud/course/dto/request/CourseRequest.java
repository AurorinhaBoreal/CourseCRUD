package com.db.crud.course.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CourseRequest(

    @NotBlank(message = "Must inform a courseId.")
    Long courseId,

    @NotBlank(message = "Must assign a teacher to the course.")
    Long teacherId,

    @NotBlank(message = "Must inform the course name.")
    String name,

    @NotNull(message = "Must inform course duration")
    Integer semesters

) {}
