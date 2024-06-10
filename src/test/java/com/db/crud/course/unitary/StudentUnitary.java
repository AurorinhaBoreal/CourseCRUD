package com.db.crud.course.unitary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.NoSuchElementException;
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
import com.db.crud.course.dto.response.StudentAgeResponse;
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.exception.DuplicateCpfException;
import com.db.crud.course.exception.ObjectsDontMatchException;
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

    @Test
    @DisplayName("Happy Test: Student Service List Pageable")
    @SuppressWarnings("unchecked")
    void shouldListStudentUnitaryS() {
        var listStudents = mock(Page.class);
        when(studentRepository.findAll(pageable)).thenReturn(listStudents);

        listStudents = studentService.list(pageable);

        verify(studentRepository).findAll(pageable);
        verifyNoMoreInteractions(studentRepository);
    }
    
    @Test
    @DisplayName("Happy Test: Student Service List Specific Student By CPF")
    void shouldListSpecificStudentCpfUnitaryS() {
        when(studentRepository.findByCpf(studentDTOValid.cpf())).thenReturn(Optional.of(studentEntityValid));

        StudentResponse studentResponse = studentService.specific(studentDTOValid.cpf(), "cpf");
    
        assertNotNull(studentResponse);
    }

    @Test
    @DisplayName("Happy Test: Student Service List Specific Student By First Name")
    void shouldListSpecificStudentFNameUnitaryS() {
        when(studentRepository.findByFirstName(studentDTOValid.firstName())).thenReturn(Optional.of(studentEntityValid));

        StudentResponse studentResponse = studentService.specific(studentDTOValid.firstName(), "fn");
    
        assertNotNull(studentResponse);
    }

    @Test
    @DisplayName("Happy Test: Student Service List Specific Student By Last Name")
    void shouldListSpecificStudentLNameUnitaryS() {
        when(studentRepository.findByLastName(studentDTOValid.lastName())).thenReturn(Optional.of(studentEntityValid));

        StudentResponse studentResponse = studentService.specific(studentDTOValid.lastName(), "ln");
    
        assertNotNull(studentResponse);
    }

    @Test
    @DisplayName("Happy Test: Student Service Get Age of Student")
    void shouldGetAgeOfStudentUnitaryS() {
        when(studentRepository.findByEnrollmentId(anyLong())).thenReturn(Optional.of(studentEntityValid));

        StudentAgeResponse response = studentService.getAge(studentDTOValid.enrollmentId());

        assertNotNull(response.age());
    }

    @Test
    @DisplayName("Happy Test:")
    void test() {
    
    }

    @Test
    @DisplayName("Happy Test: Student Service Create Student")
    void shouldCreateStudentUnitaryS() {
        when(studentRepository.save(any())).thenReturn(studentEntityValid);

        StudentResponse createdStudent = studentService.create(studentDTOValid);

        assertEquals(7, createdStudent.semester());
        assertEquals( "Aurora", createdStudent.firstName());
        assertEquals( "Kruschewsky", createdStudent.lastName());
        assertEquals( "Geisa Kruschewsky", createdStudent.parentName());
        assertEquals( "11974356085", createdStudent.parentNumber());
        assertEquals( 111L, createdStudent.enrollmentId());
    }

    @Test
    @DisplayName("Happy Test: Student Service Update Student")
    void shouldUpdateStudentUnitaryS() {
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
    void shouldDeleteStudentUnitaryS() {
        when(studentRepository.findByEnrollmentId(anyLong())).thenReturn(Optional.of(studentEntityValid));
        when(studentRepository.findByCpf(anyString())).thenReturn(Optional.of(studentEntityValid));
     
        Long deleted = studentService.delete(12L, "1111111112");

        assertEquals(12, deleted);
    }

    @Test
    @DisplayName("Sad Test: Student Service Shouldn't Delete Student")
    void shouldNotDeleteStudentUnitaryS() {
    ObjectsDontMatchException thrown = assertThrows(ObjectsDontMatchException.class, () -> {
        when(studentRepository.findByEnrollmentId(113L)).thenReturn(Optional.of(studentEntityUpdate));
        when(studentRepository.findByCpf("09730461040")).thenReturn(Optional.of(studentEntityValid));

        studentService.delete(113L, "09730461040");
    });
    
    assertEquals("Objects found through parameters don't match.", thrown.getMessage());
    }

    @Test
    @DisplayName("Happy Test: Student Repository findByCpf")
    void shouldFindByCpfUnitaryS() {
        when(studentRepository.findByCpf(studentDTOValid.cpf())).thenReturn(Optional.of(studentEntityValid));

        Student foundStudent = studentRepository.findByCpf(studentDTOValid.cpf()).get();

        assertNotNull(foundStudent);
    }

    @Test
    @DisplayName("Sad Test: Student Repository findByEnrollmentId")
    void shouldNotFindByEnrollmentIdUnitaryS() {
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            studentService.findStudent(10L);
        });
    
        assertEquals("No value present", thrown.getMessage());
    }

    @Test
	@DisplayName("Sad Test: Should thrown DuplicateCpfException in create")
	void shouldThrownDuplicateCpfExceptionUnitaryS() {
		DuplicateCpfException thrown = assertThrows(DuplicateCpfException.class, () -> {
			when(studentRepository.existsByCpf(anyString())).thenReturn(true);

			studentService.create(studentDTOValid);
		});
	
		assertEquals("This cpf already is registered. CPF: 09730461040", thrown.getMessage());
	}

    @Test
    @DisplayName("Happy Test: Student Service Verify CPF")
    void shouldVerifyValidCpfUnitaryS() {
        when(studentRepository.existsByCpf(anyString())).thenReturn(false);

        boolean verification = studentService.verifyCPF(anyString());

        assertFalse(verification);
    }

    @Test
    @DisplayName("Happy Test: Student Service Find Student")
    void shouldFindStudentUnitaryS() {
        when(studentRepository.findByEnrollmentId(anyLong())).thenReturn(Optional.of(studentEntityInvalid));

        Student student = studentService.findStudent(anyLong());

        assertNotNull(student);
    }
}
