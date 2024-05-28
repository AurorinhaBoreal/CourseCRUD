package com.db.crud.course.service;

import com.db.crud.course.dto.mapper.StudentMapper;
import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.model.Student;

public class StudentServiceImpl implements StudentService {
    
    @Override
    public StudentResponse create(StudentRequest studentRequestDTO) {

        Student studentReturn = StudentMapper.dtoToStudent(studentRequestDTO);

        return StudentMapper.studentToDto(studentReturn);
    }

    public StudentResponse update(StudentRequest studentRequestDTO, Long enrollmentId) {

        Student studentReturn = StudentMapper.dtoToStudent(studentRequestDTO);

        return StudentMapper.studentToDto(studentReturn);
    }

    public Long delete(Long enrollmentId) {

        return enrollmentId;
    }
}
