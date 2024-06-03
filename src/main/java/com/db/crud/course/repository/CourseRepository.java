package com.db.crud.course.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.crud.course.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseId(Long courseId);
}
