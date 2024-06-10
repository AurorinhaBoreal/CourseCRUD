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

import com.db.crud.course.dto.mapper.TeacherMapper;
import com.db.crud.course.dto.request.TeacherRequest;
import com.db.crud.course.dto.response.TeacherAgeResponse;
import com.db.crud.course.dto.response.TeacherResponse;
import com.db.crud.course.exception.DuplicateCpfException;
import com.db.crud.course.exception.ObjectsDontMatchException;
import com.db.crud.course.fixture.TeacherFixture;
import com.db.crud.course.model.Teacher;
import com.db.crud.course.repository.TeacherRepository;
import com.db.crud.course.service.teacher.TeacherService;
import com.db.crud.course.service.teacher.TeacherServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TeacherUnitary {
    
    
    @Mock
    TeacherRepository teacherRepository;
    @Mock
    TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherService teacherService = new TeacherServiceImpl();
    
    TeacherRequest teacherDTOValid = TeacherFixture.TeacherDTOValidFixture();
    TeacherRequest teacherDTOInvalid = TeacherFixture.TeacherDTOInvalidFixture();
    TeacherRequest teacherDTOUpdate = TeacherFixture.TeacherDTOUpdateFixture();
    Teacher teacherEntityValid = TeacherFixture.TeacherEntityValid();
    Teacher teacherEntityInvalid = TeacherFixture.TeacherEntityInvalid();
    Teacher teacherEntityUpdate = TeacherFixture.TeacherEntityUpdate();
    Pageable pageable;

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Happy Test: Teacher Service List Pageable")
    void shouldListTeachersUnitaryT() {
        var listTeachers = mock(Page.class);
        when(teacherRepository.findAll(pageable)).thenReturn(listTeachers);

        listTeachers = teacherService.list(pageable);

        verify(teacherRepository).findAll(pageable);
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    @DisplayName("Happy Test: Teacher Service List Specific Teacher By CPF")
    void shouldListSpecificTeacherCpfUnitaryT() {
        when(teacherRepository.findByCpf(teacherDTOValid.cpf())).thenReturn(Optional.of(teacherEntityValid));

        TeacherResponse teacherResponse = teacherService.specific(teacherDTOValid.cpf(), "cpf");
    
        assertNotNull(teacherResponse);
    }

    
    @Test
    @DisplayName("Happy Test: Teacher Service List Specific Teacher By First Name")
    void shouldListSpecificTeacherFNameUnitaryS() {
        when(teacherRepository.findByFirstName(teacherDTOValid.firstName())).thenReturn(Optional.of(teacherEntityValid));

        TeacherResponse teacherResponse = teacherService.specific(teacherDTOValid.firstName(), "fn");
    
        assertNotNull(teacherResponse);
    }

    @Test
    @DisplayName("Happy Test: Teacher Service List Specific Teacher By First Name")
    void shouldListSpecificTeacherLNameUnitaryS() {
        when(teacherRepository.findByLastName(teacherDTOValid.lastName())).thenReturn(Optional.of(teacherEntityValid));

        TeacherResponse teacherResponse = teacherService.specific(teacherDTOValid.lastName(), "ln");
    
        assertNotNull(teacherResponse);
    }

    @Test
    @DisplayName("Happy Test: Teacher Service Get Age of Teacher")
    void shouldGetAgeOfTeacherUnitaryT() {
        when(teacherRepository.findByTeacherId(anyLong())).thenReturn(Optional.of(teacherEntityValid));

        TeacherAgeResponse response = teacherService.getAge(teacherDTOValid.teacherId());

        assertNotNull(response.age());
    }

    @Test
    @DisplayName("Happy Test: Teacher Service Create Teacher")
    void shouldCreateTeacherUnitaryT() {
        when(teacherRepository.save(any())).thenReturn(teacherEntityValid);

        TeacherResponse createdTeacher = teacherService.create(teacherDTOValid);

        assertEquals( "Pedrro", createdTeacher.firstName());
        assertEquals( "Lost", createdTeacher.lastName());
        assertEquals( "11954672398", createdTeacher.phoneNumber());
    }

    @Test
    @DisplayName("Happy Test: Teacher Service Update Teacher")
    void shouldUpdateTeacherUnitaryT() {
        when(teacherRepository.findByTeacherId(anyLong())).thenReturn(Optional.of(teacherEntityValid));
        when(teacherRepository.save(teacherEntityValid)).thenReturn(teacherEntityUpdate);

        TeacherResponse teacherUpdated = teacherService.update(teacherDTOUpdate, 12L);

        assertEquals(teacherDTOUpdate.firstName(), teacherUpdated.firstName());
        assertEquals(teacherDTOUpdate.lastName(), teacherUpdated.lastName());
        assertEquals(teacherDTOUpdate.birthDate(), teacherUpdated.birthDate());
        assertEquals(teacherDTOUpdate.phoneNumber(), teacherUpdated.phoneNumber());
    }

    @Test
    @DisplayName("Happy Test: Teacher Service Delete Teacher")
    void shouldDeleteTeacherUnitaryT() {
        when(teacherRepository.findByTeacherId(anyLong())).thenReturn(Optional.of(teacherEntityValid));
        when(teacherRepository.findByCpf(anyString())).thenReturn(Optional.of(teacherEntityValid));
     
        Long deleted = teacherService.delete(12L, "1111111112");

        assertEquals(12, deleted);
    }

    @Test
    @DisplayName("Sad Test: Teacher Service Shouldn't Delete Teacher")
    void shouldNotDeleteTeacherUnitaryT() {
    ObjectsDontMatchException thrown = assertThrows(ObjectsDontMatchException.class, () -> {
        when(teacherRepository.findByTeacherId(113L)).thenReturn(Optional.of(teacherEntityUpdate));
        when(teacherRepository.findByCpf("09730461040")).thenReturn(Optional.of(teacherEntityValid));

        teacherService.delete(113L, "09730461040");
    });
    
    assertEquals("Objects found through parameters don't match.", thrown.getMessage());
    }

    @Test
    @DisplayName("Happy Test: Teacher Repository findByCpf")
    void shouldFindByCpfUnitaryT() {
        when(teacherRepository.findByCpf(teacherDTOValid.cpf())).thenReturn(Optional.of(teacherEntityValid));

        Teacher foundTeacher = teacherRepository.findByCpf(teacherDTOValid.cpf()).get();

        assertNotNull(foundTeacher);
    }

    @Test
    @DisplayName("Sad Test: Teacher Repository findByTeacherId")
    void shouldNotFindByEnrollmentIdUnitaryT() {
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            teacherService.findTeacher(10L);
        });
    
        assertEquals("No value present", thrown.getMessage());
    }

    @Test
	@DisplayName("Sad Test: Should thrown DuplicateCpfException in create")
	void shouldThrownDuplicateCpfExceptionUnitaryT() {
		DuplicateCpfException thrown = assertThrows(DuplicateCpfException.class, () -> {
			when(teacherRepository.existsByCpf(anyString())).thenReturn(true);

			teacherService.create(teacherDTOValid);
		});
	
		assertEquals("This cpf already is registered. CPF: 09730461040", thrown.getMessage());
	}

    @Test
    @DisplayName("Happy Test: Teacher Service Verify CPF")
    void shouldVerifyValidCpfUnitaryT() {
        when(teacherRepository.existsByCpf(anyString())).thenReturn(false);

        boolean verification = teacherService.verifyCPF(anyString());

        assertFalse(verification);
    }

    @Test
    @DisplayName("Happy Test: Teacher Service Find Teacher")
    void shouldFindTeacherUnitaryT() {
        when(teacherRepository.findByTeacherId(anyLong())).thenReturn(Optional.of(teacherEntityInvalid));

        Teacher teacher = teacherService.findTeacher(anyLong());

        assertNotNull(teacher);
    }
}
