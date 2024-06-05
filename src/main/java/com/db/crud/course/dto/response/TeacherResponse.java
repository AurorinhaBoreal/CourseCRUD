package com.db.crud.course.dto.response;

import com.db.crud.course.model.Course;
import java.util.List;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record TeacherResponse(
    Long teacherId,
    String firstName,
    String lastName,
    LocalDate birthDate,
    String phoneNumber,
    String cpf,
    List<Course> courses
) {}
