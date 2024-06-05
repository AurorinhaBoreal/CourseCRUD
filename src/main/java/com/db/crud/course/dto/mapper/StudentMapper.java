package com.db.crud.course.dto.mapper;

import org.mapstruct.Mapper;

import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    
    static StudentResponse studentToDto(Student student) {
        return StudentResponse.builder()
            .enrollmentId(student.getEnrollmentId())
            .firstName(student.getFirstName())
            .lastName(student.getLastName())
            .semester(student.getSemester())
            .birthDate(student.getBirthDate())
            .cpf(student.getCpf())
            .parentName(student.getParentName())
            .parentNumber(student.getParentNumber())
            .courses(student.getCourses())
            .build();
    }

    static Student dtoToStudent(StudentRequest studentDTO) {
        return Student.builder()
            .enrollmentId(studentDTO.enrollmentId())
            .firstName(studentDTO.firstName())
            .lastName(studentDTO.lastName())
            .semester(studentDTO.semester())
            .birthDate(studentDTO.birthDate())
            .cpf(studentDTO.cpf())
            .parentName(studentDTO.parentName())
            .parentNumber(studentDTO.parentNumber())
            .build();
    }
    
    static Student updateEntity(Student originalStudent, StudentRequest updateStudent) {

        originalStudent.setFirstName(updateStudent.firstName());
        originalStudent.setLastName(updateStudent.lastName());
        originalStudent.setSemester(updateStudent.semester());
        originalStudent.setBirthDate(updateStudent.birthDate());
        originalStudent.setParentName(updateStudent.parentName());
        originalStudent.setParentNumber(updateStudent.parentNumber());
        
        return originalStudent;
    }
}
