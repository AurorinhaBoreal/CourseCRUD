package com.db.crud.course.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.db.crud.course.dto.mapper.TeacherMapper;
import com.db.crud.course.dto.request.TeacherRequest;
import com.db.crud.course.dto.response.TeacherResponse;
import com.db.crud.course.model.Teacher;
import com.db.crud.course.repository.TeacherRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    
    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public Page<Object> list(Pageable pageable) {
        log.info("Searching for Teachers in the Database...");
        
        return teacherRepository.findAll(pageable).map(teacher -> {
            return TeacherMapper.teacherToDto(teacher);
        });
    }

    @Override
    public TeacherResponse create(TeacherRequest teacherRequestDTO) {

        verifyCPF(teacherRequestDTO.cpf());
        Teacher teacher = TeacherMapper.dtoToTeacher(teacherRequestDTO);
        teacherRepository.save(teacher);

        return TeacherMapper.teacherToDto(teacher);
    }

    @Override
    public TeacherResponse update(TeacherRequest teacherRequestDTO, Long teacherId) {
        Teacher originalTeacher = findTeacher(teacherId);

        originalTeacher.setFirstName(teacherRequestDTO.firstName());
        originalTeacher.setLastName(teacherRequestDTO.lastName());
        originalTeacher.setBirthDate(teacherRequestDTO.birthDate());
        originalTeacher.setPhoneNumber(teacherRequestDTO.phoneNumber());

        teacherRepository.save(originalTeacher);

        return TeacherMapper.teacherToDto(originalTeacher);
    }

    // TODO: Create Customized Exceptions
    @Override
    public Long delete(Long teacherId, String cpf) {
        Teacher teacherOne = findTeacher(teacherId);
        Teacher teacherTwo = teacherRepository.findByCpf(cpf).get();

        if (teacherOne == teacherTwo) {
            teacherRepository.delete(teacherOne);
            return teacherId;
        } else {
            throw new RuntimeException("The data doesn't match!");
        }
        
    }

    // TODO: Create Customized Exceptions
    @Override
    public Teacher findTeacher(Long teacherId) {
        Teacher teacherFounded = teacherRepository.findByTeacherId(teacherId).get();
        if (teacherFounded == null) {
            throw new RuntimeException("Teacher with this enrollment ID isn't registered!");
        }

        return teacherFounded;
    }

    // TODO: Create Customized Exceptions
    @Override
    public boolean verifyCPF(String cpf) {
        if (teacherRepository.existsByCpf(cpf)) {
            throw new RuntimeException("This cpf already is registered. CPF: "+cpf);
        }
        return false;
    }
}
