package com.knf.dev.demo.controller;

import com.knf.dev.demo.entity.Student;
import com.knf.dev.demo.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students/agt/{age}")
    public List<Student> findStudentByAgeGreaterThan(@PathVariable Integer age)
    {
        return studentService.findStudentByAgeGreaterThan(age);
    }

    @GetMapping("/students/alt/{age}")
    public List<Student> findStudentByAgeLessThan(@PathVariable Integer age)
    {
        return studentService.findByAgeLessThan(age);
    }

    @GetMapping("/students/{name}")
    public Student findStudentByName(@PathVariable String name)
    {
        return studentService.findByName(name);
    }
}
