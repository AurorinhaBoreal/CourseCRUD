package com.db.crud.course.service.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.db.crud.course.dto.request.TeacherRequest;
import com.db.crud.course.dto.response.TeacherAgeResponse;
import com.db.crud.course.dto.response.TeacherResponse;
import com.db.crud.course.model.Teacher;

public interface TeacherService {
    
    public Page<Object> list(Pageable pageable);

    public TeacherResponse specific(String info);

    public TeacherAgeResponse getAge(Long teacherId);

    public TeacherResponse create(TeacherRequest teacherRequestDTO);
    
    public TeacherResponse update(TeacherRequest teacherRequestDTO, Long teacherId);

    public Long delete(Long teacherId, String cpf);

    public Teacher findTeacher(Long teacherId);

    public boolean verifyCPF(String cpf);
}
