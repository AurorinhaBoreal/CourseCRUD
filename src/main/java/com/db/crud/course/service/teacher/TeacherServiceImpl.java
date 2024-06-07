package com.db.crud.course.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.db.crud.course.dto.mapper.TeacherMapper;
import com.db.crud.course.dto.request.TeacherRequest;
import com.db.crud.course.dto.response.TeacherResponse;
import com.db.crud.course.exception.DuplicateCpfException;
import com.db.crud.course.exception.ObjectsDontMatchException;
import com.db.crud.course.model.Teacher;
import com.db.crud.course.repository.TeacherRepository;

import jakarta.transaction.Transactional;
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
    @Transactional
    public TeacherResponse create(TeacherRequest teacherRequestDTO) {

        verifyCPF(teacherRequestDTO.cpf());
        Teacher teacher = TeacherMapper.dtoToTeacher(teacherRequestDTO);
        teacherRepository.save(teacher);

        return TeacherMapper.teacherToDto(teacher);
    }

    @Override
    @Transactional
    public TeacherResponse update(TeacherRequest teacherRequestDTO, Long teacherId) {
        Teacher originalTeacher = findTeacher(teacherId);

        TeacherMapper.updateEntity(originalTeacher, teacherRequestDTO);
        teacherRepository.save(originalTeacher);

        return TeacherMapper.teacherToDto(originalTeacher);
    }

    @Override
    @Transactional
    public Long delete(Long teacherId, String cpf) {
        Teacher teacherOne = findTeacher(teacherId);
        Teacher teacherTwo = teacherRepository.findByCpf(cpf).get();

        if (teacherOne == teacherTwo) {
            teacherRepository.delete(teacherOne);
            return teacherId;
        } else {
            throw new ObjectsDontMatchException("Objects found through parameters don't match.");
        }
        
    }

    @Override
    public Teacher findTeacher(Long teacherId) {
        Teacher teacherFounded = teacherRepository.findByTeacherId(teacherId).get();

        return teacherFounded;
    }

    @Override
    public boolean verifyCPF(String cpf) {
        if (teacherRepository.existsByCpf(cpf)) {
            throw new DuplicateCpfException("This cpf already is registered. CPF: "+cpf);
        }
        return false;
    }
}
