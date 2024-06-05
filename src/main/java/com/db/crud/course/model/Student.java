package com.db.crud.course.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student")
    @JsonIgnore
    private Long Id;

    @Column(name = "enrollment_id", unique = true, nullable = false)
    private Long enrollmentId;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 25, nullable = false)
    private String lastName;

    @Column(length = 2, nullable = false)
    private Integer semester;

    @Column(name = "birth_date", nullable = false)
    @NotNull(message = "Informe uma data válida!")
    private LocalDate birthDate;

    @Column(length = 11, nullable = false)
    @JsonIgnore
    private String cpf;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "parent_number", length = 14)
    private String parentNumber;

    // Vários estudantes podem fazer vários cursos diferentes - ONE TO MANY

    @JsonIgnore
    @ManyToMany(mappedBy = "students")
    private List<Course> courses = new ArrayList<>();
}
