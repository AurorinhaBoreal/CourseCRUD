package com.db.crud.course.unitary;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.db.crud.course.dto.mapper.StudentMapper;
import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.fixture.StudentFixture;
import com.db.crud.course.model.Student;
import com.db.crud.course.repository.StudentRepository;
import com.db.crud.course.service.StudentService;
import com.db.crud.course.service.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StudentUnitary {
    
    @Mock
    StudentRepository studentRepository;
    @Mock
    StudentMapper studentMapper;

    @InjectMocks
    private StudentService studentService = new StudentServiceImpl();
    
    StudentRequest studentDTOValid = StudentFixture.StudentDTOValidFixture();
    StudentRequest studentDTOInvalid = StudentFixture.StudentDTOInvalidFixture();
    Student studentEntityValid = StudentFixture.StudentEntityValid();
    Student studentEntityInvalid = StudentFixture.StudentEntityInvalid();
    Pageable pageable;
    
    @Test
    @DisplayName("Happy Test: Student Repository findByCpf")
    void findByCpf() {
        when(studentRepository.findByCpf(studentDTOValid.cpf())).thenReturn(Optional.of(studentEntityValid));

        Student foundStudent = studentRepository.findByCpf(studentDTOValid.cpf()).get();

        assertNotNull(foundStudent);
    }

    @Test
    @DisplayName("Happy Test: Student Service List Pageable")
    void listStudent() {
        var listStudents = mock(Page.class);
        when(studentRepository.findAll(pageable)).thenReturn(listStudents);

        listStudents = studentService.list(pageable);

        verify(studentRepository).findAll(pageable);
        verifyNoMoreInteractions(studentRepository);
    }

}
