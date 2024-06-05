package com.db.crud.course.service.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.db.crud.course.dto.mapper.CourseMapper;
import com.db.crud.course.dto.request.CourseRequest;
import com.db.crud.course.dto.response.CourseResponse;
import com.db.crud.course.dto.response.CourseStudentResponse;
import com.db.crud.course.exception.ObjectsDontMatchException;
import com.db.crud.course.model.Course;
import com.db.crud.course.model.Student;
import com.db.crud.course.model.Teacher;
import com.db.crud.course.repository.CourseRepository;
import com.db.crud.course.repository.StudentRepository;
import com.db.crud.course.repository.TeacherRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    StudentRepository studentRepository;

    @Override
    public Page<Object> list(Pageable pageable) {
        log.info("Searching for Students in the Database...");
        
        return courseRepository.findAll(pageable).map(course -> {
            return CourseMapper.courseToDto(course);
        });
    }

    public Page<Object> listStudents(Pageable pageable) {
        log.info("Searching for Students in the Database...");
        
        return courseRepository.findAll(pageable).map(course -> {
            return CourseMapper.courseStudent(course);
        });
    }

    @Override
    public CourseStudentResponse enroll(Long courseId, Long studentId) {
        Course courseFound = findCourse(courseId);
        Student studentFound = findStudent(studentId);

        List<Student> listStudents = courseFound.getStudents();
        listStudents.add(studentFound);
        courseFound.setStudents(listStudents);

        courseRepository.save(courseFound);

        CourseStudentResponse studentResponse = CourseMapper.courseStudent(courseFound);
        return studentResponse;
    }


    @Override
    @Transactional
    public CourseStudentResponse disenroll(Long courseId, Long studentId) {
        Course courseFound = findCourse(courseId);
        Student studentFound = findStudent(studentId);

        courseRepository.desmatricularAluno(courseFound.getId(), studentFound.getId());

        CourseStudentResponse studentResponse = CourseMapper.courseStudent(courseFound);
        courseRepository.save(courseFound);


        return studentResponse;
    }

    @Override
    public CourseResponse create(CourseRequest courseRequestDTO) {

        Teacher teacher = verifyTeacher(courseRequestDTO);
        Course course = CourseMapper.dtoToCourse(courseRequestDTO, teacher);
        
        courseRepository.save(course);

        return CourseMapper.courseToDto(course);
    }

    @Override
    public CourseResponse update(CourseRequest courseRequestDTO, Long courseId) {
        Course originalCourse = findCourse(courseId);

        CourseMapper.updateEntity(originalCourse, courseRequestDTO);
        courseRepository.save(originalCourse);

        return CourseMapper.courseToDto(originalCourse);
    }

    @Override
    public Long delete(Long courseId, Integer courseDuration) {
        Course course = findCourse(courseId);

        if (course.getSemesters() == courseDuration) {
            courseRepository.delete(course);
            return courseId;
        } else {
            throw new ObjectsDontMatchException("Objects found through parameters don't match.");
        }
        
    }

    @Override
    public Course findCourse(Long courseId) {
        Course courseFounded = courseRepository.findByCourseId(courseId).get();

        return courseFounded;
    }

    public Student findStudent(long studentId) {
        Student studentFounded = studentRepository.findByEnrollmentId(studentId).get();

        return studentFounded;
    }
    public Teacher verifyTeacher(CourseRequest courseRequest) {
        Teacher teacherFound = teacherRepository.findByTeacherId(courseRequest.teacherId()).get();
        return teacherFound;
    }
}
