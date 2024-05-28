package com.db.crud.course.service;

import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.dto.response.StudentResponse;

public interface StudentService {

    public StudentResponse create(StudentRequest studentRequestDTO);

    public StudentResponse update(StudentRequest studentRequestDTO, Long enrollmentId);

    public Long delete(Long enrollmentId);

    // TODO: Implement Enroll and Disenroll after Implement Course CRUD
    // public StudentResponse enroll();

    // public StudentResponse disenroll();
}
