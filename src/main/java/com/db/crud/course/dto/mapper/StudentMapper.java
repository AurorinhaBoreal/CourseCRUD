package com.db.crud.course.dto.mapper;

import org.mapstruct.Mapper;

import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    
    static StudentResponse studentToDto(Student student) {
        return StudentResponse.builder()
            .firstName(student.getFirstName())
            .lastName(student.getLastName())
            .grade(student.getGrade())
            .birthDate(student.getBirthDate())
            .Cpf(student.getCpf())
            .parentName(student.getParentName())
            .parentNumber(student.getParentNumber())
            .courses(student.getCourses())
            .build();
    }

    static Student dtoToStudent(StudentRequest studentDTO) {
        return Student.builder()
            .firstName(studentDTO.firstName())
            .lastName(studentDTO.lastName())
            .grade(studentDTO.grade())
            .birthDate(studentDTO.birthDate())
            .Cpf(studentDTO.Cpf())
            .parentName(studentDTO.parentName())
            .parentNumber(studentDTO.parentNumber())
            .build();
    }
}
