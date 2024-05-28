package com.db.crud.course.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_teacher")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
public class Teacher {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_teacher")
    private Long Id;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 25, nullable = false)
    private String lastName;

    @Column
    @NotNull(message = "Informe uma data válida!")
    private LocalDate birthDate;

    @Column(length = 14)
    private String phoneNumber;

    @Column(length = 11, nullable = false)
    private String Cpf;

    // Um professor para varios cursos - ONE TO MANY
    // Por essa Entidade ter o "mappedBy", ela vai ser a filha da relação
    // Ou seja, quem vai gerenciar a chave estrangeira será a Entidade curso
    @OneToMany(mappedBy = "teacherId")
    @Valid
    private List<Course> courses = new ArrayList<>();
}
