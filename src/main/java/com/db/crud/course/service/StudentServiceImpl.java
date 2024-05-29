package com.db.crud.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.db.crud.course.dto.mapper.StudentMapper;
import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.model.Student;
import com.db.crud.course.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Page<Object> listar(Pageable pageable) {
        log.info("Searching for Students in the Database...");
        
        return studentRepository.findAll(pageable).map(student -> {
            return StudentMapper.studentToDto(student);
        });
    }

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
