package com.db.crud.course.unitary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.fixture.StudentFixture;
import com.db.crud.course.model.Student;
import com.db.crud.course.repository.StudentRepository;
import com.db.crud.course.service.student.StudentService;
import com.db.crud.course.service.student.StudentServiceImpl;

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
    StudentRequest studentDTOUpdate = StudentFixture.StudentDTOUpdateFixture();
    Student studentEntityValid = StudentFixture.StudentEntityValid();
    Student studentEntityInvalid = StudentFixture.StudentEntityInvalid();
    Student studentEntityUpdate = StudentFixture.StudentEntityUpdate();
    Pageable pageable;

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Happy Test: Student Service List Pageable")
    void listStudent() {
        var listStudents = mock(Page.class);
        when(studentRepository.findAll(pageable)).thenReturn(listStudents);

        listStudents = studentService.list(pageable);

        verify(studentRepository).findAll(pageable);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    @DisplayName("Happy Test: Student Service Create Student")
    void createStudent() {
        when(studentRepository.save(any())).thenReturn(studentEntityValid);

        StudentResponse createdStudent = studentService.create(studentDTOValid);

        assertEquals(7, createdStudent.grade());
        assertEquals( "Aurora", createdStudent.firstName());
        assertEquals( "Kruschewsky", createdStudent.lastName());
        assertEquals( "Geisa Kruschewsky", createdStudent.parentName());
        assertEquals( "11974356085", createdStudent.parentNumber());
        assertEquals( 111L, createdStudent.enrollmentId());
    }

    @Test
    @DisplayName("Happy Test: Student Service Update Student")
    void updateStudent() {
        when(studentRepository.findByEnrollmentId(anyLong())).thenReturn(Optional.of(studentEntityValid));
        when(studentRepository.save(studentEntityValid)).thenReturn(studentEntityUpdate);

        StudentResponse studentUpdated = studentService.update(studentDTOUpdate, 12L);

        assertEquals(studentDTOUpdate.firstName(), studentUpdated.firstName());
        assertEquals(studentDTOUpdate.lastName(), studentUpdated.lastName());
        assertEquals(studentDTOUpdate.birthDate(), studentUpdated.birthDate());
        assertEquals(studentDTOUpdate.parentName(), studentUpdated.parentName());
        assertEquals(studentDTOUpdate.parentNumber(), studentUpdated.parentNumber());
    }

    @Test
    @DisplayName("Happy Test: Student Service Delete Student")
    void shouldDeleteStudent() {
        when(studentRepository.findByEnrollmentId(anyLong())).thenReturn(Optional.of(studentEntityValid));
        when(studentRepository.findByCpf(anyString())).thenReturn(Optional.of(studentEntityValid));
     
        Long deleted = studentService.delete(12L, "1111111112");

        assertEquals(12, deleted);
    }

    // @Test
    // @DisplayName("Sad Test:")
    // void shouldNotDeleteStudent() {
    // ... thrown = assertThrows(....class, () -> {
    // // TODO: Test Logic
    // });
    
    // assertEquals(, thrown.getMessage());
    // }

    @Test
    @DisplayName("Happy Test: Student Repository findByCpf")
    void shouldFindByCpf() {
        when(studentRepository.findByCpf(studentDTOValid.cpf())).thenReturn(Optional.of(studentEntityValid));

        Student foundStudent = studentRepository.findByCpf(studentDTOValid.cpf()).get();

        assertNotNull(foundStudent);
    }

    // @Test
    // @DisplayName("Sad Test: Student Repository findByCpf")
    // void shouldNotFindByCpf() {
    // ... thrown = assertThrows(....class, () -> {
    //     // TODO: Test Logic
    // });
    
    // assertEquals(, thrown.getMessage());
    // }

    // @Test
	// @DisplayName("Sad Test: Should thrown DuplicateCpfException in create")
	// void thrownDuplicateCpfException() {
	// 	DuplicateCpfException thrown = assertThrows(DuplicateCpfException.class, () -> {
	// 		when(personRepository.existsByCpf(anyString())).thenReturn(true);

	// 		personService.create(personDTOValid);
	// 	});
	
	// 	assertEquals("Already exists a person with this cpf!", thrown.getMessage());
	// }

    @Test
    @DisplayName("Happy Test: Student Service Verify CPF")
    void verifyValidCpf() {
        when(studentRepository.existsByCpf(anyString())).thenReturn(false);

        boolean verification = studentService.verifyCPF(anyString());

        assertFalse(verification);
    }

    // @Test
    // @DisplayName("Sad Test: Student Service Verify CPF")
    // void verifyInvalidCpf() {
    // ... thrown = assertThrows(....class, () -> {
    // // TODO: Test Logic
    // });
    
    // assertEquals(, thrown.getMessage());
    // }

    @Test
    @DisplayName("Happy Test: Student Service Find Student")
    void findStudent() {
        when(studentRepository.findByEnrollmentId(anyLong())).thenReturn(Optional.of(studentEntityInvalid));

        Student student = studentService.findStudent(anyLong());

        assertNotNull(student);
    }
}
