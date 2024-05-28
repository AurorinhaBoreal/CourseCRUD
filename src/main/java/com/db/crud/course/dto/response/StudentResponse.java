package com.db.crud.course.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.db.crud.course.model.Course;

import lombok.Builder;

@Builder
public record StudentResponse(
    Long enrollmentId,
    String firstName,
    String lastName,
    Integer grade,
    LocalDate birthDate,
    String Cpf,
    String parentName,
    String parentNumber,
    List<Course> courses
) {
    
}
