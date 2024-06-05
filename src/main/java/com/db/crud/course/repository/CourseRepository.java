package com.db.crud.course.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.db.crud.course.model.Course;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseId(Long courseId);
    Optional<Course> findByName(String name);

    @Modifying
    @Query(value = "DELETE FROM tbl_course_students WHERE course_id = :course_id AND student_id = :student_id", nativeQuery = true)
    void desmatricularAluno(@Param("course_id") Long courseId, @Param("student_id") Long studentId);

}
