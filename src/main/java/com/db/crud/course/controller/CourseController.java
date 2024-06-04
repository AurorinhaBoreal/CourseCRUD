package com.db.crud.course.controller;

import org.springframework.web.bind.annotation.RestController;

import com.db.crud.course.dto.request.CourseRequest;
import com.db.crud.course.dto.response.CourseResponse;
import com.db.crud.course.service.course.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("/course")
public class CourseController {
    
    @Autowired
    private CourseService courseService;

    @GetMapping
    public Page<Object> listPageable(@PageableDefault(size = 3, sort = {"name"}) Pageable pageable) {
        return courseService.list(pageable);
    }

    @PostMapping("/create")
    public ResponseEntity<CourseResponse> create(@RequestBody CourseRequest courseRequest) {
        var body = courseService.create(courseRequest);        
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<CourseResponse> putMethodName(@PathVariable Long courseId, @RequestBody CourseRequest updateCourse) {
        var body = courseService.update(updateCourse, courseId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("/delete/{courseId}/{courseDuration}")
    public ResponseEntity<Void> delete(@PathVariable Long courseId, @PathVariable Integer courseDuration) {
        courseService.delete(courseId, courseDuration);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
