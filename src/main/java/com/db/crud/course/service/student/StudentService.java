package com.db.crud.course.service.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.dto.response.StudentAgeResponse;
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.model.Student;

@Service
public interface StudentService {

    public Page<Object> list(Pageable pageable);

    public StudentResponse specific(String info, String searchType);

    public StudentAgeResponse getAge(Long enrollmentId);

    public StudentResponse create(StudentRequest studentRequestDTO);
    
    public StudentResponse update(StudentRequest studentRequestDTO, Long enrollmentId);

    public Long delete(Long enrollmentId, String cpf);

    public Student findStudent(Long enrollmentId);

    public boolean verifyCPF(String cpf);
}
