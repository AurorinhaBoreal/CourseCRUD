package com.db.crud.course.fixture;

import java.time.LocalDate;

import com.db.crud.course.dto.mapper.StudentMapper;
import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.model.Student;

public class StudentFixture {
    
    public static StudentRequest StudentDTOValidFixture() {
        return new StudentRequest(111L, "Aurora", "Kruschewsky", 7, LocalDate.of(2004, 05, 14), "09730461040", "Geisa Kruschewsky", "11974356085");
    }

    public static StudentRequest StudentDTOUpdateFixture() {
        return new StudentRequest(113L, "Madame", "Bonita", 8, LocalDate.of(2010, 07, 23), "72295193060", "Roberta Vasco", "21975437543");
    }

    public static StudentRequest StudentDTOInvalidFixture() {
        return new StudentRequest(112L, null, null, null, LocalDate.of(2000, 10, 02), null, null, null);
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
