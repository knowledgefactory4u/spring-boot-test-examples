package com.knf.dev.demo.service;

import com.knf.dev.demo.entity.Student;
import com.knf.dev.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Flux<Student> getAllStudents(){

        return this.studentRepository.findAll();
    }

    public Mono<Student> getStudentById(int id){

        return this.studentRepository.findById(id);
    }

    public Mono<Student> createStudent(final Student student){

        return this.studentRepository.save(student);
    }

    public Mono<Student> updateStudent(int id, final Mono<Student> studentMono){

        return this.studentRepository.findById(id)
                .flatMap(p -> studentMono.map(u -> {
                    p.setEmail(u.getEmail());
                    p.setName(u.getName());
                    return p;
                }))
                .flatMap(p -> this.studentRepository.save(p));
    }

    public Mono<Void> deleteStudent(final int id){
        return this.studentRepository.deleteById(id);
    }
}
