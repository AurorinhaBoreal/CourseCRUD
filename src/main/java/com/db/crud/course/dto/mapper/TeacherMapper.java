package com.db.crud.course.dto.mapper;

import org.mapstruct.Mapper;

import com.db.crud.course.dto.request.TeacherRequest;
import com.db.crud.course.dto.response.TeacherResponse;
import com.db.crud.course.model.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    
    static TeacherResponse teacherToDto(Teacher teacher) {
        return TeacherResponse.builder()
            .teacherId(teacher.getTeacherId())
            .firstName(teacher.getFirstName())
            .lastName(teacher.getLastName())
            .birthDate(teacher.getBirthDate())
            .phoneNumber(teacher.getPhoneNumber())
            .cpf(teacher.getCpf())
            .courses(teacher.getCourses())
            .build();
    }

    static Teacher dtoToTeacher(TeacherRequest teacherDTO) {
        return Teacher.builder()
            .teacherId(teacherDTO.teacherId())
            .firstName(teacherDTO.firstName())
            .lastName(teacherDTO.lastName())
            .birthDate(teacherDTO.birthDate())
            .phoneNumber(teacherDTO.phoneNumber())
            .cpf(teacherDTO.cpf())
            .build();
    }
}
