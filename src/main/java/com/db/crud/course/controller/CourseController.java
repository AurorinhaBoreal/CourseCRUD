package com.db.crud.course.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @PostMapping("/create")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }

    @PutMapping("/update/{courseId}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }

    @DeleteMapping("/delete/{courseId}-{courseName}")
    
    
}
