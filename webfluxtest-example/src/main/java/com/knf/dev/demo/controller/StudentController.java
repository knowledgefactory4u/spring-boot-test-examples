package com.knf.dev.demo.controller;

import com.knf.dev.demo.entity.Student;
import com.knf.dev.demo.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public Flux<Student> getAllStudents(){

        return this.studentService.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public Mono<ResponseEntity<Student>> getStudentById(@PathVariable int id){

        return this.studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    public Mono<Student> createStudent(@RequestBody Mono<Student> studentMono){

        return studentMono.flatMap(this.studentService::createStudent);
    }

    @PutMapping("/students/{id}")
    public Mono<Student> updateStudent(@PathVariable int id,
                                        @RequestBody Mono<Student> studentMono){
        return this.studentService.updateStudent(id, studentMono);
    }

    @DeleteMapping("/students/{id}")
    public Mono<Void> deleteStudent(@PathVariable int id){

        return this.studentService.deleteStudent(id);
    }


}
