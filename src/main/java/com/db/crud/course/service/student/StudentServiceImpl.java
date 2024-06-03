package com.db.crud.course.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.db.crud.course.dto.mapper.StudentMapper;
import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.exception.DuplicateCpfException;
import com.db.crud.course.exception.ObjectsDontMatchException;
import com.db.crud.course.model.Student;
import com.db.crud.course.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Page<Object> list(Pageable pageable) {
        log.info("Searching for Students in the Database...");
        
        return studentRepository.findAll(pageable).map(student -> {
            return StudentMapper.studentToDto(student);
        });
    }

    @Override
    public StudentResponse create(StudentRequest studentRequestDTO) {

        verifyCPF(studentRequestDTO.cpf());
        Student student = StudentMapper.dtoToStudent(studentRequestDTO);
        studentRepository.save(student);

        return StudentMapper.studentToDto(student);
    }

    @Override
    public StudentResponse update(StudentRequest studentRequestDTO, Long enrollmentId) {
        Student originalStudent = findStudent(enrollmentId);

        originalStudent.setFirstName(studentRequestDTO.firstName());
        originalStudent.setLastName(studentRequestDTO.lastName());
        originalStudent.setBirthDate(studentRequestDTO.birthDate());
        originalStudent.setGrade(studentRequestDTO.grade());
        originalStudent.setParentName(studentRequestDTO.parentName());
        originalStudent.setParentNumber(studentRequestDTO.parentNumber());

        studentRepository.save(originalStudent);

        return StudentMapper.studentToDto(originalStudent);
    }

    @Override
    public Long delete(Long enrollmentId, String cpf) {
        Student studentOne = findStudent(enrollmentId);
        Student studentTwo = studentRepository.findByCpf(cpf).get();

        if (studentOne == studentTwo) {
            studentRepository.delete(studentOne);
            return enrollmentId;
        } else {
            throw new ObjectsDontMatchException("Objects found through parameters don't match.");
        }
        
    }

    @Override
    public Student findStudent(Long enrollmentId) {
        Student studentFounded = studentRepository.findByEnrollmentId(enrollmentId).get();

        return studentFounded;
    }

    @Override
    public boolean verifyCPF(String cpf) {
        if (studentRepository.existsByCpf(cpf)) {
            throw new DuplicateCpfException("This cpf already is registered. CPF: "+cpf);
        }
        return false;
    }
}
