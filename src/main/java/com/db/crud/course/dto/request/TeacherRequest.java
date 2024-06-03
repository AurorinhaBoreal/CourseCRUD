package com.db.crud.course.dto.request;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeacherRequest(

    @NotBlank(message = "Inform a teacher Id!")
    Long teacherId,

    @NotBlank(message = "First name cannot be empty!")
    String firstName,

    @NotBlank(message = "Last name cannot be empty!")
    String lastName,

    @NotNull(message = "Date must be valid!")
    LocalDate birthDate,

    @NotBlank(message = "Please inform a Phone Number!")
    String phoneNumber,

    @NotBlank(message = "CPF must be informed!")
    @CPF(message = "Invalid CPF!")
    String cpf
) {}
