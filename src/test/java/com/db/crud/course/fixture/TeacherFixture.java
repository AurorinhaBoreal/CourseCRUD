package com.db.crud.course.fixture;

import java.time.LocalDate;

import com.db.crud.course.dto.mapper.TeacherMapper;
import com.db.crud.course.dto.request.TeacherRequest;
import com.db.crud.course.model.Teacher;

public class TeacherFixture {
    
    public static TeacherRequest TeacherDTOValidFixture() {
        return new TeacherRequest(111L, "Pedrro", "Lost", LocalDate.of(1995, 02, 24), "11954672398", "09730461040");
    }

    public static TeacherRequest TeacherDTOUpdateFixture() {
        return new TeacherRequest(111L, "Pedror", "Los", LocalDate.of(1995, 02, 24), "11954672398", "09730461040");
    }

    public static TeacherRequest TeacherDTOInvalidFixture() {
        return new TeacherRequest(111L, null, null, LocalDate.of(1995, 02, 24), null, null);
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
