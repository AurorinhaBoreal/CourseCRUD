package com.db.crud.course.fixture;

import java.time.LocalDate;

import com.db.crud.course.dto.mapper.TeacherMapper;
import com.db.crud.course.dto.request.TeacherRequest;
import com.db.crud.course.model.Teacher;

public class TeacherFixture {
    
    public static TeacherRequest TeacherDTOValidFixture() {
        return TeacherRequest.builder()
            .teacherId(111L)
            .firstName("Pedrro")
            .lastName("Lost")
            .birthDate(LocalDate.of(1995, 02, 24))
            .cpf("09730461040")
            .phoneNumber("11954672398")
            .build();
    }

    public static TeacherRequest TeacherDTOUpdateFixture() {
        return TeacherRequest.builder()
                .teacherId(113L)
                .firstName("Pedror")
                .lastName("Los")
                .birthDate(LocalDate.of(1995, 2, 24))
                .cpf("568495080")
                .phoneNumber("11954672398")
                .build();
    }

    public static TeacherRequest TeacherDTOInvalidFixture() {
        return TeacherRequest.builder()
                .teacherId(112L)
                .firstName(null)
                .lastName(null)
                .birthDate(LocalDate.of(1995, 2, 24))
                .cpf(null)
                .phoneNumber(null)
                .build();
    }

    public static Teacher TeacherEntityValid() {
        Teacher teacher = TeacherMapper.dtoToTeacher(TeacherDTOValidFixture());

        return teacher;
    }
    
    public static Teacher TeacherEntityInvalid() {
        Teacher teacher = TeacherMapper.dtoToTeacher(TeacherDTOInvalidFixture());

        return teacher;
    }

    public static Teacher TeacherEntityUpdate() {
        Teacher teacher = TeacherMapper.dtoToTeacher(TeacherDTOUpdateFixture());

        return teacher;
    }
}
