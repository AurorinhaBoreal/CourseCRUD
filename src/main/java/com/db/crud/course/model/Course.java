package com.db.crud.course.model;

import java.util.List;


import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_course")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_course")
    private Long Id;

    @Column(name = "course_id", unique = true, nullable = false)
    private Long courseId;

    // Indica o ID do professor que da aula nesse curso, fechando a relação na Entidade Teacher
    // Muitos Cursos podem ter um (o mesmo) professor
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "course_teacher", nullable = false)
    private Teacher teacherId;

    @Column(name = "course_name", length = 50)
    private String name;

    @Column(name = "course_duration", length = 2)
    private Integer semesters;

    // Vários estudantes, estudam em vários cursos - MANY TO MANY
    @ManyToMany
    @JoinTable(
        name = "tbl_course_students",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();
}
