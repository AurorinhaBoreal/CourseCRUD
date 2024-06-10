package com.db.crud.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.crud.course.dto.request.TeacherRequest;
import com.db.crud.course.dto.response.TeacherResponse;
import com.db.crud.course.service.teacher.TeacherService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/teacher")
public class TeacherController {
    
    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public Page<Object> listPageable(@PageableDefault(size = 3, sort = {"firstName"}) Pageable pageable) {
        
        return teacherService.list(pageable);
    }
    
    @GetMapping("/specific/{info}")
    public ResponseEntity<TeacherResponse> specificStudent(@PathVariable String info) {
        var body = teacherService.specific(info);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
   
    @PostMapping("/create")
    public ResponseEntity<TeacherResponse> create(@RequestBody TeacherRequest teacherRequest) {
        var body = teacherService.create(teacherRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
    
    @PutMapping("/update/{teacherId}")
    public ResponseEntity<TeacherResponse> update(@PathVariable Long teacherId, @RequestBody TeacherRequest updateTeacher) {
        var body = teacherService.update(updateTeacher, teacherId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping("/delete/{teacherId}/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable Long teacherId, @PathVariable String cpf) {
        teacherService.delete(teacherId, cpf);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
