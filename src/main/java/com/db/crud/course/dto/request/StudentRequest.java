package com.db.crud.course.dto.request;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentRequest(

    @NotBlank(message = "Inform a enrollment Id!")
    Long enrollmentId,
    
    @NotBlank(message = "First name cannot be empty!")
    String firstName,

    @NotBlank(message = "Last name cannot be empty!")
    String lastName,

    @NotNull(message = "It must be informed a course semester!")
    Integer semester,

    @NotNull(message = "Date must be valid!")
    LocalDate birthDate,

    @NotBlank(message = "CPF must be informed!")
    @CPF(message = "Invalid CPF!")
    String cpf,

    @NotBlank(message = "Must be informed a parent!")
    String parentName,

    @NotBlank(message = "Parent must have a contact number!")
    String parentNumber
) {}