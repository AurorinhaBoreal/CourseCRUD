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

import com.db.crud.course.dto.mapper.TeacherMapper;
import com.db.crud.course.dto.request.TeacherRequest;
import com.db.crud.course.dto.response.TeacherResponse;
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
    void listTeachers() {
        var listTeachers = mock(Page.class);
        when(teacherRepository.findAll(pageable)).thenReturn(listTeachers);

        listTeachers = teacherService.list(pageable);

        verify(teacherRepository).findAll(pageable);
        verifyNoMoreInteractions(teacherRepository);
    }
    // void listTeachers() {
    //     var listTeachers = mock(Page.class);
    //     // when(teacherRepository.findAll(pageable)).thenReturn(listTeachers);
    //     when(teacherRepository.findAll(pageable)).thenReturn(listTeachers);

    //     listTeachers = teacherService.list(pageable);

    //     verify(teacherRepository).findAll(pageable);
    //     verifyNoMoreInteractions(teacherRepository);
    // }

    @Test
    @DisplayName("Happy Test: Teacher Service Create Teacher")
    void createTeacher() {
        when(teacherRepository.save(any())).thenReturn(teacherEntityValid);

        TeacherResponse createdTeacher = teacherService.create(teacherDTOValid);

        assertEquals( "Pedrro", createdTeacher.firstName());
        assertEquals( "Lost", createdTeacher.lastName());
        assertEquals( "11954672398", createdTeacher.phoneNumber());
    }

    @Test
    @DisplayName("Happy Test: Teacher Service Update Teacher")
    void updateTeacher() {
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
    void shouldDeleteTeacher() {
        when(teacherRepository.findByTeacherId(anyLong())).thenReturn(Optional.of(teacherEntityValid));
        when(teacherRepository.findByCpf(anyString())).thenReturn(Optional.of(teacherEntityValid));
     
        Long deleted = teacherService.delete(12L, "1111111112");

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
    @DisplayName("Happy Test: Teacher Repository findByCpf")
    void shouldFindByCpf() {
        when(teacherRepository.findByCpf(teacherDTOValid.cpf())).thenReturn(Optional.of(teacherEntityValid));

        Teacher foundTeacher = teacherRepository.findByCpf(teacherDTOValid.cpf()).get();

        assertNotNull(foundTeacher);
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
    @DisplayName("Happy Test: Teacher Service Verify CPF")
    void verifyValidCpf() {
        when(teacherRepository.existsByCpf(anyString())).thenReturn(false);

        boolean verification = teacherService.verifyCPF(anyString());

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
    @DisplayName("Happy Test: Teacher Service Find Teacher")
    void findTeacher() {
        when(teacherRepository.findByTeacherId(anyLong())).thenReturn(Optional.of(teacherEntityInvalid));

        Teacher teacher = teacherService.findTeacher(anyLong());

        assertNotNull(teacher);
    }
}
