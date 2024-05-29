package com.db.crud.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.crud.course.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @GetMapping
    public Page<Object> studentPageable(@PageableDefault(size = 3, sort = {"firstName"}) Pageable pageable) {
        return studentService.listar(pageable);
    }
}
