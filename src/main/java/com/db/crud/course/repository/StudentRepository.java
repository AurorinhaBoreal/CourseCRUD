package com.db.crud.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.crud.course.model.Student;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByCpf(String cpf);
}
