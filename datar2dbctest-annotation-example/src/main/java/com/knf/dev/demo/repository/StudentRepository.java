package com.knf.dev.demo.repository;

import com.knf.dev.demo.entity.Student;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface StudentRepository
        extends ReactiveCrudRepository<Student, Long> {

    @Query("SELECT * FROM Student WHERE email = $1")
    Mono<Student> findStudentByEmail(String email);

}