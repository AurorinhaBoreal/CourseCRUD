package com.db.crud.course.unitary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.db.crud.course.dto.mapper.CourseMapper;
import com.db.crud.course.dto.request.CourseRequest;
import com.db.crud.course.dto.response.CourseResponse;
import com.db.crud.course.dto.response.CourseStudentResponse;
import com.db.crud.course.exception.ObjectsDontMatchException;
import com.db.crud.course.fixture.CourseFixture;
import com.db.crud.course.fixture.StudentFixture;
import com.db.crud.course.fixture.TeacherFixture;
import com.db.crud.course.model.Course;
import com.db.crud.course.model.Student;
import com.db.crud.course.model.Teacher;
import com.db.crud.course.repository.CourseRepository;
import com.db.crud.course.repository.StudentRepository;
import com.db.crud.course.repository.TeacherRepository;
import com.db.crud.course.service.course.CourseService;
import com.db.crud.course.service.course.CourseServiceImpl;
import com.db.crud.course.service.student.StudentService;
import com.db.crud.course.service.student.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CourseUnitary {
    @Mock
    CourseRepository courseRepository;
    @Mock
    TeacherRepository teacherRepository;
    @Mock
    StudentRepository studentRepository;
    @Mock
    CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService = new CourseServiceImpl();
    @InjectMocks 
    private StudentService studentService = new StudentServiceImpl();
    
    CourseRequest courseDTOValid = CourseFixture.CourseDTOValidFixture();
    CourseRequest courseDTOInvalid = CourseFixture.CourseDTOInvalidFixture();
    CourseRequest courseDTOUpdate = CourseFixture.CourseDTOUpdateFixture();
    Course courseEntityValid = CourseFixture.CourseEntityValid();
    Course courseEntityInvalid = CourseFixture.CourseEntityInvalid();
    Course courseEntityUpdate = CourseFixture.CourseEntityUpdate();
    Teacher teacherEntityValid = TeacherFixture.TeacherEntityValid();
    Student studentEntityValid = StudentFixture.StudentEntityValid();
    Pageable pageable;

    
    @Test
    @DisplayName("Happy Test: Course Service List Pageable")
    @SuppressWarnings("unchecked")
    void listCourses() {
        var listCourses = mock(Page.class);
        when(courseRepository.findAll(pageable)).thenReturn(listCourses);

        listCourses = courseService.list(pageable);

        verify(courseRepository).findAll(pageable);
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    @DisplayName("Happy Test: Course Service List Students Pageable")
    @SuppressWarnings("unchecked")
    void listStudents() {
        var listStudents = mock(Page.class);
        when(courseRepository.findAll(pageable)).thenReturn(listStudents);

        listStudents = courseService.listStudents(pageable);

        verify(courseRepository).findAll(pageable);
        verifyNoMoreInteractions(courseRepository);
    }

    @Test
    @DisplayName("Happy Test: Course Service Enroll Student in Course")
    void enrollStudent() {
        when(courseRepository.findByCourseId(anyLong())).thenReturn(Optional.of(courseEntityValid));
        when(studentRepository.findByEnrollmentId(anyLong())).thenReturn(Optional.of(studentEntityValid));

        CourseStudentResponse studentResponse = courseService.enroll(111L, 111L);

        assertNotNull(studentResponse.name());
        assertEquals(courseEntityValid.getName(), studentResponse.name());
        assertEquals(courseEntityValid.getCourseId(), studentResponse.courseId());
        assertFalse(studentResponse.students().isEmpty());
    }

    @Test
    @DisplayName("Happy Test: Course Service Create Course")
    void createCourse() {
        when(courseRepository.save(any())).thenReturn(courseEntityValid);
        when(teacherRepository.findByTeacherId(anyLong())).thenReturn(Optional.of(teacherEntityValid));

        CourseResponse createdCourse = courseService.create(courseDTOValid);

        assertEquals("Técnico em Desenvolvimento de Sistemas", createdCourse.name());
        assertEquals(6, createdCourse.semesters());
    }

    @Test
    @DisplayName("Happy Test: Course Service Update Course")
    void ShouldUpdateCourse() {
        when(courseRepository.findByCourseId(anyLong())).thenReturn(Optional.of(courseEntityValid));
        when(courseRepository.save(courseEntityValid)).thenReturn(courseEntityUpdate);

        CourseResponse courseUpdated = courseService.update(courseDTOUpdate, 12L);

        assertEquals(courseDTOUpdate.name(), courseUpdated.name());
        assertEquals(courseDTOUpdate.semesters(), courseUpdated.semesters());
    }

    @Test
    @DisplayName("Happy Test: Course Service Delete Course")
    void shouldDeleteCourse() {
        when(courseRepository.findByCourseId(anyLong())).thenReturn(Optional.of(courseEntityValid));
     
        Long deleted = courseService.delete(111L, 6);

        assertEquals(111, deleted);
    }

    @Test
    @DisplayName("Sad Test: Course Service Shouldn't Delete Course")
    void shouldNotDeleteCourse() {
    ObjectsDontMatchException thrown = assertThrows(ObjectsDontMatchException.class, () -> {
        when(courseRepository.findByCourseId(113L)).thenReturn(Optional.of(courseEntityUpdate));

        courseService.delete(113L, courseEntityValid.getSemesters());
    });
    
    assertEquals("Objects found through parameters don't match.", thrown.getMessage());
    }

    @Test
    @DisplayName("Happy Test: Student Service Find Student")
    void findStudent() {
        when(studentRepository.findByEnrollmentId(anyLong())).thenReturn(Optional.of(studentEntityValid));

        Student student = studentService.findStudent(anyLong());

        assertNotNull(student);
    }
}
