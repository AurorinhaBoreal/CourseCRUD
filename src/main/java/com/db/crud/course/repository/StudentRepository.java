package com.db.crud.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.crud.course.model.Student;
import java.util.Optional;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByCpf(String cpf);
    Optional<Student> findByEnrollmentId(Long enrollmentId);
    boolean existsByCpf(String cpf);
}
