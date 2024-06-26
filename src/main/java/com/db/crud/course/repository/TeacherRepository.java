package com.db.crud.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.crud.course.model.Teacher;
import java.util.Optional;


public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    
    Optional<Teacher> findByCpf(String cpf);
    Optional<Teacher> findByTeacherId(Long teacherId);
    Optional<Teacher> findByFirstName(String firstName);
    Optional<Teacher> findByLastName(String lastName);
    boolean existsByCpf(String cpf);
}
