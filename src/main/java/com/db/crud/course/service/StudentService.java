package com.db.crud.course.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.model.Student;

@Service
public interface StudentService {

    public Page<Object> list(Pageable pageable);

    public StudentResponse create(StudentRequest studentRequestDTO);
    
    public StudentResponse update(StudentRequest studentRequestDTO, Long enrollmentId);

    public Long delete(Long enrollmentId);

    public Student findStudent(Long enrollmentId);

    public boolean verifyCPF(String cpf);

    // TODO: Implement Enroll and Disenroll after Implement Course CRUD
    // public StudentResponse enroll();

    // public StudentResponse disenroll();
}
