package com.db.crud.course.dto.response;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record TeacherAgeResponse( 
    Long teacherId,
    String firstName,
    String lastName,
    LocalDate birthDate,
    Integer age
    ) {}
