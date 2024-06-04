package com.db.crud.course.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.db.crud.course.dto.mapper.CourseMapper;
import com.db.crud.course.dto.request.CourseRequest;
import com.db.crud.course.dto.response.CourseResponse;
import com.db.crud.course.exception.ObjectsDontMatchException;
import com.db.crud.course.model.Course;
import com.db.crud.course.model.Teacher;
import com.db.crud.course.repository.CourseRepository;
import com.db.crud.course.repository.TeacherRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public Page<Object> list(Pageable pageable) {
        log.info("Searching for Students in the Database...");
        
        return courseRepository.findAll(pageable).map(course -> {
            return CourseMapper.courseToDto(course);
        });
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

    public Teacher verifyTeacher(CourseRequest courseRequest) {
        Teacher teacherFound = teacherRepository.findById(courseRequest.teacherId()).get();
        return teacherFound;
    }
}
