package com.db.crud.course.service.student;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.db.crud.course.dto.mapper.StudentMapper;
import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.dto.response.StudentAgeResponse;
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.exception.DuplicateCpfException;
import com.db.crud.course.exception.ObjectsDontMatchException;
import com.db.crud.course.model.Student;
import com.db.crud.course.repository.StudentRepository;

import jakarta.transaction.Transactional;
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
    public StudentResponse specific(String info) {
        Student student = null;
        if (info.length() == 11)
            student = studentRepository.findByCpf(info).get();

        if (student == null) {
            student = studentRepository.findByFirstName(info).get();
        }

        if (student == null) {
            student = studentRepository.findByLastName(info).get();
        }

        return StudentMapper.studentToDto(student);
    }

    @Override
    public StudentAgeResponse getAge(Long enrollmentId) {
        Student student = findStudent(enrollmentId);

        Integer age = calcAge(student.getBirthDate());

        return StudentMapper.studentToAgeDto(student, age);
    }

    @Override
    @Transactional
    public StudentResponse create(StudentRequest studentRequestDTO) {

        verifyCPF(studentRequestDTO.cpf());
        Student student = StudentMapper.dtoToStudent(studentRequestDTO);
        studentRepository.save(student);

        return StudentMapper.studentToDto(student);
    }

    @Override
    @Transactional
    public StudentResponse update(StudentRequest studentRequestDTO, Long enrollmentId) {
        Student originalStudent = findStudent(enrollmentId);

        StudentMapper.updateEntity(originalStudent, studentRequestDTO);
        studentRepository.save(originalStudent);

        return StudentMapper.studentToDto(originalStudent);
    }

    @Override
    @Transactional
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

    public Integer calcAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        Integer age = Period.between(birthDate, currentDate).getYears();
        return age;
    }
}
