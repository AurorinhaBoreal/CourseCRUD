package com.db.crud.course.service.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.db.crud.course.dto.request.CourseRequest;
import com.db.crud.course.dto.response.CourseResponse;
import com.db.crud.course.model.Course;

public interface CourseService {
    
    public Page<Object> list(Pageable pageable);

    public CourseResponse create(CourseRequest courseRequestDTO);
    
    public CourseResponse update(CourseRequest courseRequestDTO, Long courseId);

    public Long delete(Long courseId, Integer courseDuration);

    public Course findCourse(Long courseId);

}
