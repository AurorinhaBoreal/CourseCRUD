package com.db.crud.course.fixture;

import java.time.LocalDate;

import com.db.crud.course.dto.mapper.StudentMapper;
import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.model.Student;

public class StudentFixture {
    
    public static StudentRequest StudentDTOValidFixture() {
        return new StudentRequest(111L, "Aurora", "Kruschewsky", 7, LocalDate.of(2004, 05, 14), "09730461040", "Geisa Kruschewsky", "11974356085");
    }

    public static StudentRequest StudentDTOInvalidFixture() {
        return new StudentRequest(112L, null, null, null, LocalDate.of(2000, 10, 02), null, null, null);
    }

    public static Student StudentEntityValid() {
        Student student = StudentMapper.dtoToStudent(StudentDTOValidFixture());

        return student;
    }

    
    public static Student StudentEntityInvalid() {
        Student student = StudentMapper.dtoToStudent(StudentDTOInvalidFixture());

        return student;
    }
}
