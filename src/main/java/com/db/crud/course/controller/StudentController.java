package com.db.crud.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.crud.course.dto.request.StudentRequest;
import com.db.crud.course.dto.response.StudentAgeResponse;
import com.db.crud.course.dto.response.StudentResponse;
import com.db.crud.course.service.student.StudentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @GetMapping
    public Page<Object> listPageable(@PageableDefault(size = 3, sort = {"firstName"}) Pageable pageable) {
        return studentService.list(pageable);
    }

    @GetMapping("/specific/{info}/{searchType}")
    public ResponseEntity<StudentResponse> specificStudent(@PathVariable String info, @PathVariable String searchType) {
        var body = studentService.specific(info, searchType);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping("/age/{enrollmentId}")
    public ResponseEntity<StudentAgeResponse> getAge(@PathVariable Long enrollmentId) {
        var body = studentService.getAge(enrollmentId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
    

    @PostMapping("/create")
    public ResponseEntity<StudentResponse> create(@RequestBody StudentRequest studentRequest) {
        var body = studentService.create(studentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
    
    @PutMapping("/update/{enrollmentId}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long enrollmentId, @RequestBody StudentRequest updateStudent) {
        var body = studentService.update(updateStudent, enrollmentId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("/delete/{enrollmentId}/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable Long enrollmentId, @PathVariable String cpf) {
        studentService.delete(enrollmentId, cpf);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
