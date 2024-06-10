package com.db.crud.course.fixture;

import java.time.LocalDate;

import com.db.crud.course.dto.mapper.StudentMapper;
import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.model.Student;

public class StudentFixture {
    
    public static StudentRequest StudentDTOValidFixture() {
        return StudentRequest.builder()
            .enrollmentId(111L)
            .firstName("Aurora")
            .lastName("Kruschewsky")
            .semester(7)
            .birthDate(LocalDate.of(2004, 05, 14))
            .cpf("09730461040")
            .parentName("Geisa Kruschewsky")
            .parentNumber("11974356085")
            .build();
    }

    public static StudentRequest StudentDTOUpdateFixture() {
        return StudentRequest.builder()
            .enrollmentId(113L)
            .firstName("Madame") 
            .lastName("Bonita")
            .semester(8)
            .birthDate(LocalDate.of(2010, 07, 23))
            .cpf("72295193060") 
            .parentName("Roberta Vasco")
            .parentNumber("21975437543")
            .build();
    }

    public static StudentRequest StudentDTOInvalidFixture() {
        return StudentRequest.builder()
                .enrollmentId(112L)
                .firstName(null)
                .lastName(null)
                .birthDate(LocalDate.of(2000, 10, 2))
                .parentName(null)
                .parentNumber(null)
                .build();
    }

    public static Student StudentEntityValid() {
        Student student = StudentMapper.dtoToStudent(StudentDTOValidFixture());
        student.setId(1L);
        return student;
    }
    
    public static Student StudentEntityInvalid() {
        Student student = StudentMapper.dtoToStudent(StudentDTOInvalidFixture());

        return student;
    }

    public static Student StudentEntityUpdate() {
        Student student = StudentMapper.dtoToStudent(StudentDTOUpdateFixture());

        return student;
    }
}
